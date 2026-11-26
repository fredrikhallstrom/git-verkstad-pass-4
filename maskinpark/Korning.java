package se.fjax26.maskinpark;

import java.time.LocalDateTime;

public class Korning {

    private String korningId;
    private Anlaggningsmaskin maskin;
    private String forarNamn;
    private String forarKorkortKlass; // Code smell: Primitive Obsession ("CE", "C", "TRAKTOR")
    private double timmar;
    private String terrang;
    private LocalDateTime startTid;
    private boolean kvallsPass;
    private String rabattKod;

    public Korning(String korningId, Anlaggningsmaskin maskin, String forarNamn, String forarKorkortKlass, double timmar, String terrang, boolean kvallsPass) {
        this.korningId = korningId;
        this.maskin = maskin;
        this.forarNamn = forarNamn;
        this.forarKorkortKlass = forarKorkortKlass;
        this.timmar = timmar;
        this.terrang = terrang;
        this.startTid = LocalDateTime.now();
        this.kvallsPass = kvallsPass;
        this.rabattKod = "";
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

    // Getters och Setters
    public String getKorningId() { return korningId; }
    public Anlaggningsmaskin getMaskin() { return maskin; }
    public String getForarNamn() { return forarNamn; }
    public String getForarKorkortKlass() { return forarKorkortKlass; }
    public double getTimmar() { return timmar; }
    public String getTerrang() { return terrang; }
    public LocalDateTime getStartTid() { return startTid; }
    public boolean isKvallsPass() { return kvallsPass; }
    public String getRabattKod() { return rabattKod; }
    public void setRabattKod(String rabattKod) { this.rabattKod = rabattKod; }
}