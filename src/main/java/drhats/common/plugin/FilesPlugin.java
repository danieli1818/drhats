package drhats.common.plugin;

import drhats.common.files.FileConfigurationsUtils;
import drhats.utils.reloader.ReloaderManager;

public interface FilesPlugin extends LoggerPlugin {

	public FileConfigurationsUtils getFileConfigurationsUtils();
	
	public ReloaderManager getReloaderManager();
	
}
