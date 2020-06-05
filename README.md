# Paint Project

## Description of the project
This **paint program** is a software graphics program allows the user to
draw different shapes.

**Project Features**:Ⅰ.supports various shapes such as: Line, Circle, Ellipse, Triangle,
                 Rectangle, Square, RoundRectangle.
                 Ⅱ. Drawing, Coloring, Moving and Resizing
                 Ⅲ.supports save and load using different file formats such as XML and
                 JSON
                 Ⅳ.supports redo and undo.
---

## Overview of the design

Simply we implemented 6 supported shapes as the picture illustrate
plus an additional shape which can be loaded dynamically called
“RoundRectangle”.

As shown in the UML Diagram below, We implement *Ellipse, Rectangle,
Triangle and Line* and used **OOP concepts** like inheritance for Square
and Circle since they both share the same properties respectively with
Rectangle and Ellipse

**For the GUI**, We have used the in-built java GUI library called “Swing”.
We have implemented multiple features such as

**Select:** We used a Geometry Handler class to check whether a point is
inside a Shape or no.

**Move:** For the selected shape you can drag it across the canvas this is
done by adding the change in x and y coordinates of the mouse to the
shape.

**Resize:** Simply implemented by redrawing the shape again but for the
triangle we took the simple approach of being able to move any of the 3
ends of the Triangle

**Delete:** You can delete any selected Shape.

**Coloring:** The program has two colors as illustrated into the two
squares, The front one is Fillcolor and the rear is the border color. You
can select one of them then quick select one of the colors in the top left
corner of the program or you can just click on Color picker to get a
wider range of colors.

**Stroke Width:** Every shape has Stroke width for its border you can
adjust that by moving the slider.

**Save and load buttons:** Choose either save or load then type the file
name and choose the file format.

**Dynamic Loading:** Lets you browse where the jar is placed and load it
then the button will become a new Shape option, We have used a
URLClassloader and some regex to read what classes are in the jar and
whether they implement the interface or not. We have attached a
testing jar for that.

**For the redo/undo**, We simply created an interface called Command
and made some classes that implement this interface such as
ShapeAddition, ShapeEdition.

**What ShapeAddition**simply does is that it takes a copy of all shapes
and stores before and after the command.
However in ShapeEdition it takes a copy of the object and replaces in
undo/redo. To find out which shape it is we created a UUID generator
to create an ID for each shape to distinctively distinguish the shapes
from each other.

---