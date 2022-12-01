package id.bazrira.dicoding.intermediate.data.repository.authentication

import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Headers.BEARER
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.preferences.PreferencesImpl.Companion.IS_LOGGED_IN
import id.bazrira.dicoding.intermediate.data.preferences.PreferencesImpl.Companion.TOKEN
import id.bazrira.dicoding.intermediate.data.repository.authentication.source.remote.AuthRemoteDataSource
import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import io.reactivex.Observable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
  private val remoteDataSource: AuthRemoteDataSource,
  private val preferences: Preferences,
) : AuthRepository {
  override fun login(bodyRequest: LoginRequest): Observable<Boolean> {
    return remoteDataSource.login(bodyRequest = bodyRequest).map {
      if (!it.error) {
        preferences.saveData(TOKEN, "${BEARER}${it.loginResult.token.toString()}")
        preferences.saveData(IS_LOGGED_IN, true)
      }

      !it.error
    }
  }

  override fun register(bodyRequest: RegisterRequest): Observable<Boolean> {
    return remoteDataSource.register(bodyRequest = bodyRequest).map {
      !it.error
    }
  }
}