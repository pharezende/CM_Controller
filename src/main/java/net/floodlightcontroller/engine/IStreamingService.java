package net.floodlightcontroller.engine;

import java.io.IOException;

import net.floodlightcontroller.core.module.IFloodlightService;

public interface IStreamingService extends IFloodlightService{

	public void jsonToJavaDescriptor(String json) throws IOException;	

}
