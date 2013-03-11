/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package partyshake_server;

import java.util.Date;

/**
 *
 * @author hofer_lu
 */

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
