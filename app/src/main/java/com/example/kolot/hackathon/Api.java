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
    Call<List<MeetallId>> getInfo();


    @POST("api/notes/")
    Call<MeetallId> registerUser( @Body RegistrationBody body);

    @GET("api/notes/{id}")
    Call<MeetallId> getIds (
            @Path("id") int id
    );

    @GET ("/oauth/v2/authorization?response_type=code&client_id=863v2jm0276p45&redirect_uri=http://meetall.herokuapp.com/api/notes&state=987654321&scope=r_basicprofile")
    Call<String> getTestLinkedIn ();

   // @POST("authorization_code&code=AQQKYaYDpPE2lfRKuz5Lns4Py9Z0injKexPgksENGuhb2icI-_QUljeYv3pQmJiHFdXSN1f-hm1KFYEA5hcPODN7U6X0gCIKyPV4_HRekLPecBqxlwYik4xPk3QqA7_yySRb31_nzSIcI0yOZ7ogQKKfFt6nlg&redirect_uri=http://meetall.herokuapp.com/api/notes&client_id=863v2jm0276p45&client_secret=oRqCvJnOOJ8A29iJ")
}
