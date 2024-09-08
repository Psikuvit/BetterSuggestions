package me.psikuvit.betterSuggestions.commands;

import me.psikuvit.betterSuggestions.BSPlugin;
import me.psikuvit.betterSuggestions.managers.SuggestionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuggestCommand implements CommandExecutor {

    private final SuggestionManager suggestionManager;

    public SuggestCommand(BSPlugin plugin) {
        this.suggestionManager = plugin.getSuggestionManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (args.length == 1) {
            String suggestion = args[0];
            suggestionManager.addNewSuggestion(suggestion, player.getUniqueId());
        }
        return false;
    }
}
