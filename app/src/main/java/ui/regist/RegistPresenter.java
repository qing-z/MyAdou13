package ui.regist;

import android.text.TextUtils;

import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

/**
 * Created by Administrator on 2018/1/2.
 */

public class RegistPresenter implements RegistContract.Presenter{
    RegistActivity registActivity;
    RegistContract.View mview;


    public RegistPresenter(RegistContract.View mview){
        this.mview = mview;
        registActivity=(RegistActivity) mview;
    }
    //注册是否成功的方法
    @Override
    public void regist(String acount, String pass, String confirmpass) {
            if (TextUtils.isEmpty(acount)|| TextUtils.isEmpty(pass)||TextUtils.isEmpty(confirmpass)){
                mview.registInfoEmpty();
            }
        if (acount.length()<6||pass.length()<8||confirmpass.length()<8){
            mview.registInfoError();
        }
        if (!pass.equals(confirmpass)){
            mview.registConfirmPassError();
        }
        readlRegist(acount,pass);
    }
    private void readlRegist(String acount, String pass) {
        //直播的处理
        ILiveLoginManager.getInstance().tlsRegister(acount, pass, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                mview.registSuccess();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                mview.registError(errCode,errMsg);
            }
        });
    }
}
