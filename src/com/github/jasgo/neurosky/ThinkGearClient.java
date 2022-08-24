package com.github.jasgo.neurosky;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ThinkGearClient {
    private final String name;
    private final String key;
    private final String host;
    private final int port;

    public ThinkGearClient(String name, String key) {
        this.name = name;
        this.key = key;
        this.host = "localhost";
        this.port = 13854;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
    public JSONObject getAuth() {
        JSONObject result = new JSONObject();
        result.put("appName", name);
        result.put("appKey", key);
        return result;
    }
    public JSONObject getConfig() {
        JSONObject result = new JSONObject();
        result.put("enableRawOutput", false);
        result.put("format", "Json");
        return result;
    }
    public void connect() throws IOException {
        System.out.println(getAuth().toJSONString());
        System.out.println(getConfig().toJSONString());
        Socket socket = new Socket(host, port);
        Socket server = new Socket(host, 3000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        PrintWriter serverWriter = new PrintWriter(server.getOutputStream(), true, StandardCharsets.UTF_8);
        String value;
        boolean configSent = false;
        writer.println(getAuth().toJSONString());
        while ((value = reader.readLine()) != null) {
            if (!configSent) {
                configSent = true;
                writer.println(getConfig().toJSONString());
            } else {
                System.out.println(value);
                serverWriter.println(value);
            }
        }
    }
}
