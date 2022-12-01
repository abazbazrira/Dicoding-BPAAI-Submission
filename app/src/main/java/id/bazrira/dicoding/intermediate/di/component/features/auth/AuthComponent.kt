package id.bazrira.dicoding.intermediate.di.component.features.auth

import dagger.Component
import id.bazrira.dicoding.intermediate.di.component.RepoComponent
import id.bazrira.dicoding.intermediate.di.module.features.auth.AuthModule
import id.bazrira.dicoding.intermediate.di.module.features.auth.AuthViewModelModule
import id.bazrira.dicoding.intermediate.di.scope.FeatureScope
import id.bazrira.dicoding.intermediate.features.authentication.login.LoginActivity
import id.bazrira.dicoding.intermediate.features.authentication.register.RegisterActivity

@FeatureScope
@Component(
  dependencies = [RepoComponent::class],
  modules = [
    AuthModule::class,
    AuthViewModelModule::class,
  ]
)
interface AuthComponent {
  fun inject(target: LoginActivity)
  fun inject(target: RegisterActivity)
}