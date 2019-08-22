package controllers;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import helpers.Config;
import helpers.API;


public class LConfigWindowController implements Initializable
{

    @FXML
    private TextField addressInput, portInput;

    @FXML
    private Button cancelButton;

    private Config configHelper = new Config();
    private double xMouseResize;
    private double yMouseResize;

    public LConfigWindowController() throws FileNotFoundException { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        addressInput.setText(configHelper.getAddress());
        portInput.setText(configHelper.getPort());
    }

    public void SaveButtonClicked(ActionEvent actionEvent) throws FileNotFoundException
    {
        configHelper.ReplaceConfig("Address", addressInput.getText());
        configHelper.ReplaceConfig("Port", portInput.getText());
        if(configHelper.getErrorStatus())
        {
            new Alert(Alert.AlertType.ERROR, "Error when saving to configuration file.").showAndWait();
        }
        else
        {
            new Alert(Alert.AlertType.INFORMATION, "Configuration file updated.").showAndWait();
        }
    }

    public void MousePressed(MouseEvent mouseEvent)
    {
        xMouseResize = mouseEvent.getSceneX();
        yMouseResize = mouseEvent.getSceneY();
    }

    public void MouseDragged(MouseEvent mouseEvent)
    {
        Stage currentStage = (Stage)cancelButton.getScene().getWindow();
        currentStage.setX(mouseEvent.getScreenX() - xMouseResize);
        currentStage.setY(mouseEvent.getScreenY() - yMouseResize);
    }

    public void ExitImageButtonClicked(MouseEvent event)
    {
        Stage currentStage = (Stage)cancelButton.getScene().getWindow();
        currentStage.close();
    }

    public void MinImageButtonClicked(MouseEvent event)
    {
        Stage currentStage = (Stage)cancelButton.getScene().getWindow();
        currentStage.setIconified(true);
    }

    public void CancelButtonClicked(ActionEvent actionEvent)
    {
        Stage currentStage = (Stage)cancelButton.getScene().getWindow();
        currentStage.close();
    }

    public void TestButtonClicked(ActionEvent actionEvent)
    {
        if(API.TestConnection(addressInput.getText(), portInput.getText()))
        {
            new Alert(Alert.AlertType.INFORMATION, "Connection successful.").showAndWait();
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "Connection unsuccessful").showAndWait();
        }
    }
}
