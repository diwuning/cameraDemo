package com.example.castedemo.family;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.castedemo.Avatar.AvaterActivity;
import com.example.castedemo.R;
import com.example.castedemo.camera.UserInfo1Activity;
import com.example.castedemo.family.adapter.MemberAdapter;
import com.example.castedemo.family.adapter.TagAdapter;
import com.example.castedemo.user.BindPhoneActivity;
import com.example.castedemo.user.BirthdayActivity;
import com.example.castedemo.user.NickNameActivity;
import com.example.castedemo.user.SexActivity;
import com.example.castedemo.user.UserTagActivity;
import com.example.castedemo.user.WeightActivity;
import com.example.castedemo.utils.DialogHelper;
import com.example.castedemo.view.CircleImageView;
import com.example.sugardemo.UserInfo;
import com.example.sugardemo.UserInfoDao;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FamilyListActivity extends Activity {
    private static final String TAG = "FamilyListActivity";

    @BindView(R.id.tv_member)
    TextView tv_member;
    @BindView(R.id.gv_family)
    GridView gvFamily;
    @BindView(R.id.ci_userPic)
    CircleImageView ciUserPic;
    @BindView(R.id.ll_userPic)
    LinearLayout llUserPic;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.ll_nickName)
    LinearLayout llNickName;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.tv_userTag)
    TextView tvUserTag;
    @BindView(R.id.ll_userTag)
    LinearLayout llUserTag;
    @BindView(R.id.gv_tag)
    GridView gvTag;
    @BindView(R.id.hsv_tag)
    HorizontalScrollView hsv_tag;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_faceid)
    TextView tvFaceid;
    @BindView(R.id.switch_face)
    Switch switchFace;
    @BindView(R.id.ll_faceId)
    LinearLayout llFaceId;
    @BindView(R.id.btn_saveMember)
    Button btnSaveMember;
    @BindView(R.id.btn_delMember)
    Button btnDelMember;
    Context mContext;
    UserInfoDao userInfoDao;
    List<UserInfo> userInfos = new ArrayList<UserInfo>();
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.sv_family)
    ScrollView svFamily;
    @BindView(R.id.iv_addMember)
    ImageView ivAddMember;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_noMember)
    RelativeLayout rl_noMember;
    UserInfo userInfo;
    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);
        ButterKnife.bind(this);
        mContext = FamilyListActivity.this;
        userInfoDao = new UserInfoDao();
        initMembers();
        //faceId的操作
        switchFace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }else{

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initMembers();
    }

    /*
    * 获取已添加的成员列表
    * */
    public void initMembers() {
        userInfos = userInfoDao.getAllUsers();
        if (userInfos.size() != 0) {
            svFamily.setVisibility(View.VISIBLE);
            rl_noMember.setVisibility(View.GONE);
            MemberAdapter memberAdapter = new MemberAdapter(mContext, userInfos);
            int size = userInfos.size() + 1;
            gvFamily.setNumColumns(size);
            int width = 210;
            int gridWidth = size * (width + 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, ViewGroup.LayoutParams.FILL_PARENT);
            gvFamily.setLayoutParams(params);
            gvFamily.setColumnWidth(210);
            gvFamily.setHorizontalSpacing(10);
            gvFamily.setAdapter(memberAdapter);
            //点击一个头像，将该头像信息显示到下方各个选项中
            gvFamily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    userInfo = userInfos.get(position);
                    isUpdate = true;
                    setTextValue();
                }

            });
        }else{
            svFamily.setVisibility(View.GONE);
            rl_noMember.setVisibility(View.VISIBLE);
            ivAddMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addIntent = new Intent(mContext, UserInfo1Activity.class);
                    startActivity(addIntent);
                }
            });
        }
    }

    /*
    * 设置各个文本框的值
    * */
    public void setTextValue(){
        if(userInfo != null){
            if(userInfo.getUserImg() == null){
                ciUserPic.setImageResource(R.drawable.ic_launcher_round);
            }else if(userInfo.getUserImg().contains("assets:")) {
                ImageLoader.getInstance().displayImage(userInfo.getUserImg(), ciUserPic);
            }else {
                ImageLoader.getInstance().displayImage("file://"+userInfo.getUserImg(), ciUserPic);
            }

            if (userInfo.getUserName() != null && !userInfo.getUserName().equals("")) {
                tvNickName.setText(userInfo.getUserName());
            }
            if (userInfo.getBirthday() != null && !userInfo.getBirthday().equals("")) {
                tvBirthday.setText(userInfo.getBirthday());
            }
            if (userInfo.getSex() != null && !userInfo.getSex().equals("")) {
                tvSex.setText(userInfo.getSex());
            }
            if (userInfo.getHeight() != 0) {
                tvHeight.setText(userInfo.getHeight()+"CM");
            }
            if (userInfo.getWeight() != 0) {
                tvWeight.setText(userInfo.getWeight()+"KG");
            }
//            if (userInfo.getTags() != null && !userInfo.getTags().equals("")) {
                if (userInfo.getTags() == null) {
                    hsv_tag.setVisibility(View.GONE);
                } else {
                    String selTags = userInfo.getTags();
                    String[] tags = selTags.split(",");
                    Log.e(TAG,"tags "+tags.length);
                    hsv_tag.setVisibility(View.VISIBLE);
                    TagAdapter tagAdapter = new TagAdapter(mContext, tags);
                    int tagSize = tags.length;
                    gvTag.setNumColumns(tagSize);
                    int width = 210;
                    int gridWidth = tagSize * (width + 10);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridWidth, ViewGroup.LayoutParams.FILL_PARENT);
                    gvTag.setLayoutParams(params);
                    gvTag.setColumnWidth(210);
                    gvTag.setHorizontalSpacing(10);
                    gvTag.setAdapter(tagAdapter);
                }

//            }

            if (userInfo.getPhone() != null && !userInfo.getPhone().equals("")) {
                tvPhone.setText(userInfo.getPhone());
            }
            if (userInfo.isFaceId()) {
                switchFace.setChecked(true);
                switchFace.setVisibility(View.VISIBLE);
                tvFaceid.setVisibility(View.GONE);
            }else{
                tvFaceid.setVisibility(View.VISIBLE);
                switchFace.setVisibility(View.GONE);
            }
        }else{
            Log.e(TAG,"clear");
            tvNickName.setText("");
            tvBirthday.setText("");
            tvHeight.setText("");
            tvWeight.setText("");
            tvSex.setText("");
            tvPhone.setText("");
            ImageLoader.getInstance().displayImage("",ciUserPic);
        }

    }

    @OnClick({R.id.ll_userPic, R.id.ll_nickName, R.id.ll_birthday, R.id.ll_height, R.id.ll_weight,
            R.id.ll_userTag, R.id.ll_phone, R.id.btn_saveMember, R.id.btn_delMember, R.id.ll_sex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_userPic:
                Intent picIntent = new Intent(mContext, AvaterActivity.class);
                picIntent.putExtra("status","update");
                startActivity(picIntent);
                break;
            case R.id.ll_nickName:
                Intent nicIntent = new Intent(mContext, NickNameActivity.class);
                nicIntent.putExtra("nickName",tvNickName.getText().toString());
                startActivityForResult(nicIntent,2001);
                break;
            case R.id.ll_sex:
                Intent sexIntent = new Intent(mContext, SexActivity.class);
                sexIntent.putExtra("sex",tvSex.getText().toString());
                startActivityForResult(sexIntent,2003);
                break;
            case R.id.ll_birthday:
                Intent birIntent = new Intent(mContext, BirthdayActivity.class);
                birIntent.putExtra("birthday",tvBirthday.getText().toString());
                startActivityForResult(birIntent,2002);
                break;
            case R.id.ll_height:
                Intent heightIntent = new Intent(mContext, WeightActivity.class);
                heightIntent.putExtra("from","height");
                heightIntent.putExtra("height",userInfo.getHeight()+"");
                startActivityForResult(heightIntent,2004);
                break;
            case R.id.ll_weight:
                Intent weightIntent = new Intent(mContext, WeightActivity.class);
                weightIntent.putExtra("from","weight");
                weightIntent.putExtra("weight",userInfo.getWeight()+"");
                startActivityForResult(weightIntent,2005);
                break;
            case R.id.ll_userTag:
                Intent tagIntent = new Intent(mContext, UserTagActivity.class);
                tagIntent.putExtra("selTag",userInfo.getTags());
                startActivityForResult(tagIntent,2006);
                break;
            case R.id.ll_phone:
                Intent phoneIntent = new Intent(mContext, BindPhoneActivity.class);
                phoneIntent.putExtra("phone",userInfo.getPhone());
                startActivityForResult(phoneIntent,2007);
                break;
            case R.id.btn_saveMember:
                if(isUpdate){
                    if(!imgPath.equals("")){
                        userInfo.setUserImg(imgPath);
                    }
                    userInfoDao.updateUserInfo(userInfo);
                    initMembers();
                }

                break;
            case R.id.btn_delMember:
                if(userInfo != null){
                    DialogHelper.getBaseDialogWithText(mContext, "确认删除该成员吗？", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //确定
                            userInfoDao.delUser(userInfo.getId());
                            initMembers();
                            userInfo = null;
                            setTextValue();
                            isUpdate = false;
                            //保持焦点在顶端
                            tv_member.setFocusable(true);
                            tv_member.setFocusableInTouchMode(true);
                            tv_member.requestFocus();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //取消

                        }
                    }).show();
                }else{
                    Toast.makeText(mContext,"请选择要删除的成员",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    int weight=0,height=0;
    String receiveTags = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 2001:
                    Log.e(TAG, "1001");
                    String nickName = data.getStringExtra("nickName");
                    tvNickName.setText(nickName);
                    if(isUpdate)
                        userInfo.setUserName(nickName);
                    break;
                case 2002:
                    String birth = data.getStringExtra("birthday");
                    tvBirthday.setText(birth);
                    if(isUpdate)
                        userInfo.setBirthday(birth);
                    break;
                case 2003:
                    String sex = data.getStringExtra("sex");
                    tvSex.setText(sex);
                    if(isUpdate)
                        userInfo.setSex(sex);
                    break;
                case 2004://身高
                    height = data.getIntExtra("height", 0);
                    tvHeight.setText(height + "CM");
                    if(isUpdate)
                        userInfo.setHeight(height);
                    break;
                case 2005://体重
                    weight = data.getIntExtra("weight", 0);
                    tvWeight.setText(weight + "KG");
                    if(isUpdate)
                        userInfo.setWeight(weight);
                    break;
                case 2006://偏好标签
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
                        hsv_tag.setVisibility(View.VISIBLE);
                        if(isUpdate)
                            userInfo.setTags(receiveTags);
                    }else {
                        hsv_tag.setVisibility(View.GONE);
                    }
                    break;
                case 2007://手机号
                    String phone = data.getStringExtra("phone");
                    tvPhone.setText(phone);
                    if(isUpdate)
                        userInfo.setPhone(phone);
                    break;
            }
        }
    }

    String imgPath = "";
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        imgPath = intent.getStringExtra("path");
        Log.e(TAG, "onNewIntent ====uri=" + getIntent().getData() + ",path=" + imgPath);
        String from = intent.getStringExtra("from");
        if (from.equals("crop")) {
//            ImageLoader.getInstance().displayImage("file://"+imgPath,ivUserPic);
            ImageLoader.getInstance().displayImage("file://" + imgPath, ciUserPic);
        } else if (from.equals("system")) {
//            ImageLoader.getInstance().displayImage(imgPath,ivUserPic);
            ImageLoader.getInstance().displayImage(imgPath, ciUserPic);
        }
    }
}
