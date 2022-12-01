package id.bazrira.dicoding.intermediate.network

import id.bazrira.dicoding.intermediate.BuildConfig
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Url.STORY_API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {

  private fun okHttpClient(): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    if (!BuildConfig.DEBUG) {
      logging.setLevel(HttpLoggingInterceptor.Level.NONE)
    } else {
      logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    return OkHttpClient.Builder()
      .retryOnConnectionFailure(true)
      .addInterceptor(logging)
      .pingInterval(30, TimeUnit.SECONDS)
      .readTimeout(1, TimeUnit.MINUTES)
      .connectTimeout(1, TimeUnit.SECONDS)
      .build()
  }

  fun builder(baseUrl: String = STORY_API_URL): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .client(okHttpClient())
      .build()
  }
}