package minesweeper;

import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Paint;



public class Tile {
    int TILE_SIZE = Main.TILE_SIZE, N = board.N;
    int i, j, number;
    boolean flagged, revealed, deactivated;
    StackPane stackPane;
    Label label, flagLabel;
    Rectangle rect;

    // Paint is an 8-color array. It contains all the colors that can be set as text fill of each Tile label.
    // It follows WindowsXP Minesweeper coloring.
    Paint[] colors = {Color.web("#0000FF"), Color.web("#008000"), Color.web("#FF0000"), Color.web("#00007F"),
            Color.web("#800000"), Color.web("#008080"), Color.web("#000000"), Color.web("#808080")};

    public Tile(){}
    public Tile(int i, int j){
        this.i = i; this.j = j;
        this.number = 0;
        this.flagged = false;
        this.revealed = false;
        // Rectangle: Tile representation
        rect = new Rectangle(TILE_SIZE, TILE_SIZE);
        RectangleCreate(this.rect);
        // Label of rectangle: Contains the number of Tile neighbors
        label = new Label("");
        LabelCreate(this.label);
        // Flag label
        flagLabel = new Label("\uD83D\uDEA9");
        flagLabelCreate(this.flagLabel);
        // Tile stack (tile with its neighbor number)
        this.stackPane = new StackPane();
        this.stackPane.getChildren().addAll(this.rect, this.label, this.flagLabel);
    }
    public void neighbors(){}

    // reveal(): Recursively reveal tiles
    public void reveal(){
        if(number == 0 && !revealed){
            revealed = true;
            if(i>0){
                board.hidden[i-1][j].reveal();
                if(j>0) board.hidden[i-1][j-1].reveal();
                if(j<N-1) board.hidden[i-1][j+1].reveal();
            }
            if(i<N-1){
                board.hidden[i+1][j].reveal();
                if(j>0) board.hidden[i+1][j-1].reveal();
                if(j<N-1) board.hidden[i+1][j+1].reveal();
            }
            if(j>0) board.hidden[i][j-1].reveal();
            if(j<N-1) board.hidden[i][j+1].reveal();
        }
        revealTile();
        revealed = true;
    }

    // flag(): Flags a tile and increases the flag counter.
    // Flags cannot outnumber that Mines (MAX_FLAGS = #mines)
    public void flag(){
        if (revealed || deactivated) return;
        if(board.cntFlags < board.MAX_FLAGS){
            board.cntFlags++;
            flagged = true;
            flagLabel.setVisible(true);
        }
    }

    // unflag(): Unflags a tile and decreases the flag counter.
    public void unflag(){
        if(flagged){
            flagged = false;
            flagLabel.setVisible(false);
            if(board.cntFlags > 0) {
                board.cntFlags--;
            }
        }
    }

    protected void RectangleCreate(Rectangle rect) {
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.LIGHTGRAY);
    }
    protected void LabelCreate(Label label) {
        label.setFont(Font.font("DejaVu Sans Mono", FontWeight.BOLD, 20));
        label.setVisible(false);
    }
    protected void LabelCreate(Label label, Color color) {
        LabelCreate(label);
        label.setTextFill(color);
    }
    protected void flagLabelCreate(Label flagLabel){
        flagLabel.setVisible(false);
        flagLabel.setFont(new Font(20));
        flagLabel.setTextFill(Color.RED);
    }

    // revealTile(): Reveal number of mines that are neighbors of this Tile
    public void revealTile(){
        unflag();
        deactivated = true;
        if(number > 0 && !(this instanceof Mine)) {
            label.setTextFill(colors[number-1]);
            label.setText(String.valueOf(number));
        }
        // If a Mine is revealed by flagging the Supermine, decrease MAX_FLAGS.
        // This happens because, due to this feature, some Mines may be revealed during the game,
        // so fewer flags are needed.
        if(this instanceof Mine) board.MAX_FLAGS--;
        rect.setFill(Color.GRAY);
        label.setVisible(true);
    }

}