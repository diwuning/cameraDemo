package com.example.castedemo.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;
import com.itheima.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 设置身高或体重
* */
public class WeightActivity extends Activity {
    private static final String TAG = "WeightActivity";

    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.wheel_weight)
    WheelPicker wheelWeight;
    @BindView(R.id.tv_weightLabel)
    TextView tvWeightLabel;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.view_bottom)
    View viewBottom;
    @BindView(R.id.btn_weightCancel)
    Button btnWeightCancel;
    @BindView(R.id.btn_weightSave)
    Button btnWeightSave;
    Context mContext;
    String from = "";
    List<Integer> weightData = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        ButterKnife.bind(this);
        mContext = WeightActivity.this;
        if(getIntent().getStringExtra("from")!= null && !getIntent().getStringExtra("from").equals("")){
            from = getIntent().getStringExtra("from");
        }
        if(getIntent().getStringExtra("height")!= null && !getIntent().getStringExtra("height").equals("")){
            float height = Float.valueOf(getIntent().getStringExtra("height"));
            wheelWeight.setSelectedItemPosition((int) height-80);
        }

        if(getIntent().getStringExtra("weight")!= null && !getIntent().getStringExtra("weight").equals("")){
            float weight = Float.valueOf(getIntent().getStringExtra("weight"));
            wheelWeight.setSelectedItemPosition((int) weight-20);
        }
        initView();
    }

    public void initView(){
        if(from.equals("weight")){
            for(int i=20;i<=200;i++){
                weightData.add(i);
            }
            wheelWeight.setData(weightData);
        }else{
            tvWeight.setText("选择身高");
            tvWeightLabel.setText("CM");
            for(int i=80;i<260;i++){
                weightData.add(i);
            }
            wheelWeight.setData(weightData);
        }
    }

    @OnClick({R.id.btn_weightCancel, R.id.btn_weightSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_weightCancel:
                finish();
                break;
            case R.id.btn_weightSave:
                if(from.equals("weight")){
                    int weight = wheelWeight.getCurrentItemPosition()+20;
                    Intent weightIntent = new Intent();
                    weightIntent.putExtra("weight",weight);
                    setResult(RESULT_OK,weightIntent);
                }else{
                    int height = wheelWeight.getCurrentItemPosition()+80;
                    Intent weightIntent = new Intent();
                    weightIntent.putExtra("height",height);
                    setResult(RESULT_OK,weightIntent);
                }
                finish();
                break;
        }
    }
}
