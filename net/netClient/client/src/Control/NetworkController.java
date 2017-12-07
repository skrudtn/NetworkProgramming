package Control;

import Model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import Model.ClassDiagramModel.CDModel;
import Model.StaticModel.Ack;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by skrud on 2017-09-27.
 */
public class NetworkController {
    private final static String IP = "127.0.0.1";
    private final static int PORT = 10001;
    private MainController controller = null;
    private CryptoController cc;
    private Socket socket;

    public NetworkController(MainController controller) {
        this.controller = controller;
        this.cc= controller.getCryptoController();
        socket = null;
        connect();
    }

    private void connect() {
        try {
            socket = new Socket(IP, PORT);
            new Thread( new ReceivedThread(controller,socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendStr(String str) {
        try {
            String encodedStr = cc.getAesEncodedText(str);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(encodedStr);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }


}
