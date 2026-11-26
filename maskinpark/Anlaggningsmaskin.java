package se.fjax26.maskinpark;

public abstract class Anlaggningsmaskin {

    private final String id;
    private final String modell;
    private double timKostnad;
    private double drifttimmar;
    private double bransleNivaLiter; // Procent eller Liter (här liter, max 200L)
    private String status;

    protected Anlaggningsmaskin(String id, String modell, double timKostnad) {
        this.id = id;
        this.modell = modell;
        this.timKostnad = timKostnad;
        this.drifttimmar = 0.0;
        this.bransleNivaLiter = 150.0; // Startar med 150L
        this.status = "REDO";
    }

    public void tanka(double liter) {
        this.bransleNivaLiter = Math.min(200.0, this.bransleNivaLiter + liter);
        if (this.bransleNivaLiter > 20.0 && this.status.equals("BRÄNSLE_LÅGT")) {
            this.status = "REDO";
        }
    }

    // Getters och Setters
    public String getId() { return id; }
    public String getModell() { return modell; }
    public double getTimKostnad() { return timKostnad; }
    public void setTimKostnad(double timKostnad) { this.timKostnad = timKostnad; }
    public double getDrifttimmar() { return drifttimmar; }
    protected void setDrifttimmar(double drifttimmar) { this.drifttimmar = drifttimmar; }
    public double getBransleNivaLiter() { return bransleNivaLiter; }
    protected void setBransleNivaLiter(double bransleNivaLiter) { this.bransleNivaLiter = bransleNivaLiter; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - Timkostnad: %.2f kr/h | Timmar: %.1fh | Bränsle: %.1fL | Status: %s",
                id, modell, getClass().getSimpleName(), timKostnad, drifttimmar, bransleNivaLiter, status);
    }
}
