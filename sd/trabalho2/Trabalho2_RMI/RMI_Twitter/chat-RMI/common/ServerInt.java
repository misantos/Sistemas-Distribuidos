package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface ServerInt extends Remote{
	
	public boolean login (ClientInt c) throws RemoteException;
	public void publish (String s) throws RemoteException;
	public Vector getConnected() throws RemoteException;
	

}
