package drhats.common.commands;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drhats.common.plugin.MessagesPlugin;
import drhats.messages.MessagesIDs;

public class ReloadCommand extends SubCommand {

	public ReloadCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String description, String permission) {
		super(plugin, fatherCommand, description, permission);
	}
	
	public ReloadCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand) {
		this(plugin, fatherCommand, "Reloads the plugin's configurations", plugin.getID() + ".reload");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (super.onCommand(sender, command, label, args)) {
			return true;
		}
		if (!(sender instanceof Player)) {
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.PLAYER_COMMAND_MESSAGE_ID, sender);
			return false;
		}
		getPlugin().getReloaderManager().reloadAllSet();
		getPlugin().getPluginLogger().logTranslated(Level.INFO, MessagesIDs.RELOAD_CONFIGURATIONS_LOG_MESSAGE_ID);
		getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.SUCCESSFULLY_RELOADED_CONFIGURATIONS_MESSAGE_ID, sender);
		return true;
	}

	
	
}
