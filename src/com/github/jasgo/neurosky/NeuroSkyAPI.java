package com.github.jasgo.neurosky;

import java.io.IOException;

public class NeuroSkyAPI {
    public static void main(String[] args) {
        ThinkGearClient client = new ThinkGearClient("NodeNeuroSky", "0fc4141b4b45c675cc8d3a765b8d71c5bde9390");
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
