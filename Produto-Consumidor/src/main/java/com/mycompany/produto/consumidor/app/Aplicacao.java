package com.mycompany.produto.consumidor.app;

/**
 * Ponto de entrada: cria o Buffer compartilhado, instancia as
 * threads Produtora e Consumidora e aguarda ambas terminarem (join).
 */
public class Aplicacao {

    public static void main(String[] args) throws InterruptedException {

        Buffer     buf  = new Buffer();
        Thread     tProd = new Thread(new Produto(buf),   "Produtor-1");
        Thread     tCons = new Thread(new Consumidor(buf), "Consumidor-1");

        tProd.start();
        tCons.start();

        tProd.join();   // main aguarda o Produtor finalizar
        tCons.join();   // main aguarda o Consumidor finalizar

        System.out.println("\nAmbas as threads finalizaram. Programa encerrado.");
    }
}
