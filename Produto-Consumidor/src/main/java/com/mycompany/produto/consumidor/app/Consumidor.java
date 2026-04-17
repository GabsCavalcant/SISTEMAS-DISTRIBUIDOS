package com.mycompany.produto.consumidor.app;

import java.util.Random;

/** Thread Consumidora: consome 20 valores do Buffer. */
public class Consumidor implements Runnable {

    private final Buffer buffer;
    private final Random random = new Random();

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 20; i++) {
                buffer.ler();
                Thread.sleep(random.nextInt(300)); // consome mais devagar
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Consumidor] finalizou.");
    }
}
