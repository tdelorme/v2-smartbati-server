package fr.ceured.batismart.server.commons;

import java.text.NumberFormat;
import java.text.ParseException;

public class DoubleUtils {
    public static double roundPrice(double value) throws ParseException {
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);

        return formatter.parse(formatter.format(value)).doubleValue();
    }
}
