package com.example.castedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.castedemo.Avatar.AvaterActivity;
import com.example.castedemo.camera.UserInfo1Activity;
import com.example.castedemo.family.FamilyListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_userPic)
    Button btnUserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_camera, R.id.btn_userPic})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                Intent intent = new Intent(MainActivity.this, UserInfo1Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_userPic:
                Intent picIntent = new Intent(MainActivity.this, FamilyListActivity.class);
                startActivity(picIntent);
                break;
        }
    }
}
