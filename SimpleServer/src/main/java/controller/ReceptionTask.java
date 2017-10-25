package controller;

import datas.RequestMessage;
import exceptions.BadRequestException;
import exceptions.BtagNotSupportException;
import exceptions.VersionNotSupportException;
import parser.Parser;
import tokens.RequestToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by PC301 on 25/10/2560.
 */
public class ReceptionTask implements Runnable {
    private Socket connectionSocket;

    public ReceptionTask(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            RequestMessage requestMessage = translate(inFromClient);

//            String capitalizedSentence = clientSentence.toUpperCase() + '\n';
//            outToClient.writeBytes(capitalizedSentence);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (BtagNotSupportException e) {
            e.printStackTrace();
        } catch (VersionNotSupportException e) {
            e.printStackTrace();
        }

    }

    private RequestMessage translate(BufferedReader reader) throws BtagNotSupportException, BadRequestException, VersionNotSupportException, IOException {
        Parser parser = new Parser();
        RequestMessage requestMessage = parser.parseToRequest(reader);
        return requestMessage;
    }

    private void perform(RequestMessage message){
        if (RequestToken.Method.DEPOSIT.equalsIgnoreCase(message.getMethod()))
            deposit(message);
        if (RequestToken.Method.WITHDRAW.equalsIgnoreCase(message.getMethod()))
            withdraw(message);
        if (RequestToken.Method.BALANCE.equalsIgnoreCase(message.getMethod()))
            checkBalance(message);

    }

    private void withdraw(RequestMessage message) {

    }

    private void deposit(RequestMessage message){

    }

    private void checkBalance(RequestMessage message){

    }
}
