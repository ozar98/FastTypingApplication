package FastTyping;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.RED;

public class ResultController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label resultHead;

    @FXML
    private Label resultBody;

    @FXML
    private Button playAgainButton;

    @FXML
    private Label speed;

    @FXML
    private Label accuracy;



    @FXML
    private Button homeButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem exitMenuID;
    @FXML
    private Label levelLabel;

    @FXML
    private ImageView resultImage;

    @FXML
    private ImageView gradeImageView;



    private static final AudioClip SUCCESS_AUDIOCLIP = new AudioClip(ResultController.class.getResource("/Audio/applause4.mp3").toString());

    private static final AudioClip FAIL_AUDIOCLIP = new AudioClip(ResultController.class.getResource("/Audio/fail.mp3").toString());

    private static final AudioClip COMPLETE_AUDIOCLIP = new AudioClip(ResultController.class.getResource("/Audio/missionComplete.mp3").toString());



    @FXML
    void goHome(ActionEvent event) {
        try{
            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(root);
            theStage.setScene(scene);
            theStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }
    public void initializeMyData( int wordCount, int errorCount, int totalChar ) throws FileNotFoundException {

        double acc = (double) (100 - (errorCount * 100)/totalChar);

        speed.setText( String.format("%d", wordCount));
        accuracy.setText(String.format("%.1f", acc));



        SUCCESS_AUDIOCLIP.setVolume(2.0);
        FAIL_AUDIOCLIP.setVolume(2.0);
        COMPLETE_AUDIOCLIP.setVolume(2.0);

        if(acc>99.9){
            FileInputStream inputGrade = new FileInputStream("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\Images\\A-plus.jpg");
            Image image=new Image(inputGrade);
            gradeImageView.setImage(image);
            gradeImageView.setFitWidth(150);
            gradeImageView.setFitHeight(118);
            ResultController.SUCCESS_AUDIOCLIP.play();
            levelLabel.setText("Master");
            resultBody.setText("Great Job! You completed the typing test.\nHere are your results.");
        }else if(acc<99.9 & acc>85){
            FileInputStream inputGrade = new FileInputStream("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\Images\\A.png");
            Image image=new Image(inputGrade);
            gradeImageView.setImage(image);
            gradeImageView.setFitWidth(150);
            gradeImageView.setFitHeight(118);
            ResultController.COMPLETE_AUDIOCLIP.play();
            levelLabel.setText("Legendary");
            resultBody.setText("Amazing Job! You completed the typing test.\nHere are your results.");

        }else if(acc<84.9& acc>50){
            FileInputStream inputGrade = new FileInputStream("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\Images\\bgrade.png");
            Image image=new Image(inputGrade);
            gradeImageView.setImage(image);
            gradeImageView.setFitWidth(150);
            gradeImageView.setFitHeight(118);

            ResultController.COMPLETE_AUDIOCLIP.play();
            levelLabel.setText("Average");
            resultBody.setText("Good Job! You completed the typing test.\nHere are your results.");

        }
        else{
            FileInputStream inputGrade = new FileInputStream("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\Images\\f.png");
            Image imageG=new Image(inputGrade);
            gradeImageView.setImage(imageG);
            gradeImageView.setFitWidth(150);
            gradeImageView.setFitHeight(118);

            FileInputStream input = new FileInputStream("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\Images\\fail.png");
            Image image=new Image(input);
            resultImage.setImage(image);
            resultImage.setFitWidth(117);
            resultImage.setFitHeight(89);
            ResultController.FAIL_AUDIOCLIP.play();
            levelLabel.setText("Failing. Please practice more!");
            levelLabel.setTextFill(RED);
            resultBody.setText("Lower than average!\nPlease come try again by practicising on our software");
        }


    }
    @FXML
    void gotoLesson(ActionEvent event) {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        exitMenuID.setOnAction(e -> Platform.exit());

    }
}
