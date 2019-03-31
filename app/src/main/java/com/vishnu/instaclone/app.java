package com.vishnu.instaclone;

import android.app.Application;

import com.parse.Parse;

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("tDbgX6FFsixQRbOYywEi5YvgwgfMXvfUeuLqAP7i")
                // if defined
                .clientKey("jrLWxiKqptSTYpkVzfhBKjPACToob9Ex82OdqHFs")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
