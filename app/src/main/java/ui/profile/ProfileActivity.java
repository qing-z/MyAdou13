package ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.TIMFriendGenderType;

import bean.AdouTimUserProfile;
import demo.sharesdk.cn.myadou.R;
import utils.ImageUtils;
import utils.ToastUtils;


/**
 * Created by Administrator on 2018/1/2.
 * 个人被关注的界面和粉丝界面
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,ProfileContract.View{

    private ProfileContract.Presenter presenter;
    private ImageView iv_avatar;
    private TextView tv_nickname;
    private ImageView iv_gender;
    private TextView tv_grade,tv_acount_id;
    private LinearLayout Lin_check_profile;
    private ImageView imageView;
    private TextView tv_my_fans,tv_my_fork;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initToolbar();
        initPresenter();
        initListener();
    }

    private void initListener() {
        Lin_check_profile.setOnClickListener(this);
    }

    private void initPresenter() {
        presenter=new ProfilePresenter(this);
        presenter.getUserProfile();
    }

    private void initToolbar() {


    }

    private void initView() {
        tv_acount_id= (TextView) findViewById(R.id.tv_acount_id);
        imageView= (ImageView) findViewById(R.id.imageView);
        Lin_check_profile= (LinearLayout) findViewById(R.id.Lin_check_profile);
        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        iv_gender = (ImageView) findViewById(R.id.iv_gender);
        tv_grade = (TextView) findViewById(R.id.tv_grade);
        tv_my_fans = (TextView) findViewById(R.id.tv_my_fans);
        tv_my_fork = (TextView) findViewById(R.id.tv_my_fork);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Lin_check_profile:
                Intent intent=new Intent(this, EditProfileActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    @Override
    public void updateProfile(AdouTimUserProfile profile) {
        //更新view,设置图片跟字段
        if (!TextUtils.isEmpty(profile.getProfile().getFaceUrl())){
            ImageUtils.getInstance().loadCircle(profile.getProfile().getFaceUrl(),iv_avatar);
        } else{
            ImageUtils.getInstance().loadCircle(R.mipmap.ic_launcher,iv_avatar);
        }
        if (!TextUtils.isEmpty(profile.getProfile().getNickName())){
            tv_nickname.setText(profile.getProfile().getNickName());
        }else {
            tv_nickname.setText("暂无昵称");
        }
        if (!TextUtils.isEmpty(profile.getProfile().getIdentifier())){
            tv_acount_id.setText("阿斗号:"+profile.getProfile().getIdentifier());
        }
        if (profile.getProfile().getGender()== TIMFriendGenderType.Male){
            iv_gender.setBackgroundResource(R.mipmap.male);
        }else if (profile.getProfile().getGender()==TIMFriendGenderType.Female){
            iv_gender.setBackgroundResource(R.mipmap.female);
        }else{
            //默认是男
            iv_gender.setBackgroundResource(R.mipmap.male);
        }
        tv_grade.setText(profile.getGrade()+"");
        tv_my_fork.setText(profile.getFork()+"");
        tv_my_fans.setText(profile.getFans()+"");

    }

    @Override
    public void updateProfileError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("拉取信息失败");
            }
        });
    }
}
