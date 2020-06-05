package eg.edu.alexu.csd.oop.draw.Listeners;

import eg.edu.alexu.csd.oop.draw.Command;
import eg.edu.alexu.csd.oop.draw.Commands.ShapeEdition;
import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.draw.Shape;
import eg.edu.alexu.csd.oop.draw.Shapes.Rectangle;
import eg.edu.alexu.csd.oop.draw.Shapes.*;
import eg.edu.alexu.csd.oop.draw.Utils.StaticVars;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.*;

public class Motions implements MouseListener, MouseMotionListener {
    Engine eng;
    final Shape tmp[] = new Shape[1];
    boolean flag = false;
    boolean commandFlag = false;
    Point p1;
    Point currentSelectedPoint;
    Shape oldCopy;
    public Motions(Engine engine){
        this.eng = engine;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(curShape != null){
            switch (curShape) {
                case "Square":
                    tmp[0] = new Square(new Point(mouseEvent.getX(), mouseEvent.getY()), borderColor, curColor, strokeWidth);
                    eng.addShape(tmp[0]);
                    eng.refresh(canvas.getGraphics());
                    break;
                case "Rect":
                    tmp[0] = new Rectangle(new Point(mouseEvent.getX(), mouseEvent.getY()), borderColor, curColor, strokeWidth);
                    eng.addShape(tmp[0]);
                    eng.refresh(canvas.getGraphics());
                    break;
                case "Ellipse":
                    tmp[0] = new Ellipse(new Point(mouseEvent.getX(), mouseEvent.getY()), borderColor, curColor, strokeWidth);
                    eng.addShape(tmp[0]);
                    eng.refresh(canvas.getGraphics());
                    break;
                case "Circle":
                    tmp[0] = new Circle(new Point(mouseEvent.getX(), mouseEvent.getY()), borderColor, curColor, strokeWidth);
                    eng.addShape(tmp[0]);
                    eng.refresh(canvas.getGraphics());
                    break;
                case "Line":
                    p1 = new Point(mouseEvent.getX(), mouseEvent.getY());
                    tmp[0] = new Line(p1, p1, borderColor, strokeWidth);
                    eng.addShape(tmp[0]);
                    eng.refresh(canvas.getGraphics());
                    break;
                case "Triangle":
                    if(flag == true && curShape == "Triangle" && tmp[0] != null){
                        tmp[0].setPosition(mouseEvent.getPoint());
                        lastShapeDrawn.setPosition(mouseEvent.getPoint());
                        eng.refresh(canvas.getGraphics());
                        flag = false;
                        tmp[0] = null;
                        curShape = null;
                    }else {
                        flag = true;
                        p1 = new Point(mouseEvent.getX(), mouseEvent.getY());
                        tmp[0] = new Triangle(p1, p1, borderColor, curColor, strokeWidth);
                        eng.addShape(tmp[0]);
                        eng.refresh(canvas.getGraphics());
                    }
                    break;
                case "RoundRectangle":
                    try {
                        tmp[0] = (eg.edu.alexu.csd.oop.draw.Shape) roundedClass.getConstructor(Point.class, Color.class, Color.class, int.class).newInstance(new Point(mouseEvent.getX(), mouseEvent.getY()), StaticVars.borderColor, StaticVars.curColor, StaticVars.strokeWidth);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    eng.addShape(tmp[0]);
                    eng.refresh(canvas.getGraphics());
                    break;
            }

        }else {
            Shape[] shapes = eng.getShapes();
            selectedShape = null;
            for (Shape s : shapes) {
                boolean flag = false;
                try {
                    flag = (boolean) s.getClass().getMethod("checkInside", Point.class).invoke(s, mouseEvent.getPoint());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                if (flag) {
                    selectedShape = s;
                    try {
                        oldCopy = (Shape) s.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                    currentSelectedPoint = mouseEvent.getPoint();
                }
            }
            eng.refresh(canvas.getGraphics());
        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(commandFlag == true){
            commandFlag = false;
            Command e = null;
            try {
                e = new ShapeEdition(oldCopy, (Shape) selectedShape.clone());
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
            engine.addCommand(e);

        }
        if(curShape == "Triangle" && flag){

        }else{
            curShape = null;
            tmp[0] = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        if(curShape != null) {
            if (tmp[0] != null) {
                if (curShape == "Triangle" && flag) {
                    try {
                        lastShapeDrawn.getClass().getMethod("setSecondPoint", Point.class).invoke(lastShapeDrawn, mouseEvent.getPoint());
                        tmp[0].getClass().getMethod("setSecondPoint", Point.class).invoke(tmp[0], mouseEvent.getPoint());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                } else {
                    lastShapeDrawn.setPosition(mouseEvent.getPoint());
                    tmp[0].setPosition(mouseEvent.getPoint());
                }
                eng.refresh(canvas.getGraphics());
            }
        }else{
            if(selectedShape != null) {
                if (curOperation == "Move") {
                    commandFlag = true;
                    try {
                        selectedShape.getClass().getMethod("moveShape", int.class, int.class).invoke(selectedShape, mouseEvent.getX() - currentSelectedPoint.x, mouseEvent.getY() - currentSelectedPoint.y);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                else if(curOperation == "Resize"){
                    commandFlag = true;
                    if(selectedShape.getClass() == Triangle.class) {
                        try {
                            selectedShape.getClass().getMethod("computeResize", Point.class).invoke(selectedShape, currentSelectedPoint);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        selectedShape.getClass().getMethod("resizeShape", Point.class, Point.class).invoke(selectedShape, mouseEvent.getPoint(), currentSelectedPoint);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                currentSelectedPoint = mouseEvent.getPoint();
                eng.refresh(canvas.getGraphics());
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        if(flag == true && curShape == "Triangle"){
            tmp[0].setPosition(mouseEvent.getPoint());
            eng.refresh(canvas.getGraphics());
        }
    }
}
