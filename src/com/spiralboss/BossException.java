package com.spiralboss;

public class BossException extends RuntimeException {

	private static final long serialVersionUID = 4268201007356030544L;

	public BossException(String msg){
		super(msg);
	}
	
	public BossException(String msg, Throwable t){ 
		super(msg,t);
	}

}
