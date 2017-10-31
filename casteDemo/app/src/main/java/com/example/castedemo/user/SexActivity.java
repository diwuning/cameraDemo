package com.example.castedemo.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;
import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SexActivity extends Activity {

    @BindView(R.id.wheel_sex)
    WheelPicker wheelSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.btn_sexCancel)
    Button btnSexCancel;
    @BindView(R.id.btn_sexSave)
    Button btnSexSave;
    List<String> sexList = new ArrayList<String>();
    String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        ButterKnife.bind(this);
        initSexWheel();
        if(getIntent().getStringExtra("sex") != null && !getIntent().getStringExtra("sex").equals("")){
            sex = getIntent().getStringExtra("sex");
            if(sex.equals("男")){
                wheelSex.setSelectedItemPosition(0);
            }else {
                wheelSex.setSelectedItemPosition(1);
            }

        }
    }

    public void initSexWheel(){
        sexList.add("男");
        sexList.add("女");
        wheelSex.setData(sexList);
    }

    @OnClick({R.id.btn_sexCancel, R.id.btn_sexSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sexCancel:
                finish();
                break;
            case R.id.btn_sexSave:
                sex = sexList.get(wheelSex.getCurrentItemPosition());
                Intent sexIntent = new Intent(SexActivity.this, UserInfo1Activity.class);
                sexIntent.putExtra("sex",sex);
                setResult(RESULT_OK,sexIntent);
                finish();
                break;
        }
    }
}
