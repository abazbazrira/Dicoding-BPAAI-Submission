package id.bazrira.dicoding.intermediate.data.repository.authentication.source.remote

import id.bazrira.dicoding.intermediate.network.Services
import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import id.bazrira.dicoding.intermediate.network.response.LoginResponse
import id.bazrira.dicoding.intermediate.network.response.RegisterResponse
import io.reactivex.Observable
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
  private val services: Services
) : AuthRemoteDataSource {
  override fun login(bodyRequest: LoginRequest): Observable<LoginResponse> {
    return services.login(bodyRequest = bodyRequest)
  }

  override fun register(bodyRequest: RegisterRequest): Observable<RegisterResponse> {
    return services.register(bodyRequest = bodyRequest)
  }
}