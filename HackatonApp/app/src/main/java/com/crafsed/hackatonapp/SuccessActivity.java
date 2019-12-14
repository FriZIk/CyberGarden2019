package com.crafsed.hackatonapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class SuccessActivity extends AppCompatActivity {
    private Bitmap bitmap;

    private ImageView imgv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_treatment);

//        byte[] byteArray = getIntent().getByteArrayExtra("PICTURE");
        byte[] byteArray = Logic.image;
        bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
        imgv = findViewById(R.id.imageView);
        imgv.setImageBitmap(bitmap);
        imgv.animate().rotation(90);
    }


    public void returnToMainMenu(View view) {
        Intent intent=new Intent(SuccessActivity.this, MenuActivity.class);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
    }
}
