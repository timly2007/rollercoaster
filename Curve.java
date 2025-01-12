package com.example.rollercoaster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.shape.*;
import javafx.scene.paint.*;


public class Curve {

    static double radToDeg = 180 / Math.PI;
    static double deltaT = 0.00001;
    public static double magnitude(double[] a) {
        return (a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);
    }
    public static double dot(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }
    public static double[] cross(double[] a, double[] b) {
        double[] cross = new double[3];
        cross[0] = a[1] * b[2] - a[2] * b[1];
        cross[1] = - a[0] * b[2] + a[2] * b[0];
        cross[2] = a[0] * b[1] - a[1] * b[0];

        return cross;
    }
    public static double acos(double x) {
        if (x >= 0) {
            return 1.0472 - 1.1 * (x - 0.5) - (0.3/2) * Math.pow(x - 0.5, 2) - (10.0 / 6) * Math.pow(x - 0.5, 3) - (60.0 / 24) * Math.pow(x - 0.5, 4);
        } else {
            return 2.0944 - 1.1 * (x + 0.5) + (0.3/2) * Math.pow(x + 0.5, 2) - (10.0 / 6) * Math.pow(x + 0.5, 3) + (60.0 / 24) * Math.pow(x + 0.5, 4);
        }
    }
    public static double asin(double x) {
        return 1.57079 - acos(x);
    }
    public static double[] mMProduct(double[] a, double[] b) {
        double[] product = {
                a[0] * b[0] + a[1] * b[3] + a[2] * b[6], a[0] * b[1] + a[1] * b[4] + a[2] * b[7], a[0] * b[2] + a[1] * b[5] + a[2] * b[8],
                a[3] * b[0] + a[4] * b[3] + a[5] * b[6], a[3] * b[1] + a[4] * b[4] + a[5] * b[7], a[3] * b[2] + a[4] * b[5] + a[5] * b[8],
                a[6] * b[0] + a[7] * b[3] + a[8] * b[6], a[6] * b[1] + a[7] * b[4] + a[8] * b[7], a[6] * b[2] + a[7] * b[5] + a[8] * b[8]};
        return product;
    }

    public static double[] position(double t, int curve) {
        double x = 0;
        double y = 0;
        double z = 0;
        if (curve == 0 || curve == 10) {
            x = Curves.f0f(t);
            y = Curves.g0f(t);
            z = Curves.h0f(t);
        } else if (curve == 1) {
            x = Curves.f1f(t);
            y = Curves.g1f(t);
            z = Curves.h1f(t);
        } else if (curve == 11) {
            x = Curves.f1i(t);
            y = Curves.g1i(t);
            z = Curves.h1i(t);
        } else if (curve == 2) {
            x = Curves.f2f(t);
            y = Curves.g2f(t);
            z = Curves.h2f(t);
        } else if (curve == 12) {
            x = Curves.f2i(t);
            y = Curves.g2i(t);
            z = Curves.h2i(t);
        } else if (curve == 3) {
            x = Curves.f3f(t);
            y = Curves.g3f(t);
            z = Curves.h3f(t);
        } else if (curve == 13) {
            x = Curves.f3i(t);
            y = Curves.g3i(t);
            z = Curves.h3i(t);
        } else if (curve == 4) {
            x = Curves.f4f(t);
            y = Curves.g4f(t);
            z = Curves.h4f(t);
        } else if (curve == 14) {
            x = Curves.f4i(t);
            y = Curves.g4i(t);
            z = Curves.h4i(t);
        } else if (curve == 5) {
            x = Curves.f5f(t);
            y = Curves.g5f(t);
            z = Curves.h5f(t);
        }
        double[] positionOutput;
        positionOutput = new double[]{x, y, z};

        return positionOutput;
    }
    public static double[] firstDerivative(double t, int curve) {
        double[] d1 = new double[3];
        for (int i = 0; i < 3; i++) {
            d1[i] = (1 / deltaT) * (position(t + deltaT, curve)[i] - position(t, curve)[i]);
        }
        return d1;
    }
    public static double[] secondDerivative(double t, int curve) {
        double[] d2 = new double[3];
        for (int i = 0; i < 3; i++) {
            d2[i] = (1 / deltaT) * (firstDerivative(t + deltaT, curve)[i] - firstDerivative(t, curve)[i]);
        }
        return d2;
    }
    public static double[] thirdDerivative(double t, int curve) {
        double[] d3 = new double[3];
        for (int i = 0; i < 3; i++) {
            d3[i] = (1 / deltaT) * (secondDerivative(t + deltaT, curve)[i] - secondDerivative(t, curve)[i]);
        }
        return d3;
    }
    public static double[] tangent(double t, int curve) {
        double[] tangent = firstDerivative(t, curve);
        double magnitude = Math.sqrt(tangent[0] * tangent[0] + tangent[1] * tangent[1] + tangent[2] * tangent[2]);

        tangent[0] /= magnitude;
        tangent[1] /= magnitude;
        tangent[2] /= magnitude;

        return tangent;
    }
    public static double[] normal(double t, int curve) {
        double[] normal = cross(binormal(t, curve), tangent(t, curve));
        return normal;
    }
    public static double[] binormal(double t, int curve) {
        double[] binormal = cross(firstDerivative(t, curve), secondDerivative(t, curve));
        double magnitude = Math.sqrt(binormal[0] * binormal[0] + binormal[1] * binormal[1] + binormal[2] * binormal[2]);

        binormal[0] /= magnitude;
        binormal[1] /= magnitude;
        binormal[2] /= magnitude;

        return binormal;
    }
    public static double torsion(double t, int curve) {
        double[] rRR = cross(firstDerivative(t, curve), secondDerivative(t, curve));
        return dot(rRR, thirdDerivative(t, curve)) / dot(rRR, rRR);
    }
    public static double[] rotationMatrix(int curve) {
        Curves c = new Curves();
        double t1 = c.getArr()[curve - 1][1];
        double t2 = c.getArr()[curve][0];

        double[] frame1 = {
                tangent(t1, curve - 1)[0], normal(t1, curve - 1)[0], binormal(t1, curve - 1)[0],
                tangent(t1, curve - 1)[1], normal(t1, curve - 1)[1], binormal(t1, curve - 1)[1],
                tangent(t1, curve - 1)[2], normal(t1, curve - 1)[2], binormal(t1, curve - 1)[2]};

        double[] frame2Transpose = {
                tangent(t2, curve + 10)[0], tangent(t2, curve + 10)[1], tangent(t2, curve + 10)[2],
                normal(t2, curve + 10)[0], normal(t2, curve + 10)[1], normal(t2, curve + 10)[2],
                binormal(t2, curve + 10)[0], binormal(t2, curve + 10)[1], binormal(t2, curve + 10)[2]};

        return mMProduct(frame1, frame2Transpose);
    }

    public static double[] findAngles(double[] t) {
        double angleY, angleZ, incline;
        double xzHypotenuse = Math.sqrt(t[0] * t[0] + t[2] * t[2]);

        incline = acos(t[1]);

        double sign = 0;
        if (t[2] != 0) {
            sign = Math.signum(t[2]);
        }

        if (xzHypotenuse == 0) {
            angleY = 0;
            angleZ = 0;
        } else {
            angleY = radToDeg * sign * asin(t[0] / xzHypotenuse);

            // double[] planarT = {t[0], 0, t[2]};
            // angleZ = - Math.signum(dot(planarT, t)) * radToDeg * incline;
            angleZ = radToDeg * sign * incline;
        }
        double[] angle = {angleY, angleZ};
        return angle;
    }

    public static double[] cameraAngles(double t, int curve) {
        double angleY, angleX;
        double[] tan = tangent(t, curve);

        double xzHypotenuse = Math.sqrt(tan[0] * tan[0] + tan[2] * tan[2]);

        if (tan[0] >= 0) {
            angleY = radToDeg * Math.acos(tan[2] / xzHypotenuse);
        } else {
            angleY = - radToDeg * Math.acos(tan[2] / xzHypotenuse);
        }

        angleX = - radToDeg * Math.asin(tan[1]);
        double[] angle = {angleY, angleX};
        return angle;
    }

    static double rate = 0.003;
    public static double findTime(double a, double[] b) {
        for (int i = 0; i < b.length; i++) {
            if (a <= b[i]) {
                return rate * i;
            }
        }
        return -1;
    }
    public Curve() {

    }
}