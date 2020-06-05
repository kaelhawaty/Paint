package eg.edu.alexu.csd.oop.draw.Commands;

import eg.edu.alexu.csd.oop.draw.Command;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.util.ArrayList;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.*;

public class ShapeAddition implements Command {
    ArrayList<Shape> oldList;
    ArrayList<Shape> newList;
    public ShapeAddition(ArrayList<Shape> oldList, ArrayList<Shape> newList){
        this.oldList = oldList;
        this.newList = newList;
    }
    @Override
    public void undo() {
        engine.arr = engine.cloneArr(oldList);
    }

    @Override
    public void redo() {
        engine.arr = engine.cloneArr(newList);
    }
}
