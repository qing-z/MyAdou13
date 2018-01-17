package ui.regist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import demo.sharesdk.cn.myadou.R;
import utils.ToastUtils;

/**
 * Created by Administrator on 2018/1/1.
 */

public class RegistActivity extends AppCompatActivity implements RegistContract.View, View.OnClickListener {

    private EditText et_regist_acount;
    private Toolbar toolbar;
    private EditText et_regist_pass;
    private EditText et_regist_confirm;
    private Button regist;
    private TextView info;

    RegistContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initListener();
        //实例化Toolbar
        initToolbar();
        initPresenter();
    }

    private void initPresenter() {
        this.presenter = new RegistPresenter(this);
    }

    private void initToolbar() {
        toolbar.setTitle("注册");
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        regist.setOnClickListener(this);
        info.setOnClickListener(this);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_regist_acount = (EditText) findViewById(R.id.et_regist_acount);
        et_regist_pass = (EditText) findViewById(R.id.et_regist_pass);
        et_regist_confirm = (EditText) findViewById(R.id.et_regist_confirm);
        regist = (Button) findViewById(R.id.bt_regist);
        info = (TextView) findViewById(R.id.tv_info);

    }

    @Override
    public void registSuccess() {
        ToastUtils.show("注册成功！请您登陆...");
        finish();
    }

    @Override
    public void registError(int errCode, String errMsg) {
        //登陆失败的方法
        ToastUtils.show("注册失败！" + errMsg + "错误码：" + errCode);
    }

    @Override
    public void registInfoEmpty() {
        ToastUtils.show("账户密码不能为空...");
    }

    @Override
    public void registInfoError() {
        ToastUtils.show("账户密码有误...");
    }

    @Override
    public void registConfirmPassError() {
        ToastUtils.show("两次输入的结果不同，请检查...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                String acount = et_regist_acount.getText().toString().trim();
                String pass = et_regist_pass.getText().toString().trim();
                String confirmPass = et_regist_confirm.getText().toString().trim();
                presenter.regist(acount, pass, confirmPass);

                break;
            case R.id.tv_info:

                break;
            default:
                break;
        }

    }
}
