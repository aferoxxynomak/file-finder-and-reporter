package hu.cordongroup.filefinderandreporter.util;

public class Util {
    public static void printErrorMsg(String message) {
        System.out.println(Constants.ERROR_MESSAGE_COLOR + message);
    }

    public static String[] getCsvHeader() {
        return new String[]{Constants.CSV_HEADER_COL_1, Constants.CSV_HEADER_COL_2, Constants.CSV_HEADER_COL_3};
    }
}
