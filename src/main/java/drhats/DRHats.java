package drhats;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import drhats.commands.HatsCommands;
import drhats.common.files.FileConfigurationsLoader;
import drhats.common.files.FileConfigurationsUtils;
import drhats.common.plugin.MessagesPlugin;
import drhats.management.HatsManager;
import drhats.utils.log.PluginLogger;
import drhats.utils.messages.MessagesSender;
import drhats.utils.messages.MessagesStorage;
import drhats.utils.reloader.ReloaderManager;

public class DRHats extends JavaPlugin implements MessagesPlugin {
	
	public static final String ID = "drhats";
	
	private PluginLogger logger;
	private FileConfigurationsUtils fileConfigurationsUtils;
	private ReloaderManager reloaderManager;
	private MessagesSender messagesSender;

	@Override
	public void onEnable() {
		super.onEnable();

		logger = new PluginLogger(this);
		
		fileConfigurationsUtils = new FileConfigurationsUtils(new FileConfigurationsLoader(this));
		
		reloaderManager = new ReloaderManager(this);
		
		MessagesStorage messagesStorage = new MessagesStorage(this);
		messagesSender = new MessagesSender(this, messagesStorage);
		
		reloaderManager.registerReloadable(logger);
		reloaderManager.registerReloadable(HatsManager.getInstance(this, "hats.yml"));
		reloaderManager.registerReloadable(messagesStorage);
		
		reloaderManager.reloadAllSet();
		
		Bukkit.getPluginCommand("drhats").setExecutor(new HatsCommands(this));
		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
	}
	
	public FileConfigurationsUtils getFileConfigurationsUtils() {
		return fileConfigurationsUtils;
	}
	
	public ReloaderManager getReloaderManager() {
		return reloaderManager;
	}
	
	public MessagesSender getMessagesSender() {
		return messagesSender;
	}

	@Override
	public PluginLogger getPluginLogger() {
		return logger;
	}

	@Override
	public String getID() {
		return ID;
	}
	
}
