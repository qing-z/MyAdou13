package ui.createlive;

/**
 * Created by Administrator on 2018/1/10.
 */

public class CreateLiveContract{
    interface View{
        void onCreateSuccess();
        void onCreateFailed();
    }
    interface Presenter{
        void createLive(String url,String liveName);
    }
}
