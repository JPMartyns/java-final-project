package src.POO.teste_final;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Modela o jogo de futebol.
 * É responsável por gerir as equipas, o resultado, e simular o decorrer
 * da partida com golos aleatórios.
 */
public class Jogo {

    // --- ATRIBUTOS ---

    private Equipa equipaCasa;
    private Equipa equipaVisitante;
    private LocalDateTime dataHora;
    private String arbitro;
    private List<Bilhete> bilhetesVendidos; // Para calcular a receita do jogo

    // Atributos para controlar o estado do jogo
    private int golosCasa;
    private int golosVisitante;
    private List<String> marcadores; // Lista para registar quem marcou os golos
    private boolean jogoTerminado;

    // --- CONSTRUTOR ---

    /**
     * Cria uma nova instância de Jogo.
     *
     * @param equipaCasa A equipa da casa.
     * @param equipaVisitante A equipa visitante.
     * @param dataHora A data e hora agendadas para o jogo.
     */
    public Jogo(Equipa equipaCasa, Equipa equipaVisitante, LocalDateTime dataHora, String arbitro) {
        this.equipaCasa = equipaCasa;
        this.equipaVisitante = equipaVisitante;
        this.dataHora = dataHora;
        this.arbitro = arbitro;

        // Inicializa o estado do jogo
        this.bilhetesVendidos = new ArrayList<>();
        this.golosCasa = 0;
        this.golosVisitante = 0;
        this.marcadores = new ArrayList<>();
        this.jogoTerminado = false;
    }

    // --- GETTERS ---

    public Equipa getEquipaCasa() {
        return equipaCasa;
    }

    public Equipa getEquipaVisitante() {
        return equipaVisitante;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public List<Bilhete> getBilhetesVendidos() {
        return bilhetesVendidos;
    }

    /**
     * Devolve o resultado final do jogo numa string formatada.
     * @return Uma string como "Sporting 0 - 3 Benfica".
     */
    public String getResultado() {
        if (!this.jogoTerminado) {
            return "O jogo ainda não terminou.";
        }
        return String.format("%s %d - %d %s",
                this.equipaCasa.getNome(), this.golosCasa,
                this.golosVisitante, this.equipaVisitante.getNome());
    }

    // --- MÉTODOS DE GESTÃO DO JOGO ---

    /**
     * Adiciona um bilhete à lista de bilhetes vendidos para este jogo.
     * @param bilhete O bilhete que foi vendido.
     */
    public void adicionarBilheteVendido(Bilhete bilhete) {
        this.bilhetesVendidos.add(bilhete);
    }

    /**
     * Calcula a receita total gerada pela venda de bilhetes para este jogo.
     * @return O valor total da receita da bilheteira.
     */
    public double calcularReceitaBilheteira() {
        double receitaTotal = 0.0;
        for (Bilhete bilhete : this.bilhetesVendidos) {
            receitaTotal += bilhete.getPreco();
        }
        return receitaTotal;
    }

    /**
     * Inicia e simula o decorrer do jogo.
     * 1 segundo na vida real = 1 minuto no jogo.
     * A cada 10 minutos de jogo, há 10% de probabilidade de golo para cada equipa.
     */
    public void iniciarJogo(Estadio estadio) {
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("\n=== A Iniciar Jogo ===");
        System.out.println(this.equipaCasa.getNome() + " VS " + this.equipaVisitante.getNome());
        System.out.println("Data: " + this.dataHora.format(formatadorData));
        System.out.println("Hora: " + this.dataHora.format(formatadorHora));
        System.out.println("Árbitro: " + this.arbitro);

        Random random = new Random();
        System.out.println("\nPrimeira parte:");

        // Loop que simula os 90 minutos do jogo
        for (int minuto = 1; minuto <= 90; minuto++) {
            try {
                // Pausa de 1 segundo para simular a passagem de 1 minuto
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Em caso de erro na thread, interrompe o jogo.
                System.err.println("A simulação do jogo foi interrompida.");
                Thread.currentThread().interrupt(); // Boa prática para restaurar o estado de interrupção
                return;
            }

            boolean eventoOcorreu = false;

            // A cada 10 minutos, verifica a probabilidade de golo
            if (minuto % 10 == 0) {
                // Probabilidade de golo para a equipa da casa (10%)
                if (random.nextDouble() < 0.10) {
                    this.golosCasa++;
                    String marcador = equipaCasa.getPlantel().get(random.nextInt(equipaCasa.getPlantel().size()));
                    System.out.printf("%d' GOLO! %s marca! (%s)\n", minuto, this.equipaCasa.getNome(), getResultadoTemporario());
                    System.out.printf("   Marcador: %s\n", marcador);
                    eventoOcorreu = true;
                }

                // Probabilidade de golo para a equipa visitante (10%)
                if (random.nextDouble() < 0.10) {
                    this.golosVisitante++;
                    String marcador = equipaVisitante.getPlantel().get(random.nextInt(equipaVisitante.getPlantel().size()));
                    System.out.printf("%d' GOLO! %s marca! (%s)\n", minuto, this.equipaVisitante.getNome(), getResultadoTemporario());
                    System.out.printf("   Marcador: %s\n", marcador);
                    eventoOcorreu = true;
                }
            }

            // Eventos Específicos do Jogo
            if (minuto == 1) {
                System.out.println("1' Início do jogo");
                eventoOcorreu = true;
            } else if (minuto == 45) {
                System.out.println("45'");
                estadio.mostrarEstatisticasIntervalo();
                try {
                    Thread.sleep(2000); // Pausa de 2 segundos para o intervalo
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
                System.out.println("Segunda parte:");
                eventoOcorreu = true;
            } else if (minuto == 46) {
                System.out.println("46' recomeço");
                eventoOcorreu = true;
            } else if (minuto == 90) {
                System.out.println("90' Fim de jogo");
                eventoOcorreu = true;
            }

            // Se não houve evento, imprime apenas o minuto
            if (!eventoOcorreu) {
                System.out.println(minuto + "'");
            }
        }

        this.jogoTerminado = true;

    }

    // Método auxiliar para obter o placar durante o jogo
    private String getResultadoTemporario() {
        return String.format("%s %d - %d %s",
                this.equipaCasa.getNome(), this.golosCasa,
                this.golosVisitante, this.equipaVisitante.getNome());
    }
}
