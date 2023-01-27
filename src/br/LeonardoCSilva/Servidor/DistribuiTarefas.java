package br.LeonardoCSilva.Servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuiTarefas implements Runnable{
    Socket socketCliente;
    ServidorTarefa servidorTarefa;
    private final ExecutorService threadPool;

    public DistribuiTarefas(ExecutorService threadPool, Socket socketCliente, ServidorTarefa servidorTarefa) {
        this.threadPool = threadPool;
        this.socketCliente = socketCliente;
        this.servidorTarefa=servidorTarefa;
    }

    @Override
    public void run() {

            System.out.println("cliente aceito " + socketCliente);
            try {
                Scanner scannerCliente = new Scanner(socketCliente.getInputStream());
                boolean sair =false;
                PrintStream respostaCliente = new PrintStream(socketCliente.getOutputStream());

                //laço que trata mensagens recebidas pelo cliente
                while(scannerCliente.hasNextLine()&& !sair){
                    String comando = scannerCliente.nextLine();
                   //printa o comando recebido caso queira ver
                   // System.out.println(comando);
                    switch (comando){
                        case "c1":

                            Comando1 c1 = new Comando1(respostaCliente);
                            this.threadPool.execute(c1);
                            break;
                        case "c2":
                            Comando2WS c2ws = new Comando2WS(respostaCliente);
                            Comando2DB c2db = new Comando2DB(respostaCliente);
                            Future<String> respostaC2ws = this.threadPool.submit(c2ws);
                            Future<String> respostaC2db = this.threadPool.submit(c2db);
                            this.threadPool.submit(new JoinWsDb(respostaC2ws,respostaC2db,respostaCliente));
                            break;
                        case "c3":
                            respostaCliente.println("confirma C3");
                            break;
                        case "fim":
                            respostaCliente.println("Desligando servidor");
                            sair=true;
                            servidorTarefa.parar();
                            break;
                        default:
                            respostaCliente.println("comando invalido: "+comando);
                            break;
                    }


                }
                scannerCliente.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }




        System.out.println("fim tarefa cliente "+socketCliente);
    }
}
