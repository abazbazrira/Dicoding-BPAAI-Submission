package id.bazrira.dicoding.intermediate.features.authentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.bazrira.dicoding.intermediate.abstraction.base.BaseViewModel
import id.bazrira.dicoding.intermediate.abstraction.rx.SchedulerProvider
import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.utils.NetworkUtils
import id.bazrira.dicoding.intermediate.abstraction.utils.NetworkUtils.isHttpCodeError
import id.bazrira.dicoding.intermediate.data.usecases.login.AuthUseCase
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import id.bazrira.dicoding.intermediate.network.response.BaseResponse
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
  private val useCase: AuthUseCase,
  private val schedulerProvider: SchedulerProvider,
) : BaseViewModel() {

  private val _state = MutableLiveData<Resource<Boolean>>()
  val state: LiveData<Resource<Boolean>>
    get() = _state

  fun register(bodyRequest: RegisterRequest) {
    subscribe(
      useCase.register(bodyRequest)
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
        .doOnSubscribe { _state.postValue(Resource.Loading()) }
        .subscribe({
          _state.postValue(Resource.Success(it))
        }, {
          if (isHttpCodeError(it)) {
            val errorMessage =
              NetworkUtils.getErrorMessage(
                it,
                BaseResponse::class.java
              )?.message.toString()

            _state.postValue(Resource.Error(errorMessage))
          } else _state.postValue(Resource.Error(it.localizedMessage))
        })
    )
  }
}