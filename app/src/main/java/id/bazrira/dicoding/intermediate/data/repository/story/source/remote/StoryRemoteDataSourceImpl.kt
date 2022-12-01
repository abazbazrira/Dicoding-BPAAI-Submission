package id.bazrira.dicoding.intermediate.data.repository.story.source.remote

import id.bazrira.dicoding.intermediate.network.Services
import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import id.bazrira.dicoding.intermediate.network.response.AddNewStoryResponse
import id.bazrira.dicoding.intermediate.network.response.GetAllStoriesResponse
import id.bazrira.dicoding.intermediate.network.response.GetDetailStoryResponse
import io.reactivex.Observable
import javax.inject.Inject

class StoryRemoteDataSourceImpl @Inject constructor(
  private val services: Services,
) : StoryRemoteDataSource {

  override fun addNewStory(
    bodyRequest: AddNewStoryRequest,
    token: String
  ): Observable<AddNewStoryResponse> {
    return services.addNewStory(
      description = bodyRequest.description,
      longitude = bodyRequest.longitude,
      latitude = bodyRequest.latitude,
      file = bodyRequest.photo,
      token = token
    )
  }

  override fun getAllStories(
    bodyRequest: GetAllStoriesRequest,
    token: String
  ): Observable<GetAllStoriesResponse> {
    return services.getAllStories(bodyRequest = bodyRequest.partMap(), token = token)
  }

  override fun getDetailStory(
    bodyRequest: GetDetailStoryRequest,
    token: String
  ): Observable<GetDetailStoryResponse> {
    return services.getDetailStory(id = bodyRequest.id, token = token)
  }
}