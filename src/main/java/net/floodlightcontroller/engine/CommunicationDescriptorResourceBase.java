package net.floodlightcontroller.engine;

import org.restlet.resource.ServerResource;

public class CommunicationDescriptorResourceBase extends ServerResource{
	
    IStreamingService getStreamingService() {
	return (IStreamingService)getContext().getAttributes().
	        get(IStreamingService.class.getCanonicalName());
    }

}
