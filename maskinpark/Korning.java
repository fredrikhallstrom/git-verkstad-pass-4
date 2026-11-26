package se.fjax26.maskinpark;

import java.time.LocalDateTime;

public record Korning(String korningId, Anlaggningsmaskin maskin, String forarNamn, Korkort forarKorkortKlass,
                      double timmar, String terrang, LocalDateTime startTid, boolean kvallsPass, String rabattKod) {

    public Korning(String korningId, Anlaggningsmaskin maskin, String forarNamn, Korkort forarKorkortKlass,
                   double timmar, String terrang, boolean kvallsPass) {
        this(korningId, maskin, forarNamn, forarKorkortKlass, timmar, terrang, LocalDateTime.now(), kvallsPass, "");
    }

    // Code Smell: Long Method + Arrow Anti-Pattern + Type Checking (instanceof) + Magic Numbers


    public double beraknaSlutprisOchGenereraRapport(boolean inkluderaMoms, boolean arHelg) {
        double basPris = 0.0;

        // Code smell: Typ-kontroll med instanceof istället för polymorfi!
        if (maskin != null) {
            if (maskin instanceof Gravmaskin) {
                Gravmaskin g = (Gravmaskin) maskin;
                basPris = timmar * g.getTimKostnad();
                if (g.getSkopVolymKbm() > 2.5) {
                    basPris += timmar * 75.0; // Magic number
                }
                if (g.getGrävArmTyp().equals("LONG_REACH")) {
                    basPris += 500.0; // Magic number
                }
            } else if (maskin instanceof Valt) {
                Valt v = (Valt) maskin;
                basPris = timmar * v.getTimKostnad();
                if (v.isVibrationsLageAktivt()) {
                    basPris += timmar * 45.0; // Magic number extra slitage
                }
                if (v.getViktITon() > 10.0) {
                    basPris += 300.0; // Magic number tung vält tillägg
                }
            } else if (maskin instanceof Hjullastare) {
                Hjullastare h = (Hjullastare) maskin;
                basPris = timmar * h.getTimKostnad();
                if (h.isHarGafflarMonterade()) {
                    basPris += timmar * 30.0;
                }
                if (h.getSkopVolymKbm() > 3.0) {
                    basPris += timmar * 60.0;
                }
            } else if (maskin instanceof Lastbil) {
                Lastbil l = (Lastbil) maskin;
                basPris = timmar * l.getTimKostnad();
                if (l.isHarSlap()) {
                    basPris += timmar * 120.0; // Magic number släptillägg
                }
                if (l.getAntalAxlar() >= 4) {
                    basPris += 250.0; // Magic number tung axel avgift
                }
            } else {
                basPris = timmar * maskin.getTimKostnad();
            }
        } else {
            throw new IllegalArgumentException("Maskin saknas för körning");
        }

        // Tillägg för kväll och helg (Arrow Anti-Pattern)
        if (kvallsPass) {
            if (arHelg) {
                basPris *= 1.50; // 50% helg+kvälls OB
            } else {
                basPris *= 1.25; // 25% kvälls OB
            }
        } else {
            if (arHelg) {
                basPris *= 1.30; // 30% helg OB
            }
        }

        // Terrängtillägg
        if (terrang != null) {
            if (terrang.equalsIgnoreCase("BERG")) {
                basPris += timmar * 110.0;
            } else if (terrang.equalsIgnoreCase("LERA") || terrang.equalsIgnoreCase("LERMARK")) {
                basPris += timmar * 65.0;
            }
        }

        // Rabattkodshantering (Nästlad logik)
        if (rabattKod != null && !rabattKod.isBlank()) {
            if (rabattKod.equalsIgnoreCase("SOMMAR2026")) {
                basPris *= 0.90; // 10% rabatt
            } else if (rabattKod.equalsIgnoreCase("PARTNER_FREE_SHIPPING")) {
                basPris -= 200.0;
            }
        }

        // Momsberäkning
        if (inkluderaMoms) {
            basPris *= 1.25; // 25% moms
        }

        return Math.max(0, basPris);
    }

    // Setters
    public Korning setRabattKod(String rabattKod) {
        return new Korning(korningId, maskin, forarNamn, forarKorkortKlass, timmar, terrang, startTid, kvallsPass, rabattKod);}
}