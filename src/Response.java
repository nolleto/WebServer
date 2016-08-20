/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe
 */
public class Response {
    private byte[] header;
    private byte[] body;
    
    private Request request;
    
    public static Response ServerError() {
        return new Response(null);
    }
    
    public Response(Request request) {
        this.request = request;
        this.header = new byte[0];
        this.body = new byte[0];
        
        if (request == null) {
            processHeader();
            return;
        }
        
        switch (request.getType()) {
            case JPG:
                body = FileHelper.getFile(request.getUrl());
                break;
            default:
                body = FileHelper.getHtml(request.getUrl());
                break;
        }
        processHeader();
    }
    
    private void processHeader() {
        String data = "";
        
        if (body != null) {
            data = "HTTP/1.1 200 OK\n";
            data +=       "Content-Type: " + request.getContetType() + ";\n";
            data +=       "Server: Xulapa 7.2\n";
            data +=       "Connection: close\n";
            data +=       "Content-Length: " + body.length + "\n\n";
        } else {
            data = "HTTP/1.1 500 OK\n";
            data +=       "Content-Type: text/html;\n";
            data +=       "Server: Xulapa 7.2\n";
            data +=       "Connection: close\n";
            data +=       "Content-Length: 0\n\n";
        }
        
        header = data.getBytes();
    }
    
    public byte[] toBytes() {
        if (body == null) body = new byte[0];
        return FileHelper.concatBytes(header, body);
    }
}
