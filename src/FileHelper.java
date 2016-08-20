
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe
 */
public class FileHelper {
    
    public static byte[] concatBytes(byte[] a, byte[] b) {
        if (a.length == 0 && b.length == 0) return a;
        else if (a.length == 0) return b;
        else if (b.length == 0) return a;
        
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
    
    public static byte[] errorHeaders() {
        String data = "HTTP/1.1 500 OK\n";
            data +=       "Content-Type: text/html;\n";
            data +=       "Server: Xulapa 7.2\n";
            data +=       "Connection: close\n";
            data +=       "Content-Length: 0\n\n";
         
        return data.getBytes();
    }
    
    public static byte[] createHeaders(String contetType, int length) {
        String data = "HTTP/1.1 200 OK\n";
            data +=       "Content-Type: " + contetType + ";\n";
            data +=       "Server: Xulapa 7.2\n";
            data +=       "Connection: close\n";
            data +=       "Content-Length: " +length + "\n\n";
         
        return data.getBytes();
    }
    
    public static byte[] getHtml(String file) {
        String result = "";
        if (file == null || file.isEmpty() || file.equals("/")){
            file = "\\home.html";
        }
        
        return getFile(file);
    }
    
    public static byte[] getFile(String file) {
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
}
