package net.minecraft.server;

final class EnchantmentModifierDamage implements EnchantmentModifier {

    public float a;
    public EnumMonsterType b;

    private EnchantmentModifierDamage() {
    }

    EnchantmentModifierDamage(EmptyClass emptyclass) {
        this();
    }

    public void a(Enchantment enchantment, int i) {
        this.a += enchantment.a(i, this.b);
    }
}
