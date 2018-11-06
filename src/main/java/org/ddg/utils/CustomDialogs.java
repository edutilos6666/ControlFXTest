package org.ddg.utils;


import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;

import java.util.Optional;

public class CustomDialogs {
    public static Optional<ButtonType> showInfoWarningErrorDialog(String headerText, String contentText) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        ButtonType okBtn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okBtn);
        Button ok = ((Button)dialog.getDialogPane().lookupButton(okBtn));
        ok.setDefaultButton(true);
        return dialog.showAndWait();
    }
    public static Optional<ButtonType> showConfirmationDialog(String headerText, String contentText) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        ButtonType okBtn = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, cancelBtn);
        Button cancel = ((Button)dialog.getDialogPane().lookupButton(cancelBtn));
        cancel.setCancelButton(true);
        Button ok = ((Button)dialog.getDialogPane().lookupButton(okBtn));
        ok.setDefaultButton(true);
        cancel.setOnKeyPressed(evt2-> {
            if(evt2.getCode().equals(KeyCode.ENTER)) {
                cancel.fire();
            }
        });
        ok.setOnKeyPressed(evt2-> {
            if(evt2.getCode().equals(KeyCode.ENTER)) {
                ok.fire();
            }
        });
        Optional<ButtonType> answer = dialog.showAndWait();
        return answer;
    }
}
