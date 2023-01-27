package br.LeonardoCSilva.Servidor;

import java.io.PrintStream;

public class Comando1 implements Runnable{
    private PrintStream resposta;

    public Comando1(PrintStream resposta) {
        this.resposta = resposta;
    }

    @Override
    public void run() {
        resposta.println("confirma C1");
        //codigo executado quando chamado esse comando
    }
}
