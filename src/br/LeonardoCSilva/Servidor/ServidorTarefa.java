/***
 * Leonardo correia da silva
 * leonardocsilva214@gmail.com
 */
package br.LeonardoCSilva.Servidor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefa {
    private final ServerSocket servidor;
    private final ExecutorService threadPool;
    private final AtomicBoolean rodando;//equivalente a um volatile boolean(volatile , para evitar inconsistencia de valor em cache)
    public static void main(String[] args) throws IOException {
        ServidorTarefa servidor = new ServidorTarefa();
        servidor.aceitarConexoes();
        servidor.parar();







    }
    public void parar() throws IOException {
        rodando.set(false);
        servidor.close();
        threadPool.shutdown();
    }
    public void aceitarConexoes()  {

        //laço de conexões
        while(rodando.get()){
            //socket que representa o cliente que se conectou
            Socket SCliente = null;
            try {
                SCliente = servidor.accept();
                System.out.println("conectou");
                //cria thread para tratar a conexão com o cliente que se conect ou
                DistribuiTarefas distribuiTarefas = new DistribuiTarefas (threadPool,SCliente,this);
                threadPool.execute(distribuiTarefas);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public ServidorTarefa() throws IOException {
        rodando=new AtomicBoolean(true);
        //declarando socket do servidor
       this.servidor = new ServerSocket(12345);
        //definindo tamanho do pool de threads

        //threadPool =  Executors.newFixedThreadPool(20);//pool de tamanho fixo
        this.threadPool =  Executors.newCachedThreadPool( new FabricadorDeThreads());//pool dinamica
    }
}
