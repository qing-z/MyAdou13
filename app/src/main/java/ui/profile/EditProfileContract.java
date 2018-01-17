package ui.profile;

import bean.AdouTimUserProfile;

/**
 * Created by Administrator on 2018/1/4.
 */

public interface EditProfileContract {
    interface View{
        void updateView(AdouTimUserProfile profile);
        void onGetInfoFailed();
        void updateInfoError();
        void updateSuccess();
    }
    interface Presenter{
        void getUserInfo();
        void onUpdateInfoSuccess();
    }
}
