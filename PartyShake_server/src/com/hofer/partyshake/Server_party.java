/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hofer.partyshake;

import java.net.Socket;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author hofer_lu
 */
public class Server_party {
  int party_id;
  Vector<Socket> client_socket_list;
  String party_name;
  Position party_position;
  Date party_date;
  int duration;
}
