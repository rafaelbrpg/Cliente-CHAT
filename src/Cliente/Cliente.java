package Cliente;

import Interface.ClienteInterface;
import Interface.ServidorInterface;
import java.io.DataInputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import sun.rmi.registry.RegistryImpl;

public class Cliente {

    DataInputStream entradaTeclado;
    ServidorInterface servidorInterface;
    ClienteInterface clienteInterface;
    String portaServidor;
    String enderecoServidor;
    String apelidoOrigem;

    private List<String> mensagens;
    private HashMap<String, Contato> contatos;
    private DefaultTableModel tabelaclientes;

    public Cliente(String endereco, String portaServidor,String enderecoCliente, String apelido, String nome, String portaCliente) throws NoSuchObjectException, RemoteException {

        entradaTeclado = new DataInputStream(System.in);
        clienteInterface = new ClienteImpl();
        disponibilizarServicos(enderecoCliente, portaCliente, apelido);
        conectar(endereco, portaServidor,enderecoCliente, apelido, nome, portaCliente);
        
        contatos = new HashMap<String, Contato>();
        tabelaclientes = new DefaultTableModel();
        tabelaclientes.addColumn("Apelido");
        tabelaclientes.addColumn("Porta");
    }

    private void disponibilizarServicos(String enderecoCliente, String portaCliente, String apelido) {
        try {
            Registry impl = new RegistryImpl(Integer.valueOf(portaCliente));
        } catch (RemoteException ex) {
            System.out.println("Servidor de registro nao foi carregado");
        }

        try {
            //Naming.rebind("rmi://"+enderecoCliente+":" + String.valueOf(portaCliente) + "/" + apelido, clienteInterface);
            Naming.rebind("rmi://localhost:" + String.valueOf(portaCliente) + "/" + apelido, clienteInterface);
        } catch (RemoteException ex) {
            System.out.println("Falha ao disponibilizar Servicos!");
        } catch (Exception e) {
            System.out.println("erro!");
        }
    }

    private void conectar(String endereco, String portaServ, String enderecoCliente, String apelido, String nome, String portaCliente) {
        try {
            enderecoServidor = endereco;
            portaServidor = portaServ;
            apelidoOrigem = apelido;

            servidorInterface = (ServidorInterface) Naming.lookup("rmi://" + enderecoServidor + ":" + portaServidor + "/servidorEco");
            servidorInterface.Conectar(apelidoOrigem, nome, enderecoCliente, portaCliente);
            
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarMensagem(String apelidoDestino, String mensagem) {
        try {
            servidorInterface = (ServidorInterface) Naming.lookup("rmi://" + enderecoServidor + ":" + portaServidor + "/servidorEco");
            servidorInterface.ReceberMensagemCliente(apelidoOrigem, apelidoDestino, mensagem);
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconectar(String enderecoCliente, String apelido, String portaCliente){
        try {
            servidorInterface = (ServidorInterface) Naming.lookup("rmi://" + enderecoServidor + ":" + portaServidor + "/servidorEco");
            servidorInterface.Desconectar(apelido, enderecoCliente, portaCliente);
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
