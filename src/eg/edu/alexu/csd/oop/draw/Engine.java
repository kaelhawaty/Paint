package eg.edu.alexu.csd.oop.draw;


import eg.edu.alexu.csd.oop.draw.Commands.ShapeAddition;
import eg.edu.alexu.csd.oop.draw.Save.JSON;
import eg.edu.alexu.csd.oop.draw.Shapes.Rectangle;
import eg.edu.alexu.csd.oop.draw.Save.XML;
import eg.edu.alexu.csd.oop.draw.Shapes.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.*;

public class Engine implements DrawingEngine{
    public ArrayList<Shape> arr = new ArrayList<Shape>();
    Deque<Command> commands = new LinkedList<>();
    Stack<Command> redo = new Stack<>();
    List<Class<? extends Shape>> supportedShapes = new ArrayList<>();
    public Engine(){
        supportedShapes.add(Circle.class);
        supportedShapes.add(Ellipse.class);
        supportedShapes.add(Line.class);
        supportedShapes.add(Rectangle.class);
        supportedShapes.add(Square.class);
        supportedShapes.add(Triangle.class);

    }
    @Override
    public void refresh(Graphics canvas) {
        GUI.gui.repaint();
    }
    public ArrayList<Shape> cloneArr(ArrayList<Shape> list) {
        ArrayList<Shape> temp = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            try {
                temp.add((Shape)list.get(i).clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }
    @Override
    public void addShape(Shape shape) {
        ArrayList<Shape> temp = cloneArr(arr);
        arr.add(shape);
        ArrayList<Shape> temp2 =cloneArr(arr);
        lastShapeDrawn = temp2.get(temp2.size()-1);
        Command e = new ShapeAddition(temp, temp2);
        addCommand(e);
    }

    @Override
    public void removeShape(Shape shape) {
        ArrayList<Shape> temp = cloneArr(arr);
        for(int i = 0; i < arr.size(); i++){
            if(shape == arr.get(i)){
                arr.remove(i);
            }
        }
        Command e = new ShapeAddition(temp, cloneArr(arr));
        addCommand(e);
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
        ArrayList<Shape> temp = cloneArr(arr);
        for(int i = 0; i < arr.size(); i++){
            if(oldShape == arr.get(i)){
                arr.set(i, newShape);
            }
        }
        Command e = new ShapeAddition(temp, cloneArr(arr));
        addCommand(e);
    }

    @Override
    public Shape[] getShapes() {
        Shape[] shapes = new Shape[arr.size()];
        for(int i = 0; i < arr.size(); i++){
            shapes[i] = arr.get(i);
        }
        return shapes;
    }
    public void addCommand(Command e){
        commands.add(e);
        redo.clear();
        if(commands.size() == 21){
            commands.removeFirst();
        }
    }
    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
        return supportedShapes;
    }
    public void addSupportedShape(Class<? extends Shape> cls){
        supportedShapes.add(cls);
    }
    private char fS = File.separatorChar;
    private String getClassName(String fileName) {
        String newName = fileName.replace(fS, '.');
        newName = newName.replace('/', '.');
        return newName.substring(0, newName.length() - 6);
    }
    public void installPluginShape( String jarPath) {
        URL[] forLoader = new URL[0];
        try {
            forLoader = new URL[] { (new File(jarPath)).toURI().toURL()};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClassLoader loader = URLClassLoader.newInstance(forLoader,
                getClass().getClassLoader());

        String path = jarPath;

        JarInputStream jis = null;
        try {
            jis = new JarInputStream(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JarEntry entry = null;
        try {
            entry = jis.getNextJarEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (entry != null) {
            if (entry.getName().endsWith(".class")) {
                String className = getClassName(entry.getName());
                Class classLoaded = null;
                try {
                    classLoaded = Class.forName(className, true, loader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    if (!classLoaded.isInterface()
                            && !Modifier.isAbstract(classLoaded.getModifiers())
                            && classLoaded.newInstance() instanceof Shape) {
                        roundedClass = classLoaded;
                        this.addSupportedShape((Class<? extends Shape>) roundedClass);
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            try {
                entry = jis.getNextJarEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            jis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void undo() {
        if(commands.size() != 0){
            Command e = commands.getLast();
            commands.removeLast();
            redo.add(e);
            e.undo();
        }
        refresh(canvas.getGraphics());
    }

    @Override
    public void redo() {
        if(!redo.empty()){
            Command e = redo.peek();
            redo.pop();
            commands.addLast(e);
            e.redo();
        }
        refresh(canvas.getGraphics());
    }

    @Override
    public void save(String path) {
        try {
            if (path.endsWith(".xml"))
                XML.save(this.getShapes(), path);
            else if(path.endsWith(".json"))
                JSON.save(this.getShapes(), path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(String path) {
        try {
            arr.clear();
            if(path.endsWith(".xml")){
                XML xml = new XML();
                Shape shapes[] = xml.load(path);
                for(Shape s : shapes)
                    if(s != null) {
                        arr.add(s);
                    }
            }
            else if(path.endsWith(".json")){
                JSON json = new JSON();
                arr = json.load(path);
            }
            selectedShape = null;
        } catch (Exception e) {
             System.out.println("Some shapes weren't Loaded due to not finding their classes, Import their plugins first!");
        }
    }
}
