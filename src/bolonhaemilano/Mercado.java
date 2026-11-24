
package bolonhaemilano;

public class Mercado {

    private static final int PRECO_GRAO = 2;
    private static final int PRECO_TERRA = 1;

    public int getPrecoGrao() {
        return PRECO_GRAO;
    }

    public int getPrecoTerra() {
        return PRECO_TERRA;
    }

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        int custo = quantidade * PRECO_GRAO;

        if (cidade.removerFlorins(custo)) {
            cidade.adicionarGraos(quantidade);
            return true;
        }

        return false;
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        if (cidade.removerGraos(quantidade)) {
            cidade.adicionarFlorins(quantidade * PRECO_GRAO);
            return true;
        }

        return false;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        int custo = quantidade * PRECO_TERRA;

        if (cidade.removerFlorins(custo)) {
            cidade.adicionarTerras(quantidade);
            return true;
        }

        return false;
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        if (cidade.removerTerras(quantidade)) {
            cidade.adicionarFlorins(quantidade * PRECO_TERRA);
            return true;
        }

        return false;
    }

}
