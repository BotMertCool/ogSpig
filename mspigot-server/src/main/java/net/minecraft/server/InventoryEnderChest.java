package net.minecraft.server;

// CraftBukkit start

import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

import java.util.List;
// CraftBukkit end

public class InventoryEnderChest extends InventorySubcontainer {

    // CraftBukkit start - add fields and methods
    public List<HumanEntity> transaction = new java.util.ArrayList<HumanEntity>();
    public org.bukkit.entity.Player player;
    private TileEntityEnderChest a;
    private int maxStack = MAX_STACK;

    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }

    public ItemStack[] getContents() {
        return this.items;
    }

    public void onOpen(CraftHumanEntity who) {
        transaction.add(who);
    }

    public void onClose(CraftHumanEntity who) {
        transaction.remove(who);
    }

    public List<HumanEntity> getViewers() {
        return transaction;
    }

    public org.bukkit.inventory.InventoryHolder getOwner() {
        return this.player;
    }

    public int getMaxStackSize() {
        return maxStack;
    }
    // CraftBukkit end

    public void setMaxStackSize(int size) {
        maxStack = size;
    }

    public void a(TileEntityEnderChest tileentityenderchest) {
        this.a = tileentityenderchest;
    }

    public void a(NBTTagList nbttaglist) {
        int i;

        for (i = 0; i < this.getSize(); ++i) {
            this.setItem(i, (ItemStack) null);
        }

        for (i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.get(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < this.getSize()) {
                this.setItem(j, ItemStack.createStack(nbttagcompound));
            }
        }
    }

    public NBTTagList h() {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.getSize(); ++i) {
            ItemStack itemstack = this.getItem(i);

            if (itemstack != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();

                nbttagcompound.setByte("Slot", (byte) i);
                itemstack.save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
        }

        return nbttaglist;
    }

    public boolean a(EntityHuman entityhuman) {
        return this.a != null && !this.a.a(entityhuman) ? false : super.a(entityhuman);
    }

    public void startOpen() {
        if (this.a != null) {
            this.a.a();
        }

        super.startOpen();
    }

    public void closeContainer() {
        if (this.a != null) {
            this.a.b();
        }

        super.closeContainer();
        this.a = null;
    }
}