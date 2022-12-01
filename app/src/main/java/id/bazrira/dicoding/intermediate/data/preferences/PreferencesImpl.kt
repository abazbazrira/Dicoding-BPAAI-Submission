package id.bazrira.dicoding.intermediate.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesImpl(private val context: Context) : Preferences {

  private val mainSharePref: SharedPreferences by lazy {
    context.getSharedPreferences(
      SHARED_PREF, Context.MODE_PRIVATE
    )
  }

  companion object {
    const val SHARED_PREF = "mainSharedPref"
    const val IS_LOGGED_IN = "isLoggedIn"
    const val TOKEN = "token"
    const val USER_ID = "userId"
    const val NAME = "name"

    @Volatile
    private var instance: PreferencesImpl? = null

    fun getInstance(context: Context): PreferencesImpl =
      instance ?: synchronized(this) {
        instance ?: PreferencesImpl(context)
      }

  }

  override fun saveData(key: String, value: Any) {
    mainSharePref.edit {
      when (value) {
        is Int -> putInt(key, value)
        is String -> putString(key, value)
        is Long -> putLong(key, value)
        is Float -> putFloat(key, value)
        is Boolean -> putBoolean(key, value)
        else -> return
      }
    }
  }

  override fun getIsLoggedIn(): Boolean {
    return mainSharePref.getBoolean(IS_LOGGED_IN, false)
  }

  override fun getToken(): String {
    return mainSharePref.getString(TOKEN, "") ?: ""
  }

  override fun getUserId(): String {
    return mainSharePref.getString(USER_ID, "") ?: ""
  }
}