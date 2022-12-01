package id.bazrira.dicoding.intermediate.network

import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.AddNewStory.DESCRIPTION
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.AddNewStory.LATITUDE
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.AddNewStory.LONGITUDE
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Body.Request.GetDetailStory.ID
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Endpoint.Authentication.LOGIN
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Endpoint.Authentication.REGISTER
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Endpoint.Story.ADD_NEW_STORY
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Endpoint.Story.GET_ALL_STORIES
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Endpoint.Story.GET_DETAIL_STORY
import id.bazrira.dicoding.intermediate.abstraction.constants.Api.Headers.AUTHORIZATION
import id.bazrira.dicoding.intermediate.network.request.LoginRequest
import id.bazrira.dicoding.intermediate.network.request.RegisterRequest
import id.bazrira.dicoding.intermediate.network.response.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface Services {

  @POST(REGISTER)
  fun register(
    @Body bodyRequest: RegisterRequest,
  ): Observable<RegisterResponse>

  @POST(LOGIN)
  fun login(
    @Body bodyRequest: LoginRequest,
  ): Observable<LoginResponse>

  @Multipart
  @POST(ADD_NEW_STORY)
  fun addNewStory(
    @Part(DESCRIPTION) description: RequestBody,
    @Part(LONGITUDE) longitude: RequestBody,
    @Part(LATITUDE) latitude: RequestBody,
    @Part file: MultipartBody.Part,
    @Header(AUTHORIZATION) token: String,
  ): Observable<AddNewStoryResponse>

  @GET(GET_ALL_STORIES)
  fun getAllStories(
    @QueryMap bodyRequest: Map<String, String>,
    @Header(AUTHORIZATION) token: String,
  ): Observable<GetAllStoriesResponse>

  @GET(GET_DETAIL_STORY)
  fun getDetailStory(
    @Path(ID) id: String,
    @Header(AUTHORIZATION) token: String,
  ): Observable<GetDetailStoryResponse>
}