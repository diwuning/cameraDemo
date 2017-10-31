package com.example.castedemo.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindPhoneActivity extends Activity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.rl_phone)
    LinearLayout rlPhone;
    @BindView(R.id.btn_phoneCancel)
    Button btnPhoneCancel;
    @BindView(R.id.btn_phoneSave)
    Button btnPhoneSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        if(getIntent().getStringExtra("phone") != null && !getIntent().getStringExtra("phone").equals("")){
            String phone = getIntent().getStringExtra("phone");
            etPhone.setText(phone);
        }
    }

    @OnClick({R.id.btn_phoneCancel, R.id.btn_phoneSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_phoneCancel:
                finish();
                break;
            case R.id.btn_phoneSave:
                if(!etPhone.getText().toString().trim().equals("")){
                    String phone = etPhone.getText().toString();
                    Intent userIntent = new Intent();
                    userIntent.putExtra("phone",phone);
                    setResult(RESULT_OK,userIntent);
                    finish();
                }
                break;
        }
    }
}
