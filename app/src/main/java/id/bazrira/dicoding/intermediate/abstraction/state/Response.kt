package id.bazrira.dicoding.intermediate.abstraction.state

sealed class Response<out R>{
    data class Success<out T>(val data : T): Response<T>()
    data class Error(val message : String): Response<Nothing>()
    object Empty : Response<Nothing>()
}
