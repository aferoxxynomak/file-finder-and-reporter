package hu.cordongroup.filefinderandreporter.util;

public class Constants {
    public static final String WINDOWS_CHARSET = "windows-1250";
    public static final String ERROR_MESSAGE_COLOR = "\n\u001B[31m";
    public static final String HELP_HEADER = """
            Fájl kereső és riport készítő alkalmazás
            ----------------------------------------
            """;
    public static final String HELP_TEXT = """
            Egy bemeneti .csv fájlban megadott fájlneveket keres a meghajtó(ko)n és erről egy riport .csv fájlt készít, melyet a bemeneti fájl mellé helyez el időbélyeggel ellátva.
            Használat: findandreport.bat [--c] [--p] [--r] bemeneti_.csv_fájl
                Példa: findandreport.bat C:\\kodok.csv
                       findandreport.bat --c --r C:\\kodok.csv
                       findandreport.bat "C:\\Program Files\\kodok.csv"
            Paraméterek:
                --c                - Csak a C: meghajtót fogja átvizsgálni. Alapértelmezetten az összes elérhető meghajtót fogja vizsgálni.
                --p                - Elrejti az épp aktuálisan vizsgált könyvtár kiírását.
                --r                - Rejtett könyvtárakat is átvizsgál. Alapértelmezetten kihagyja azokat.
                bemeneti_.csv_fájl - A teljes elérési út a bemeneti .csv fájlhoz, mely sorokra bontott kiterjesztés nélküli fájlneveket tartalmaz.
                                     A program automatikusan törli az esetleges szóközöket a fájlnevek elött és után.
                                     Amennyiben szóközt tartalmazna az elérési út, úgy tegye ""-ek közé.
                                     Példa a fájl tartalmára: SM001
                                                              SM002
                                                              SM003
            """;
    public static final String CSV_HEADER_COL_1 = "Fájlnév";
    public static final String CSV_HEADER_COL_2 = "Megtalálva";
    public static final String CSV_HEADER_COL_3 = "Elérési út";
    public static final char CSV_SEPARATOR = ';';
    public static final String CSV_FOUND_YES = "IGEN";
    public static final String CSV_FOUND_NO = "NEM";
}
