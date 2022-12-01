package id.bazrira.dicoding.intermediate.network.response

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.DetailStory.STORY

data class GetDetailStoryResponse(
  @SerializedName(STORY) val story: StoryItemResponse,
) : BaseResponse()