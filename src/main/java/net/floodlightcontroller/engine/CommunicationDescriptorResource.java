package net.floodlightcontroller.engine;


import java.io.IOException;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class CommunicationDescriptorResource extends CommunicationDescriptorResourceBase{
	
    @Post("json")
    public Object handleRequest(String json) {
        IStreamingService streaming = this.getStreamingService();
        try {
			streaming.jsonToJavaDescriptor(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "Did not work!";
		}
        
		return "Worked!";
    }
    
    @Get("json")
    public Object handleRequest() {
    	return "OK";
    }
    
}
