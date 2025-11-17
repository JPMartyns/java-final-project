package src.POO.teste_final;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        // 1. Configurar o ambiente (limpeza ao sair)
        configurarAmbiente();

        System.out.println("\nBem-vindo ao Sistema de Gestão do Estádio!");
        // 2. Criar os dados base do sistema (o estádio e o jogo)
        Estadio estadio = criarDadosBase();

        // 3. Entregar o controlo ao gestor de menus
        MenuController menu = new MenuController(estadio);
        menu.iniciar(); // O programa principal agora corre dentro do controlador
    }

    /**
     * Cria os objetos centrais e imprime o seu estado inicial,
     * @return A instância principal do Estádio.
     */
    private static Estadio criarDadosBase() {
        // --- Criação do Estádio ---
        System.out.println("\nCriando Estádio...");
        Estadio estadio = new Estadio("Estádio José Alvalade", "Rua Professor Fernando da Fonseca, Lisboa");
        System.out.println("Nome: " + estadio.getNome());
        System.out.println("ID: " + estadio.getId());
        System.out.println("Localização: " + estadio.getLocalizacao());

        // --- Detalhes dos Setores ---
        System.out.println("\nSetores criados:");
        for (Setor setor : estadio.getSetores()) {
            System.out.printf("- Setor %s: %d lugares (%.0f€)\n",
                    setor.getId(), setor.getCapacidade(), setor.getPrecoBase());
        }

        // --- Visualização dos Setores ---
        System.out.println("\nVisualização inicial dos setores:");
        System.out.println("(Legenda: [ ] = Livre, [X] = Ocupado)");
        for (Setor setor : estadio.getSetores()) {
            setor.mostrarLugares();
        }

        // --- Criação do Jogo ---
        System.out.println("\n=== A Criar o jogo ===");
        Equipa casa = new Equipa("Sporting", "Lisboa", 1906, "Rúben Amorim");
        casa.adicionarJogador("Franco Israel"); casa.adicionarJogador("Ricardo Esgaio"); casa.adicionarJogador("Jeremiah St. Juste");
        casa.adicionarJogador("Sebastián Coates"); casa.adicionarJogador("Gonçalo Inácio"); casa.adicionarJogador("Nuno Santos");
        casa.adicionarJogador("Morten Hjulmand"); casa.adicionarJogador("Pedro Gonçalves"); casa.adicionarJogador("Marcus Edwards");
        casa.adicionarJogador("Francisco Trincão"); casa.adicionarJogador("Viktor Gyökeres");

        Equipa visitante = new Equipa("Benfica", "Lisboa", 1904, "Roger Schmidt");
        visitante.adicionarJogador("Anatoliy Trubin"); visitante.adicionarJogador("Alexander Bah"); visitante.adicionarJogador("António Silva");
        visitante.adicionarJogador("Nicolás Otamendi"); visitante.adicionarJogador("Fredrik Aursnes"); visitante.adicionarJogador("João Neves");
        visitante.adicionarJogador("Orkun Kökçü"); visitante.adicionarJogador("Ángel Di María"); visitante.adicionarJogador("Rafa Silva");
        visitante.adicionarJogador("David Neres"); visitante.adicionarJogador("Arthur Cabral");

        // Imprime os detalhes das equipas
        imprimirDetalhesEquipa(casa, "Equipa da Casa");
        imprimirDetalhesEquipa(visitante, "Equipa Visitante");

        LocalDateTime dataDoJogo = LocalDateTime.of(2025, 9, 11, 20, 45);
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println("\nData do jogo: " + dataDoJogo.format(formatadorData));
        System.out.println("Hora: " + dataDoJogo.format(formatadorHora));

        String arbitro = "João Pinheiro";
        estadio.criarJogo(casa, visitante, dataDoJogo, arbitro);

        System.out.println("\nJogo criado com sucesso: " + casa.getNome() + " vs " + visitante.getNome());

        return estadio;
    }

    /**
     * Método auxiliar para imprimir os detalhes de uma equipa.
     * @param equipa O objeto equipa a detalhar.
     * @param tipo O título a dar (ex: "Equipa da Casa").
     */
    private static void imprimirDetalhesEquipa(Equipa equipa, String tipo) {
        System.out.println("\n" + tipo + ": " + equipa.getNome());
        System.out.println("- Cidade: " + equipa.getCidade());
        System.out.println("- Fundação: " + equipa.getDataFundacao());
        System.out.println("- Treinador: " + equipa.getTreinador());
        System.out.println("\nPlantel:");
        for(String jogador : equipa.getPlantel()) {
            System.out.println("- " + jogador);
        }
    }

    /**
     * Configura o ambiente, nomeadamente o hook de limpeza.
     */
    private static void configurarAmbiente() {
        Path diretorioAdeptos = Paths.get("adeptos");
        Thread ganchoDeEncerramento = new Thread(() -> limparDiretorio(diretorioAdeptos));
        Runtime.getRuntime().addShutdownHook(ganchoDeEncerramento);
    }

    private static void limparDiretorio(Path caminhoDiretorio) {
        if (Files.exists(caminhoDiretorio)) {
            try {
                // Percorre todos os ficheiros e pastas em ordem inversa (conteúdo antes da pasta)
                Files.walk(caminhoDiretorio)
                        .sorted(Comparator.reverseOrder())
                        .filter(path -> !path.equals(caminhoDiretorio)) // Não apaga a pasta raiz "adeptos"
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                System.err.println("Falha ao apagar: " + path + " - " + e.getMessage());
                            }
                        });
                System.out.println("\n[INFO] Diretório '" + caminhoDiretorio + "' foi limpo para a próxima execução.");
            } catch (IOException e) {
                System.err.println("ERRO: Falha ao percorrer o diretório para limpeza: " + e.getMessage());
            }
        }
    }
}
