package src.POO.teste_final;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modela um bilhete individual vendido a um adepto.
 * Contém informações sobre o lugar, o setor, o preço pago e a data da transação.
 */
public class Bilhete {

    // --- ATRIBUTOS ---

    private String id;              // O ID único do bilhete (ex: "A7", "C12").
    private Setor setor;            // A referência ao objeto Setor ao qual este bilhete pertence.
    private String lugar;           // Uma descrição do lugar (ex: "7 (Fila 2, Posição 2)").
    private double preco;           // O preço final pago pelo bilhete.
    private LocalDateTime dataCompra; // A data e hora exatas da compra.

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Bilhete.
     * A data e hora da compra são automaticamente definidas para o momento da criação.
     *
     * @param id O ID único para este bilhete.
     * @param setor A instância do Setor onde o bilhete é válido.
     * @param lugar A descrição textual do lugar.
     * @param preco O preço efetivamente pago.
     */
    public Bilhete(String id, Setor setor, String lugar, double preco) {
        this.id = id;
        this.setor = setor;
        this.lugar = lugar;
        this.preco = preco;
        // Captura a data e hora atuais no momento em que o bilhete é criado.
        this.dataCompra = LocalDateTime.now();
    }

    // --- GETTERS ---
    // Geralmente, os dados de um bilhete não mudam após a compra,
    // então podemos omitir os setters para a maioria dos atributos para torná-lo "imutável".

    /**
     * Devolve o ID do bilhete.
     * @return O ID do bilhete.
     */
    public String getId() {
        return id;
    }

    /**
     * Devolve o objeto Setor associado a este bilhete.
     * @return A instância do Setor.
     */
    public Setor getSetor() {
        return setor;
    }

    /**
     * Devolve a descrição do lugar.
     * @return A descrição do lugar.
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Devolve o preço pago pelo bilhete.
     * @return O preço do bilhete.
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Devolve a data e hora da compra do bilhete.
     * @return A data e hora da compra.
     */
    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    // --- MÉTODOS ADICIONAIS ---

    /**
     * Retorna uma representação textual formatada dos detalhes do bilhete.
     * É útil para gerar recibos ou guardar em ficheiro.
     * @return Uma String com todos os dados do bilhete.
     */
    @Override
    public String toString() {
        // Define o formato desejado para a data e hora (ex: 11/12/2024 14:30)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String dataFormatada = this.dataCompra.format(formatter);

        // Constrói a string final com todos os detalhes.
        // O %.2f formata o preço para ter sempre duas casas decimais.
        return String.format(
                "- ID: %s\n" +
                        "- Setor: %s\n" +
                        "- Lugar: %s\n" +
                        "- Preço: %.2f€\n" +
                        "- Data Compra: %s",
                this.id,
                this.setor.getId(), // Usamos o getId() para obter o nome do setor
                this.lugar,
                this.preco,
                dataFormatada
        );
    }
}
