package src.POO.teste_final;

/**
 * Modela um item vendável, contendo suas informações básicas como
 * identificação, nome, preço e stock.
 */
public class Produto {

    // --- ATRIBUTOS ---

    private int id;                 // O identificador numérico único para o produto.
    private String nome;            // O nome de exibição do produto (ex: "Cachorro Quente").
    private double preco;           // O preço de venda unitário do produto.
    private int quantidadeStock;    // A quantidade de unidades deste produto atualmente disponíveis.

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Produto.
     * O construtor é um método especial chamado quando criamos um novo objeto.
     *
     * @param id O número de identificação para este produto.
     * @param nome O nome para este produto.
     * @param preco O preço para este produto.
     * @param quantidadeStock A quantidade inicial em stock.
     */
    public Produto(int id, String nome, double preco, int quantidadeStock) {
        // Validação do nome
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }

        // Validação do preço (maior que zero)
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }

        // Validação do stock (maior ou igual a zero)
        if (quantidadeStock < 0) {
            throw new IllegalArgumentException("O stock não pode ser negativo.");
        }

        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeStock = quantidadeStock;
    }

    // --- GETTERS E SETTERS ---

    /**
     * Devolve o ID do produto.
     * @return o identificador do produto.
     */
    public int getId() {
        return id;
    }

    /**
     * Define ou atualiza o ID do produto.
     * @param id O novo identificador do produto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devolve o nome do produto.
     * @return o nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define ou atualiza o nome do produto.
     * @param nome O novo nome do produto.
     */
    public void setNome(String nome) {
        // A validação de Nível 1 (na classe Main) fará o .trim()
        // Mas como defesa extra, a validação de Nível 2 verifica se o nome é válido.
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        this.nome = nome;
    }

    /**
     * Devolve o preço do produto.
     * @return o preço do produto.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define ou atualiza o preço do produto.
     * Inclui uma validação para não permitir preços negativos.
     * @param preco O novo preço do produto.
     */
    public void setPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("O preço do produto deve ser maior que zero.");
        }
        this.preco = preco;
    }

    /**
     * Devolve a quantidade em stock.
     * @return o número de unidades em stock.
     */
    public int getQuantidadeStock() {
        return quantidadeStock;
    }

    /**
     * Define ou atualiza a quantidade em stock.
     * Inclui uma validação para não permitir stock negativo.
     * @param quantidadeStock A nova quantidade em stock.
     */
    public void setQuantidadeStock(int quantidadeStock) {
        if (quantidadeStock < 0) {
            throw new IllegalArgumentException("O stock não pode ser negativo.");
        }
        this.quantidadeStock = quantidadeStock;
    }

    // --- MÉTODOS ADICIONAIS ---

    /**
     * Retorna uma representação textual formatada do objeto Produto.
     * Este método é útil para imprimir os detalhes do produto de forma clara.
     * @return uma String com os dados do produto.
     */
    @Override
    public String toString() {
        // String.format() cria uma string substituindo os marcadores (%) pelos valores fornecidos.
        return String.format("%d. %s %.2f€ (stock: %d)",
                this.id,
                this.nome,
                this.preco,
                this.quantidadeStock);
    }
}