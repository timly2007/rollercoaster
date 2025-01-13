package com.example.rollercoaster;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.animation.AnimationTimer;

import java.util.Arrays;

public class RollerCoaster extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Build the Scene Graph
        Group root = new Group();

        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        root.getChildren().add(camera);

        // Use a SubScene
        SubScene subScene = new SubScene(root, 1440,900);
        subScene.setFill(Color.FLORALWHITE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);

        primaryStage.setResizable(false);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();

        // creating rail from cylinders
        Cylinder[] rail = new Cylinder[10000];

        // creating supports
        Cylinder[] support = new Cylinder[2000];

        int indexRail = 0;
        int indexSupport = 0;
        double[][] distanceTracker = new double[6][20005];
        double[] maxDistance = new double[6];

        Curves c = new Curves();
        double cylinderLength = 1;

        for (int j = 0; j < 6; j++) {
            int intDistance = 0;
            double distance = 0;
            int fiveDistance = 0;
            int index = 0;
            for (double i = c.getArr()[j][0]; i <= c.getArr()[j][1]; i = i + Curve.rate) {
                distance += Curve.rate * Math.sqrt((Math.pow(Curve.firstDerivative(i, j)[0], 2)
                        + Math.pow(Curve.firstDerivative(i, j)[1], 2)
                        + Math.pow(Curve.firstDerivative(i, j)[2], 2)));
                distanceTracker[j][index] = distance;
                index++;

                if (distance > intDistance + cylinderLength) {
                    rail[indexRail] = new Cylinder(cylinderLength / 4, cylinderLength, 12);
                    rail[indexRail].setMaterial(new PhongMaterial(Color.BLACK));
                    rail[indexRail].setDrawMode(DrawMode.LINE);

                    rail[indexRail].setTranslateX((Curve.position(i, j))[0]);
                    rail[indexRail].setTranslateY((Curve.position(i, j))[1]);
                    rail[indexRail].setTranslateZ((Curve.position(i, j))[2]);

                    rail[indexRail].getTransforms().addAll(
                            new Rotate(Curve.findAngles(Curve.tangent(i, j))[0], Rotate.Y_AXIS),
                            new Rotate(Curve.findAngles(Curve.tangent(i, j))[1], Rotate.X_AXIS));
                    root.getChildren().add(rail[indexRail]);

                    indexRail++;
                    intDistance++;
                }

                if (distance > fiveDistance + 5) {
                    support[indexSupport] = new Cylinder(cylinderLength / 6, Curve.position(i, j)[1] - cylinderLength / 4, 6);
                    support[indexSupport].setMaterial(new PhongMaterial(Color.BLACK));
                    support[indexSupport].setDrawMode(DrawMode.LINE);

                    support[indexSupport].setTranslateX((Curve.position(i, j))[0]);
                    support[indexSupport].setTranslateY((Curve.position(i, j))[1] / 2);
                    support[indexSupport].setTranslateZ((Curve.position(i, j))[2]);
                    root.getChildren().add(support[indexSupport]);

                    indexSupport++;
                    fiveDistance += 5;
                }
            }
            System.out.println();
            maxDistance[j] = distance;

            rail[indexRail] = new Cylinder(cylinderLength / 4, cylinderLength, 8);
            rail[indexRail].setMaterial(new PhongMaterial(Color.BLACK));
            rail[indexRail].setDrawMode(DrawMode.LINE);

            rail[indexRail].setTranslateX((Curve.position(c.getArr()[j][1], j))[0]);
            rail[indexRail].setTranslateY((Curve.position(c.getArr()[j][1], j))[1]);
            rail[indexRail].setTranslateZ((Curve.position(c.getArr()[j][1], j))[2]);

            rail[indexRail].getTransforms().addAll(
                    new Rotate(Curve.findAngles(Curve.tangent(c.getArr()[j][1], j))[0], Rotate.Y_AXIS),
                    new Rotate(Curve.findAngles(Curve.tangent(c.getArr()[j][1], j))[1], Rotate.X_AXIS));
            root.getChildren().add(rail[indexRail]);

            indexRail++;
        }

        // boxes for testing
        Sphere[] testBox = new Sphere[6];
        for (int i = 0; i < 6; i++) {
            testBox[i] = new Sphere(10);
            testBox[i].setDrawMode(DrawMode.LINE);
        }
        testBox[0].setMaterial(new PhongMaterial(Color.NAVY));
        testBox[0].setTranslateZ(-50);
        testBox[1].setMaterial(new PhongMaterial(Color.DODGERBLUE));
        testBox[1].setTranslateZ(50);
        testBox[2].setMaterial(new PhongMaterial(Color.SEAGREEN));
        testBox[2].setTranslateX(-50);
        testBox[3].setMaterial(new PhongMaterial(Color.MEDIUMSEAGREEN));
        testBox[3].setTranslateX(50);
        testBox[4].setMaterial(new PhongMaterial(Color.INDIGO));
        testBox[4].setTranslateY(-50);
        testBox[5].setMaterial(new PhongMaterial(Color.BLUEVIOLET));
        testBox[5].setTranslateY(50);

        camera.setTranslateX(Curve.position(c.getArr()[0][0], 0)[0]);
        camera.setTranslateY(Curve.position(c.getArr()[0][0], 0)[1]);
        camera.setTranslateZ(Curve.position(c.getArr()[0][0], 0)[2]);

        Box cameraTest = new Box(2, 2, 3);
        cameraTest.setDrawMode(DrawMode.LINE);
        cameraTest.setMaterial(new PhongMaterial(Color.RED));
        root.getChildren().add(cameraTest);

        int tileSize = 4;
        Box[][] floorTile = new Box[(200 / tileSize) + 1][(200 / tileSize) + 1];
        // creating floor
        for (int i = -100; i <= 100; i = i + tileSize) {
            for (int j = -100; j <= 100; j = j + tileSize) {
                floorTile[(i / tileSize) + (100 / tileSize)][(j / tileSize) + (100 / tileSize)] = new Box(tileSize, 0.4, tileSize);
                floorTile[(i / tileSize) + (100 / tileSize)][(j / tileSize) + (100 / tileSize)].setDrawMode(DrawMode.LINE);
                floorTile[(i / tileSize) + (100 / tileSize)][(j / tileSize) + (100 / tileSize)].setMaterial(new PhongMaterial(Color.LIGHTGREY));
                root.getChildren().add(floorTile[(i / tileSize) + (100 / tileSize)][(j / tileSize) + (100 / tileSize)]);
                floorTile[(i / tileSize) + (100 / tileSize)][(j / tileSize) + (100 / tileSize)].setTranslateX(i);
                floorTile[(i / tileSize) + (100 / tileSize)][(j / tileSize) + (100 / tileSize)].setTranslateZ(j);
            }
        }

        // buttons & UI
        Rectangle overlay = new Rectangle(400, 900);
        overlay.setStyle("-fx-background-color: black; -fx-border-width: 0px");
        overlay.setOpacity(0.3);
        group.getChildren().add(overlay);

        Button pause = new Button("Pause");
        Font font = Font.font("Georgia", FontWeight.BOLD, 24);
        pause.setFont(font);
        pause.setTranslateX(5);
        pause.setTranslateY(815);
        pause.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        pause.setOpacity(0.5);
        group.getChildren().add(pause);

        Button settings = new Button("⚙");
        settings.setFont(font);
        settings.setTranslateX(1380);
        settings.setTranslateY(5);
        settings.setStyle("-fx-text-fill: white; -fx-border-color: white; -fx-background-color: black; -fx-border-width: 3px");
        settings.setOpacity(0.5);
        group.getChildren().add(settings);

        Text timeElapsed = new Text();
        timeElapsed.setFont(font);
        timeElapsed.setStyle("-fx-text-fill: black");
        timeElapsed.setText("Time Elapsed: ");
        timeElapsed.setX(30);
        timeElapsed.setY(30);
        group.getChildren().add(timeElapsed);

        Text distanceTravelled = new Text();
        distanceTravelled.setFont(font);
        distanceTravelled.setStyle("-fx-text-fill: black");
        distanceTravelled.setText("Total Distance: ");
        distanceTravelled.setX(30);
        distanceTravelled.setY(80);
        group.getChildren().add(distanceTravelled);

        Text speedLabel = new Text();
        speedLabel.setFont(font);
        speedLabel.setStyle("-fx-text-fill: black");
        speedLabel.setText("Speed (m/s) vs. Time");
        speedLabel.setX(30);
        speedLabel.setY(150);
        group.getChildren().add(speedLabel);

        Text accLabel = new Text();
        accLabel.setFont(font);
        accLabel.setStyle("-fx-text-fill: black");
        accLabel.setText("Acceleration (G's) vs. Time");
        accLabel.setX(30);
        accLabel.setY(450);
        group.getChildren().add(accLabel);

        Text speedAxis = new Text();
        speedAxis.setFont(font);
        speedAxis.setStyle("-fx-text-fill: black");
        speedAxis.setText("0             4             8 ");
        speedAxis.setX(-87);
        speedAxis.setY(310);
        group.getChildren().add(speedAxis);
        speedAxis.setRotate(-90);

        Text accAxis = new Text();
        accAxis.setFont(font);
        accAxis.setStyle("-fx-text-fill: black");
        accAxis.setText("-2   -1    0    1    2");
        accAxis.setX(-70);
        accAxis.setY(615);
        group.getChildren().add(accAxis);
        accAxis.setRotate(-90);

        Line vAxis = new Line(0, 0, 0, 200);
        vAxis.setStrokeWidth(3);
        group.getChildren().add(vAxis);
        vAxis.setTranslateX(40);
        vAxis.setTranslateY(200);

        Line hAxis = new Line(0, 0, 300, 0);
        hAxis.setStrokeWidth(3);
        group.getChildren().add(hAxis);
        hAxis.setTranslateX(40);
        hAxis.setTranslateY(400);

        Line vAxis2 = new Line(0, 0, 0, 200);
        vAxis2.setStrokeWidth(3);
        group.getChildren().add(vAxis2);
        vAxis2.setTranslateX(40);
        vAxis2.setTranslateY(500);

        Line hAxis2 = new Line(0, 0, 300, 0);
        hAxis2.setStrokeWidth(3);
        group.getChildren().add(hAxis2);
        hAxis2.setTranslateX(40);
        hAxis2.setTranslateY(600);

        // initial camera translation
        if (true) {
            camera.getTransforms().addAll(
                    new Translate(10, 48, -56),
                    new Rotate(180, Rotate.Z_AXIS),
                    new Rotate(-40, Rotate.X_AXIS));
        } else {
            camera.getTransforms().addAll(
                    new Translate(0, 0.75, 0));
        }
        double initialHeight = Curve.position(0, 0)[1] + 7.3;

        Circle[] graphCircle = new Circle[3000];
        Circle[] graphCircle2 = new Circle[3000];
        Circle[] graphCircle3 = new Circle[3000];
        Circle[] graphCircle4 = new Circle[3000];
        for (int i = 0; i < 3000; i++) {
            graphCircle[i] = new Circle(1, Color.BLUE);
            group.getChildren().add(graphCircle[i]);
            graphCircle[i].setTranslateX(3000);

            graphCircle2[i] = new Circle(1, Color.GREEN);
            group.getChildren().add(graphCircle2[i]);
            graphCircle2[i].setTranslateX(3000);

            graphCircle3[i] = new Circle(1, Color.RED);
            group.getChildren().add(graphCircle3[i]);
            graphCircle3[i].setTranslateX(3000);

            graphCircle4[i] = new Circle(1, Color.PURPLE);
            group.getChildren().add(graphCircle4[i]);
            graphCircle4[i].setTranslateX(3000);
        }

        //animation
        AnimationTimer animationTimer = new AnimationTimer() {
            final double gravity = 9.8;
            double dragCoefficient = 0.05;
            double potential = 0;
            double velocity = 0;
            double distance = 0;
            double prevDistance = 0;
            int curve = 0;
            double prevRotateX = 0;
            double prevRotateY = 0;
            double totalDistance = 0;
            boolean moving = true;
            double time = 0;
            double elapsed = 0;
            int offCounter = 0;
            int opacity = 0;
            double speed = 0;
            int circleIndex = 0;
            double curvature = 0;
            double cAcceleration = 0;
            double lAcceleration = 0;
            double bAcceleration = 0;
            double prevSpeed = 0;
            double count = 0;
            double dragWork = 0;
            double dragFriction = 0;
            double angleX = 0;
            double angleY = 0;
            double frictionCoefficient = 0.001;
            double angularVelocity = 0;
            double angularAcceleration = 0;
            double prevAngularVelocity = 0;
            double angularPotential = 0;
            final double passengerDistance = 0.6;

            public void handle(long now) {
                if (distance >= maxDistance[curve] && curve != 5) {
                    distance = 0;
                    if (curve == 5) {
                        curve = -1;
                    }
                    curve++;
                }

                if (curve < 6) {
                    if (moving) {
                        time = Curve.findTime(distance, distanceTracker[curve]);

                        cameraTest.setTranslateX(Curve.position(time + c.getArr()[curve][0], curve)[0]);
                        cameraTest.setTranslateY(Curve.position(time + c.getArr()[curve][0], curve)[1]);
                        cameraTest.setTranslateZ(Curve.position(time + c.getArr()[curve][0], curve)[2]);

                        angleY = Curve.cameraAngles(time + c.getArr()[curve][0], curve)[0];
                        angleX = Curve.cameraAngles(time + c.getArr()[curve][0], curve)[1];

                        cameraTest.getTransforms().addAll(
                                new Rotate(-prevRotateX, Rotate.X_AXIS),
                                new Rotate(-prevRotateY, Rotate.Y_AXIS),
                                new Rotate(angleY, Rotate.Y_AXIS),
                                new Rotate(angleX, Rotate.X_AXIS));
                    }

                    double height = Curve.position(time + c.getArr()[curve][0], curve)[1];

                    prevAngularVelocity = angularVelocity;
                    angularVelocity = (angleY - prevRotateY) / 0.0174;
                    angularAcceleration = (angularVelocity - prevAngularVelocity) / 0.0174;

                    prevRotateY = Curve.cameraAngles(time + c.getArr()[curve][0], curve)[0];
                    prevRotateX = Curve.cameraAngles(time + c.getArr()[curve][0], curve)[1];

                    dragWork += (0.007 * velocity) * dragCoefficient * velocity;
                    dragFriction += (0.007 * velocity) * frictionCoefficient * gravity * Math.abs(Curve.sin(Math.PI * angleX / 180));
                    angularPotential = (0.25) * angularVelocity * angularVelocity * passengerDistance * passengerDistance;
                    potential = Math.max(0, gravity * (initialHeight - height) - dragWork - dragFriction - angularPotential);
                    velocity = Math.sqrt(2 * potential);
                    prevDistance = distance;
                    prevSpeed = speed;

                    if (curve == 0 && time < 2) {
                        velocity = 10;
                        dragCoefficient = 0;
                        frictionCoefficient = 0;
                    } else {
                        dragCoefficient = 0.05;
                        frictionCoefficient = 0.5 * Math.pow(10, -10);
                    }
                    if (curve == 5 && (distance + 0.007 * velocity) >= maxDistance[curve]) {
                        velocity = 0;
                        moving = false;
                    }

                    distance += 0.007 * velocity;
                    totalDistance += 0.007 * velocity;
                    elapsed += 0.0174; // determined experimentally

                    speed = 0.007 * velocity / 0.0174;

                    graphCircle[circleIndex].setTranslateX(40 + (elapsed / 34.5) * 300);
                    graphCircle[circleIndex].setTranslateY(400 - (speed / 8) * 200);

                    graphCircle2[circleIndex].setTranslateX(40 + (elapsed / 34.5) * 300);
                    graphCircle2[circleIndex].setTranslateY(600 - (cAcceleration / 15) * 100);

                    graphCircle3[circleIndex].setTranslateX(40 + (elapsed / 34.5) * 300);
                    graphCircle3[circleIndex].setTranslateY(600 - (lAcceleration / 15) * 100);

                    graphCircle4[circleIndex].setTranslateX(40 + (elapsed / 34.5) * 300);
                    graphCircle4[circleIndex].setTranslateY(600 - (Math.sqrt(cAcceleration * cAcceleration + lAcceleration * lAcceleration) / 15) * 100);
                    circleIndex++;

                    double[] crossed = Curve.cross(Curve.firstDerivative(time + c.getArr()[curve][0], curve), Curve.secondDerivative(time + c.getArr()[curve][0], curve));
                    curvature = Curve.magnitude(crossed) / (Math.pow(Curve.magnitude(Curve.firstDerivative(time + c.getArr()[curve][0], curve)), 3));

                    cAcceleration = speed * speed * curvature;
                    lAcceleration = (speed - prevSpeed) / 0.0174;
                    bAcceleration = Math.abs(angularAcceleration * passengerDistance * Math.PI / 180);

                    System.out.println(bAcceleration);
                }

                timeElapsed.setText("Time Elapsed (s): " + ((int) (1000 * elapsed) / (double) 1000));
                distanceTravelled.setText("Total Distance (m): " + ((int) (1000 * totalDistance) / (double) 1000));


                if (!moving) {
                    count++;
                    if (count > 180) {
                        count = 0;
                        offCounter = 0;
                        moving = true;
                        distance = 0;
                        prevDistance = 0;
                        curve = 0;
                        totalDistance = 0;
                        time = 0;
                        elapsed = 0;
                        circleIndex = 0;
                        dragWork = 0;
                        dragFriction = 0;
                        angularVelocity = 0;
                        for (int i = 0; i < 3000; i++) {
                            graphCircle[i].setTranslateX(3000);
                            graphCircle2[i].setTranslateX(3000);
                            graphCircle3[i].setTranslateX(3000);
                            graphCircle4[i].setTranslateX(3000);
                        }
                    }
                }

                settings.setOnAction((ActionEvent actionEvent3) -> {
                    if (opacity == 0) {
                        opacity = 1;
                    } else {
                        opacity = 0;
                    }
                });

                pause.setOpacity(opacity * 0.5);
                overlay.setOpacity(opacity * 0.3);
                timeElapsed.setOpacity(opacity);
                distanceTravelled.setOpacity(opacity);
                speedLabel.setOpacity(opacity);
                vAxis.setOpacity(opacity);
                hAxis.setOpacity(opacity);
                accLabel.setOpacity(opacity);
                vAxis2.setOpacity(opacity);
                hAxis2.setOpacity(opacity);
                speedAxis.setOpacity(opacity);
                accAxis.setOpacity(opacity);
                for (int i = 0; i < 3000; i++) {
                    graphCircle[i].setOpacity(opacity);
                    graphCircle2[i].setOpacity(opacity);
                    graphCircle3[i].setOpacity(opacity);
                    graphCircle4[i].setOpacity(opacity);
                }
            }
        };

        int[] pauseVariable = {-1};

        animationTimer.start();
        pause.setOnAction((ActionEvent actionEvent3) -> {

            if(pauseVariable[0] == 1) {
                animationTimer.start();
                pauseVariable[0] = pauseVariable[0] * -1;
                pause.setText("Pause");
            } else {
                animationTimer.stop();
                pauseVariable[0] = pauseVariable[0] * -1;
                pause.setText("Play");
            }
        });
    }

    /**
     * Java main for when running without JavaFX launcher
     */
    public static void main(String[] args) {
        launch(args);
    }
}
