package id.bazrira.dicoding.intermediate.features.authentication.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import id.bazrira.dicoding.intermediate.R
import id.bazrira.dicoding.intermediate.abstraction.base.BaseActivity
import id.bazrira.dicoding.intermediate.abstraction.base.MyApplication
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.load
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelFactory
import id.bazrira.dicoding.intermediate.databinding.ActivityLoginBinding
import id.bazrira.dicoding.intermediate.di.component.features.auth.DaggerAuthComponent
import id.bazrira.dicoding.intermediate.di.module.features.auth.AuthModule
import id.bazrira.dicoding.intermediate.features.authentication.register.RegisterActivity
import id.bazrira.dicoding.intermediate.features.story.liststory.ListStoryActivity
import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>({ ActivityLoginBinding.inflate(it) }) {

  @Inject
  lateinit var viewModelFactory: ViewModelFactory
  private val viewModel: LoginViewModel by viewModels { viewModelFactory }

  override fun ActivityLoginBinding.oncreate(savedInstanceState: Bundle?) {
    initInjector()

    binding.apply {
      imageViewIcon.load(getDrawable(R.drawable.icon_dicoding))

      textViewRegister.setOnClickListener {
        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
      }

      buttonLogin.setOnClickListener {
        viewModel.login(
          LoginRequest(
            email = editTextUsername.text.toString(),
            password = editTextPassword.editText?.text.toString(),
          )
        )
      }
    }

    setCustomDialog()

    viewModel.isLoggedIn.observe(this@LoginActivity) {
      if(it){
        startActivity(Intent(this@LoginActivity, ListStoryActivity::class.java))
        finish()
      }
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
          alertDialog.dismiss()

          binding.apply {
            motionLayout.setTransition(R.id.motionSuccessLogin)
            motionLayout.progress = 0f
            motionLayout.transitionToEnd()
          }

          Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@LoginActivity, ListStoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            startActivity(intent)
            finish()
          }, 1500)
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
      .repoComponent(MyApplication().getRepoComponent(this@LoginActivity))
      .authModule(AuthModule())
      .build()
      .inject(this@LoginActivity)
  }
}