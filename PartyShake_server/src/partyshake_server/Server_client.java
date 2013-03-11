/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package partyshake_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import org.w3c.dom.Element;
import partyshake_server.Server_Party_Ds;

/**
 *
 * @author haofa.lu.sz
 */
public class Server_client implements Runnable {

    public String name = "";
    private Socket ss = null;
    Server_Party_Ds sp_DS = null;
    Connection cc = null;
    Client_Db c_db = null;

    Server_client(Socket s,Client_Db db,Server_Party_Ds sp_Db) {
        ss = s;
        c_db = db;
        sp_DS=sp_Db;
        cc = new Connection(s);
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
                Position p=new Position();
                cc.get_search_party_info(receive_msg_package_root, p);
                System.out.println("search_party");
                System.out.println(p.latitude);
                System.out.println(p.longitude);
                System.out.println(p.position_discript);
            }
            if (op_type.equals("quit_party")) {
                System.out.println("quit_party");
            }
            if (op_type.equals("join_party")) {
                System.out.println("join_party");
            }
            if (op_type.equals("chat")) {
                System.out.println("chat");
            }
            if (op_type.equals("logout")) {
                System.out.println("logout");
            }
        }
    }

    void login_handler(Element receive_msg_package_root) {
        String temp_name = null;
        cc.get_login_info(receive_msg_package_root, temp_name);
        if (temp_name == null) {
            return;//ERROR
        }
        name = temp_name;
        if (c_db.getlist(name) == null) {
            c_db.regist(name);
        } else {
            Vector<Integer> c_party_list = c_db.getlist(name);
            for(int i=0;i<c_party_list.size();++i){
                sp_DS.getparty(i).client_socket_list.add(ss);
            }
        }
        System.out.println(temp_name);
        cc.reply_login(name);
    }
    void party_setup_handler(Element receive_msg_package_root){
        Server_party Sp=new Server_party();
        Sp.party_id=sp_DS.server_id_auto_increaser++;
        cc.get_party_setup_info(receive_msg_package_root,Sp);
        Sp.client_socket_list.add(ss);
        sp_DS.add_party(Sp);
        c_db.add_client_party(name, Sp.party_id);
        cc.reply_party_setup(name, Sp);
        System.out.println(Sp.party_name+Sp.party_id+Sp.party_date+Sp.duration+Sp.party_position.latitude
                +Sp.party_position.position_discript);
    }
    void quit_party_handler(Element receive_msg_package_root){
        int p_id = Integer.parseInt(receive_msg_package_root.getAttribute("party_id"));
        c_db.delete_client_party(name, p_id);
        sp_DS.remove_party(p_id);
        cc.reply_quit_party(p_id, name);
    }

    void join_party_handler(Element receive_msg_package_root){
         int p_id = Integer.parseInt(receive_msg_package_root.getAttribute("party_id"));
         c_db.add_client_party(name,p_id);
         Server_party sp=sp_DS.getparty(p_id);
         sp.client_socket_list.add(ss);
         cc.reply_join_party(sp);
    }
    void logout_handler(Element receive_msg_package_root){
            Vector<Integer> c_party_list = c_db.getlist(name);
            for(int i=0;i<c_party_list.size();++i){
                sp_DS.getparty(i).client_socket_list.removeElement(ss);
            }
        }
    void chat_handler(Element receive_msg_package_root){
        int party_id=-1;
        Sentence S=new Sentence();
        cc.get_chat_info(receive_msg_package_root, party_id, S);
        Server_party party = sp_DS.getparty(party_id);
        for(int i=0;i<party.client_socket_list.size();++i){
            if(!party.client_socket_list.get(i).equals(ss))
            cc.reply_chat(party_id, S, party.client_socket_list.get(i));
        }
    }
}
