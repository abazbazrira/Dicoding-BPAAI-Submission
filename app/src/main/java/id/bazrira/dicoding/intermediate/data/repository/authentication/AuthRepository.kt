package id.bazrira.dicoding.intermediate.data.repository.authentication

import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import io.reactivex.Observable

interface AuthRepository {

  fun login(bodyRequest: LoginRequest): Observable<Boolean>
  fun register(bodyRequest: RegisterRequest): Observable<Boolean>
}