package br.LeonardoCSilva.Servidor;

import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JoinWsDb implements Runnable {

    private final Future<String> respostaC2ws;
    private final Future<String> respostaC2db;
    private final PrintStream respostaCliente;

    public JoinWsDb(Future<String> respostaC2ws, Future<String> respostaC2db, PrintStream respostaCliente) {

        this.respostaC2ws = respostaC2ws;
        this.respostaC2db = respostaC2db;
        this.respostaCliente = respostaCliente;
    }

    @Override
    public void run() {
        System.out.println("esperando resultados do WS e BD");
        try {
            String respostaUnida= this.respostaC2ws.get(15, TimeUnit.SECONDS);
            respostaUnida+="\n\ndados: "+ this.respostaC2db.get(15, TimeUnit.SECONDS);
            respostaCliente.println("resultado do c2:\n "+respostaUnida);

        } catch (InterruptedException | ExecutionException e) {
            respostaCliente.println("Erro enquanto executava c2");
            //como ocorreu um erro, cancela ambas as threads caso estejam executando
            respostaC2ws.cancel(true);
            respostaC2db.cancel(true);
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            respostaCliente.println("Timeout enquanto executava c2");
            //como ocorreu um erro, cancela ambas as threads caso estejam executando
            respostaC2ws.cancel(true);
            respostaC2db.cancel(true);
            throw new RuntimeException(e);
        }
        System.out.println("terminou de executar a juncao");
    }
}
