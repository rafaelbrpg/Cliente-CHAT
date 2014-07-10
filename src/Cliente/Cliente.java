package Cliente;

import Interface.ClienteInterface;
import Interface.ServidorInterface;
import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import sun.rmi.registry.RegistryImpl;
import view.TelaCliente;

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
    private TelaCliente telaCliente;

    public Cliente(TelaCliente tela, String endereco, String portaServidor, String enderecoCliente, String apelido, String nome, String portaCliente) throws NoSuchObjectException, RemoteException {

        telaCliente = tela;
        entradaTeclado = new DataInputStream(System.in);
        clienteInterface = new ClienteImpl(this);
        
        contatos = new HashMap<String, Contato>();
        tabelaclientes = new DefaultTableModel();
        tabelaclientes.addColumn("Apelido");
        tabelaclientes.addColumn("Nome");
        
        disponibilizarServicos(enderecoCliente, portaCliente, apelido);
        int rsp = conectar(endereco, portaServidor, enderecoCliente, apelido, nome, portaCliente);

        
        if (rsp == 0) {
            telaCliente.isConected(true);
        } else {
            telaCliente.isConected(false);
        }

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

    private int conectar(String endereco, String portaServ, String enderecoCliente, String apelido, String nome, String portaCliente) {
        int rsp = 1;
        try {
            enderecoServidor = endereco;
            portaServidor = portaServ;
            apelidoOrigem = apelido;

            servidorInterface = (ServidorInterface) Naming.lookup("rmi://" + enderecoServidor + ":" + portaServidor + "/servidorEco");
            rsp = servidorInterface.Conectar(apelidoOrigem, nome, enderecoCliente, portaCliente);

        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsp;
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

    public void desconectar(String enderecoCliente, String apelido, String portaCliente) {
        try {
            servidorInterface = (ServidorInterface) Naming.lookup("rmi://" + enderecoServidor + ":" + portaServidor + "/servidorEco");
            servidorInterface.Desconectar(apelido, enderecoCliente, portaCliente);
            telaCliente.isConected(false);
            UnicastRemoteObject.unexportObject(clienteInterface, true);

        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void atualizaTabelaContatos() {
        tabelaclientes.setRowCount(0);

        for (Contato contato : contatos.values()) {
            String[] vetor = {contato.getApelido(), contato.getNome()};
            tabelaclientes.addRow(vetor);
        }
    }
    
    public void atualizarContatos(String apelido, String nome) {
        //contatos = new HashMap<>();
        
        Contato novoCliente = new Contato(apelido, nome);
        contatos.put(novoCliente.getHash(), novoCliente);
        //enviarListaClientes();
        atualizaTabelaContatos();
    }
    
    public DefaultTableModel getTabelaClientes() {
        return tabelaclientes;
    }
}
