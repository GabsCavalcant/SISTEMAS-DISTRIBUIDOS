/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author bv3034666
 */
public class Servidor {

    public static void main(String[] args) {

        try {

            ServerSocket ssServer = new ServerSocket(8189);

            System.out.println("Aguardando Conexao");
            Socket sIncoming = ssServer.accept();
            System.out.println("Chegou Alguem!");

            InputStream inStream = sIncoming.getInputStream();
            Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);

            OutputStream outStram = sIncoming.getOutputStream();
            PrintWriter out = new PrintWriter(outStram, true, StandardCharsets.UTF_8);

            boolean fim = false;

            while (!fim && in.hasNextLine()) {
                String linha = in.nextLine();
                System.out.println("Cliente: " + linha);

                if (linha.equalsIgnoreCase("THAU")) {
                    fim = true;
                    System.out.println("Foi um prazer falar contigo");
                }
            }
            out.println("ok");

        } catch (IOException e) {
            e.getMessage();
        }

    }
}
