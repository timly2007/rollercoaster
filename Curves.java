package com.example.rollercoaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

import static com.example.rollercoaster.Curve.position;

public class Curves {
    public static Curves curves = new Curves();
    public static Curves curves1 = new Curves(1);
    public static Curves curves2 = new Curves(2);
    public static Curves curves3 = new Curves(3);
    public static Curves curves4 = new Curves(4);


    // input curves here
    public static double f0f(double t) {
        double x = 10 * t;
        return x;
    }
    public static double g0f(double t) {
        double y = 25 * Math.pow(Math.E, - t * t);
        return y;
    }
    public static double h0f(double t) {
        double z = 2 * t * t;
        return z;
    }

    public static double f1i(double t) {
        double x = t * t;
        return x;
    }
    public static double g1i(double t) {
        double y = 2 * t;
        return y;
    }
    public static double h1i(double t) {
        double z = 3;
        return z;
    }

    public static double f1f(double t) {
        double x = curves1.getRotation()[0] * (f1i(t) - position(curves.getArr()[1][0], 11)[0])
                + curves1.getRotation()[1] * (g1i(t) - position(curves.getArr()[1][0], 11)[1])
                + curves1.getRotation()[2] * (h1i(t) - position(curves.getArr()[1][0], 11)[2])
                + position(curves.getArr()[0][1], 0)[0];
        return x;
    }
    public static double g1f(double t) {
        double y = curves1.getRotation()[3] * (f1i(t) - position(curves.getArr()[1][0], 11)[0])
                + curves1.getRotation()[4] * (g1i(t) - position(curves.getArr()[1][0], 11)[1])
                + curves1.getRotation()[5] * (h1i(t) - position(curves.getArr()[1][0], 11)[2])
                + position(curves.getArr()[0][1], 0)[1];
        return y;
    }
    public static double h1f(double t) {
        double z = curves1.getRotation()[6] * (f1i(t) - position(curves.getArr()[1][0], 11)[0])
                + curves1.getRotation()[7] * (g1i(t) - position(curves.getArr()[1][0], 11)[1])
                + curves1.getRotation()[8] * (h1i(t) - position(curves.getArr()[1][0], 11)[2])
                + position(curves.getArr()[0][1], 0)[2];
        return z;
    }
    public static double f2i(double t) {
        double x = t;
        return x;
    }
    public static double g2i(double t) {
        double y = 3 * Math.cos(t);
        return y;
    }
    public static double h2i(double t) {
        double z = 3 * Math.sin(t);
        return z;
    }

    public static double f2f(double t) {
        double x = curves2.getRotation()[0] * (f2i(t) - position(curves.getArr()[2][0], 12)[0])
                + curves2.getRotation()[1] * (g2i(t) - position(curves.getArr()[2][0], 12)[1])
                + curves2.getRotation()[2] * (h2i(t) - position(curves.getArr()[2][0], 12)[2])
                + position(curves.getArr()[1][1], 1)[0];
        return x;
    }
    public static double g2f(double t) {
        double y = curves2.getRotation()[3] * (f2i(t) - position(curves.getArr()[2][0], 12)[0])
                + curves2.getRotation()[4] * (g2i(t) - position(curves.getArr()[2][0], 12)[1])
                + curves2.getRotation()[5] * (h2i(t) - position(curves.getArr()[2][0], 12)[2])
                + position(curves.getArr()[1][1], 1)[1];
        return y;
    }
    public static double h2f(double t) {
        double z = curves2.getRotation()[6] * (f2i(t) - position(curves.getArr()[2][0], 12)[0])
                + curves2.getRotation()[7] * (g2i(t) - position(curves.getArr()[2][0], 12)[1])
                + curves2.getRotation()[8] * (h2i(t) - position(curves.getArr()[2][0], 12)[2])
                + position(curves.getArr()[1][1], 1)[2];
        return z;
    }

    public static double f3i(double t) {
        double x = Math.tan(t);
        return x;
    }
    public static double g3i(double t) {
        double y = 3 * t;
        return y;
    }
    public static double h3i(double t) {
        double z = Math.pow(2, t);
        return z;
    }

    public static double f3f(double t) {
        double x = curves3.getRotation()[0] * (f3i(t) - position(curves.getArr()[3][0], 13)[0])
                + curves3.getRotation()[1] * (g3i(t) - position(curves.getArr()[3][0], 13)[1])
                + curves3.getRotation()[2] * (h3i(t) - position(curves.getArr()[3][0], 13)[2])
                + position(curves.getArr()[2][1], 2)[0];
        return x;
    }
    public static double g3f(double t) {
        double y = curves3.getRotation()[3] * (f3i(t) - position(curves.getArr()[3][0], 13)[0])
                + curves3.getRotation()[4] * (g3i(t) - position(curves.getArr()[3][0], 13)[1])
                + curves3.getRotation()[5] * (h3i(t) - position(curves.getArr()[3][0], 13)[2])
                + position(curves.getArr()[2][1], 2)[1];
        return y;
    }
    public static double h3f(double t) {
        double z = curves3.getRotation()[6] * (f3i(t) - position(curves.getArr()[3][0], 13)[0])
                + curves3.getRotation()[7] * (g3i(t) - position(curves.getArr()[3][0], 13)[1])
                + curves3.getRotation()[8] * (h3i(t) - position(curves.getArr()[3][0], 13)[2])
                + position(curves.getArr()[2][1], 2)[2];
        return z;
    }

    public static double f4i(double t) {
        double x = 6 * Math.cos(t);
        return x;
    }
    public static double g4i(double t) {
        double y = 6 * Math.sin(t);
        return y;
    }
    public static double h4i(double t) {
        double z = 0;
        return z;
    }

    public static double f4f(double t) {
        double x = curves4.getRotation()[0] * (f4i(t) - position(curves.getArr()[4][0], 14)[0])
                + curves4.getRotation()[1] * (g4i(t) - position(curves.getArr()[4][0], 14)[1])
                + curves4.getRotation()[2] * (h4i(t) - position(curves.getArr()[4][0], 14)[2])
                + position(curves.getArr()[3][1], 3)[0];
        return x;
    }
    public static double g4f(double t) {
        double y = curves4.getRotation()[3] * (f4i(t) - position(curves.getArr()[4][0], 14)[0])
                + curves4.getRotation()[4] * (g4i(t) - position(curves.getArr()[4][0], 14)[1])
                + curves4.getRotation()[5] * (h4i(t) - position(curves.getArr()[4][0], 14)[2])
                + position(curves.getArr()[3][1], 3)[1];
        return y;
    }
    public static double h4f(double t) {
        double z = curves4.getRotation()[6] * (f4i(t) - position(curves.getArr()[4][0], 14)[0])
                + curves4.getRotation()[7] * (g4i(t) - position(curves.getArr()[4][0], 14)[1])
                + curves4.getRotation()[8] * (h4i(t) - position(curves.getArr()[4][0], 14)[2])
                + position(curves.getArr()[3][1], 3)[2];
        return z;
    }

    static double[] start = position(curves.getArr()[4][1], 4);
    static double[] startT = Curve.firstDerivative(curves.getArr()[4][1], 4);
    static double[] end = position(curves.getArr()[0][0], 0);
    static double[] endT = Curve.firstDerivative(curves.getArr()[0][0], 0);

    // cubic splines for going back to start
    public static double f5f(double t) {
        double c = startT[0];
        double d = start[0];
        double a = endT[0] - 2 * end[0] + c + 2 * d;
        double b = - endT[0] + 3 * end[0] - 2 * c - 3 * d;
        double x = a * Math.pow(t, 3) + b * Math.pow(t, 2) + c * t + d;
        return x;
    }
    public static double g5f(double t) {
        double c = startT[1];
        double d = start[1];
        double a = endT[1] - 2 * end[1] + c + 2 * d;
        double b = - endT[1] + 3 * end[1] - 2 * c - 3 * d;
        double y = a * Math.pow(t, 3) + b * Math.pow(t, 2) + c * t + d;
        return y;
    }
    public static double h5f(double t) {
        double c = startT[2];
        double d = start[2];
        double a = endT[2] - 2 * end[2] + c + 2 * d;
        double b = - endT[2] + 3 * end[2] - 2 * c - 3 * d;
        double z = a * Math.pow(t, 3) + b * Math.pow(t, 2) + c * t + d;
        return z;
    }

    private static double[][] arr = new double[6][2];
    private double[] rotation;

    public Curves() {

        // input curve intervals here
        double[][] interval = new double[6][2];
        interval[0][0] = -2;
        interval[0][1] = 0.4;
        interval[1][0] = -4;
        interval[1][1] = 5;
        interval[2][0] = 6;
        interval[2][1] = 18;
        interval[3][0] = 0;
        interval[3][1] = 1.4;
        interval[4][0] = 0;
        interval[4][1] = 1.4 * Math.PI;
        interval[5][0] = 0;
        interval[5][1] = 1;

        this.arr = interval;
    }

    public Curves(int curve) {

        // input curve intervals here
        double[][] interval = new double[6][2];
        interval[0][0] = -2;
        interval[0][1] = 0.4;
        interval[1][0] = -4;
        interval[1][1] = 5;
        interval[2][0] = 6;
        interval[2][1] = 18;
        interval[3][0] = 0;
        interval[3][1] = 1.4;
        interval[4][0] = 0;
        interval[4][1] = 1.4 * Math.PI;
        interval[5][0] = 0;
        interval[5][1] = 1;

        this.arr = interval;
        rotation = Curve.rotationMatrix(curve);
    }
    public double[][] getArr() {
        return arr;
    }
    public double[] getRotation() {
        return rotation;
    }

}