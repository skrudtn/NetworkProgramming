package Control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by skrud on 2017-09-27.
 */
public class ServerMain {
    private final static int PORT = 10001;
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private ArrayList<Socket> clientList;

    private ServerMain() {
        executorService = Executors.newFixedThreadPool(3); // create thread pool
        clientList = new ArrayList<Socket>(0);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        for(;;){
            System.out.format("서버 대기중\n");
            Socket clnt = null;
            try {
                clnt = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            clientList.add(clnt);
            executorService.execute(new MemberThread(clnt,clientList));
            System.out.format("client List\n");
            int index = 0;
            for (Socket s : clientList) {
                System.out.format("%d : %s\n",index,s.getLocalAddress());
                index++;
            }
        }
    }


    public static void main(String[] args) {
        new ServerMain().listen();
    }
}
