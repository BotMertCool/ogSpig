package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.ContainerEnchantTableInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryEnchanting extends CraftInventory implements EnchantingInventory {
    public CraftInventoryEnchanting(ContainerEnchantTableInventory inventory) {
        super(inventory);
    }

    public ItemStack getItem() {
        return getItem(0);
    }

    public void setItem(ItemStack item) {
        setItem(0, item);
    }

    @Override
    public ContainerEnchantTableInventory getInventory() {
        return (ContainerEnchantTableInventory) inventory;
    }
}
