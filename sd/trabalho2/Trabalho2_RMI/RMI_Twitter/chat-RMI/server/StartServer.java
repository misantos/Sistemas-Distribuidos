package server;

import java.rmi.Naming;

import common.ServerInt;

public class StartServer {

	public static void main(String[] args) {

		try {
			java.rmi.registry.LocateRegistry.createRegistry(8080);
			ServerInt b = new ServerImp();
			Naming.rebind("rmi://localhost:8080/twitter", b);
			System.out.println("[system] Twitter server is ready.");
		}catch(Exception e) {
			System.out.println("Twitter server failed "+e);
		}
	}

}
