package com.example.kolot.hackathon;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kolot on 25.11.2017.
 */

public interface Api {

@POST("method/friends.add?access_token=64bcb45c0601f80d859f874dde08b312f1449e23d5ee70743487d81e9585b6ee99ad606673f68ad1799c9")
    Call<ResponseApi> getResponse(
            @Query("user_id") int user_id
);
}
