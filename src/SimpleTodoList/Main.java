package SimpleTodoList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static final String TITLE = "Todo List";

    @Override
    public void start(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listView.fxml"));

        try {
            VBox root = (VBox) loader.load();
            //AnchorPane root = (AnchorPane)loader.load();
            Scene scene = new Scene(root, 630,400);
            scene.getStylesheets().add(getClass().getResource("Basic.css").toExternalForm());
            primaryStage.setTitle(TITLE);
            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
