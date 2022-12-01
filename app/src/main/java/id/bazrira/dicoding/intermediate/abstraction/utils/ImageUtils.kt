package id.bazrira.dicoding.intermediate.abstraction.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder.createSource
import android.graphics.ImageDecoder.decodeBitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore.Images.Media.getBitmap
import android.provider.Settings.System.DATE_FORMAT
import android.util.Base64
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


object ImageUtils {
  fun bitmapToBase64String(uri: Uri, quality: Int, contentResolver: ContentResolver): String {
    var bitmap: Bitmap? = null
    try {
      bitmap = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
        decodeBitmap(createSource(contentResolver, uri))
      } else {
        getBitmap(contentResolver, uri)
      }
    } catch (e: IOException) {
      e.printStackTrace()
    }
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap!!.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    val bytes = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
  }

  fun fixImageOrientation(bitmap: Bitmap, orientation: Int): Bitmap {
    return when (orientation) {
      ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(bitmap, 90)
      ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(bitmap, 180)
      ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(bitmap, 270)
      ExifInterface.ORIENTATION_NORMAL -> bitmap
      else -> bitmap
    }
  }

  fun base64StringToBitmap(encodedImage: String): Bitmap {
    val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT or Base64.NO_WRAP)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
  }

  fun reduceImageSize(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var quality = 100
    var length: Int
    do {
      val stream = ByteArrayOutputStream()
      bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
      val byteArray = stream.toByteArray()
      length = byteArray.size
      quality -= 5
    } while (length > 1000000)
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, FileOutputStream(file))
    return file
  }

  fun convertUriFile(uri: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val file = tempFile(context)
    val inputStream = contentResolver.openInputStream(uri) as InputStream
    val outputStream: OutputStream = FileOutputStream(file)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0)
      outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return file
  }

  private fun tempFile(context: Context): File {
    val dir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(SimpleDateFormat("dd-MM-yyyy", Locale.US).format(System.currentTimeMillis()), ".jpg", dir)
  }
}