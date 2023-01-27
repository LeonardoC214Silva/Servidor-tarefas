package br.LeonardoCSilva.cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTarefas {

    public static void main(String[] args) throws IOException {
        //declarando socket do cliente
        //local host pois nos testes ele é testado na mesma maquina
        Socket socket = new Socket("localhost",12345);


        //definindo thread para envio de mensagens
        Thread enviarDados = new Thread(() -> {
            try{
                //variavel para enviar dados para o servidor
                PrintStream saida = new PrintStream(socket.getOutputStream());
                //scanner para pegar dados do teclado
                Scanner teclado = new Scanner(System.in);
                while (teclado.hasNextLine()) {
                    String entrada = teclado.nextLine();
                    //verificando se foi digitado algo ou se a linha estra em branco/é só espaços
                    if (entrada.trim().equals("")) break;

                    //enviando string digitada
                    saida.println(entrada);
                    if(entrada.trim().equals("fim"))break;
                }
                teclado.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        enviarDados.start();

        //thread que irá tratar as respostas do servidor
        new Thread(() -> {
            try{
                //identico à entrada do teclado, porem referente a resposta do servidor
                Scanner respostaServidor = new Scanner(socket.getInputStream());
                while (respostaServidor.hasNextLine()) {
                    String linha = respostaServidor.nextLine();
                    System.out.println(linha);
                }
                respostaServidor.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        try {
            //espera a thread de envio terminar antes de avançar para o restante do código
            enviarDados.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        socket.close();
    }
}
