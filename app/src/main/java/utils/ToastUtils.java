package utils;

import android.widget.Toast;

import app.AdouApplication;

/**
 * Created by Administrator on 2018/1/1.
 */

public class ToastUtils {
    static Toast toast;

    public static void show(String str) {
        if (toast == null) {
            toast = Toast.makeText(AdouApplication.getapp().getApplicationContext(), "", Toast.LENGTH_LONG);
        }
        toast.setText(str);
        toast.show();
    }
}
