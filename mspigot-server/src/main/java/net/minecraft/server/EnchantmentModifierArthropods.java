package net.minecraft.server;

final class EnchantmentModifierArthropods implements EnchantmentModifier {

    public EntityLiving a;
    public Entity b;

    private EnchantmentModifierArthropods() {
    }

    EnchantmentModifierArthropods(EmptyClass emptyclass) {
        this();
    }

    public void a(Enchantment enchantment, int i) {
        enchantment.a(this.a, this.b, i);
    }
}
