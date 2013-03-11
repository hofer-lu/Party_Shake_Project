/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package partyshake_server;

import java.util.Date;
import java.util.Random;
import java.util.Vector;
/**
 *
 * @author hofer_lu
 */
public class Server_Party_Ds {
  public int server_id_auto_increaser=0;
  Vector<Server_party> server_party_hash[];
  Vector<Server_party> server_party_map[][];
  Server_Party_Ds(){
      server_party_hash=new Vector[10];
      server_party_map=new Vector[360][360];
      for(int i=0;i<10;++i){
         server_party_hash[i]=new Vector<Server_party>();
      }
      for(int i1=0;i1<360;++i1){
      	for(int i2=0;i2<360;++i2){
      		server_party_map[i1][i2]=new Vector<Server_party>();
      	}
      }
  }
  
   synchronized void add_party(Server_party Sp){
        server_party_hash[Sp.party_id%10].add(Sp);
        server_party_map[(int)Sp.party_position.longitude%360][(int)Sp.party_position.latitude%360].add(Sp);
  }
   synchronized void remove_party(int Sp_id){
     Server_party Sp=null;int i=-1;
     for(int j=0;j<server_party_hash[Sp_id%10].size();j++){
         if(((Server_party)server_party_hash[Sp_id%10].get(j)).party_id==Sp_id){
             Sp=(Server_party) server_party_hash[Sp_id%10].get(j);
             i=j;
         }
     }
        if(i != -1)
            server_party_hash[Sp.party_id%10].remove(i);
        i = server_party_map[(int)Sp.party_position.longitude%360][(int)Sp.party_position.latitude%360].indexOf(Sp); 
        if(i != -1) 
            server_party_map[(int)Sp.party_position.longitude%360][(int)Sp.party_position.latitude%360].remove(i);
  }
   Server_party getparty(int party_id){
      for(int i=0;i<server_party_hash[party_id%10].size();++i){
      if(((Server_party)server_party_hash[party_id%10].elementAt(i)).party_id==party_id){
          return (Server_party)server_party_hash[party_id%10].elementAt(i);
      }
      }return null;
  }
   private class point{
        int x;
        int y;
        point (int a,int b){
            x=a;y=b;
        }
    }
   private point getvalidlist(int x,int y){
        if(server_party_map[x][y].isEmpty()) {
            return gethelp(x,y,1);
        }
        return new point(x,y);
        
    }
   private point gethelp(int x,int y,int n){
        if(!((x-n)>=0||(y-n)>=0||(x+n)<=360||(y+n)<=360)){
            System.out.println(n);
            return null;
        }
        for(int i=1;i<=n;++i){
            if(point_is_inside(x-i,y-n)){
                if(!server_party_map[x-i][y-n].isEmpty()) {
                    return new point(x-i,y-n);
                }
            }
            if(point_is_inside(x+i,y-n)){
                if(!server_party_map[x+i][y-n].isEmpty()) {
                    return new point(x+i,y-n);
                }
            }
            if(point_is_inside(x-i,y+n)){
                if(!server_party_map[x-i][y+n].isEmpty()) {
                    return new point(x-i,y+n);
                }
            }
            if(point_is_inside(x+i,y+n)){
                if(!server_party_map[x+i][y+n].isEmpty()) {
                    return new point(x+i,y+n);
                }
            }
            if(point_is_inside(x-n,y-i)){
                if(!server_party_map[x-n][y-i].isEmpty()) {
                    return new point(x-n,y-i);
                }
            }
            if(point_is_inside(x-n,y+i)){
                if(!server_party_map[x-n][y+i].isEmpty()) {
                    return new point(x-n,y+i);
                }
            }
            if(point_is_inside(x+n,y-i)){
                if(!server_party_map[x+n][y-i].isEmpty()) {
                    return new point(x+n,y-i);
                }
            }
           if(point_is_inside(x+n,y+i)){
                if(!server_party_map[x+n][y+i].isEmpty()) {
                    return new point(x+n,y+i);
                }
            }
        }                
        return gethelp(x,y,n+1);
    }
   private boolean point_is_inside(int x,int y){
        if(x<0||x>359||y<0||y>359)
            return false;
        return true;
    }
   Server_party get_search_party(int x,int y){
        point temp = getvalidlist(x,y);
        if(null==temp) {
            return null;
        }
        Date dat=new Date();
        Random r = new Random(dat.getTime());
        return server_party_map[temp.x][temp.y].elementAt(r.nextInt(server_party_map[temp.x][temp.y].size()));        
    }
}