package id.bazrira.dicoding.intermediate.di.module.features.auth

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import id.bazrira.dicoding.intermediate.abstraction.viewmodel.ViewModelKey
import id.bazrira.dicoding.intermediate.di.module.features.ViewModelModule
import id.bazrira.dicoding.intermediate.features.authentication.login.LoginViewModel
import id.bazrira.dicoding.intermediate.features.authentication.register.RegisterViewModel

@Module(
  includes = [
    ViewModelModule::class
  ]
)
abstract class AuthViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(RegisterViewModel::class)
  internal abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel
}