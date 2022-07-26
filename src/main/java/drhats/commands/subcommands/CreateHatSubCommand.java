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

public class CreateHatSubCommand extends SubCommand {

	public CreateHatSubCommand(MessagesPlugin plugin, AdvancedCommand fatherCommand, String description,
			String permission) {
		super(plugin, fatherCommand, description, permission);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!super.onCommand(sender, command, label, args) && args.length == 1) {
			String hatID = args[0];
			if (!(sender instanceof Player)) {
				getPlugin().getMessagesSender().sendTranslatedMessage(getPlayerCommandMessageID(), sender);
			}
			Player player = (Player)sender;
			if (HatsManager.getInstance().getHat(hatID) != null) {
				getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.HAT_ID_ALREADY_EXISTS_MESSAGE_ID, sender);
				return false;
			}
			ItemStack itemStack = player.getInventory().getItemInMainHand();
			if (itemStack == null) {
				getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.HOLDING_HAT_MESSAGE_ID, sender);
				return false;
			}
			HatsManager.getInstance().createHat(hatID, itemStack);
			HatsManager.getInstance().saveHats();
			getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.SUCCESS_CREATING_HAT_MESSAGE_ID, sender);
		}
		return true;
	}

}
