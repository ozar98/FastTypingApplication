package FastTyping;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuPageController {

    @FXML
    private AnchorPane LPbackground;

    @FXML
    private AnchorPane subpane;

    @FXML
    private Button learnTypingButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem exitMenuID;



    @FXML
    void aboutMenu(ActionEvent event) {
        Alert informationAlert=new Alert(Alert.AlertType.INFORMATION,"Ozar");
        informationAlert.setTitle("About app");
        informationAlert.setHeaderText("Developed by Ozar Aini");
        informationAlert.setContentText("05/18/2021\nCourse: Computer Graphics\nozar.aini_2021@ucentralasia.org\n+992-935012824");
        informationAlert.showAndWait();

    }

    @FXML
    void exitMenu(ActionEvent event) {
        exitMenuID.setOnAction(e -> Platform.exit());


    }

    @FXML
    void goToLessons(ActionEvent event) {
        try{
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("FastApp.fxml"));
            Scene scene = new Scene(root);

            theStage.setScene(scene);
            theStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }


}
