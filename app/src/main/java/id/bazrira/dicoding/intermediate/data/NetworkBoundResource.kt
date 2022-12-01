package id.bazrira.dicoding.intermediate.data

import id.bazrira.dicoding.intermediate.abstraction.state.Resource
import id.bazrira.dicoding.intermediate.abstraction.state.Response
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

  private var result: Flow<Resource<ResultType>> = flow {
    emit(Resource.Loading())
    val dbSource = loadFromDb().first()
    if (shouldFetch(dbSource)) {
      emit(Resource.Loading())
      when (val apiResponse = createCall().first()) {
        is Response.Success -> {
          saveCallResult(apiResponse.data)
          emitAll(loadFromDb().map {
            Resource.Success(
              it
            )
          })
        }
        is Response.Empty -> {
          emitAll(loadFromDb().map {
            Resource.Success(
              it
            )
          })
        }
        is Response.Error -> {
          onFetchFailed()
          emit(
            Resource.Error<ResultType>(
              apiResponse.message
            )
          )
        }
      }
    } else {
      emitAll(loadFromDb().map {
        Resource.Success(it)
      })
    }
  }

  protected open fun onFetchFailed() {}

  protected abstract fun loadFromDb(): Flow<ResultType>

  protected abstract fun shouldFetch(data: ResultType?): Boolean

  protected abstract suspend fun createCall(): Flow<Response<RequestType>>

  protected abstract suspend fun saveCallResult(data: RequestType)

  fun asFlow(): Flow<Resource<ResultType>> = result
}