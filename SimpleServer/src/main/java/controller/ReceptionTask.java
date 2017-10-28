package controller;

import btags.body.Status404;
import btags.parser.Status404Parser;
import database.AccountManager;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.*;
import parser.Parser;
import tokens.GeneralToken;
import tokens.RequestToken;
import tokens.ResponseToken;

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
    private AccountManager accountManager;
    private Parser parser;

    public ReceptionTask(Socket connectionSocket, AccountManager accountManager) {
        this.connectionSocket = connectionSocket;
        this.accountManager = accountManager;
        parser = new Parser();
    }

    public void run() {
        System.out.println("Thread running");
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            RequestMessage requestMessage = translate(inFromClient);
            perform(requestMessage);

            ResponseMessage responseMessage = new ResponseMessage(ResponseToken.Status.S200, GeneralToken.CURRENT_VERSION, requestMessage.getUserId(), requestMessage.getTimeStamp(), null,accountManager.getBalance(requestMessage.getUserId()), null);
            String response = parser.parseToString(responseMessage);
            System.out.println("--message to client--");
            System.out.println(response);
            outToClient.writeBytes(response);

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
        } catch (IdNotFoundException e) {
            try {
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                Status404 body = new Status404(e.getMissings());
                ResponseMessage responseMessage = new ResponseMessage(ResponseToken.Status.S404,
                                                                        GeneralToken.CURRENT_VERSION,
                                                                        e.getUid(), e.getTs(), Status404Parser.BTAG_NAME, 0, body);
                String response = parser.parseToString(responseMessage);
                System.out.println("--message to client--");
                System.out.println(response);
                outToClient.writeBytes(response);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (BtagNotSupportException e1) {
                e1.printStackTrace();
            }
        } catch (InsufficientFundException e) {
            try {
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                ResponseMessage responseMessage = new ResponseMessage(ResponseToken.Status.S418,
                        GeneralToken.CURRENT_VERSION,
                        e.getUid(), e.getTs(), null, 0, null);
                String response = parser.parseToString(responseMessage);
                System.out.println("--message to client--");
                System.out.println(response);
                outToClient.writeBytes(response);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (BtagNotSupportException e1) {
                e1.printStackTrace();
            }
        }

    }

    private RequestMessage translate(BufferedReader reader) throws BtagNotSupportException, BadRequestException, VersionNotSupportException, IOException {
        Parser parser = new Parser();
        RequestMessage requestMessage = parser.parseToRequest(reader);
        return requestMessage;
    }

    private void perform(RequestMessage message) throws IdNotFoundException, InsufficientFundException {
            if (RequestToken.Method.DEPOSIT.equalsIgnoreCase(message.getMethod()))
                deposit(message);
            if (RequestToken.Method.WITHDRAW.equalsIgnoreCase(message.getMethod()))
                withdraw(message);
            if (RequestToken.Method.BALANCE.equalsIgnoreCase(message.getMethod()))
                checkBalance(message);
            if (RequestToken.Method.TRANSFER.equalsIgnoreCase(message.getMethod()))
                transfer(message);
//            System.out.println("balance = " + accountManager.getBalance(message.getUserId()));

    }
    private void transfer(RequestMessage message) throws InsufficientFundException, IdNotFoundException {
        System.out.println("request to transfer");
        try {
            accountManager.withdraw(message.getUserId(), message.getAmount());
        } catch (IdNotFoundException e) {
            e.setUid(message.getUserId());
            e.setTs(message.getTimeStamp());
            throw e;
        } catch (InsufficientFundException e){
            e.setUid(message.getUserId());
            e.setTs(message.getTimeStamp());
            throw e;
        }

        try {
            accountManager.deposit(message.getServiceId(), message.getAmount());
        } catch (IdNotFoundException e) {
            System.err.println("Transfer target not found!");
            accountManager.deposit(message.getUserId(), message.getAmount());
            e.setUid(message.getServiceId());
            e.setTs(message.getTimeStamp());
            throw e;
        }
    }
    private void withdraw(RequestMessage message) throws InsufficientFundException, IdNotFoundException {
        try {
            System.out.println("request to withdraw");
            accountManager.withdraw(message.getUserId(), message.getAmount());
        }  catch (IdNotFoundException e) {
            e.setUid(message.getUserId());
            e.setTs(message.getTimeStamp());
            throw e;
        } catch (InsufficientFundException e){
            e.setUid(message.getUserId());
            e.setTs(message.getTimeStamp());
            throw e;
        }
    }

    private void deposit(RequestMessage message) throws IdNotFoundException {
        try {
            System.out.println("request to deposit");
            accountManager.deposit(message.getUserId(), message.getAmount());
        } catch (IdNotFoundException e) {
            e.setUid(message.getUserId());
            e.setTs(message.getTimeStamp());
            throw e;
        }
    }

    private void checkBalance(RequestMessage message){
        System.out.println("request to check balance");
    }
}
