package id.bazrira.dicoding.intermediate.di.module.repository

import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.repository.authentication.AuthRepository
import id.bazrira.dicoding.intermediate.data.repository.authentication.AuthRepositoryImpl
import id.bazrira.dicoding.intermediate.data.repository.authentication.source.remote.AuthRemoteDataSource
import id.bazrira.dicoding.intermediate.data.repository.authentication.source.remote.AuthRemoteDataSourceImpl
import id.bazrira.dicoding.intermediate.di.scope.RepositoryScope
import id.bazrira.dicoding.intermediate.network.Services

@Module
class AuthRepositoryModule {

  @Provides
  @RepositoryScope
  fun provideAuthRemoteDataSource(
    services: Services
  ): AuthRemoteDataSource {
    return AuthRemoteDataSourceImpl(services)
  }

  @Provides
  @RepositoryScope
  fun provideAuthRepository(
    remoteDataSource: AuthRemoteDataSource,
    preferences: Preferences,
  ): AuthRepository {
    return AuthRepositoryImpl(remoteDataSource, preferences)
  }
}