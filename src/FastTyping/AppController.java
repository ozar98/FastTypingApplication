package FastTyping;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.paint.Color.RED;

/**
 * FXML Controller class
 *
 * @author Anay
 */
public class AppController implements Initializable {
    @FXML
    private Button refreshButton;

    @FXML
    private ChoiceBox<String> lessonChoiceBox;

    @FXML
    private Button startButton;
    @FXML
    private Button abortButton;

    @FXML
    private Label timeLabel;



    @FXML
    private TextArea textDisplay;

    @FXML
    private Button resultButton;

    @FXML
    private Button timerButton;
    @FXML
    private RadioButton russianRadioButton;

    @FXML
    private ToggleGroup toggleLanguageGroup;

    @FXML
    private RadioButton englishRadioButton;

    @FXML
    private RadioButton easyRadioButton;

    @FXML
    private ToggleGroup toggleDifficultyGroup;

    @FXML
    private RadioButton tajikRadioButton;

    @FXML
    private RadioButton kyrgyzRadioButton;

    @FXML
    private RadioButton mediumRadioButton;

    @FXML
    private RadioButton difficultRadioButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem exitMenuID;
    @FXML
    private Label timeRemain;
    int millisB=0; int secsB=0;

    private static final AudioClip ALERT_AUDIOCLIP = new AudioClip(AppController.class.getResource("/Audio/alert.mp3").toString());

    private static final AudioClip TYPING_AUDIOCLIP = new AudioClip(AppController.class.getResource("/Audio/typing.wav").toString());
    //progress tracking variables

    private int errorCount;

    private int totalChar;

    private char expectedKey;

    private char typedKey;

    int indexOfLine=0;

    String arr="";

    int wordCount=0;

    //Timer variables
    Timeline timeline;

    int mins = 0, secs = 0, millis = 0;

    boolean sos = true;

    boolean timerStarted = false;

    @FXML
    private Button exitButton;


    @FXML
    void goToMainPage(ActionEvent event) {
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

    @FXML
    void switchSceneToResult(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Result.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            ResultController controller = loader.getController();


            controller.initializeMyData( ++wordCount,  errorCount,  totalChar );

            Stage theStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            theStage.setScene(scene);
            theStage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }



    @FXML
    void restartTest(ActionEvent event){

        textDisplay.setText("");
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
    void exit(ActionEvent event) {
        exitButton.setOnAction(e -> Platform.exit());

    }



    void loadTest(){
        try{
            BufferedReader reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
            String line;
            while((line = reader.readLine()) != null){
                textDisplay.setText( textDisplay.getText() + line);
                arr += line;
            }
            reader.close();
            textDisplay.requestFocus();
            errorCount =0; indexOfLine=0; wordCount = 0; totalChar = arr.length();

            AppController.ALERT_AUDIOCLIP.setRate(2.0);
            AppController.TYPING_AUDIOCLIP.setVolume(1.0);

            textDisplay.setStyle("-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
            textDisplay.selectRange(indexOfLine, indexOfLine+1);

            textDisplay.setOnKeyTyped( new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(!timerStarted){
                        timerStarted = true;
                        timerButton.fire();
                    }

                    expectedKey = arr.charAt(indexOfLine);
                    //System.out.println(expectedKey);
                    typedKey = event.getCharacter().charAt(0);
                    if(indexOfLine < arr.length()){
                        if(typedKey != expectedKey){
                            AppController.ALERT_AUDIOCLIP.play();
                            errorCount++;
                            indexOfLine++;
                            textDisplay.setStyle("-fx-background-color: #ffcdd2;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                            textDisplay.selectRange(indexOfLine, indexOfLine+1);
                        }
                        else{
                            if(typedKey == ' ')
                                wordCount++;
                            AppController.TYPING_AUDIOCLIP.play();
                            indexOfLine++;
                            textDisplay.setStyle("-fx-background-color: white;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                            textDisplay.selectRange(indexOfLine, indexOfLine+1);
                        }

                    }
                    if(indexOfLine == arr.length()){
                        timerButton.fire();
                        resultButton.fire();
                    }
                }
            });

        }catch( IOException ex){
            ex.printStackTrace();
        }
    }
    //method to change the text of the Elapsed Time label.
    void change(){

        if(millis == 1000) {
            secs++;
            millis = 0;
        }
        if(secs == 60) {
            mins++;
            secs = 0;
        }
        timeLabel.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs);
        millis++;
        if(timeLabel.getText().equals("00:50")){
            timeLabel.setTextFill(RED);

        }
        if(timeLabel.getText().equals("01:00")){
            timerButton.fire();
            resultButton.fire();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        exitButton.setOnAction(e -> Platform.exit());
        exitMenuID.setOnAction(e -> Platform.exit());

        lessonChoiceBox.getItems().addAll( "Demo", "Lesson 1", "Lesson 2", "Lesson 3", "Lesson 4","Lesson 5", "Lesson 6", "Lesson 7", "Lesson 8", "Lesson 9", "Lesson 10", "Lesson 11", "Lesson 12", "Lesson 13", "Lesson 14", "Lesson 15", "Common Words");
        lessonChoiceBox.getSelectionModel().select("Demo");

        //Initially Elapsed time is set to 0.
        timeLabel.setText("00:00");

        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                change();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        //Action to be performed by timer button on firing.
        timerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(sos) {
                    timeline.play();
                    sos = false;
                    timerButton.setText("Stop");
                } else {
                    timeline.pause();
                    sos = true;
                    timerButton.setText("Start");
                }
            }
        });

    }
    @FXML
    void startTest(ActionEvent event) {
        try{
            textDisplay.setText("");
            BufferedReader reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
            if(englishRadioButton.isSelected()){
                if(easyRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\english\\easy\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("1");
                }else if(mediumRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\english\\medium\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("2");

                }else if(difficultRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\english\\difficult\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("3");

                }
            }else if(russianRadioButton.isSelected()){
                if(easyRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\russian\\easy\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("4");
                }else if(mediumRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\russian\\medium\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("5");

                }else if(difficultRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\russian\\difficult\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("6");

                }

            }else if(tajikRadioButton.isSelected()){
                Alert informationAlert=new Alert(Alert.AlertType.INFORMATION,"Language");
                informationAlert.setTitle("Language settings");
                informationAlert.setHeaderText("Please install Tajik language on your laptop, before you start this exercise.");
                informationAlert.setContentText("Press exit when all required components are installed.\nHave a good time playing fast typing app!");
                informationAlert.showAndWait();
                if(easyRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\tajik\\easy\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("7");
                }else if(mediumRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\tajik\\medium\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("8");

                }else if(difficultRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\tajik\\difficult\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("9");

                }
            }else if(kyrgyzRadioButton.isSelected()){
                Alert informationAlert=new Alert(Alert.AlertType.INFORMATION,"Language");
                informationAlert.setTitle("Language settings");
                informationAlert.setHeaderText("Please install Kyrgyz language on your laptop, before you start this exercise.");
                informationAlert.setContentText("Press exit when all required components are installed.\nHave a good time playing fast typing app!");
                informationAlert.showAndWait();
                if(easyRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\kyrgyz\\easy\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("10");
                }else if(mediumRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\kyrgyz\\medium\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("11");

                }else if(difficultRadioButton.isSelected()){
                    reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\kyrgyz\\difficult\\"+ lessonChoiceBox.getSelectionModel().getSelectedItem().toString() + ".txt") ) );
                    //System.out.println("12");

                }

            }else{
                reader = new BufferedReader( new FileReader( new File("C:\\Users\\Acer\\Desktop\\Senior Year\\OOP\\Problem-Sets\\FinalProject\\src\\files\\Demo.txt") ) );
                //System.out.println(13);
            }

            String line;
            while((line = reader.readLine()) != null){
                textDisplay.setText( textDisplay.getText() + line);
                arr += line;
            }
            reader.close();
            textDisplay.requestFocus();
            errorCount =0; indexOfLine=0; wordCount = 0; totalChar = arr.length();

            AppController.ALERT_AUDIOCLIP.setRate(2.0);
            AppController.TYPING_AUDIOCLIP.setVolume(1.0);

            textDisplay.setStyle("-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
            textDisplay.selectRange(indexOfLine, indexOfLine+1);

            textDisplay.setOnKeyTyped( new EventHandler<KeyEvent>(){
                @Override
                public void handle(KeyEvent event) {
                    if(!timerStarted){
                        timerStarted = true;
                        timerButton.fire();
                    }

                    expectedKey = arr.charAt(indexOfLine);
                    //System.out.println(expectedKey);
                    typedKey = event.getCharacter().charAt(0);
                    if(indexOfLine < arr.length()){
                        if(typedKey != expectedKey){
                            AppController.ALERT_AUDIOCLIP.play();
                            errorCount++;
                            indexOfLine++;
                            textDisplay.setStyle("-fx-background-color: #ffcdd2;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                            textDisplay.selectRange(indexOfLine, indexOfLine+1);
                        }
                        else{
                            if(typedKey == ' ')
                                wordCount++;
                            AppController.TYPING_AUDIOCLIP.play();
                            indexOfLine++;
                            textDisplay.setStyle("-fx-background-color: white;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                            textDisplay.selectRange(indexOfLine, indexOfLine+1);
                        }

                    }
                    if(indexOfLine == arr.length()){
                        timerButton.fire();
                        resultButton.fire();
                    }
                }
            });

        }catch( IOException ex){
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



}
