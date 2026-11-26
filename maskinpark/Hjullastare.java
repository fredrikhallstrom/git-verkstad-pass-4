package se.fjax26.maskinpark;

public class Hjullastare extends Anlaggningsmaskin {

    private static final double SAFETY_LIMIT = 50.0;
    private static final double AVERAGE_HOUR_FUEL_CONSUMPTION = 13.5;
    private static final double AVERAGE_FUEL_FACTOR_PER_TON = 0.4;

    private final double skopVolymKbm;
    private final double maxLyftKapacitetTon;
    private boolean harGafflarMonterade;

    public Hjullastare(String id, String modell, double timKostnad, double skopVolymKbm, double maxLyftKapacitetTon) {
        super(id, modell, timKostnad);
        this.skopVolymKbm = skopVolymKbm;
        this.maxLyftKapacitetTon = maxLyftKapacitetTon;
        this.harGafflarMonterade = false;
    }

    public void monteraGafflar() {
        this.harGafflarMonterade = true;
    }

    public void monteraSkopa() {
        this.harGafflarMonterade = false;
    }

    public String utforLyftEllerLastning(double timmar, double tonMaterial) {
        if (tonMaterial > maxLyftKapacitetTon * SAFETY_LIMIT) {
            return "FEL: Överbelastning! Materialvikt (" + tonMaterial + "t) överstiger säkerhetsgränsen.";
        }

        double bransleForbrukning = timmar * AVERAGE_HOUR_FUEL_CONSUMPTION + (tonMaterial * AVERAGE_FUEL_FACTOR_PER_TON);
        setDrifttimmar(getDrifttimmar() + timmar);
        setBransleNivaLiter(Math.max(0, getBransleNivaLiter() - bransleForbrukning));

        String verktyg = harGafflarMonterade ? "Pallgafflar" : "Skopa (" + skopVolymKbm + "m³)";
        return "HJULLASTNING OK: " + getId() + " hanterade " + tonMaterial + " ton på " + timmar + "h med " + verktyg + ".";
    }

    // Getters och Setters
    public double getSkopVolymKbm() { return skopVolymKbm; }
    public double getMaxLyftKapacitetTon() { return maxLyftKapacitetTon; }
    public boolean isHarGafflarMonterade() { return harGafflarMonterade; }
}