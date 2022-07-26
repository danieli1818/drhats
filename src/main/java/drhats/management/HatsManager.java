package drhats.management;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import drcustomitems.items.CustomItemsBuilder;
import drcustomitems.items.CustomItemsProperties;
import drcustomitems.items.actions.Action;
import drcustomitems.items.actions.PluginCustomItemsActionsManager;
import drcustomitems.items.actions.holders.inventory.InventoryActionsManager;
import drguis.guis.types.decorators.data.list.permissions.PermissionsGUIsListDecorator;
import drhats.DRHats;
import drhats.common.plugin.MessagesPlugin;
import drhats.gui.HatsGUI;
import drhats.messages.MessagesIDs;
import drhats.utils.reloader.ReloadFileData;
import drhats.utils.reloader.Reloadable;

public class HatsManager implements Reloadable {

	private MessagesPlugin plugin;

	private BiMap<String, ItemStack> hats;

	private String hatsFilePath;

	private HatsGUI gui;

	private static HatsManager instance;

	private HatsManager(MessagesPlugin plugin, String hatsFilePath) {
		this.plugin = plugin;
		this.hats = HashBiMap.create();
		this.hatsFilePath = hatsFilePath;
		this.gui = new HatsGUI();
		PluginCustomItemsActionsManager.getInstance().setActionsHolderToPluginItems(DRHats.ID,
				new InventoryActionsManager().addInventoryClickAction(new Action() {

					@Override
					public void runAction(Player player, ItemStack itemStack) {
						itemStack.setAmount(0);
					}
				}));
	}

	public static HatsManager getInstance() {
		return instance;
	}

	public static HatsManager getInstance(MessagesPlugin plugin, String hatsFilePath) {
		if (instance == null) {
			instance = new HatsManager(plugin, hatsFilePath);
		}
		return instance;
	}

	public void showGUIToPlayer(Player player) {
		Inventory inventory = new PermissionsGUIsListDecorator<>(gui, "You don't have permission to wear this hat!")
				.getInventory(player);
		player.openInventory(inventory);
	}

	public ItemStack createHat(String hatID, ItemStack itemStack) {
		if (itemStack == null) {
			return removeHat(hatID);
		}
		ItemStack previousHat = hats.put(hatID, itemStack);
		plugin.getFileConfigurationsUtils().setObject(hatsFilePath, hatID, itemStack);
		reloadGUI();
		return previousHat;
	}

	public ItemStack removeHat(String hatID) {
		ItemStack previousHat = hats.remove(hatID);
		plugin.getFileConfigurationsUtils().removeObject(hatsFilePath, hatID);
		reloadGUI();
		return previousHat;
	}
	
	public ItemStack removeHat(ItemStack itemStack) {
		String hatID = hats.inverse().get(itemStack);
		if (hatID == null) {
			return null;
		}
		return removeHat(hatID);
	}
	
	public ItemStack getHat(String hatID) {
		return hats.get(hatID);
	}
	
	public void saveHats() {
		plugin.getFileConfigurationsUtils().save(hatsFilePath);
	}
	
	public boolean setHatForPlayer(Player player, String id) {
		ItemStack helmet = player.getInventory().getHelmet();
		if (helmet == null || (CustomItemsProperties.isDRCustomItem(helmet)
				&& DRHats.ID.equals(CustomItemsProperties.getDRCustomItemPlugin(helmet)))) {
			ItemStack hat = getHat(id);
			player.getInventory().setHelmet(new CustomItemsBuilder(hat).setUndroppable(true).setUnmoveable(true)
					.createWithoutSaving(DRHats.ID));
			return true;
		}
		getPlugin().getMessagesSender().sendTranslatedMessage(MessagesIDs.WEARING_HELMET_MESSAGE_ID, player);
		return false;
	}
	
	public MessagesPlugin getPlugin() {
		return plugin;
	}
	
	private void reloadGUI() {
		gui.reload(hats);
	}

	@Override
	public void reload() {
		hats = plugin.getFileConfigurationsUtils().parseAllKeysBi(hatsFilePath,
				(String key) -> plugin.getFileConfigurationsUtils().getItemStack(hatsFilePath, key));
		if (hats != null) {
			reloadGUI();
		}
	}

	@Override
	public Collection<ReloadFileData> getReloadFilenames() {
		Set<ReloadFileData> filenames = new HashSet<>();
		filenames.add(new ReloadFileData(hatsFilePath, true));
		return filenames;
	}

}
