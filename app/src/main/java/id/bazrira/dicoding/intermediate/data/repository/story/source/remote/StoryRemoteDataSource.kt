package id.bazrira.dicoding.intermediate.data.repository.story.source.remote

import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import id.bazrira.dicoding.intermediate.network.response.AddNewStoryResponse
import id.bazrira.dicoding.intermediate.network.response.GetAllStoriesResponse
import id.bazrira.dicoding.intermediate.network.response.GetDetailStoryResponse
import io.reactivex.Observable

interface StoryRemoteDataSource {

  fun addNewStory(bodyRequest: AddNewStoryRequest, token: String): Observable<AddNewStoryResponse>
  fun getAllStories(
    bodyRequest: GetAllStoriesRequest, token: String
  ): Observable<GetAllStoriesResponse>
  fun getDetailStory(bodyRequest: GetDetailStoryRequest, token: String): Observable<GetDetailStoryResponse>
}