
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WebServer ws = new WebServer();
        ws.setUp();
        while(true){
            Socket s = ws.waitForConnections();
            Request request = ws.receiveRequest(s);
            Response response = null;
            try {
                response = new Response(request);

            } catch (Exception e) {
            }

            if (response == null) {
                response = Response.ServerError();
            }

            ws.sendResponse(response.toBytes(), s);
        }
    }
}
