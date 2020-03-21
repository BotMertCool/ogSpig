package net.frozenorb.chunksnapshot;

import net.minecraft.server.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class CraftChunkSnapshot implements ChunkSnapshot {

    private final ChunkSectionSnapshot[] sections = new ChunkSectionSnapshot[16];
    private final List<NBTTagCompound> tileEntities = new ArrayList<NBTTagCompound>();

    public ChunkSectionSnapshot[] getSections() {
        return this.sections;
    }

    public List<NBTTagCompound> getTileEntities() {
        return this.tileEntities;
    }
}
