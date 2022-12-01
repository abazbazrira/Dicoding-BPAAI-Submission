package id.bazrira.dicoding.intermediate.network.request

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.Login.EMAIL
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.Login.PASSWORD

data class LoginRequest(
  @SerializedName(EMAIL) val email: String?,
  @SerializedName(PASSWORD) val password: String?,
)
