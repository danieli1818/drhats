package drhats.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drhats.commands.subcommands.CreateHatSubCommand;
import drhats.commands.subcommands.DeleteHatSubCommand;
import drhats.common.commands.ReloadCommand;
import drhats.common.commands.RootCommand;
import drhats.common.plugin.MessagesPlugin;
import drhats.management.HatsManager;
import drhats.messages.MessagesIDs;

public class HatsCommands extends RootCommand implements CommandExecutor {
	
	private static final String DESCRIPTION = "Shows the hats menu.";
	private static final String PERMISSION = "drhats.menu";
	
	public HatsCommands(MessagesPlugin plugin) {
		super(plugin, DESCRIPTION, PERMISSION, MessagesIDs.INVALID_HATS_COMMAND_MESSAGE_ID, MessagesIDs.NO_PERMISSION_MESSAGE_ID, MessagesIDs.PLAYER_COMMAND_MESSAGE_ID);
		addSubCommand("reload", new ReloadCommand(plugin, this));
		addSubCommand("create", new CreateHatSubCommand(plugin, this, "Creates a new hat", "drhats.create"));
		addSubCommand("delete", new DeleteHatSubCommand(plugin, this, "Delete a hat", "drhats.delete"));
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
