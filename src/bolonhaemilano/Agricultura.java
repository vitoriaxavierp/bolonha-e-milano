
package bolonhaemilano;

public class Agricultura {

    public int processarColheita(Cidade cidade) {

        if (cidade.isColheitaFeita()) {
            return -1;
        }

        int hectares = Math.min(
            cidade.getTerras(),
            cidade.getServos() * 5
        );

        int sementes = hectares / 2;

        if (sementes > cidade.getGraos()) {
            sementes = cidade.getGraos();
            hectares = sementes * 2;
        }

        cidade.removerGraos(sementes);

        int producao = hectares * 2;

        cidade.adicionarGraos(producao);

        cidade.marcarColheitaFeita();

        return hectares;
    }

    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (percentual < 20 || percentual > 80) {
            return false;
        }

        int alimento = cidade.getGraos() * percentual / 100;

        cidade.removerGraos(alimento);

        int necessidade = cidade.getServos() / 5;

        if (alimento >= necessidade) {
            int crescimento = cidade.getServos() * 5 / 100;
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimento;
            cidade.removerServos(deficit);
        }

        return true;
    }
}
