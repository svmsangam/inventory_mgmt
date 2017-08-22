package com.inventory.web.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by dhiraj on 6/22/17.
 */
public class PageInfo {

    public static double pageList = 2.0;
    public static int numberOfPage = 2;

    private static int decrement;
    private static int increment;


    public static List<Integer> PageLimitCalculator(int page, int lastpage, int pageLimit) {

        boolean crossfire = false;
        List<Integer> in = new ArrayList<Integer>();
        decrement = (page - 1);
        increment = (page + 1);
        in.add(page);
        for (int i = 1; i < pageLimit; i++) {
            if (page != decrement || page != increment) {
                if (crossfire) {
                    if (decrement > 0) {
                        in.add(decrement);
                        decrement--;
                    } else if (increment <= lastpage) {
                        in.add(increment);
                        increment++;
                    }
                    crossfire = false;
                } else {
                    if (increment <= lastpage) {
                        in.add(increment);
                        increment++;
                    } else if (decrement > 0) {
                        in.add(decrement);
                        decrement--;
                    }
                    crossfire = true;
                }
            }

        }
        Collections.sort(in);
        return in;
    }

}
