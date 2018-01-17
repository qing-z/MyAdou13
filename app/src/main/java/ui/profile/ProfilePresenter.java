package ui.profile;

import app.AdouApplication;
import bean.AdouTimUserProfile;
import engine.TimProfileHelper;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ProfilePresenter implements ProfileContract.Presenter{
    ProfileContract.View view;
    ProfileActivity activity;

    public ProfilePresenter(ProfileActivity view) {
        this.view = view;
        activity=(ProfileActivity)view;
    }

    @Override
    public void getUserProfile() {
        AdouTimUserProfile adouTimUserProfile = AdouApplication.getapp().getAdouTimUserProfile();
        if (adouTimUserProfile!=null){
            view.updateProfile(adouTimUserProfile);
        }else{
            new TimProfileHelper().getSelfProfile(activity, new TimProfileHelper.OnProfileGet() {
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

}
