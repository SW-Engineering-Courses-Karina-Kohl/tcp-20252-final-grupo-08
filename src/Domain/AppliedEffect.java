package Domain;

public class AppliedEffect {

    private final String effectName;
    private int count;

    public AppliedEffect(String effectName) {
        this.effectName = effectName;
        this.count = 1;
    }

    public String getEffectName() {
        return effectName;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        this.count++;
    }
}
