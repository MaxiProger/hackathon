package com.example.kolot.hackathon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by kolot on 25.11.2017.
 */


// строка запроса для access token https://oauth.vk.com/authorize?client_id=6272452&display=page&scope=3&response_type=token&v=5.69

public interface Api {

    @POST("method/friends.add?v=5.96")
    Call<ResponseApi> getResponse(
            @Query("user_id") int user_id,
            @Query("access_token") String access_token
    );

    @GET("api/notes/")
    Call<List<MeetAll>> getInfo();


    @POST("api/notes/")
    Call<MeetAll> registerUser( @Body RegistrationBody body);

    @GET("api/notes/{id}")
    Call<MeetallId> getIds (
            @Path("id") int id
    );

}
