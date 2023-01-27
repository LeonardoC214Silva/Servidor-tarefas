package br.LeonardoCSilva.Servidor;

import java.io.PrintStream;
import java.util.concurrent.Callable;

//callable permite a thread fazer um retorno
public class Comando2DB implements Callable<String> {
    private final PrintStream resposta;

    public Comando2DB(PrintStream respostaCliente) {
        this.resposta = respostaCliente;
    }

    @Override
    public String call() throws InterruptedException {
        //codigo executado quando chamado esse comando
        resposta.println("confirma c2 db");
        System.out.println("acessou db");
        Thread.sleep(2000);
        return "dados";
    }
}
