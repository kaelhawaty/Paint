package eg.edu.alexu.csd.oop.draw.Save;

import eg.edu.alexu.csd.oop.draw.Shape;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.engine;

public class XML {

    public static void save(Shape[] shapes, String path) throws Exception {
        XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(path)));
        encoder.writeObject(shapes);
        encoder.close();
    }

    public Shape[] load(String path) throws Exception {
        ClassLoader cl = this.getClass().getClassLoader();
        List<Class<? extends Shape>> supportedShapes = engine.getSupportedShapes();
        if(supportedShapes != null && supportedShapes.size() != 0) {
            cl = supportedShapes.get(supportedShapes.size() - 1).getClassLoader();
        }
        XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(path)), null, null, cl);
        Shape[] shapes = (Shape[]) decoder.readObject();
        decoder.close();
        return shapes;
    }
}