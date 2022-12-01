package id.bazrira.dicoding.intermediate.abstraction.constants

object Api {
  object Url {
    const val STORY_API_URL = "https://story-api.dicoding.dev/v1/"
  }

  object Endpoint {
    object Authentication {
      const val REGISTER = "register"
      const val LOGIN = "login"
    }

    object Story {
      const val GET_ALL_STORIES = "stories"
      const val ADD_NEW_STORY = "stories"
      const val GET_DETAIL_STORY = "stories/{id}"
    }
  }

  object Headers {
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "
  }

  object Body {
    object Request {
      object Register {
        const val NAME = "name"
        const val EMAIL = "email"
        const val PASSWORD = "password"
      }

      object Login {
        const val EMAIL = "email"
        const val PASSWORD = "password"
      }

      object AddNewStory {
        const val DESCRIPTION = "description"
        const val PHOTO = "photo"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
      }

      object GetAllStories {
        const val PAGE = "page"
        const val SIZE = "size"
        const val LOCATION = "location"
      }

      object GetDetailStory {
        const val ID = "id"
      }
    }

    object Response {
      object Base {
        const val ERROR = "error"
        const val MESSAGE = "message"
      }

      object Login {
        const val LOGIN_RESULT = "loginResult"

        const val USER_ID = "userId"
        const val NAME = "name"
        const val TOKEN = "token"
      }

      object GetAllStories {
        const val LIST_STORY = "listStory"
      }

      object DetailStory {
        const val STORY = "story"
      }

      object StoryItem {
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val PHOTO_URL = "photoUrl"
        const val CREATED_AT = "createdAt"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
      }
    }
  }
}
