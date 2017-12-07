package Control;

import Model.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-10-01.
 */
public class MemberThread implements Runnable {
    private MainController controller;
    private ClientModel client;
    private Socket socket;
    private ArrayList<ClientModel> clientList;
    private CryptoController cc;

    public MemberThread(ClientModel clientModel, ArrayList<ClientModel> clientList) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        this.client = clientModel;
        this.socket = clientModel.getSocket();
        this.clientList = clientList;
        memberInit();
    }

    private void memberInit() throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        controller = new MainController();
        cc=controller.getCryptoController();
        controller.setClientModel(clientList);

    }

    @Override
    public void run() {
        new Thread( new ReceivedThread(controller,socket, this)).start();
        System.out.format("client List\n");
        int index = 0;
        for (ClientModel c : controller.getClientModels()) {
            System.out.format("%d : %s ", index, c.getSocket().getLocalAddress());
            System.out.println(c.getId());
            index++;
        }

    }

    void sendStr(String str) {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            str = cc.getAesEncodedText(str);
            dos.writeUTF(str);
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

    public ClientModel getClient() {
        return client;
    }

    public ArrayList<ClientModel> getClientList() {
        return clientList;
    }
}
