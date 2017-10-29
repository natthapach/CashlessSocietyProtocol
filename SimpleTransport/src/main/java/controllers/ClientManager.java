package controllers;

import btags.body.Transport;
import btags.parser.TransportParser;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientManager {
    private Parser parser = new Parser();
    private String agent = "Simple transport";
    private String sid = "0001";
    private String url = "localhost";
    private SimpleDateFormat formatter = new SimpleDateFormat(GeneralToken.TIME_FORMAT);

  public double getBalance(String id) throws IdNotFoundException {
      try {
          Socket clientSocket = new Socket(url, GeneralToken.DEFAULT_PORT);
          DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
          BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

          RequestMessage requestMessage = new RequestMessage(RequestToken.Method.BALANCE,
                  GeneralToken.CURRENT_VERSION,
                  id,
                  sid,
                  formatter.format(new Date()),
                  0,
                  agent,
                  null, null);
          String msg = parser.parseToString(requestMessage);
          System.out.println("---message to server---");
          System.out.println(msg);
          outToServer.writeBytes(msg);
          ResponseMessage responseMessage = parser.parseToResponse(inFromServer);
          clientSocket.close();

          System.out.println("responseMessage.getStatusCode() = " + responseMessage.getStatusCode());

          if (responseMessage.getStatusCode() == ResponseToken.Status.C400)
              throw new BadRequestException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C404)
              throw new IdNotFoundException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C505)
              throw new VersionNotSupportException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C506)
              throw new BtagNotSupportException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C200)
              return responseMessage.getBalance();
      } catch (BtagNotSupportException e) {
          e.printStackTrace();
      } catch (VersionNotSupportException e) {
          e.printStackTrace();
      } catch (BadRequestException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return 0;
  }

  public double pay(String id, String source, String destination, double price) throws IdNotFoundException, InsufficientFundException {
      try {
          Socket clientSocket = new Socket(url, GeneralToken.DEFAULT_PORT);
          DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
          BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

          Transport body = new Transport(source, destination);
          RequestMessage requestMessage = new RequestMessage(RequestToken.Method.PAY,
                  GeneralToken.CURRENT_VERSION,
                  id,
                  sid,
                  formatter.format(new Date()),
                  price,
                  agent,
                  TransportParser.BTAG_NAME, body);
          String msg = parser.parseToString(requestMessage);
          System.out.println("---message to server---");
          System.out.println(msg);
          outToServer.writeBytes(msg);
          ResponseMessage responseMessage = parser.parseToResponse(inFromServer);
          clientSocket.close();

          if (responseMessage.getStatusCode() == ResponseToken.Status.C400)
              throw new BadRequestException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C404)
              throw new IdNotFoundException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C505)
              throw new VersionNotSupportException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C506)
              throw new BtagNotSupportException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C418)
              throw new InsufficientFundException();
          if (responseMessage.getStatusCode() == ResponseToken.Status.C200)
              return responseMessage.getBalance();
      } catch (BtagNotSupportException e) {
          e.printStackTrace();
      } catch (VersionNotSupportException e) {
          e.printStackTrace();
      } catch (BadRequestException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return 0;
  }
}
