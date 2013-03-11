/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package partyshake_server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author hofer_lu
 */
public class Connection {
    private DataInputStream dis=null;
    private DataOutputStream dos=null;
    Socket socket = null;
    Connection(Socket s){
        socket=s;
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Element receive_msg_package() {
        try {
            String XML_package = dis.readUTF();
            System.out.print(XML_package);
            InputStream XML_package_stream = new ByteArrayInputStream(XML_package.getBytes("UTF-8"));
            Element temp = XML_Operator.XMLReader(XML_package_stream);
            //temp = (Element) temp.getElementsByTagName("msg").item(0);
            return temp;
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String check_op_type(Element root) {
        Element temp = (Element) root.getElementsByTagName("type").item(0);
        String op_type = temp.getFirstChild().getNodeValue();
        return op_type;
    }

    public void get_login_info(Element root, String client_name) {
        Element temp = (Element) root.getElementsByTagName("client_name").item(0);
        client_name = temp.getFirstChild().getNodeValue();
    }

    public void get_search_party_info(Element root, Position p) {
        NodeList e_list = root.getElementsByTagName("party_position");
        Element p_root = (Element) e_list.item(0);
        p.longitude = Double.parseDouble(p_root.getElementsByTagName("latitude").item(0).getFirstChild().getNodeValue());
        p.latitude = Double.parseDouble(p_root.getElementsByTagName("longitude").item(0).getFirstChild().getNodeValue());
        p.position_discript = p_root.getElementsByTagName("position_discript").item(0).getFirstChild().getNodeValue();
    }

    public void get_party_setup_info(Element root, Server_party Sp) {
        Element temp = (Element) root.getElementsByTagName("party_Name").item(0);
        Sp.party_name = temp.getFirstChild().getNodeValue();

        temp = (Element) root.getElementsByTagName("party_date").item(0);
        Sp.party_date = new Date(Long.parseLong(temp.getFirstChild().getNodeValue()));

        temp = (Element) root.getElementsByTagName("party_duration").item(0);
        Sp.duration =  Integer.parseInt(temp.getFirstChild().getNodeValue());

        NodeList e_list = root.getElementsByTagName("party_position");
        Element p_root = (Element) e_list.item(0);
        Sp.party_position.longitude = Double.parseDouble(p_root.getElementsByTagName("latitude").item(0).getFirstChild().getNodeValue());
        Sp.party_position.latitude = Double.parseDouble(p_root.getElementsByTagName("longitude").item(0).getFirstChild().getNodeValue());
        Sp.party_position.position_discript = p_root.getElementsByTagName("position_discript").item(0).getFirstChild().getNodeValue();
    }

    public void get_quit_party_info(Element root, int party_id) {
        party_id = Integer.parseInt(root.getAttribute("party_id"));
    }

    public void get_join_party_info(Element root, int party_id, String client_name) {
        party_id = Integer.parseInt(root.getAttribute("party_id"));
        client_name = root.getAttribute("client_name");
    }

    public void get_chat_info(Element root, int party_id, Sentence s) {
        party_id = Integer.parseInt(root.getAttribute("party_id"));
        s.date_speak = new Date(Long.parseLong(root.getAttribute("speak_date")));
        s.speaker = root.getAttribute("speak_speaker");
        s.content = root.getAttribute("speak_content");
    }

    public boolean reply_login(String name) {
        try {
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            XML_package += "<msg><type>login</type><msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reply_party_setup(String client_name, Server_party sp) {
        try {
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>party_setup</type>";
            String name = "<party_Name>" + sp.party_name + "</party_Name>";
            String p_id ="< party_id>"+ sp.party_id+"</party_id>";
            String Date = "<party_date>" + sp.party_date.getTime() + "</party_date>";
            String drt = "<party_duration>" + sp.duration + "</party_duration>";
            String p_pos = "<party_position>"
                    + "<longitude>" + sp.party_position.longitude + "</longitude>"
                    + "<latitude>" + sp.party_position.latitude + "</latitude>"
                    + "<position_discript>" + sp.party_position.position_discript+ "</position_discript>"
                    + "</party_position>";
            buffer = "<msg>" + buffer + name +p_id +Date+drt+p_pos+"</msg>";
            XML_package +=buffer;
            dos.writeUTF(XML_package);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean reply_search_party(Server_party Sp) {
        try {
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>search_party</type>";
            String name = "<party_Name>" + Sp.party_name + "</party_Name>";
            String Date = "<party_date>" + Sp.party_date.getTime() + "</party_date>";
            String drt = "<party_duration>" + Sp.duration + "</party_duration>";
            String p_pos = "<party_position>"
                    + "<longitude>" + Sp.party_position.longitude + "</longitude>"
                    + "<latitude>" + Sp.party_position.latitude + "</latitude>"
                    + "<position_discript>" + Sp.party_position.position_discript + "</position_discript>"
                    + "</party_position>";
            String p_id= "<party_id>"+Sp.party_id+"</party_id>";
            buffer = "<msg>" + buffer + name + Date + drt + p_pos + p_id+"</msg>";
            XML_package +=buffer;
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reply_quit_party(int party_id,String party_name ) {
        try {
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>quit_party</type>";
            buffer += "<party_id>" + party_id + "</party_id>";
            buffer += "<party_Name>" + party_name + "</party_Name>";
            buffer = "<msg>" + buffer + "<msg>";
            XML_package +=buffer;
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reply_join_party(Server_party sp) {
        try {
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>party_setup</type>";
            String name = "<party_Name>" + sp.party_name + "</party_Name>";
            String p_id ="< party_id>"+ sp.party_id+"</party_id>";
            String Date = "<party_date>" + sp.party_date.getTime() + "</party_date>";
            String drt = "<party_duration>" + sp.duration + "</party_duration>";
            String p_pos = "<party_position>"
                    + "<longitude>" + sp.party_position.longitude + "</longitude>"
                    + "<latitude>" + sp.party_position.latitude + "</latitude>"
                    + "<position_discript>" + sp.party_position.position_discript+ "</position_discript>"
                    + "</party_position>";
            buffer = "<msg>" + buffer + name +p_id +Date+drt+p_pos+"</msg>";
            XML_package +=buffer;
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reply_chat(int party_id, Sentence S,Socket Sk) {
        try {
            DataOutputStream ddos=new DataOutputStream(Sk.getOutputStream());
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>chat</type>";
            buffer +="<party_id>"+party_id+"<party_id>";
            buffer += "<speak_date>" + S.date_speak + "</speak_date>";
            buffer += "<speak_speaker>" + S.speaker + "</speak_speaker>";
            buffer += "<speak_content>" + S.content + "</speak_content>";
            buffer = "<msg>" + buffer + "<msg>";
            XML_package +=buffer;
            ddos.writeUTF(XML_package);
            ddos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
