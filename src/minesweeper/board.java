package minesweeper;

import javafx.scene.paint.Color;

import java.util.*;

public class board{
    protected static int mines, time, supermineOn, N, round, cntFlags, MAX_FLAGS;
    protected static Tile[][] hidden;
    protected static boolean hasLost;
    protected static String winner;

    public board(int level, int mines, int time, int supermineOn) throws InvalidValueException {
        board.N = switch(level){
            case 1 -> 9;
            case 2 -> 16;
            default -> throw new InvalidValueException("Invalid Value");
        };
        board.mines = mines;
        board.time = time;
        board.round = 0;
        board.cntFlags = 0;
        board.hidden = new Tile[N][N];
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                board.hidden[i][j] = new Tile(i,j);
            }
        }
        board.supermineOn = supermineOn;
        board.hasLost = false;
        board.winner = null;
    }

    // setup(): Set Mines and Supermine in random Tiles and update nmuber of neighbors
    // of each Tile (using Mine method neighbors())
    public void setup(){
        Random random = new Random();
        int allMines = mines;
        //Random indexes
        int ri = random.nextInt(N);
        int rj = random.nextInt(N);

        if(supermineOn == 1){
            hidden[ri][rj] = new Supermine(ri,rj);
            hidden[ri][rj].neighbors();
            allMines--;
        }

        for(int m=0; m<allMines; m++){
            while(hidden[ri][rj] instanceof Mine){
                ri = random.nextInt(N);
                rj = random.nextInt(N);
            }
            hidden[ri][rj] = new Mine(ri,rj);
            hidden[ri][rj].neighbors();
        }
        MAX_FLAGS = mines;
    }

    // win(): Check if player has won. If so set background of all Mines blue.
    public boolean win(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(!(hidden[i][j] instanceof Mine) && !hidden[i][j].revealed) return false;
            }
        }
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(board.hidden[i][j] instanceof Mine){
                    board.hidden[i][j].label.setVisible(true);
                    board.hidden[i][j].rect.setFill(Color.PALETURQUOISE);
                }
            }
        }
        winner = "Player";
        return true;
    }

    // lose(): It is called when a Mine is clicked. It sets all Mines visible.
    public static void lose(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                if(board.hidden[i][j] instanceof Mine){
                    board.hidden[i][j].label.setVisible(true);
                    board.hidden[i][j].rect.setFill(Color.DARKGRAY);
                }
            }
        }
        winner = "Computer";
        board.hasLost = true;
    }

    public int getN() {
        return N;
    }
    public int getMines() {
        return mines;
    }
    public int getCntFlags() {
        return cntFlags;
    }
    public int getTime() {
        return time;
    }
    public Tile[][] getHidden(){
        return hidden;
    }
    public int getRound(){
        return round;
    }
    public int changeRound(){
        return ++round;
    }
    public void doLose(){
        lose();
    }
    public boolean getHasLost(){
        return hasLost;
    }
    public String getWinner(){
        return winner;
    }
}
