package ui.mine;

import bean.AdouTimUserProfile;

/**
 * Created by Administrator on 2018/1/9.
 */

public interface ProfileContract {
    interface View{
        void updateProfile(AdouTimUserProfile profile);
        void updateProfileError();
    }
    interface  Presenter{
        void getUserProfile();
    }
}
