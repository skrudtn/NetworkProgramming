package Model.StaticModel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by skrud on 2017-11-25.
 */
public class MyImage {
    public static ImageIcon displayBgImage = null;
    public static ImageIcon loginBgImage = null;
    public static ImageIcon btn_signin_lg = null;
    public static ImageIcon btn_signup_lg = null;
    public static ImageIcon logo= null;
    public static ImageIcon btn_signup_sm = null;
    public static ImageIcon btn_check= null;
    public static ImageIcon btn_cancel_sm=null;
    public static ImageIcon btn_changepw=null;
    public static ImageIcon btn_signout;
    public static ImageIcon btn_addfriend;
    public static ImageIcon btn_ok;
    public static ImageIcon btn_rej;
    public static ImageIcon btn_status_ok;
    public static ImageIcon btn_status_rej;
    public static ImageIcon searchRepoBgImage;
    public static ImageIcon btn_repository;
    public static ImageIcon btn_myalert;
    public static ImageIcon btn_myalert1;
    public static ImageIcon btn_myalert2;
    public static ImageIcon btn_back;
    public static ImageIcon btn_push;
    public static ImageIcon notice_bgImage;
    public static ImageIcon btn_plus;
    public static ImageIcon btn_l;
    public static ImageIcon btn_r;
    public static ImageIcon btn_yourRepo;
    public static ImageIcon btn_searchRepo;
    public static ImageIcon btn_guidebook;
    public static ImageIcon btn_addRepo;
    public static ImageIcon btn_myInfo;
    public static ImageIcon btn_delete;
    public static ImageIcon repoBgImage;
    public static ImageIcon btn_delete_ok;
    public static ImageIcon btn_delete_rej;

    public static void loadBgImage(int w, int h){
        displayBgImage= getImageIcon("bgImage.png",w,h);
    }
    public static void loadImageFile(){
        btn_changepw = getImageIcon("btn-changepw.png",Size.ADD_FRIEND_BTN_W,Size.ADD_FRIEND_BTN_H);
        btn_signout = getImageIcon("btn-signout.png",Size.SIGN_OUT_BTN_W,Size.SIGN_OUT_BTN_H);
        btn_addfriend = getImageIcon("btn-addfriend.png",Size.ADD_FRIEND_BTN_W,Size.ADD_FRIEND_BTN_H);
        btn_ok = getImageIcon("okBtn.png",Size.BACKBTNWIDTH,Size.OK_H);
        btn_rej = getImageIcon("btn_remove.png",Size.BACKBTNWIDTH,Size.BACKBTNWIDTH);
        btn_status_ok = getImageIcon("statusOkBtn.png",Size.STATUSBTN,Size.STATUSH);
        btn_status_rej = getImageIcon("statusRejBtn.png",Size.STATUSBTN,Size.STATUSH);
        searchRepoBgImage = getImageIcon("bgImage.png",Size.RWIDTH,Size.RHEIGHT);
        btn_repository = getImageIcon("btn-repository.png",Size.BTN_REPO_W, Size.BTN_REPO_H);
        btn_myalert = getImageIcon("btn-myalert.png",Size.ACC_BTN_W,Size.ACC_BTN_W);
        btn_myalert1 = getImageIcon("btn-myalert1.png",Size.ACC_BTN_W,Size.ACC_BTN_W);
        btn_myalert2 = getImageIcon("btn-myalert2.png",Size.ACC_BTN_W,Size.ACC_BTN_W);
        btn_back= getImageIcon("backBtn.png", Size.BACKBTNWIDTH, Size.BACKBTNHEIGHT);
        btn_push = getImageIcon("pushBtn.png", Size.PUSHBTNWIDTH, Size.PUSHBTNHEIGHT);
        notice_bgImage = getImageIcon("bgImage.png",Size.NOTICE_W,3000);
        btn_plus = getImageIcon("plusBtn.png",Size.TEXT_H,Size.TEXT_H);
        btn_l = getImageIcon("lBtn.png",Size.RBTNW,Size.RBTNH);
        btn_r = getImageIcon("rBtn.png",Size.RBTNW,Size.RBTNH);
        btn_yourRepo = getImageIcon("btn-yourRepository.png",Size.SEARCH_REPO_W,Size.SEARCH_REPO_H);
        btn_searchRepo = getImageIcon("btn-searchRepository.png",Size.SEARCH_REPO_W,Size.SEARCH_REPO_H);
        btn_guidebook = getImageIcon("btn-guidebook.png",Size.ACC_BTN_W,Size.ACC_BTN_H);
        btn_myInfo = getImageIcon("btn-myinfo.png",Size.ACC_BTN_W,Size.ACC_BTN_H);
        btn_delete = getImageIcon("btn_delete.png",Size.BTN_DELETE,Size.BTN_DELETE);
        btn_addRepo = getImageIcon("btn-addRepository.png",Size.ADDREPO_W,Size.ADDREPO_H);
        repoBgImage  = getImageIcon("repoBgImage.png",570,120);
        btn_delete_ok = getImageIcon("okBtn.png",Size.DELETE_OK_W,Size.DELETE_OK_H);
        btn_delete_rej = getImageIcon("btn-cancel-sm.png",Size.DELETE_OK_W,Size.DELETE_OK_H);

    }


    public static ImageIcon getImageIcon(String fileName,int w, int h) {
        String path = "client\\Image\\"+fileName;
        ImageIcon icon = new ImageIcon(path);
        Image image = icon.getImage();
        Image changeImage = image.getScaledInstance(w,h, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImage);
    }

    public static void loadLoginImage() {
        loginBgImage = getImageIcon("bgImage.png",Size.LOGIN_FRAME_W,Size.LOGIN_FRAME_H);
        btn_signin_lg = getImageIcon("btn-signin-lg.png",Size.SIGNIN_BTN_W,Size.SIGNIN_BTN_H);
        btn_signup_lg = getImageIcon("btn-signup-lg.png",Size.SIGNIN_BTN_W,Size.SIGNIN_BTN_H);
        logo = getImageIcon("logo.png",Size.LOGO_W,Size.LOGO_H);
        btn_signup_sm = getImageIcon("btn-signup-sm.png",Size.SIGNUP_SBTN_W,Size.SIGNIN_BTN_H);
        btn_check = getImageIcon("btn-check.png",Size.CHECK_BTN,Size.CHECK_BTN);
        btn_cancel_sm = getImageIcon("btn-cancel-sm.png",Size.SIGNUP_SBTN_W,Size.SIGNUP_SBTN_H);

    }
}
