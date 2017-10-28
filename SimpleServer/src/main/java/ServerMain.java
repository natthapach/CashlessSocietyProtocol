import controller.ReceptionTask;
import database.SQLiteDatabase;
import datas.RequestMessage;
import exceptions.IdNotFoundException;
import exceptions.InsufficientFundException;
import tokens.GeneralToken;

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
        SQLiteDatabase db = new SQLiteDatabase();
        ServerSocket welcomeSocket = new ServerSocket(GeneralToken.DEFAULT_PORT);
        while (true) {
            System.out.println("Server wait...");
            Socket connectionSocket = welcomeSocket.accept();
            Thread thread = new Thread(new ReceptionTask(connectionSocket, db));
            thread.start();
        }
    }
}
