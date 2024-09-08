package me.psikuvit.betterSuggestions;

import me.psikuvit.betterSuggestions.commands.SuggestCommand;
import me.psikuvit.betterSuggestions.commands.ViewCommand;
import me.psikuvit.betterSuggestions.managers.DatabaseManager;
import me.psikuvit.betterSuggestions.managers.SuggestionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class BSPlugin extends JavaPlugin {

    private DatabaseManager databaseManager;
    private SuggestionManager suggestionManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        databaseManager = new DatabaseManager();
        try {
            databaseManager.connect(this);
            Bukkit.getLogger().info("Database connected");

            suggestionManager = new SuggestionManager(databaseManager.getConnection());
            suggestionManager.loadSuggestions();
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Couldn't load data");
        }

        getCommand("suggest").setExecutor(new SuggestCommand(this));
        getCommand("view").setExecutor(new ViewCommand(this));
    }

    @Override
    public void onDisable() {
        try {
            if (suggestionManager != null) suggestionManager.saveSuggestions();
            if (databaseManager != null) databaseManager.disconnect();
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Couldn't save data");
        }
    }

    public SuggestionManager getSuggestionManager() {
        return suggestionManager;
    }
}
