package Domain;

import java.util.Random;

public class SpellEffects {

    public static void castUpgrade(Player caster, MonsterCard target) {
        MagicStatus buff = new MagicStatus(
                "Upgrade",     // nome
                +500,          // ataque
                +500,          // defesa
                2              // duração em turnos
        );

        target.applyStatus(buff);
        caster.registerEffectUse("Upgrade");
    }

    public static void castFireball(Player caster, MonsterCard target) {
        int damage = 600;

        target.receiveDamage(damage);
        caster.registerEffectUse("Fireball");

        castBurn(caster, target);
    }

    public static void castHeal(Player caster, MonsterCard target) {
        int amount = 400;

        target.heal(amount);
        caster.registerEffectUse("Heal");

        castShield(caster, target);
    }

    public static void castShield(Player caster, MonsterCard target) {
        MagicStatus shield = new MagicStatus(
                "Shield",
                0,     // ataque não muda
                +300,  // aumenta defesa (absorção de dano)
                2      // dura 2 turnos
        );

        target.applyStatus(shield);
        caster.registerEffectUse("Shield");
    }

    public static void castBurn(Player caster, MonsterCard target) {

        int duration = new Random().nextInt(4); // 0 a 3 turnos
        if (duration == 0) {
            caster.registerEffectUse("Burn (no effect)");
            return;
        }

        MagicStatus burn = new MagicStatus(
                "Burn",
                -150,  // reduz ataque
                -150,  // reduz defesa
                duration
        );

        target.applyStatus(burn);
        caster.registerEffectUse("Burn");
    }


}
