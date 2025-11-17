package src.POO.teste_final;

// É necessário importar a interface List e a classe ArrayList para usar listas.
import java.util.ArrayList;
import java.util.List;

/**
 * Modela uma equipa de futebol, com os seus dados principais como nome,
 * fundação, plantel de jogadores e treinador.
 */
public class Equipa {

    // --- ATRIBUTOS ---

    private String nome;            // O nome oficial da equipa.
    private String cidade;          // A cidade de origem da equipa.
    private int dataFundacao;       // O ano de fundação da equipa.
    private String treinador;       // O nome do treinador principal.
    private List<String> plantel;   // Uma lista para armazenar os nomes dos 11 jogadores.

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Equipa, inicializando os seus dados principais.
     * O plantel é inicializado como uma lista vazia, para que os jogadores
     * possam ser adicionados posteriormente.
     *
     * @param nome O nome da equipa (ex: "Sporting").
     * @param cidade A cidade da equipa (ex: "Lisboa").
     * @param dataFundacao O ano em que a equipa foi fundada (ex: 1906).
     * @param treinador O nome do treinador.
     */
    public Equipa(String nome, String cidade, int dataFundacao, String treinador) {
        this.nome = nome;
        this.cidade = cidade;
        this.dataFundacao = dataFundacao;
        this.treinador = treinador;
        // Inicializa a lista de jogadores como uma nova ArrayList vazia.
        this.plantel = new ArrayList<>();
    }

    // --- GETTERS E SETTERS ---

    /**
     * Devolve o nome da equipa.
     * @return O nome da equipa.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define ou atualiza o nome da equipa.
     * Valida para garantir que o nome não seja nulo ou vazio.
     * @param nome O novo nome da equipa.
     */
    public void setNome(String nome) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        } else {
            System.out.println("Erro: O nome da equipa não pode ser vazio.");
        }
    }

    /**
     * Devolve a cidade da equipa.
     * @return A cidade da equipa.
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade da equipa.
     * Valida para garantir que a cidade não seja nula ou vazia.
     * @param cidade A nova cidade da equipa.
     */
    public void setCidade(String cidade) {
        if (cidade != null && !cidade.trim().isEmpty()) {
            this.cidade = cidade;
        } else {
            System.out.println("Erro: A cidade da equipa não pode ser vazia.");
        }
    }

    /**
     * Devolve o ano de fundação da equipa.
     * @return O ano de fundação.
     */
    public int getDataFundacao() {
        return dataFundacao;
    }

    /**
     * Define o ano de fundação da equipa.
     * @param dataFundacao O novo ano de fundação.
     */
    public void setDataFundacao(int dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    /**
     * Devolve o nome do treinador.
     * @return O nome do treinador.
     */
    public String getTreinador() {
        return treinador;
    }

    /**
     * Define o nome do treinador.
     * Valida para garantir que o nome não seja nulo ou vazio.
     * @param treinador O novo nome do treinador.
     */
    public void setTreinador(String treinador) {
        if (treinador != null && !treinador.trim().isEmpty()) {
            this.treinador = treinador;
        } else {
            System.out.println("Erro: O nome do treinador não pode ser vazio.");
        }
    }

    /**
     * Devolve a lista de jogadores do plantel.
     * @return Uma lista com os nomes dos jogadores.
     */
    public List<String> getPlantel() {
        return plantel;
    }

    /**
     * Define a lista de jogadores do plantel.
     * @param plantel A nova lista de jogadores.
     */
    public void setPlantel(List<String> plantel) {
        this.plantel = plantel;
    }

    // --- MÉTODOS ADICIONAIS ---

    /**
     * Adiciona um jogador à lista do plantel.
     * @param nomeJogador O nome do jogador a ser adicionado.
     */
    public void adicionarJogador(String nomeJogador) {
        // Valida se o nome do jogador é válido antes de adicionar à lista.
        if (nomeJogador != null && !nomeJogador.trim().isEmpty()) {
            this.plantel.add(nomeJogador.trim());
        } else {
            System.out.println("Erro: Não é possível adicionar um jogador sem nome.");
        }
    }
}
