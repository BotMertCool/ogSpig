package org.bukkit.craftbukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.Packet;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;
import org.bukkit.craftbukkit.scheduler.CraftTask;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitTask;
import org.spigotmc.CustomTimingsHandler;

import java.util.HashMap;

public class SpigotTimings {

    public static final CustomTimingsHandler serverTickTimer = new CustomTimingsHandler("** Full Server Tick");
    public static final CustomTimingsHandler playerListTimer = new CustomTimingsHandler("Player List");
    public static final CustomTimingsHandler connectionTimer = new CustomTimingsHandler("Connection Handler");
    public static final CustomTimingsHandler tickablesTimer = new CustomTimingsHandler("Tickables");
    public static final CustomTimingsHandler schedulerTimer = new CustomTimingsHandler("Scheduler");
    public static final CustomTimingsHandler chunkIOTickTimer = new CustomTimingsHandler("ChunkIOTick");
    public static final CustomTimingsHandler timeUpdateTimer = new CustomTimingsHandler("Time Update");
    public static final CustomTimingsHandler serverCommandTimer = new CustomTimingsHandler("Server Command");
    public static final CustomTimingsHandler worldSaveTimer = new CustomTimingsHandler("World Save");

    public static final CustomTimingsHandler entityMoveTimer = new CustomTimingsHandler("** entityMove");
    public static final CustomTimingsHandler tickEntityTimer = new CustomTimingsHandler("** tickEntity");
    public static final CustomTimingsHandler activatedEntityTimer = new CustomTimingsHandler("** activatedTickEntity");
    public static final CustomTimingsHandler tickTileEntityTimer = new CustomTimingsHandler("** tickTileEntity");

    public static final CustomTimingsHandler timerEntityBaseTick = new CustomTimingsHandler("** livingEntityBaseTick");
    public static final CustomTimingsHandler timerEntityAI = new CustomTimingsHandler("** livingEntityAI");
    public static final CustomTimingsHandler timerEntityAICollision = new CustomTimingsHandler("** livingEntityAICollision");
    public static final CustomTimingsHandler timerEntityAIMove = new CustomTimingsHandler("** livingEntityAIMove");
    public static final CustomTimingsHandler timerEntityTickRest = new CustomTimingsHandler("** livingEntityTickRest");

    public static final CustomTimingsHandler processQueueTimer = new CustomTimingsHandler("processQueue");
    public static final CustomTimingsHandler schedulerSyncTimer = new CustomTimingsHandler("** Scheduler - Sync Tasks", JavaPluginLoader.pluginParentTimer);

    public static final CustomTimingsHandler playerCommandTimer = new CustomTimingsHandler("** playerCommand");

    public static final CustomTimingsHandler entityActivationCheckTimer = new CustomTimingsHandler("entityActivationCheck");
    public static final CustomTimingsHandler checkIfActiveTimer = new CustomTimingsHandler("** checkIfActive");

    // Poweruser start
    public static final HashMap<String, CustomTimingsHandler> packetHandlerTimingMap = new HashMap<String, CustomTimingsHandler>();
    public static final CustomTimingsHandler timerEntity_C = new CustomTimingsHandler("** livingEntityBaseTick_Entity_C()");
    public static final CustomTimingsHandler timerEntityInsentient_C = new CustomTimingsHandler("** livingEntityBaseTick_EntityInsentient_C()");
    public static final CustomTimingsHandler timerEntityLiving_C = new CustomTimingsHandler("** livingEntityBaseTick_EntityLiving_C()");
    public static final CustomTimingsHandler timerEntity_C_portal = new CustomTimingsHandler("** livingEntityBaseTick_Entity_C()_portal");
    public static final CustomTimingsHandler connectionTimer_PacketFlying_move = new CustomTimingsHandler("** Connection Handler_PacketFlying_move");
    public static final CustomTimingsHandler connectionTimer_PacketFlying_playerChunks = new CustomTimingsHandler("** Connection Handler_PacketFlying_airCheck");
    public static final HashMap<String, CustomTimingsHandler> entityTypeTimingMap = new HashMap<String, CustomTimingsHandler>();
    // Poweruser end
    public static final HashMap<String, CustomTimingsHandler> tileEntityTypeTimingMap = new HashMap<String, CustomTimingsHandler>();
    public static final HashMap<String, CustomTimingsHandler> pluginTaskTimingMap = new HashMap<String, CustomTimingsHandler>();

    public static CustomTimingsHandler getPacketHandlerTimings(Packet packet) {
        String packetType = packet.getClass().getName();
        CustomTimingsHandler result = packetHandlerTimingMap.get(packetType);
        if (result == null) {
            result = new CustomTimingsHandler("** Connection Handler - " + packetType, connectionTimer);
            packetHandlerTimingMap.put(packetType, result);
        }
        return result;
    }

    /**
     * Gets a timer associated with a plugins tasks.
     *
     * @param task
     * @param period
     * @return
     */
    public static CustomTimingsHandler getPluginTaskTimings(BukkitTask task, long period) {
        if (!task.isSync()) {
            return null;
        }
        String plugin;
        final CraftTask ctask = (CraftTask) task;

        if (task.getOwner() != null) {
            plugin = task.getOwner().getDescription().getFullName();
        } else if (ctask.timingName != null) {
            plugin = "CraftScheduler";
        } else {
            plugin = "Unknown";
        }
        String taskname = ctask.getTaskName();

        String name = "Task: " + plugin + " Runnable: " + taskname;
        if (period > 0) {
            name += "(interval:" + period + ")";
        } else {
            name += "(Single)";
        }
        CustomTimingsHandler result = pluginTaskTimingMap.get(name);
        if (result == null) {
            result = new CustomTimingsHandler(name, SpigotTimings.schedulerSyncTimer);
            pluginTaskTimingMap.put(name, result);
        }
        return result;
    }

    /**
     * Get a named timer for the specified entity type to track type specific timings.
     *
     * @param entity
     * @return
     */
    public static CustomTimingsHandler getEntityTimings(Entity entity) {
        String entityType = entity.getClass().getName();
        CustomTimingsHandler result = entityTypeTimingMap.get(entityType);
        if (result == null) {
            result = new CustomTimingsHandler("** tickEntity - " + entityType, activatedEntityTimer);
            entityTypeTimingMap.put(entityType, result);
        }
        return result;
    }

    /**
     * Get a named timer for the specified tile entity type to track type specific timings.
     *
     * @param entity
     * @return
     */
    public static CustomTimingsHandler getTileEntityTimings(TileEntity entity) {
        String entityType = entity.getClass().getName();
        CustomTimingsHandler result = tileEntityTypeTimingMap.get(entityType);
        if (result == null) {
            result = new CustomTimingsHandler("** tickTileEntity - " + entityType, tickTileEntityTimer);
            tileEntityTypeTimingMap.put(entityType, result);
        }
        return result;
    }

    /**
     * Set of timers per world, to track world specific timings.
     */
    public static class WorldTimingsHandler {
        public final CustomTimingsHandler mobSpawn;
        public final CustomTimingsHandler doChunkUnload;
        public final CustomTimingsHandler doChunkUnloadSave;
        public final CustomTimingsHandler doPortalForcer;
        public final CustomTimingsHandler doTickPending;
        public final CustomTimingsHandler doTickTiles;
        public final CustomTimingsHandler doTickTiles_buildList;
        public final CustomTimingsHandler doTickTiles_tickingChunks;
        public final CustomTimingsHandler doTickTiles_tickingChunks_getChunk;
        public final CustomTimingsHandler doTickTiles_tickingChunks_tickChunk;
        public final CustomTimingsHandler doTickTiles_tickingChunks_iceAndSnow;
        public final CustomTimingsHandler doTickTiles_tickingChunks_tickBlocks;
        public final CustomTimingsHandler doVillages;
        public final CustomTimingsHandler doChunkMap;
        public final CustomTimingsHandler doChunkGC;
        public final CustomTimingsHandler doSounds;
        public final CustomTimingsHandler entityTick;
        public final CustomTimingsHandler tileEntityTick;
        public final CustomTimingsHandler tileEntityPending;
        public final CustomTimingsHandler tracker;
        public final CustomTimingsHandler doTick;
        public final CustomTimingsHandler tickEntities;
        // Poweruser start
        public final CustomTimingsHandler entityPlayerTickNormal;
        public final CustomTimingsHandler entityPlayerTickOnMove;
        // Poweruser end

        public final CustomTimingsHandler syncChunkLoadTimer;
        public final CustomTimingsHandler syncChunkLoadDataTimer;
        public final CustomTimingsHandler syncChunkLoadStructuresTimer;
        public final CustomTimingsHandler syncChunkLoadEntitiesTimer;
        public final CustomTimingsHandler syncChunkLoadTileEntitiesTimer;
        public final CustomTimingsHandler syncChunkLoadTileTicksTimer;
        public final CustomTimingsHandler syncChunkLoadPostTimer;

        public WorldTimingsHandler(World server) {
            String name = server.worldData.getName() + " - ";

            mobSpawn = new CustomTimingsHandler("** " + name + "mobSpawn");
            doChunkUnload = new CustomTimingsHandler("** " + name + "doChunkUnload");
            doChunkUnloadSave = new CustomTimingsHandler("** " + name + "doChunkUnload_save");
            doTickPending = new CustomTimingsHandler("** " + name + "doTickPending");
            doTickTiles = new CustomTimingsHandler("** " + name + "doTickTiles");
            doTickTiles_buildList = new CustomTimingsHandler("** " + name + "doTickTiles_buildList");
            doTickTiles_tickingChunks = new CustomTimingsHandler("** " + name + "doTickTiles_tickingChunks");
            doTickTiles_tickingChunks_getChunk = new CustomTimingsHandler("** " + name + "doTickTiles_tickingChunks_getChunk");
            doTickTiles_tickingChunks_tickChunk = new CustomTimingsHandler("** " + name + "doTickTiles_tickingChunks_tickChunk");
            doTickTiles_tickingChunks_iceAndSnow = new CustomTimingsHandler("** " + name + "doTickTiles_tickingChunks_iceAndSnow");
            doTickTiles_tickingChunks_tickBlocks = new CustomTimingsHandler("** " + name + "doTickTiles_tickingChunks_tickBlocks");
            doVillages = new CustomTimingsHandler("** " + name + "doVillages");
            doChunkMap = new CustomTimingsHandler("** " + name + "doChunkMap");
            doSounds = new CustomTimingsHandler("** " + name + "doSounds");
            doChunkGC = new CustomTimingsHandler("** " + name + "doChunkGC");
            doPortalForcer = new CustomTimingsHandler("** " + name + "doPortalForcer");
            entityTick = new CustomTimingsHandler("** " + name + "entityTick");
            tileEntityTick = new CustomTimingsHandler("** " + name + "tileEntityTick");
            tileEntityPending = new CustomTimingsHandler("** " + name + "tileEntityPending");
            // Poweruser start
            entityPlayerTickNormal = new CustomTimingsHandler("** " + name + "entityPlayerTick_normal");
            entityPlayerTickOnMove = new CustomTimingsHandler("** " + name + "entityPlayerTick_onMove");
            // Poweruser end

            syncChunkLoadTimer = new CustomTimingsHandler("** " + name + "syncChunkLoad");
            syncChunkLoadDataTimer = new CustomTimingsHandler("** " + name + "syncChunkLoad - Data");
            syncChunkLoadStructuresTimer = new CustomTimingsHandler("** " + name + "chunkLoad - Structures");
            syncChunkLoadEntitiesTimer = new CustomTimingsHandler("** " + name + "chunkLoad - Entities");
            syncChunkLoadTileEntitiesTimer = new CustomTimingsHandler("** " + name + "chunkLoad - TileEntities");
            syncChunkLoadTileTicksTimer = new CustomTimingsHandler("** " + name + "chunkLoad - TileTicks");
            syncChunkLoadPostTimer = new CustomTimingsHandler("** " + name + "chunkLoad - Post");


            tracker = new CustomTimingsHandler(name + "tracker");
            doTick = new CustomTimingsHandler(name + "doTick");
            tickEntities = new CustomTimingsHandler(name + "tickEntities");
        }
    }
}
