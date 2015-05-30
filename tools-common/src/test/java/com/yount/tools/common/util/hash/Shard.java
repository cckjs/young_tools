package com.yount.tools.common.util.hash;

public class Shard {

	private String serverIp;
	
	private int serverPort;

	public Shard(String serverIp,int serverPort){
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}
	
	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public String toString(){
		return serverIp+"_"+serverPort;
	}
}
