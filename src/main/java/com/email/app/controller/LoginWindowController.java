package com.email.app.controller;

import com.email.app.EmailManager;
import com.email.app.controller.services.LoginService;
import com.email.app.model.EmailAccount;
import com.email.app.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {
    @FXML
    private TextField emailAddress;

    @FXML
    private Label errorLabel;
    @FXML
    ProgressIndicator pi;
    @FXML
    Button login_button;

    @FXML
    private TextField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlFile) {
        super(emailManager, viewFactory, fxmlFile);
    }

    @FXML
    void enterPress(KeyEvent keyEvent){
        if (keyEvent.getCode() == KeyCode.ENTER){
            loginButtonAction();
        }
    }

  void   hide(Node node){
      node.setVisible(false);
      node.managedProperty().bind(node.visibleProperty());

    }

    @FXML
    void loginButtonAction() {

        if (fieldsAreValid()){

            EmailAccount emailAccount = new EmailAccount(emailAddress.getText(), passwordField.getText());
            hide(login_button);
            pi.setVisible(true);
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start(); // this will do the background work
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = loginService.getValue();

                switch (emailLoginResult) {
                    case SUCCESS:
                        System.out.println("login successful!!!" + emailAccount);
                        if (!viewFactory.isMainViewIntialized()) {
                            viewFactory.showMainWindow(); // on click, it changes from login to main window
                        }
                        Stage stage = (Stage)errorLabel.getScene().getWindow(); // this gets the window which has errorLabel
                        viewFactory.closeStage(stage); // then this deletes that stage which has errorLabel as a component
                        return;
                    case FAILED_BY_CREDENTIALS:
                        errorLabel.setText("invalid credentials!");
                        hide(pi);
                        login_button.setVisible(true);
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        hide(pi);
                        login_button.setVisible(true);
                        errorLabel.setText("unexpected error!");
                        return;
                    default:
                        return;
                }
            });
        }
    }

    private boolean fieldsAreValid() {
        if(emailAddress.getText().isEmpty()) {
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()) {
            errorLabel.setText("Please fill password");
            return false;
        }

        return true;
    }

    //Is called whenever the controller is called
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAddress.setText("pradumraj98@gmail.com");
        passwordField.setText("ywcdtdygbjiyehyi");

        hide(pi);

      /*  emailAddress.setText("pradumraj98@gmail.com");
        passwordField.setText("cypqkgoaciacuacp");*/
    }
}
