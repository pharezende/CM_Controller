package net.floodlightcontroller.engine;

public class Stream {

	private Integer id;
	private Integer requiredBandwidth;
	private String bandwidthUnit;

	public Stream(Integer id, Integer requiredBandwidth, String bandwidthUnit) {
		this.id = id;
		this.requiredBandwidth = requiredBandwidth;
		this.bandwidthUnit = bandwidthUnit;
	}

	public Integer getId() {
		return id;
	}

	public Integer getRequiredBandwidth() {
		return requiredBandwidth;
	}

	public String getBandwidthUnit() {
		return bandwidthUnit;
	}

}
