package drhats.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import drguis.guis.types.decorators.data.list.permissions.PermissionsGUIsListDecorator;
import drhats.common.plugin.MessagesPlugin;
import drhats.gui.HatsGUI;
import drhats.utils.reloader.ReloadFileData;
import drhats.utils.reloader.Reloadable;

public class HatsManager implements Reloadable {

	private MessagesPlugin plugin;

	private Map<String, ItemStack> hats;

	private String filename;

	private HatsGUI gui;

	private static HatsManager instance;

	private HatsManager(MessagesPlugin plugin) {
		this.plugin = plugin;
		this.hats = new HashMap<>();
		this.filename = "hats.yml";
		this.gui = new HatsGUI(plugin.getMessagesSender());
	}

	public static HatsManager getInstance() {
		return instance;
	}

	public static HatsManager getInstance(MessagesPlugin plugin) {
		if (instance == null) {
			instance = new HatsManager(plugin);
		}
		return instance;
	}

	public void showGUIToPlayer(Player player) {
		Inventory inventory = new PermissionsGUIsListDecorator<>(gui, "You don't have permission to wear this hat!")
				.getInventory(player);
		player.openInventory(inventory);
	}

	public ItemStack createHat(String hatID, ItemStack itemStack) {
		return hats.put(hatID, itemStack);
	}

	public ItemStack removeHat(String hatID) {
		return hats.remove(hatID);
	}

	@Override
	public void reload() {
		hats = plugin.getFileConfigurationsUtils().parseAllKeys(filename,
				(String key) -> plugin.getFileConfigurationsUtils().getItemStack(filename, key));
		if (hats != null) {
			gui.reload(hats);
		}
	}

	@Override
	public Collection<ReloadFileData> getReloadFilenames() {
		Set<ReloadFileData> filenames = new HashSet<>();
		filenames.add(new ReloadFileData(filename, true));
		return filenames;
	}

}
