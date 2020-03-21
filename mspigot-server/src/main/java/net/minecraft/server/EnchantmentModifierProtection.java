package net.minecraft.server;

final class EnchantmentModifierProtection implements EnchantmentModifier {

    public int a;
    public DamageSource b;

    private EnchantmentModifierProtection() {
    }

    EnchantmentModifierProtection(EmptyClass emptyclass) {
        this();
    }

    public void a(Enchantment enchantment, int i) {
        this.a += enchantment.a(i, this.b);
    }
}
