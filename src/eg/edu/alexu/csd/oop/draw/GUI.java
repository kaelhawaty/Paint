package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.Front.Front;
import eg.edu.alexu.csd.oop.draw.Listeners.KeyboardListener;
import eg.edu.alexu.csd.oop.draw.Listeners.Motions;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.*;

public class GUI extends JFrame{
    private JPanel contentPane;
    JMenuItem mntmMove, mntmResize, mntmDelete, mntmSave, mntmLoad, mntmExit;
    public GUI() throws IOException {
        inizialize();
        engine = new Engine();
        Front front = new Front(contentPane);

        Motions e = new Motions(engine);
        canvas.addMouseListener(e);
        canvas.addMouseMotionListener(e);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyboardListener());

    }
    public class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background
            setBackground(Color.white);  // set background color for this JPanel
            Shape[] shapes = engine.getShapes();
            for (Shape s : shapes) {
                s.draw(g);
            }
        }
    }
    public void inizialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1000, 800);
        setResizable(false);
        setTitle("Paint");
        MenuListeners menuListener = new MenuListeners();
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        setJMenuBar(menuBar);

        JMenu mnMenuFile = new JMenu("File");
        menuBar.add(mnMenuFile);

        mntmSave = new JMenuItem("Save");
        mnMenuFile.add(mntmSave);
        mntmSave.addActionListener(menuListener);
        mntmLoad = new JMenuItem("Load");
        mnMenuFile.add(mntmLoad);
        mntmLoad.addActionListener(menuListener);
        mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(menuListener);
        mnMenuFile.add(mntmExit);

        JMenu mnMenuEdit = new JMenu("Edit");
        menuBar.add(mnMenuEdit);

        mntmMove = new JMenuItem("Move");
        mnMenuEdit.add(mntmMove);
        mntmMove.addActionListener(menuListener);
        mntmDelete = new JMenuItem("Delete");
        mnMenuEdit.add(mntmDelete);
        mntmDelete.addActionListener(menuListener);
        mntmResize = new JMenuItem("Resize");
        mnMenuEdit.add(mntmResize);
        mntmResize.addActionListener(menuListener);

        JSlider slider = new JSlider();
        //slider.setPaintTicks(true);
        slider.setValue(2);
        slider.setBounds(200, 50, 200, 25);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                strokeWidth = slider.getValue();
                engine.refresh(canvas.getGraphics());
            }
        });
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        canvas = new DrawCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.setBounds(0, 80, 875, 655);
        contentPane.add(canvas);
        contentPane.add(slider);
    }
    private class MenuListeners implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(actionEvent.getSource() == mntmMove)
                curOperation = "Move";
            else if (actionEvent.getSource() == mntmResize)
                curOperation = "Resize";
            else if(actionEvent.getSource() == mntmDelete){
                if(selectedShape != null){
                    engine.removeShape(selectedShape);
                    engine.refresh(canvas.getGraphics());
                    selectedShape = null;
                }
            }else if(actionEvent.getSource() == mntmSave){
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
            }else if(actionEvent.getSource() == mntmLoad){
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
            }else if(actionEvent.getSource() == mntmExit){
                System.exit(0);
            }
        }
    }
    public static GUI gui;
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    gui = new GUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gui.setVisible(true);
            }
        });
    }
}
