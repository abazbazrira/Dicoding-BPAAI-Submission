package id.bazrira.dicoding.intermediate.di.module.features.auth

import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.data.repository.authentication.AuthRepository
import id.bazrira.dicoding.intermediate.data.usecases.login.AuthUseCase
import id.bazrira.dicoding.intermediate.data.usecases.login.AuthUseCaseImpl
import id.bazrira.dicoding.intermediate.di.scope.FeatureScope

@Module
class AuthModule {

  @Provides
  @FeatureScope
  fun provideAuthUseCase(repository: AuthRepository): AuthUseCase {
    return AuthUseCaseImpl(repository)
  }
}