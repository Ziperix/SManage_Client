package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.*;

import helpers.API;
import helpers.Config;
import helpers.User;


public class LoginWindowController implements Initializable
{
    @FXML
    private PasswordField passwordInput;
    @FXML
    private TextField usernameInput;
    @FXML
    private Button loginButton;

    private double xMouse = 0;
    private double yMouse = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        try
        {
            Config a = new Config();
            if(a.getErrorStatus())
            {
                new Alert(Alert.AlertType.ERROR, "IOError, please restart SManage as administrator").showAndWait();
            }
        }
        catch (FileNotFoundException e)
        {
            //Not going to happen - handled by ConfigHelper class
        }
    }

    public void MousePressed(MouseEvent mouseEvent)
    {
        xMouse = mouseEvent.getSceneX();
        yMouse = mouseEvent.getSceneY();
    }

    public void MouseDragged(MouseEvent mouseEvent)
    {
        Stage currentStage = (Stage)loginButton.getScene().getWindow();
        currentStage.setX(mouseEvent.getScreenX() - xMouse);
        currentStage.setY(mouseEvent.getScreenY() - yMouse);
    }

    public void ConfigButtonClicked(ActionEvent actionEvent) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LConfigWindow.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Configuration");
        stage.setScene(scene);
        stage.show();
    }

    public void ExitImageButtonClicked(MouseEvent event)
    {
        System.exit(0);
    }

    public void MinImageButtonClicked(MouseEvent event)
    {
        Stage currentStage = (Stage)loginButton.getScene().getWindow();
        currentStage.setIconified(true);
    }


    public void LoginButtonClicked(ActionEvent actionEvent) throws IOException
    {
        String usernameToLog = usernameInput.getText();
        String passwordToLog = passwordInput.getText();
        JSONObject res = API.ApiLogin(usernameToLog, passwordToLog);
        if(res.getString("code").equals("S2"))
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HomeWindow.fxml"));
            Parent root = loader.load();
            HomeWindowController homeWindowController = loader.getController();
            homeWindowController.PassUser(new User(res.getString("token")));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("SManage Home");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage)loginButton.getScene().getWindow();
            currentStage.close();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.format("%s: %s", res.getString("code"), res.getString("reason")));
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }
}
