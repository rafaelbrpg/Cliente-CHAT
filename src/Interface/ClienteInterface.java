/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClienteInterface extends Remote {

    public void ReceberMensagemServidor(String apelidoOrigem, String mensagem) throws RemoteException;

    // Receber apelido e o nome de um cliente que acabou de se conectar no Servidor

    public void ReceberNovaConexao(String apelido, String nome) throws RemoteException;

    // Receber uma desconexão de cliente

    public void DesconexaoCliente(String apelido, String nome) throws RemoteException;

}
