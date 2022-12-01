package id.bazrira.dicoding.intermediate.data.usecases.login


import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import io.reactivex.Observable

interface AuthUseCase {

  fun login(bodyRequest: LoginRequest): Observable<Boolean>
  fun register(bodyRequest: RegisterRequest): Observable<Boolean>
}