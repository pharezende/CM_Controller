package net.floodlightcontroller.engine;

import java.util.ArrayList;
import java.util.List;

public class CommunicationDescriptor {
	
	private String srcIPAddress;
	private String dstIPAddress;
	private String transportProtocol;
	private Integer srcPort;
	private Integer dstPort;
	private List<Stream> streams;
	private char[] key = new char[256]; 

	public CommunicationDescriptor(String srcIPAddress, 
			String dstIPAddress, 
			String transportProtocol, 
			Integer srcPort,
			Integer dstPort,
			char[] key) {
		
		this.srcIPAddress = srcIPAddress;
		this.dstIPAddress = dstIPAddress;
		this.transportProtocol = transportProtocol;
		this.srcPort = srcPort;
		this.dstPort = dstPort;
		this.streams = new ArrayList<>();
		this.key = key;
	}
	
	
	public String getSrcIPAddress() {
		return srcIPAddress;
	}
	public String getDstIPAddress() {
		return dstIPAddress;
	}
	public String getTransportProtocol() {
		return transportProtocol;
	}
	public Integer getSrcPort() {
		return srcPort;
	}
	public Integer getDstPort() {
		return dstPort;
	}
	public List<Stream> getStreams() {
		return streams;
	}
	
	public void addStream(Stream s){
		streams.add(s);
	}
	
	public char[] getKey() {
		return key;
	}
	
}
