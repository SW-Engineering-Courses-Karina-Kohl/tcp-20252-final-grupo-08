package Domain;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CardStatusIcons {

    private static final Map<String, Image> ICONS = new HashMap<>();

    static {
        ICONS.put("Upgrade", load("/resources/status/update_status.png"));
        ICONS.put("Heal",    load("/resources/status/heal_status.png"));
        ICONS.put("Burn",    load("/resources/status/burn_status.png"));
    }

    private static Image load(String path) {
        try {
            return new ImageIcon(Objects.requireNonNull(CardStatusIcons.class.getResource(path))).getImage();
        } catch (Exception e) {
            System.err.println("Falha ao carregar ícone: " + path);
            return null;
        }
    }

    public static Image getIcon(String effectName) {
        return ICONS.get(effectName);
    }
}
