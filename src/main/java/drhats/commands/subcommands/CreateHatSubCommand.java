package drhats.commands.subcommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drhats.common.commands.AdvancedCommand;
import drhats.common.commands.SubCommand;
import drhats.common.plugin.MessagesPlugin;

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
			ItemStack itemStack = player.getInventory().getItemInMainHand();
			if (itemStack == null) {
				// TODO Send error message that you need to hold an item when you run this command
				return false;
			}
			getPlugin().getFileConfigurationsUtils().setObject("hats.yml", hatID, itemStack);
			getPlugin().getFileConfigurationsUtils().save("hats.yml");
		}
		return true;
	}

}
