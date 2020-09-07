package com.jcm001.redirect.dto;

public class Endpoint {
	
	private String code;
	private String link;
	private int times;
	private int quantity;
	
	public Endpoint() {
		
	}

	public Endpoint(String code, String link, int times, int quantity) {
		super();
		this.code = code;
		this.link = link;
		this.times = times;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Endpoint [code=");
		builder.append(code);
		builder.append(", link=");
		builder.append(link);
		builder.append(", times=");
		builder.append(times);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}