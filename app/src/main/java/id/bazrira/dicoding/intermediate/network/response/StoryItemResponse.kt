package id.bazrira.dicoding.intermediate.network.response

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.CREATED_AT
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.DESCRIPTION
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.ID
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.LATITUDE
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.LONGITUDE
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.NAME
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.StoryItem.PHOTO_URL

data class StoryItemResponse(
  @SerializedName(ID) val id: String?,
  @SerializedName(NAME) val name: String?,
  @SerializedName(DESCRIPTION) val description: String?,
  @SerializedName(PHOTO_URL) val photoUrl: String?,
  @SerializedName(CREATED_AT) val createdAt: String?,
  @SerializedName(LATITUDE) val latitude: Float?,
  @SerializedName(LONGITUDE) val longitude: Float?,
)
