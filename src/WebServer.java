
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

    public Request receiveRequest(Socket s) {
        Request request = null;
        String command = null;
        StringBuilder header = new StringBuilder();
        StringBuilder body = new StringBuilder();
        String line;
        boolean readingHeader = true;
        boolean appendLine = true;
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            command = in.readLine();
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                if (line.equals("\n")) {
                    readingHeader = false;
                    continue;
                }
                if (readingHeader) {
                    header.append(line);
                    header.append(System.getProperty("line.separator"));
                } else {
                    body.append(line);
                    body.append(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new Request(command, header.toString().trim(), body.toString().trim());
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



