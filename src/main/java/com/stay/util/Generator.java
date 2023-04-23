package com.stay.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class Generator {

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static String getStringRandomNumber(int min, int max) {
        String pattern = String.valueOf(max).replaceAll(".","0");
        NumberFormat formatter = new DecimalFormat(pattern);

        int number = getRandomNumber(min, max);
        String StrigifyNumber = formatter.format(number);
            return StrigifyNumber;
    }
}
