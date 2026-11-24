
package bolonhaemilano;

public class Cidade {

    private String nome;
    private int anoAtual;
    private Jogador dono;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
    }

    public void avancarAno() {
        anoAtual++;
    }

    public void exibirStatus() {
        System.out.println("\n========== STATUS DA CIDADE ==========");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getNome());
        System.out.println("Titulo: " + dono.getTitulo());
        System.out.println("Ano atual: " + anoAtual);
        System.out.println("======================================\n");
    }

}
