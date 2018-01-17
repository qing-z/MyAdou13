package ui.profile;

import bean.AdouTimUserProfile;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ProfileContract {
    interface View{
        void updateProfile(AdouTimUserProfile profile);
        void updateProfileError();
    }
    interface  Presenter{
        void getUserProfile();
    }
}
