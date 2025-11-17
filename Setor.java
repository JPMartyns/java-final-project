package src.POO.teste_final;

/**
 * Modela um setor do estádio, com um identificador, preço base e uma
 * grelha de lugares. É responsável por gerir a disponibilidade dos assentos.
 */
public class Setor {

    // --- CONSTANTES ---

    // Usar constantes evita "números mágicos" no código e define a capacidade
    // num único lugar.
    private static final int FILAS = 5;
    private static final int COLUNAS = 5;

    // --- ATRIBUTOS ---

    private String id;              // O identificador do setor (ex: "A", "B", "C", "D").
    private double precoBase;       // O preço base de um bilhete para este setor.
    private boolean[][] lugares;    // Matriz para representar os lugares.

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Setor.
     * Inicializa a grelha de lugares com base nas constantes de FILAS e COLUNAS.
     *
     * @param id O identificador do setor (ex: "A").
     * @param precoBase O preço base do bilhete para este setor.
     */
    public Setor(String id, double precoBase) {
        this.id = id;
        this.precoBase = precoBase;
        // Cria a matriz usando as constantes.
        this.lugares = new boolean[FILAS][COLUNAS];
    }

    // --- GETTERS E SETTERS ---

    /**
     * Devolve a capacidade total do setor, calculada a partir das constantes.
     * Isto cumpre o requisito de ter uma "capacidade" acessível.
     * @return A capacidade total do setor (25).
     */
    public int getCapacidade() {
        return FILAS * COLUNAS;
    }

    /**
     * Devolve o ID do setor.
     * @return O identificador do setor.
     */
    public String getId() {
        return id;
    }

    /**
     * Devolve o preço base do bilhete para este setor.
     * @return O preço do bilhete.
     */
    public double getPrecoBase() {
        return precoBase;
    }

    /**
     * Verifica o número total de lugares disponíveis no setor.
     * @return O número de lugares livres (não ocupados).
     */
    public int verificarDisponibilidade() {
        int lugaresLivres = 0;
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (!this.lugares[i][j]) {
                    lugaresLivres++;
                }
            }
        }
        return lugaresLivres;
    }

    /**
     * Exibe uma representação visual da grelha de lugares do setor.
     */
    public void mostrarLugares() {
        System.out.println("\nEstado atual do Setor " + this.id + ":");
        System.out.println("(Legenda: [ ] = Livre, [X] = Ocupado)");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (this.lugares[i][j]) {
                    System.out.print("[X] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Ocupa um lugar específico na grelha do setor.
     * @param fila A fila do lugar (0 a 4).
     * @param coluna A coluna do lugar (0 a 4).
     */
    public void ocuparLugar(int fila, int coluna) {
        if (fila >= 0 && fila < FILAS && coluna >= 0 && coluna < COLUNAS) {
            this.lugares[fila][coluna] = true; // Define o lugar como ocupado
        }
    }

    /**
     * Verifica se um lugar específico já está ocupado.
     * @param fila A fila do lugar (0 a 4).
     * @param coluna A coluna do lugar (0 a 4).
     * @return true se o lugar estiver ocupado, false caso contrário.
     */
    public boolean isLugarOcupado(int fila, int coluna) {
        if (fila >= 0 && fila < FILAS && coluna >= 0 && coluna < COLUNAS) {
            return this.lugares[fila][coluna];
        }
        return true; // Considera ocupado se as coordenadas forem inválidas
    }
}