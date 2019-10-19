package com.example.wgu_c196.utilities;

import java.text.SimpleDateFormat;

public class TextFormatter {
    public static String crdPat = "MMM d yyyy";
    private static String fulPat = "MM/dd/yyyy";
    public static SimpleDateFormat crdDateFrmat = new SimpleDateFormat(crdPat);
    public static SimpleDateFormat fulDateFrmat = new SimpleDateFormat(fulPat);
}
