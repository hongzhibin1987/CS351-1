import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.lang.*;
import static java.lang.Math.*;

public class Visualization {
    // Increment of factor, inital is 0.0
    private double factorNum = 0.0;

    // Number of points,  360.
    private int numPoints = 360;


    // Radius of the main circle
    private final int RADIUS = 300;
    // center point of the circle
    private final int CIRCLE_CENTER = 350;

    // group for points and lines
    private Group CIRCLES = new Group();


    /**
     * Constuctor of CirclePage
     * Create circle points and lines
     * @param nFactor the number of times tables
     * @param numPoints the number of points
     * @param color the color for the lines
     */
    public Visualization(double nFactor, int numPoints, Color color) {
        this.numPoints = numPoints;
        this.factorNum = nFactor;

        Circle circle = new Circle(CIRCLE_CENTER,
                CIRCLE_CENTER,RADIUS+1,Color.WHITE);
        CIRCLES.getChildren().add(circle);

        /**
         * This creates the circle point calculated
         * based on the number of points specified
         */
        for(int i=0; i < numPoints; i++){

            double THETA = i * (2 * PI/numPoints) + PI;
            //X axis
            double x = RADIUS * cos(THETA);
            //Y axis
            double y = RADIUS * sin(THETA);

            //Draw the circle with with the calculated x,y coordinate
            Circle circlePoints = new Circle(x + CIRCLE_CENTER,
                    y + CIRCLE_CENTER, 3, Color.BLACK);
            CIRCLES.getChildren().add(circlePoints);
        }

        /**
         * Draw the lines base on the calculated factor
         * by determining the current and next x,y coordinate
         */
        for(int i=0; i< numPoints; i++){
            if(factorNum != 0){
                // Calculate the current and next angle
                double THETA = i * (2 * PI/numPoints) + PI;
                double nextTHETA = (i * factorNum) * (2 * PI/numPoints) + PI;


                //The first point in the circle
                double firstX = (RADIUS * Math.cos(THETA)) + CIRCLE_CENTER;
                double firstY = CIRCLE_CENTER + (RADIUS * Math.sin(THETA));

                //and the next Point the connect circle
                double nextX =  (CIRCLE_CENTER + (RADIUS * Math.cos(nextTHETA)));
                double nextY =  (CIRCLE_CENTER + (RADIUS * Math.sin(nextTHETA)));

                //line
                Line line = new Line(firstX, firstY, nextX, nextY);
                //color to the line
                line.setStroke(color);
                //add the line to the Circle group
                CIRCLES.getChildren().addAll(line);
            }
        }
    }

    /**
     * Empty contructor to access the method from this class
     */
    public Visualization() {}


    /**
     * set the increment factor
     * @param factorNum the increment value
     */
    public void setFactor(double factorNum) {
        this.factorNum = factorNum;
    }

    /**
     * Get the current time table number
     * @return the current time table number
     */
    public double getFactor() {
        return factorNum;
    }

    /**
     * Get the number of points
     * @return number of points
     */
    public int getnumPoints() {
        return numPoints;
    }

    /**
     * Set the number of points in the circle
     * @param numPoints number of points
     */
    public void setnumPoints(int numPoints) {
        this.numPoints = numPoints;
    }



    /**
     * Add the CIRCLE group consisted of circle
     * points and lines into the root
     * @param root pane root
     */
    public void show(Pane root) {
        root.getChildren().addAll(CIRCLES);
    }
}
