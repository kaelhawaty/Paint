package eg.edu.alexu.csd.oop.draw.Commands;

import eg.edu.alexu.csd.oop.draw.Command;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.engine;

public class ShapeEdition implements Command {
    Shape oldShape;
    Shape newShape;
    public ShapeEdition(Shape oldShape, Shape newShape){
        this.oldShape = oldShape;
        this.newShape = newShape;
    }
    @Override
    public void undo() {
        List<Shape> arr = engine.arr;
        for(int i = 0; i < arr.size(); i++){
            try {
                if((int)newShape.getClass().getMethod("getUUID").invoke(newShape) == (int)arr.get(i).getClass().getMethod("getUUID").invoke(arr.get(i))){
                    try {
                        arr.set(i, (Shape) oldShape.clone());
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void redo() {
        List<Shape> arr = engine.arr;
        for(int i = 0; i < arr.size(); i++){
            try {
                if((int)oldShape.getClass().getMethod("getUUID").invoke(oldShape) == (int)arr.get(i).getClass().getMethod("getUUID").invoke(arr.get(i))){
                    arr.set(i, (Shape) newShape.clone());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }
}
