package id.bazrira.dicoding.intermediate.features.authentication.register

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import id.bazrira.dicoding.intermediate.R
import id.bazrira.dicoding.intermediate.abstraction.base.BaseActivity
import id.bazrira.dicoding.intermediate.abstraction.base.MyApplication
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.load
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelFactory
import id.bazrira.dicoding.intermediate.databinding.ActivityRegisterBinding
import id.bazrira.dicoding.intermediate.di.component.features.auth.DaggerAuthComponent
import id.bazrira.dicoding.intermediate.di.module.features.auth.AuthModule
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import javax.inject.Inject

class RegisterActivity :
  BaseActivity<ActivityRegisterBinding>({ ActivityRegisterBinding.inflate(it) }) {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  private val viewModel: RegisterViewModel by viewModels { viewModelFactory }

  override fun ActivityRegisterBinding.oncreate(savedInstanceState: Bundle?) {
    initInjector()

    binding.apply {
      imageViewIcon.load(getDrawable(R.drawable.icon_dicoding))

      buttonLogin.setOnClickListener {
        finish()
      }

      buttonRegister.setOnClickListener {
        viewModel.register(
          RegisterRequest(
            name = editTextName.text.toString(),
            password = editTextPassword.editText?.text.toString(),
            email = editTextUsername.text.toString()
          )
        )
      }
    }

    setCustomDialog {
      alertDialog.dismiss()
      finish()
    }
  }

  override fun observerViewModel() {
    viewModel.state.observe(this) {
      when (it) {
        is Resource.Loading -> {
          bindingDialog.textViewStatus.visibility = View.GONE
          bindingDialog.textViewDescription.visibility = View.GONE
          bindingDialog.buttonOk.visibility = View.GONE
          bindingDialog.progressBar.visibility = View.VISIBLE

          alertDialog.show()
        }
        is Resource.Success -> {
          bindingDialog.textViewStatus.text = "Data"
          bindingDialog.textViewStatus.visibility = View.VISIBLE

          bindingDialog.textViewDescription.text = "Account Registered"
          bindingDialog.textViewDescription.visibility = View.VISIBLE

          bindingDialog.buttonOk.visibility = View.VISIBLE
          bindingDialog.progressBar.visibility = View.GONE
        }
        is Resource.Error -> {
          bindingDialog.textViewStatus.text = "Error"
          bindingDialog.textViewStatus.visibility = View.VISIBLE

          bindingDialog.textViewDescription.text = it.message
          bindingDialog.textViewDescription.visibility = View.VISIBLE

          bindingDialog.buttonOk.visibility = View.VISIBLE
          bindingDialog.progressBar.visibility = View.GONE
        }
      }
    }
  }

  private fun initInjector() {
    DaggerAuthComponent.builder()
      .repoComponent(MyApplication().getRepoComponent(this@RegisterActivity))
      .authModule(AuthModule())
      .build()
      .inject(this@RegisterActivity)
  }
}