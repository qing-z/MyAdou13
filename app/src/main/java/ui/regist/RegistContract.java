package ui.regist;

/**
 * Created by Administrator on 2018/1/2.
 */

public interface RegistContract {
    interface View{
        //注册成功的方法
        void registSuccess();
        //注册失败的方法
        void registError(int errCode, String errMsg);
        //判断信息不能为空
        void registInfoEmpty();
        //输入信息出错
        void registInfoError();
        //两次密码输入不一致
        void registConfirmPassError();
    }
    interface Presenter{
        void regist(String acount,String pass,String confirmpass);
    }
}
