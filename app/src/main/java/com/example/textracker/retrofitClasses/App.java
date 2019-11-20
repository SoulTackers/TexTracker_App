package com.example.textracker.retrofitClasses;

import android.app.Application;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private Session session;
    private APIClient apiService;
    private AuthenticationListener authenticationListener;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // use a storage option to store the
    // credentails and user info
    // i.e: SQLite, SharedPreference etc.
    public Session getSession() {
        if (session == null) {
            session = new Session() {
                @Override
                public boolean isLoggedIn() {
                    // check if token exist or not
                    // return true if exist otherwise false
                    // assuming that token exists
                    return true;
                }

                @Override
                public void saveToken(String token) {
                    // save the token
                }

                @Override
                public String getToken() {
                    // return the token that was saved earlier
                    return "token";
                }

               // @Override
                public void saveEmail() {
                    // save user's email
                }

               // @Override
                public String getEmail() {
                    return "email";
                }

                //@Override
                public void savePassword() {
                    // encrypt and save
                }

                @Override
                public String getPassword() {
                    // decrypt and return
                    return "password";
                }

                @Override
                public void invalidate() {
                    // get called when user become logged out
                    // delete token and other user info
                    // (i.e: email, password)
                    // from the storage

                    // sending logged out event to it's listener
                    // i.e: Activity, Fragment, Service
                    if (authenticationListener != null) {
                        authenticationListener.onUserLoggedOut();
                    }
                }
            };
        }

        return session;
    }

    public interface AuthenticationListener {
        void onUserLoggedOut();
    }

    public void setAuthenticationListener(AuthenticationListener listener) {
        this.authenticationListener = listener;
    }

    public APIClient getApiService() {
        if (apiService == null) {
            apiService = provideRetrofit(APIClient.BASE_URL).create(APIClient.class);
        }
        return apiService;
    }

    private Retrofit provideRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        return okhttpClientBuilder.build();
    }
}