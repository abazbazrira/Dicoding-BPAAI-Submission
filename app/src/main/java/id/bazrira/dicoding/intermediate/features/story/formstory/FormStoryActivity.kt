package id.bazrira.dicoding.intermediate.features.story.formstory

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import id.bazrira.dicoding.intermediate.R
import id.bazrira.dicoding.intermediate.abstraction.base.BaseActivity
import id.bazrira.dicoding.intermediate.abstraction.base.MyApplication
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.ID
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.ImageUtils.convertUriFile
import id.bazrira.dicoding.intermediate.abstraction.utils.ImageUtils.fixImageOrientation
import id.bazrira.dicoding.intermediate.abstraction.utils.ImageUtils.reduceImageSize
import id.bazrira.dicoding.intermediate.abstraction.utils.load
import id.bazrira.dicoding.intermediate.abstraction.utils.showToast
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelFactory
import id.bazrira.dicoding.intermediate.databinding.ActivityFormStoryBinding
import id.bazrira.dicoding.intermediate.di.component.features.story.DaggerStoryComponent
import id.bazrira.dicoding.intermediate.di.module.features.story.StoryModule
import id.bazrira.dicoding.intermediate.features.story.liststory.ListStoryActivity
import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FormStoryActivity :
  BaseActivity<ActivityFormStoryBinding>({ ActivityFormStoryBinding.inflate(it) }),
  EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  private val viewModel: FormStoryViewModel by viewModels { viewModelFactory }

  private lateinit var currentPhotoPath: String
  private lateinit var file: File

  private val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

  override fun ActivityFormStoryBinding.oncreate(savedInstanceState: Bundle?) {
    initInjector()
    setCustomDialog()

    binding.apply {
      imageViewPhoto.setOnClickListener {
        startActivityForResult(getPickImageChooserIntent(), REQUEST_IMAGE_STORY)
      }

      buttonAddStory.setOnClickListener {
        val fileReduce = reduceImageSize(file)
        val imageFile = fileReduce.asRequestBody("image/jpg".toMediaType())
        val imgMultiPart: MultipartBody.Part =
          MultipartBody.Part.createFormData("photo", fileReduce.name, imageFile)

        val description =
          editTextDescription.text.toString().toRequestBody("text/plain".toMediaType())
        val longitude = 0f.toString().toRequestBody("text/plain".toMediaType())
        val latitude = 0f.toString().toRequestBody("text/plain".toMediaType())

        viewModel.addStory(
          AddNewStoryRequest(
            description = description,
            longitude = longitude,
            latitude = latitude,
            photo = imgMultiPart,
          )
        )
      }
    }

    if (intent.extras != null) {
      binding.imageViewPhoto.setOnClickListener(null)
      viewModel.detailStory(
        bodyRequest = GetDetailStoryRequest(
          id = intent.extras!!.getString(
            ID
          ).toString()
        )
      )
    } else {
      checkPermission()
    }
  }

  override fun observerViewModel() {
    viewModel.stateSubmit.observe(this) {
      when (it) {
        is Resource.Loading -> {
          showProgressDialog()
        }
        is Resource.Success -> {
          showMessageDialog("Success", it.message)

          bindingDialog.buttonOk.setOnClickListener {
            hideDialog()

            val intent = Intent(this@FormStoryActivity, ListStoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
          }
        }
        is Resource.Error -> {
          showMessageDialog("Error", it.message)
        }
      }
    }

    viewModel.stateDetail.observe(this) {
      when (it) {
        is Resource.Loading -> {
          showProgressDialog()
        }
        is Resource.Success -> {
          hideDialog()

          binding.apply {
            imageViewPhoto.load(it.data?.photoUrl)
            editTextDescription.setText(it.data?.description.toString())
            editTextDescription.isEnabled = false
            buttonAddStory.visibility = View.GONE

            textViewName.text = "Posted by ${it.data?.name}"
            textViewName.visibility = View.VISIBLE
          }
        }
        is Resource.Error -> {
          showMessageDialog("Error", it.message)
        }
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
      val yes = "Yes"
      val no = "No"
      // Do something after user returned from app settings screen, like showing a Toast.
      showToast(
        getString(
          R.string.returned_from_app_settings_to_activity,
          if (hasCameraPermission()) yes else no,
          if (hasGalleryPermissions()) yes else no
        )
      )
    } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_STORY) {

      val isCamera = isCamera(data)

      val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (isCamera) Uri.fromFile(File(currentPhotoPath))
        else getPickImageResultUri(data)
      } else {
        getPickImageResultUri(data)
      }

      if (imageUri != null) {
        var bitmap: Bitmap
        try {
          bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)

          if (isCamera) {
            val getImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
              getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            } else {
              externalCacheDir
            }

            if (getImage != null) {
              file = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(currentPhotoPath)
              } else {
                File(getImage.path, "photo.png")
              }.apply {
                reduceImageSize(this)
              }

              val ei = ExifInterface(FileInputStream(file))
              val orientation: Int = ei.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
              )

              bitmap = fixImageOrientation(bitmap, orientation)
            }
          } else {
            file = convertUriFile(imageUri, this@FormStoryActivity)
          }

          binding.imageViewPhoto.load(bitmap)
        } catch (e: IOException) {
          e.printStackTrace()
        }
      }
    }
  }

  /**
   * Get the URI of the selected image from [.getPickImageChooserIntent].<br></br>
   * Will return the correct URI for camera and gallery image.
   *
   * @param data the returned data of the activity result
   */
  private fun getPickImageResultUri(data: Intent?): Uri? {
    return if (isCamera(data)) getCaptureImageOutputUri() else data?.data
  }

  private fun isCamera(data: Intent?): Boolean {
    var isCamera = true
    if (data != null) {
      val action = data.action
      isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
    }
    return isCamera
  }

  @Throws(IOException::class)
  private fun createImageFile(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    if (storageDir?.exists() == true) {
      storageDir.deleteRecursively()
      Log.d("CHECK-DATA", "storageDir.exists() -> ${storageDir.exists()}")
      storageDir.mkdir()
    }

    return File.createTempFile(
      "JPEG_${timeStamp}_", /* prefix */
      ".jpg", /* suffix */
      storageDir /* directory */
    ).apply {
      // Save a file: path for use with ACTION_VIEW intents
      currentPhotoPath = absolutePath
    }
  }

  /**
   * Get URI to image received from capture by camera.
   */
  private fun getCaptureImageOutputUri(): Uri? {
    var outputFileUri: Uri? = null
    val getImage = externalCacheDir
    if (getImage != null) {
      outputFileUri = Uri.fromFile(File(getImage.path, "photo.png"))
    }
    return outputFileUri
  }

  /**
   * Create a chooser intent to select the source to get image from.<br></br>
   * The source can be camera's (ACTION_IMAGE_CAPTURE) or gallery's (ACTION_GET_CONTENT).<br></br>
   * All possible sources are added to the intent chooser.
   */
  private fun getPickImageChooserIntent(): Intent? {

    // Determine Uri of camera image to save.
    var outputFileUri = getCaptureImageOutputUri()
    val allIntents = arrayListOf<Intent>()
    val packageManager = packageManager

    // collect all camera intents
    val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

    captureIntent.resolveActivity(packageManager)

    val listCam = packageManager.queryIntentActivities(captureIntent, 0)
    for (res in listCam) {
      val intent = Intent(captureIntent)
      intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
      intent.setPackage(res.activityInfo.packageName)
      if (outputFileUri != null) {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
      }

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val photoFile: File = createImageFile()
        outputFileUri = FileProvider.getUriForFile(
          this@FormStoryActivity, "com.example.android.fileprovider", photoFile
        )
      }
      intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)

      intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
      allIntents.add(intent)
    }

    // collect all gallery intents
    val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
    galleryIntent.type = "image/*"
    val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
    for (res in listGallery) {
      val intent = Intent(galleryIntent)
      intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
      intent.setPackage(res.activityInfo.packageName)
      allIntents.add(intent)
    }

    // the main intent is the last in the list (fucking android) so pickup the useless one
    var mainIntent: Intent? = allIntents[allIntents.size - 1]
    for (intent in allIntents) {
      if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
        mainIntent = intent
        break
      }
    }
    allIntents.remove(mainIntent)

    // Create a chooser from the main intent
    val chooserIntent = Intent.createChooser(mainIntent, "Select source")

    // Add all other intents
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray())
    return chooserIntent
  }

  private fun checkPermission() {
    if (!EasyPermissions.hasPermissions(this, *perms)) {
      EasyPermissions.requestPermissions(this, "Aplikasi ini membutuhkan izin akses", 1, *perms)
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String>,
    grantResults: IntArray,
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
  }

  override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

  override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    EasyPermissions.requestPermissions(
      this, "Aplikasi ini membutuhkan izin akses", 1, *this.perms
    )
  }

  private fun hasGalleryPermissions(): Boolean {
    return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)
  }

  private fun hasCameraPermission(): Boolean {
    return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
  }

  override fun onRationaleAccepted(requestCode: Int) {
    Log.d("CHECK-DATA", "onRationaleAccepted: $requestCode")
  }

  override fun onRationaleDenied(requestCode: Int) {
    Log.d("CHECK-DATA", "onRationaleDenied: $requestCode")
  }

  private fun initInjector() {
    DaggerStoryComponent.builder()
      .repoComponent(MyApplication().getRepoComponent(this@FormStoryActivity))
      .storyModule(StoryModule()).build().inject(this@FormStoryActivity)
  }

  override fun onBackPressed() {
    super.onBackPressed()

    finish()
  }

  companion object {
    private const val REQUEST_IMAGE_STORY = 100
  }
}