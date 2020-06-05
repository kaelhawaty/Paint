package eg.edu.alexu.csd.oop.draw.Geometry;

import java.awt.*;

public  class GeometryHandler {
    private static double area(int x1, int y1, int x2, int y2, int x3, int y3)
    {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
    }
    public static boolean checkPointInRectangle(int xTop, int yTop, int width, int height, Point p){
        int xBottom = xTop+Math.abs(width);
        int yBottom = yTop+Math.abs(height);
        return xTop <= p.x && p.x <= xBottom && yTop <= p.y && p.y <= yBottom;
    }
    public static boolean checkPointInEllipse(int xTop, int yTop, int width, int height, Point p){
        int centerX= xTop+Math.abs(width)/2;
        int centerY = yTop +Math.abs(height)/2;
        return (double)(p.x - centerX)*(p.x - centerX)/(Math.abs(width/2))/(Math.abs(width/2)) + (double)(p.y - centerY)*(p.y - centerY)/(Math.abs(height/2))/(Math.abs(height/2)) <= 1;
    }
    public static boolean checkPointOnLine(int x1, int y1, int x2, int y2 , Point p){
        double slope = 1.0*(y2-y1)/(x2-x1);
        double slopeTemp = 1.0*(p.y - y1)/(p.x-x1);
        return Math.min(x1,x2) <= p.x && p.x <= Math.max(x1,x2) && Math.min(y1,y2) <= p.y && p.y <= Math.max(y1,y2) && Math.abs(slope-slopeTemp) <= 0.1;
    }
    public static boolean checkPointInTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Point p)
    {
        /* Calculate area of triangle ABC */
        double A = area(x1, y1, x2, y2, x3, y3);

        /* Calculate area of triangle PBC */
        double A1 = area (p.x, p.y, x2, y2, x3, y3);

        /* Calculate area of triangle PAC */
        double A2 = area (x1, y1, p.x, p.y, x3, y3);

        /* Calculate area of triangle PAB */
        double A3 = area (x1, y1, x2, y2, p.x, p.y);

        /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3);
    }

}
