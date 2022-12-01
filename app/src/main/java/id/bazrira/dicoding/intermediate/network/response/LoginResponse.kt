package id.bazrira.dicoding.intermediate.network.response

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.Login.LOGIN_RESULT
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.Login.NAME
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.Login.TOKEN
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.Login.USER_ID

data class LoginResponse(
  @SerializedName(LOGIN_RESULT) val loginResult: LoginResultData,
) : BaseResponse() {

  data class LoginResultData(
    @SerializedName(USER_ID) val userId: String?,
    @SerializedName(NAME) val name: String?,
    @SerializedName(TOKEN) val token: String?,
  )
}
