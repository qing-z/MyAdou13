package ui.profile;

import bean.AdouTimUserProfile;
import engine.TimProfileHelper;

/**
 * Created by Administrator on 2018/1/4.
 */

public class EditProfilePresenter implements EditProfileContract.Presenter {
    EditProfileContract.View mview;
    EditProfileActivity editProfileActivity;

    public EditProfilePresenter(EditProfileContract.View mview) {
        this.mview = mview;
        editProfileActivity = (EditProfileActivity) mview;
    }

    @Override
    public void getUserInfo() {
        //从app中拿，如果没有在自己去获取
        TimProfileHelper.getInstance().getSelfProfile(editProfileActivity, new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(AdouTimUserProfile mProfile) {
                mview.updateView(mProfile);
            }

            @Override
            public void noGet() {
                mview.onGetInfoFailed();
            }
        });
    }

    @Override
    public void onUpdateInfoSuccess() {
        TimProfileHelper.getInstance().getSelfProfile(editProfileActivity, new TimProfileHelper.OnProfileGet() {
            @Override
            public void onGet(AdouTimUserProfile mProfile) {
                mview.updateView(mProfile);
//                mview.updateSuccess();
            }

            @Override
            public void noGet() {

            }
        });
    }
}
