package me.psikuvit.betterSuggestions.commands;

import me.psikuvit.betterSuggestions.BSPlugin;
import me.psikuvit.betterSuggestions.Suggestion;
import me.psikuvit.betterSuggestions.managers.SuggestionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ViewCommand implements CommandExecutor {

    private final SuggestionManager suggestionManager;

    public ViewCommand(BSPlugin plugin) {
        this.suggestionManager = plugin.getSuggestionManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (args.length == 1) {
            int id = 0;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("id must be an integer");
            }

            if (id == 0 || !suggestionManager.isRightID(id)) {
                player.sendMessage("Invalid id");
                return false;
            }
            Suggestion suggestion = suggestionManager.getSuggestionByID(id);

            player.sendMessage(suggestion.toString());
        }
        return false;
    }
}
