package src.POO.teste_final;

import java.util.ArrayList;
import java.util.List;

/**
 * Modela uma rolote de venda de produtos no estádio.
 * É responsável por gerir a sua lista de produtos, o seu estado (aberta/fechada)
 * e o seu faturamento.
 */
public class Rolote {

    // --- ATRIBUTOS ---

    private int id;                     // O ID único da rolote (1 a 5).
    private String nome;                // O nome da rolote (ex: "Sabores do Leão").
    private List<Produto> produtos;     // A lista de produtos que a rolote vende.
    private boolean aberto;             // O estado da rolote: true = aberta, false = fechada.
    private double faturamentoDiario;   // O total de vendas em euros.

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Rolote.
     * Por defeito, a rolote começa fechada, com faturação a zero e sem produtos.
     *
     * @param id O ID único para esta rolote.
     * @param nome O nome da rolote.
     */
    public Rolote(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.produtos = new ArrayList<>(); // Inicializa uma lista de produtos vazia.
        this.aberto = false;               // Começa fechada por defeito.
        this.faturamentoDiario = 0.0;      // Começa com zero de faturação.
    }

    // --- GETTERS E SETTERS ---

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public boolean isAberto() {
        return aberto;
    }

    /**
     * Devolve a faturação total da rolote.
     * Corresponde ao requisito 'calcularFaturacao'.
     * @return O valor total faturado.
     */
    public double getFaturamentoDiario() {
        return faturamentoDiario;
    }

    // --- MÉTODOS DE GESTÃO ---

    /**
     * Abre a rolote para vendas.
     */
    public void abrirRolote() {
        this.aberto = true;
        System.out.println("\nA rolote '" + this.nome + "' está agora aberta.");
    }

    /**
     * Fecha a rolote para vendas.
     */
    public void fechaRolote() {
        this.aberto = false;
        System.out.println("\nA rolote '" + this.nome + "' está agora fechada.");
    }

    /**
     * Adiciona um produto à lista de produtos vendidos pela rolote.
     * @param produto O objeto Produto a ser adicionado.
     */
    public void adicionaProduto(Produto produto) {
        this.produtos.add(produto);
    }

    /**
     * Processa a venda de uma certa quantidade de um produto.
     * Verifica se a rolote está aberta e se há stock suficiente.
     *
     * @param produto O produto a ser vendido.
     * @param quantidade A quantidade a ser vendida.
     * @return true se a venda for bem-sucedida, false caso contrário.
     */
    public boolean vendeProduto(Produto produto, int quantidade) {
        // Validação 1: A rolote está aberta?
        if (!this.aberto) {
            System.out.println("Erro: A rolote '" + this.nome + "' está fechada.");
            return false;
        }

        // Validação 2: A quantidade é válida?
        if (quantidade <= 0) {
            System.out.println("Erro: A quantidade deve ser positiva.");
            return false;
        }

        // Validação 3: Há stock suficiente?
        if (produto.getQuantidadeStock() < quantidade) {
            System.out.println("Erro: Stock insuficiente para '" + produto.getNome() + "'. Pedido: " + quantidade + ", Disponível: " + produto.getQuantidadeStock());
            return false;
        }

        // --- Se todas as validações passarem, processa a venda ---

        // Atualiza o stock do produto
        int novoStock = produto.getQuantidadeStock() - quantidade;
        produto.setQuantidadeStock(novoStock);

        // Atualiza a faturação da rolote
        double valorVenda = produto.getPreco() * quantidade;
        this.faturamentoDiario += valorVenda;

        System.out.println("Venda efetuada: " + quantidade + "x " + produto.getNome());
        return true;
    }

    /**
     * Retorna uma representação textual do menu da rolote.
     * @return Uma String com o nome da rolote e a lista dos seus produtos.
     */
    @Override
    public String toString() {
        // StringBuilder é mais eficiente para construir strings em ciclos.
        StringBuilder menu = new StringBuilder();
        menu.append("--- ").append(this.nome).append(" ---\n");

        if (produtos.isEmpty()) {
            menu.append(" (Sem produtos disponíveis de momento)\n");
        } else {
            for (Produto produto : this.produtos) {
                // O método produto.toString() já formata a linha do produto.
                menu.append(produto.toString()).append("\n");
            }
        }
        return menu.toString();
    }
}
