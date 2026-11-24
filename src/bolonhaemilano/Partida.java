
package bolonhaemilano;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    public static final int MAXIMO_JOGADORES = 4;
    private static final int ANO_FINAL = 1410;

    private List<Cidade> cidades;
    private int indiceAtual;

    public Partida() {
        cidades = new ArrayList<>();
        indiceAtual = 0;
    }

    public boolean adicionarCidade(Cidade cidade) {
        if (cidades.size() >= MAXIMO_JOGADORES || cidade == null) {
            return false;
        }

        cidades.add(cidade);
        return true;
    }

    public List<Cidade> getCidades() {
        return new ArrayList<>(cidades);
    }

    public Cidade getCidadeAtual() {
        if (cidades.isEmpty()) {
            return null;
        }

        return cidades.get(indiceAtual);
    }

    public int getAnoAtual() {
        if (cidades.isEmpty()) {
            return 1400;
        }

        return cidades.get(0).getAnoAtual();
    }

    public int getAnoFinal() {
        return ANO_FINAL;
    }

    public boolean passarTurno() {
        if (cidades.isEmpty()) {
            return false;
        }

        indiceAtual++;

        if (indiceAtual >= cidades.size()) {
            indiceAtual = 0;
            return true;
        }

        return false;
    }

    public List<Cidade> encerrarAno(Nobreza nobreza) {
        List<Cidade> promovidas = new ArrayList<>();

        for (Cidade cidade : cidades) {
            cidade.avancarAno();

            if (nobreza.verificarPromocao(cidade)) {
                promovidas.add(cidade);
            }
        }

        return promovidas;
    }

    public boolean terminou() {
        for (Cidade cidade : cidades) {
            if (cidade.getDono().ehMonarca()) {
                return true;
            }
        }

        return getAnoAtual() > ANO_FINAL;
    }

    public Cidade determinarVencedor(Nobreza nobreza) {
        if (cidades.isEmpty()) {
            return null;
        }

        Cidade vencedor = cidades.get(0);

        for (int i = 1; i < cidades.size(); i++) {
            Cidade candidata = cidades.get(i);

            int nivelVencedor = vencedor.getDono().getNivelTitulo();
            int nivelCandidato = candidata.getDono().getNivelTitulo();

            if (nivelCandidato > nivelVencedor) {
                vencedor = candidata;

            } else if (nivelCandidato == nivelVencedor) {
                int prestigioVencedor = nobreza.calcularPrestigio(vencedor);
                int prestigioCandidato = nobreza.calcularPrestigio(candidata);

                if (prestigioCandidato > prestigioVencedor) {
                    vencedor = candidata;
                }
            }
        }

        return vencedor;
    }
}
