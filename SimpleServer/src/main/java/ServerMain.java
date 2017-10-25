import controller.ReceptionTask;
import database.SQLiteDatabase;
import datas.RequestMessage;
import exceptions.IdNotFoundException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by PC301 on 25/10/2560.
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {
        String clientSentence;
        String capitalizedSentence;
        SQLiteDatabase db = new SQLiteDatabase();
        ServerSocket welcomeSocket = new ServerSocket(6789);
        try {
            double balance = db.getBalance("5810405185");
            System.out.println("balance = " + balance);
        } catch (IdNotFoundException e) {
            e.printStackTrace();
        }
//        while (true) {
//            System.out.println("Server wait...");
//            Socket connectionSocket = welcomeSocket.accept();
//            Thread thread = new Thread(new ReceptionTask(connectionSocket));
//            thread.start();
//        }
    }
}
