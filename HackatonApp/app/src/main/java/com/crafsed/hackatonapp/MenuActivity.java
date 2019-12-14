package com.crafsed.hackatonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileOutputStream;

public class MenuActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        button = (Button)findViewById((R.id.btnMakePic));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MenuActivity.this, CameraActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
            }
        });
    }


}
