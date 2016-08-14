/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Felipe
 */
public class Request {
    
    public Method method;
    public String url;
    public String contetType;
    public Type type;
    
    public Request(String header) {
        String[] parts = header.split(" ");
        method = Method.valueOf(parts[0]);
        url = parts[1];
        
        if (url.contains(".jpg")){
            contetType = "image/jpeg";
            type = Type.JPG;
        } else {
            contetType = "text/html";
            type = Type.HTML;
        }
    }
    
    public enum Method {
        POST,
        GET,
        PUT,
        PATCH,
        DELETE
    }
    
    public enum Type {
        HTML,
        JPG
    }
}
