package Control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by skrud on 2017-09-27.
 */
public class ServerMain {
    final static int PORT = 10001;
    private ServerSocket serverSocket;

    ArrayList<Socket> clientList;

    public ServerMain() {
        clientList = new ArrayList<Socket>(0);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            System.out.format("서버 대기중\n");
            Socket clnt = null;
            try {
                clnt = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            clientList.add(clnt);
            new Thread(
                    new MemberThread(clnt, clientList)
            ).start();
            System.out.println("client List");
            for (Socket s : clientList) {
                System.out.println(s.getLocalAddress());
            }
        }
    }


    public static void main(String[] args) {
        new ServerMain().listen();

    }
}
