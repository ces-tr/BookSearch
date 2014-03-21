package com.Android.enums;



public enum ServerType {

	   	   
	   Campusbooks("Campusbooks", 0) {
			@Override
			public String getServer() {
				return "Campusbooks";
			}
	   	},
	   Googlebooks("Googlebooks", 1) {
			@Override
			public String getServer() {
				return "Googlebooks";
			}
	
	   	};
	   private ServerType(String var1, int var2) {}
	   public abstract String getServer();
	}
