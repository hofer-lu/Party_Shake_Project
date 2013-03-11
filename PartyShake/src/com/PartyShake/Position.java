package com.PartyShake;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Position implements Serializable{
	public double  longitude;
	public double  latitude;
	public String  position_discript;
	Position(double lo,double lp,String pd){
		longitude=lo;
		latitude=lp;
		position_discript=pd;
	}
}
