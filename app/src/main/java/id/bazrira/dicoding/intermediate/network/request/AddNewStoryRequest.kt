package id.bazrira.dicoding.intermediate.network.request

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class AddNewStoryRequest(
  val description: RequestBody,
  val latitude: RequestBody,
  val longitude: RequestBody,
  val photo: MultipartBody.Part
)
