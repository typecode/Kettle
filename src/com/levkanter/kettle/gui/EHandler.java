package com.levkanter.kettle.gui;

public interface EHandler<T>
{
	
	void handle(Eve<?> event);
	
}