package net.minecraft.server;

// CraftBukkit start

import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
// CraftBukkit end

final class DispenseBehaviorTNT extends DispenseBehaviorItem {

    DispenseBehaviorTNT() {
    }

    protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
        EnumFacing enumfacing = BlockDispenser.b(isourceblock.h());
        World world = isourceblock.k();
        int i = isourceblock.getBlockX() + enumfacing.getAdjacentX();
        int j = isourceblock.getBlockY() + enumfacing.getAdjacentY();
        int k = isourceblock.getBlockZ() + enumfacing.getAdjacentZ();

        // CraftBukkit start
        ItemStack itemstack1 = itemstack.a(1);
        org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockX(), isourceblock.getBlockY(), isourceblock.getBlockZ());
        CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);

        BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(i + 0.5, j + 0.5, k + 0.5));
        if (!BlockDispenser.eventFired) {
            world.getServer().getPluginManager().callEvent(event);
        }

        if (event.isCancelled()) {
            itemstack.count++;
            return itemstack;
        }

        if (!event.getItem().equals(craftItem)) {
            itemstack.count++;
            // Chain to handler for new item
            ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
            IDispenseBehavior idispensebehavior = (IDispenseBehavior) BlockDispenser.a.get(eventStack.getItem());
            if (idispensebehavior != IDispenseBehavior.a && idispensebehavior != this) {
                idispensebehavior.a(isourceblock, eventStack);
                return itemstack;
            }
        }

        // PaperSpigot start - Add FallingBlock and TNT source location API
        org.bukkit.Location loc = new org.bukkit.Location(world.getWorld(), event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ());
        EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(loc, world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), (EntityLiving) null);
        // PaperSpigot end
        // CraftBukkit end

        world.addEntity(entitytntprimed);
        // --itemstack.count; // CraftBukkit - handled above
        return itemstack;
    }
}
