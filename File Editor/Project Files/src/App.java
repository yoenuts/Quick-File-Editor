import java.io.*;
import java.util.Scanner;

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
    private File file;
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

        return mBar;
    }

    //set the text area empty
    private void doNew(){
        textArea.setText("");
        mainStage.setTitle("New, Untitled file");
    }

    


}

