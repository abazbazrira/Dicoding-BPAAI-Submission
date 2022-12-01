package id.bazrira.dicoding.intermediate.data.repository.story

import id.bazrira.dicoding.intermediate.data.model.StoryModel
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.repository.story.source.remote.StoryRemoteDataSource
import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import io.reactivex.Observable
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
  private val remoteDataSource: StoryRemoteDataSource,
  private val preferences: Preferences,
) : StoryRepository {

  override fun addNewStory(bodyRequest: AddNewStoryRequest): Observable<Boolean> {
    return remoteDataSource.addNewStory(bodyRequest, preferences.getToken()).map {
      !it.error
    }
  }

  override fun getAllStories(bodyRequest: GetAllStoriesRequest): Observable<List<StoryModel>> {
    return remoteDataSource.getAllStories(bodyRequest, preferences.getToken()).map {
      if (it.error) listOf()
      else {
        it.listStory.map { data ->
          StoryModel(
            id = data.id.toString(),
            name = data.name.toString(),
            description = data.description.toString(),
            photoUrl = data.photoUrl.toString(),
            createdAt = data.createdAt.toString(),
            longitude = data.longitude ?: 0f,
            latitude = data.latitude ?: 0f,
          )
        }
      }
    }
  }

  override fun getDetailStory(bodyRequest: GetDetailStoryRequest): Observable<StoryModel> {
    return remoteDataSource.getDetailStory(bodyRequest, preferences.getToken()).map {
      if (it.error) StoryModel()
      else {
        StoryModel(
          id = it.story.id.toString(),
          name = it.story.name.toString(),
          description = it.story.description.toString(),
          createdAt = it.story.createdAt.toString(),
          photoUrl = it.story.photoUrl.toString(),
          latitude = it.story.latitude ?: 0f,
          longitude = it.story.longitude ?: 0f,
        )
      }
    }
  }
}