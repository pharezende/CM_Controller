package net.floodlightcontroller.engine;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.restserver.IRestApiService;

public class Engine implements IStreamingService, IOFMessageListener, IFloodlightModule{
	
	protected IFloodlightProviderService floodlightProvider;
	protected IRestApiService restApi;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "engine";
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		// TODO Auto-generated method stub
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IStreamingService.class);
		return l;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		// TODO Auto-generated method stub
		Map<Class<? extends IFloodlightService>, IFloodlightService> m = new HashMap<Class<? extends IFloodlightService>, IFloodlightService>();
		// We are the class that implements the service
		m.put(IStreamingService.class, this);
		return m;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		// TODO Auto-generated method stub
		Collection<Class<? extends IFloodlightService>> l = new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		l.add(IRestApiService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		restApi = context.getServiceImpl(IRestApiService.class);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		// TODO Auto-generated method stub
		
		restApi.addRestletRoutable(new EngineWebRoutable());
		
	}

	@Override
	public net.floodlightcontroller.core.IListener.Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void jsonToJavaDescriptor(String json) throws IOException {
		// TODO Auto-generated method stub
		CommunicationDescriptor cd;
		MappingJsonFactory f = new MappingJsonFactory();
		JsonParser jp;

		String srcIP = null;
		String dstIP = null;
		String tpProtocol = null;
		Integer srcPort = null;
		Integer dstPort = null;
		List<Stream> streams = new ArrayList<Stream>();
		BigInteger key = null;

		try {
			jp = f.createParser(json);
		} catch (JsonParseException e) {
			throw new IOException(e);
		}

		jp.nextToken();
		if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
			throw new IOException("Expected START_OBJECT");
		}

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			if (jp.getCurrentToken() != JsonToken.FIELD_NAME) {
				throw new IOException("Expected FIELD_NAME");
			}

			String n = jp.getCurrentName();
			jp.nextToken();

			switch (n) {

			case "srcIPAddress":
				srcIP = jp.getText();
				break;

			case "dstIPAddress":
				dstIP = jp.getText();
				break;

			case "TransportProtocol":
				tpProtocol = jp.getText();
				break;

			case "srcPort":
				srcPort = jp.getValueAsInt();
				break;

			case "dstPort":
				dstPort = jp.getValueAsInt();
				break;

			case "streams":
				streams = jsonToStream(jp.getText(), jp);
				break;

			case "key":
				key = jp.getBigIntegerValue();
				break;
				
			default:
				throw new IOException("Incorret key-value pair");

			}
		}
		
		cd = new CommunicationDescriptor(srcIP, dstIP, tpProtocol, srcPort, dstPort, key);
		addStreamsToDescriptor(cd, streams);
		
		//Then, assign a path to each stream above. This should be done in a different class.
		
	}
	
	private void addStreamsToDescriptor(CommunicationDescriptor cd,
			List<Stream> streams) {
		
		for (Stream stream : streams) {
			cd.addStream(stream);
		}
		
	}

	private List<Stream> jsonToStream(String streams, JsonParser jp) throws IOException{
		
		List<Stream> streamsList = new ArrayList<Stream>();
		
		if (jp.getCurrentToken() != JsonToken.START_ARRAY) {
			throw new IOException("Expected START_ARRAY");
		}
		
		Integer id = 0;
		Integer requiredBandwidth = 0;
		String bandwidthUnit = "";
		
		while (jp.nextToken() != JsonToken.END_ARRAY) {
			
			if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
				id = 0;
			    requiredBandwidth = 0;
				bandwidthUnit = "";
			}
			
			
			while (jp.nextToken() != JsonToken.END_OBJECT) {
				if (jp.getCurrentToken() != JsonToken.FIELD_NAME) {
					throw new IOException("Expected FIELD_NAME");
				}
				
				String name = jp.getCurrentName();
				jp.nextToken();
				
				switch (name) {

				case "id":
					id = jp.getIntValue();
					break;

				case "requiredBandwidth":
					requiredBandwidth = jp.getIntValue();
					break;

				case "bandwidthUnit":
					bandwidthUnit = jp.getText();
					break;
					
				default:
					throw new IOException("Incorret key-value pair");


				}
			}
			Stream s = new Stream(id, requiredBandwidth, bandwidthUnit);
			streamsList.add(s);
			
		}
		
		return streamsList;
		
	}
	
}
