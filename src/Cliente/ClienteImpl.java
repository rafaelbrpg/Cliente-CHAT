package Cliente;

import Interface.ClienteInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteImpl extends UnicastRemoteObject implements ClienteInterface{
    
  
    public ClienteImpl() throws RemoteException {
        super();
         
    }
   
    @Override
    public void ReceberMensagemServidor(String apelidoOrigem, String mensagem) throws RemoteException {
        System.out.println(apelidoOrigem + "Disse...:"+mensagem);
    }

    @Override
    public void ReceberNovaConexao(String apelido, String nome) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DesconexaoCliente(String apelido, String nome) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
