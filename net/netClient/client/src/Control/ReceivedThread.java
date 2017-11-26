package Control;

import Model.ClassDiagramModel.CDModel;
import Model.Event;
import Model.RepoModel;
import Model.SearchRepoModel;
import Model.StaticModel.Ack;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-11-21.
 */
public class ReceivedThread implements Runnable {
    private MainController controller;
    private GUIController gc;
    private JsonController jc;
    private Socket socket;
    ReceivedThread(MainController controller, Socket socket){
        this.controller = controller;
        this.gc = controller.getGUIController();
        this.jc=  controller.getJsonController();
        this.socket = socket;
    }
    @Override
    public void run() {
        while(recvAck());
        System.out.println("서버 종료");
    }

    private boolean recvAck() {
        boolean rt = true;
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            int ack = dataInputStream.readInt();
            System.out.println("ack " + ack);
            switch (ack) {
                case Ack.error:
                    break; //에러
                case Ack.lAck:
                    gc.newDisplayView();
                    recvLoginModel();
                    recvSearchData(true);
                    recvFriends();
                    recvEvent();
                    break; //로그인 성공
                case Ack.pREJ:
                    gc.loginStateUpdate("Invalid PW", false);
                    break;// 비밀번호 틀림
                case Ack.iREJ:
                    gc.loginStateUpdate("Invalid ID", false);
                    break;
                case Ack.oACK:
                    gc.signupStateUpdate("Available ID", true);
                    gc.setOverLapFlag(0);
                    break;
                case Ack.oREJ:
                    gc.signupStateUpdate("ID already in use", false);
                    break;
                case Ack.signUpACK:
                    gc.loginView();
                    break;
                case Ack.signUpREJ:
                    gc.signupStateUpdate("Sign Up Failed", false);
                    break;
                case Ack.pwCACK:
                    gc.loginView();
                    break;
                case Ack.pwCREJ:
                    gc.pwChangeStateUpdate("Password Change Failed", 0);
                    break;
                case Ack.pushACK:
                    recvSearchData(true);
                    gc.showMainMessage("Push Completed", -1);
                    break;
                case Ack.pushREJ:
                    gc.showMainMessage("Push Failed", 0);
                    break;
                case Ack.cloneACK:
                    recvCloneData();
                    gc.cllone();
                    break;
                case Ack.cloneREJ:
                    gc.showMainMessage("Clone Failed", 0);
                    break;
                case Ack.searchAck:
                    recvSearchData(false);
                    break;
                case Ack.searchRej:
                    controller.setSdms(null);
                    gc.searchUpdate();
                    break;
                case Ack.loginDupliAck:
                    gc.loginStateUpdate("Failed due to duplicate login", false);
                    break;
                case Ack.logoutAck:
                    gc.signOut();
                    break;
                case Ack.searchIdAck:
                    gc.searchIdStateUpdate("Sent Friend Request",1);
                    break;
                case Ack.searchIdRej:
                    gc.searchIdStateUpdate("Check the Id",0);
                    break;
                case Ack.addFriendAck:  //친구 추가 요청
                    recvReqFriend();
                    break;
                case Ack.addFriendRej:

                    break;
                case Ack.overlapRepoACK: // repo 생성가능
                    recvRepoData();
                    recvSearchData(true);
                    break;
                case Ack.overlapRepoREJ: // repo 중복
                    gc.showRepoMessage(0);
                    break;
                case Ack.repoACK: // version 선택
                    recvRepoData();
                    break;
                case Ack.repoREJ: // version 선택불가능
                    break;
                case Ack.acceptFriend: // 친구 승낙
                    recvFriends();
                    break;
                case Ack.rejectFriend: // 친구 거절
                    break;
                case Ack.updateAuthoAck: // 멤버 교환
                    gc.memberManageUpdate("Complete Save",1);
                    break;
                case Ack.updateAuthoRej: // 친구 거절

                    gc.memberManageUpdate("Failed Save",0);
                    break;

                default:
                    break;
            }

        } catch (IOException e) {
            rt = false;
        }
        return rt;
    }

    private void recvReqFriend() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            controller.getEventsController().addEvent(jc.str2friendEvents(str));
            gc.resEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvEvent() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            controller.getEventsController().getEvents().addAll((jc.str2Events(str)));
            controller.getEventsController().display();
            gc.resEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvRepoData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            RepoModel rm = jc.str2rm(str);
            controller.getUmlController().setRepoModel(rm);
            gc.versionView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvFriends() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            System.out.println(str);
            ArrayList<String> friends = jc.str2friends(str);
            controller.setFriends(friends);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvSearchData(boolean isMysmds) {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            ArrayList<SearchRepoModel> smds = jc.str2smds(str);
            if (isMysmds) {
                controller.setMySdms(smds);
                gc.myRepoUpdate();
            } else {
                controller.setSdms(smds);
                gc.searchUpdate();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvLoginModel() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            controller.getLoginController().setMyAccount(jc.str2lm(str));
            gc.setMainFrameTitle(controller.getLoginController().getMyAccount().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recvCloneData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String str = dataInputStream.readUTF();
            CDModel cdModel = jc.str2cdm(str);
            controller.getUmlController().setCdModel(cdModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
