package Control;

import Model.ClientModel;
import Model.SharedData;

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
    private ArrayList<ClientModel> clientList;
    private ServerMain() {
        executorService = Executors.newFixedThreadPool(3); // create thread pool
        clientList = new ArrayList<ClientModel>(0);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        for(;;){
            System.out.format("서버 대기중\n");
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ClientModel client = new ClientModel(socket);
            clientList.add(client);
            executorService.execute(new MemberThread(client,clientList));

        }
    }


    public static void main(String[] args) {
        new ServerMain().listen();
    }
}
