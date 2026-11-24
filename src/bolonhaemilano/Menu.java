
package bolonhaemilano;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        jogador = null;
        cidade = null;
        mercado = new Mercado();
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
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Sobre o jogo");
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
                exibirStatus();
                break;

            case 3:
                if (cidade == null) {
                    System.out.println("\nInicie um jogo antes de acessar o mercado.\n");
                } else {
                    abrirMercado();
                }
                break;

            case 4:
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

    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo foi iniciado ainda.\n");
        } else {
            cidade.exibirStatus();
        }
    }

    private void abrirMercado() {
        int opcao;

        do {
            System.out.println("\n========== MERCADO ==========");
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    comprarGraos();
                    break;

                case 2:
                    venderGraos();
                    break;

                case 3:
                    comprarTerras();
                    break;

                case 4:
                    venderTerras();
                    break;

                case 0:
                    System.out.println("\nVoltando ao menu principal...");
                    break;

                default:
                    System.out.println("\nOpcao invalida!");
                    break;
            }

        } while (opcao != 0);
    }

    private int lerQuantidade() {
        System.out.print("Digite a quantidade: ");

        try {
            int quantidade = Integer.parseInt(scanner.nextLine());

            if (quantidade <= 0) {
                System.out.println("A quantidade deve ser maior que zero.");
                return -1;
            }

            return quantidade;

        } catch (NumberFormatException e) {
            System.out.println("Digite um numero inteiro valido.");
            return -1;
        }
    }

    private void comprarGraos() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.comprarGraos(cidade, quantidade)) {
                System.out.println("Compra de graos realizada!");
            } else {
                System.out.println("Florins insuficientes.");
            }
        }
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.venderGraos(cidade, quantidade)) {
                System.out.println("Venda de graos realizada!");
            } else {
                System.out.println("Graos insuficientes.");
            }
        }
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.comprarTerras(cidade, quantidade)) {
                System.out.println("Compra de terras realizada!");
            } else {
                System.out.println("Florins insuficientes.");
            }
        }
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.venderTerras(cidade, quantidade)) {
                System.out.println("Venda de terras realizada!");
            } else {
                System.out.println("Terras insuficientes.");
            }
        }
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
