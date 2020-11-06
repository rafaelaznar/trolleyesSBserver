/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ausiasmarch.trolleyesSBserver.helper;

import java.security.SecureRandom;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 *
 * @author raznara
 */
public class RandomHelper {

    protected static SecureRandom random = new SecureRandom();

    public static synchronized String getRandomHexString(int size) {
        StringBuffer sb = new StringBuffer();
        while (sb.length() < size) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        return sb.toString().substring(0, size);
    }

    public static synchronized String getToken(int size) {
        return Long.toString(Math.abs(random.nextLong()), size);
    }

    public static int getRandomInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static Date getRadomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = getRandomInt(1925, 2019);
        gc.set(gc.YEAR, year);
        int dayOfYear = getRandomInt(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        Date date = new Date(gc.getTimeInMillis());
        return date;
    }

    public static char getRadomChar() {
        Random r = new Random();
        char c = (char) (r.nextInt(26) + 'a');
        return Character.toUpperCase(c);
    }

    public static double getRadomDouble(int rangeMin, int rangeMax) {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
}
