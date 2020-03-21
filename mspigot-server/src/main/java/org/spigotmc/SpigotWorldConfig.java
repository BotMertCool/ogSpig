package org.spigotmc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.List;

public class SpigotWorldConfig {

    private final String worldName;
    private final YamlConfiguration config;
    public int chunksPerTick;
    public boolean clearChunksOnTick;
    // Crop growth rates
    public int cactusModifier;
    public int caneModifier;
    public int melonModifier;
    public int mushroomModifier;
    public int pumpkinModifier;
    public int saplingModifier;
    public int wheatModifier;
    public double itemMerge;
    public double expMerge;
    public int viewDistance;
    public byte mobSpawnRange;
    public int animalActivationRange = 32;
    public int monsterActivationRange = 32;
    public int miscActivationRange = 16;
    public int playerTrackingRange = 48;
    public int animalTrackingRange = 48;
    public int monsterTrackingRange = 48;
    public int miscTrackingRange = 32;
    public int otherTrackingRange = 64;
    public boolean altHopperTicking;
    public int hopperTransfer;
    public int hopperCheck;
    public int hopperAmount;
    public boolean randomLightUpdates;
    public boolean saveStructureInfo;
    public int itemDespawnRate;
    public int arrowDespawnRate;
    public boolean antiXray;
    public int engineMode;
    public List<Integer> hiddenBlocks;
    public List<Integer> replaceBlocks;
    public AntiXray antiXrayInstance;
    public boolean zombieAggressiveTowardsVillager;
    public boolean nerfSpawnerMobs;
    public boolean enableZombiePigmenPortalSpawns;
    public int maxBulkChunk;
    public int maxCollisionsPerEntity;
    public int dragonDeathSoundRadius;
    public int witherSpawnSoundRadius;
    public int villageSeed;
    public int largeFeatureSeed;
    public float walkExhaustion;
    public float sprintExhaustion;
    public float combatExhaustion;
    public float regenExhaustion;
    public int currentPrimedTnt = 0;
    public int maxTntTicksPerTick;
    public int hangingTickFrequency;
    public int expDespawnRate;
    public boolean mobsEnabled;
    // Poweruser start
    public boolean enderPearlsCanPassNonSolidBlocks;
    public boolean updateMapItemsInPlayerInventory;
    public boolean useAlternateEndSpawn;
    private boolean verbose;
    public SpigotWorldConfig(String worldName) {
        this.worldName = worldName;
        this.config = SpigotConfig.config;
        init();
    }

    public void init() {
        this.verbose = getBoolean("verbose", true);

        log("-------- World Settings For [" + worldName + "] --------");
        SpigotConfig.readConfig(SpigotWorldConfig.class, this);
    }

    private void log(String s) {
        if (verbose) {
            Bukkit.getLogger().info(s);
        }
    }

    private void set(String path, Object val) {
        config.set("world-settings.default." + path, val);
    }

    private boolean getBoolean(String path, boolean def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getBoolean("world-settings." + worldName + "." + path, config.getBoolean("world-settings.default." + path));
    }

    private double getDouble(String path, double def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getDouble("world-settings." + worldName + "." + path, config.getDouble("world-settings.default." + path));
    }

    private int getInt(String path, int def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getInt("world-settings." + worldName + "." + path, config.getInt("world-settings.default." + path));
    }

    private <T> List getList(String path, T def) {
        config.addDefault("world-settings.default." + path, def);
        return (List<T>) config.getList("world-settings." + worldName + "." + path, config.getList("world-settings.default." + path));
    }

    private String getString(String path, String def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getString("world-settings." + worldName + "." + path, config.getString("world-settings.default." + path));
    }

    private void chunksPerTick() {
        chunksPerTick = getInt("chunks-per-tick", 650);
        log("Chunks to Grow per Tick: " + chunksPerTick);

        clearChunksOnTick = getBoolean("clear-tick-list", false);
        log("Clear tick list: " + clearChunksOnTick);
    }

    private int getAndValidateGrowth(String crop) {
        int modifier = getInt("growth." + crop.toLowerCase() + "-modifier", 100);
        if (modifier == 0) {
            log("Cannot set " + crop + " growth to zero, defaulting to 100");
            modifier = 100;
        }
        log(crop + " Growth Modifier: " + modifier + "%");

        return modifier;
    }

    private void growthModifiers() {
        cactusModifier = getAndValidateGrowth("Cactus");
        caneModifier = getAndValidateGrowth("Cane");
        melonModifier = getAndValidateGrowth("Melon");
        mushroomModifier = getAndValidateGrowth("Mushroom");
        pumpkinModifier = getAndValidateGrowth("Pumpkin");
        saplingModifier = getAndValidateGrowth("Sapling");
        wheatModifier = getAndValidateGrowth("Wheat");
    }

    private void itemMerge() {
        itemMerge = getDouble("merge-radius.item", 2.5);
        log("Item Merge Radius: " + itemMerge);
    }

    private void expMerge() {
        expMerge = getDouble("merge-radius.exp", 3.0);
        log("Experience Merge Radius: " + expMerge);
    }

    private void viewDistance() {
        viewDistance = getInt("view-distance", Bukkit.getViewDistance());
        log("View Distance: " + viewDistance);
    }

    private void mobSpawnRange() {
        mobSpawnRange = (byte) getInt("mob-spawn-range", 4);
        log("Mob Spawn Range: " + mobSpawnRange);
    }

    private void activationRange() {
        animalActivationRange = getInt("entity-activation-range.animals", animalActivationRange);
        monsterActivationRange = getInt("entity-activation-range.monsters", monsterActivationRange);
        miscActivationRange = getInt("entity-activation-range.misc", miscActivationRange);
        log("Entity Activation Range: An " + animalActivationRange + " / Mo " + monsterActivationRange + " / Mi " + miscActivationRange);
    }

    private void trackingRange() {
        playerTrackingRange = getInt("entity-tracking-range.players", playerTrackingRange);
        animalTrackingRange = getInt("entity-tracking-range.animals", animalTrackingRange);
        monsterTrackingRange = getInt("entity-tracking-range.monsters", monsterTrackingRange);
        miscTrackingRange = getInt("entity-tracking-range.misc", miscTrackingRange);
        otherTrackingRange = getInt("entity-tracking-range.other", otherTrackingRange);
        log("Entity Tracking Range: Pl " + playerTrackingRange + " / An " + animalTrackingRange + " / Mo " + monsterTrackingRange + " / Mi " + miscTrackingRange + " / Other " + otherTrackingRange);
    }

    private void hoppers() {
        // Alternate ticking method. Uses inventory changes, redstone updates etc.
        // to update hoppers. Hopper-check is disabled when this is true.
        boolean prev = altHopperTicking;
        altHopperTicking = getBoolean("hopper-alt-ticking", false);
        // Necessary for the reload command
        if (prev != altHopperTicking) {
            net.minecraft.server.World world = (net.minecraft.server.World) Bukkit.getWorld(this.worldName);
            if (world != null) {
                if (altHopperTicking) {
                    for (Object o : world.tileEntityList) {
                        if (o instanceof net.minecraft.server.TileEntityHopper) {
                            ((net.minecraft.server.TileEntityHopper) o).convertToScheduling();
                        }
                    }
                } else {
                    for (Object o : world.tileEntityList) {
                        if (o instanceof net.minecraft.server.TileEntityHopper) {
                            ((net.minecraft.server.TileEntityHopper) o).convertToPolling();
                        }
                    }
                }
            }
        }
        // Set the tick delay between hopper item movements
        hopperTransfer = getInt("ticks-per.hopper-transfer", 8);
        // Set the tick delay between checking for items after the associated
        // container is empty. Default to the hopperTransfer value to prevent
        // hopper sorting machines from becoming out of sync.
        hopperCheck = getInt("ticks-per.hopper-check", hopperTransfer);
        hopperAmount = getInt("hopper-amount", 1);
        log("Alternative Hopper Ticking: " + altHopperTicking);
        log("Hopper Transfer: " + hopperTransfer + " Hopper Check: " + hopperCheck + " Hopper Amount: " + hopperAmount);
    }

    private void lightUpdates() {
        randomLightUpdates = getBoolean("random-light-updates", false);
        log("Random Lighting Updates: " + randomLightUpdates);
    }

    private void structureInfo() {
        saveStructureInfo = getBoolean("save-structure-info", true);
        log("Structure Info Saving: " + saveStructureInfo);
        if (!saveStructureInfo) {
            log("*** WARNING *** You have selected to NOT save structure info. This may cause structures such as fortresses to not spawn mobs when updating to 1.7!");
            log("*** WARNING *** Please use this option with caution, SpigotMC is not responsible for any issues this option may cause in the future!");
        }
    }

    private void itemDespawnRate() {
        itemDespawnRate = getInt("item-despawn-rate", 6000);
        log("Item Despawn Rate: " + itemDespawnRate);
    }

    private void arrowDespawnRate() {
        arrowDespawnRate = getInt("arrow-despawn-rate", 1200);
        log("Arrow Despawn Rate: " + arrowDespawnRate);
    }

    private void antiXray() {
        antiXray = getBoolean("anti-xray.enabled", true);
        log("Anti X-Ray: " + antiXray);

        engineMode = getInt("anti-xray.engine-mode", 1);
        log("\tEngine Mode: " + engineMode);

        if (SpigotConfig.version < 5) {
            set("anti-xray.blocks", null);
        }
        hiddenBlocks = getList("anti-xray.hide-blocks", Arrays.asList(new Integer[]
                {
                        14, 15, 16, 21, 48, 49, 54, 56, 73, 74, 82, 129, 130
                }));
        log("\tHidden Blocks: " + hiddenBlocks);

        replaceBlocks = getList("anti-xray.replace-blocks", Arrays.asList(new Integer[]
                {
                        1, 5
                }));
        log("\tReplace Blocks: " + replaceBlocks);

        antiXrayInstance = new AntiXray(this);
    }

    private void zombieAggressiveTowardsVillager() {
        zombieAggressiveTowardsVillager = getBoolean("zombie-aggressive-towards-villager", true);
        log("Zombie Aggressive Towards Villager: " + zombieAggressiveTowardsVillager);
    }

    private void nerfSpawnerMobs() {
        nerfSpawnerMobs = getBoolean("nerf-spawner-mobs", false);
        log("Nerfing mobs spawned from spawners: " + nerfSpawnerMobs);
    }

    private void enableZombiePigmenPortalSpawns() {
        enableZombiePigmenPortalSpawns = getBoolean("enable-zombie-pigmen-portal-spawns", true);
        log("Allow Zombie Pigmen to spawn from portal blocks: " + enableZombiePigmenPortalSpawns);
    }

    private void bulkChunkCount() {
        maxBulkChunk = getInt("max-bulk-chunks", 5);
        log("Sending up to " + maxBulkChunk + " chunks per packet");
    }

    private void maxEntityCollision() {
        maxCollisionsPerEntity = getInt("max-entity-collisions", 8);
        log("Max Entity Collisions: " + maxCollisionsPerEntity);
    }

    private void keepDragonDeathPerWorld() {
        dragonDeathSoundRadius = getInt("dragon-death-sound-radius", 0);
    }

    private void witherSpawnSoundRadius() {
        witherSpawnSoundRadius = getInt("wither-spawn-sound-radius", 0);
    }

    private void initWorldGenSeeds() {
        villageSeed = getInt("seed-village", 10387312);
        largeFeatureSeed = getInt("seed-feature", 14357617);
        log("Custom Map Seeds:  Village: " + villageSeed + " Feature: " + largeFeatureSeed);
    }

    private void initHunger() {
        walkExhaustion = (float) getDouble("hunger.walk-exhaustion", 0.2);
        sprintExhaustion = (float) getDouble("hunger.sprint-exhaustion", 0.8);
        combatExhaustion = (float) getDouble("hunger.combat-exhaustion", 0.3);
        regenExhaustion = (float) getDouble("hunger.regen-exhaustion", 3);
    }

    private void maxTntPerTick() {
        if (SpigotConfig.version < 7) {
            set("max-tnt-per-tick", 100);
        }
        maxTntTicksPerTick = getInt("max-tnt-per-tick", 100);
        log("Max TNT Explosions: " + maxTntTicksPerTick);
    }

    private void hangingTickFrequency() {
        hangingTickFrequency = getInt("hanging-tick-frequency", 100);
    }

    private void expDespawnRate() {
        expDespawnRate = getInt("exp-despawn-rate", 6000);
        log("Experience Orb Despawn Rate: " + expDespawnRate);
    }

    private void mobsEnabled() {
        mobsEnabled = getBoolean("mobs-enabled", true);
        log("Mobs enabled: " + mobsEnabled);
    }

    private void enderPearlsCanPassNonSolidBlocks() {
        enderPearlsCanPassNonSolidBlocks = getBoolean("enderPearlsCanPassNonSolidBlocks", false);
        log("Enderpearls can pass non-solid blocks: " + enderPearlsCanPassNonSolidBlocks);
    }

    private void dontUpdateMapItemsInPlayerInventory() {
        updateMapItemsInPlayerInventory = getBoolean("updateMapItemsInPlayerInventory", false);
    }

    private void useAlternateEndSpawn() {
        useAlternateEndSpawn = getBoolean("useAlternateEndSpawn", false);
    }
    // Poweruser end
}