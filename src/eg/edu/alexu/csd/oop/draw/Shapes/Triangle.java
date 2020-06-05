package eg.edu.alexu.csd.oop.draw.Shapes;

import eg.edu.alexu.csd.oop.draw.Geometry.GeometryHandler;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Utils.StaticVars;
import eg.edu.alexu.csd.oop.draw.Utils.UUIDGenerator;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Triangle implements Shape {
    int UUID;
    Map<String, Double> prop = new HashMap<>();
    int[] pX = new int[3];
    int[] pY = new int[3];
    Color fillColor;
    Color color;
    int strokeWidth;
    public Triangle(Point firstPoint, Point secondPoint, Color color, Color fillColor, int width) {
        pX[0] = firstPoint.x;
        pY[0] = firstPoint.y;
        pX[1] = secondPoint.x;
        pY[1] = secondPoint.y;
        pX[2] = secondPoint.x;
        pY[2] = secondPoint.y;
        this.color = color;
        this.fillColor = fillColor;
        this.strokeWidth = width;
        UUID = UUIDGenerator.getInstance().generate();

    }
    public Triangle(){}
    public void setSecondPoint(Point position){
        pX[1] = position.x;
        pY[1] = position.y;
        pX[2] = position.x;
        pY[2] = position.y;
    }
    @Override
    public void setPosition(Point position) {
        pX[2] = position.x;
        pY[2] = position.y;
    }

    @Override
    public Point getPosition() {
        return new Point(pX[0], pY[0]);
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
        return GeometryHandler.checkPointInTriangle(pX[0], pY[0], pX[1], pY[1], pX[2], pY[2], p);
    }
    public int getUUID(){
        return UUID;
    }
    public void setUUID(int id){UUID = id;}
    public void moveShape(int dx, int dy){
        for(int i = 0; i < 3; i++){
            pX[i] += dx;
            pY[i] += dy;
        }
    }
    public void computeResize(Point pp){
        int min = 99999999;
        int idx = -1;
        for(int i = 0; i < 3; ++i){
            int n = (pp.x-pX[i])*(pp.x-pX[i]) + (pp.y-pY[i])*(pp.y-pY[i]);
            if(n < min){
                min = n;
                idx = i;
            }
        }
        Point [] p = new Point[2];
        for (int i = 0, cnt = 0; i < 3; ++i){
            if(i != idx){
                p[cnt] = new Point(pX[i], pY[i]);
                cnt++;
            }
        }
        for (int i = 0, cnt = 0; i < 3; ++i){
            pX[i] = p[cnt].x;
            pY[i] = p[cnt].y;
            if(i == 0)
                cnt++;
        }
    }
    public void resizeShape(Point pp1, Point pp2){
        setPosition(pp1);
    }
    public int getStrokeWidth(){return strokeWidth;}
    public void setStrokeWidth(int w){strokeWidth = w;}
    public int[] getPX(){return pX;}
    public void setPX(int [] a){
        pX[0] = a[0];
        pX[1] = a[1];
        pX[2] = a[2];
    }
    public int[] getPY(){return pY;}
    public void setPY(int [] a){
        pY[0] = a[0];
        pY[1] = a[1];
        pY[2] = a[2];
    }
    @Override
    public void draw(Graphics canvas) {
        Graphics2D g = (Graphics2D) canvas;
        if(StaticVars.selectedShape == this){
            Stroke dashed = new BasicStroke(strokeWidth+3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);
            canvas.setColor(Color.BLACK);
            canvas.drawPolygon(pX, pY, 3);

        }
        canvas.setColor(color);
        ((Graphics2D) canvas).setStroke(new BasicStroke(strokeWidth));
        canvas.drawPolygon(pX, pY, 3);
        canvas.setColor(fillColor);
        canvas.fillPolygon(pX, pY, 3);

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Triangle temp = new Triangle(new Point(pX[0], pY[0]), new Point(pX[1], pY[1]), new Color(color.getRGB()), new Color(fillColor.getRGB()), strokeWidth);
        temp.pX[2] = this.pX[2];
        temp.pY[2] = this.pY[2];
        temp.UUID = this.UUID;
        return temp;
    }
}
