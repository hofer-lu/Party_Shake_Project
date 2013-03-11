package com.PartyShake;

import java.util.Hashtable;
import java.util.Vector;

import org.w3c.dom.Element;

import android.widget.Toast;

import connect.Connection;

public class Client implements Runnable{
	String client_name;
	Hashtable<Integer, Client_party> party_list;
	Connection cc;
	Client(String c_name,Connection c_connection){
		cc = c_connection;
		client_name = c_name;
		party_list = new Hashtable<Integer, Client_party>();
	}
	Connection get_connection(){
		return cc;
	}
    public void run() {
        while (true) {
            Element receive_msg_package_root = cc.receive_msg_package();
            String op_type = cc.check_op_type(receive_msg_package_root);
            if (op_type.equals("login")) {
                System.out.println("enter login\n");
                login_handler(receive_msg_package_root);
            }
            if (op_type.equals("party_setup")) {
                System.out.println("party_setup");
                party_setup_handler(receive_msg_package_root);
            }
            if (op_type.equals("search_party")) {
            	Client_party cp=new Client_party();
                cc.get_search_party_info(receive_msg_package_root, cp);
            }
            if (op_type.equals("quit_party")) {
                System.out.println("quit_party");
                quit_party_handler(receive_msg_package_root);
            }
            if (op_type.equals("join_party")) {
                System.out.println("join_party");
                join_party_handler(receive_msg_package_root);
            }
            if (op_type.equals("chat")) {
                System.out.println("chat");
                chat_handler(receive_msg_package_root);
            }
            if (op_type.equals("logout")) {
                System.out.println("logout");
                logout_handler(receive_msg_package_root);
            }
        }
    }
    void login_handler(Element receive_msg_package_root) {
        String temp_name = null;
        cc.get_login_info(receive_msg_package_root, temp_name);
        //login success
    }
    void party_setup_handler(Element receive_msg_package_root){
    	Client_party Sp=new Client_party();
    	cc.get_party_setup_info(receive_msg_package_root, Sp);
    	party_list.put(Sp.party_id,Sp);   	
    }
    void quit_party_handler(Element receive_msg_package_root){
        int p_id = Integer.parseInt(receive_msg_package_root.getAttribute("party_id"));
        party_list.remove(p_id);
        
    }

    void join_party_handler(Element receive_msg_package_root){
    	Client_party Sp=new Client_party();
    	cc.get_party_setup_info(receive_msg_package_root, Sp);
    	party_list.put(Sp.party_id,Sp); 
    }
    void logout_handler(Element receive_msg_package_root){
        }
    void chat_handler(Element receive_msg_package_root){
        int party_id=-1;
        Sentence S=new Sentence();
        cc.get_chat_info(receive_msg_package_root, party_id, S);
        party_list.get(party_id).last_ten_sentance_list.add(S);
    }
}
