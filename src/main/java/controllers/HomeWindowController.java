package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import helpers.User;


public class HomeWindowController implements Initializable
{
    @FXML
    private MenuBar topBar;

    @FXML
    private Button studentButton, teacherButton;

    private Boolean resizePoint = false;
    private double xMouseMove;
    private double yMouseMove;
    private double xMouseResize;
    private double yMouseResize;
    private User user;

    public void PassUser(User passedUser)
    {
        user = passedUser;
    }

    public void MousePressed(MouseEvent mouseEvent)
    {
        Stage stage = (Stage)teacherButton.getScene().getWindow();
        if (mouseEvent.getX() > stage.getWidth() - 23
                && mouseEvent.getX() < stage.getWidth() + 23
                && mouseEvent.getY() > stage.getHeight() - 23
                && mouseEvent.getY() < stage.getHeight() + 23)
        {
            resizePoint = true;
            xMouseMove = stage.getWidth() - mouseEvent.getX();
            yMouseMove = stage.getHeight() - mouseEvent.getY();
        }
        else
        {
            resizePoint = false;
            xMouseResize = mouseEvent.getSceneX();
            yMouseResize = mouseEvent.getSceneY();
        }
    }

    public void MouseDragged(MouseEvent mouseEvent)
    {
        Stage currentStage = (Stage)teacherButton.getScene().getWindow();
        if (!resizePoint) {
            currentStage.setX(mouseEvent.getScreenX() - xMouseResize);
            currentStage.setY(mouseEvent.getScreenY() - yMouseResize);
        } else {
            currentStage.setWidth(mouseEvent.getX() + xMouseMove);
            currentStage.setHeight(mouseEvent.getY() + yMouseMove);
        }
    }

    public void ExitImageButtonClicked(MouseEvent event)
    {
        System.exit(0);
    }

    public void MinImageButtonClicked(MouseEvent event)
    {
        Stage currentStage = (Stage)teacherButton.getScene().getWindow();
        currentStage.setIconified(true);
    }

    private double oldSizeX, oldSizeY, oldX, oldY;
    private boolean maximized = false;

    public void MaxImageButtonClicked(MouseEvent event)
    {
        Stage currentStage = (Stage)teacherButton.getScene().getWindow();
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if(!maximized)
        {
            oldSizeX = currentStage.getWidth();
            oldSizeY = currentStage.getHeight();
            oldY = currentStage.getY();
            oldX = currentStage.getX();
            currentStage.setX(bounds.getMinX());
            currentStage.setY(bounds.getMinY());
            currentStage.setWidth(bounds.getWidth());
            currentStage.setHeight(bounds.getHeight());
            maximized = true;
        }
        else
        {
            currentStage.setY(oldY);
            currentStage.setX(oldX);
            currentStage.setWidth(oldSizeX);
            currentStage.setHeight(oldSizeY);
            maximized = false;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }
}
