/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hofer.partyshake;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author haofa.lu.sz
 */
/*
 * used to store the registclient but not the online client
*/
public class Client_Db {
    Hashtable client_hs = null;
    Client_Db(){
        client_hs = new Hashtable();
    }
    public void regist(String client_name){
        client_hs.put(client_name, new Vector<Integer>());
    }
    public Vector<Integer> getlist(String client_name){
        return (Vector<Integer>)client_hs.get(client_name);
    }
    public void add_client_party(String client_name,int party_id){
        ((Vector<Integer>)client_hs.get(client_name)).add(party_id);
    }
    public void delete_client_party(String client_name,int party_id){
        Vector<Integer> temp = (Vector<Integer>)client_hs.get(client_name);
        if(temp.isEmpty()) {
           return;
       }
        for(int i=0;i<temp.size();++i){
            if(temp.get(i)==party_id) {
                temp.remove(i);
            }
        }
    }
}
