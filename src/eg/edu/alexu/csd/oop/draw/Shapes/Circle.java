package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Utils.UUIDGenerator;

import java.awt.*;
import java.util.Map;

public class Circle extends Ellipse implements Shape {
    int UUID;
    public Circle(Point c, Color color, Color fillColor, int width) {
        super(c, color, fillColor, width);
        UUID = UUIDGenerator.getInstance().generate();
    }
    public Circle(){}
    @Override
    public void setPosition(Point position) {
        width = position.x - topLeft.x;
        height = position.y - topLeft.y;
        int diffX = position.x - topLeft.x;
        int diffY = position.y - topLeft.y;
        diffX = (Math.abs(diffX) + Math.abs(diffY))/2;
        if(width > 0){
            width = diffX;
        }else{
            width = -diffX;
        }
        if(height > 0){
            height = diffX;
        }else{
            height = -diffX;
        }

    }
    @Override
    public void setProperties(Map<String, Double> properties) {
    }
    public int getUUID(){
        return UUID;
    }
    public void setUUID(int id){UUID = id;}
    @Override
    public void resizeShape(Point p1, Point p2){
        setPosition(p2);
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        Circle temp = new Circle(topLeft, new Color(color.getRGB()), new Color(fillColor.getRGB()), strokeWidth);
        temp.width = this.width;
        temp.height = this.height;
        temp.UUID = this.UUID;
        return temp;
    }

}
