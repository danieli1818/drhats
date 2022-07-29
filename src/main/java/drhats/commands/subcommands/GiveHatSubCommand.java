package drhats.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import drhats.common.commands.AdvancedCommand;
import drhats.common.commands.SubCommand;
import drhats.common.plugin.MessagesPlugin;
import drhats.management.HatsManager;
import drhats.messages.MessagesIDs;

public class GiveHatSubCommand extends SubCommand {

	public GiveHatSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String description,
			String permission) {
		super(plugin, fatherCommand, description, permission);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (super.onCommand(sender, command, label, args) || args.length != 2) {
			return false;
		}
		String playerName = args[0];
		Player player = Bukkit.getPlayer(playerName);
		if (player == null) {
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.PLAYER_NOT_FOUND_MESSAGE_ID, sender);
			return false;
		}
		String hatID = args[1];
		HatsManager hatsManager = HatsManager.getInstance();
		if (!hatsManager.hatExists(hatID)) {
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.HAT_NOT_FOUND_MESSAGE_ID, sender);
			return false;
		}
		if (hatsManager.hasHat(player, hatID)) {
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.PLAYER_ALREADY_HAS_HAT_MESSAGE_ID, sender);
			return false;
		}
		hatsManager.giveHat(player, hatID);
		getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.SUCCESS_GIVING_HAT_TO_PLAYER_MESSAGE_ID, sender);
		return true;
	}

}
