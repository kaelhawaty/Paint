package eg.edu.alexu.csd.oop.draw.Front;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.*;

public class Front {
    JPanel contentPane;
    JButton[] colorPallet = new JButton[10];
    JButton colorPicker = new JButton("Color Picker"), cur = new JButton(), border = new JButton(), roundedRect, saveButton, loadButton;
    Color[] colors = {Color.BLACK,Color.BLUE, Color.GREEN, Color.RED , Color.MAGENTA, Color.GRAY, Color.ORANGE, Color.PINK,Color.CYAN, Color.YELLOW};
    BufferedImage[] image = new BufferedImage[10];
    JButton[] shapesImages = new JButton[6]; // Triangle, Circle, Ellipse, Rect, Square, Line;
    String names[] = {"Triangle", "Circle", "Ellipse", "Rect", "Square", "Line"};
    Boolean flag = true;// 1 for color button & 0 for border button
    Boolean roundFlag = true;
    ActionListener listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < colorPallet.length; ++i){
                if (e.getSource() == colorPallet[i]){
                    if(flag) {
                        cur.setBackground(colorPallet[i].getBackground());
                        curColor = cur.getBackground();
                    }else{
                        border.setBackground(colorPallet[i].getBackground());
                        borderColor = border.getBackground();
                    }
                }
            }
            if(e.getSource() == colorPicker) {
                Color color = JColorChooser.showDialog(null, "Pick your color!", Color.WHITE);
                if (color == null)
                    color = Color.WHITE;
                if(flag) {
                    cur.setBackground(color);
                    curColor = cur.getBackground();
                }else{
                    border.setBackground(color);
                    borderColor = border.getBackground();
                }
            }
            else if(e.getSource() == border)
                flag = false;
            else if (e.getSource() == cur)
                flag = true;
            else if(e.getSource() == roundedRect){
                if (roundFlag) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter(".jar", "JAR"));
                    chooser.setAcceptAllFileFilterUsed(false);
                    int option = chooser.showOpenDialog(contentPane);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = chooser.getSelectedFile();
                        engine.installPluginShape(selectedFile.getPath());
                        if(roundedClass != null) {
                            BufferedImage temp = null;
                            try {
                                temp = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "roundRect.png"));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            Icon i = new ImageIcon(temp);
                            roundedRect.setIcon(i);
                            roundFlag = false;
                        }
                    }
                }else{
                    curShape = "RoundRectangle";
                }
            }else if(e.getSource() == saveButton){
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Save File");
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(".xml", "XML"));
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(".json", "JSON"));
                chooser.setAcceptAllFileFilterUsed(false);
                int option = chooser.showSaveDialog(contentPane);
                if (option == JFileChooser.APPROVE_OPTION){
                    StringBuilder st = new StringBuilder();
                    st.append(chooser.getSelectedFile().getAbsolutePath());
                    st.append( chooser.getFileFilter().getDescription());
                    engine.save(st.toString());
                }
            }else if(e.getSource() == loadButton){
                JFileChooser chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(".xml", "XML"));
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(".json", "JSON"));
                chooser.setAcceptAllFileFilterUsed(false);
                int option = chooser.showOpenDialog(contentPane);
                if (option == JFileChooser.APPROVE_OPTION){
                    engine.load(chooser.getSelectedFile().getAbsolutePath());
                    canvas.removeAll();
                    engine.refresh(canvas.getGraphics());
                }
            }
            for(int i = 0; i < shapesImages.length; ++i){
                if(e.getSource() == shapesImages[i]){
                    curShape = names[i];
                }
            }
        }
    };
    public Front(JPanel contentPane) throws IOException {
        this.contentPane = contentPane;
        try {
            image[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Triangle.png"));
            image[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Circle.png"));
            image[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Elipse.png"));
            image[3] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Rect.png"));
            image[4] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Square.png"));
            image[5] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Line.png"));
            image[6] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "LoadRound.png"));
            image[7] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Save.png"));
            image[8] = ImageIO.read(getClass().getClassLoader().getResourceAsStream( "Load.png"));
        }catch( Exception e){
            e.printStackTrace();
        }
        /*

           Shapes

         */
        for(int i = 0; i < shapesImages.length; i++){
            shapesImages[i] = new JButton(new ImageIcon(image[i]));
            if(i == shapesImages.length-1)
                shapesImages[i].setBounds(796, 0, 80, 80);
            else
                shapesImages[i].setBounds(875, i*147, 108, 148);
            shapesImages[i].addActionListener(listener);
            contentPane.add(shapesImages[i]);
        }
         /*
         Color Pallet
         */
        int cnt = 0;
        for(int i = 0; i < colorPallet.length; i++ ){
            if(i != 0 && i%5 == 0){
                cnt++;
            }
            colorPallet[i] = new JButton();
            colorPallet[i].setSize(40,40);
            colorPallet[i].setBackground(colors[i]);
            colorPallet[i].setBorder(null);
            colorPallet[i].setLocation(40*(i%5), 40*cnt );
            colorPallet[i].addActionListener(listener);
            contentPane.add(colorPallet[i]);
        }
        colorPicker.setSize(120, 30);
        colorPicker.setLocation(230, 0);
        cur.setSize(50, 50);
        cur.setLocation(425, 25);
        cur.addActionListener(listener);
        roundedRect = new JButton(new ImageIcon(image[6]));
        roundedRect.setBounds(717, 0, 80,80);
        roundedRect.addActionListener(listener);
        saveButton = new JButton(new ImageIcon(image[7]));
        loadButton = new JButton(new ImageIcon(image[8]));
        saveButton.setBounds(480, 0, 40, 40);
        loadButton.setBounds(480, 40, 40, 40);
        saveButton.addActionListener(listener);
        loadButton.addActionListener(listener);
        border.setSize(50, 50);
        border.setLocation(400, 0);
        border.setBackground(Color.BLACK);
        cur.setBackground(Color.white);
        border.addActionListener(listener);
        colorPicker.addActionListener(listener);
        contentPane.add(colorPicker);
        contentPane.add(cur);
        contentPane.add(border);
        contentPane.add(roundedRect);
        contentPane.add(saveButton);
        contentPane.add(loadButton);
    }
}