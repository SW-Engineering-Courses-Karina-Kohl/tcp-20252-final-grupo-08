package utils;

public enum EnumerateSounds {

    PERCENT_0("0%", 0.00),
    PERCENT_10("10%", 0.10),
    PERCENT_20("20%", 0.20),
    PERCENT_30("30%", 0.30),
    PERCENT_40("40%", 0.40),
    PERCENT_50("50%", 0.50),
    PERCENT_60("60%", 0.60),
    PERCENT_70("70%", 0.70),
    PERCENT_80("80%", 0.80),
    PERCENT_90("90%", 0.90),
    PERCENT_100("100%", 1.00);

    private final String label;
    private final double value;

    EnumerateSounds(String label, double value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return label;
    }

    public static EnumerateSounds fromLabel(String label) {
        for (EnumerateSounds s : values()) {
            if (s.getLabel().equals(label)) {
                return s;
            }
        }
        return null;
    }

}
