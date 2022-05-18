import java.rmi.*;
public class ArrancaServidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try
	        {
	            System.out.println("Arrancando servidor...");
	            Naming.rebind("ServidorMat_1", new ServidorMat());
	        }
	        catch (Exception e)
	        {
	            System.out.println("Ocorreu um problema no arranque do servidor.\n"+e.toString());
	        }

	}

}
