package id.bazrira.dicoding.intermediate.features.story.liststory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bazrira.dicoding.intermediate.abstraction.base.BaseViewModel
import id.bazrira.dicoding.intermediate.abstraction.rx.SchedulerProvider
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.NetworkUtils
import id.bazrira.dicoding.intermediate.data.model.StoryModel
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.preferences.PreferencesImpl.Companion.IS_LOGGED_IN
import id.bazrira.dicoding.intermediate.data.preferences.PreferencesImpl.Companion.TOKEN
import id.bazrira.dicoding.intermediate.data.usecases.story.StoryUseCase
import id.bazrira.dicoding.intermediate.network.request.GetAllStoriesRequest
import id.bazrira.dicoding.intermediate.network.response.BaseResponse
import retrofit2.HttpException
import javax.inject.Inject

class ListStoryViewModel @Inject constructor(
  private val useCase: StoryUseCase,
  private val schedulerProvider: SchedulerProvider,
  private val preferences: Preferences,
) : BaseViewModel() {

  private val _state = MutableLiveData<Resource<List<StoryModel>>>()
  val state: LiveData<Resource<List<StoryModel>>>
    get() = _state

  private val _isAuthenticated = MutableLiveData<Boolean>().apply { value = true }
  val isAuthenticated: LiveData<Boolean>
    get() = _isAuthenticated

  fun getAllStories(bodyRequest: GetAllStoriesRequest) {
    subscribe(
      useCase.getAllStories(bodyRequest)
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .doOnSubscribe { _state.postValue(Resource.Loading()) }
        .subscribe({
          _state.postValue(Resource.Success(it))
        }, {
          if (NetworkUtils.isHttpCodeError(it)) {
            val errorMessage =
              NetworkUtils.getErrorMessage(
                it,
                BaseResponse::class.java
              )?.message.toString()

            _state.postValue(Resource.Error(errorMessage))

            if ((it as HttpException).code() == 401) {
              _isAuthenticated.postValue(false)
            }
          } else _state.postValue(Resource.Error(it.localizedMessage))
        })
    )
  }

  fun clearUserData() {
    preferences.saveData(IS_LOGGED_IN, false)
    preferences.saveData(TOKEN, "")
  }
}