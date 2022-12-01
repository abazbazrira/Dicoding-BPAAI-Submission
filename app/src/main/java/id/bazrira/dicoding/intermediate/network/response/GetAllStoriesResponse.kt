package id.bazrira.dicoding.intermediate.network.response

import com.google.gson.annotations.SerializedName
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Response.GetAllStories.LIST_STORY

data class GetAllStoriesResponse(
  @SerializedName(LIST_STORY) val listStory: List<StoryItemResponse>,
) : BaseResponse()