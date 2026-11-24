
package bolonhaemilano;

public class Construcoes {

    private static final int PRECO_SOLDADO = 5;
    private static final int PRECO_CONSTRUCAO = 20;

    public boolean recrutarSoldados(Cidade cidade, int quantidade) {

        if (quantidade <= 0) {
            return false;
        }

        int custoFlorins = quantidade * PRECO_SOLDADO;
        int servosNecessarios = quantidade * 1;

        if (cidade.getFlorins() < custoFlorins
                || cidade.getServos() < servosNecessarios) {
            return false;
        }

        cidade.removerFlorins(custoFlorins);
        cidade.removerServos(servosNecessarios);
        cidade.adicionarSoldados(quantidade);

        return true;
    }

    public boolean construir(Cidade cidade, int quantidade) {

        if (quantidade <= 0) {
            return false;
        }

        int custo = quantidade * PRECO_CONSTRUCAO;

        if (!cidade.removerFlorins(custo)) {
            return false;
        }

        cidade.adicionarConstrucoes(quantidade);

        return true;
    }
}
