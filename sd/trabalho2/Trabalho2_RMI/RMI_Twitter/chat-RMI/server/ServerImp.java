package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import common.ClientInt;
import common.ServerInt;

public class ServerImp extends UnicastRemoteObject implements ServerInt{

	private Vector v = new Vector();
	public ServerImp() throws RemoteException{}
	
	public boolean login (ClientInt c) throws RemoteException{
		System.out.println(c.getName()+" got connected.......");
		c.tell("You have connected sucessfully.");
		publish(c.getName()+" has just connected.");
		
		v.add(c);
		return true;
	}
	
	public void publish (String s) throws RemoteException{
		System.out.println(s);
		for(int i = 0; i < v.size(); i++) {
			try {
				ClientInt tmp = (ClientInt)v.get(i);
				tmp.tell(s);
			}catch(Exception e) {}
		}
	}
		
	public Vector getConnected() throws RemoteException{
		return v;
	}
}
