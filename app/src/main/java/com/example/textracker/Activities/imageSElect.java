package com.example.textracker.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.example.textracker.models.Login;
import com.example.textracker.models.LoginResponse;
import com.example.textracker.models.UploadDocResponse;
import com.example.textracker.retrofitClasses.APIClient;
import com.example.textracker.retrofitClasses.APIInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.content.CursorLoader;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.textracker.R;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class imageSElect extends AppCompatActivity {

    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private APIInterface apiInterface;
    private String image_path;

    private Uri filePath;
    Uri selectedImage;

    private final int PICK_IMAGE_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.imgView);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uploadImage();
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            selectedImage = data.getData();
            uploadImage(selectedImage, "My Image");

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //age_path = filePath;
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage( Uri fileuri, String desc) {

        File file = new File(getRealPathFromURI(fileuri));

        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileuri)), file);
        RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), desc);


       /* MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",file.getName().requestFile);

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), "image to upload");*/

/*
        Call<UploadDocResponse> call = apiInterface.uploadDoc(1, image);

        call.enqueue(new Callback<UploadDocResponse>() {
            @Override
            public void onResponse(Call<UploadDocResponse> call, Response<UploadDocResponse> response) {

            }

            @Override
            public void onFailure(Call<UploadDocResponse> call, Throwable t) {

            }
        });*/

    }

}
