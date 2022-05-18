package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.ClientInt;

public class ClientImp extends UnicastRemoteObject implements ClientInt{
	private String name;
	private ChatUI ui;
	
	public ClientImp (String n)throws RemoteException{
		name = n;
	}
	
	public void tell (String st) throws RemoteException{
		System.out.println(st);
		ui.writeMsg(st);
	}
	
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setGUI(ChatUI t) {
		ui =t;
	}
}
