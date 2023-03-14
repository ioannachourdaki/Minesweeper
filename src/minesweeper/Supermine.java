package minesweeper;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Supermine extends Mine{
    public Supermine(int i, int j){
        this.i = i; this.j = j;
        this.number = 0;
        this.flagged = false;
        // Rectangle: Tile representation
        rect = new Rectangle(TILE_SIZE, TILE_SIZE);
        RectangleCreate(this.rect);
        // Label of rectangle: Contains the number of Tile neighbors
        label = new Label("\uD83D\uDCA3");
        LabelCreate(this.label, Color.GREEN);
        // Flag label
        flagLabel = new Label("\uD83D\uDEA9");
        flagLabelCreate(this.flagLabel);
        // Tile stack (tile with its neighbor number)
        this.stackPane = new StackPane();
        this.stackPane.getChildren().addAll(this.rect, this.label, this.flagLabel);
    }

    // flag(): Flags Supermine
    public void flag(){
        if (deactivated) return;
        if(board.cntFlags < board.MAX_FLAGS){
            board.cntFlags++;
            flagged = true;
            flagLabel.setVisible(true);
        }
        // If the Supermine is flagged during the first 4 tries, all Tiles that are positioned
        // to the same column or row with the Supermine are being revealed.
        if(board.round < 4){
            board.MAX_FLAGS++;
            for(int k=0; k<N; k++){
                board.hidden[this.i][k].revealTile();
                board.hidden[k][this.j].revealTile();
            }
        }
    }
}
