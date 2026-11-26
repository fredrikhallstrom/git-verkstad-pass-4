package se.fjax26.maskinpark;

public class Valt extends Anlaggningsmaskin {

    private static final int MINIMUM_USE_TIME = 0;
    private static final double FUEL_CONSUMPTION_VIBRATION = 16.5;
    private static final double FUEL_CONSUMPTION_NO_VIBRATION = 11.0;

    private static final String ERROR_INVALID_TIME = "FEL: Ogiltig tid";

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
        if (timmar <= MINIMUM_USE_TIME) return ERROR_INVALID_TIME;

        double bransleForbrukning = timmar * (vibrationsLageAktivt ? FUEL_CONSUMPTION_VIBRATION : FUEL_CONSUMPTION_NO_VIBRATION);
        setDrifttimmar(getDrifttimmar() + timmar);
        setBransleNivaLiter(Math.max(0, getBransleNivaLiter() - bransleForbrukning));

        return "VÄLTNING UTFÖRD: " + getId() + " (" + getModell() + ") packade " + underlag + " i " + timmar + "h. Vibration: " + (vibrationsLageAktivt ? "JA" : "NEJ");
    }

    // Getters och Setters
    public double getViktITon() { return viktITon; }
    public double getTrummaBreddMeter() { return trummaBreddMeter; }
    public boolean isVibrationsLageAktivt() { return vibrationsLageAktivt; }
}