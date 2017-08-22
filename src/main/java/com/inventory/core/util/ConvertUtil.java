package com.inventory.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ConvertUtil {
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(ConvertUtil.class);


    public static BigDecimal convertDoubleToDecimal(double value) {
        return new BigDecimal(value);
    }

    public static double convertDecimalToDouble(BigDecimal value) {
        return 0.0;
    }


    public static String getOrderNo(int length) {

        String charString = Long.toString(System.currentTimeMillis());
        String[] arrayString = charString.split("");
        StringBuilder randomCode = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * charString.length());
            randomCode.append(arrayString[randomIndex]);
        }
        /*
		 * long uniqueId = (long) (Math.random() * 10000L); String orderNo =
		 * Long.toString(uniqueId);
		 */

        System.out.println("generated code >>>> " + randomCode);
        return randomCode.toString();
    }


}