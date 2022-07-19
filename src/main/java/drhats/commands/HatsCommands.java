package drhats.commands;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drhats.commands.subcommands.CreateHatSubCommand;
import drhats.common.commands.ConsumerSubCommand;
import drhats.common.commands.RootCommand;
import drhats.common.plugin.MessagesPlugin;
import drhats.management.HatsManager;

public class HatsCommands extends RootCommand implements CommandExecutor {
	
	private static final String DESCRIPTION = "Shows the hats menu.";
	private static final String PERMISSION = "drhats.menu";
	private static final String INVALID_HATS_COMMAND_MESSAGE_ID = "invalid_hats_command_message";
	private static final String NO_PERMISSION_MESSAGE_ID = "no_permission_message";
	private static final String PLAYER_COMMAND_MESSAGE_ID = "player_command_message";
	private static final String SUCCESSFULLY_RELOADED_CONFIGURATIONS_MESSAGE_ID = "successfully_reloaded_configurations_message";
	private static final String RELOAD_CONFIGURATIONS_LOG_MESSAGE_ID = "reload_configurations_log_message";

	public HatsCommands(MessagesPlugin plugin) {
		super(plugin, DESCRIPTION, PERMISSION, INVALID_HATS_COMMAND_MESSAGE_ID, NO_PERMISSION_MESSAGE_ID, PLAYER_COMMAND_MESSAGE_ID);
		addSubCommand("reload", new ConsumerSubCommand(getPlugin(), (CommandSender sender) -> {
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(PLAYER_COMMAND_MESSAGE_ID, sender);
				return;
			}
			getPlugin().getReloaderManager().reloadAllSet();
			getPlugin().getPluginLogger().logTranslated(Level.INFO, RELOAD_CONFIGURATIONS_LOG_MESSAGE_ID);
			getPlugin().getMessagesSender().sendTranslatedMessage(SUCCESSFULLY_RELOADED_CONFIGURATIONS_MESSAGE_ID, sender);
		}, this, "Reloads the plugin's configurations", "drhats.reload"));
		addSubCommand("create", new CreateHatSubCommand(plugin, this, "Creates a new hat", "drhats.create"));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args) && args.length == 0) {
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
				return false;
			}
			HatsManager.getInstance().showGUIToPlayer((Player)sender);
		}
		return true;
	}
	
}
