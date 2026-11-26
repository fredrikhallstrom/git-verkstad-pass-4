package se.fjax26.maskinpark;

public abstract class Anlaggningsmaskin {

    private String id;
    private String modell;
    private double timKostnad;
    private double drifttimmar;
    private double bransleNiva; // Procent eller Liter (här liter, max 200L)
    private String status;

    public Anlaggningsmaskin(String id, String modell, double timKostnad) {
        this.id = id;
        this.modell = modell;
        this.timKostnad = timKostnad;
        this.drifttimmar = 0.0;
        this.bransleNiva = 150.0; // Startar med 150L
        this.status = "REDO";
    }

    public void tanka(double liter) {
        this.bransleNiva = Math.min(200.0, this.bransleNiva + liter);
        if (this.bransleNiva > 20.0 && this.status.equals("BRÄNSLE_LÅGT")) {
            this.status = "REDO";
        }
    }

    // Getters och Setters
    public String getId() { return id; }
    public String getModell() { return modell; }
    public double getTimKostnad() { return timKostnad; }
    public double getBransleNiva() { return bransleNiva; }
    public double getDrifttimmar() { return drifttimmar; }
    public String getStatus() { return status; }
    public void setTimKostnad(double timKostnad) { this.timKostnad = timKostnad; }
    protected void setDrifttimmar(double drifttimmar) { this.drifttimmar = drifttimmar; }
    protected void setBransleNiva(double bransleNiva) { this.bransleNiva = bransleNiva; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - Timkostnad: %.2f kr/h | Timmar: %.1fh | Bränsle: %.1fL | Status: %s",
                id, modell, getClass().getSimpleName(), timKostnad, drifttimmar, bransleNiva, status);
    }
}
