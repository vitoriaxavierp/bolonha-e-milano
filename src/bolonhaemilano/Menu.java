
package bolonhaemilano;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        jogador = null;
        cidade = null;
        mercado = new Mercado();
        agricultura = new Agricultura();
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
        System.out.println("4 - Agricultura");
        System.out.println("5 - Sobre o jogo");
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
                    System.out.println("Inicie um jogo primeiro.");
                } else {
                    abrirMercado();
                }
                break;

            case 4:
                if (cidade == null) {
                    System.out.println("Inicie um jogo primeiro.");
                } else {
                    abrirAgricultura();
                }
                break;

            case 5:
                exibirSobre();
                break;

            case 0:
                emExecucao = false;
                System.out.println("Encerrando o jogo...");
                break;

            default:
                System.out.println("Opcao invalida!");
                break;
        }
    }

    private void iniciarNovoJogo() {
        System.out.print("Digite o nome do jogador: ");
        String nomeJogador = scanner.nextLine().trim();

        System.out.print("Digite o nome da cidade: ");
        String nomeCidade = scanner.nextLine().trim();

        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);

        cidade.exibirStatus();
    }

    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("Nenhum jogo foi iniciado ainda.");
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
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }

        } while (opcao != 0);
    }

    private int lerQuantidade() {
        System.out.print("Digite a quantidade: ");

        try {
            int quantidade = Integer.parseInt(scanner.nextLine());

            if (quantidade <= 0) {
                System.out.println("Quantidade invalida.");
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
                System.out.println("Compra realizada!");
            } else {
                System.out.println("Florins insuficientes.");
            }
        }
    }

    private void venderGraos() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.venderGraos(cidade, quantidade)) {
                System.out.println("Venda realizada!");
            } else {
                System.out.println("Graos insuficientes.");
            }
        }
    }

    private void comprarTerras() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.comprarTerras(cidade, quantidade)) {
                System.out.println("Compra realizada!");
            } else {
                System.out.println("Florins insuficientes.");
            }
        }
    }

    private void venderTerras() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (mercado.venderTerras(cidade, quantidade)) {
                System.out.println("Venda realizada!");
            } else {
                System.out.println("Terras insuficientes.");
            }
        }
    }

    private void abrirAgricultura() {
        boolean naAgricultura = true;

        while (naAgricultura) {
            System.out.println("\n========== AGRICULTURA ==========");
            System.out.println("Terras: " + cidade.getTerras());
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos: " + cidade.getGraos());
            System.out.println("1 - Colher");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    colher();
                    break;

                case 2:
                    distribuirAlimentos();
                    break;

                case 0:
                    naAgricultura = false;
                    break;

                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    private void colher() {
        int hectares = agricultura.processarColheita(cidade);

        System.out.println("Colheita processada!");
        System.out.println("Hectares cultivados: " + hectares);
        System.out.println("Graos disponiveis: " + cidade.getGraos());
    }

    private void distribuirAlimentos() {
        System.out.print("Digite o percentual de graos para distribuir (20 a 80): ");

        int percentual = lerOpcao();

        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Alimentos distribuidos!");
            System.out.println("Servos atuais: " + cidade.getServos());
            System.out.println("Graos restantes: " + cidade.getGraos());
        } else {
            System.out.println("Percentual invalido. Utilize valores entre 20 e 80.");
        }
    }

    private void exibirSobre() {
        System.out.println("\nBolonha e Milano e um jogo de estrategia economica medieval.");
    }
}
