package com.PartyShake;

import java.util.Date;

public class Sentence {
	public Date date_speak;
	public String speaker;
	public String content;
	void sentence(Date d,String spker,String c){
		date_speak=d;
		speaker=spker;
		content=c;
	}
}
