/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorInterface extends Remote {

    public void somar(float a, float b) throws java.rmi.RemoteException;
    // Receber mensagem do cliente
    // Se apelidoDestino == "TODOS", a mensagem deverá ser enviada a todos os clientes conectados, caso contrário,
    // somente ao cliente informado
    // Caso o retorno (int) seja igual a 0, significa que a mensagem foi enviada

    public int ReceberMensagemCliente(String apelidoOrigem, String apelidoDestino, String mensagem) throws RemoteException;

    // Conexão de um novo cliente
    // Se o retorno (int) for igual a 0, significa que o cliente foi aceito, caso contrário, não.

    public int Conectar(String apelido, String nome, String ipCliente, String portaCliente) throws RemoteException;

    // Desconexão de um cliente

    public void Desconectar(String apelido, String ipCliente, String portaCliente) throws RemoteException;

}
