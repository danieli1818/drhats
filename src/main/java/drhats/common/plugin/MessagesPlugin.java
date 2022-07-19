package drhats.common.plugin;

import drhats.utils.messages.MessagesSender;

public interface MessagesPlugin extends FilesPlugin {

	public MessagesSender getMessagesSender();
	
}
