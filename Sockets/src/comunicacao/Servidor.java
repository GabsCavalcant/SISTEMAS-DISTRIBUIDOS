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

/**
 *
 * @author bv3034666
 */
public class Servidor {

    public static void main(String[] args) {
        
         Scanner teclado = new Scanner(System.in);

        try {

            ServerSocket ssServer = new ServerSocket(8189);

            System.out.println("Aguardando Conexao");
            Socket sIncoming = ssServer.accept();
            System.out.println("Chegou Alguem!");
            
            InputStream inStream = sIncoming.getInputStream();
            Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);

            OutputStream outStram = sIncoming.getOutputStream();
            PrintWriter out = new PrintWriter(outStram, true, StandardCharsets.UTF_8);
            
            while(in.hasNextLine()){
        
            if(in.hasNextLine()){
                String escolha = in.nextLine();
                System.out.print("O Cliente digitou:  " + escolha );
                
                
                if(escolha.equals("1")){
                    calcular(in, out);
                }else if (escolha.equals("2")){
                    stringCalc(in,out);
                }
            }

                out.println("ok");
            }
            
            
          
            

        } catch (IOException e) {
            e.getMessage();
        }

        
           
        
    }
            private static void calcular(Scanner in, PrintWriter out){
               if(in.hasNextLine()){
                   String expressao = in.nextLine();
                   System.out.println("Processando: " + expressao);
                   String[] partes = expressao.split(" ");
                   
                   Double n1 = Double.parseDouble(partes[0]);
                   String operador = partes[1];
                   Double n2 = Double.parseDouble(partes[2]);
                   Double resposta = 0.0;
                   
                   switch (operador) {
                       case "+": resposta = n1 + n2; break;
                       case "-": resposta = n1 - n2; break;
                       case "/": resposta = n1 / n2; break;
                       case "*": resposta = n1 * n2; break;
         
                   }
                   out.println("A resposta é! " + resposta);
               }
               
               
               
           } 
            private static void stringCalc(Scanner in,PrintWriter out){
                   if(in.hasNextLine()){
                       String dados = in.nextLine();
                       String[] partes = dados.split(";");
                       String frase = partes[0];
                       String palavra = partes[1];
                       
                       int posicao = frase.indexOf(palavra);
                    
                       if(posicao != -1){
                           out.println("Palavra encontrada na posicao :" + posicao);
                       }else{
                           out.println("Palavra não encontrada!!");
                       }
                   }
               }
}
