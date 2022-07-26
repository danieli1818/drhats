package drhats.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drhats.common.commands.AdvancedCommand;
import drhats.common.commands.SubCommand;
import drhats.common.plugin.MessagesPlugin;
import drhats.management.HatsManager;
import drhats.messages.MessagesIDs;

public class DeleteHatSubCommand extends SubCommand {

	public DeleteHatSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String description,
			String permission) {
		super(plugin, fatherCommand, description, permission);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (super.onCommand(sender, command, label, args)) {
			return true;
		}
		switch (args.length) {
		case 0:
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
			}
			Player player = (Player)sender;
			ItemStack itemStack = player.getInventory().getItemInMainHand();
			if (itemStack == null) {
				getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.HOLDING_HAT_MESSAGE_ID, sender);
				return false;
			}
			if (HatsManager.getInstance().removeHat(itemStack) == null) {
				getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.HAT_NOT_FOUND_MESSAGE_ID, sender);
				return false;
			}
			HatsManager.getInstance().saveHats();
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.SUCCESS_DELETING_HAT_MESSAGE_ID, sender);
			break;
		case 1:
			String hatID = args[0];
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
			}
			player = (Player)sender;
			HatsManager.getInstance().removeHat(hatID);
			HatsManager.getInstance().saveHats();
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.SUCCESS_DELETING_HAT_MESSAGE_ID, sender);
			break;
		default:
			getPlugin().getMessagesSender().sendTranslatedMessage(getInvalidCommandMessageID(), sender);
			return false;
		}
		return true;
	}

}
