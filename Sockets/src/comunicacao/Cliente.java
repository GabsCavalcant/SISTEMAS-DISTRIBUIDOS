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
public class Cliente {
    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        //crio o socket para instanciar o cliente
        try(Socket socket = new Socket("localhost", 8189)) {
           //enviar mensagem para o servidor.
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
          
          //para recber mensagem do servidor
          Scanner in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
          
          System.out.println("Entre com o (1) Se quiser acessar a calculadora, ou (2) Para buscar String");
            
         String servico = teclado.nextLine(); 
         
           out.println(servico);
           
           if(servico.equals("1")){
           System.out.println("Digite o calculo (Ex: 10 * 5): ");
           }else if(servico.equals("2")){
               
               System.out.println("Pesquise uma String em uma frase, usando a frase separado por ;, ex: {frase;palavra}");
           }else{
               System.out.println("opção inexistente!");
               out.println("ok");
           }
           
        
           String dados = teclado.nextLine();
           
           out.println(dados);
           
            if(in.hasNextLine()){
                System.out.println("RESPOSTA DO SERVIDOR: " + in.nextLine());
            }
            
        } catch (IOException e) {
            e.getMessage();
        }
    
    
}}
