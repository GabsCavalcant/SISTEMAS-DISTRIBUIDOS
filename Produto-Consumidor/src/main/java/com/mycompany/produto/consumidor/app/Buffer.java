package com.mycompany.produto.consumidor.app;

/**
 * Buffer circular sincronizado (cap. 23 – Java Como Programar).
 * Usa wait() / notifyAll() para coordenar Produtor e Consumidor.
 */
public class Buffer {

    private final int[] buffer = new int[5];
    private int posEscrita = 0;  // próximo índice a escrever
    private int posLeitura = 0;  // próximo índice a ler
    private int ocupados   = 0;  // itens disponíveis

    /** Produtor chama este método. Bloqueia se buffer cheio. */
    public synchronized void escrever(int valor) throws InterruptedException {
        while (ocupados == buffer.length) {
            System.out.println("[Produtor] buffer CHEIO – aguardando...");
            wait();          // libera o lock e aguarda notificação
        }
        buffer[posEscrita] = valor;
        posEscrita = (posEscrita + 1) % buffer.length;
        ocupados++;
        System.out.printf("[Produtor] escreveu %3d | ocupados: %d%n", valor, ocupados);
        notifyAll();     // acorda quem estiver esperando (Consumidor)
    }

    /** Consumidor chama este método. Bloqueia se buffer vazio. */
    public synchronized int ler() throws InterruptedException {
        while (ocupados == 0) {
            System.out.println("[Consumidor] buffer VAZIO – aguardando...");
            wait();
        }
        int valor = buffer[posLeitura];
        posLeitura = (posLeitura + 1) % buffer.length;
        ocupados--;
        System.out.printf("[Consumidor] leu     %3d | ocupados: %d%n", valor, ocupados);
        notifyAll();     // acorda o Produtor se estava bloqueado
        return valor;
    }
}
