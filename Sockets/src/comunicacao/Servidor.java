package comunicacao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bv3034666
 */
public class Servidor {

    // Lista compartilhada para broadcast
    private static final List<PrintWriter> clientesConectados = new ArrayList<>();

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        try {

            ServerSocket ssServer = new ServerSocket(8189);

            System.out.println("Aguardando Conexao");

            // Loop para aceitar múltiplos clientes, cada um em sua própria thread
            while (true) {
                Socket sIncoming = ssServer.accept();
                System.out.println("Chegou Alguem!");

                // Cria e inicia a thread para atender esse cliente
                Thread t = new Thread(new ClienteHandler(sIncoming));
                t.start();
            }

        } catch (IOException e) {
            e.getMessage();
        }
    }

    // Envia mensagem para todos os clientes conectados, identificando o remetente
    private static synchronized void broadcast(String nomeCliente, String mensagem) {
        for (PrintWriter out : clientesConectados) {
            out.println("[" + nomeCliente + "]: " + mensagem);
        }
    }

    private static synchronized void adicionarCliente(PrintWriter out) {
        clientesConectados.add(out);
    }

    private static synchronized void removerCliente(PrintWriter out) {
        clientesConectados.remove(out);
    }

    // ---------------------------------------------------------------
    // Classe interna que atende UM cliente — mesma lógica original
    // ---------------------------------------------------------------
    static class ClienteHandler implements Runnable {

        private final Socket sIncoming;
        private String nomeCliente;  // agora vem do cliente, como no exemplo do professor

        public ClienteHandler(Socket sIncoming) {
            this.sIncoming = sIncoming;
        }

        @Override
        public void run() {
            try {
                InputStream inStream = sIncoming.getInputStream();
                Scanner in = new Scanner(inStream, StandardCharsets.UTF_8);

                OutputStream outStram = sIncoming.getOutputStream();
                PrintWriter out = new PrintWriter(outStram, true, StandardCharsets.UTF_8);

                adicionarCliente(out);

                // Primeira mensagem recebida é o nome do cliente (igual ao professor)
                nomeCliente = in.nextLine();
                System.out.println(nomeCliente + " conectou-se.");

                while (in.hasNextLine()) {

                    if (in.hasNextLine()) {
                        String escolha = in.nextLine();
                        System.out.print("O Cliente digitou:  " + escolha);

                        if (escolha.equals("1")) {
                            calcular(in, out);
                        } else if (escolha.equals("2")) {
                            stringCalc(in, out);
                        } else {
                            // Qualquer outra mensagem de texto → broadcast para todos
                            broadcast(nomeCliente, escolha);
                        }
                    }
                }

                removerCliente(out);

            } catch (IOException e) {
                e.getMessage();
            }
        }

        // Mantido exatamente igual ao original
        private static void calcular(Scanner in, PrintWriter out) {
            if (in.hasNextLine()) {
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

        // Mantido exatamente igual ao original
        private static void stringCalc(Scanner in, PrintWriter out) {
            if (in.hasNextLine()) {
                String dados = in.nextLine();
                String[] partes = dados.split(";");
                String frase = partes[0];
                String palavra = partes[1];

                int posicao = frase.indexOf(palavra);

                if (posicao != -1) {
                    out.println("Palavra encontrada na posicao :" + posicao);
                } else {
                    out.println("Palavra não encontrada!!");
                }
            }
        }
    }
}