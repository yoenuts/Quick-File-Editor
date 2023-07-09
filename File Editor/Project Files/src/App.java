import java.io.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

// A program that allows you to edit short text files through a file dialog. Code references is from a book.
//will be coding along with comments.

public class App extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }

    //open a window with a text area where a user can edit text from
    
    private TextArea textArea;
    private File mainFile;
    private Stage mainStage;

    public void start(Stage stage){
        mainStage = stage;
        
        textArea = new TextArea(); 

        //set size

        //create a BorderPane
        BorderPane root = new BorderPane(textArea);

        //place items in the root
        root.setTop(createMenuBar());
        stage.setTitle("Quick Edit~~");
        stage.setScene(new Scene(root));
        
        stage.show();

    }

    //make a menuBar
    private MenuBar createMenuBar(){
        Menu fMenu = new Menu("File");
        //make a menu bar
        MenuBar mBar = new MenuBar(fMenu);
        //put stuff in the menu bar

        //new file edit
        MenuItem newF = new MenuItem("New");
        newF.setOnAction(e -> doNew());
        fMenu.getItems().add(newF);
        //open a file
        MenuItem openF = new MenuItem("Open..");
        newF.setOnAction(e -> doOpen());
        fMenu.getItems().add(openF);

        MenuItem saveF = new MenuItem("Save");
        newF.setOnAction(e -> doSave());
        fMenu.getItems().add(saveF);

        return mBar;
    }

    //set the text area empty
    private void doNew(){
        textArea.setText("");
        mainFile = null;
        mainStage.setTitle("New, Untitled file");
    }

    // to save a file, text area contents must be pasted to user's chosen output file
    private void doSave(){
        FileChooser fileDialog = new FileChooser();
        if(mainFile == null){ //it's null becuase it's not being edited.
            fileDialog.setInitialFileName("filename.txt");
            //this will set the directory in the dialog to 
            // a user's home directory, or the folder where these program files are kept.
            fileDialog.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        else{
            //currently being edited
            //get that file's name
            fileDialog.setInitialFileName(mainFile.getName());
            //get the file's location
            fileDialog.setInitialDirectory(mainFile.getParentFile());
        }
        //open FileChooser to allow user to choose where to save new file
        fileDialog.setTitle("Select File to be Saved");
        File selectedFile = fileDialog.showSaveDialog(mainStage);

        if(selectedFile == null){
            return; //nothing was selected
        }

        // create a printwriter to copy the text from the textArea to a selected file
        PrintWriter out;
        try{ 
            out = new PrintWriter(new FileWriter(selectedFile));
        } catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR, "An error was encountered while trying to save file.");
            error.showAndWait();
            return;
        }






    }

    private void doOpen(){

    }

    


}

