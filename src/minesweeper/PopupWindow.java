package minesweeper;

import java.util.Queue;
import java.util.LinkedList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PopupWindow {
    protected final Queue<String> contents;
    private final Stage stage;
    private final VBox root;
    private final Label label1, label2, label3, label4, label5;

    public PopupWindow() {
        contents = new LinkedList<>();
        stage = new Stage();
        root = new VBox();
        label1 = new Label();
        label1.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 12));
        label2 = new Label();
        label2.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 12));
        label3 = new Label();
        label3.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 12));
        label4 = new Label();
        label4.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 12));
        label5 = new Label();
        label5.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 12));

        root.getChildren().addAll(label1, label2, label3, label4, label5);
        root.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(root, 200, 600));
        stage.setResizable(false);
    }

    public Stage getStage(){
        return stage;
    }

    public void addContent(String content) {
        if (contents.size() == 5) {
            contents.poll(); // remove the oldest content
        }
        contents.offer(content); // add the new content
        updateLabels();
        stage.show();
    }

    private void updateLabels() {
        label1.setText(contents.size() >= 1 ? contents.toArray()[0].toString() : "");
        label2.setText(contents.size() >= 2 ? contents.toArray()[1].toString() : "");
        label3.setText(contents.size() >= 3 ? contents.toArray()[2].toString() : "");
        label4.setText(contents.size() >= 4 ? contents.toArray()[3].toString() : "");
        label5.setText(contents.size() >= 5 ? contents.toArray()[4].toString() : "");
    }
}
