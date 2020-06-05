package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Geometry.GeometryHandler;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Utils.StaticVars;
import eg.edu.alexu.csd.oop.draw.Utils.UUIDGenerator;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Line implements Shape {
    int UUID;
    Map<String, Double> prop = new HashMap<String,Double>();
    Point p1 =  new Point();
    Point p2 = new Point();
    Color color;
    int strokeWidth;
    public Line(Point a, Point b, Color color, int width){
        p1.x = a.x;
        p1.y = a.y;
        p2.x = b.x;
        p2.y = b.y;
        this.color = new Color(color.getRGB());
        this.strokeWidth = width;
        UUID = UUIDGenerator.getInstance().generate();

    }
    public Line(){}
    @Override
    //Assumption: Meant starting Point
    public void setPosition(Point position) {
        p2.x = position.x;
        p2.y = position.y;
    }
    // returns starting point
    @Override
    public Point getPosition() {
        return p1;
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
        this.color = new Color(color.getRGB());
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.color = new Color(color.getRGB());
    }

    @Override
    public Color getFillColor() {
        return color;
    }
    public boolean checkInside(Point p){
        return GeometryHandler.checkPointOnLine(p1.x, p1.y, p2.x, p2.y, p);
    }
    public void moveShape(int dx, int dy){
        p1.x += dx;
        p1.y += dy;
        p2.x += dx;
        p2.y += dy;
    }
    public int getUUID(){
        return UUID;
    }
    public void setUUID(int id){UUID = id;}
    public void resizeShape(Point pp1, Point pp2){
       setPosition(pp2);
    }
    public Point getSecond(){return p2;}
    public void setSecond(Point p){p1.x = p.x; p1.y = p.y;}
    public int getStrokeWidth(){return strokeWidth;}
    public void setStrokeWidth(int w){strokeWidth = w;}
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g = (Graphics2D) canvas;
        if(StaticVars.selectedShape == this){
            Stroke dashed = new BasicStroke(strokeWidth+3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);
            canvas.setColor(Color.BLACK);
            canvas.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
        canvas.setColor(color);
        ((Graphics2D) canvas).setStroke(new BasicStroke(strokeWidth));
        canvas.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Line t = new Line(p1, p2, color, strokeWidth);
        t.UUID = this.UUID;
        return t;
    }
}
