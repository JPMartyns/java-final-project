package src.POO.teste_final;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Modela um adepto, que pode comprar bilhetes e produtos.
 * Esta classe é responsável por gerir os dados do adepto, a sua carteira
 * e por guardar as suas informações em ficheiros no disco.
 */
public class Adepto {

    // --- ATRIBUTOS ---

    private String id;                  // O ID único e sequencial (ex: "AD001").
    private String nome;                // O nome do adepto.
    private int idade;                  // A idade do adepto.
    private String documento;           // O documento de identificação (CC).
    private String endereco;            // A morada do adepto.
    private double carteira;            // O dinheiro que o adepto tem disponível.
    private List<Bilhete> bilhetes;     // A lista de bilhetes que o adepto comprou.

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Adepto.
     * No momento da criação, guarda automaticamente os dados do adepto num ficheiro info.txt,
     * dentro de uma pasta com o seu ID.
     *
     * @param id O ID único para o adepto.
     * @param nome O nome do adepto.
     * @param idade A idade do adepto.
     * @param documento O número do documento de identificação.
     * @param endereco A morada do adepto.
     * @param carteira O valor inicial na carteira do adepto.
     */
    public Adepto(String id, String nome, int idade, String documento, String endereco, double carteira) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.documento = documento;
        this.endereco = endereco;
        this.carteira = carteira;
        this.bilhetes = new ArrayList<>(); // A lista de bilhetes começa vazia.

        // Tenta guardar os dados em ficheiro assim que o adepto é criado.
        salvarInformacaoEmFicheiro();
    }

    // --- GETTERS E SETTERS ---
    // (A maioria dos dados de um adepto são definidos na criação, mas getters são úteis)

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public double getCarteira() {
        return carteira;
    }

    public List<Bilhete> getBilhetes() {
        return bilhetes;
    }

    // --- MÉTODOS DE COMPRA ---

    /**
     * Tenta comprar um bilhete para o adepto.
     * Verifica se há dinheiro suficiente na carteira.
     *
     * @param bilhete O bilhete a ser comprado.
     * @return true se a compra for bem-sucedida, false caso contrário.
     */
    public boolean comprarBilhete(Bilhete bilhete) {
        // Validação: verificar se tem dinheiro
        if (this.carteira < bilhete.getPreco()) {
            System.out.println("Erro: " + this.nome + " não tem saldo suficiente para comprar o bilhete.");
            return false;
        }

        // Processar a compra
        this.carteira -= bilhete.getPreco(); // Subtrai o preço da carteira
        this.bilhetes.add(bilhete);          // Adiciona o bilhete à lista

        //System.out.println("\nCompra de bilhete para " + this.id + " efetuada com sucesso!");
        //System.out.printf("Novo saldo na carteira: %.2f€\n", this.carteira);

        // Guarda os dados do bilhete num ficheiro
        salvarBilheteEmFicheiro(bilhete);

        return true;
    }

    /**
     * Tenta comprar comida (ou outros produtos).
     *
     * @param valorCompra O custo total dos produtos a comprar.
     * @return true se a compra for bem-sucedida, false caso contrário.
     */
    public boolean comprarComida(double valorCompra) {
        if (this.carteira < valorCompra) {
            System.out.println("Erro: " + this.id + " não tem saldo suficiente para esta compra.");
            return false;
        }

        this.carteira -= valorCompra;
        System.out.println("Compra de comida efetuada com sucesso!");
        System.out.printf("Novo saldo na carteira: %.2f€\n", this.carteira);
        return true;
    }

    // --- MÉTODOS DE GESTÃO DE FICHEIROS (PERSISTÊNCIA) ---

    /**
     * Guarda os dados principais do adepto num ficheiro info.txt.
     * Cria um diretório para o adepto se não existir.
     */
    private void salvarInformacaoEmFicheiro() {
        // Define o caminho para o diretório do adepto (ex: "adeptos/AD001")
        Path diretorioAdepto = Paths.get("adeptos", this.id);

        try {
            // Cria o diretório (e o diretório "adeptos" se necessário). Não faz nada se já existir.
            Files.createDirectories(diretorioAdepto);

            // Define o caminho completo para o ficheiro (ex: "adeptos/AD001/info.txt")
            File ficheiroInfo = new File(diretorioAdepto.toString(), "info.txt");

            // Usa try-with-resources para garantir que o PrintWriter é fechado automaticamente.
            try (PrintWriter writer = new PrintWriter(new FileWriter(ficheiroInfo))) {
                writer.println("ID: " + this.id);
                writer.println("Nome: " + this.nome);
                writer.println("Idade: " + this.idade);
                writer.println("Documento: " + this.documento);
                writer.println("Morada: " + this.endereco);
                writer.printf("Carteira Inicial: %.2f€\n", this.carteira);
            }

        } catch (IOException e) {
            System.err.println("ERRO: Falha ao guardar os dados do adepto " + this.id + " em ficheiro.");
            e.printStackTrace(); // Imprime o detalhe do erro na consola de erro.
        }
    }

    /**
     * Guarda os dados de um bilhete comprado num ficheiro .txt dentro da pasta do adepto.
     * @param bilhete O bilhete cujos dados serão guardados.
     */
    private void salvarBilheteEmFicheiro(Bilhete bilhete) {
        // Define o caminho para o diretório de bilhetes do adepto (ex: "adeptos/AD001/bilhetes")
        Path diretorioBilhetes = Paths.get("adeptos", this.id, "bilhetes");

        try {
            Files.createDirectories(diretorioBilhetes);

            // O nome do ficheiro será o ID do bilhete (ex: "A7.txt")
            File ficheiroBilhete = new File(diretorioBilhetes.toString(), bilhete.getId() + ".txt");

            try (PrintWriter writer = new PrintWriter(new FileWriter(ficheiroBilhete))) {
                // Usamos o método toString() do bilhete, que já formata a informação toda.
                writer.println(bilhete.toString());
            }

        } catch (IOException e) {
            System.err.println("ERRO: Falha ao guardar o bilhete " + bilhete.getId() + " em ficheiro.");
            e.printStackTrace();
        }
    }
}