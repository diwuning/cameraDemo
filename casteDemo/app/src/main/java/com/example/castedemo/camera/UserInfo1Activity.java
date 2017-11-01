package com.example.castedemo.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.castedemo.Avatar.AvaterActivity;
import com.example.castedemo.Avatar.adapter.SystemAvaterAdapter;
import com.example.castedemo.Avatar.bean.MediaBean;
import com.example.castedemo.R;
import com.example.castedemo.family.adapter.TagAdapter;
import com.example.castedemo.user.BindPhoneActivity;
import com.example.castedemo.user.BirthdayActivity;
import com.example.castedemo.user.NickNameActivity;
import com.example.castedemo.user.SexActivity;
import com.example.castedemo.user.UserTagActivity;
import com.example.castedemo.user.WeightActivity;
import com.example.castedemo.view.CircleImageView;
import com.example.sugardemo.UserInfo;
import com.example.sugardemo.UserInfoDao;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 新建家庭成员
* */
public class UserInfo1Activity extends Activity {
    private static final String TAG = "UserInfo1Activity";
    @BindView(R.id.gv_family)
    GridView gvFamily;
    //头像
//    @BindView(R.id.iv_userPic)
//    ImageView ivUserPic;
    @BindView(R.id.ll_userPic)
    LinearLayout llUserPic;
    @BindView(R.id.ci_userPic)
    CircleImageView circleImageView;
    //昵称
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.ll_nickName)
    LinearLayout llNickName;
    //性别
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    //生日
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    //身高
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    //体重
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    //偏好标签
    @BindView(R.id.tv_userTag)
    TextView tvUserTag;
    @BindView(R.id.ll_userTag)
    LinearLayout llUserTag;
    //手机号
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    //
    @BindView(R.id.tv_faceid)
    TextView tvFaceid;
    @BindView(R.id.ll_faceId)
    LinearLayout llFaceId;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_save)
    Button btnSave;
    UserInfoDao userInfoDao;
    UserInfo passInfo;
    Context mContext;
    String userId;
    @BindView(R.id.gv_tag)
    GridView gvTag;
    @BindView(R.id.hsv_tag)
    HorizontalScrollView hsvTag;
    String[] tags;
    String receiveTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        mContext = UserInfo1Activity.this;
        userInfoDao = new UserInfoDao();
        getSystemAvater();
    }

    /*
    * 获取系统头像
    * */
    public void getSystemAvater() {
//        String[] systemAvater = new String[0];
        final List<MediaBean> systemAvaters = new ArrayList<MediaBean>();
        try {
            String[] systemAvater = getResources().getAssets().list("pictures");
            String[] avaterName = {"爸爸", "妈妈", "儿子", "女儿", "其他"};
            String[] sexArr = {"男","女","男","女","男"};
            for (int i = 0; i < systemAvater.length; i++) {
                systemAvater[i] = "assets://pictures/" + systemAvater[i];
                Log.e(TAG, "system=" + systemAvater[i]);
                MediaBean bean = new MediaBean();
                bean.setDisplayName(avaterName[i]);
                bean.setPath(systemAvater[i]);
                bean.setSex(sexArr[i]);
                systemAvaters.add(bean);
            }
            SystemAvaterAdapter adapter = new SystemAvaterAdapter(mContext, systemAvaters);
            int size = systemAvater.length;
            gvFamily.setNumColumns(size);
            int width = 210;
            int gridWidth = size * (width + 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, ViewGroup.LayoutParams.FILL_PARENT);
            gvFamily.setLayoutParams(params);
            gvFamily.setColumnWidth(210);
            gvFamily.setHorizontalSpacing(10);
            gvFamily.setAdapter(adapter);
            gvFamily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MediaBean bean = systemAvaters.get(position);
                    ImageLoader.getInstance().displayImage(bean.getPath(),circleImageView);
                    imgPath = bean.getPath();
                    tvNickName.setText(bean.getDisplayName());
                    tvSex.setText(bean.getSex());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.ll_userPic, R.id.ll_nickName, R.id.ll_birthday, R.id.ll_height, R.id.ll_weight,
            R.id.ll_userTag, R.id.ll_phone, R.id.ll_faceId, R.id.btn_cancel, R.id.btn_save, R.id.ll_sex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_userPic:
                Intent picIntent = new Intent(mContext, AvaterActivity.class);
//                startActivityForResult(picIntent,11);
                picIntent.putExtra("status","add");
                startActivity(picIntent);
                break;
            case R.id.ll_nickName:
                Intent nickIntent = new Intent(mContext, NickNameActivity.class);
                startActivityForResult(nickIntent, 1001);
                break;
            case R.id.ll_sex:
                Intent sexIntent = new Intent(mContext, SexActivity.class);
                startActivityForResult(sexIntent, 1003);
                break;
            case R.id.ll_birthday:
                Intent birIntent = new Intent(mContext, BirthdayActivity.class);
                startActivityForResult(birIntent,1002);
                break;
            case R.id.ll_height:
                Intent heightIntent = new Intent(mContext, WeightActivity.class);
                heightIntent.putExtra("from", "height");
                startActivityForResult(heightIntent, 1004);
                break;
            case R.id.ll_weight:
                Intent weightIntent = new Intent(mContext, WeightActivity.class);
                weightIntent.putExtra("from", "weight");
                startActivityForResult(weightIntent, 1005);
                break;
            case R.id.ll_userTag:
                Intent tagIntent = new Intent(mContext, UserTagActivity.class);
                tagIntent.putExtra("from", "weight");
                startActivityForResult(tagIntent, 1006);
                break;
            case R.id.ll_phone:
                Intent phoneIntent = new Intent(mContext, BindPhoneActivity.class);
                phoneIntent.putExtra("from", "weight");
                startActivityForResult(phoneIntent, 1007);
                break;
            case R.id.ll_faceId:
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_save:
                UserInfo userInfo = new UserInfo();
                if (!tvNickName.getText().toString().trim().equals("")) {
                    userInfo.setUserName(tvNickName.getText().toString());
                }else {
                    Toast.makeText(mContext,"昵称不为能空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!tvSex.getText().toString().trim().equals("")) {
                    userInfo.setSex(tvSex.getText().toString());
                }
//                userInfo.setBirthday(tvBirthday.getText().toString());
                if (!imgPath.equals("")) {
                    userInfo.setUserImg(imgPath);
                }
                if (!tvBirthday.getText().toString().trim().equals("")) {
                    userInfo.setBirthday(tvBirthday.getText().toString());
                }
                if (!tvWeight.getText().toString().trim().equals("")) {
                    userInfo.setWeight(weight);
                }
                if (!tvHeight.getText().toString().trim().equals("")) {
                    userInfo.setHeight(height);
                }
                if (!tvPhone.getText().toString().trim().equals("")) {
                    userInfo.setPhone(tvPhone.getText().toString());
                }
                if(receiveTags != null){
                    userInfo.setTags(receiveTags);
                }
//                userInfo.setFaceId(true);//测试
                userInfoDao.addUser(userInfo);
                finish();
                break;
        }
    }
    int weight=0,height=0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:
                    Log.e(TAG, "1001");
                    String nickName = data.getStringExtra("nickName");
                    tvNickName.setText(nickName);
                    break;
                case 1002:
                    String birth = data.getStringExtra("birthday");
                    tvBirthday.setText(birth);
                    break;
                case 1003:
                    String sex = data.getStringExtra("sex");
                    tvSex.setText(sex);
                    break;
                case 1004://身高
                    height = data.getIntExtra("height", 0);
                    tvHeight.setText(height + "CM");
                    break;
                case 1005://体重
                    weight = data.getIntExtra("weight", 0);
                    tvWeight.setText(weight + "KG");
                    break;
                case 1006://偏好标签
//                    tags = data.getStringArrayExtra("tag");
                    receiveTags = data.getStringExtra("tag");

                    if(receiveTags != null && !receiveTags.equals(""))/*(tags != null && tags.length != 0)*/{
                        String[] tags = receiveTags.split(",");
                        TagAdapter tagAdapter = new TagAdapter(mContext, tags);
                        int size = tags.length;
                        gvTag.setNumColumns(size);
                        int width = 210;
                        int gridWidth = size * (width + 10);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, ViewGroup.LayoutParams.FILL_PARENT);
                        gvTag.setLayoutParams(params);
                        gvTag.setColumnWidth(210);
                        gvTag.setHorizontalSpacing(10);
                        gvTag.setAdapter(tagAdapter);
                        hsvTag.setVisibility(View.VISIBLE);
                    }else {
                        hsvTag.setVisibility(View.GONE);
                    }

                    break;
                case 1007://手机号
                    String phone = data.getStringExtra("phone");
                    tvPhone.setText(phone);
                    break;
            }
        }
    }

    String imgPath = "";

    //    CropperImage cropperImage;
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        imgPath = intent.getStringExtra("path");
        Log.e(TAG, "onNewIntent ====uri=" + getIntent().getData() + ",path=" + imgPath);
        String from = intent.getStringExtra("from");
        if (from.equals("crop")) {
//            ImageLoader.getInstance().displayImage("file://"+imgPath,ivUserPic);
            ImageLoader.getInstance().displayImage("file://" + imgPath, circleImageView);
        } else if (from.equals("system")) {
//            ImageLoader.getInstance().displayImage(imgPath,ivUserPic);
            ImageLoader.getInstance().displayImage(imgPath, circleImageView);
        }
    }
}
