package com.example.checkerajavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static com.example.checkerajavafx.GameConstants.*;

public class Menu {
    private StackPane pane;
    private Label text;
    private Button singlePlayerButton;
    private Button multiPlayerButton;
    private Button hostMultiButton;
    private Button joinMultiButton;
    private TextField hostPortField;
    private TextField serverIPAddress;
    private Label timer;


    public Menu() {
        paneInit();
        textInit();
        buttonsInit();
        timerInit();
    }

    public void paneInit() {
        pane = new StackPane();
        pane.setMinSize(MENU_WIDTH, MENU_HEIGHT);
        pane.setTranslateX((double) MENU_WIDTH / 2);
        pane.setTranslateY((double) MENU_HEIGHT / 2);
    }

    public void textInit() {
        text = new Label("Wybierz tryb");
        text.setMinSize(MENU_WIDTH, MENU_HEIGHT);
        text.setFont(Font.font(TEXT_FONT_SIZE));
        text.setAlignment(Pos.CENTER);
        text.setTranslateY(TEXT_OFFSET_Y);
        pane.getChildren().add(text);
    }

    public void timerInit() {
        timer = new Label("0 s");
        timer.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        timer.setFont(Font.font(TEXT_FONT_SIZE));
        timer.setAlignment(Pos.CENTER);
        timer.setTranslateX((double)((MENU_WIDTH - BUTTONS_WIDTH) / 2) - BUTTONS_OFFSET_X);
        timer.setTranslateY(BUTTONS_OFFSET_Y);
        timer.setVisible(false);
        pane.getChildren().add(timer);
    }

    public void buttonsInit() {
        singlePlayerButton = new Button("Jeden Gracz");
        singlePlayerButton.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        singlePlayerButton.setTranslateX(BUTTONS_OFFSET_X + (double)((BUTTONS_WIDTH - MENU_WIDTH) / 2));
        singlePlayerButton.setTranslateY(BUTTONS_OFFSET_Y);
        pane.getChildren().add(singlePlayerButton);

        multiPlayerButton = new Button("Dwóch Graczy");
        multiPlayerButton.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        multiPlayerButton.setTranslateX((double)((MENU_WIDTH - BUTTONS_WIDTH) / 2) - BUTTONS_OFFSET_X);
        multiPlayerButton.setTranslateY(BUTTONS_OFFSET_Y);
        pane.getChildren().add(multiPlayerButton);

        hostMultiButton = new Button("Hostuj grę");
        hostMultiButton.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        hostMultiButton.setTranslateX(BUTTONS_OFFSET_X + (double)((BUTTONS_WIDTH - MENU_WIDTH) / 2));
        hostMultiButton.setTranslateY(BUTTONS_OFFSET_Y);
        hostMultiButton.setVisible(false);
        pane.getChildren().add(hostMultiButton);

        joinMultiButton = new Button("Dołącz do Gry");
        joinMultiButton.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        joinMultiButton.setTranslateX((double)((MENU_WIDTH - BUTTONS_WIDTH) / 2) - BUTTONS_OFFSET_X);
        joinMultiButton.setTranslateY(BUTTONS_OFFSET_Y);
        joinMultiButton.setVisible(false);
        pane.getChildren().add(joinMultiButton);

        hostPortField = new TextField();
        hostPortField.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        hostPortField.setTranslateX(joinMultiButton.getTranslateX() - BUTTONS_WIDTH - 15);
        hostPortField.setTranslateY(BUTTONS_OFFSET_Y);
        hostPortField.setVisible(false);
        hostPortField.setDisable(true);
        pane.getChildren().add(hostPortField);

        serverIPAddress = new TextField();
        serverIPAddress.setMinSize(BUTTONS_WIDTH, BUTTONS_HEIGHT);
        serverIPAddress.setTranslateX(joinMultiButton.getTranslateX() - (BUTTONS_WIDTH + 15) * 2);
        serverIPAddress.setTranslateY(BUTTONS_OFFSET_Y);
        serverIPAddress.setVisible(false);
        serverIPAddress.setDisable(true);
        pane.getChildren().add(serverIPAddress);
    }

    public void multiPlayerMenuInit() {
        singlePlayerButton.setVisible(false);
        multiPlayerButton.setVisible(false);
        hostMultiButton.setVisible(true);
        joinMultiButton.setVisible(true);
        hostPortField.setVisible(true);
        hostPortField.setDisable(false);
        serverIPAddress.setVisible(true);
        serverIPAddress.setDisable(false);
        updateText("Hostuj grę, lub podaj IP i port istniejącej już gry");
    }

    public void hideButtons() {
        singlePlayerButton.setVisible(false);
        multiPlayerButton.setVisible(false);
        hostMultiButton.setVisible(false);
        joinMultiButton.setVisible(false);
        hostPortField.setVisible(false);
        serverIPAddress.setVisible(false);
    }

    public void hideTimer() {
        timer.setVisible(false);
    }
    public void showTimer() {
        timer.setVisible(true);
    }

    public String getHostPort() {
        return hostPortField.getText();
    }

    public String getServerIP() {
        return serverIPAddress.getText();
    }

    public void setSinglePlayerButtonOnAction(EventHandler<ActionEvent> onAction) {
        singlePlayerButton.setOnAction(onAction);
    }

    public void setMultiPlayerButtonOnAction(EventHandler<ActionEvent> onAction) {
        multiPlayerButton.setOnAction(onAction);
    }

    public void setHostMultiButtonOnAction(EventHandler<ActionEvent> onAction) {
        hostMultiButton.setOnAction(onAction);
    }

    public void setJoinMultiButtonOnAction(EventHandler<ActionEvent> onAction) {
        joinMultiButton.setOnAction(onAction);
    }

    public void updateText(String text) {
        this.text.setText(text);
    }

    public void updateTimer(String text) {
        this.timer.setText(text);
    }

    public StackPane getPane() {
        return pane;
    }
}
