package net.floodlightcontroller.engine;

import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class EngineWebRoutable implements RestletRoutable{

	@Override
	public Restlet getRestlet(Context context) {
		// TODO Auto-generated method stub
        Router router = new Router(context);
        router.attach("/communication-descriptor/", CommunicationDescriptorResource.class);
        return router;
	}

	@Override
	public String basePath() {
		// TODO Auto-generated method stub
		return "/engine";
	}

}
