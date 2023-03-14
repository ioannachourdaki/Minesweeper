package minesweeper;

public class Exceptions{
    public void checkInput(int level, int mines, int time, int supermineOn) throws InvalidValueException {
        if((level != 1 && level != 2) ||
                ((level == 1) && (mines<9 || mines>11 || time<120 || time>180 || supermineOn!=0)) ||
                ((level == 2) && (mines<35 || mines>45 || time<240 || time>360 || (supermineOn != 0 && supermineOn != 1)))){
            throw new InvalidValueException("Invalid Value");
        }
    }
    public void checkFile(int size) throws InvalidDescriptionException {
        if(size != 4) throw new InvalidDescriptionException("Invalid Description");
    }
}
