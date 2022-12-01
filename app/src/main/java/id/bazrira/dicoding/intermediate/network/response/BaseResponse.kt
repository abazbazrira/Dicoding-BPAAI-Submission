package id.bazrira.dicoding.intermediate.network.response

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.Base.ERROR
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.Base.MESSAGE

open class BaseResponse(
  @SerializedName(ERROR) val error: Boolean = false,
  @SerializedName(MESSAGE) val message: String = "",
)