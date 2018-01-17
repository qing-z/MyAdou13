package ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;

import org.json.JSONObject;

import java.io.File;

import bean.AdouTimUserProfile;
import app.QiniuConfig;
import demo.sharesdk.cn.myadou.MainActivity;
import demo.sharesdk.cn.myadou.R;
import engine.PicChooseHelper;
import qiniu.QiniuUploadHelper;
import utils.ToastUtils;
import widget.EditProfileAvatarDialog;
import widget.EditProfileDialog;
import widget.EditProfileDialog2;
import widget.EditProfileItem;
import widget.EditProfile_Gender_Dialog;


/**
 * Created by Administrator on 2018/1/2.
 * 个人的资料界面
 */

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.View, View.OnClickListener {
    private Toolbar toolbar;
    private EditProfileItem ep_avatar;//头像
    private EditProfileItem ep_nickname;//昵称
    private EditProfileItem ep_adouid;//啊斗号
    private EditProfileItem ep_gender;//性别
    private EditProfileItem ep_xingzuo;//星座
    private EditProfileItem ep_active_area;//活动地带
    private EditProfileItem ep_signature;//个性签名
    private EditProfileItem ep_home_country;//家乡
    private EditProfileItem ep_job;//职业
    private Button bt_save_profile;
    private EditProfileContract.Presenter presenter;
    EditProfileAvatarDialog avatarDialog;
    private EditProfileDialog editProfileDialog;
    EditProfile_Gender_Dialog genderDialog;

    EditProfileDialog2  signatrueDialog;

    EditProfileDialog2  activeDialog;

    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sp = getSharedPreferences("isfirstenter", MODE_PRIVATE);
        edit = sp.edit();
        edit.putBoolean("isfirst", false);
        edit.commit();
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter = new EditProfilePresenter(this);
        presenter.getUserInfo();
    }

    //设置toolbar按钮的点击事件
    private void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //这是每个条目的点击事件
        ep_avatar.setOnClickListener(this);
        ep_nickname.setOnClickListener(this);
        ep_gender.setOnClickListener(this);
        ep_xingzuo.setOnClickListener(this);
        ep_active_area.setOnClickListener(this);
        ep_signature.setOnClickListener(this);
        ep_home_country.setOnClickListener(this);
        ep_job.setOnClickListener(this);
        bt_save_profile.setOnClickListener(this);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ep_avatar = (EditProfileItem) findViewById(R.id.ep_avatar);//头像
        ep_nickname = (EditProfileItem) findViewById(R.id.ep_nickname);//昵称
        ep_adouid = (EditProfileItem) findViewById(R.id.ep_adouid);//啊斗号
        ep_gender = (EditProfileItem) findViewById(R.id.ep_gender);//性别
        ep_xingzuo = (EditProfileItem) findViewById(R.id.ep_xingzuo);//星座
        ep_active_area = (EditProfileItem) findViewById(R.id.ep_active_area);//活跃地带
        ep_signature = (EditProfileItem) findViewById(R.id.ep_signature);//个性签名
        ep_home_country = (EditProfileItem) findViewById(R.id.ep_home_country);//家乡
        ep_job = (EditProfileItem) findViewById(R.id.ep_job);//职业
        bt_save_profile= (Button) findViewById(R.id.bt_save_profile);//保存
    }


    @Override
    public void updateView(AdouTimUserProfile profile) {
        if (profile != null) {
            TIMUserProfile userprofile = profile.getProfile();
            String nickName = userprofile.getNickName();
            String faceUrl = userprofile.getFaceUrl();
            String identifier = userprofile.getIdentifier();
            TIMFriendGenderType gender = userprofile.getGender();
            String location = userprofile.getLocation();
            String selfSignature = userprofile.getSelfSignature();
            if (!TextUtils.isEmpty(faceUrl)) {
                ep_avatar.setAvatar(faceUrl);
            }
            if (!TextUtils.isEmpty(nickName)) {
                ep_nickname.setValue(nickName);
            }
            if (!TextUtils.isEmpty(identifier)) {
                ep_adouid.setValue(identifier);
            }
            if (!TextUtils.isEmpty(location)) {
                ep_active_area.setValue(location);
            }
            if (!TextUtils.isEmpty(selfSignature)) {
                ep_signature.setValue(selfSignature);
            }
            if (gender == TIMFriendGenderType.Male) {
                ep_gender.setValue("男");
            } else if (gender == TIMFriendGenderType.Female) {
                ep_gender.setValue("女");
            } else {
                ep_gender.setValue("未知");
            }

        }
    }

    @Override
    public void onGetInfoFailed() {
        //失败的方法
    }

    @Override
    public void updateInfoError() {
        ToastUtils.show("更新信息失败，请重试");
    }

    @Override
    public void updateSuccess() {
        ToastUtils.show("更新信息成功");
        presenter.getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像
            case R.id.ep_avatar:
                showSelectAvatarDialog();

                break;
            //昵称
            case R.id.ep_nickname:

                showEditNickNameDialog();
                break;
            //性别
            case R.id.ep_gender:
                showEditGenderDialog();
                break;
            //活跃地带
            case R.id.ep_active_area:
                showEditActiveDialog();
                break;
            //职业
            case R.id.ep_job:
                break;
            //个性签名
            case R.id.ep_signature:
                showEditSignatureDialog();
                break;
            //星座
            case R.id.ep_xingzuo:
                break;
            //保存
            case R.id.bt_save_profile:
                finish();
                Intent intent = new Intent(this, MainActivity.class);
                //添加标记 说明是从这跳过取得
                intent.putExtra("from","EditProfileActivity");
                startActivity(intent);
            default:
                break;
        }
    }

    //个性签名
    private void showEditSignatureDialog() {
       signatrueDialog = new EditProfileDialog2(this, new EditProfileDialog2.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                TIMFriendshipManager.getInstance().setSelfSignature(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onSuccess() {
                        signatrueDialog.hide();
                        signatrueDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {
            }
        });
        signatrueDialog.setTitleAndIcon("请输入您的个性签名", R.mipmap.male);
        signatrueDialog.show();
    }

    //活跃地带
    private void showEditActiveDialog() {
        activeDialog = new EditProfileDialog2(this, new EditProfileDialog2.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(final String value) {
                //先通过api 设置location
                String value2 = value;
                TIMFriendshipManager.getInstance().setLocation(value2, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        updateInfoError();
                    }

                    @Override
                    public void onSuccess() {
                        //再展示新的信息
//                         ep_active_area.setValue(value2);
                        activeDialog.hide();
                        activeDialog = null;
                        presenter.onUpdateInfoSuccess();

                    }
                });
                //更新应用缓存
            }

            @Override
            public void onChangeError() {
                activeDialog.hide();
                activeDialog = null;
                updateInfoError();

            }
        });
        activeDialog.setTitleAndIcon("请输入您的活跃地带：", R.mipmap.female);
        activeDialog.show();

    }

    //性别
    private void showEditGenderDialog() {
        genderDialog = new EditProfile_Gender_Dialog(this, new EditProfile_Gender_Dialog.OnProfileChangedListener() {
            @Override
            public void onChangeSuccess(String value) {
                TIMFriendGenderType type;
                if ("男生".equals(value)) {
                    type = TIMFriendGenderType.Male;
                } else if ("女生".equals(value)) {
                    type = TIMFriendGenderType.Female;
                } else {
                    type = TIMFriendGenderType.Unknow;
                }
                TIMFriendshipManager.getInstance().setGender(type, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        genderDialog.hide();
                        genderDialog = null;
                        presenter.onUpdateInfoSuccess();
                    }
                });
            }

            @Override
            public void onChangeError() {

            }
        });
        genderDialog.setTitleAndIcon("请选择您的性别：", R.mipmap.female);
        genderDialog.show();

    }

    //称呼,姓名
    private void showEditNickNameDialog() {
        editProfileDialog = new EditProfileDialog(this, new EditProfileDialog.OnEditChangedListener() {

            @Override
            public void onChanged(String value) {
                //更改服务器上内容
                TIMFriendshipManager.getInstance().setNickName(value, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        //更新信息失败
                        updateInfoError();
                    }

                    @Override
                    public void onSuccess() {
                        //更新信息成功
                        presenter.onUpdateInfoSuccess();

                        editProfileDialog.dismiss();
                        editProfileDialog = null;
                    }
                });
            }

            @Override
            public void onContentEmpty() {
                editProfileDialog.dismiss();
                editProfileDialog = null;
                updateInfoError();
            }
        });
        editProfileDialog.setTitleAndIcon("请输入您的昵称：", R.mipmap.ic_launcher);
        editProfileDialog.show();
    }

    //头像
    private void showSelectAvatarDialog() {
        avatarDialog = new EditProfileAvatarDialog(this, R.style.common_dialog_style);
        avatarDialog.show();
    }

    private void updateNetAvatarInfo(String url) {
        //需要把图片同步到服务器
        TIMFriendshipManager.getInstance().setFaceUrl(url, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                updateInfoError();
            }

            @Override
            public void onSuccess() {
                //重置缓存信息
                presenter.onUpdateInfoSuccess();
            }
        });
    }
    //这里是在相册中选择好图片的时候对图片进行处理，然后上传图片的一些操作
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PicChooseHelper.getInstance(this).onActivityResult(requestCode, resultCode
                , data, new PicChooseHelper.OnAvatarReadyListener() {
            @Override
            public void onReady(Uri outUri) {
                ep_avatar.setAvatar(outUri);
                avatarDialog.dismiss();
                //需要把路径传到服务器（七牛）
                String path = outUri.getPath();
                File file = new File(path);
                String absolutePath = file.getAbsolutePath();
                String name = file.getName();
                try {
                    QiniuUploadHelper.uploadPic(absolutePath, name, new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject response) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()){
                                Logger.i("qiniu", "Upload Success");
                                updateNetAvatarInfo(QiniuConfig.QINIU_HOST+key);
                            }else{
                                Logger.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
                            Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + response);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
