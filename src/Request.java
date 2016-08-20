
import java.util.HashMap;

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
    
    private Method method;
    private String url;
    private String contetType;
    private Type type;
    
    public HashMap<String, String> headers;
    public HashMap<String, String> parameters;
    
    public Request(String command, String header) {
        headers = new HashMap<>();
        parameters = new HashMap<>();
        
        processCommand(command);
        processHeader(header);
    }

    public void setBody(String body) {
        processBody(body);
    }
    
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getContetType() {
        return contetType;
    }

    public Method getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public Type getType() {
        return type;
    }
    
    private void processHeader(String header) {
        String[] lines = header.split(System.getProperty("line.separator"));
        
        for (String line : lines) {
            String[] temp = line.split(":");
            headers.put(temp[0], temp[1].trim());
        }
    }
    
    private void processBody(String body) {
        processParameters(body);
    }
    
    private void processCommand(String cmd) {
        String[] parts = cmd.split(" ");
        method = Method.valueOf(parts[0]);
        
        processUrl(parts[1]);
        processContentType(url);
    }
    
    private void processUrl(String url) {
        if (url.contains("?")) {
            String[] parts = url.split("\\?");
        
            this.url = parts[0];
            processParameters(parts[1]);
        } else {
            this.url = url;
        }
    }
    
    private void processContentType(String str) {
        String t = str;
        if (str.contains(".")) {
            String[] parts = str.split("\\.");
            t = parts[parts.length - 1];

        } 
        
        if (t.contains("jpg")){
            contetType = "image/jpeg";
            this.type = Type.JPG;
        } else {
            contetType = "text/html";
            this.type = Type.HTML;
        }
    }
    
    private void processParameters(String str) {
        String[] parts = new String[] { str };
                
        if (str.contains("&")) {
            parts = str.split("&");
        }
        
        for (String part : parts) {
            String[] temp = part.split("=");
            String key = temp[0];
            String value = temp.length == 1 ? "" : temp[1];
            value = value.replaceAll("(%20)", " ");
            
            parameters.put(key, value);
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
