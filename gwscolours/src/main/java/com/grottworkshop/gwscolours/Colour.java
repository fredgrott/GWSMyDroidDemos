/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Matthew York
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.grottworkshop.gwscolours;

import android.graphics.Color;

/**
 * The type Colour.
 * Created by fgrott on 8/27/2015.
 */
@SuppressWarnings("unused")
public class Colour extends Color {

    //Color Scheme Enumeration (for color scheme generation)
    public enum ColorScheme {
        ColorSchemeAnalagous, ColorSchemeMonochromatic, ColorSchemeTriad, ColorSchemeComplementary
    }

    public enum ColorDistanceFormula {
        ColorDistanceFormulaCIE76, ColorDistanceFormulaCIE94, ColorDistanceFormulaCIE2000
    }

    // ///////////////////////////////////
    // 4 Color Scheme from Color
    // ///////////////////////////////////

    /**
     * Creates an int[] of 4 Colors that complement the Color.
     *
     * @param type ColorSchemeAnalagous, ColorSchemeMonochromatic,
     *             ColorSchemeTriad, ColorSchemeComplementary
     * @return ArrayList<Integer>
     */
    public static int[] colorSchemeOfType(int color, ColorScheme type) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        switch (type) {
            case ColorSchemeAnalagous:
                return Colour.analagousColors(hsv);
            case ColorSchemeMonochromatic:
                return Colour.monochromaticColors(hsv);
            case ColorSchemeTriad:
                return Colour.triadColors(hsv);
            case ColorSchemeComplementary:
                return Colour.complementaryColors(hsv);
            default:
                return null;
        }
    }

    public static int[] analagousColors(float[] hsv) {
        float[] CA1 = {Colour.addDegrees(hsv[0], 15),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.05)};
        float[] CA2 = {Colour.addDegrees(hsv[0], 30),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.1)};
        float[] CB1 = {Colour.addDegrees(hsv[0], -15),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.05)};
        float[] CB2 = {Colour.addDegrees(hsv[0], -30),
                (float) (hsv[1] - 0.05), (float) (hsv[2] - 0.1)};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static int[] monochromaticColors(float[] hsv) {
        float[] CA1 = {hsv[0], hsv[1], hsv[2] / 2};
        float[] CA2 = {hsv[0], hsv[1] / 2, hsv[2] / 3};
        float[] CB1 = {hsv[0], hsv[1] / 3, hsv[2] * 2 / 3};
        float[] CB2 = {hsv[0], hsv[1], hsv[2] * 4 / 5};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static int[] triadColors(float[] hsv) {

        float[] CA1 = {Colour.addDegrees(hsv[0], 120), hsv[1],
                hsv[2]};
        float[] CA2 = {Colour.addDegrees(hsv[0], 120),
                hsv[1] * 7 / 6, (float) (hsv[2] - 0.05)};
        float[] CB1 = {Colour.addDegrees(hsv[0], 240), hsv[1],
                hsv[2]};
        float[] CB2 = {Colour.addDegrees(hsv[0], 240),
                hsv[1] * 7 / 6, (float) (hsv[2] - 0.05)};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static int[] complementaryColors(float[] hsv) {
        float[] CA1 = {hsv[0], hsv[1] * 5 / 7, hsv[2]};
        float[] CA2 = {hsv[0], hsv[1], hsv[2] * 4 / 5};
        float[] CB1 = {Colour.addDegrees(hsv[0], 180), hsv[1],
                hsv[2]};
        float[] CB2 = {Colour.addDegrees(hsv[0], 180),
                hsv[1] * 5 / 7, hsv[2]};

        return new int[]{Color.HSVToColor(CA1), Color.HSVToColor(CA2),
                Color.HSVToColor(CB1), Color.HSVToColor(CB2)};
    }

    public static float addDegrees(float addDeg, float staticDeg) {
        staticDeg += addDeg;
        if (staticDeg > 360) {
            return staticDeg - 360;
        } else if (staticDeg < 0) {
            return -1 * staticDeg;
        } else {
            return staticDeg;
        }
    }

    /**
     * Returns black or white, depending on which color would contrast best with the provided color.
     *
     * @param color (Color)
     * @return int
     */
    public static int blackOrWhiteContrastingColor(int color) {
        int[] rgbaArray = new int[]{Colour.red(color), Colour.green(color), Colour.blue(color)};
        double a = 1 - ((0.00299 * (double) rgbaArray[0]) + (0.00587 * (double) rgbaArray[1]) + (0.00114 * (double) rgbaArray[2]));
        return a < 0.5 ? Colour.BLACK : Colour.WHITE;
    }


    /**
     * This method will create a color instance that is the exact opposite color from another color on the color wheel. The same saturation and brightness are preserved, just the hue is changed.
     *
     * @param color (Color)
     * @return int
     */
    public static int complementaryColor(int color) {
        float[] hsv = new float[3];
        Colour.colorToHSV(color, hsv);
        float newH = Colour.addDegrees(180, hsv[0]);
        hsv[0] = newH;

        return Colour.HSVToColor(hsv);
    }

    // CMYK

    /**
     * Color to cMYK.
     *
     * @param color the color int
     * @return float [ ]
     */
    public static float[] colorToCMYK(int color) {
        float r = Colour.red(color);
        float g = Colour.green(color);
        float b = Colour.blue(color);
        float c = 1 - r / 255;
        float m = 1 - g / 255;
        float y = 1 - b / 255;
        float k = Math.min(1, Math.min(c, Math.min(m, y)));
        if (k == 1) {
            c = 0;
            m = 0;
            y = 0;
        } else {
            c = (c - k) / (1 - k);
            m = (m - k) / (1 - k);
            y = (y - k) / (1 - k);
        }

        return new float[]{c, m, y, k};
    }


    /**
     * CMYK to color.
     *
     * @param cmyk the cmyk array
     * @return color
     */
    public static int CMYKtoColor(float[] cmyk) {
        float c = cmyk[0] * (1 - cmyk[3]) + cmyk[3];
        float m = cmyk[1] * (1 - cmyk[3]) + cmyk[3];
        float y = cmyk[2] * (1 - cmyk[3]) + cmyk[3];
        return Colour.rgb((int) ((1 - c) * 255), (int) ((1 - m) * 255), (int) ((1 - y) * 255));
    }

    /**
     * Color to cIE _ lAB.
     *
     * @param color the color int
     * @return double[]
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public static double[] colorToCIE_LAB(int color) {
        // Convert Color to XYZ format first
        double r = Colour.red(color) / 255.0;
        double g = Colour.green(color) / 255.0;
        double b = Colour.blue(color) / 255.0;

        // Create deltaRGB
        r = (r > 0.04045) ? Math.pow((r + 0.055) / 1.055, 2.40) : (r / 12.92);
        g = (g > 0.04045) ? Math.pow((g + 0.055) / 1.055, 2.40) : (g / 12.92);
        b = (b > 0.04045) ? Math.pow((b + 0.055) / 1.055, 2.40) : (b / 12.92);

        // Create XYZ
        double X = r * 41.24 + g * 35.76 + b * 18.05;
        double Y = r * 21.26 + g * 71.52 + b * 7.22;
        double Z = r * 1.93 + g * 11.92 + b * 95.05;

        // Convert XYZ to L*a*b*
        X = X / 95.047;
        Y = Y / 100.000;
        Z = Z / 108.883;
        X = (X > Math.pow((6.0 / 29.0), 3.0)) ? Math.pow(X, 1.0 / 3.0) : (1 / 3) * Math.pow((29.0 / 6.0), 2.0) * X + 4 / 29.0;
        Y = (Y > Math.pow((6.0 / 29.0), 3.0)) ? Math.pow(Y, 1.0 / 3.0) : (1 / 3) * Math.pow((29.0 / 6.0), 2.0) * Y + 4 / 29.0;
        Z = (Z > Math.pow((6.0 / 29.0), 3.0)) ? Math.pow(Z, 1.0 / 3.0) : (1 / 3) * Math.pow((29.0 / 6.0), 2.0) * Z + 4 / 29.0;
        double CIE_L = 116 * Y - 16;
        double CIE_a = 500 * (X - Y);
        double CIE_b = 200 * (Y - Z);
        return new double[]{CIE_L, CIE_a, CIE_b};
    }

    /**
     * CIE _ lab to color.
     *
     * @param cie_lab the double[]
     * @return color
     */
    public static int CIE_LabToColor(double[] cie_lab) {
        double L = cie_lab[0];
        double A = cie_lab[1];
        double B = cie_lab[2];
        double Y = (L + 16.0) / 116.0;
        double X = A / 500 + Y;
        double Z = Y - B / 200;
        X = (Math.pow(X, 3.0) > 0.008856) ? Math.pow(X, 3.0) : (X - 4 / 29.0) / 7.787;
        Y = (Math.pow(Y, 3.0) > 0.008856) ? Math.pow(Y, 3.0) : (Y - 4 / 29.0) / 7.787;
        Z = (Math.pow(Z, 3.0) > 0.008856) ? Math.pow(Z, 3.0) : (Z - 4 / 29.0) / 7.787;
        X = X * .95047;
        Y = Y * 1.00000;
        Z = Z * 1.08883;

        // Convert XYZ to RGB
        double R = X * 3.2406 + Y * -1.5372 + Z * -0.4986;
        double G = X * -0.9689 + Y * 1.8758 + Z * 0.0415;
        double _B = X * 0.0557 + Y * -0.2040 + Z * 1.0570;
        R = (R > 0.0031308) ? 1.055 * (Math.pow(R, (1 / 2.4))) - 0.055 : R * 12.92;
        G = (G > 0.0031308) ? 1.055 * (Math.pow(G, (1 / 2.4))) - 0.055 : G * 12.92;
        _B = (_B > 0.0031308) ? 1.055 * (Math.pow(_B, (1 / 2.4))) - 0.055 : _B * 12.92;
        return Colour.rgb((int) (R * 255), (int) (G * 255), (int) (_B * 255));
    }

    public static double distanceBetweenColors(int colorA, int colorB) {
        return distanceBetweenColorsWithFormula(colorA, colorB, ColorDistanceFormula.ColorDistanceFormulaCIE94);
    }

    public static double distanceBetweenColorsWithFormula(int colorA, int colorB, ColorDistanceFormula formula) {
        double[] lab1 = Colour.colorToCIE_LAB(colorA);
        double[] lab2 = Colour.colorToCIE_LAB(colorB);
        double L1 = lab1[0];
        double A1 = lab1[1];
        double B1 = lab1[2];
        double L2 = lab2[0];
        double A2 = lab2[1];
        double B2 = lab2[2];

        // CIE76 first
        if (formula == ColorDistanceFormula.ColorDistanceFormulaCIE76) {
            double distance = Math.sqrt(Math.pow((L1 - L2), 2) + Math.pow((A1 - A2), 2) + Math.pow((B1 - B2), 2));
            return distance;
        }

        // More Common Variables
        double kL = 1;
        double kC = 1;
        double kH = 1;
        double k1 = 0.045;
        double k2 = 0.015;
        double deltaL = L1 - L2;
        double C1 = Math.sqrt((A1 * A1) + (B1 * B1));
        double C2 = Math.sqrt((A2 * A2) + (B2 * B2));
        double deltaC = C1 - C2;
        double deltaH = Math.sqrt(Math.pow((A1 - A2), 2.0) + Math.pow((B1 - B2), 2.0) - Math.pow(deltaC, 2.0));
        double sL = 1;
        double sC = 1 + k1 * (Math.sqrt((A1 * A1) + (B1 * B1)));
        double sH = 1 + k2 * (Math.sqrt((A1 * A1) + (B1 * B1)));

        // CIE94
        if (formula == ColorDistanceFormula.ColorDistanceFormulaCIE94) {
            return Math.sqrt(Math.pow((deltaL / (kL * sL)), 2.0) + Math.pow((deltaC / (kC * sC)), 2.0) + Math.pow((deltaH / (kH * sH)), 2.0));
        }

        // CIE2000
        // More variables
        double deltaLPrime = L2 - L1;
        double meanL = (L1 + L2) / 2;
        double meanC = (C1 + C2) / 2;
        double aPrime1 = A1 + A1 / 2 * (1 - Math.sqrt(Math.pow(meanC, 7.0) / (Math.pow(meanC, 7.0) + Math.pow(25.0, 7.0))));
        double aPrime2 = A2 + A2 / 2 * (1 - Math.sqrt(Math.pow(meanC, 7.0) / (Math.pow(meanC, 7.0) + Math.pow(25.0, 7.0))));
        double cPrime1 = Math.sqrt((aPrime1 * aPrime1) + (B1 * B1));
        double cPrime2 = Math.sqrt((aPrime2 * aPrime2) + (B2 * B2));
        double cMeanPrime = (cPrime1 + cPrime2) / 2;
        double deltaCPrime = cPrime1 - cPrime2;
        double hPrime1 = Math.atan2(B1, aPrime1);
        double hPrime2 = Math.atan2(B2, aPrime2);
        hPrime1 = hPrime1 % RAD(360.0);
        hPrime2 = hPrime2 % RAD(360.0);
        double deltahPrime = 0;
        if (Math.abs(hPrime1 - hPrime2) <= RAD(180.0)) {
            deltahPrime = hPrime2 - hPrime1;
        } else {
            deltahPrime = (hPrime2 <= hPrime1) ? hPrime2 - hPrime1 + RAD(360.0) : hPrime2 - hPrime1 - RAD(360.0);
        }
        double deltaHPrime = 2 * Math.sqrt(cPrime1 * cPrime2) * Math.sin(deltahPrime / 2);
        double meanHPrime = (Math.abs(hPrime1 - hPrime2) <= RAD(180.0)) ? (hPrime1 + hPrime2) / 2 : (hPrime1 + hPrime2 + RAD(360.0)) / 2;
        double T = 1 - 0.17 * Math.cos(meanHPrime - RAD(30.0)) + 0.24 * Math.cos(2 * meanHPrime) + 0.32 * Math.cos(3 * meanHPrime + RAD(6.0)) - 0.20 * Math.cos(4 * meanHPrime - RAD(63.0));
        sL = 1 + (0.015 * Math.pow((meanL - 50), 2)) / Math.sqrt(20 + Math.pow((meanL - 50), 2));
        sC = 1 + 0.045 * cMeanPrime;
        sH = 1 + 0.015 * cMeanPrime * T;
        double Rt = -2 * Math.sqrt(Math.pow(cMeanPrime, 7) / (Math.pow(cMeanPrime, 7) + Math.pow(25.0, 7))) * Math.sin(RAD(60.0) * Math.exp(-1 * Math.pow((meanHPrime - RAD(275.0)) / RAD(25.0), 2)));

        // Finally return CIE2000 distance
        return Math.sqrt(Math.pow((deltaLPrime / (kL * sL)), 2) + Math.pow((deltaCPrime / (kC * sC)), 2) + Math.pow((deltaHPrime / (kH * sH)), Rt * (deltaC / (kC * sC)) * (deltaHPrime / (kH * sH))));
    }

    private static double RAD(double degree) {
        return degree * Math.PI / 180;
    }

    // Predefined Colors
    // System Colors
    public static int infoBlueColor() {
        return Colour.rgb(47, 112, 225);
    }

    public static int successColor() {
        return Colour.rgb(83, 215, 106);
    }

    public static int warningColor() {
        return Colour.rgb(221, 170, 59);
    }

    public static int dangerColor() {
        return Colour.rgb(229, 0, 15);
    }

    // Whites
    public static int antiqueWhiteColor() {
        return Colour.rgb(250, 235, 215);
    }

    public static int oldLaceColor() {
        return Colour.rgb(253, 245, 230);
    }

    public static int ivoryColor() {
        return Colour.rgb(255, 255, 240);
    }

    public static int seashellColor() {
        return Colour.rgb(255, 245, 238);
    }

    public static int ghostWhiteColor() {
        return Colour.rgb(248, 248, 255);
    }

    public static int snowColor() {
        return Colour.rgb(255, 250, 250);
    }

    public static int linenColor() {
        return Colour.rgb(250, 240, 230);
    }

    // Grays
    public static int black25PercentColor() {
        return Colour.rgb(64, 64, 64);
    }

    public static int black50PercentColor() {
        return Colour.rgb(128, 128, 128);
    }

    public static int black75PercentColor() {
        return Colour.rgb(192, 192, 192);
    }

    public static int warmGrayColor() {
        return Colour.rgb(133, 117, 112);
    }

    public static int coolGrayColor() {
        return Colour.rgb(118, 122, 133);
    }

    public static int charcoalColor() {
        return Colour.rgb(34, 34, 34);
    }

    // Blues
    public static int tealColor() {
        return Colour.rgb(28, 160, 170);
    }

    public static int steelBlueColor() {
        return Colour.rgb(103, 153, 170);
    }

    public static int robinEggColor() {
        return Colour.rgb(141, 218, 247);
    }

    public static int pastelBlueColor() {
        return Colour.rgb(99, 161, 247);
    }

    public static int turquoiseColor() {
        return Colour.rgb(112, 219, 219);
    }

    public static int skyBlueColor() {
        return Colour.rgb(0, 178, 238);
    }

    public static int indigoColor() {
        return Colour.rgb(13, 79, 139);
    }

    public static int denimColor() {
        return Colour.rgb(67, 114, 170);
    }

    public static int blueberryColor() {
        return Colour.rgb(89, 113, 173);
    }

    public static int cornflowerColor() {
        return Colour.rgb(100, 149, 237);
    }

    public static int babyBlueColor() {
        return Colour.rgb(190, 220, 230);
    }

    public static int midnightBlueColor() {
        return Colour.rgb(13, 26, 35);
    }

    public static int fadedBlueColor() {
        return Colour.rgb(23, 137, 155);
    }

    public static int icebergColor() {
        return Colour.rgb(200, 213, 219);
    }

    public static int waveColor() {
        return Colour.rgb(102, 169, 251);
    }

    // Greens
    public static int emeraldColor() {
        return Colour.rgb(1, 152, 117);
    }

    public static int grassColor() {
        return Colour.rgb(99, 214, 74);
    }

    public static int pastelGreenColor() {
        return Colour.rgb(126, 242, 124);
    }

    public static int seafoamColor() {
        return Colour.rgb(77, 226, 140);
    }

    public static int paleGreenColor() {
        return Colour.rgb(176, 226, 172);
    }

    public static int cactusGreenColor() {
        return Colour.rgb(99, 111, 87);
    }

    public static int chartreuseColor() {
        return Colour.rgb(69, 139, 0);
    }

    public static int hollyGreenColor() {
        return Colour.rgb(32, 87, 14);
    }

    public static int oliveColor() {
        return Colour.rgb(91, 114, 34);
    }

    public static int oliveDrabColor() {
        return Colour.rgb(107, 142, 35);
    }

    public static int moneyGreenColor() {
        return Colour.rgb(134, 198, 124);
    }

    public static int honeydewColor() {
        return Colour.rgb(216, 255, 231);
    }

    public static int limeColor() {
        return Colour.rgb(56, 237, 56);
    }

    public static int cardTableColor() {
        return Colour.rgb(87, 121, 107);
    }

    // Reds
    public static int salmonColor() {
        return Colour.rgb(233, 87, 95);
    }

    public static int brickRedColor() {
        return Colour.rgb(151, 27, 16);
    }

    public static int easterPinkColor() {
        return Colour.rgb(241, 167, 162);
    }

    public static int grapefruitColor() {
        return Colour.rgb(228, 31, 54);
    }

    public static int pinkColor() {
        return Colour.rgb(255, 95, 154);
    }

    public static int indianRedColor() {
        return Colour.rgb(205, 92, 92);
    }

    public static int strawberryColor() {
        return Colour.rgb(190, 38, 37);
    }

    public static int coralColor() {
        return Colour.rgb(240, 128, 128);
    }

    public static int maroonColor() {
        return Colour.rgb(80, 4, 28);
    }

    public static int watermelonColor() {
        return Colour.rgb(242, 71, 63);
    }

    public static int tomatoColor() {
        return Colour.rgb(255, 99, 71);
    }

    public static int pinkLipstickColor() {
        return Colour.rgb(255, 105, 180);
    }

    public static int paleRoseColor() {
        return Colour.rgb(255, 228, 225);
    }

    public static int crimsonColor() {
        return Colour.rgb(187, 18, 36);
    }

    // Purples
    public static int eggplantColor() {
        return Colour.rgb(105, 5, 98);
    }

    public static int pastelPurpleColor() {
        return Colour.rgb(207, 100, 235);
    }

    public static int palePurpleColor() {
        return Colour.rgb(229, 180, 235);
    }

    public static int coolPurpleColor() {
        return Colour.rgb(140, 93, 228);
    }

    public static int violetColor() {
        return Colour.rgb(191, 95, 255);
    }

    public static int plumColor() {
        return Colour.rgb(139, 102, 139);
    }

    public static int lavenderColor() {
        return Colour.rgb(204, 153, 204);
    }

    public static int raspberryColor() {
        return Colour.rgb(135, 38, 87);
    }

    public static int fuschiaColor() {
        return Colour.rgb(255, 20, 147);
    }

    public static int grapeColor() {
        return Colour.rgb(54, 11, 88);
    }

    public static int periwinkleColor() {
        return Colour.rgb(135, 159, 237);
    }

    public static int orchidColor() {
        return Colour.rgb(218, 112, 214);
    }

    // Yellows
    public static int goldenrodColor() {
        return Colour.rgb(215, 170, 51);
    }

    public static int yellowGreenColor() {
        return Colour.rgb(192, 242, 39);
    }

    public static int bananaColor() {
        return Colour.rgb(229, 227, 58);
    }

    public static int mustardColor() {
        return Colour.rgb(205, 171, 45);
    }

    public static int buttermilkColor() {
        return Colour.rgb(254, 241, 181);
    }

    public static int goldColor() {
        return Colour.rgb(139, 117, 18);
    }

    public static int creamColor() {
        return Colour.rgb(240, 226, 187);
    }

    public static int lightCreamColor() {
        return Colour.rgb(240, 238, 215);
    }

    public static int wheatColor() {
        return Colour.rgb(240, 238, 215);
    }

    public static int beigeColor() {
        return Colour.rgb(245, 245, 220);
    }

    // Oranges
    public static int peachColor() {
        return Colour.rgb(242, 187, 97);
    }

    public static int burntOrangeColor() {
        return Colour.rgb(184, 102, 37);
    }

    public static int pastelOrangeColor() {
        return Colour.rgb(248, 197, 143);
    }

    public static int cantaloupeColor() {
        return Colour.rgb(250, 154, 79);
    }

    public static int carrotColor() {
        return Colour.rgb(237, 145, 33);
    }

    public static int mandarinColor() {
        return Colour.rgb(247, 145, 55);
    }

    // Browns
    public static int chiliPowderColor() {
        return Colour.rgb(199, 63, 23);
    }

    public static int burntSiennaColor() {
        return Colour.rgb(138, 54, 15);
    }

    public static int chocolateColor() {
        return Colour.rgb(94, 38, 5);
    }

    public static int coffeeColor() {
        return Colour.rgb(141, 60, 15);
    }

    public static int cinnamonColor() {
        return Colour.rgb(123, 63, 9);
    }

    public static int almondColor() {
        return Colour.rgb(196, 142, 72);
    }

    public static int eggshellColor() {
        return Colour.rgb(252, 230, 201);
    }

    public static int sandColor() {
        return Colour.rgb(222, 182, 151);
    }

    public static int mudColor() {
        return Colour.rgb(70, 45, 29);
    }

    public static int siennaColor() {
        return Colour.rgb(160, 82, 45);
    }

    public static int dustColor() {
        return Colour.rgb(236, 214, 197);
    }


    // All Material colors in Android
    public static int mdRed50(){
        return Color.parseColor("#fde0dc");
    }

    public static int mdRed100(){
        return Color.parseColor("#f9bdb");
    }

    public static int mdRed200() {
        return Color.parseColor("#f69988");
    }

    public static int mdRed300(){
        return Color.parseColor("#f36c60");
    }

    public static int mdRed400(){
        return Color.parseColor("#e84e40");
    }

    public static int mdRed500(){
        return Color.parseColor("#e51c23");
    }

    public static int mdRed600(){
        return Color.parseColor("#dd191d");
    }

    public static int mdRed700(){
        return Color.parseColor("#d01716");
    }

    public static int mdRed800(){
        return Color.parseColor("#c41411");
    }

    public static int mdRed900(){
        return Color.parseColor("#b0120a");
    }

    public static int mdRedA100(){
        return Color.parseColor("#ff7997");
    }

    public static int mdRedA200(){
        return Color.parseColor("#ff5177");
    }

    public static int mdRedA400(){
        return Color.parseColor("#ff2d6f");
    }

    public static int mdRedA700(){
        return Color.parseColor("#e00032");
    }

    public static int mdPink50(){
        return Color.parseColor("#fce4ec");
    }

    public static int mdPink100(){
        return Color.parseColor("#f8bbd0");
    }

    public static int mdPink200(){
        return Color.parseColor("#f48fb1");
    }

    public static int mdPink300(){
        return Color.parseColor("#f06292");
    }

    public static int mdPink400(){
        return Color.parseColor("#ec407a");
    }

    public static int mdPink500(){
        return Color.parseColor("#e91e63");
    }

    public static int mdPink600(){
        return Color.parseColor("#d81b60");
    }

    public static int mdPink700(){
        return Color.parseColor("#c2185b");
    }

    public static int mdPink800(){
        return Color.parseColor("#ad1457");
    }

    public static int mdPink900(){
        return Color.parseColor("#880e4f");
    }

    public static int mdPinkA100(){
        return Color.parseColor("#ff80ab");
    }

    public static int mdPinkA200(){
        return Color.parseColor("#ff4081");
    }

    public static int mdPinkA400(){
        return Color.parseColor("#f50057");
    }

    public static int mdPinkA700(){
        return Color.parseColor("#c51162");
    }

    public static int mdPurple50(){
        return Color.parseColor("#f3e5f5");
    }

    public static int mdPurple100(){
        return Color.parseColor("#e1bee7");
    }

    public static int mdPurple200(){
        return Color.parseColor("#ce93d8");
    }

    public static int mdPurple300(){
        return Color.parseColor("#ba68c8");
    }

    public static int mdPurple400(){
        return Color.parseColor("#ab47bc");
    }

    public static int mdPurple500(){
        return Color.parseColor("#9c27b0");
    }

    public static int mdPurple600(){
        return Color.parseColor("#8e24aa");
    }

    public static int mdPurple700(){
        return Color.parseColor("#7b1fa2");
    }

    public static int mdPurple800(){
        return Color.parseColor("#6a1b9a");
    }

    public static int mdPurple900(){
        return Color.parseColor("#4a148c");
    }

    public static int mdPurpleA100(){
        return Color.parseColor("#ea80fc");
    }

    public static int mdPurpleA200(){
        return Color.parseColor("#e040fb");
    }

    public static int mdPurpleA400(){
        return Color.parseColor("#d500f9");
    }

    public static int mdPurpleA700(){
        return Color.parseColor("#aa00ff");
    }

    public static int mdDeepPurple50(){
        return Color.parseColor("#ede7f6");
    }

    public static int mdDeepPurple100(){
        return Color.parseColor("#d1c4e9");
    }

    public static int mdDeepPurple200(){
        return Color.parseColor("#b39ddb");
    }

    public static int mdDeepPurple300(){
        return Color.parseColor("#9575cd");
    }

    public static int mdDeepPurple400(){
        return Color.parseColor("#7e57c2");
    }

    public static int mdDeepPurple500(){
        return Color.parseColor("#673ab7");
    }

    public static int mdDeepPurple600(){
        return Color.parseColor("#5e35b1");
    }

    public static int mdDeepPurple700(){
        return Color.parseColor("#512da8");
    }

    public static int mdDeepPurple800(){
        return Color.parseColor("#4527a0");
    }

    public static int mdDeepPurple900(){
        return Color.parseColor("#311b92");
    }

    public static int mdDeepPurpleA100(){
        return Color.parseColor("#b388ff");
    }

    public static int mdDeepPurpleA200(){
        return Color.parseColor("#7c4dff");
    }

    public static int mdDeepPurpleA400(){
        return Color.parseColor("#651fff");
    }

    public static int mdDeepPurpleA700(){
        return Color.parseColor("#6200ea");
    }

    public static int mdIndigo50(){
        return Color.parseColor("#e8eaf6");
    }

    public static int mdIndigo100(){
        return Color.parseColor("#c5cae9");
    }

    public static int mdIndigo200(){
        return Color.parseColor("#9fa8da");
    }

    public static int mdIndigo300(){
        return Color.parseColor("#7986cb");
    }

    public static int mdIndigo400(){
        return Color.parseColor("#5c6bc0");
    }

    public static int mdIndigo500(){
        return Color.parseColor("#3f51b5");
    }

    public static int mdIndigo600(){
        return Color.parseColor("#3949ab");
    }

    public static int mdIndigo700(){
        return Color.parseColor("#303f9f");
    }

    public static int mdIndigo800(){
        return Color.parseColor("#283593");
    }

    public static int mdIndigo900(){
        return Color.parseColor("#1a237e");
    }

    public static int mdIndigoA100(){
        return Color.parseColor("#8c9eff");
    }

    public static int mdIndigoA200(){
        return Color.parseColor("#536dfe");
    }

    public static int mdIndigoA400(){
        return Color.parseColor("#3d5afe");
    }

    public static int mdIndigoA700(){
        return Color.parseColor("#304ffe");
    }


    public static int mdBlue50(){
        return Color.parseColor("#e7e9fd");
    }

    public static int mdBlue100(){
        return Color.parseColor("#d0d9ff");
    }

    public static int mdBlue200(){
        return Color.parseColor("#afbfff");
    }

    public static int mdBlue300(){
        return Color.parseColor("#91a7ff");
    }

    public static int mdBlue400(){
        return Color.parseColor("#738ffe");
    }

    public static int mdBlue500(){
        return Color.parseColor("#5677fc");
    }

    public static int mdBlue600(){
        return Color.parseColor("#4e6cef");
    }

    public static int mdBlue700(){
        return Color.parseColor("#455ede");
    }

    public static int mdBlue800(){
        return Color.parseColor("#3b50ce");
    }

    public static int mdBlue900(){
        return Color.parseColor("#2a36b1");
    }

    public static int mdBlueA100(){
        return Color.parseColor("#a6baff");
    }

    public static int mdBlueA200(){
        return Color.parseColor("#6889ff");
    }

    public static int mdBlueA400(){
        return Color.parseColor("#4d73ff");
    }

    public static int mdBlueA700(){
        return Color.parseColor("#4d69ff");
    }

    public static int mdLightBlue50(){
        return Color.parseColor("#e1f5fe");
    }

    public static int mdLightBlue100(){
        return Color.parseColor("#b3e5fc");
    }

    public static int mdLightBlue200(){
        return Color.parseColor("#81d4fa");
    }

    public static int mdLightBlue300(){
        return Color.parseColor("#4fc3f7");
    }

    public static int mdLightBlue400(){
        return Color.parseColor("#29b6f6");
    }

    public static int mdLightBlue500(){
        return Color.parseColor("#03a9f4");
    }

    public static int mdLightBlue600(){
        return Color.parseColor("#039be5");
    }

    public static int mdLightBlue700(){
        return Color.parseColor("#0288d1");
    }

    public static int mdLightBlue800(){
        return Color.parseColor("#0277bd");
    }

    public static int mdLightBlue900(){
        return Color.parseColor("#01579b");
    }

    public static int mdLightBlueA100(){
        return Color.parseColor("#80d8ff");
    }

    public static int mdLightBlueA200(){
        return Color.parseColor("#40c4ff");
    }

    public static int mdLightBlueA400(){
        return Color.parseColor("#00b0ff");
    }

    public static int mdLightBlueA700(){
        return Color.parseColor("#0091ea");
    }


    public static int mdCyan50(){
        return Color.parseColor("#e0f7fa");
    }

    public static int mdCyan100(){
        return Color.parseColor("#b2ebf2");
    }

    public static int mdCyan200(){
        return Color.parseColor("#80deea");
    }

    public static int mdCyan300(){
        return Color.parseColor("#4dd0e1");
    }

    public static int mdCyan400(){
        return Color.parseColor("#26c6da");
    }

    public static int mdCyan500(){
        return Color.parseColor("#00bcd4");
    }

    public static int mdCyan600(){
        return Color.parseColor("#00acc1");
    }

    public static int mdCyan700(){
        return Color.parseColor("#0097a7");
    }

    public static int mdCyan800(){
        return Color.parseColor("#00838f");
    }

    public static int mdCyan900(){
        return Color.parseColor("#006064");
    }

    public static int mdCyanA100(){
        return Color.parseColor("#84ffff");
    }

    public static int mdCyanA200(){
        return Color.parseColor("#18ffff");
    }

    public static int mdCyanA400(){
        return Color.parseColor("#00e5ff");
    }

    public static int mdCyanA700(){
        return Color.parseColor("#00b8d4");
    }

    public static int mdTeal50(){
        return Color.parseColor("#e0f2f1");
    }

    public static int mdTeal100(){
        return Color.parseColor("#b2dfdb");
    }

    public static int mdTeal200(){
        return Color.parseColor("#80cbc4");
    }

    public static int mdTeal300(){
        return Color.parseColor("#4db6ac");
    }

    public static int mdTeal400(){
        return Color.parseColor("#26a69a");
    }

    public static int mdTeal500(){
        return Color.parseColor("#009688");
    }

    public static int mdTeal600(){
        return Color.parseColor("#00897b");
    }

    public static int mdTeal700(){
        return Color.parseColor("#00796b");
    }

    public static int mdTeal800(){
        return Color.parseColor("#00695c");
    }

    public static int mdTeal900(){
        return Color.parseColor("#004d40");
    }

    public static int mdTealA100(){
        return Color.parseColor("#a7ffeb");
    }

    public static int mdTealA200(){
        return Color.parseColor("#64ffda");
    }

    public static int mdTealA400(){
        return Color.parseColor("#1de9b6");
    }

    public static int mdTealA700(){
        return Color.parseColor("#00bfa5");
    }


    public static int mdGreen50(){
        return Color.parseColor("#d0f8ce");
    }

    public static int mdGreen100(){
        return Color.parseColor("#a3e9a4");
    }

    public static int mdGreen200(){
        return Color.parseColor("#72d572");
    }

    public static int mdGreen300(){
        return Color.parseColor("#42bd41");
    }

    public static int mdGreen400(){
        return Color.parseColor("#2baf2b");
    }

    public static int mdGreen500(){
        return Color.parseColor("#259b24");
    }

    public static int mdGreen600(){
        return Color.parseColor("#0a8f08");
    }

    public static int mdGreen700(){
        return Color.parseColor("#0a7e07");
    }

    public static int mdGreen800(){
        return Color.parseColor("#056f00");
    }

    public static int mdGreen900(){
        return Color.parseColor("#0d5302");
    }

    public static int mdGreenA100(){
        return Color.parseColor("#a2f78d");
    }

    public static int mdGreenA200(){
        return Color.parseColor("#5af158");
    }

    public static int mdGreenA400(){
        return Color.parseColor("#14e715");
    }

    public static int mdGreenA700(){
        return Color.parseColor("#12c700");
    }


    public static int mdLightGreen50(){
        return Color.parseColor("#f1f8e9");
    }

    public static int mdLightGreen100(){
        return Color.parseColor("#dcedc8");
    }

    public static int mdLightGreen200(){
        return Color.parseColor("#c5e1a5");
    }

    public static int mdLightGreen300(){
        return Color.parseColor("#aed581");
    }

    public static int mdLightGreen400(){
        return Color.parseColor("#9ccc65");
    }

    public static int mdLightGreen500(){
        return Color.parseColor("#8bc34a");
    }

    public static int mdLightGreen600(){
        return Color.parseColor("#7cb342");
    }

    public static int mdLightGreen700(){
        return Color.parseColor("#689f38");
    }

    public static int mdLightGreen800(){
        return Color.parseColor("#558b2f");
    }

    public static int mdLightGreen900(){
        return Color.parseColor("#33691e");
    }

    public static int mdLightGreenA100(){
        return Color.parseColor("#ccff90");
    }

    public static int mdLightGreenA200(){
        return Color.parseColor("#b2ff59");
    }

    public static int mdLightGreenA400(){
        return Color.parseColor("#76ff03");
    }

    public static int mdLightGreenA700(){
        return Color.parseColor("#64dd17");
    }

    public static int mdLime50(){
        return Color.parseColor("#f9fbe7");
    }

    public static int mdLime100(){
        return Color.parseColor("#f0f4c3");
    }

    public static int mdLime200(){
        return Color.parseColor("#e6ee9c");
    }

    public static int mdLime300(){
        return Color.parseColor("#dce775");
    }

    public static int mdLime400(){
        return Color.parseColor("#d4e157");
    }

    public static int mdLime500(){
        return Color.parseColor("#cddc39");
    }

    public static int mdLime600(){
        return Color.parseColor("#c0ca33");
    }

    public static int mdLime700(){
        return Color.parseColor("#afb42b");
    }

    public static int mdLime800(){
        return Color.parseColor("#9e9d24");
    }

    public static int mdLime900(){
        return Color.parseColor("#827717");
    }

    public static int mdLimeA100(){
        return Color.parseColor("#f4ff81");
    }

    public static int mdLimeA200(){
        return Color.parseColor("#eeff41");
    }

    public static int mdLimeA400(){
        return Color.parseColor("#c6ff00");
    }

    public static int mdLimeA700(){
        return Color.parseColor("#aeea00");
    }

    public static int mdYellow50(){
        return Color.parseColor("#fffde7");
    }

    public static int mdYellow100(){
        return Color.parseColor("#fff9c4");
    }

    public static int mdYellow200(){
        return Color.parseColor("#fff59d");
    }

    public static int mdYellow300(){
        return Color.parseColor("#fff176");
    }

    public static int mdYellow400(){
        return Color.parseColor("#ffee58");
    }

    public static int mdYellow500(){
        return Color.parseColor("#ffeb3b");
    }

    public static int mdYellow600(){
        return Color.parseColor("#fdd835");
    }

    public static int mdYellow700(){
        return Color.parseColor("#fbc02d");
    }

    public static int mdYellow800(){
        return Color.parseColor("#f9a825");
    }

    public static int mdYellow900(){
        return Color.parseColor("#f57f17");
    }

    public static int mdYellowA100(){
        return Color.parseColor("#ffff8d");
    }

    public static int mdYellowA200(){
        return Color.parseColor("#ffff00");
    }

    public static int mdYellowA400(){
        return Color.parseColor("#ffea00");
    }

    public static int mdYellowA700(){
        return Color.parseColor("#ffd600");
    }

    public static int mdAmber50(){
        return Color.parseColor("#fff8e1");
    }

    public static int mdAmber100(){
        return Color.parseColor("#ffecb3");
    }

    public static int mdAmber200(){
        return Color.parseColor("#ffe082");
    }

    public static int mdAmber300(){
        return Color.parseColor("#ffd54f");
    }

    public static int mdAmber400(){
        return Color.parseColor("#ffca28");
    }

    public static int mdAmber500(){
        return Color.parseColor("#ffc107");
    }

    public static int mdAmber600(){
        return Color.parseColor("#ffb300");
    }

    public static int mdAmber700(){
        return Color.parseColor("#ffa000");
    }

    public static int mdAmber800(){
        return Color.parseColor("#ff8f00");
    }

    public static int mdAmber900(){
        return Color.parseColor("#ff6f00");
    }

    public static int mdAmberA100(){
        return Color.parseColor("#ffe57f");
    }

    public static int mdAmberA200(){
        return Color.parseColor("#ffd740");
    }

    public static int mdAmberA400(){
        return Color.parseColor("#ffc400");
    }

    public static int mdAmberA700(){
        return Color.parseColor("#ffab00");
    }

    public static int mdOrange50(){
        return Color.parseColor("#fff3e0");
    }

    public static int mdOrange100(){
        return Color.parseColor("#ffe0b2");
    }

    public static int mdOrange200(){
        return Color.parseColor("#ffcc80");
    }

    public static int mdOrange300(){
        return Color.parseColor("#ffb74d");
    }

    public static int mdOrange400(){
        return Color.parseColor("#ffa726");
    }

    public static int mdOrange500(){
        return Color.parseColor("#ff9800");
    }

    public static int mdOrange600(){
        return Color.parseColor("#fb8c00");
    }

    public static int mdOrange700(){
        return Color.parseColor("#f57c00");
    }

    public static int mdOrange800(){
        return Color.parseColor("#ef6c00");
    }

    public static int mdOrange900(){
        return Color.parseColor("#e65100");
    }

    public static int mdOrangeA100(){
        return Color.parseColor("#ffd180");
    }

    public static int mdOrangeA200(){
        return Color.parseColor("#ffab40");
    }

    public static int mdOrangeA400(){
        return Color.parseColor("#ff9100");
    }

    public static int mdOrangeA700(){
        return Color.parseColor("#ff6d00");
    }

    public static int mdDeepOrange50(){
        return Color.parseColor("#fbe9e7");
    }

    public static int mdDeepOrange100(){
        return Color.parseColor("#ffccbc");
    }

    public static int mdDeepOrange200(){
        return Color.parseColor("#ffab91");
    }

    public static int mdDeepOrange300(){
        return Color.parseColor("#ff8a65");
    }

    public static int mdDeepOrange400(){
        return Color.parseColor("#ff7043");
    }

    public static int mdDeepOrange500(){
        return Color.parseColor("#ff5722");
    }

    public static int mdDeepOrange600(){
        return Color.parseColor("#f4511e");
    }

    public static int mdDeepOrange700(){
        return Color.parseColor("#e64a19");
    }

    public static int mdDeepOrange800(){
        return Color.parseColor("#d84315");
    }

    public static int mdDeepOrange900(){
        return Color.parseColor("#bf360c");
    }

    public static int mdDeepOrangeA100(){
        return Color.parseColor("#ff9e80");
    }

    public static int mdDeepOrangeA200(){
        return Color.parseColor("#ff6e40");
    }

    public static int mdDeepOrangeA400(){
        return Color.parseColor("#ff3d00");
    }


    public static int mdDeepOrangeA700(){
        return Color.parseColor("#dd2c00");
    }

    public static int mdBrown50(){
        return Color.parseColor("#efebe9");
    }

    public static int mdBrown100(){
        return Color.parseColor("#d7ccc8");
    }

    public static int mdBrown200(){
        return Color.parseColor("#bcaaa4");
    }

    public static int mdBrown300(){
        return Color.parseColor("#a1887f");
    }

    public static int mdBrown400(){
        return Color.parseColor("#8d6e63");
    }

    public static int mdBrown500(){
        return Color.parseColor("#795548");
    }

    public static int mdBrown600(){
        return Color.parseColor("#6d4c41");
    }

    public static int mdBrown700(){
        return Color.parseColor("#5d4037");
    }

    public static int mdBrown800(){
        return Color.parseColor("#4e342e");
    }

    public static int mdBrown900(){
        return Color.parseColor("#3e2723");
    }

    public static int mdGrey50(){
        return Color.parseColor("#fafafa");
    }

    public static int mdGrey100(){
        return Color.parseColor("#f5f5f5");
    }

    public static int mdGrey200(){
        return Color.parseColor("#eeeeee");
    }

    public static int mdGrey300(){
        return Color.parseColor("#e0e0e0");
    }

    public static int mdGrey400(){
        return Color.parseColor("#bdbdbd");
    }

    public static int mdGrey500(){
        return Color.parseColor("#9e9e9e");
    }

    public static int mdGrey600(){
        return Color.parseColor("#757575");
    }

    public static int mdGrey700(){
        return Color.parseColor("#616161");
    }

    public static int mdGrey800(){
        return Color.parseColor("#424242");
    }

    public static int mdGrey900(){
        return Color.parseColor("#212121");
    }

    public static int mdBlack(){
        return Color.parseColor("#000000");
    }

    public static int mdWhite(){
        return Color.parseColor("#ffffff");
    }

    public static int mdBlueGrey50(){
        return Color.parseColor("#eceff1");
    }

    public static int mdBlueGrey100(){
        return Color.parseColor("#cfd8dc");
    }

    public static int mdBlueGrey200(){
        return Color.parseColor("#b0bec5");
    }

    public static int mdBlueGrey300(){
        return Color.parseColor("#90a4ae");
    }

    public static int mdBlueGrey400(){
        return Color.parseColor("#78909c");
    }

    public static int mdBluegrey500(){
        return Color.parseColor("#607d8b");
    }

    public static int mdBlueGrey600(){
        return Color.parseColor("#546e7a");
    }

    public static int mdBlueGrey700(){
        return Color.parseColor("#455a64");
    }

    public static int mdBlueGrey800(){
        return Color.parseColor("#37474f");
    }

    public static int mdBlueGrey900(){
        return Color.parseColor("#263238");
    }
    // All Holo Colors in Android

    public static int holoBlueLightColor() {
        return Colour.parseColor("#ff33b5e5");
    }

    public static int holoGreenLightColor() {
        return Colour.parseColor("#ff99cc00");
    }

    public static int holoRedLightColor() {
        return Colour.parseColor("#ffff4444");
    }

    public static int holoBlueDarkColor() {
        return Colour.parseColor("#ff0099cc");
    }

    public static int holoGreenDarkColor() {
        return Colour.parseColor("#ff669900");
    }

    public static int holoRedDarkColor() {
        return Colour.parseColor("#ffcc0000");
    }

    public static int holoPurpleColor() {
        return Colour.parseColor("#ffaa66cc");
    }

    public static int holoOrangeLightColor() {
        return Colour.parseColor("#ffffbb33");
    }

    public static int holoOrangeDarkColor() {
        return Colour.parseColor("#ffff8800");
    }

    public static int holoBlueBrightColor() {
        return Colour.parseColor("#ff00ddff");
    }

    // Holo Background colors

    public static int backgroundDarkColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int backgroundLightColor() {
        return Colour.parseColor("#ffffffff");
    }

    public static int brightForegroundDarkColor() {
        return Colour.parseColor("#ffffffff");
    }

    public static int brightForegroundLightColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int brightForegroundDarkDisabledColor() {
        return Colour.parseColor("#80ffffff");
    }

    public static int backgroundHoloDarkColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int backgroundHoloLightColor() {
        return Colour.parseColor("#fff3f3f3");
    }

    public static int brightForegroundHoloDarkColor() {
        return Colour.parseColor("#fff3f3f3");
    }

    public static int brightForegroundHoloLightColor() {
        return Colour.parseColor("#ff000000");
    }

    public static int brightForegroundDisabledHoloDarkColor() {
        return Colour.parseColor("#ff4c4c4c");
    }

    public static int brightForegroundDisabledHoloLightColor() {
        return Colour.parseColor("#ffb2b2b2");
    }

    public static int dimForegroundHoloDarkColor() {
        return Colour.parseColor("#bebebe");
    }

    public static int dimForegroundDisabledHoloDarkColor() {
        return Colour.parseColor("#80bebebe");
    }

    public static int hintForegroundHoloDarkColor() {
        return Colour.parseColor("#808080");
    }

    public static int dimForegroundHoloLightColor() {
        return Colour.parseColor("#323232");
    }

    public static int dimForegroundDisabledHoloLightColor() {
        return Colour.parseColor("#80323232");
    }

    public static int hintForegroundHoloLightColor() {
        return Colour.parseColor("#808080");
    }

    public static int highlightedTextHoloDarkColor() {
        return Colour.parseColor("#6633b5e5");
    }

    public static int highlightedTextHoloLightColor() {
        return Colour.parseColor("#ff00ddff");
    }

}
