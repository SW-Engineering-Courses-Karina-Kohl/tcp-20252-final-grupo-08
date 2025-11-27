package utils;

public enum EnummerateSounds {

    PERCENT_0("0%", 0.00),
    PERCENT_25("25%", 0.25),
    PERCENT_50("50%", 0.50),
    PERCENT_75("75%", 0.75),
    PERCENT_100("100%", 1.00);

    private final String label;
    private final double value;

    EnummerateSounds(String label, double value) {
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
}
