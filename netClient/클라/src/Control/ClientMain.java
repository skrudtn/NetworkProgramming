package Control;

//import View.MainView.DrawPanel;

import Model.LoginModel;

/*
 * Created by skrud on 2017-09-12.
 */
public class ClientMain {

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.getGUIController().test();
//        controller.getGUIController().newLoginView();
    }
}
