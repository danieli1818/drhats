package drhats.common.plugin;

import java.io.File;

import drhats.utils.log.PluginLogger;

public interface LoggerPlugin extends AdvancedPlugin {

	public PluginLogger getPluginLogger();
	
	public File getDataFolder();
	
	public void saveResource(String resourcePath, boolean replace);
	
}
