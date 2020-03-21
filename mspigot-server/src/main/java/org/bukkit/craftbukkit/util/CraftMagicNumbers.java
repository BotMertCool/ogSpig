package org.bukkit.craftbukkit.util;

import net.minecraft.server.*;
import org.bukkit.Achievement;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.UnsafeValues;
import org.bukkit.craftbukkit.CraftStatistic;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("deprecation")
public final class CraftMagicNumbers implements UnsafeValues {
    public static final UnsafeValues INSTANCE = new CraftMagicNumbers();

    private CraftMagicNumbers() {
    }

    public static Block getBlock(org.bukkit.block.Block block) {
        return getBlock(block.getType());
    }

    @Deprecated
    // A bad method for bad magic.
    public static Block getBlock(int id) {
        return getBlock(Material.getMaterial(id));
    }

    @Deprecated
    // A bad method for bad magic.
    public static int getId(Block block) {
        return Block.getId(block);
    }

    public static Material getMaterial(Block block) {
        return Material.getMaterial(Block.getId(block));
    }

    public static Item getItem(Material material) {
        // TODO: Don't use ID
        Item item = Item.getById(material.getId());
        return item;
    }

    @Deprecated
    // A bad method for bad magic.
    public static Item getItem(int id) {
        return Item.getById(id);
    }

    @Deprecated
    // A bad method for bad magic.
    public static int getId(Item item) {
        return Item.getId(item);
    }

    public static Material getMaterial(Item item) {
        // TODO: Don't use ID
        Material material = Material.getMaterial(Item.getId(item));

        if (material == null) {
            return Material.AIR;
        }

        return material;
    }

    public static Block getBlock(Material material) {
        // TODO: Don't use ID
        Block block = Block.getById(material.getId());

        if (block == null) {
            return Blocks.AIR;
        }

        return block;
    }

    @Override
    public Material getMaterialFromInternalName(String name) {
        return getMaterial((Item) Item.REGISTRY.get(name));
    }

    @Override
    public List<String> tabCompleteInternalMaterialName(String token, List<String> completions) {
        return StringUtil.copyPartialMatches(token, Item.REGISTRY.keySet(), completions);
    }

    @Override
    public ItemStack modifyItemStack(ItemStack stack, String arguments) {
        net.minecraft.server.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        nmsStack.setTag((NBTTagCompound) MojangsonParser.parse(arguments));

        stack.setItemMeta(CraftItemStack.getItemMeta(nmsStack));

        return stack;
    }

    @Override
    public Statistic getStatisticFromInternalName(String name) {
        return CraftStatistic.getBukkitStatisticByName(name);
    }

    @Override
    public Achievement getAchievementFromInternalName(String name) {
        return CraftStatistic.getBukkitAchievementByName(name);
    }

    @Override
    public List<String> tabCompleteInternalStatisticOrAchievementName(String token, List<String> completions) {
        List<String> matches = new ArrayList<String>();
        Iterator iterator = StatisticList.stats.iterator();
        while (iterator.hasNext()) {
            String statistic = ((net.minecraft.server.Statistic) iterator.next()).name;
            if (statistic.startsWith(token)) {
                matches.add(statistic);
            }
        }
        return matches;
    }
}
