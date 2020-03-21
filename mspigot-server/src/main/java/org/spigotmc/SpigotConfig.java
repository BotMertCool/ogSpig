package org.spigotmc;

import com.google.common.base.Throwables;
import net.hylist.command.KnockbackCommand;
import net.hylist.json.JsonConfig;
import net.hylist.knockback.CraftKnockbackProfile;
import net.hylist.knockback.KnockbackProfile;
import net.minecraft.server.AttributeRanged;
import net.minecraft.server.GenericAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

public class SpigotConfig {

    public static final List<KnockbackProfile> kbProfiles = new ArrayList<>();
    private static final File CONFIG_FILE = new File("config/server", "spigot.yml"); // MineHQ - Dedicated config directory
    private static final String HEADER = "This is the main configuration file for Spigot.\n"
            + "As you can see, there's tons to configure. Some options may impact gameplay, so use\n"
            + "with caution, and make sure you know what each option does before configuring.\n"
            + "For a reference for any variable inside this file, check out the Spigot wiki at\n"
            + "http://www.spigotmc.org/wiki/spigot-configuration/\n"
            + "\n"
            + "If you need help with the configuration or have any questions related to Spigot,\n"
            + "join us at the IRC or drop by our forums and leave a post.\n"
            + "\n"
            + "IRC: #spigot @ irc.spi.gt ( http://www.spigotmc.org/pages/irc/ )\n"
            + "Forums: http://www.spigotmc.org/\n";
    public static KnockbackProfile globalKbProfile;
    /*========================================================================*/
    public static YamlConfiguration config;
    public static boolean logCommands;
    public static int tabComplete;
    // joeleoli end
    public static String whitelistMessage;
    public static String unknownCommandMessage;
    public static String serverFullMessage;
    public static String outdatedClientMessage = "Outdated client! Please use {0}";
    public static String outdatedServerMessage = "Outdated server! I\'m still on {0}";
    public static int timeoutTime = 10;
    public static boolean restartOnCrash = true;
    public static String restartScript = "./start.sh";
    public static String restartMessage;
    public static boolean bungee;
    public static boolean lateBind;
    public static boolean disableStatSaving = true;
    public static TObjectIntHashMap<String> forcedStats = new TObjectIntHashMap<String>();
    public static int playerSample;
    public static int playerShuffle;
    public static List<String> spamExclusions;
    public static boolean silentCommandBlocks;
    public static boolean filterCreativeItems;
    public static Set<String> replaceCommands;
    public static int userCacheCap;
    public static boolean saveUserCacheOnStopOnly;
    public static int intCacheLimit;
    public static double movedWronglyThreshold;
    public static double movedTooQuicklyThreshold;
    public static double maxHealth = 2048;
    public static double movementSpeed = 2048;
    public static double attackDamage = 2048;
    public static boolean debug;
    // Poweruser start
    public static boolean disablePlayerFileSaving;
    public static boolean logRemainingAsyncThreadsDuringShutdown;
    public static int playersPerChunkIOThread;
    public static int autoSaveChunksPerTick;
    public static boolean autoSaveFireWorldSaveEvent;
    public static boolean autoSaveClearRegionFileCache;
    public static boolean lagSpikeLoggerEnabled;
    public static long lagSpikeLoggerTickLimitNanos;
    // Griffin start
    public static int brewingMultiplier;
    public static int smeltingMultiplier;
    public static boolean instantRespawn;
    // Guardian start
    public static boolean guardianEnabled;
    public static boolean guardianTesting;
    public static boolean disableTracking;
    public static boolean disableBlockTicking;
    public static boolean disableVillageTicking;
    public static boolean disableWeatherTicking;
    public static boolean disableSleepCheck;
    public static boolean disableEntityCollisions;
    public static boolean cacheChunkMaps;
    public static boolean disableSaving;
    public static boolean playerListPackets;
    public static boolean updatePingOnTablist;
    public static boolean onlyCustomTab;
    public static boolean reduceArmorDamage;
    public static boolean pearlThroughGatesAndTripwire = false;
    static int version;
    static Map<String, Command> commands;
    // joeleoli start
    private static JsonConfig knockbackConfig;
    /*========================================================================*/
    private static Metrics metrics;

    public static KnockbackProfile getKbProfileByName(String name) {
        for (KnockbackProfile profile : kbProfiles) {
            if (profile.getName().equalsIgnoreCase(name)) {
                return profile;
            }
        }

        return null;
    }

    public static void loadKnockbackProfiles() {
        knockbackConfig = new JsonConfig(new File("config/server", "knockback.json")).load();

        Map<String, Object> profilesMap = (Map<String, Object>) knockbackConfig.get("profiles");

        for (String profileName : profilesMap.keySet()) {
            KnockbackProfile profile = getKbProfileByName(profileName);

            if (profile == null) {
                profile = new CraftKnockbackProfile(profileName);
            }

            profile.setFriction(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".friction")));
            profile.setHorizontal(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".horizontal")));
            profile.setVertical(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".vertical")));
            profile.setVerticalLimit(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".verticalLimit")));
            profile.setExtraHorizontal(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".extraHorizontal")));
            profile.setExtraVertical(Double.valueOf(knockbackConfig.getString("profiles." + profileName + ".extraVertical")));

            kbProfiles.add(profile);
        }

        if (kbProfiles.isEmpty()) {
            kbProfiles.add(new CraftKnockbackProfile("Default"));
        }

        globalKbProfile = getKbProfileByName(knockbackConfig.getString("global-profile", "Default"));

        if (globalKbProfile == null) {
            globalKbProfile = kbProfiles.get(0);
        }
    }

    public static void saveKnockbackProfiles() {
        knockbackConfig.clear();

        for (KnockbackProfile profile : kbProfiles) {
            knockbackConfig.set("profiles." + profile.getName() + ".friction", profile.getFriction());
            knockbackConfig.set("profiles." + profile.getName() + ".horizontal", profile.getHorizontal());
            knockbackConfig.set("profiles." + profile.getName() + ".vertical", profile.getVertical());
            knockbackConfig.set("profiles." + profile.getName() + ".verticalLimit", profile.getVerticalLimit());
            knockbackConfig.set("profiles." + profile.getName() + ".extraHorizontal", profile.getExtraHorizontal());
            knockbackConfig.set("profiles." + profile.getName() + ".extraVertical", profile.getExtraVertical());
        }

        knockbackConfig.save();
    }

    public static void sendKnockbackInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 35));

        for (KnockbackProfile profile : kbProfiles) {
            boolean current = globalKbProfile.getName().equals(profile.getName());

            sender.sendMessage((current ? ChatColor.GREEN.toString() : ChatColor.RED.toString()) + ChatColor.BOLD + profile.getName());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Friction: " + ChatColor.RED + profile.getFriction());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Horizontal: " + ChatColor.RED + profile.getHorizontal());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Vertical: " + ChatColor.RED + profile.getVertical());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Vertical Limit: " + ChatColor.RED + profile.getVerticalLimit());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Extra Horizontal: " + ChatColor.RED + profile.getExtraHorizontal());
            sender.sendMessage(ChatColor.GOLD + "-> " + ChatColor.YELLOW + "Extra Vertical: " + ChatColor.RED + profile.getExtraVertical());
        }

        sender.sendMessage(ChatColor.BLUE + ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 35));
    }

    public static void init() {
        config = new YamlConfiguration();
        try {
            config.load(CONFIG_FILE);
        } catch (IOException ex) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load spigot.yml, please correct your syntax errors", ex);
            throw Throwables.propagate(ex);
        }

        config.options().header(HEADER);
        config.options().copyDefaults(true);

        commands = new HashMap<String, Command>();

        version = getInt("config-version", 8);
        set("config-version", 8);
        readConfig(SpigotConfig.class, null);

        loadKnockbackProfiles();
        commands.put("beefwellington", new KnockbackCommand());
    }

    public static void registerCommands() {
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            MinecraftServer.getServer().server.getCommandMap().register(entry.getKey(), "Spigot", entry.getValue());
        }

        if (metrics == null) {
            try {
                metrics = new Metrics();
                metrics.start();
            } catch (IOException ex) {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not start metrics service", ex);
            }
        }
    }

    static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterTypes().length == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (InvocationTargetException ex) {
                        throw Throwables.propagate(ex.getCause());
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method, ex);
                    }
                }
            }
        }

        try {
            config.save(CONFIG_FILE);
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save " + CONFIG_FILE, ex);
        }
    }

    private static void set(String path, Object val) {
        config.set(path, val);
    }

    private static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, config.getBoolean(path));
    }

    private static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, config.getInt(path));
    }

    private static <T> List getList(String path, T def) {
        config.addDefault(path, def);
        return (List<T>) config.getList(path, config.getList(path));
    }

    private static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, config.getString(path));
    }

    private static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, config.getDouble(path));
    }

    private static void logCommands() {
        logCommands = getBoolean("commands.log", true);
    }

    private static void tabComplete() {
        if (version < 6) {
            boolean oldValue = getBoolean("commands.tab-complete", true);
            if (oldValue) {
                set("commands.tab-complete", 0);
            } else {
                set("commands.tab-complete", -1);
            }
        }
        tabComplete = getInt("commands.tab-complete", 0);
    }

    private static String transform(String s) {
        return ChatColor.translateAlternateColorCodes('&', s).replaceAll("\\n", "\n");
    }

    private static void messages() {
        if (version < 8) {
            set("messages.outdated-client", outdatedClientMessage);
            set("messages.outdated-server", outdatedServerMessage);
        }

        whitelistMessage = transform(getString("messages.whitelist", "You are not whitelisted on this server!"));
        unknownCommandMessage = transform(getString("messages.unknown-command", "Unknown command. Type \"/help\" for help."));
        serverFullMessage = transform(getString("messages.server-full", "The server is full!"));
        outdatedClientMessage = transform(getString("messages.outdated-client", outdatedClientMessage));
        outdatedServerMessage = transform(getString("messages.outdated-server", outdatedServerMessage));
    }

    private static void watchdog() {
        timeoutTime = getInt("settings.timeout-time", timeoutTime);
        restartOnCrash = getBoolean("settings.restart-on-crash", restartOnCrash);
        restartScript = getString("settings.restart-script", restartScript);
        restartMessage = transform(getString("messages.restart", "Server is restarting"));
        commands.put("restart", new RestartCommand("restart"));
        WatchdogThread.doStart(timeoutTime, restartOnCrash);
    }

    private static void bungee() {
        if (version < 4) {
            set("settings.bungeecord", false);
            System.out.println("Oudated config, disabling BungeeCord support!");
        }
        bungee = getBoolean("settings.bungeecord", false);
    }

    private static void nettyThreads() {
        int count = getInt("settings.netty-threads", 4);
        System.setProperty("io.netty.eventLoopThreads", Integer.toString(count));
        Bukkit.getLogger().log(Level.INFO, "Using {0} threads for Netty based IO", count);
    }

    private static void lateBind() {
        lateBind = getBoolean("settings.late-bind", false);
    }

    private static void stats() {
        if (!config.contains("stats.forced-stats")) {
            config.createSection("stats.forced-stats");
        }

        ConfigurationSection section = config.getConfigurationSection("stats.forced-stats");
        for (String name : section.getKeys(true)) {
            if (section.isInt(name)) {
                forcedStats.put(name, section.getInt(name));
            }
        }

        if (disableStatSaving && section.getInt("achievement.openInventory", 0) < 1) {
            forcedStats.put("achievement.openInventory", 1);
        }
    }

    private static void tpsCommand() {
        commands.put("tps", new TicksPerSecondCommand("tps"));
    }

    private static void playerSample() {
        playerSample = getInt("settings.sample-count", 12);
        System.out.println("Server Ping Player Sample Count: " + playerSample);
    }

    private static void playerShuffle() {
        playerShuffle = getInt("settings.player-shuffle", 0);
    }

    private static void spamExclusions() {
        spamExclusions = getList("commands.spam-exclusions", Arrays.asList(new String[]
                {
                        "/skill"
                }));
    }

    private static void silentCommandBlocks() {
        silentCommandBlocks = getBoolean("commands.silent-commandblock-console", false);
    }

    private static void filterCreativeItems() {
        filterCreativeItems = getBoolean("settings.filter-creative-items", true);
    }

    private static void replaceCommands() {
        if (config.contains("replace-commands")) {
            set("commands.replace-commands", config.getStringList("replace-commands"));
            config.set("replace-commands", null);
        }
        replaceCommands = new HashSet<String>((List<String>) getList("commands.replace-commands",
                Arrays.asList("setblock", "summon", "testforblock", "tellraw")));
    }

    private static void userCacheCap() {
        userCacheCap = getInt("settings.user-cache-size", 1000);
    }
    // Poweruser end

    private static void saveUserCacheOnStopOnly() {
        saveUserCacheOnStopOnly = getBoolean("settings.save-user-cache-on-stop-only", false);
    }

    private static void intCacheLimit() {
        intCacheLimit = getInt("settings.int-cache-limit", 1024);
    }

    private static void movedWronglyThreshold() {
        movedWronglyThreshold = getDouble("settings.moved-wrongly-threshold", 0.0625D);
    }

    private static void movedTooQuicklyThreshold() {
        movedTooQuicklyThreshold = getDouble("settings.moved-too-quickly-threshold", 100.0D);
    }

    private static void attributeMaxes() {
        maxHealth = getDouble("settings.attribute.maxHealth.max", maxHealth);
        ((AttributeRanged) GenericAttributes.maxHealth).b = maxHealth;
        movementSpeed = getDouble("settings.attribute.movementSpeed.max", movementSpeed);
        ((AttributeRanged) GenericAttributes.d).b = movementSpeed;
        attackDamage = getDouble("settings.attribute.attackDamage.max", attackDamage);
        ((AttributeRanged) GenericAttributes.e).b = attackDamage;
    }

    private static void globalAPICache() {
        if (getBoolean("settings.global-api-cache", false) && !CachedStreamHandlerFactory.isSet) {
            Bukkit.getLogger().info("Global API cache enabled - All requests to Mojang's API will be " +
                    "handled by Spigot");
            CachedStreamHandlerFactory.isSet = true;
            URL.setURLStreamHandlerFactory(new CachedStreamHandlerFactory());
        }
    }
    // Griffin end

    private static void debug() {
        debug = getBoolean("settings.debug", false);

        if (debug && !LogManager.getRootLogger().isTraceEnabled()) {
            // Enable debug logging
            LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            Configuration conf = ctx.getConfiguration();
            conf.getLoggerConfig(LogManager.ROOT_LOGGER_NAME).setLevel(org.apache.logging.log4j.Level.ALL);
            ctx.updateLoggers(conf);
        }

        if (LogManager.getRootLogger().isTraceEnabled()) {
            Bukkit.getLogger().info("Debug logging is enabled");
        } else {
            Bukkit.getLogger().info("Debug logging is disabled");
        }
    }

    private static void playerFiles() {
        disablePlayerFileSaving = getBoolean("settings.disable-player-file-saving", false);
        if (disablePlayerFileSaving) {
            disableStatSaving = true;
        }
    }

    private static void logRemainingAsyncThreadsDuringShutdown() {
        logRemainingAsyncThreadsDuringShutdown = getBoolean("settings.logRemainingAsyncThreadsDuringShutdown", true);
    }

    private static void powertpsCommand() {
        commands.put("tps2", new net.frozenorb.command.TPSCommand("tps2"));
    }
    // Guardian end

    private static void worldstatsCommand() {
        commands.put("worldstats", new net.frozenorb.command.WorldStatsCommand("worldstats"));
    }

    private static void setviewdistanceCommand() {
        commands.put("setviewdistance", new net.frozenorb.command.SetViewDistanceCommand("setviewdistance"));
    }

    private static void playersPerChunkIOThread() {
        playersPerChunkIOThread = Math.max(1, getInt("settings.chunkio.players-per-thread", 150));
    }

    private static void autoSaveChunksPerTick() {
        autoSaveChunksPerTick = getInt("settings.autosave.chunks-per-tick", 200);
    }

    private static void autoSaveFireWorldSaveEvent() {
        autoSaveFireWorldSaveEvent = getBoolean("settings.autosave.fire-WorldSaveEvent", false);
    }

    private static void autoSaveClearRegionFileCache() {
        autoSaveClearRegionFileCache = getBoolean("settings.autosave.clear-RegionFileCache", false);
    }

    private static void lagSpikeLoggerEnabled() {
        lagSpikeLoggerEnabled = getBoolean("settings.lagSpikeLogger.enabled", true);
    }

    private static void lagSpikeLoggerTickLimitNanos() {
        lagSpikeLoggerTickLimitNanos = ((long) getInt("settings.lagSpikeLogger.tickLimitInMilliseconds", 100)) * 1000000L;
    }

    private static void brewingMultiplier() {
        brewingMultiplier = getInt("settings.brewingMultiplier", 1);
    }

    private static void smeltingMultiplier() {
        smeltingMultiplier = getInt("settings.smeltingMultiplier", 1);
    }

    private static void instantRespawn() {
        instantRespawn = getBoolean("settings.instantRespawn", false);
    }

    private static void guardianEnabled() {
        guardianEnabled = getBoolean("settings.guardian.enabled", true);
    }

    private static void guardianTesting() {
        guardianTesting = getBoolean("settings.guardian.testing", false);
    }

    // MineHQ start
    private static void noTrackCommand() {
        commands.put("notrack", new net.frozenorb.command.NoTrackCommand("notrack"));
    }

    private static void disableTracking() {
        disableTracking = getBoolean("settings.disable.entityTracking", false);
    }

    private static void disableBlockTicking() {
        disableBlockTicking = getBoolean("settings.disable.ticking.blocks", false);
    }

    private static void disableVillageTicking() {
        disableVillageTicking = getBoolean("settings.disable.ticking.villages", false);
    }

    private static void disableWeatherTicking() {
        disableWeatherTicking = getBoolean("settings.disable.ticking.weather", false);
    }

    private static void disableSleepCheck() {
        disableSleepCheck = getBoolean("settings.disable.general.sleepcheck", false);
    }

    private static void disableEntityCollisions() {
        disableEntityCollisions = getBoolean("settings.disable.general.entity-collisions", false);
    }

    private static void cacheChunkMaps() {
        cacheChunkMaps = getBoolean("settings.cache-chunk-maps", false);
    }
    // MineHQ end

    private static void disableSaving() {
        disableSaving = getBoolean("settings.disableSaving", false);
    }

    private static void packets() {
        onlyCustomTab = getBoolean("settings.only-custom-tab", false);

        if (!onlyCustomTab) {
            playerListPackets = !getBoolean("settings.disable.player-list-packets", false);
            updatePingOnTablist = getBoolean("settings.disable.ping-update-packets", false);
        }
    }

    private static void reduceArmorDamage() {
        reduceArmorDamage = getBoolean("settings.reduce-armor-damage", false);
    }

    private static void pearls() {
        pearlThroughGatesAndTripwire = getBoolean("settings.pearl-through-gates-and-tripwire", false);
    }

}
