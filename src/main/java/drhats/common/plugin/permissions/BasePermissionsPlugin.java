package drhats.common.plugin.permissions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import drhats.common.plugin.exceptions.PluginMissingException;
import drhats.common.plugin.permissions.functions.LuckPermsFunctions;
import drhats.common.plugin.permissions.functions.PermissionsFunctions;

public abstract class BasePermissionsPlugin extends JavaPlugin implements PermissionsPlugin {

	private PermissionsFunctions permissionsFunctions;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		if (getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
			permissionsFunctions = new LuckPermsFunctions();
		} else {
			throw new PluginMissingException("Permissions");
		}
		
	}

	@Override
	public boolean hasPermission(Player player, String permission) {
		return permissionsFunctions.hasPermission(player, permission);
	}

	@Override
	public boolean givePermission(Player player, String permission) {
		return permissionsFunctions.givePermission(player, permission);
	}
	
}
