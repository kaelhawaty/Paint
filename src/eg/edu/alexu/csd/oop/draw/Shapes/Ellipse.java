package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Geometry.GeometryHandler;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Utils.StaticVars;
import eg.edu.alexu.csd.oop.draw.Utils.UUIDGenerator;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Ellipse implements Shape {
    int UUID;
    Map<String, Double> prop = new HashMap<>();
    Point topLeft = new Point();
    int width = 0, height = 0;
    Color fillColor;
    Color color;
    int strokeWidth;
    public Ellipse(Point c, Color color, Color fillColor, int width){
        topLeft.x = c.x;
        topLeft.y = c.y;
        this.color = color;
        this.fillColor = fillColor;
        this.strokeWidth = width;
        UUID = UUIDGenerator.getInstance().generate();
    }
    public Ellipse(){}
    @Override
    public void setPosition(Point position) {
        width = position.x - topLeft.x;
        height = position.y - topLeft.y;
    }

    @Override
    public Point getPosition() {
        return topLeft;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
    }

    @Override
    public Map<String, Double> getProperties() {
        return prop;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }
    public boolean checkInside(Point p){
        int px;
        if(width > 0){
            px = topLeft.x;
        }else{
            px = topLeft.x + width;
        }
        int py;
        if(height > 0){
            py = topLeft.y;
        }else{
            py = topLeft.y+height;
        }
        return GeometryHandler.checkPointInEllipse(px, py, width, height, p);
    }
    public int getUUID(){
        return UUID;
    }
    public void setUUID(int id){UUID = id;}
    public void moveShape(int dx, int dy){
        topLeft.x += dx;
        topLeft.y += dy;
    }
    public void resizeShape(Point p1, Point p2){
        width+=p1.x-p2.x;
        height+=p1.y-p2.y;
    }
    public int getStrokeWidth(){return strokeWidth;}
    public void setStrokeWidth(int w){strokeWidth = w;}
    public int getWidth(){return width;}
    public void setWidth(int w){width = w;}
    public int getHeight(){return height;}
    public void setHeight(int h){height = h;}
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g = (Graphics2D) canvas;
        int px;
        if(width > 0){
            px = topLeft.x;
        }else{
            px = topLeft.x + width;
        }
        int py;
        if(height > 0){
            py = topLeft.y;
        }else{
            py = topLeft.y+height;
        }
        if(StaticVars.selectedShape == this){
            Stroke dashed = new BasicStroke(3+strokeWidth/10, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            ((Graphics2D) canvas).setStroke(dashed);
            canvas.setColor(Color.BLACK);
            canvas.drawOval(px-strokeWidth/2, py-strokeWidth/2, Math.abs(width)+strokeWidth, Math.abs(height)+strokeWidth);
        }
        canvas.setColor(color);
        ((Graphics2D) canvas).setStroke(new BasicStroke(strokeWidth));
        canvas.drawOval(px, py, Math.abs(width),Math.abs(height));
        canvas.setColor(fillColor);
        canvas.fillOval(px, py, Math.abs(width),Math.abs(height));


    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        Ellipse temp = new Ellipse(topLeft, new Color(color.getRGB()), new Color(fillColor.getRGB()), strokeWidth);
        temp.width = this.width;
        temp.height = this.height;
        temp.UUID = this.UUID;
        return temp;
    }
}
