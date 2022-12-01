package id.bazrira.dicoding.intermediate.data.usecases.story

import id.bazrira.dicoding.intermediate.data.model.StoryModel
import id.bazrira.dicoding.intermediate.data.repository.story.StoryRepository
import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import io.reactivex.Observable
import javax.inject.Inject

class StoryUseCaseImpl @Inject constructor(
  private val repository: StoryRepository,
) : StoryUseCase {

  override fun addNewStory(bodyRequest: AddNewStoryRequest): Observable<Boolean> {
    return repository.addNewStory(bodyRequest = bodyRequest)
  }

  override fun getAllStories(bodyRequest: GetAllStoriesRequest): Observable<List<StoryModel>> {
    return repository.getAllStories(bodyRequest = bodyRequest)
  }

  override fun getDetailStory(bodyRequest: GetDetailStoryRequest): Observable<StoryModel> {
    return repository.getDetailStory(bodyRequest = bodyRequest)
  }
}