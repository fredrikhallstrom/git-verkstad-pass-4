package se.fjax26.maskinpark;

import java.util.ArrayList;
import java.util.List;

public record MaskinPark(List<Anlaggningsmaskin> maskiner, List<Korning> korningar) {

    public MaskinPark() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public void registreraMaskin(Anlaggningsmaskin m) {
        if (m != null) {
            maskiner.add(m);
        }
    }

    public void registreraKorning(Korning k) {
        if (k != null) {
            korningar.add(k);
        }
    }

    public void skrivUtAllaMaskiner() {
        System.out.println("=== MASKINPARK ÖVERSIKT (" + maskiner.size() + " maskiner) ===");
        for (Anlaggningsmaskin m : maskiner) {
            System.out.println(" • " + m);
        }
    }

    public void skrivUtAllaKorningar() {
        System.out.println("=== KÖRNINGSRAPPORT (" + korningar.size() + " körpass) ===");
        for (Korning k : korningar) {
            double slutPris = k.beraknaSlutprisOchGenereraRapport(true, false);
            System.out.println(String.format(" • [%s] Förare: %s | Maskin: %s (%s) | Tid: %.1fh | Slutpris (inkl moms): %.2f kr",
                    k.korningId(), k.forarNamn(), k.maskin().getId(), k.maskin().getModell(), k.timmar(), slutPris));
        }
    }
}