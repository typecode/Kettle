package com.levkanter.kettle.gui;

import java.util.HashMap;

public class Eve<T>
{
	
	public final T source;
	public final String name;
	public final HashMap<String, Object> params;
	
	public Eve(T source, String name) {
		this(source, name, null);
	}
	
	public Eve(T source, 
			String name, 
			HashMap<String, Object> params) 
	{
		this.source = source;
		this.name = name;
		this.params = params;
	}
	
	public boolean has(String param) {
		if (params == null) {
			return false;
		}
		return params.containsKey(param);
	}
	
}