package Cliente;

import Interface.ClienteInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClienteImpl extends UnicastRemoteObject implements ClienteInterface{
    
    Cliente cliente;
    public ClienteImpl(Cliente cli) throws RemoteException {
        super();
        cliente = cli;
    }
   
    @Override
    public void ReceberMensagemServidor(String apelidoOrigem, String mensagem) throws RemoteException {
        System.out.println(apelidoOrigem + " Disse...:"+mensagem);
    }

    @Override
    public void ReceberNovaConexao(String apelido, String nome) throws RemoteException {
        
        System.out.println("* "+apelido+" Está conectado.");
       cliente.atualizarContatos(apelido, nome);
       //cliente.atualizaTabelaContatos();
    }

    @Override
    public void DesconexaoCliente(String apelido, String nome) throws RemoteException {
        cliente.desconectaCliente(apelido, nome);
    }
}
