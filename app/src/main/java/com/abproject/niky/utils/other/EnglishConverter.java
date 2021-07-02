package com.abproject.niky.utils.other;

/**
 * this class has one method that takes a String
 * and search into them, if there was number into
 * String take theme and convert to Persian Number.
 * Reference -> https://gist.github.com/Udmx/19e47eee2d28e528163ddd03746382b8
 */
public class EnglishConverter {

    public static String convertEnglishNumberToPersianNumber(String faNumbers) {
        String[][] mChars = new String[][]{
                {"0", "۰"},
                {"1", "۱"},
                {"2", "۲"},
                {"3", "۳"},
                {"4", "۴"},
                {"5", "۵"},
                {"6", "۶"},
                {"7", "۷"},
                {"8", "۸"},
                {"9", "۹"}
        };
        for (String[] num : mChars) {
            faNumbers = faNumbers.replace(num[0], num[1]);

        }
        return faNumbers;
    }

}