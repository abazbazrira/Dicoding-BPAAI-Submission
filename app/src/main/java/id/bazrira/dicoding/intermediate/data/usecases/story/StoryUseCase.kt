package id.bazrira.dicoding.intermediate.data.usecases.story

import id.bazrira.dicoding.intermediate.data.model.StoryModel
import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import io.reactivex.Observable

interface StoryUseCase {

  fun addNewStory(bodyRequest: AddNewStoryRequest): Observable<Boolean>
  fun getAllStories(bodyRequest: GetAllStoriesRequest): Observable<List<StoryModel>>
  fun getDetailStory(bodyRequest: GetDetailStoryRequest): Observable<StoryModel>
}