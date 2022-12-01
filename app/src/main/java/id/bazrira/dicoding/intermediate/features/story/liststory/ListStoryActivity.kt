package id.bazrira.dicoding.intermediate.features.story.liststory

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gzeinnumer.rab.singleType.AdapterBuilder
import com.gzeinnumer.rab.singleType.AdapterCreator
import id.bazrira.dicoding.intermediate.R
import id.bazrira.dicoding.intermediate.abstraction.base.BaseActivity
import id.bazrira.dicoding.intermediate.abstraction.base.MyApplication
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.ID
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.load
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelFactory
import id.bazrira.dicoding.intermediate.data.model.StoryModel
import id.bazrira.dicoding.intermediate.databinding.ActivityListStoryBinding
import id.bazrira.dicoding.intermediate.databinding.ItemStoryBinding
import id.bazrira.dicoding.intermediate.di.component.features.story.DaggerStoryComponent
import id.bazrira.dicoding.intermediate.di.module.features.story.StoryModule
import id.bazrira.dicoding.intermediate.features.authentication.login.LoginActivity
import id.bazrira.dicoding.intermediate.features.story.formstory.FormStoryActivity
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import javax.inject.Inject

class ListStoryActivity :
  BaseActivity<ActivityListStoryBinding>({ ActivityListStoryBinding.inflate(it) }) {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  private val viewModel: ListStoryViewModel by viewModels { viewModelFactory }

  private lateinit var adapterStory: AdapterCreator<StoryModel>
  private var listStories: MutableList<StoryModel> = arrayListOf()

  override fun ActivityListStoryBinding.oncreate(savedInstanceState: Bundle?) {
    initInjector()
    initAdapter()
    setCustomDialog()

    viewModel.getAllStories(GetAllStoriesRequest())

    binding.apply {
      recyclerViewStories.apply {
        adapter = adapterStory
        layoutManager = LinearLayoutManager(applicationContext)
        hasFixedSize()
      }

      imageViewLogout.setOnClickListener {
        AlertDialog.Builder(this@ListStoryActivity)
          .setCancelable(false)
          .setTitle("Konfirmasi")
          .setMessage("Apakah Anda yakin ingin logout?")
          .setPositiveButton("Yakin") { dialog, _ ->
            dialog.dismiss()

            viewModel.clearUserData()
            logout()
          }
          .setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
          }.create().show()
      }

      swipeRefresh.setOnRefreshListener {
        viewModel.getAllStories(GetAllStoriesRequest())
      }

      buttonAddStory.setOnClickListener {
        startActivity(Intent(this@ListStoryActivity, FormStoryActivity::class.java))
      }
    }
  }

  private fun initAdapter() {
    adapterStory = AdapterBuilder<StoryModel>(R.layout.item_story)
      .setList(listStories)
      .onBind { adapter, holder, data, position ->

        val bindingItem = ItemStoryBinding.bind(holder)
        bindingItem.apply {
          this.textViewName.text = "Posted by ${data.name}"
          this.textViewDescription.text = data.description
          this.imageViewPhoto.load(data.photoUrl)

          holder.setOnClickListener {
            Intent(this@ListStoryActivity, FormStoryActivity::class.java).also {
              it.putExtra(ID, data.id)

              startActivity(it)
            }
          }
        }
      }
  }

  private fun logout() {
    Intent(this@ListStoryActivity, LoginActivity::class.java).also {
      it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

      startActivity(it)
      finish()
    }
  }

  override fun observerViewModel() {
    viewModel.isAuthenticated.observe(this) {
      if (!it) {
        bindingDialog.buttonOk.setOnClickListener {
          viewModel.clearUserData()
          logout()
        }
      }
    }

    viewModel.state.observe(this) {
      when (it) {
        is Resource.Loading -> {
          showProgressDialog()
        }
        is Resource.Success -> {
          hideDialog()

          binding.swipeRefresh.apply {
            if (isRefreshing) isRefreshing = false
          }

          adapterStory.setList(it.data)
        }
        is Resource.Error -> {
          showMessageDialog("Error", it.message)
        }
      }
    }
  }

  private fun initInjector() {
    DaggerStoryComponent.builder()
      .repoComponent(MyApplication().getRepoComponent(this@ListStoryActivity))
      .storyModule(StoryModule())
      .build()
      .inject(this@ListStoryActivity)
  }
}