package drhats.common.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class FileConfigurationsLoader {

	private Plugin plugin;
	private BiMap<String, FileConfiguration> fileConfigurations;
	
	public FileConfigurationsLoader(Plugin plugin) {
		this.plugin = plugin;
		fileConfigurations = HashBiMap.create();
	}
	
	public boolean createPluginFolder() {
		if (!plugin.getDataFolder().exists()) {
			return plugin.getDataFolder().mkdirs();
		}
		return plugin.getDataFolder().isDirectory();
	}
	
	public FileConfiguration getFileConfiguration(String filePath) {
		return getFileConfiguration(filePath, true);
	}
	
	public FileConfiguration getFileConfiguration(String filePath, boolean shouldLoadFileConfigurationIfNotLoaded) {
		if (!fileConfigurations.containsKey(filePath)) {
			if (shouldLoadFileConfigurationIfNotLoaded) {
				loadFileConfiguration(filePath);
			}
		}
		return fileConfigurations.get(filePath);
	}
	
	public FileConfiguration getFileConfiguration(String filePath, boolean createPath, boolean loadResource, boolean shouldReplaceFile) {
		if (!fileConfigurations.containsKey(filePath)) {
			loadFileConfiguration(filePath, createPath, loadResource, shouldReplaceFile);
		}
		return fileConfigurations.get(filePath);
	}
	
	public boolean loadFileConfiguration(String filePath) {
		return loadFileConfiguration(filePath, true);
	}
	
	public boolean loadFileConfiguration(String filePath, boolean createPath) {
		return loadFileConfiguration(filePath, createPath, false, false);
	}
	
	public boolean loadFileConfiguration(String filePath, boolean createPath, boolean loadResource, boolean shouldReplaceFile) {
		File file = new File(getAbsolutePath(filePath));
		if (!file.exists() && createPath) {
			System.out.println("Making dirs in load file configuration!");
			if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
				System.out.println("Error Making Dirs!");
				// TODO log error message couldn't create file path
				return false;
			}
			if (loadResource) {
				try {
					plugin.saveResource(filePath, shouldReplaceFile);
				} catch (IllegalArgumentException e) {
					// TODO log error message couldn't load resource file with exception
					return false;
				}
			}
		}
		if (!file.exists() || !file.isFile()) {
			// TODO log error message file doesn't exist
			return false;
		}
		FileConfiguration fileConfiguration = new YamlConfiguration();
		try {
			fileConfiguration.load(file);
		} catch (Exception e) {
			// TODO log error message couldn't load file configuration with the exception
			return false;
		}
		fileConfigurations.put(filePath, fileConfiguration);
		return true;
	}
	
	public boolean saveFileConfiguration(String filePath) {
		FileConfiguration config = fileConfigurations.get(filePath);
		if (config == null) {
			return false;
		}
		try {
			config.save(new File(getAbsolutePath(filePath)));
		} catch (IOException e) {
			// TODO log message couldn't save file configuration with the exception
			return false;
		}
		return true;
	}
	
	private String getAbsolutePath(String relativePath) {
		return plugin.getDataFolder().getAbsolutePath() + File.separator + relativePath;
	}
	
}
