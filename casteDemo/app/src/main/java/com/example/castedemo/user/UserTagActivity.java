package com.example.castedemo.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.example.castedemo.R;
import com.example.castedemo.family.adapter.NewTagAdapter;
import com.example.castedemo.family.adapter.TagAdapter;
import com.example.castedemo.user.bean.TagBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserTagActivity extends Activity {
    private static final String TAG = "UserTagActivity";

    @BindView(R.id.gv_addedTag)
    GridView gvAddedTag;
    @BindView(R.id.gv_allTag)
    GridView gvAllTag;
    //    @BindView(R.id.iv_back)
//    ImageView ivBack;
//    @BindView(R.id.rl_bottom)
//    RelativeLayout rlBottom;
    String[] allTags = {"推荐", "热点", "视频", "图片", "段子", "社会", "娱乐", "科技"};
    NewTagAdapter tagAdapter;
    @BindView(R.id.btn_tagCancel)
    Button btnTagCancel;
    @BindView(R.id.btn_tagSave)
    Button btnTagSave;
    Context mContext;
    List<String> selTags = new ArrayList<String>();
    List<TagBean> tagBeen = new ArrayList<TagBean>();
    List<TagBean> selTagBeans = new ArrayList<TagBean>();
    NewTagAdapter selTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_tag);
        ButterKnife.bind(this);
        mContext = UserTagActivity.this;
        initData();
        initSelData();
    }

    public void initData() {
        for(int i=0;i<allTags.length;i++){
            TagBean bean = new TagBean();
            bean.setTag(allTags[i]);
            bean.setSel(false);
            tagBeen.add(bean);
        }
        tagAdapter = new NewTagAdapter(mContext,tagBeen);
        gvAllTag.setAdapter(tagAdapter);
        gvAllTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagBean tagBean = tagBeen.get(position);
                Log.e(TAG,"tagName="+tagBean.getTag()+","+tagBean.isSel());
                if(tagBean.isSel()){
                    tagBean.setSel(false);
                    selTagBeans.remove(tagBean);
                }else{
                    tagBean.setSel(true);
                    selTagBeans.add(tagBean);
                }
                tagAdapter.notifyDataSetChanged();
                selTagAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initSelData(){
        if(getIntent().getStringExtra("selTag") != null){
            String tags = getIntent().getStringExtra("selTag");
            String[] saveTag = tags.split(",");
            for(int i=0;i<saveTag.length;i++){
                for(int j=0;j<tagBeen.size();j++){
                    TagBean bean = tagBeen.get(j);
                    if(saveTag[i].equals(bean.getTag())){
                        bean.setSel(true);
                        selTagBeans.add(bean);
                    }
                }
            }
        }
        selTagAdapter = new NewTagAdapter(mContext,selTagBeans);
        gvAddedTag.setAdapter(selTagAdapter);
        gvAddedTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TagBean bean = selTagBeans.get(position);
                bean.setSel(false);
                selTagBeans.remove(position);
                selTagAdapter.notifyDataSetChanged();
                tagAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.btn_tagCancel, R.id.btn_tagSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tagCancel:
                finish();
                break;
            case R.id.btn_tagSave:
                String passTags = "";
                for(int i=0;i<selTagBeans.size();i++){
//                    passTags[i] = selTagBeans.get(i).getTag();
                    if(selTagBeans.size()-1 == i){
                        passTags += selTagBeans.get(i).getTag();
                    }else{
                        passTags += selTagBeans.get(i).getTag()+",";
                    }
                }
                Intent userIntent = new Intent();
                userIntent.putExtra("tag",passTags);
                setResult(RESULT_OK,userIntent);
                finish();
                break;
        }
    }
}
