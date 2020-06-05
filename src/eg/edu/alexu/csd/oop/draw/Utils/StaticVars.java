package eg.edu.alexu.csd.oop.draw.Utils;

import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.draw.GUI;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;

public class StaticVars {
    public static GUI.DrawCanvas canvas;
    public static Color curColor = Color.WHITE, borderColor = Color.BLACK;
    public static  String curShape;
    public static Shape selectedShape;
    public static Engine engine;
    public static int strokeWidth = 2;
    public static String curOperation;
    public static Class roundedClass;
    public static Shape lastShapeDrawn;
}
