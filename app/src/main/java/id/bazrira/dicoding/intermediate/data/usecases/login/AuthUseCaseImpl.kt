package id.bazrira.dicoding.intermediate.data.usecases.login

import id.bazrira.dicoding.intermediate.data.repository.authentication.AuthRepository
import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import io.reactivex.Observable
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
  private val repository: AuthRepository,
) : AuthUseCase {

  override fun login(bodyRequest: LoginRequest): Observable<Boolean> {
    return repository.login(bodyRequest)
  }

  override fun register(bodyRequest: RegisterRequest): Observable<Boolean> {
    return repository.register(bodyRequest)
  }
}