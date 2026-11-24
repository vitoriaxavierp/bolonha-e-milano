
package bolonhaemilano;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Scanner scanner;
    private boolean emExecucao;
    private Partida partida;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;
    private Nobreza nobreza;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        partida = null;
        cidade = null;
        mercado = new Mercado();
        agricultura = new Agricultura();
        governo = new Governo();
        construcoes = new Construcoes();
        nobreza = new Nobreza();
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
        System.out.println("\n========== BOLONHA E MILANO ==========");
        System.out.println("Ano: " + (partida == null ? "-" : partida.getAnoAtual()));
        System.out.println("Jogador da vez: " +
                (cidade == null ? "-" : cidade.getDono().getNome()));

        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificacao");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
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
                if (exigirJogoEmAndamento()) {
                    abrirMercado();
                }
                break;

            case 4:
                if (exigirJogoEmAndamento()) {
                    abrirAgricultura();
                }
                break;

            case 5:
                if (exigirJogoEmAndamento()) {
                    abrirGoverno();
                }
                break;

            case 6:
                if (exigirJogoEmAndamento()) {
                    abrirConstrucoes();
                }
                break;

            case 7:
                if (exigirJogoEmAndamento()) {
                    encerrarTurno();
                }
                break;

            case 8:
                if (exigirJogoEmAndamento()) {
                    exibirClassificacao();
                }
                break;

            case 9:
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

    private boolean exigirJogoEmAndamento() {
        if (partida == null || partida.terminou()) {
            System.out.println("Nenhuma partida em andamento.");
            return false;
        }

        return true;
    }

    private void iniciarNovoJogo() {
        System.out.print("\nQuantos jogadores participarao? (1 a 4): ");

        int quantidade = lerOpcao();

        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) {
            System.out.println("Quantidade invalida. Serao utilizados 2 jogadores.");
            quantidade = 2;
        }

        partida = new Partida();

        String[] cidades = {
            "Bolonha",
            "Milano",
            "Florença",
            "Veneza"
        };

        for (int i = 0; i < quantidade; i++) {
            System.out.println("\nJogador " + (i + 1));

            System.out.print("Nome do governante: ");
            String nomeJogador = scanner.nextLine().trim();

            Jogador jogador = new Jogador(nomeJogador);
            Cidade novaCidade = new Cidade(cidades[i], jogador);

            partida.adicionarCidade(novaCidade);
        }

        cidade = partida.getCidadeAtual();

        System.out.println("\nPartida iniciada!");
        cidade.exibirStatus();
    }

    private void exibirStatus() {
        if (exigirJogoEmAndamento()) {
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

        if (hectares == -1) {
            System.out.println("A colheita ja foi realizada neste ano.");
        } else {
            System.out.println("Colheita processada!");
            System.out.println("Hectares cultivados: " + hectares);
            System.out.println("Graos disponiveis: " + cidade.getGraos());
        }
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

    private void abrirGoverno() {
        boolean noGoverno = true;

        while (noGoverno) {
            System.out.println("\n========== GOVERNO ==========");
            System.out.println("Florins: " + cidade.getFlorins());
            System.out.println("Taxa de imposto: " + cidade.getTaxaImposto() + "%");
            System.out.println("Nivel de justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    arrecadarImpostos();
                    break;
                case 2:
                    definirTaxaImposto();
                    break;
                case 3:
                    definirJustica();
                    break;
                case 0:
                    noGoverno = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    private void arrecadarImpostos() {
        int arrecadado = governo.arrecadarImpostos(cidade);

        if (arrecadado == -1) {
            System.out.println("Os impostos ja foram arrecadados neste ano.");
        } else {
            System.out.println("Impostos arrecadados: " + arrecadado + " florins.");
            System.out.println("Saldo atual: " + cidade.getFlorins() + " florins.");
        }
    }

    private void definirTaxaImposto() {
        System.out.print("Digite a taxa de imposto (0 a 50): ");

        int taxa = lerOpcao();

        if (governo.definirTaxaImposto(cidade, taxa)) {
            System.out.println("Taxa de imposto atualizada!");
        } else {
            System.out.println("Taxa invalida. Utilize um valor entre 0 e 50.");
        }
    }

    private void definirJustica() {
        System.out.println("Niveis disponiveis: Justa, Moderada ou Severa");
        System.out.print("Digite o nivel de justica: ");

        String nivel = scanner.nextLine().trim();

        if (governo.definirJustica(cidade, nivel)) {
            System.out.println("Nivel de justica atualizado!");
        } else {
            System.out.println("Nivel invalido.");
        }
    }

    private void abrirConstrucoes() {
        boolean naConstrucoes = true;

        while (naConstrucoes) {
            System.out.println("\n========== CONSTRUCOES ==========");
            System.out.println("Florins: " + cidade.getFlorins());
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Soldados: " + cidade.getSoldados());
            System.out.println("Construcoes: " + cidade.getConstrucoes());
            System.out.println("1 - Recrutar soldados");
            System.out.println("2 - Construir");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    recrutarSoldados();
                    break;
                case 2:
                    construir();
                    break;
                case 0:
                    naConstrucoes = false;
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }
        }
    }

    private void recrutarSoldados() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (construcoes.recrutarSoldados(cidade, quantidade)) {
                System.out.println("Soldados recrutados com sucesso!");
            } else {
                System.out.println("Recursos insuficientes para recrutar soldados.");
            }
        }
    }

    private void construir() {
        int quantidade = lerQuantidade();

        if (quantidade > 0) {
            if (construcoes.construir(cidade, quantidade)) {
                System.out.println("Construcoes realizadas com sucesso!");
            } else {
                System.out.println("Florins insuficientes para construir.");
            }
        }
    }

    private void encerrarTurno() {
        System.out.println("\nTurno encerrado por " + cidade.getDono().getNome() + ".");

        boolean fimDoAno = partida.passarTurno();

        if (fimDoAno) {
            System.out.println("\n========== FIM DO ANO ==========");

            List<Cidade> promovidas = partida.encerrarAno(nobreza);

            for (Cidade promovida : promovidas) {
                System.out.println(promovida.getDono().getNome()
                        + " foi promovido(a) a "
                        + promovida.getDono().getTitulo() + "!");
            }

            exibirClassificacao();

            if (partida.terminou()) {
                encerrarPartida();
                return;
            }
        }

        cidade = partida.getCidadeAtual();

        System.out.println("\nAgora e a vez de "
                + cidade.getDono().getNome()
                + " - " + cidade.getNome());
    }

    private void exibirClassificacao() {
        System.out.println("\n========== CLASSIFICACAO ==========");
        System.out.println("Ano: " + partida.getAnoAtual());

        for (Cidade cidadeLista : partida.getCidades()) {
            Jogador jogadorLista = cidadeLista.getDono();

            int prestigio = nobreza.calcularPrestigio(cidadeLista);
            int proximo = nobreza.prestigioParaProximoTitulo(jogadorLista);

            System.out.println("\nCidade: " + cidadeLista.getNome());
            System.out.println("Governante: " + jogadorLista.getNome());
            System.out.println("Titulo: " + jogadorLista.getTitulo());
            System.out.println("Prestigio: " + prestigio);

            if (proximo == -1) {
                System.out.println("Ja alcancou o titulo maximo!");
            } else {
                System.out.println("Faltam " + Math.max(0, proximo - prestigio)
                        + " pontos para o proximo titulo.");
            }
        }

        System.out.println("===================================");
    }

    private void encerrarPartida() {
        Cidade vencedor = partida.determinarVencedor(nobreza);

        System.out.println("\n========== FIM DE JOGO ==========");

        if (vencedor != null) {
            System.out.println("Vencedor: " + vencedor.getDono().getNome());
            System.out.println("Cidade: " + vencedor.getNome());
            System.out.println("Titulo: " + vencedor.getDono().getTitulo());
            System.out.println("Prestigio: " + nobreza.calcularPrestigio(vencedor));
        }

        System.out.println("=================================");

        partida = null;
        cidade = null;
    }

    private void exibirSobre() {
        System.out.println("\nBolonha e Milano e um jogo de estrategia economica medieval.");
        System.out.println("Administre sua cidade, desenvolva sua economia e conquiste titulos.");
    }
}
