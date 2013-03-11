package connect;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.PartyShake.Client_party;
import com.PartyShake.Position;
import com.PartyShake.Sentence;

import android.util.Log;



public class Connection {
	String serverAddr_s = "192.168.1.102";
	int port_num = 5800;
    private DataInputStream dis=null;
	private DataOutputStream dos=null;
    Socket socket = null;
    public Connection(String add){
    	serverAddr_s=add;
    }
	public boolean init() throws IOException {
		InetAddress serverAddr = InetAddress.getByName(serverAddr_s);// TCPServer.SERVERIP
		Log.i("TCP", "C: Connecting...");
		socket = new Socket(serverAddr, port_num);
		Log.i("TCP", "tcp5");
	       dis = new DataInputStream(socket.getInputStream());
	       Log.i("TCP", "tcp6");
	       dos = new DataOutputStream(socket.getOutputStream());
	       Log.i("TCP", "tcp7");
		if(socket==null){
			Log.i("TCP", "≥ı º ß∞‹");
			return false;
		}
		return true;
	}

	public boolean send(String a) {
		String message = "AndroidRes,Where is my Pig (Android)?";
		try {
			Log.d("TCP", "C: Sending: '" + message + "'");
			dos.writeUTF(a);
			dos.writeInt(1234);
			dos.writeUTF("Œ“≤›∞°£°£°  ≤›≤›≈∂");
			dos.flush();
		} catch (Exception e) {
			Log.e("TCP", "S: Error", e);
			return false;
		}
		return true;
	}


    public boolean close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public String check_op_type(Element root) {
        Element temp = (Element) root.getElementsByTagName("type").item(0);
        String op_type = temp.getFirstChild().getNodeValue();
        return op_type;
    }
    
    public boolean login(String name) {
        try {
            //init();
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            XML_package +="<msg><type>login</type><client_name>" + name + "</client_name></msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean logout() {
        try {
            close();
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            XML_package += "<msg><type>logout</type></msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean party_setup(String client_name, String party_Name, Date party_date, int duration, Position party_position) {
        try {
        	String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>party_setup</type>";
            String clieant_name = "<client_name>" + client_name + "client_name";
            String name = "<party_Name>" + party_Name + "</party_Name>";
            String Date = "<party_date>" + party_date.getTime() + "</party_date>";
            String drt = "<party_duration>" + duration + "</party_duration>";
            String p_pos = "<party_position>"
                    + "<longitude>" + party_position.longitude + "</longitude>"
                    + "<latitude>" + party_position.latitude + "</latitude>"
                    + "<position_discript>" + party_position.position_discript + "</position_discript>"
                    + "</party_position>";
            buffer = "<msg>" + buffer + name + Date + drt + p_pos + "</msg>";
            XML_package = XML_package + buffer;
            dos.writeUTF(buffer);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean search_party(Position party_position) {
        try {
        	String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>search_party</type>";
            buffer  += "<party_position>"
                    + "<longitude>" + party_position.longitude + "</longitude>"
                    + "<latitude>" + party_position.latitude + "</latitude>"
                    + "<position_discript>" + party_position.position_discript + "</position_discript>"
                    + "</party_position>";
            XML_package = XML_package + "<msg>" + buffer + "</msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean quit_party(int party_id) {
        try {
        	String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>quit_party</type>";
            buffer += "<party_id>" + party_id + "</party_id>";
            XML_package = XML_package + "<msg>" + buffer + "</msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean join_party(String client_name, int party_id) {
        try {
        	String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>join_party</type>";
            buffer += "<client_name>" + client_name + "</client_name>";
            buffer += "<party_id>" + party_id + "</party_id>";
            XML_package = XML_package + "<msg>" + buffer + "</msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean chat(Client_party CP, Sentence S) {
        try {
            Date date_speak;
            String speaker;
            String content;
            String XML_package ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
            String buffer = "<type>chat</type>";
            buffer += "<party_id>" + CP.party_id + "</party_id>";
            buffer += "<speak_date>" + S.date_speak + "</speak_date>";
            buffer += "<speak_speaker>" + S.speaker + "</speak_speaker>";
            buffer += "<speak_content>" + S.content + "</speak_content>";
            XML_package = XML_package + "<msg>" + buffer + "</msg>";
            dos.writeUTF(XML_package);
            dos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

 public Element receive_msg_package() {
        try {
            String XML_package = dis.readUTF();
            System.out.print(XML_package);
            InputStream XML_package_stream = new ByteArrayInputStream(XML_package.getBytes("UTF-8"));
            Element temp = null;
			try {
				temp = (Element) XML_Operator.XMLReader(XML_package_stream);
			} catch (Exception e) {
				e.printStackTrace();
			}
            return temp;
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
 
    public void get_login_info(Element root,String client_name) {
        Element temp = (Element) root.getElementsByTagName("client_name").item(0);
        client_name = temp.getFirstChild().getNodeValue();
    }

    public void get_search_party_info(Element root, Client_party Cp) {
        Element temp = (Element) root.getElementsByTagName("party_Name").item(0);
        Cp.party_name = temp.getFirstChild().getNodeValue();

        temp = (Element) root.getElementsByTagName("party_date").item(0);
        Cp.party_date = new Date(Long.parseLong(temp.getFirstChild().getNodeValue()));

        temp = (Element) root.getElementsByTagName("party_duration").item(0);
        Cp.duration =  Integer.parseInt(temp.getFirstChild().getNodeValue());
        
        temp = (Element) root.getElementsByTagName("party_id").item(0);
        Cp.party_id =  Integer.parseInt(temp.getFirstChild().getNodeValue());

        NodeList e_list = root.getElementsByTagName("party_position");
        Element p_root = (Element) e_list.item(0);
        Cp.party_position.longitude = Double.parseDouble(p_root.getElementsByTagName("latitude").item(0).getFirstChild().getNodeValue());
        Cp.party_position.latitude = Double.parseDouble(p_root.getElementsByTagName("longitude").item(0).getFirstChild().getNodeValue());
        Cp.party_position.position_discript = p_root.getElementsByTagName("position_discript").item(0).getFirstChild().getNodeValue();
    }

    public void get_party_setup_info(Element root, Client_party Cp) {
   	
    	 Element temp = (Element) root.getElementsByTagName("party_Name").item(0);
         Cp.party_name = temp.getFirstChild().getNodeValue();

         temp = (Element) root.getElementsByTagName("party_date").item(0);
         Cp.party_date = new Date(Long.parseLong(temp.getFirstChild().getNodeValue()));

         temp = (Element) root.getElementsByTagName("party_duration").item(0);
         Cp.duration =  Integer.parseInt(temp.getFirstChild().getNodeValue());
         
         temp = (Element) root.getElementsByTagName("party_id").item(0);
         Cp.party_id =  Integer.parseInt(temp.getFirstChild().getNodeValue());

         NodeList e_list = root.getElementsByTagName("party_position");
         Element p_root = (Element) e_list.item(0);
         Cp.party_position.longitude = Double.parseDouble(p_root.getElementsByTagName("latitude").item(0).getFirstChild().getNodeValue());
         Cp.party_position.latitude = Double.parseDouble(p_root.getElementsByTagName("longitude").item(0).getFirstChild().getNodeValue());
         Cp.party_position.position_discript = p_root.getElementsByTagName("position_discript").item(0).getFirstChild().getNodeValue();
    }

    public void get_quit_party_info(Element root, int party_id) {
        //party_name = root.getAttribute("party_Name");
        Element temp = (Element) root.getElementsByTagName("party_id").item(0);
        party_id =Integer.parseInt(temp.getFirstChild().getNodeValue());
    }

    public void get_join_party_info(Element root, Client_party Cp) {
        
   	 Element temp = (Element) root.getElementsByTagName("party_Name").item(0);
     Cp.party_name = temp.getFirstChild().getNodeValue();

     temp = (Element) root.getElementsByTagName("party_date").item(0);
     Cp.party_date = new Date(Long.parseLong(temp.getFirstChild().getNodeValue()));

     temp = (Element) root.getElementsByTagName("party_duration").item(0);
     Cp.duration =  Integer.parseInt(temp.getFirstChild().getNodeValue());
     
     temp = (Element) root.getElementsByTagName("party_id").item(0);
     Cp.party_id =  Integer.parseInt(temp.getFirstChild().getNodeValue());

     NodeList e_list = root.getElementsByTagName("party_position");
     Element p_root = (Element) e_list.item(0);
     Cp.party_position.longitude = Double.parseDouble(p_root.getElementsByTagName("latitude").item(0).getFirstChild().getNodeValue());
     Cp.party_position.latitude = Double.parseDouble(p_root.getElementsByTagName("longitude").item(0).getFirstChild().getNodeValue());
     Cp.party_position.position_discript = p_root.getElementsByTagName("position_discript").item(0).getFirstChild().getNodeValue();
    }

    public void get_chat_info(Element root, int party_id, Sentence s) {
        Element temp = (Element) root.getElementsByTagName("party_id").item(0);
        party_id =Integer.parseInt(temp.getFirstChild().getNodeValue());
    	
        temp = (Element) root.getElementsByTagName("speak_date").item(0);
        s.date_speak = new Date(Long.parseLong(temp.getFirstChild().getNodeValue()));
        temp = (Element) root.getElementsByTagName("speak_speaker").item(0);
        s.speaker = temp.getFirstChild().getNodeValue();
        temp = (Element) root.getElementsByTagName("speak_content").item(0);
        s.content = temp.getFirstChild().getNodeValue();
    }
}
