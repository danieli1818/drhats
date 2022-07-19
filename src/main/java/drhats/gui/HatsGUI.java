package drhats.gui;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drcustomitems.items.CustomItemsBuilder;
import drcustomitems.items.actions.Action;
import drcustomitems.items.actions.PluginCustomItemsActionsManager;
import drcustomitems.items.actions.holders.inventory.InventoryActionsManager;
import drguis.guis.common.MatrixSlotData;
import drguis.guis.icons.types.PermissionIcon;
import drguis.guis.types.list.general.ArrayGUIsList;
import drhats.DRHats;
import drhats.utils.messages.MessagesSender;

public class HatsGUI extends ArrayGUIsList<PermissionIcon> {

	private MessagesSender messagesSender;
	
	public HatsGUI(MessagesSender messagesSender) {
		super(36, "Hats", new MatrixSlotData(3, 0, new ItemStack(Material.ARROW)),
				new MatrixSlotData(3, 8, new ItemStack(Material.ARROW)));
		System.out.println("Adding Icon!");
		addIcon((PermissionIcon) new PermissionIcon(new ItemStack(Material.DIAMOND), "drhats.hat.diamond_hat")
				.addClickAction((Player player) -> givePlayerHat(player, new ItemStack(Material.DIAMOND))));
		System.out.println("Added Icon!");
		this.messagesSender = messagesSender;
		PluginCustomItemsActionsManager.getInstance().setActionsHolderToPluginItems(DRHats.ID, new InventoryActionsManager().addInventoryClickAction(new Action() {
			
			@Override
			public void runAction(Player player, ItemStack itemStack) {
				itemStack.setAmount(0);
			}
		}));
	}

	public void load(Map<String, ItemStack> hats) {
		for (Map.Entry<String, ItemStack> hatEntry : hats.entrySet()) {
			addIcon((PermissionIcon) new PermissionIcon(hatEntry.getValue(), "drhats.hat." + hatEntry.getKey())
					.addClickAction((Player player) -> givePlayerHat(player, hatEntry.getValue())));
		}
	}

	public void clear() {
		System.out.println("Removing all icons!");
		removeAllIcons();
	}

	public void reload(Map<String, ItemStack> hats) {
		clear();
		load(hats);
		System.out.println("Adding Icon!");
		addIcon((PermissionIcon) new PermissionIcon(new ItemStack(Material.DIAMOND), "drhats.hat.diamond_hat")
				.addClickAction((Player player) -> givePlayerHat(player, new ItemStack(Material.DIAMOND))));
		System.out.println("Added Icon!");
	}

	private boolean givePlayerHat(Player player, ItemStack itemStack) {
		if (player.getInventory().getHelmet() == null) {
			player.getInventory().setHelmet(new CustomItemsBuilder(itemStack).setUndroppable(true).setUnmoveable(true).createWithoutSaving(DRHats.ID));
			return true;
		}
		messagesSender.sendTranslatedMessage("wearing_helmet_message", player);
		return false;
	}

}
