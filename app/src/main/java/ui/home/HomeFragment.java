package ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.sharesdk.cn.myadou.MainActivity;
import demo.sharesdk.cn.myadou.R;

/**
 * Created by Administrator on 2018/1/9.
 */

public class HomeFragment extends Fragment {
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,null,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        mainActivity.initToolbar("首页");
    }
}
