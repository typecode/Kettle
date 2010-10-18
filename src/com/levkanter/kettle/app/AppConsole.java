package com.levkanter.kettle.app;

import java.util.LinkedList;

public class AppConsole 
{
	public static class CMessage 
	{
		enum CMessageLevel {
			INFO, 
			WARNING, 
			ERROR
		}
		
		private String message;
		private CMessageLevel level;
		
		CMessage(String message) {
			this(message, "info");
		}
		
		public CMessage(String message, String levelStr) {
			this.message = message;
			
			if (levelStr.equalsIgnoreCase("error")) {
				level = CMessageLevel.ERROR;
			} else if (levelStr.equalsIgnoreCase("warn")) {
				level = CMessageLevel.WARNING;
			} else {
				level = CMessageLevel.INFO;
			}
		}
		
		String message() {
			return message;
		}
		
		CMessageLevel level() {
			return level;
		}
		
		void println() {
			String ln;
			switch (level) {
				case ERROR:
					ln = "--- ERROR --- " + message;
					break;
				case WARNING:
					ln = "--- WARNING --- " + message;
					break;
				default:
					ln = "--- INFO --- " + message;
			}
			System.out.println(ln);
		}

	}
	
	public boolean AUTO_TRACE;

	private LinkedList<CMessage> mq;
	private int max;
	
	public AppConsole() {
		this(-1);
	}
	
	public AppConsole(int max) {
		mq = new LinkedList<CMessage>();
		this.max = max;
	}
	
	public AppConsole log(String message) {
		return log(message, "info");
	}
	
	public AppConsole log(String message, String levelStr) {
		if (max < 0 || mq.size() < max) { 
			CMessage cmess = new CMessage(message, levelStr);
			mq.add(cmess);
			if (AUTO_TRACE) {
				cmess.println();
			}
		}  else {
			overflow(message, levelStr);
		}
		return this;
	}
	
	void overflow(String message, String levelStr) { 
		
	}
	
	public boolean isEmpty() {
		return mq.isEmpty();
	}
	
	public AppConsole.CMessage peek() {
		return mq.peek();
	}
		
	public AppConsole.CMessage poll() {
		return mq.poll();
	}
	
	public AppConsole clear() {
		mq.clear();
		return this;
	}
	
	public AppConsole dump(boolean reverse) {
		if (reverse) {
			for (int i = 0; i < mq.size(); i += 1) {
				mq.get(i).println();
			}
		} else {
			for (int i = mq.size() - 1; i > -1; i -= 1) {
				mq.get(i).println();
			}
		}
		return this;
	}
	
	public AppConsole dump() {
		return dump(true);
	}
	
}
