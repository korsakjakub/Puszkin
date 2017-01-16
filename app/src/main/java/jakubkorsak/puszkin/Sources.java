package jakubkorsak.puszkin;


public class Sources {


    public static String SENDER_ACTIVITY = "senderActivity";

    public static String TYPE_OF_WEB_VIEW[] = {"plan", "harmonogram", "zastepstwa", "dzienniczek"};

    public static String TAG = "tag";

    static String TWOJA_KLASA_SAVED = "twoja_klasa_saved";

    public static String Nauczyciele[] = {
            "Ewa Andrzejewska-Sidorowicz",
            "Marta Bagińska",
            "Mariusz Biniewski",
            "Tomasz Bobin",
            "Agnieszka Bujak",
            "Monika Duchnowska",
            "Radosław Duchnowski",
            "Piotr Pluta",
            "Dariusz Gerek",
            "Aleksandra Gratka",
            "Olga Grochowska",
            "Zofia Gromala",
            "Krystyna Gutowska",
            "Małgorzata Jach",
            "Justyna Kaczyńska - Bronisz",
            "Anna Kociołek",
            "Piotr Kociołek",
            "Bartłomiej Korasiak",
            "Anna Korytowska",
            "Teresa Kostyszak",
            "Zbigniew Łuczka",
            "Kamil Maciejewski",
            "Robert Malenkowski",
            "Barbara Marjowska",
            "Elżbieta Mierzejewska",
            "Sylwia Miler",
            "Elżbieta Niekrasz",
            "Małgorzata Niewiadomska",
            "Ewa Olczak",
            "Ireneusz Ostapczuk",
            "Monika Ostrowska",
            "Agata Piekarska",
            "Zbigniew Pietrzak",
            "Iryna Polikowska",
            "Marek Przeworski",
            "Iwona Różańska",
            "Gabriela Sajkowska",
            "Agata Stapf - Skiba",
            "Paweł Szafrański",
            "Ewa Szmit",
            "Jolanta Śmigiel",
            "Justyna Walus",
            "Arkadiusz Wiącek",
            "Małgorzata Wiczkowska",
            "Jolanta Winnik",
            "Paweł Wiśniewski",
            "Paulina Wysocka",
            "Paweł Zaborowski"
    };
    public static String Gabinety[] = {
            "j.polski (1)",
            "j.polski (14)",
            "j.polski (15)",
            "język obcy (17)",
            "j.niemiecki (26)",
            "matematyka (27)",
            "j. angielski (28)",
            "j. angielski (29)",
            "j. angielski (30)",
            "j.polski (31)",
            "j. angielski (32)",
            "historia/po (37)",
            "historia (38)",
            "inf./j. niem (39)",
            "informatyka/ F.inf. (40)",
            "informatyka (41)",
            "biologia (42)",
            "j. obcy (43)",
            "geografia (47)",
            "fizyka/matem. (48)",
            "fizyka/matemat. (49)",
            "chemia (51)",
            "matematyka (52)",
            "j. angielski (54)",
            "religia (SK)",
            "WF (G1)",
            "WF (G2)",
            "WF (G3)",
            "WF (HS1)",
            "l. wych (SU)",
            "j. polski (5)",
            "j. obcy (czyt)"
    };
    public static String klasy[] = {
            "1a", "1b", "1c", "1d", "1e", "1f",
            "2a", "2b", "2c", "2d", "2e", "2f",
            "3a", "3b", "3c", "3d", "3e", "3f"
    };

    public static String index[] = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27",
            "28", "29", "30", "31", "32", "33", "34", "35", "36",
            "37", "38", "39", "40", "41", "42", "43", "44", "45",
            "46", "47", "48", "49", "50", "51", "52", "53", "54"
    };

    public static String zrodla[] = {
            "ostatnia", "twoja"
    };

    /**
     * n - nauczyciel
     * containerString - sala
     * o - oddział
     *
     * @param prefix to jest ta część odpowiedzialna za (n, containerString, o)
     * @param source to jest część odpowiedzialna za indeks obiektu
     * @return String webView postaci pathParameter = "(n, containerString, o) + indeks tego obiektu"
     **/
     /*
     * Zasada działania:
     * dostajemy input pathParameter, jakiś ciąg znaków. Sprawdzamy czy znajduje się on webView którejś z tablic webView Sources
     * jeśli tak, to zwracamy dany "prefix" + indeks webView jakim znaleziono tą wartość + 1 bo
     * tablice liczą od 0.
     *
     */
    static String getID(String h, String prefix, String[] source) {
        int i = 0;
        while (i <= source.length) {
            if (h.equalsIgnoreCase(source[i])) {
                h = prefix + String.valueOf(i + 1);
                break;
            }
            i++;
        }
        return h;
    }


    /**
     * @param h      "[o,n,containerString] + index[1;18]"
     * @param prefix webView tej wersji jest to zawsze "o" ale webView późniejszych wersjach rozszerzę tą funkcję
     * @param source tablica do której ma porównywać "pathParameter"
     * @return odpowiadająca nazwa klasy webView postaci "[1;3] + [A;F]"
     */
    static String getIndex(String h, String prefix, String[] source, String[] output) {
        int i = 0;
        h = h.replace(prefix, "");
        while (i <= source.length) {
            if (h.equalsIgnoreCase(source[i])) {
                h = output[i];
                break;
            }
            i++;
        }
        return h;
    }
}
