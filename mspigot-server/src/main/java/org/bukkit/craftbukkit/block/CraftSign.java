package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntitySign;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.CraftWorld;

public class CraftSign extends CraftBlockState implements Sign {
    private final TileEntitySign sign;
    private final String[] lines;

    public CraftSign(final Block block) {
        super(block);

        CraftWorld world = (CraftWorld) block.getWorld();
        sign = (TileEntitySign) world.getTileEntityAt(getX(), getY(), getZ());
        // Spigot start
        if (sign == null) {
            lines = new String[]{"", "", "", ""};
            return;
        }
        // Spigot end
        lines = new String[sign.lines.length];
        System.arraycopy(sign.lines, 0, lines, 0, lines.length);
    }

    public static String[] sanitizeLines(String[] lines) {
        String[] astring = new String[4];

        for (int i = 0; i < 4; i++) {
            if (i < lines.length && lines[i] != null) {
                astring[i] = lines[i];
            } else {
                astring[i] = "";
            }
        }

        return TileEntitySign.sanitizeLines(astring);
    }

    public String[] getLines() {
        return lines;
    }

    public String getLine(int index) throws IndexOutOfBoundsException {
        return lines[index];
    }

    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        lines[index] = line;
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        boolean result = super.update(force, applyPhysics);

        if (result && sign != null) { // Spigot, add null check
            sign.lines = sanitizeLines(lines);
            sign.update();
        }

        return result;
    }
}
