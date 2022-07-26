package drhats.gui;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import drguis.guis.common.MatrixSlotData;
import drguis.guis.icons.types.PermissionIcon;
import drguis.guis.types.list.general.ArrayGUIsList;
import drhats.management.HatsManager;

public class HatsGUI extends ArrayGUIsList<PermissionIcon> {

	public HatsGUI() {
		super(36, "Hats", new MatrixSlotData(3, 0, new ItemStack(Material.ARROW)),
				new MatrixSlotData(3, 8, new ItemStack(Material.ARROW)));
	}

	public void load(Map<String, ItemStack> hats) {
		for (Map.Entry<String, ItemStack> hatEntry : hats.entrySet()) {
			addHat(hatEntry.getKey(), hatEntry.getValue());
		}
	}
	
	private void addHat(String hatID, ItemStack hat) {
		addIcon((PermissionIcon) new PermissionIcon(hat, "drhats.hat." + hatID)
				.addClickAction((Player player) -> HatsManager.getInstance().setHatForPlayer(player, hatID)));
	}

	public void clear() {
		removeAllIcons();
	}

	public void reload(Map<String, ItemStack> hats) {
		clear();
		load(hats);
	}

}
