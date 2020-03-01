package rest;

import models.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestAPI {

    @GET("/search")
    Call<Result> getAllTracks(@Query("term") String track);

}