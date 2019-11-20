package com.example.textracker.retrofitClasses;



import com.example.textracker.models.Login;
import com.example.textracker.models.LoginResponse;
import com.example.textracker.models.PendingInward;
import com.example.textracker.models.UploadDocResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface APIInterface {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> getLoggedIn(@Body Login login);

    @Headers("Content-Type: application/json")
    @GET("inward_pending_document_list")
    Call<List<PendingInward>> getData();

    @Headers("Content-Type: application/json")
    @Multipart
    @POST("myrecord")
    //Call<UploadDocResponse> uploadDoc(@Query("inward_id") int inward_id, @Part MultipartBody.Part file);

    Call<UploadDocResponse> uploadImage(@Part("image\"; filename=\"myfile.jpg\" ") RequestBody file, @Part("desc") RequestBody desc);
}

