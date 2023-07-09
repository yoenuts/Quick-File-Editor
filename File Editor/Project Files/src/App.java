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
    private File mainFile;
    private Stage mainStage;

    public void start(Stage stage){
        mainStage = stage;
        
        textArea = new TextArea(); 
        //set size
        textArea.setPrefColumnCount(60);
        textArea.setPrefRowCount(30);
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
        openF.setOnAction(e -> doOpen());
        fMenu.getItems().add(openF);
        
        //save the file
        MenuItem saveF = new MenuItem("Save Changes");
        saveF.setOnAction(e -> doSave());
        fMenu.getItems().add(saveF);
        
        fMenu.getItems().add(new SeparatorMenuItem());

        //quit program
        MenuItem quitF = new MenuItem("Quit");
        quitF.setOnAction(e -> doQuit());
        fMenu.getItems().add(quitF);

        return mBar;
    }

    //set the text area empty
    private void doNew(){
        textArea.setText("");
        System.out.println("Im working");
        mainFile = null;
        mainStage.setTitle("New, Untitled file");
    }

    // to save a file, text area contents must be pasted to user's chosen output file
    private void doSave(){
        FileChooser fileDialog = new FileChooser();
        if(mainFile == null){ //it's null becuase it's not being edited.
            fileDialog.setInitialFileName("filename.txt");
            //open file manager to This PC
            fileDialog.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        else{
            //currently being edited: the contents are already being shown sa textArea
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
        // copy contents from textArea, close printwriter and save it by setting the mainFile as the selectedFile
        try{
            out.print(textArea.getText());
            out.close();
            mainFile = selectedFile;
            mainStage.setTitle("Edit: " + mainFile.getName());
        } catch(Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR, "An error was occured while trying to write data.");
            error.showAndWait();
            //return;
        }
    }

    private void doOpen(){
        FileChooser openDialog = new FileChooser();
        openDialog.setTitle("Select file to be opened");
        openDialog.setInitialFileName(null); // no file selected yet bruh
        if(mainFile == null){
            openDialog.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        else{
            //if the file is currently being edited, set the file name to its original name
            openDialog.setInitialFileName(mainFile.getName());
            //get the file's location
            openDialog.setInitialDirectory(mainFile.getParentFile());
        }
        ////to avoid overriding a file, create a temporary file to hold the contents of
        // a selected file, then set the program mainfile equal to that selectedFile.
        File selectedFile = openDialog.showOpenDialog(mainStage);
        Scanner in;
        try{
            in = new Scanner(selectedFile);
        } catch(Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR, "An error was encountered while trying to save file.");
            error.showAndWait();
            return;
        }
        try{
            StringBuilder inp = new StringBuilder();
            while(in.hasNextLine()){
                String str = in.nextLine();
                if(str == null){
                    break; //EOF
                }
                inp.append(str);
                inp.append("\n");
                //limit the texts to a maximum of 1000 characters only

                if(str.length() > 1000){
                    Alert error = new Alert(Alert.AlertType.ERROR, "Error: File too large...");
                    error.showAndWait();    
                    return;
                }
            }
            textArea.setText(inp.toString());
            mainFile = selectedFile; 
            mainStage.setTitle("Edit: " + mainFile.getName());
        }catch(Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR, "An error was encountered while trying to read file data.");
            error.showAndWait();    
            return;
        }
        finally{
            in.close();
        }
    }

    private void doQuit(){
        System.exit(0);
    }

}

