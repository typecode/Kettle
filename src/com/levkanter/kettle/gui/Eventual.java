package com.levkanter.kettle.gui;

public interface Eventual<T> 
{
	
	public T addHandler(EHandler<T> handler);
	
	public T clearHandlers();
		
}
