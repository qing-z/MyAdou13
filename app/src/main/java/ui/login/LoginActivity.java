package ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import demo.sharesdk.cn.myadou.R;
import ui.regist.RegistActivity;
import utils.ToastUtils;

/**
 * Created by Administrator on 2018/1/1.
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.View,View.OnClickListener {


    private Button bt_regist;
    private EditText et_pass;
    private EditText et_username;
    private TextView tv_info;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        initPresenter();
    }

    private void initPresenter() {
        presenter=new LoginPresenter(this);
    }

    private void initListener() {
        bt_regist.setOnClickListener(this);
        tv_info.setOnClickListener(this);
    }

    private void initView() {
        bt_regist = (Button) findViewById(R.id.bt_regist);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_username = (EditText) findViewById(R.id.et_username);
        tv_info = (TextView) findViewById(R.id.tv_info);
    }

    @Override
    public void loginSuccess() {
        //登陆成功的方法
        ToastUtils.show("登陆成功~~~");
    }

    @Override
    public void loginFaild(int errCode, String errMsg) {
        //登录失败
        ToastUtils.show("登陆失败~"+errCode+"错误码："+errMsg);
    }

    @Override
    public void loginInfoiEmapty() {
        //登陆时判断不能为空
        ToastUtils.show("账户或密码不能为空...");
    }

    @Override
    public void loginInfoError() {
        //登陆异常的方法
        ToastUtils.show("输入有误，请检查...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_regist:
                String acount = et_username.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                presenter.login(acount,pass);
                break;
            case R.id.tv_info:
                Intent intent=new Intent(this,RegistActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
