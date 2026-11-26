package se.fjax26.maskinpark;

public class Valt extends Anlaggningsmaskin {

    private final double viktITon;
    private final double trummaBreddMeter;
    private boolean vibrationsLageAktivt;

    public Valt(String id, String modell, double timKostnad, double viktITon, double trummaBreddMeter) {
        super(id, modell, timKostnad);
        this.viktITon = viktITon;
        this.trummaBreddMeter = trummaBreddMeter;
        this.vibrationsLageAktivt = false;
    }

    public void aktiveraVibration() {
        this.vibrationsLageAktivt = true;
    }

    public void inaktiveraVibration() {
        this.vibrationsLageAktivt = false;
    }

    public String utforValtning(double timmar, String underlag) {
        if (timmar <= 0) return "FEL: Ogiltig tid";

        double bransleForbrukning = timmar * (vibrationsLageAktivt ? 16.5 : 11.0);
        setDrifttimmar(getDrifttimmar() + timmar);
        setBransleNivaLiter(Math.max(0, getBransleNivaLiter() - bransleForbrukning));

        return "VÄLTNING UTFÖRD: " + getId() + " (" + getModell() + ") packade " + underlag + " i " + timmar + "h. Vibration: " + (vibrationsLageAktivt ? "JA" : "NEJ");
    }

    // Getters och Setters
    public double getViktITon() { return viktITon; }
    public double getTrummaBreddMeter() { return trummaBreddMeter; }
    public boolean isVibrationsLageAktivt() { return vibrationsLageAktivt; }
}