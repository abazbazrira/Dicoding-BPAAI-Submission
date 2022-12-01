package id.bazrira.dicoding.intermediate.network.request

import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.GetAllStories.LOCATION
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.GetAllStories.PAGE
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.GetAllStories.SIZE

data class GetAllStoriesRequest(
  private val page: String = "",
  private val size: String = "",
  private val location: String = "",
) {
  fun partMap(): Map<String, String> {
    val value = hashMapOf(
      PAGE to page,
      SIZE to size,
      LOCATION to location
    )

    value.values.removeIf { it.isNullOrEmpty() }

    return value
  }
}