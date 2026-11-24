
package bolonhaemilano;

public class Testes {

    private static int aprovados = 0;
    private static int reprovados = 0;

    public static void main(String[] args) {

        System.out.println("===== TESTES BOLONHA E MILANO =====");

        testarCidade();
        testarMercado();
        testarAgricultura();
        testarGoverno();
        testarConstrucoes();
        testarJogadorENobreza();
        testarPartida();

        System.out.println("\n===================================");
        System.out.println("Aprovados: " + aprovados
                + " | Reprovados: " + reprovados);

        if (reprovados > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descricao, boolean condicao) {
        if (condicao) {
            aprovados++;
            System.out.println("[OK] " + descricao);
        } else {
            reprovados++;
            System.out.println("[FALHA] " + descricao);
        }
    }

    private static Cidade novaCidade() {
        return new Cidade("Bolonha", new Jogador("Teste"));
    }

    private static void testarCidade() {
        System.out.println("\n-- Cidade --");

        Cidade cidade = novaCidade();

        verificar("cidade nasce com 1000 florins",
                cidade.getFlorins() == 1000);

        verificar("cidade nasce no ano 1400",
                cidade.getAnoAtual() == 1400);

        int saldoInicial = cidade.getFlorins();

        boolean removeu = cidade.removerFlorins(1500);

        verificar("removerFlorins acima do saldo devolve false",
                !removeu);

        verificar("removerFlorins acima do saldo nao altera o saldo",
                cidade.getFlorins() == saldoInicial);

        cidade.marcarColheitaFeita();
        cidade.avancarAno();

        verificar("avancarAno soma 1 ao ano",
                cidade.getAnoAtual() == 1401);

        verificar("avancarAno libera a colheita de novo",
                !cidade.isColheitaFeita());

        verificar("cidade comeca com 200 servos",
                cidade.getServos() == 200);

        verificar("cidade comeca com 10000 hectares",
                cidade.getTerras() == 10000);
    }

    private static void testarMercado() {
        System.out.println("\n-- Mercado --");

        Cidade cidade = novaCidade();
        Mercado mercado = new Mercado();

        boolean comprou = mercado.comprarGraos(cidade, 100);

        verificar("comprar 100 graos da certo", comprou);

        verificar("comprar 100 graos custa 200 florins",
                cidade.getFlorins() == 800);

        verificar("comprar 100 graos soma 100 graos",
                cidade.getGraos() == 600);

        boolean quantidadeNegativa =
                mercado.comprarGraos(cidade, -10);

        verificar("comprar quantidade negativa e recusado",
                !quantidadeNegativa);

        cidade.removerFlorins(800);

        boolean semFlorins = mercado.comprarGraos(cidade, 100);

        verificar("comprar sem florins suficientes e recusado",
                !semFlorins);
    }

    private static void testarAgricultura() {
        System.out.println("\n-- Agricultura --");

        Cidade cidade = novaCidade();
        Agricultura agricultura = new Agricultura();

        int hectares = agricultura.processarColheita(cidade);

        verificar("primeira colheita cultiva 1000 hectares",
                hectares == 1000);

        verificar("primeira colheita deixa 2000 graos",
                cidade.getGraos() == 2000);

        int segundaColheita = agricultura.processarColheita(cidade);

        verificar("segunda colheita no mesmo ano devolve -1",
                segundaColheita == -1);

        verificar("distribuir 10% e recusado",
                !agricultura.distribuirAlimentos(cidade, 10));
    }

    private static void testarGoverno() {
        System.out.println("\n-- Governo --");

        Cidade cidade = novaCidade();
        Governo governo = new Governo();

        verificar("taxa de 60% e recusada",
                !governo.definirTaxaImposto(cidade, 60));

        verificar("justica Branda e recusada",
                !governo.definirJustica(cidade, "Branda"));

        governo.definirJustica(cidade, "Severa");

        int arrecadado = governo.arrecadarImpostos(cidade);

        verificar("200 servos, 10%, Severa arrecada 26 florins",
                arrecadado == 26);

        verificar("segunda arrecadacao no mesmo ano devolve -1",
                governo.arrecadarImpostos(cidade) == -1);
    }

    private static void testarConstrucoes() {
        System.out.println("\n-- Construcoes --");

        Cidade cidade = novaCidade();
        Construcoes construcoes = new Construcoes();

        cidade.removerServos(150);

        boolean recrutou = construcoes.recrutarSoldados(cidade, 100);

        verificar("recrutar 100 soldados com so 50 servos e recusado",
                !recrutou);

        verificar("recrutamento recusado nao debita florins",
                cidade.getFlorins() == 1000);

        boolean construiu = construcoes.construir(cidade, 10);

        verificar("construir 10 da certo", construiu);

        verificar("construir 10 custa 200 florins",
                cidade.getFlorins() == 800);
    }

    private static void testarJogadorENobreza() {
        System.out.println("\n-- Jogador e Nobreza --");

        Cidade cidade = novaCidade();
        Jogador jogador = cidade.getDono();
        Nobreza nobreza = new Nobreza();

        verificar("jogador comeca como Cavaleiro",
                jogador.getTitulo().equals("Cavaleiro"));

        verificar("prestigio inicial e 300",
                nobreza.calcularPrestigio(cidade) == 300);

        verificar("prestigio inicial nao promove",
                !nobreza.verificarPromocao(cidade));

        cidade.adicionarConstrucoes(50);

        verificar("com 50 construcoes sobe para Barao",
                nobreza.verificarPromocao(cidade)
                        && jogador.getTitulo().equals("Barão"));

        verificar("sobe no maximo um titulo por chamada",
                jogador.getNivelTitulo() == 1);

        while (!jogador.ehMonarca()) {
            jogador.promover();
        }

        verificar("promover alem de Rei/Rainha e recusado",
                !jogador.promover());
    }

    private static void testarPartida() {
        System.out.println("\n-- Partida --");

        Partida partida = new Partida();
        Nobreza nobreza = new Nobreza();

        Jogador jogador1 = new Jogador("Ana");
        Jogador jogador2 = new Jogador("Bruno");

        Cidade bolonha = new Cidade("Bolonha", jogador1);
        Cidade milano = new Cidade("Milano", jogador2);

        partida.adicionarCidade(bolonha);
        partida.adicionarCidade(milano);

        verificar("primeiro turno e de Bolonha",
                partida.getCidadeAtual() == bolonha);

        verificar("passar turno de Bolonha NAO fecha o ano",
                !partida.passarTurno());

        verificar("agora e a vez de Milano",
                partida.getCidadeAtual() == milano);

        verificar("passar turno de Milano fecha o ano",
                partida.passarTurno());

        partida.encerrarAno(nobreza);

        verificar("encerrarAno avanca todas as cidades",
                bolonha.getAnoAtual() == 1401
                        && milano.getAnoAtual() == 1401);

        verificar("partida ainda nao terminou em 1401",
                !partida.terminou());

        jogador2.promover();

        verificar("vencedor parcial e Milano",
                partida.determinarVencedor(nobreza) == milano);

        partida.adicionarCidade(
                new Cidade("Florença", new Jogador("Carlos"))
        );

        partida.adicionarCidade(
                new Cidade("Veneza", new Jogador("Daniel"))
        );

        boolean quintaCidade = partida.adicionarCidade(
                new Cidade("Roma", new Jogador("Eduardo"))
        );

        verificar("quinta cidade e recusada",
                !quintaCidade);
    }
}
