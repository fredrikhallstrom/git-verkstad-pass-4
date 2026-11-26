package se.fjax26.maskinpark;

public class Gravmaskin extends Anlaggningsmaskin {

    private double skopVolymKbm;
    private double hydraulTryckBar;
    private String grävArmTyp; // Code smell: Primitive Obsession ("STANDARD", "TELESKOP", "LONG_REACH")
    private int skopSlitageProcent;

    public Gravmaskin(String id, String modell, double timKostnad, double skopVolymKbm) {
        super(id, modell, timKostnad);
        this.skopVolymKbm = skopVolymKbm;
        this.hydraulTryckBar = 210.0;
        this.grävArmTyp = "STANDARD";
        this.skopSlitageProcent = 15;
    }

    // Code Smell: Long Method + Arrow Anti-Pattern + Magic Numbers + Primitive Obsession
    public String utforArbetspass(double timmar, String terrangTyp, String operatorNiva, boolean harTungSkopa) {
        // Code Smell: Magic Numbers & Deep Nesting
        if (timmar > 0) {
            if (timmar <= 16) {
                if (terrangTyp != null) {
                    if (terrangTyp.equalsIgnoreCase("BERG") || terrangTyp.equalsIgnoreCase("LERMARK") || terrangTyp.equalsIgnoreCase("LERA")) {
                        if (operatorNiva != null && operatorNiva.equalsIgnoreCase("JUNIOR")) {
                            // Extra slitage för junior i berg
                            skopSlitageProcent += (int) (timmar * 3.5);
                            this.hydraulTryckBar -= timmar * 2.1;
                        } else if (operatorNiva != null && operatorNiva.equalsIgnoreCase("SENIOR")) {
                            skopSlitageProcent += (int) (timmar * 1.8);
                            this.hydraulTryckBar -= timmar * 1.0;
                        } else {
                            skopSlitageProcent += (int) (timmar * 2.5);
                            this.hydraulTryckBar -= timmar * 1.5;
                        }

                        double bransleForbrukning = timmar * 18.5; // Magic number
                        if (harTungSkopa) {
                            bransleForbrukning += timmar * 4.2; // Magic number
                            if (skopVolymKbm > 2.0) {
                                bransleForbrukning *= 1.15; // Magic number
                            }
                        }

                        if (terrangTyp.equalsIgnoreCase("BERG")) {
                            bransleForbrukning *= 1.25; // Magic number
                        }

                        double kostnad = timmar * getTimKostnad();
                        if (grävArmTyp.equals("LONG_REACH")) {
                            kostnad += timmar * 150.0; // Magic number
                        } else if (grävArmTyp.equals("TELESKOP")) {
                            kostnad += timmar * 85.0; // Magic number
                        }

                        // Uppdatera maskintillstånd
                        setDrifttimmar(getDrifttimmar() + timmar);
                        setBransleNivaLiter(Math.max(0, getBransleNivaLiter() - bransleForbrukning));

                        // Hårdkodad rapportsträng med mer nästling
                        if (getBransleNivaLiter() < 15.0) {
                            if (skopSlitageProcent > 80) {
                                setStatus("SERVICE_KRÄVS_KRITISK");
                                return "VARNING: Bränsle lågt (" + String.format("%.1f", getBransleNivaLiter()) + "L) OCH skopa utsliten (" + skopSlitageProcent + "%). Totalkostnad: " + kostnad + " kr.";
                            } else {
                                setStatus("BRÄNSLE_LÅGT");
                                return "VARNING: Bränsle lågt. Körning genomförd: " + timmar + "h. Kostnad: " + kostnad + " kr.";
                            }
                        } else {
                            setStatus("REDO");
                            return "ARBETSPASS OK: " + getId() + " (" + getModell() + ") kördes " + timmar + "h i " + terrangTyp + ". Förbrukning: " + String.format("%.1f", bransleForbrukning) + "L. Kostnad: " + kostnad + " kr.";
                        }
                    } else if (terrangTyp.equalsIgnoreCase("NORMAL") || terrangTyp.equalsIgnoreCase("GRUS")) {
                        double bransleForbrukning = timmar * 14.0;
                        if (harTungSkopa) {
                            bransleForbrukning += timmar * 2.5;
                        }
                        double kostnad = timmar * getTimKostnad();
                        setDrifttimmar(getDrifttimmar() + timmar);
                        setBransleNivaLiter(Math.max(0, getBransleNivaLiter() - bransleForbrukning));
                        return "ARBETSPASS OK: " + getId() + " kördes " + timmar + "h i " + terrangTyp + ". Kostnad: " + kostnad + " kr.";
                    } else {
                        return "FEL: Okänd terrängtyp: " + terrangTyp;
                    }
                } else {
                    return "FEL: Terrängtyp får inte vara null";
                }
            } else {
                return "FEL: Ett arbetspass kan inte överstiga 16 timmar";
            }
        } else {
            return "FEL: Ogiltigt antal timmar: " + timmar;
        }
    }

    // Getters och Setters
    public double getSkopVolymKbm() { return skopVolymKbm; }
    public void setSkopVolymKbm(double skopVolymKbm) { this.skopVolymKbm = skopVolymKbm; }
    public double getHydraulTryckBar() { return hydraulTryckBar; }
    public void setHydraulTryckBar(double hydraulTryckBar) { this.hydraulTryckBar = hydraulTryckBar; }
    public String getGrävArmTyp() { return grävArmTyp; }
    public void setGrävArmTyp(String grävArmTyp) { this.grävArmTyp = grävArmTyp; }
    public int getSkopSlitageProcent() { return skopSlitageProcent; }
    public void setSkopSlitageProcent(int skopSlitageProcent) { this.skopSlitageProcent = skopSlitageProcent; }
}