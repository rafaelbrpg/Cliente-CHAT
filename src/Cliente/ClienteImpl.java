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
        cliente.atualizaTabelaChat("> "+apelidoOrigem + "  diz : "+mensagem);
    }

    @Override
    public void ReceberNovaConexao(String apelido, String nome) throws RemoteException {
       cliente.atualizarContatos(apelido, nome);
    }

    @Override
    public void DesconexaoCliente(String apelido, String nome) throws RemoteException {
        cliente.desconectaCliente(apelido, nome);
    }
}
