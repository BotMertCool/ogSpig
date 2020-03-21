package org.bukkit.plugin;

import com.avaje.ebean.EbeanServer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class TestPlugin extends PluginBase {
    final private String pluginName;
    private boolean enabled = true;

    public TestPlugin(String pluginName) {
        this.pluginName = pluginName;
    }

    public File getDataFolder() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public PluginDescriptionFile getDescription() {
        return new PluginDescriptionFile(pluginName, "1.0", "test.test");
    }

    public FileConfiguration getConfig() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public InputStream getResource(String filename) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void saveConfig() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void saveDefaultConfig() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void saveResource(String resourcePath, boolean replace) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void reloadConfig() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public PluginLogger getLogger() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public PluginLoader getPluginLoader() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public Server getServer() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void onDisable() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void onLoad() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void onEnable() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean isNaggable() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void setNaggable(boolean canNag) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public EbeanServer getDatabase() {
        throw new UnsupportedOperationException("Not supported.");
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        throw new UnsupportedOperationException("Not supported.");
    }
}
