package eg.edu.alexu.csd.oop.draw.Save;

import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Shapes.Rectangle;
import eg.edu.alexu.csd.oop.draw.Shapes.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.engine;

public class JSON {
    public static void save(Shape shapes[], String path) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write("{ \n  \"Shapes\" : [");
        for(int i = 0; i < shapes.length; i++)
        {
            writer.write("\n\t     { \n");
            writer.write("\t     \"className\" : \"");
            writer.write(shapes[i].getClass().getName() + "\",\n");
            writer.write("\t     \"Position.X\" : \"");
            writer.write((shapes[i].getPosition() == null ? null : shapes[i].getPosition().x) + "\",\n");
            writer.write("\t     \"Position.Y\" : \"");
            writer.write((shapes[i].getPosition() == null ? null : shapes[i].getPosition().y) + "\",\n");
            writer.write("\t     \"Color\" : \"");
            writer.write((shapes[i].getColor() == null ? null : (shapes[i].getColor().toString().replace("java.awt.Color", ""))) + "\",\n");
            writer.write("\t     \"fillColor\" : \"");
            writer.write((shapes[i].getFillColor() == null ? null : (shapes[i].getFillColor().toString().replace("java.awt.Color", "").replace("]", ",a=") + shapes[i].getFillColor().getAlpha()) + "]") + "\",\n");
            writer.write("\t     \"strokeWidth\" : \"");
            writer.write((shapes[i].getClass().getMethod("getStrokeWidth").invoke(shapes[i])) + "\",\n");
            writer.write("\t     \"UUID\" : \"");
            writer.write((shapes[i].getClass().getMethod("getUUID").invoke(shapes[i])) + "\",\n");
            if (Line.class.equals(shapes[i].getClass())) {
                writer.write("\t     \"secondPoint.X\" : \"");
                Point p = (Point) shapes[i].getClass().getMethod("getSecond").invoke(shapes[i]);
                writer.write((p == null ? null : p.x) + "\",\n");
                writer.write("\t     \"secondPoint.Y\" : \"");
                writer.write((p == null ? null : p.y) + "\"");
            }
            else if(Triangle.class.equals(shapes[i].getClass())){
                writer.write("\t     \"Points.X\" : \"");
                int[] a = (int[]) shapes[i].getClass().getMethod("getPX").invoke(shapes[i]);
                writer.write(("["+a[0]+", "+a[1]+", "+a[2]+"]") + "\",\n");
                a = (int[]) shapes[i].getClass().getMethod("getPY").invoke(shapes[i]);
                writer.write("\t     \"Points.Y\" : \"");
                writer.write(("["+a[0]+", "+a[1]+", "+a[2]+"]") + "\"");
            }
            else{
                writer.write("\t     \"Height\" : \"");
                writer.write(((int)shapes[i].getClass().getMethod("getHeight").invoke(shapes[i])) + "\",\n");
                writer.write("\t     \"Width\" : \"");
                writer.write(((int)shapes[i].getClass().getMethod("getWidth").invoke(shapes[i])) + "\"");
            }
            writer.write("\n\t     }");
            if (i != shapes.length-1) {
                writer.write(",");
            }
        }
        writer.write("\n\t     ]\n}");
        writer.flush();
        writer.close();
    }
    public  ArrayList load(String path) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        ArrayList<Shape> arr = new ArrayList<>();
        reader.readLine();
        reader.readLine();
        while (reader.readLine().replace(" ", "").replace("\t", "").equals("{")) {
            ClassLoader cl = this.getClass().getClassLoader();
            List<Class<? extends Shape>> supportedShapes = engine.getSupportedShapes();
            if(supportedShapes != null && supportedShapes.size() != 0) {
                cl = supportedShapes.get(supportedShapes.size() - 1).getClassLoader();
            }
            String className = reader.readLine().replace("	     \"className\" : ", "").replace("\"", "").replace(",", "");
            Class<?> c = null;
            for(Class<? extends Shape> clas : supportedShapes){
                if(clas.getName().equals(className)){
                    c = Class.forName(className, false, cl);
                    break;
                }
            }
            Shape shape = null;
            String px = reader.readLine().replace("	     \"Position.X\" : ", "").replace("\"", "").replace(",", "");
            String py = reader.readLine().replace("	     \"Position.Y\" : ", "").replace("\"", "").replace(",", "");
            Point pos = null;
            Color color = null, fillColor = null;
            if (!(px.equals("null") || py.equals("null")))
                pos = new Point(Integer.parseInt(px), Integer.parseInt(py));
            String sc = reader.readLine().replace("	     \"Color\" : ", "").replace("\"", "").replace(",", "");
            if (!(sc.equals("null"))) {
                StringTokenizer st = new StringTokenizer(sc.replace("[r=", "").replace("g", "").replace("b", "").replace("]", ""), "=");
                color = new Color(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            sc = reader.readLine().replace("	     \"fillColor\" : ", "").replace("\"", "").replace(",", "");
            if (!(sc.equals("null"))) {
                StringTokenizer st = new StringTokenizer(sc.replace("[r=", "").replace("g", "").replace("b", "").replace("a", "").replace("]", ""), "=");
                fillColor = new Color(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            String sw = reader.readLine().replace("	     \"strokeWidth\" : ", "").replace("\"", "").replace(",", "");
            String uuid = reader.readLine().replace("	     \"UUID\" : ", "").replace("\"", "").replace(",", "");
            if(Line.class == c){
                px = reader.readLine().replace("	     \"secondPoint.X\" : ", "").replace("\"", "").replace(",", "");
                py = reader.readLine().replace("	     \"secondPoint.Y\" : ", "").replace("\"", "").replace(",", "");
                Point sPoint = null;
                if (!(px.equals("null") || py.equals("null")))
                    sPoint = new Point(Integer.parseInt(px), Integer.parseInt(py));
                shape = new Line(pos, sPoint, color, Integer.parseInt(sw));
            }
            else if (Triangle.class == c){
                sc = reader.readLine().replace("	     \"Points.X\" : ", "").replace("\"", "").replace(",", "");
                StringTokenizer st = new StringTokenizer(sc.replace("[", "").replace(", ", " ").replace("]", ""), " ");
                int [] x = new int [3], y = new int [3];
                for(int i = 0; i < 3; ++i)
                    x[i] = Integer.parseInt(st.nextToken());
                sc = reader.readLine().replace("	     \"Points.Y\" : ", "").replace("\"", "").replace(",", "");
                st = new StringTokenizer(sc.replace("[", "").replace(", ", " ").replace("]", ""), " ");
                for(int i = 0; i < 3; ++i)
                    y[i] = Integer.parseInt(st.nextToken());
                shape = new Triangle(pos, pos, color, fillColor, Integer.parseInt(sw));
                shape.getClass().getMethod("setPX", int[].class).invoke(shape, x);
                shape.getClass().getMethod("setPY", int[].class).invoke(shape, y);
            }else {
                String h = reader.readLine().replace("	     \"Height\" : ", "").replace("\"", "").replace(",", "");
                String w = reader.readLine().replace("	     \"Width\" : ", "").replace("\"", "").replace(",", "");
                if (Ellipse.class == c)
                    shape = new Ellipse(pos, color, fillColor, Integer.parseInt(sw));
                else if (Circle.class == c)
                    shape = new Circle(pos, color, fillColor, Integer.parseInt(sw));
                else if (Rectangle.class == c)
                    shape = new Rectangle(pos, color, fillColor, Integer.parseInt(sw));
                else if(Square.class == c)
                    shape = new Square(pos, color, fillColor, Integer.parseInt(sw));
                else if (c != null &&supportedShapes.contains(c)){
                    shape = (eg.edu.alexu.csd.oop.draw.Shape) c.getConstructor(Point.class, Color.class, Color.class, int.class).newInstance(pos, color,fillColor ,Integer.parseInt(sw));
                }
                if(shape != null) {
                    shape.getClass().getMethod("setWidth", int.class).invoke(shape, Integer.parseInt(w));
                    shape.getClass().getMethod("setHeight", int.class).invoke(shape, Integer.parseInt(h));
                }
            }
            if(shape != null){
                shape.getClass().getMethod("setUUID", int.class).invoke(shape, Integer.parseInt(uuid));
                arr.add(shape);
            }
            reader.readLine();
        }
        reader.close();
        return arr;
    }
}