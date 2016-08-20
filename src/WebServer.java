
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

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
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            command = in.readLine();
            System.out.println(command);
            
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                header.append(line);
                header.append(System.getProperty("line.separator"));
                
            }
            
            request = new Request(command, header.toString().trim());
            
            HashMap<String, String> headers = request.getHeaders();
            
            if (headers.containsKey("Content-Length")) {
                int length = Integer.parseInt(headers.get("Content-Length"));
                
                if (length > 0) {
                    int read;
                    while ((read = in.read()) != -1) {
                        body.append((char) read);
                        if (body.length() == length)
                            break;
                    }
                    request.setBody(body.toString());
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return request;
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



