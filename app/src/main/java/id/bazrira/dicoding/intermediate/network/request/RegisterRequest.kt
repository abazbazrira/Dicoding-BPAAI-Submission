package id.bazrira.dicoding.intermediate.network.request

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.Register.EMAIL
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.Register.NAME
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.Register.PASSWORD

data class RegisterRequest(
  @SerializedName(NAME) val name: String?,
  @SerializedName(EMAIL) val email: String?,
  @SerializedName(PASSWORD) val password: String?,
)
