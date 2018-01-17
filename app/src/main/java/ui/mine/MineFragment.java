package ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.TIMFriendGenderType;

import bean.AdouTimUserProfile;
import demo.sharesdk.cn.myadou.MainActivity;
import demo.sharesdk.cn.myadou.R;
import ui.profile.EditProfileActivity;
import utils.ImageUtils;
import utils.ToastUtils;

/**
 * Created by Administrator on 2018/1/9.
 */

public class MineFragment extends Fragment implements ProfileContract.View {

    private ProfileContract.Presenter presenter;
    private ImageView iv_avatar;
    private TextView tv_nickname;
    private ImageView iv_gender;
    private TextView tv_grade, tv_acount_id;
    private TextView Lin_check_profile;
    private ImageView imageView;
    private TextView tv_my_fans, tv_my_fork;
    private TextView tv_send,tv_receive;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
        initPresenter();
        mainActivity = (MainActivity) getActivity();
        mainActivity.initToolbar("我");
    }

    private void initPresenter() {
        presenter = new ProfilePresenter(this);
    }

    private void initListener() {
        Lin_check_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        tv_send= (TextView) view.findViewById(R.id.tv_send);
        tv_receive= (TextView) view.findViewById(R.id.tv_receive);
        tv_acount_id = (TextView) view.findViewById(R.id.tv_acount_id);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        Lin_check_profile = (TextView) view.findViewById(R.id.Lin_check_profile);
        iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
        tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
        iv_gender = (ImageView) view.findViewById(R.id.iv_gender);
        tv_grade = (TextView) view.findViewById(R.id.tv_grade);
        tv_my_fans = (TextView) view.findViewById(R.id.tv_my_fans);
        tv_my_fork = (TextView) view.findViewById(R.id.tv_my_fork);
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
        tv_send.setText(profile.getFork()+"");
        tv_receive.setText(profile.getFans()+"");

    }

    @Override
    public void updateProfileError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show("拉去信息失败！！");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserProfile();
    }
}
