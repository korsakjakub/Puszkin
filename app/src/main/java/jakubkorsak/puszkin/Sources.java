package jakubkorsak.puszkin;


class Sources {

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

    static String getID(String h, String[] source) {
        int i = 0;

        while (true) {
            if (h == source[i]) {
                h = "n" + String.valueOf(i + 1);
                return h;
            }
            i++;
        }
    }
}
