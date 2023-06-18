package com.example.checkerajavafx;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp extends Application {

    private int serverPort;
    private Socket clientSocket;

    @Override
    public void start(Stage primaryStage) {
        Thread serverThread = new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(serverPort);
                clientSocket = serverSocket.accept();
                System.out.println("Połączono z klientem.");

//                while (true) {
//
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();
    }

    public int getServerPort() {
        return serverPort;
    }


    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
