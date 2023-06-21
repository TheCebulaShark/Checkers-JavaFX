package com.example.checkerajavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class CheckersApp extends Application {
    Menu menu;
    Board board;

    private static GameMode mode;
    private static TwoPlayersOneComputerGame twoPlayersOneComputerGame;

    public enum GameMode {
        TWO_PLAYERS_ONE_COMPUTER,
        MULTIPLAYER,
        PLAYER_VS_COMPUTER
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene game = new Scene(root, GameConstants.APP_WIDTH, GameConstants.APP_HEIGHT);
            initLayout(root);
            primaryStage.setScene(game);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initGame() {
        menu.hideButtons();
        menu.showTimer();
        if (mode == GameMode.TWO_PLAYERS_ONE_COMPUTER) {
            twoPlayersOneComputerGame = new TwoPlayersOneComputerGame(board, menu);
            twoPlayersOneComputerGame.start();
        }
    }

    private void initLayout(BorderPane root) {
        initMenu(root);
        initGameBoard(root);
    }

    private void initMenu(BorderPane root) {
        menu = new Menu();
        menu.setMultiPlayerButtonOnAction(multiPlayerModeChosen());
        menu.setSinglePlayerButtonOnAction(singlePlayerModeChosen());
        menu.setHostMultiButtonOnAction(hostServerChosen());
        menu.setJoinMultiButtonOnAction(joinServerChosen());

        root.getChildren().add(menu.getPane());
    }

    private void initGameBoard(BorderPane root) {
        board = new Board();

        root.getChildren().add(board.getChessboard());
        root.getChildren().add(board.getPiecesGroup());
    }

    private EventHandler<ActionEvent> multiPlayerModeChosen() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menu.multiPlayerMenuInit();
            }
        };
    }

    private EventHandler<ActionEvent> singlePlayerModeChosen() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mode = GameMode.TWO_PLAYERS_ONE_COMPUTER;
                initGame();
            }
        };
    }

    public static void handlePieceClick(Piece piece) {
        if (mode == GameMode.TWO_PLAYERS_ONE_COMPUTER && twoPlayersOneComputerGame != null) {
            twoPlayersOneComputerGame.handlePieceClick(piece);
        }
    }

    public static void handleTileClick(Tile tile) {
        if (mode == GameMode.TWO_PLAYERS_ONE_COMPUTER && twoPlayersOneComputerGame != null) {
            twoPlayersOneComputerGame.handleTileClick(tile);
        }
    }

    private EventHandler<ActionEvent> hostServerChosen() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String port = ServerUtils.generateRandomPort();

                menu.updateText("Port serweru: " + port);
                menu.hideButtons();

                Thread serverThread = new Thread(() -> {
                    ServerApp serverApp = new ServerApp();
                    serverApp.setServerPort(Integer.parseInt(port));
                    serverApp.start(new Stage());
                    System.out.println("Serwer został uruchomiony.");
                });
                serverThread.start();
            }
        };
    }

    private EventHandler<ActionEvent> joinServerChosen() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String serverIP = menu.getServerIP();
                int serverPort = Integer.parseInt(menu.getHostPort());

                Thread clientThread = new Thread(() -> {
                    try {
                        Socket socket = new Socket(serverIP, serverPort);
                        Platform.runLater(() -> {
                            menu.updateText("Pomyślnie połączono");
                        });
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        Platform.runLater(() -> {
                            menu.updateText("Niepoprawny adres IP lub port serwera");
                        });
                    }
                });
                clientThread.start();
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }

    public GameMode getMode() {
        return mode;
    }
}