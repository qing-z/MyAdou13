package ui.mine;

import bean.AdouTimUserProfile;
import engine.TimProfileHelper;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ProfilePresenter implements ProfileContract.Presenter{

    ProfileContract.View view;
    MineFragment mineFragment;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        mineFragment=(MineFragment)view;
    }

    @Override
    public void getUserProfile() {

        new TimProfileHelper().getSelfProfile(mineFragment.getActivity(), new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(AdouTimUserProfile mProfile) {
                view.updateProfile(mProfile);
            }
            @Override
            public void noGet() {
                //没有获取到
                view.updateProfileError();
            }
        });
    }
}
