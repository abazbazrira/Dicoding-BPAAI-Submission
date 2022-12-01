package id.bazrira.dicoding.intermediate.data.repository.authentication.source.remote

import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import id.bazrira.dicoding.intermediate.network.response.LoginResponse
import id.bazrira.dicoding.intermediate.network.response.RegisterResponse
import io.reactivex.Observable

interface AuthRemoteDataSource {

  fun login(bodyRequest: LoginRequest): Observable<LoginResponse>
  fun register(bodyRequest: RegisterRequest): Observable<RegisterResponse>
}