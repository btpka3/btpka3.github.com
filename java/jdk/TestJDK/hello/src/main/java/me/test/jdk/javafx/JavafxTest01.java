package me.test.jdk.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * mvn clean javafx:run
 * <p>
 * http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
 *
 * @see <a href="https://gluonhq.com/products/javafx/">JavaFX</a>
 * @see <a href="https://openjfx.io/openjfx-docs/#install-javafx">https://openjfx.io/openjfx-docs/#install-javafx</a>
 */
public class JavafxTest01 extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
