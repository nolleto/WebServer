
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabriel
 */
public class WebServer {

    ServerSocket ss;

    public void setUp() {
        try {
            ss = new ServerSocket(8081);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket waitForConnections() {
        try {
            return ss.accept();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Socket Error!");
        }
    }

    public String receiveRequest(Socket s) {
        String command = null;
        String line;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            command = in.readLine();
            while ((line = in.readLine()).length() > 0) {
                continue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return command;
    }

    public byte[] processRequest(String request) {
        System.out.println(request);
        return null;
    }

    public void sendResponse(byte responseData[], Socket s) {        
        try{
            OutputStream out = s.getOutputStream();
            out.write(responseData);
            out.flush();
            out.close();
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}



