package jakubkorsak.puszkin;


class Sources {


    static String SENDER_ACTIVITY = "senderActivity";

    static String TYPE_OF_WEB_VIEW[] = {"plan", "harmonogram", "zastepstwa", "dzienniczek"};

    static String TAG = "tag";

    static String Nauczyciele[] = {
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
    static String Gabinety[] = {
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
    static String klasy[] = {
            "1a", "1b", "1c", "1d", "1e", "1f",
            "2a", "2b", "2c", "2d", "2e", "2f",
            "3a", "3b", "3c", "3d", "3e", "3f"
    };

    static String index[] = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "10", "11", "12", "13", "14", "15", "16", "17", "18"
    };

    static String zrodla[] = {
            "ostatnia", "twoja"
    };


    /**
     * n - nauczyciel
     * s - sala
     * o - oddział
     *
     * @param prefix to jest ta część odpowiedzialna za (n, s, o)
     * @param source to jest część odpowiedzialna za indeks obiektu
     * @return String w postaci h = "(n, s, o) + indeks tego obiektu"
     **/
     /*
     * Zasada działania:
     * dostajemy input h, jakiś ciąg znaków. Sprawdzamy czy znajduje się on w którejś z tablic w Sources
     * jeśli tak, to zwracamy dany "prefix" + indeks w jakim znaleziono tą wartość + 1 bo
     * tablice liczą od 0.
     *
     * Jeśli nie ma tego ciągu znaków w tablicy (nie powinno się zdarzyć) dostaniemy jakiś
     * NullPointerException albo coś takiego. Zaznaczam, że nie powinno się to nigdy zdarzyć ze
     * względu na to, że wartość inputu h pochodzi z tablicy z Resources.
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
     * @param h      "[o,n,s] + index[1;18]"
     * @param prefix w tej wersji jest to zawsze "o" ale w późniejszych wersjach rozszerzę tą funkcję
     * @param source tablica do której ma porównywać "h"
     * @return odpowiadająca nazwa klasy w postaci "[1;3] + [A;F]"
     */
    static String getIndex(String h, String prefix, String[] source) {
        int i = 0;
        h = h.replace(prefix, "");
        while (i <= source.length) {
            if (h.equalsIgnoreCase(source[i])) {
                h = Sources.klasy[i];
                break;
            }
            i++;
        }
        return h;
    }
}
