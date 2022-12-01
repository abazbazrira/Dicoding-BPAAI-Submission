package id.bazrira.dicoding.intermediate.abstraction.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.HttpException


object NetworkUtils {

  fun isHttpCodeError(throwable: Throwable): Boolean {
    return (throwable is HttpException && throwable.code() !in 300 downTo 199)
  }

  fun isHttpCodeError(throwable: Throwable, statusCode: Int): Boolean {
    return (throwable is HttpException && throwable.code() == statusCode)
  }

  fun isHttpStatusCodeV2(responseCode: Int, statusCode: Int): Boolean {
    return (responseCode == statusCode)
  }

  fun <T> getErrorMessage(throwable: Throwable, java: Class<T>): T? {
    val body = (throwable as HttpException).response()?.errorBody()
    val builder: Moshi = Moshi
      .Builder()
      .add(KotlinJsonAdapterFactory())
      .build()

    val jsonAdapter = builder.adapter(java)

    return jsonAdapter.fromJson(body!!.string())
  }

  fun <T> getErrorMessage(body: ResponseBody?, java: Class<T>): T? {
    val builder: Moshi = Moshi
      .Builder()
      .add(KotlinJsonAdapterFactory())
      .build()

    val jsonAdapter = builder.adapter(java)

    return jsonAdapter.fromJson(body!!.string())
  }
}