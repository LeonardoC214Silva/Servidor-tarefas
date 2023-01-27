package br.LeonardoCSilva.Servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;

//callable permite a thread fazer um retorno
public class Comando2WS implements Callable<String> {
    private final PrintStream resposta;

    public Comando2WS(PrintStream respostaCliente) {
        this.resposta = respostaCliente;
    }

    @Override
    public String call() throws InterruptedException {
        //codigo executado quando chamado esse comando
        resposta.println("confirma c2 ws");
        System.out.println("acessou ws");
        Thread.sleep(20000);//sleep simula o tempo de processamento
        return "site";
    }
}
