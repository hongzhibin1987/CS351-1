import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class Main extends Application {
    // to set the Color of the lines
    private Color color;

    // circle times tables Factor
    private double factorNum = 0;

    // The increment of times tables number
    private double incrementNum = 0.1;

    // The delay second rate, inital 0.15
    private double FPS = 0.15;

    // Number of times user click Random button
    private int countClick = 0;

    /**
     * To format a double into a string with the form of 2 digit
     * after the decimal value.
     */
    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Setting up the canvas, root, and scene.
        double WIDTH = 1200;
        double HEIGHT = 800;
        //distance between labels and boxes
        double SPACING = 10;

        primaryStage.setTitle("Times-Table Visualization");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Pane pane1 = new Pane(canvas);
        Scene scene1 = new Scene(pane1);
        primaryStage.setScene(scene1);
        primaryStage.show();

        //Create a circle object and get the number of points
        Visualization circle = new Visualization();

        //Create a vertical box to hold all button/sliders/textfield
        VBox overallLayout = new VBox(SPACING);
        overallLayout.setLayoutX(750);
        overallLayout.setLayoutY(200);

        //Creating Start and Pause buttons , and stack in Hbox
        HBox startPauseBox = new HBox(SPACING);
        Button startButton = new Button(" Start ");
        Button pauseButton = new Button ("Pause");
        Button randomButtton = new Button("Random Pictures");
        startPauseBox.getChildren().addAll(startButton, pauseButton, randomButtton);
        Button jumpButton = new Button("Jump to");



        //Create slider to set increments for times tables number
        VBox incrementVBox = new VBox(SPACING);
        Label incrementLabel = new Label("Increment: " + decimalFormat.format(incrementNum));
        Slider incrementSlider = new Slider(0,5,0.1);
        incrementSlider.setBlockIncrement(0.1f);
        incrementSlider.setMajorTickUnit(0.25f);
        incrementSlider.setShowTickLabels(true);
        incrementSlider.setShowTickMarks(true);
        incrementVBox.getChildren().addAll(incrementLabel,incrementSlider);

        //Create slider to set the delay second (fps setting)
        VBox fpsVBox = new VBox(SPACING);
        Label fpsText = new Label("Delay by (Seconds) : " + decimalFormat.format(FPS));
        Slider fpsSlider = new Slider(0,0.5,0.05);
        fpsSlider.setShowTickLabels(true);
        fpsSlider.setShowTickMarks(true);
        fpsSlider.setBlockIncrement(0.01f);
        fpsSlider.setMajorTickUnit(0.05f);
        fpsVBox.getChildren().addAll(fpsText,fpsSlider);

        //Create slider to set number of points
        Label numPointsLabel = new Label("Number of points: " + circle.getnumPoints());
        Slider numPtsSlider = new Slider(0,360,360);
        numPtsSlider.setShowTickMarks(true);
        numPtsSlider.setShowTickLabels(true);
        numPtsSlider.setBlockIncrement(1);
        numPtsSlider.setMajorTickUnit(25);
        numPtsSlider.setPrefWidth(180);

        //make increment vbox and delay vbox in a line
        HBox allSliders = new HBox(SPACING);
        allSliders.getChildren().addAll(fpsVBox,incrementVBox);

        //Creating a textfield for user to set number of points to jump to
        Label jumpLabel = new Label("Jump Settings : Enter the integer below and press the 'Jump to' botton");
        TextField setNumPointTo = new TextField();

        //Hint for label time table number
        setNumPointTo.setPromptText("Number of Points");

        //setNumPointJump textfield listener
        setNumPointTo.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                setNumPointTo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        //Creating a textfield for user to set time table number to jump to
        TextField setFactorTo = new TextField();

        setFactorTo.setPromptText("Time Table Number");

        //setFactorJump textfield listener
        setFactorTo.textProperty().addListener((observable, oldValue, newValue) -> {
            //Making sure that user can only insert integer value
            if (!newValue.matches("\\d*")) {
                setFactorTo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        //Make horizontal allign for jump control settings
        HBox allJumpControl = new HBox(SPACING);

        allJumpControl.getChildren().addAll(jumpButton,setNumPointTo,setFactorTo);

        // Add all the created nodes into the Vertical box controller
        overallLayout.getChildren().addAll(allSliders, jumpLabel,
                allJumpControl,numPointsLabel, numPtsSlider, startPauseBox);

        // Creating a label to show the current
        // number of points and times tables number
        Label tableNumberInfo = new Label("Number of points: " + circle.getnumPoints() +
                "\nTimes Tables Number: "+ decimalFormat.format(factorNum));
        tableNumberInfo.setLayoutX(750);
        tableNumberInfo.setLayoutY(700);

        //Listener of the Increment Slider
        incrementSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            incrementNum = incrementSlider.getValue();
            //circle.setFactor(0.0);
            incrementLabel.setText("Increment By: " + decimalFormat.format(incrementNum));
        });

        //Listener of the FPS Slider
        fpsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            FPS = fpsSlider.getValue();
            fpsText.setText("Delay by (Seconds) : " + decimalFormat.format(FPS));
        });



        //Setting up the animation.
        AnimationTimer timer = new AnimationTimer() {

            private long nextTime = 0;

            @Override
            public void handle(long now) {
                pane1.getChildren().remove(tableNumberInfo);

                //Drawing the lines and circle points after the specified second.
                if(now - nextTime >= 1000_000_000 * fpsSlider.getValue()){

                    // Incrementing the times tables number
                    factorNum = factorNum + incrementNum;
                    int RandomColor;
                    RandomColor = (int) (factorNum % 50);
                    //Cycling random color
                    switch(RandomColor)
                    {

                        default : color = Color.color(Math.random(), Math.random(), Math.random());

                    }



                    //Draw the lines with its color, and show in the root
                    Visualization circlePage = new Visualization(factorNum, circle.getnumPoints(), color);
                    circlePage.show(pane1);
                    nextTime = now;
                }
                //Update the times tables number text
                tableNumberInfo.setText("Number of points: " + circle.getnumPoints() +
                        "\nTimes Tables Number: "+ decimalFormat.format(factorNum));
                pane1.getChildren().add(tableNumberInfo);
            }
        };

        //Listener of the Number of points slider
        numPtsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            timer.stop();

            //set the number of points based on slider value
            circle.setnumPoints((int)numPtsSlider.getValue());

            //updating the number of point label
            numPointsLabel.setText("Number of points: " + circle.getnumPoints());
        });


        //start the animation when start button is clicked
        startButton.setOnAction(event -> {
            pane1.getChildren().clear();

            //update the slider value
            numPtsSlider.setValue(circle.getnumPoints());
            timer.start();

            //Upating the UI
            pane1.getChildren().addAll(overallLayout);
        });

        //Stop the animation when pause button is clicked
        pauseButton.setOnAction(event -> {
            timer.stop();
        });

        //Jump to the specified number of points
        //and time table number when clicked.
        jumpButton.setOnAction(event -> {
            timer.stop();
            pane1.getChildren().clear();

            //Draw the circle based on the specified value
            generateCircle(Integer.parseInt(setFactorTo.getText()),
                    Integer.parseInt(setNumPointTo.getText()),
                    Color.YELLOWGREEN,pane1);

            //Set the circle current no. of points and times table no.
            circle.setnumPoints(Integer.parseInt(setNumPointTo.getText()));
            factorNum = Integer.parseInt(setFactorTo.getText());

            //Set the slider value to be as what have specified by user
            numPtsSlider.setValue(Integer.parseInt(setNumPointTo.getText()));

            //Updating label based on current points
            numPointsLabel.setText("Number of points: " + circle.getnumPoints());
            tableNumberInfo.setText("Number of points: " + circle.getnumPoints() +
                    "\nTimes Tables Number: "+ decimalFormat.format(factorNum));

            //Add all controllers to root
            pane1.getChildren().add(tableNumberInfo);
            pane1.getChildren().addAll(overallLayout);
        });

        //Favorite image tour button
        randomButtton.setOnAction(event -> {
            pane1.getChildren().clear();
            countClick += 1; //Count how many times user click the button
            timer.stop();

            // show the some of pictures.
            if (countClick % 10 == 0){
                generateCircle(6,160,Color.color(Math.random(), Math.random(), Math.random()),pane1);
                circle.setFactor(6);
                circle.setnumPoints(160);

            } else if (countClick % 10 == 1) {
                generateCircle(87,200,Color.color(Math.random(), Math.random(), Math.random()),pane1);
                circle.setFactor(87);
                circle.setnumPoints(200);

            } else if (countClick % 10 == 2) {
                generateCircle(33,260,Color.color(Math.random(), Math.random(), Math.random()),pane1);
                circle.setFactor(33);
                circle.setnumPoints(260);

            } else if (countClick % 10 == 3) {
                generateCircle(66,150, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(66);
                circle.setnumPoints(150);

            } else if (countClick % 10 == 4) {
                generateCircle(8,250, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(8);
                circle.setnumPoints(250);

            }else if (countClick % 10 == 5) {
                generateCircle(33,350, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(76);
                circle.setnumPoints(150);

            }else if (countClick % 10 == 6) {
                generateCircle(76,300, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(76);
                circle.setnumPoints(300);

            }else if (countClick % 10 == 7) {
                generateCircle(76,200, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(74);
                circle.setnumPoints(150);

            }else if (countClick % 10 == 8) {
                generateCircle(150,150, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(76);
                circle.setnumPoints(150);

            }else if (countClick % 10 == 9) {
                generateCircle(76,150, Color.color(Math.random(), Math.random(), Math.random()), pane1);
                circle.setFactor(76);
                circle.setnumPoints(150);

            }


            //Update the times tables number text
            tableNumberInfo.setText("Number of points: " + circle.getnumPoints() +
                    "\nTimes Tables Number: "+ decimalFormat.format(circle.getFactor()));

            //Add all controllers to root
            pane1.getChildren().addAll(tableNumberInfo, overallLayout);
        });

        //Add all controllers to root
        pane1.getChildren().addAll(tableNumberInfo,overallLayout);
    }

    /**
     * This method is to create a Circle with the specified factors,
     * number of points, color, and showing it to root.
     * @param factor is the times tables number
     * @param points is the number of points on the circle
     * @param color is the color of the lines
     * @param pane is the root
     */
    public void generateCircle(int factor, int points, Color color, Pane pane){
        Visualization circle = new Visualization(factor, points, color);
        circle.show(pane);
    }
}
