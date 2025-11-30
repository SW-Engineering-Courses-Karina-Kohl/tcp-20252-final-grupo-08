package utils;

public enum EnumerateScales {

    SCALE_480P("480p", 640, 480),
    SCALE_720P("720p", 1280, 720),
    SCALE_1080P("1080p", 1920, 1080),
    SCALE_1440P("1440p", 2560, 1440);

    private final String label;
    private final int width;
    private final int height;

    EnumerateScales(String label, int width, int height) {
        this.label = label;
        this.width = width;
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return label;
    }

    // 🔥 Converte o texto do JComboBox ("480p") → enum correspondente
    public static EnumerateScales fromLabel(String label) {
        for (EnumerateScales s : values()) {
            if (s.label.equalsIgnoreCase(label)) {
                return s;
            }
        }
        return null;
    }
}
