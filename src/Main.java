
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WebServer ws = new WebServer();
        ws.setUp();
        while(true){
            Socket s = ws.waitForConnections();
            String request = ws.receiveRequest(s);
            ws.processRequest(request); 
            Request req = new Request(request);
           
            if (req.type == Request.Type.HTML) {
                String response = getHtml(req.url);
                String data = String.format("%s\n%s", createHeaders(req.contetType, response.length()), response);
                ws.sendResponse(data.getBytes(), s);
                
            } else {
                String response = getImage(req.url);
                String data = String.format("%s\n%s", createHeaders(req.contetType, response.length()), response);
                ws.sendResponse(data.getBytes(), s);
            }
        
        }
    }
    
    
    public static String createHeaders(String contetType, int length) {
        String data = "HTTP/1.1 200 OK\n";
            data +=       "Content-Type: " + contetType + ";\n";
            data +=       "Server: Xulapa 7.2\n";
            data +=       "Connection: close\n";
            data +=       "Content-Length: " +length + "\n";
            
        return data;
    }
    
    public static String getHtml(String file) {
        String result = "";
        if (file == null || file.isEmpty() || file.equals("/")){
            file = "\\home.html";
        }
        
        byte[] content = getFile(file);
        
        if (content != null) {
            try {
                result = new String(content, "UTF-8");
            } catch (Exception e) {
                
            }
        }
            
        return result;
    }
    
    private static byte[] getFile(String file) {
        try {
            File currentDirFile = new File("src" + file);
            String strPath = currentDirFile.getAbsolutePath();
            Path path = Paths.get(strPath);

            FileInputStream fis = new FileInputStream(currentDirFile);
            
            return Files.readAllBytes(path);
        
        } catch (Exception e) {
            
        }
        return null;
    }
    
    public static String getImage(String file) {
        byte[] content = getFile(file);
        
        if (content != null) {
            try {
                return new String(content, "utf-8");
            } catch (Exception e) {
                
            }
        }
            
        return "";
    }
}
