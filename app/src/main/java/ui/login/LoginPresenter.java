package ui.login;

import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import engine.TimProfileHelper;
import ui.profile.ProfileActivity;
import utils.ToastUtils;


/**
 * Created by Administrator on 2018/1/1.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenter";
    //持有view的引用
    LoginContract.View mView;
    LoginActivity loginActivity;
    //通过构造方法传参
    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        //强转成activity
        loginActivity = (LoginActivity) mView;
    }
    //真正的登录
    private void realLogin(String acount, String pass) {
        ILiveLoginManager.getInstance().tlsLoginAll(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                //登陆成功的方法
                mView.loginSuccess();
                getUserInfo();
                Intent intent=new Intent(loginActivity, ProfileActivity.class);
                loginActivity.startActivity(intent);
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                //登陆失败的方法
                mView.loginFaild(errCode, errMsg);
            }
        });
    }

    @Override
    public void login(String count, String pass) {
        if (TextUtils.isEmpty(count) || TextUtils.isEmpty(pass)) {
            mView.loginInfoiEmapty();
            return;
        }
        if (count.length() < 6 || pass.length() < 8) {
            mView.loginInfoError();
            return;
        }
        realLogin(count, pass);
    }
    //获取用户的个人信息
    private void getUserInfo() {
        new TimProfileHelper().getSelfProfile(loginActivity,null);
    }
}
