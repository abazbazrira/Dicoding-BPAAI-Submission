package id.bazrira.dicoding.intermediate.data.preferences

interface Preferences {

  fun saveData(key: String, value: Any)

  fun getIsLoggedIn(): Boolean
  fun getToken(): String
  fun getUserId(): String
}