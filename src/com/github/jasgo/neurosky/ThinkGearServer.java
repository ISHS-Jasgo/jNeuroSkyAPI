package com.github.jasgo.neurosky;

import dk.thibaut.serial.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ThinkGearServer extends Thread {

    public static Socket socket;

    public ThinkGearServer(Socket socket) {
        ThinkGearServer.socket = socket;
    }

    public static void main(String[] args) {
        List<String> portNames = SerialPort.getAvailablePortsNames();
        portNames.forEach(System.out::println);
        try {
            ServerSocket server = new ServerSocket(13854);
            while (true) {
                Socket socket = server.accept();
                Thread thread = new ThinkGearServer(socket);
                thread.start();
                new ListeningThread().start();
                System.out.println("사용자가 접속했습니다");
            }
        } catch (IOException ignored){}
    }
    public static class ListeningThread extends Thread {
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String readValue;
                while ((readValue = reader.readLine()) != null) {
                    System.out.println(readValue);
                }
            } catch (IOException ignored) {
            }
        }
    }
}
