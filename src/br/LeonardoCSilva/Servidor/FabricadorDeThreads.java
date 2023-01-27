package br.LeonardoCSilva.Servidor;

import java.util.concurrent.ThreadFactory;

public class FabricadorDeThreads implements ThreadFactory {
    //classe responsavel por iniciar as threads na pool, dando nome, tratamento de erros padrao, etc
    private static int numero =1;
    @Override
    public Thread newThread(Runnable r) {
        Thread thread= new Thread(r,"Thread ServidorTarefa "+(numero++));
        //lida com as excecoes que estouram as threads da pool
        thread.setUncaughtExceptionHandler(new TratadorDeExcecao());
        return thread;
    }
}
