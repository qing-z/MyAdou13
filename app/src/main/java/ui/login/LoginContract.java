package ui.login;

/**
 * Created by Administrator on 2018/1/1.
 */

public interface LoginContract {
    interface View {
        //登陆成功
        void loginSuccess();

        //登陆失败
        void loginFaild(int errCode, String errMsg);

        //登陆不能为空
        void loginInfoiEmapty();

        //登陆异常的
        void loginInfoError();
    }

    interface Presenter {
        void login(String acount, String pass);
    }
}
