package com.inventory.web.testMain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dhiraj on 8/12/17.
 */
public class Main {
    /*public static void main(String ars[]) {

        System.out.println("current date  : " + new Date());
        System.out.println("1 : " + calculateExpiryDate(1));
        System.out.println("7 : " + calculateExpiryDate(7));
        System.out.println("10 : " + calculateExpiryDate(10));
        System.out.println("14 : " + calculateExpiryDate(14));
        System.out.println("30 : " + calculateExpiryDate(30));
        System.out.println("60 : " + calculateExpiryDate(60));
        System.out.println("330 : " + calculateExpiryDate(330));
        System.out.println("360 : " + calculateExpiryDate(360));
        System.out.println("365 : " + calculateExpiryDate(365));
        System.out.println("665 : " + calculateExpiryDate(665));

    }

    private static Date calculateExpiryDate(int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.DATE, days);
        return new Date(cal.getTime().getTime());
    }*/
}
