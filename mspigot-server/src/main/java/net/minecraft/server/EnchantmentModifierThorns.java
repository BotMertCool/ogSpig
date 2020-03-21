package net.minecraft.server;

final class EnchantmentModifierThorns implements EnchantmentModifier {

    public EntityLiving a;
    public Entity b;

    private EnchantmentModifierThorns() {
    }

    EnchantmentModifierThorns(EmptyClass emptyclass) {
        this();
    }

    public void a(Enchantment enchantment, int i) {
        enchantment.b(this.a, this.b, i);
    }
}
