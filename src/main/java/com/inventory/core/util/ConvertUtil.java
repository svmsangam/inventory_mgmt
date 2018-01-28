package com.inventory.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;

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

    public static String roundUpDoubleToString(double value , int fraction){
        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMaximumFractionDigits(fraction);
        fmt.setRoundingMode(RoundingMode.FLOOR);

        return fmt.format(value);
    }

    public static double roundUpDoubleToDouble(double value , int fraction) throws ParseException {

        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMaximumFractionDigits(fraction);
        fmt.setRoundingMode(RoundingMode.CEILING);

        String str = fmt.format(value);

        return (double) fmt.parse(str);
    }


}