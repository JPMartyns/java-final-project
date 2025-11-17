package src.POO.teste_final;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Modela o Estádio, a classe central que contém e gere todos os
 * outros elementos do sistema (Setores, Rolotes, Adeptos, Jogo).
 */
public class Estadio {

    // --- ATRIBUTOS ---

    private long id;                            // ID único e aleatório de 10 dígitos.
    private String nome;                        // Nome do estádio.
    private String localizacao;                 // Localização do estádio.

    private List<Setor> setores;                // Lista dos 4 setores do estádio.
    private List<Rolote> rolotes;               // Lista de rolotes, com um máximo de 5.
    private List<Adepto> adeptos;               // Lista de todos os adeptos registados.
    private List<Bilhete> bilhetesVendidos;     // Lista de todos os bilhetes vendidos
    private Jogo jogo;                          // O jogo que está atualmente agendado ou a decorrer.

    // Contador para gerar IDs sequenciais para os adeptos
    private int proximoIdAdepto = 1;


    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Estádio.
     * Gera um ID aleatório e inicializa os 4 setores padrão com os seus preços.
     * @param nome O nome do estádio.
     * @param localizacao A morada do estádio.
     */
    public Estadio(String nome, String localizacao) {
        // Gera um ID aleatório de 10 dígitos.
        this.id = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        this.nome = nome;
        this.localizacao = localizacao;

        // Inicializa as listas
        this.rolotes = new ArrayList<>();
        this.adeptos = new ArrayList<>();
        this.setores = new ArrayList<>();
        this.bilhetesVendidos = new ArrayList<>();

        // Cria e adiciona os 4 setores padrão com os seus preços base
        this.setores.add(new Setor("A", 10.0));
        this.setores.add(new Setor("B", 20.0));
        this.setores.add(new Setor("C", 30.0));
        this.setores.add(new Setor("D", 40.0));
    }


    // --- GETTERS ---
    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public List<Rolote> getRolotes() {
        return rolotes;
    }

    public List<Adepto> getAdeptos() {
        return adeptos;
    }

    public Jogo getJogo() {
        return jogo;
    }

    /**
     * Devolve a capacidade máxima do estádio, calculada a partir dos seus setores.
     * Cumpre o requisito (d) capacidadeMaxima: 100.
     * @return A capacidade máxima total do estádio.
     */
    public int getCapacidadeMaxima() {
        int capacidadeTotal = 0;
        for (Setor setor : this.setores) {
            capacidadeTotal += setor.getCapacidade();
        }
        return capacidadeTotal;
    }

    public List<Bilhete> getBilhetesVendidos() {
        return bilhetesVendidos;
    }

    // --- MÉTODOS DE GESTÃO ---

    /**
     * Adiciona um bilhete à lista central de bilhetes vendidos do estádio.
     * @param bilhete O bilhete que foi vendido.
     */
    public void adicionarBilheteVendido(Bilhete bilhete) {
        this.bilhetesVendidos.add(bilhete);
        // Também podemos adicionar o bilhete à lista do jogo, se for necessário para a receita do jogo
        if (this.jogo != null) {
            this.jogo.adicionarBilheteVendido(bilhete);
        }
    }

    /**
     * Cria e agenda um novo jogo no estádio.
     * @param casa A equipa da casa.
     * @param visitante A equipa visitante.
     * @param dataHora A data e hora do jogo.
     */
    public void criarJogo(Equipa casa, Equipa visitante, LocalDateTime dataHora, String arbitro) {
        this.jogo = new Jogo(casa, visitante, dataHora, arbitro);
    }

    /**
     * Adiciona uma nova rolote ao estádio, se houver espaço.
     * O estádio tem uma capacidade máxima de 5 rolotes.
     * @param rolote A rolote a ser adicionada.
     * @return true se a rolote foi adicionada, false caso contrário.
     */
    public boolean adicionarRolote(Rolote rolote) {
        if (this.rolotes.size() < 5) {
            this.rolotes.add(rolote);
            System.out.println("Rolote '" + rolote.getNome() + "' adicionada ao estádio.");
            return true;
        } else {
            System.out.println("Erro: O estádio já atingiu a capacidade máxima de 5 rolotes.");
            return false;
        }
    }

    /**
     * Cria um novo adepto com um ID sequencial e regista-o no estádio.
     * @return O objeto Adepto que foi criado.
     */
    public Adepto criarNovoAdepto(String nome, int idade, String cc, String endereco, double carteira) {
        // Formata o ID para ter 3 dígitos, com zeros à esquerda (ex: AD001, AD015, AD123)
        String idAdepto = String.format("AD%03d", this.proximoIdAdepto);
        this.proximoIdAdepto++; // Incrementa o contador para o próximo adepto

        Adepto novoAdepto = new Adepto(idAdepto, nome, idade, cc, endereco, carteira);
        this.adeptos.add(novoAdepto);

        return novoAdepto;
    }

    /**
     * Inicia a simulação do jogo agendado.
     */
    public void iniciarJogo() {
        if (this.jogo != null) {
            this.jogo.iniciarJogo(this);
        } else {
            System.out.println("Erro: Nenhum jogo foi criado para poder ser iniciado.");
        }
    }

    /**
     * Calcula e mostra as estatísticas do estádio para o intervalo do jogo.
     */
    public void mostrarEstatisticasIntervalo() {
        System.out.println("\n=== Intervalo (5 segundos) ===");
        System.out.println("\nEstatísticas Parciais:");

        // Calcular Ocupação
        int lugaresOcupados = 0;
        int capacidadeTotal = 0;
        for (Setor setor : this.setores) {
            capacidadeTotal += setor.getCapacidade();
            lugaresOcupados += (setor.getCapacidade() - setor.verificarDisponibilidade());
        }
        double percentagemOcupacao = (double) lugaresOcupados / capacidadeTotal * 100;
        System.out.printf("- Ocupação: %.2f%% (%d/%d)\n", percentagemOcupacao, lugaresOcupados, capacidadeTotal);

        // Calcular Rolotes Abertas e Faturação
        int rolotesAbertas = 0;
        double faturacaoTotalRolotes = 0.0;
        for (Rolote rolote : this.rolotes) {
            if (rolote.isAberto()) {
                rolotesAbertas++;
            }
            faturacaoTotalRolotes += rolote.getFaturamentoDiario();
        }
        System.out.println("- Rolotes abertas: " + rolotesAbertas);
        System.out.printf("- Faturamento rolotes: %.2f€\n\n", faturacaoTotalRolotes);
    }
}