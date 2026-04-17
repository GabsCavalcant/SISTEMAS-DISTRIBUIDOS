package com.mycompany.produto.consumidor.app;

import java.util.Random;

/** Thread Produtora: gera 20 valores aleatórios e os deposita no Buffer. */
public class Produto implements Runnable {

    private final Buffer buffer;
    private final Random random = new Random();

    public Produto(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                int valor = random.nextInt(100);
                buffer.escrever(valor);
                Thread.sleep(random.nextInt(200)); // simula tempo de produção
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Produtor] finalizou.");
    }
}
