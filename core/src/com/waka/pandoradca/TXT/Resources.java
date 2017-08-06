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

    public static String Job(int i) {
        switch (i) {
            case 1:
                return job1();
            case 2:
                return job2();
            default:
                return "";
        }
    }

    public static String job1() {
        return "           Osoby z którymi \n" +
                "  pracuję są ode mnie mniejsze \n" +
                "\n" +
                "  i mają dużo energii. Lubię jak \n" +
                " słuchają mnie z zainteresowaniem.\n" +
                "\n" +
                "      W pracy często sprawdzam \n" +
                "   sprawdziany, wystawiam oceny.\n" +
                "\n" +
                "      Muszę prowadzić dziennik.\n" +
                "\n" +
                "                   Kim jestem?";
    }

    public static String job2() {
        return "             Mój zawód od \n" +
                "  dzieciństwa był moją wielką \n" +
                " pasją, zawsze chciałem pomagać \n" +
                "      innym ludziom. W pracy \n" +
                " codziennie ocieram się o cierpienie \n" +
                "    i ból, nie zawsze mogę temu \n" +
                " zaradzić. Często mam nocne dyżury, \n" +
                "   jak jest jakiś wypadek to jestem \n" +
                " wzywany. Jednak czerpię ogromną \n" +
                " satysfakcje z tego kim jestem, \n" +
                "     w końcu to ode mnie zależy \n" +
                "                 ludzkie życie!\n" +
                "                   Kim jestem?\n";
    }
}
/*
    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }

    public static String job1(){
        return
    }
}*/
