package com.example.castedemo.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NickNameActivity extends Activity {
    private static final String TAG = "NickNameActivity";

    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.et_nick)
    EditText etNick;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save)
    Button btnSave;
    String nickName = "";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        ButterKnife.bind(this);
        mContext = NickNameActivity.this;
        if(getIntent().getStringExtra("nickName") != null && !getIntent().getStringExtra("nickName").equals("")){
            nickName = getIntent().getStringExtra("nickName");
            etNick.setText(nickName);
        }

    }

    @OnClick({R.id.btn_cancel, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_save:
                if(!etNick.getText().toString().trim().equals("")){
                    nickName = etNick.getText().toString();
                }
                Intent userIntent = new Intent(mContext, UserInfo1Activity.class);
                userIntent.putExtra("nickName",nickName);
//                userIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                setResult(RESULT_OK,userIntent);
                finish();
                break;
        }
    }

    /*
    * 点击屏幕其他地方使软键盘消失
    * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //隐藏软键盘
            if ((null != getCurrentFocus()) && (null != getCurrentFocus().getWindowToken())) {
                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);

    }
}
