package id.bazrira.dicoding.intermediate.features.story.formstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bazrira.dicoding.intermediate.abstraction.base.BaseViewModel
import id.bazrira.dicoding.intermediate.abstraction.rx.SchedulerProvider
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.NetworkUtils
import id.bazrira.dicoding.intermediate.data.model.StoryModel
import id.bazrira.dicoding.intermediate.data.usecases.story.StoryUseCase
import id.bazrira.dicoding.intermediate.network.request.AddNewStoryRequest
import id.bazrira.dicoding.intermediate.network.request.GetDetailStoryRequest
import id.bazrira.dicoding.intermediate.network.response.BaseResponse
import javax.inject.Inject

class FormStoryViewModel @Inject constructor(
  private val useCase: StoryUseCase,
  private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

  private val _stateSubmit = MutableLiveData<Resource<Boolean>>()
  val stateSubmit: LiveData<Resource<Boolean>>
    get() = _stateSubmit

  private val _stateDetail = MutableLiveData<Resource<StoryModel>>()
  val stateDetail: LiveData<Resource<StoryModel>>
    get() = _stateDetail

  fun addStory(bodyRequest: AddNewStoryRequest) {
    subscribe(
      useCase.addNewStory(bodyRequest)
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .doOnSubscribe { _stateSubmit.postValue(Resource.Loading()) }
        .subscribe({
          _stateSubmit.postValue(Resource.Success(it))
        }, {
          if (NetworkUtils.isHttpCodeError(it)) {
            val errorMessage =
              NetworkUtils.getErrorMessage(
                it,
                BaseResponse::class.java
              )?.message.toString()

            _stateSubmit.postValue(Resource.Error(errorMessage))
          } else _stateSubmit.postValue(Resource.Error(it.localizedMessage))
        })
    )
  }

  fun detailStory(bodyRequest: GetDetailStoryRequest) {
    subscribe(
      useCase.getDetailStory(bodyRequest)
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .doOnSubscribe { _stateDetail.postValue(Resource.Loading()) }
        .subscribe({
          _stateDetail.postValue(Resource.Success(it))
        }, {
          if (NetworkUtils.isHttpCodeError(it)) {
            val errorMessage =
              NetworkUtils.getErrorMessage(
                it,
                BaseResponse::class.java
              )?.message.toString()

            _stateDetail.postValue(Resource.Error(errorMessage))
          } else _stateDetail.postValue(Resource.Error(it.localizedMessage))
        })
    )
  }
}