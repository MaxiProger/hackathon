package com.example.kolot.hackathon;

import com.vk.sdk.VKSdk;

/**
 * Created by kolot on 25.11.2017.
 */

public class Applcation extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(this);

    }
}
