package me.test.jdk.javafx;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

/**
 * mvn clean javafx:run
 *
 * http://docs.oracle.com/javase/8/javase-clienttechnologies.htm
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
