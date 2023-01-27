package br.LeonardoCSilva.Servidor;

public class TratadorDeExcecao implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {

        System.out.println("ocorreu uma excecao na thread "+t.getName()+": "+e.getMessage());
    }
}
