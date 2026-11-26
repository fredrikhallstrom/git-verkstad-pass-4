package se.fjax26.maskinpark;

public class Hjullastare extends Anlaggningsmaskin {

    private double skopVolymKbm;
    private double maxLyftKapacitetTon;
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
        if (tonMaterial > maxLyftKapacitetTon * 50.0) {
            return "FEL: Överbelastning! Materialvikt (" + tonMaterial + "t) överstiger säkerhetsgränsen.";
        }

        double bransleForbrukning = timmar * 13.5 + (tonMaterial * 0.4);
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