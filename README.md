# Fájl kereső és riport készítő alkalmazás

Egy bemeneti .csv fájlban megadott fájlneveket keres a meghajtó(ko)n és erről egy riport .csv fájlt készít, melyet a
bemeneti fájl mellé helyez el időbélyeggel ellátva.

## Használat

`java -jar file-finder-and-reporter.jar [--c] [--p] [--r] bemeneti_.csv_fájl`

vagy

`findandreport.bat [--c] [--p] [--r] bemeneti_.csv_fájl`

Példa:

        findandreport.bat C:\kodok.csv
        findandreport.bat --c --r C:\kodok.csv
        findandreport.bat "C:\Program Files\kodok.csv"

Paraméterek:

`--c` - Csak a C: meghajtót fogja átvizsgálni. Alapértelmezetten az összes elérhető meghajtót fogja vizsgálni.

`--p` - Elrejti az épp aktuálisan vizsgált könyvtár kiírását.

`--r` - Rejtett könyvtárakat is átvizsgál. Alapértelmezetten kihagyja azokat.

`bemeneti_.csv_fájl` - A teljes elérési út a bemeneti .csv fájlhoz, mely sorokra bontott kiterjesztés nélküli
fájlneveket tartalmaz.A program automatikusan törli az esetleges szóközöket a fájlnevek elött és után. Amennyiben
szóközt tartalmazna az elérési út, úgy tegye `""`-ek közé.
Példa a fájl tartalmára:

    SM001
    SM002
    SM003
