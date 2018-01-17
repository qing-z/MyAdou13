package ui.createlive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import demo.sharesdk.cn.myadou.R;
import utils.ToastUtils;
import widget.SelectPicDialog;


/**
 * Created by Administrator on 2018/1/10.
 */

public class CreateLiveActivity extends AppCompatActivity implements CreateLiveContract.View, View.OnClickListener {
    private EditText et_room_name;
    private Button bt_createlive;
    private ImageView iv_cover;
    private FrameLayout fl;
    SelectPicDialog selectPicDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlive);
        initView();
        initListener();
    }

    private void initListener() {
        fl.setOnClickListener(this);
        bt_createlive.setOnClickListener(this);
    }

    private void initView() {
        fl = (FrameLayout) findViewById(R.id.fl);
        iv_cover = (ImageView) findViewById(R.id.iv_cover);
        et_room_name = (EditText) findViewById(R.id.et_room_name);
        bt_createlive = (Button) findViewById(R.id.bt_createlive);

    }

    @Override
    public void onCreateSuccess() {

    }

    @Override
    public void onCreateFailed() {
        ToastUtils.show("信息不能为空，创建失败~");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //设置背景图片
            case R.id.fl:
                showSelectPicDialog();
                break;
            case R.id.bt_createlive:
                //点击开始直播

                break;
            default:
                break;
        }
    }

    private void showSelectPicDialog() {
        selectPicDialog = new SelectPicDialog(this, R.style.dialog_ios_style);
        selectPicDialog.show();
    }
}
