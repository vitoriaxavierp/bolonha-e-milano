
package bolonhaemilano;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        jogador = null;
        cidade = null;
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();

            int opcao = lerOpcao();

            processarOpcao(opcao);
        }

        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println("==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("   Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("\nEscolha uma opcao: ");
    }

    private int lerOpcao() {
        String entrada = scanner.nextLine();

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;

            case 2:
                if (cidade == null) {
                    System.out.println("\nNenhum jogo foi iniciado ainda.\n");
                } else {
                    cidade.exibirStatus();
                }
                break;

            case 3:
                exibirSobre();
                break;

            case 0:
                emExecucao = false;
                System.out.println("\nEncerrando o jogo...");
                break;

            default:
                System.out.println("\nOpcao invalida! Tente novamente.");
                break;
        }
    }

    private void iniciarNovoJogo() {
        System.out.print("\nDigite o nome do jogador: ");
        String nomeJogador = scanner.nextLine().trim();

        System.out.print("Digite o nome da cidade: ");
        String nomeCidade = scanner.nextLine().trim();

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);

        cidade.exibirStatus();
    }

    private void exibirSobre() {
        System.out.println("\n========== SOBRE O JOGO ==========");
        System.out.println("Bolonha e Milano e um jogo de estrategia");
        System.out.println("economica medieval.");
        System.out.println("O objetivo e administrar seus recursos e");
        System.out.println("desenvolver sua cidade.");
        System.out.println("==================================\n");
    }

}
