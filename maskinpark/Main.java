package se.fjax26.maskinpark;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== GEKÅS/FJAX MASKINPARK OCH LOGISTIKVERKSTAD (PASS 4) ===");

        // 1. Skapa maskinpark
        MaskinPark park = new MaskinPark();

        // 2. Skapa de fyra maskintyperna
        Gravmaskin g1 = new Gravmaskin("G-101", "Volvo EC220E", 850.0, 1.8);
        Valt v1 = new Valt("V-201", "Dynapac CA2500D", 650.0, 12.5, 2.1);
        Hjullastare h1 = new Hjullastare("H-301", "Caterpillar 950M", 750.0, 3.2, 7.5);
        Lastbil l1 = new Lastbil("L-401", "Scania R580 Dumper", 950.0, 24.0, 4, true);

        park.registreraMaskin(g1);
        park.registreraMaskin(v1);
        park.registreraMaskin(h1);
        park.registreraMaskin(l1);

        // 3. Utför specifika arbetspass på maskinerna
        System.out.println("--- KÖR ARBETSPASS ---");
        System.out.println(g1.utforArbetspass(6.5, "BERG", "JUNIOR", true));

        v1.aktiveraVibration();
        System.out.println(v1.utforVältning(4.0, "Krossgrus 0-90"));

        h1.monteraGafflar();
        System.out.println(h1.utforLyftEllerLastning(5.0, 120.0));

        System.out.println(l1.utforTransport(140.0, 38.0));

        // 4. Skapa och registrera körningar (som utnyttjar Korning.java med nästlad beräkning)
        Korning k1 = new Korning("KRN-001", g1, "Anna Andersson", Korkort.CE, 6.5, "BERG", true);
        k1 = k1.setRabattKod("SOMMAR2026");

        Korning k2 = new Korning("KRN-002", v1, "Björn Berg", Korkort.C, 4.0, "GRUS", false);

        Korning k3 = new Korning("KRN-003", h1, "Cecilia Ek", Korkort.CE, 5.0, "NORMAL", true);

        Korning k4 = new Korning("KRN-004", l1, "David Lind", Korkort.CE, 3.0, "NORMAL", false);

        park.registreraKorning(k1);
        park.registreraKorning(k2);
        park.registreraKorning(k3);
        park.registreraKorning(k4);

        // 5. Skriv ut rapporter
        System.out.println();
        park.skrivUtAllaMaskiner();
        park.skrivUtAllaKorningar();
    }
}
