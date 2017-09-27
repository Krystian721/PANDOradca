package com.waka.pandoradca.TXT;

public abstract class Resources {
    public static String Instruction() {
        return "     Mała Pando! Masz przed sobą pierwsze zadanie.\n" +
                "\n" +
                "  Trafiasz do domu w którym czekają na ciebie różne \n" +
                "                        czynności do wykonania. \n" +
                " Spośród nich możesz wybrać tylko 5 najważniejszych! \n" +
                "\n" +
                "   Naskocz tylko na te czynności, które wydadzą Ci się \n" +
                "                                bardzo ważne! \n" +
                "  Uważaj, by nie dotknąć tych na które nie masz dzisiaj \n" +
                "                                       czasu.. \n" +
                "\n" +
                "                                  Powodzenia!\n";
    }

    public static String ForestInstruction(){
        return  "     Mała Pando! \n\n" +
                "     Wkroczyłeś w tajemniczy świat PAN-DORADCY.\n" +
                "     Doświadczysz tutaj nowych przygód związanych z \n" +
                "     zawodami i swoimi mocnymi cechami.\n" +
                "     Czekają tu na Ciebie rózne zadania do wykonania, \n" +
                "     musisz się do nich dobrze przygotować.\n" +
                "     Poćwicz więc skakanie, bieganie i omijanie \n" +
                "     przeszkód.\n" +
                "     Jak odnajdziesz tajemniczy domek DUŻEJ PANDY \n" +
                "     przejdziesz do następnego etapu.\n\n" +
                "     Powodzenia MAŁA PANDO!!";
    }

    public static String CityInstruction(){
        return  "\n\nDroga Pando, \n\n" +
                "masz przed sobą kolejne zadanie!\n\n" +
                "Spróbuj rozpoznać zawod kierujący się za 3 słowami. \n\n" +
                "W razie czego skorzystaj z podpowiedzi. \n\n" +
                "Do dzieła!";
    }
    public static String Job(int i) {
        switch (i) {
            case 1:
                return job1();
            case 2:
                return job2();
            case 3:
                return job3();
            case 4:
                return job4();
            case 5:
                return job5();
            case 6:
                return job6();
            case 7:
                return job7();
            case 8:
                return job8();
            case 9:
                return job9();
            case 10:
                return job10();
            case 11:
                return job11();
            case 12:
                return job12();
            default:
                return "";
        }
    }

    public static String job1() {
        return "\n" +
                "  TABLICA \n\n\n\n" +
                "  KSIĄŻKA \n\n\n\n" +
                "SPRAWDZIAN";
    }

    public static String job2() {
        return "\n" +
                "  PACJENT \n\n\n\n" +
                "  BADANIE \n\n\n\n" +
                "  CHOROBA";
    }

    public static String job3() {
        return "\n" +
                "    DANIE \n\n\n\n" +
                "  PATELNIA \n\n\n\n" +
                "     NÓŻ";
    }

    public static String job4() {
        return "\n" +
                "  KOMPUTER \n\n\n\n" +
                "  PROCESOR \n\n\n\n" +
                "   MYSZKA";
    }

    public static String job5() {
        return "\n" +
                "  BUDYNKI \n\n\n\n" +
                "  PROJEKT \n\n\n\n" +
                "  RYSUNEK";
    }

    public static String job6(){
        return "\n" +
                "    HAMULEC \n\n\n\n" +
                "SKRZYNIA BIEGÓW \n\n\n\n" +
                "    JEZDNIA";
    }

    public static String job7(){
        return "\n" +
                "    MUNDUR \n\n\n\n" +
                "  KOMISARIAT \n\n\n\n" +
                "   KAJDANKI";
    }

    public static String job8(){
        return "\n" +
                "    LEKI \n\n\n\n" +
                "  RECEPTY \n\n\n\n" +
                " PACJENCI";
    }

    public static String job9(){
        return "\n" +
                "  SAMOCHÓD \n\n\n\n" +
                "    KLUCZ \n\n\n\n" +
                "   USTERKA";
    }

    public static String job10(){
        return "\n" +
                "  TORT \n\n\n\n" +
                "  MĄKA \n\n\n\n" +
                "PIEKARNIK";
    }

    public static String job11(){
        return "\n" +
                "    PIES \n\n\n\n" +
                "  ZASTRZYK \n\n\n\n" +
                " WŚCIEKLIZNA";
    }

    public static String job12(){
        return "\n" +
                "     PODRÓZE \n\n\n\n" +
                "  PAS STARTOWY \n\n\n\n" +
                "      STER";
    }

    public static String[] cityQuestions()
    {
        String[] allQuestions = new String[]{"Nauczyciel", "Lekarz", "Kucharz", "Informatyk", "Architekt", "Kierowca", "Policjant", "Farmaceuta", "Mechanik", "Cukiernik", "Weterynarz", "Pilot"};
        return allQuestions;
    }
}
