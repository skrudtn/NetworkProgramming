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
    private Socket socket;
    private ServerSocket serverSocket;

    ArrayList<Socket> clientList;

    public ServerMain(){
        clientList = new ArrayList<Socket>(0);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void listen(){
        try {
            while(true) {
                System.out.format("서버 대기중\n");
                socket = serverSocket.accept();
                clientList.add(socket);
                new MemberThread(socket, clientList).run();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args){
        new ServerMain().listen();
    }
}
