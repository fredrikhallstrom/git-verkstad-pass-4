package se.fjax26.maskinpark;

public class Valt extends Anlaggningsmaskin {

    private double viktITon;
    private double trummBreddMeter;
    private boolean vibrationsLageAktivt;

    public Valt(String id, String modell, double timKostnad, double viktITon, double trummBreddMeter) {
        super(id, modell, timKostnad);
        this.viktITon = viktITon;
        this.trummBreddMeter = trummBreddMeter;
        this.vibrationsLageAktivt = false;
    }

    public void aktiveraVibration() {
        this.vibrationsLageAktivt = true;
    }

    public void inaktiveraVibration() {
        this.vibrationsLageAktivt = false;
    }

    public String utforVältning(double timmar, String underlag) {
        if (timmar <= 0) return "FEL: Ogiltig tid";

        double bransleForbrukning = timmar * (vibrationsLageAktivt ? 16.5 : 11.0);
        setDrifttimmar(getDrifttimmar() + timmar);
        setBransleNiva(Math.max(0, getBransleNiva() - bransleForbrukning));

        return "VÄLTNING UTFÖRD: " + getId() + " (" + getModell() + ") packade " + underlag + " i " + timmar + "h. Vibration: " + (vibrationsLageAktivt ? "JA" : "NEJ");
    }

    // Getters och Setters
    public double getViktITon() { return viktITon; }
    public double getTrummBreddMeter() { return trummBreddMeter; }
    public boolean isVibrationsLageAktivt() { return vibrationsLageAktivt; }
}