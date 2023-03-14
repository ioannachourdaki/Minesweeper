package minesweeper;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Mine extends Tile{

    /**
     * Constructor of class Mine
     */
    public Mine(){}

    /**
     * Constructor of class Mine
     *  @param i the row position of the mine
     *  @param j the column position of the mine
     */
    public Mine(int i, int j){
        this.i = i; this.j = j;
        this.number = 0;
        this.flagged = false;
        // Rectangle: Tile representation
        rect = new Rectangle(TILE_SIZE, TILE_SIZE);
        RectangleCreate(this.rect);
        // Label of rectangle: Contains the number of Tile neighbors
        label = new Label("\uD83D\uDCA3");
        LabelCreate(this.label, Color.BLACK);
        // Flag label
        flagLabel = new Label("\uD83D\uDEA9");
        flagLabelCreate(this.flagLabel);
        // Tile stack (tile with its neighbor number)
        this.stackPane = new StackPane();
        this.stackPane.getChildren().addAll(this.rect, this.label, this.flagLabel);
    }

    /**
     * Increases "number" of each Tile that is a neighbor to this Mine.
     * "Number" is an attribute of Tile that contains the number of neighboring mines
     */
    public void neighbors(){
        if(i>0){
            board.hidden[i-1][j].number++;
            if(j>0) board.hidden[i-1][j-1].number++;
            if(j<N-1) board.hidden[i-1][j+1].number++;
        }
        if(i<N-1){
            board.hidden[i+1][j].number++;
            if(j>0) board.hidden[i+1][j-1].number++;
            if(j<N-1) board.hidden[i+1][j+1].number++;
        }
        if(j>0) board.hidden[i][j-1].number++;
        if(j<N-1) board.hidden[i][j+1].number++;
    }

    /**
     * Changes the color of the Mine label to red, reveals the Mine and calls lose() method,
     * which leads player to lose the game.
     * If the mine has already been deactivated nothing happens.
     * @see board#lose()
     */
    public void reveal(){
        if(deactivated) return;
        label.setTextFill(Color.RED);
        board.lose();
    }
}
