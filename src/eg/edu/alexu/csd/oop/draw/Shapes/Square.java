package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Utils.UUIDGenerator;

import java.awt.*;
import java.util.Map;

public class Square extends Rectangle {
    int length = 0;
    int UUID;
    public Square(Point a,  Color color, Color fillColor, int strokeWidth) {
        super(a,color, fillColor, strokeWidth);
        UUID = UUIDGenerator.getInstance().generate();
    }
    public Square(){}
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
    public void resizeShape(Point p1, Point p2){
        setPosition(p2);
    }
    @Override
    public void setProperties(Map<String, Double> properties) {
    }
    public int getUUID(){
        return UUID;
    }
    public void setUUID(int id){UUID = id;}
    @Override
    public Object clone() throws CloneNotSupportedException {
        Square temp = new Square(topLeft,new Color(color.getRGB()), new Color(fillColor.getRGB()), strokeWidth);
        temp.width = this.width;
        temp.height = this.height;
        temp.UUID = this.UUID;
        return temp;
    }
}
