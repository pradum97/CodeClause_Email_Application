package com.email.app.controller;

import com.email.app.EmailManager;
import com.email.app.controller.services.EmailSenderService;
import com.email.app.model.EmailAccount;
import com.email.app.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComposeMessageController extends BaseController implements Initializable {

    private List<File> attachments = new ArrayList<File>();

    @FXML
    private Label errorLabel;
    @FXML
    Button sendBn;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private TextField recipientTextField;

    @FXML
    private TextField subjectTextField;

    @FXML
    ProgressIndicator pi;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoice;

    @FXML
    void attachBtnAction() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            attachments.add(selectedFile);
        }
    }

    void  hide(Node node){
        node.setVisible(false);
        node.managedProperty().bind(node.visibleProperty());

    }

    @FXML
    void sendButtonAction() {

        String recipient = recipientTextField.getText();

        if (recipient.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR){};
            alert.setContentText("Please enter recipient email");
            alert.show();
            return;
        }


        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoice.getValue(),
                subjectTextField.getText(),
                recipientTextField.getText(),
                htmlEditor.getHtmlText(),
                attachments
        );

        System.out.println("1");
        hide(sendBn);
        pi.setVisible(true);
        emailSenderService.start();

        System.out.println("2");

        emailSenderService.setOnSucceeded(e->{
            System.out.println("3");
            EmailSendingResult emailSendingResult = emailSenderService.getValue();
            hide(pi);
            sendBn.setVisible(true);
            switch (emailSendingResult){
                case SUCCESS:

                    Stage stage = (Stage) recipientTextField.getScene().getWindow();
                    viewFactory.closeStage(stage);
                    break;
                case FAILED_BY_PROVIDER:
                    errorLabel.setText("Provider error!!");
                    break;
                case FAILED_BY_UNEXPECTED_ERROR:
                    errorLabel.setText("Unexpected error");
                    break;
            }
        });
    }
    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFile) {
        super(emailManager, viewFactory, fxmlFile);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAccountChoice.setItems(emailManager.getEmailAccounts());
        emailAccountChoice.setValue(emailManager.getEmailAccounts().get(0));
        hide(pi);
    }
}
