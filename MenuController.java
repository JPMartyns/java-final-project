package src.POO.teste_final;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MenuController {

    // --- ATRIBUTOS ---
    private Estadio estadio;
    private Scanner leitor;

    // --- CONSTRUTOR ---
    public MenuController(Estadio estadio) {
        this.estadio = estadio;
        this.leitor = new Scanner(System.in);
    }

    // --- MÉTODOS DE ORQUESTRAÇÃO ---

    /**
     * Inicia o fluxo principal do programa: setup inicial seguido do menu.
     */
    public void iniciar() {
        // Fluxo de inicialização interativo, executado apenas uma vez.
        System.out.println("\n--- Configuração Inicial do Evento ---");
        System.out.println("\n--- Criação da Primeira Rolote ---");
        processoCriarRolote();

        System.out.println("\n--- Criação do Primeiro Adepto e Compra de Bilhete(s) ---");
        Adepto primeiroAdepto = processoCriarAdepto();
        if (primeiroAdepto != null) {
            System.out.println("\nAdepto " + primeiroAdepto.getId() + " criado com sucesso!");
            System.out.println("Avançando para a compra de bilhete(s)...");
            processoCompraBilhete(primeiroAdepto);
        }

        // Após a configuração inicial, o programa entra no menu principal.
        menuPrincipalLoop();
    }

    /**
     * Mantém o menu principal em execução até o utilizador decidir sair.
     */
    public void menuPrincipalLoop() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n======= MENU PRINCIPAL =======");
            System.out.println("O que deseja fazer?");
            System.out.println("    1. Adicionar nova rolote");
            System.out.println("    2. Criar novo adepto");
            System.out.println("    3. Comprar comida");
            System.out.println("    4. Comprar mais bilhetes");
            System.out.println("    5. Ver Estatísticas do Estádio");
            System.out.println("    6. Iniciar Jogo");
            System.out.println("    0. Sair do Programa");
            System.out.print("Sua escolha: ");

            try {
                int escolha = leitor.nextInt();
                leitor.nextLine(); // Consome a quebra de linha

                switch (escolha) {
                    case 1:
                        processoCriarRolote();
                        break;
                    case 2:
                        Adepto novoAdepto = processoCriarAdepto();
                        System.out.println("Adepto " + novoAdepto.getId() + " criado com sucesso!");
                        break;
                    case 3:
                        processoComprarComida();
                        break;
                    case 4:
                        processoComprarMaisBilhetes();
                        break;
                    case 5:
                        processoVerEstatisticas();
                        break;
                    case 6:
                        if (estadio.getJogo() != null) {
                            estadio.iniciarJogo(); // Inicia a simulação
                            processoRelatorioFinal(); // Mostra o relatório no fim
                        } else {
                            System.out.println("ERRO: Não há nenhum jogo agendado.");
                        }
                        sair = true; // Termina o programa após o jogo
                        break;
                    case 0:
                        sair = true;
                        break;
                    default:
                        System.out.println("ERRO: Opção inválida. Por favor, escolha um número do menu.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Por favor, insira um número.");
                leitor.next(); // Limpa o buffer do scanner
            }
        }
        System.out.println("\nObrigado por usar o Sistema de Gestão do Estádio!");
    }

    /**
     * Gera e exibe o relatório final completo do evento após o término do jogo.
     */
    private void processoRelatorioFinal() {
        Jogo jogo = estadio.getJogo();
        if (jogo == null) {
            System.out.println("Não há dados de jogo para apresentar.");
            return;
        }

        System.out.println("\n=======================================");
        System.out.println("======= RELATÓRIO FINAL DO JOGO =======");
        System.out.println("=======================================");
        System.out.println("Resultado: " + jogo.getResultado());

        System.out.println("\nEstatísticas do jogo:");

        // --- 1. Bilheteira ---
        System.out.println("--- 1. Bilheteira ---");
        int capacidadeTotal = estadio.getCapacidadeMaxima();
        int lugaresOcupados = estadio.getBilhetesVendidos().size();

        System.out.println("- Ocupação por setor:");
        for (Setor setor : estadio.getSetores()) {
            int ocupadosSetor = setor.getCapacidade() - setor.verificarDisponibilidade();
            System.out.printf("  * Setor %s: %d/%d\n", setor.getId(), ocupadosSetor, setor.getCapacidade());
        }
        System.out.printf("- Lugares Ocupados: %d/%d\n", lugaresOcupados, capacidadeTotal);
        double receitaBilheteira = jogo.calcularReceitaBilheteira();
        System.out.printf("- Receita da Bilheteira: %.2f€\n", receitaBilheteira);

        // --- 2. Rolotes ---
        System.out.println("\n--- 2. Rolotes ---");
        double faturacaoTotalRolotes = 0.0;
        if (estadio.getRolotes().isEmpty()) {
            System.out.println("Nenhuma rolote operou durante o evento.");
        } else {
            for (Rolote rolote : estadio.getRolotes()) {
                System.out.printf("- Faturação da Rolote '%s': %.2f€\n", rolote.getNome(), rolote.getFaturamentoDiario());
                faturacaoTotalRolotes += rolote.getFaturamentoDiario();
            }
        }
        System.out.printf("- Faturação Total das Rolotes: %.2f€\n", faturacaoTotalRolotes);

        // --- 3. Receita Total ---
        System.out.println("\n--- 3. Receita Total do Evento ---");
        double receitaTotal = receitaBilheteira + faturacaoTotalRolotes;
        System.out.printf("Receita Total (Bilhetes + Rolotes): %.2f€\n", receitaTotal);
        System.out.println("\nTodos os dados foram salvos durante a execução.");
        System.out.println("===============================================");
    }

    // --- MÉTODOS INTERATIVOS ---

    /**
     * Guia o utilizador na criação de uma nova rolote e adiciona-a ao estádio.
     */
    private void processoCriarRolote() {
        String nome;
        do {
            System.out.print("Introduza o nome da rolote: ");
            nome = leitor.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("ERRO: O nome não pode ser vazio. Tente novamente.");
            }
        } while (nome.isEmpty());

        Rolote novaRolote = new Rolote(estadio.getRolotes().size() + 1, nome);

        int numProdutos = -1;
        boolean entradaValida = false;
        do {
            try {
                System.out.print("Quantos produtos deseja adicionar? ");
                numProdutos = leitor.nextInt();
                if (numProdutos > 0) {
                    entradaValida = true;
                } else {
                    System.out.println("ERRO: O número de produtos não pode ser negativo ou 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Por favor, insira um número inteiro.");
                leitor.next();
            }
        } while (!entradaValida);
        leitor.nextLine();

        for (int i = 0; i < numProdutos; i++) {
            System.out.println("\n-- Adicionar Produto " + (i + 1) + " --");
            String nomeProduto;
            do {
                System.out.print("Nome do produto: ");
                nomeProduto = leitor.nextLine().trim();
            } while (nomeProduto.isEmpty());

            double preco = 0; // Inicializado a 0 para garantir que o loop começa
            do {
                try {
                    System.out.print("Preço do produto (deve ser maior que 0): "); // Dica para o utilizador
                    preco = leitor.nextDouble();
                    if (preco <= 0) {
                        System.out.println("ERRO: O preço deve ser maior que zero.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("ERRO: Entrada inválida. Insira um número (ex: 5.50).");
                    leitor.next();
                    preco = 0; // Garante que o loop continua em caso de erro
                }
            } while (preco <= 0);

            int stock = -1;
            do {
                try {
                    System.out.print("Stock do produto: ");
                    stock = leitor.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("ERRO: Entrada inválida. Insira um número inteiro.");
                    leitor.next();
                }
            } while (stock < 0);
            leitor.nextLine();

            novaRolote.adicionaProduto(new Produto(i + 1, nomeProduto, preco, stock));
        }
        novaRolote.abrirRolote();
        estadio.adicionarRolote(novaRolote);
    }

    /**
     * Guia o utilizador na criação de um novo adepto e devolve o objeto criado.
     */
    private Adepto processoCriarAdepto() {
        String nome, cc, endereco;
        int idade = 0;
        double carteira = 0;

        do {
            System.out.print("Introduza o nome do adepto: ");
            nome = leitor.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("ERRO: O nome não pode ser vazio.");
            }
        } while (nome.isEmpty());

        // --- VALIDAÇÃO DE IDADE (>= 18) ---
        boolean idadeValida = false;
        do {
            try {
                System.out.print("Idade (mínimo 18 anos): ");
                idade = leitor.nextInt();
                if (idade >= 18) {
                    idadeValida = true;
                } else {
                    System.out.println("ERRO: O adepto deve ter no mínimo 18 anos.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Idade inválida. Insira um número inteiro.");
                leitor.next(); // Limpa o buffer do scanner
            }
        } while (!idadeValida);
        leitor.nextLine(); // Consome a quebra de linha pendente

        // --- VALIDAÇÃO DO CC (8 DÍGITOS NUMÉRICOS) ---
        boolean ccValido = false;
        do {
            System.out.print("Cartão de Cidadão (CC - 8 dígitos numéricos): ");
            cc = leitor.nextLine().trim();
            // A expressão regular "\\d{8}" verifica se a string contém exatamente 8 dígitos.
            if (cc.matches("\\d{8}")) {
                ccValido = true;
            } else {
                System.out.println("ERRO: O CC deve conter exatamente 8 dígitos numéricos. Tente novamente.");
            }
        } while (!ccValido);

        do {
            System.out.print("Endereço: ");
            endereco = leitor.nextLine().trim();
            if (endereco.isEmpty()) {
                System.out.println("ERRO: O endereço não pode ser vazio.");
            }
        } while (endereco.isEmpty());

        boolean carteiraValida = false;
        do {
            try {
                System.out.print("Valor inicial na carteira: ");
                carteira = leitor.nextDouble();
                if (carteira >= 10) {
                    carteiraValida = true;
                } else {
                    System.out.println("ERRO: O valor mínimo da carteira é 10€.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Valor inválido. Insira um número (ex: 100.0).");
                leitor.next();
            }
        } while (!carteiraValida);
        leitor.nextLine();

        return estadio.criarNovoAdepto(nome, idade, cc, endereco, carteira);
    }

    /**
     * Guia o utilizador na compra de um bilhete para um adepto específico.
     */
    private void processoCompraBilhete(Adepto adepto) {
        System.out.println("\n--- Comprar Bilhete(s) para adepto " + adepto.getId() + " ---");
        System.out.printf("Saldo atual da carteira: %.2f€\n", adepto.getCarteira());


        // 1. PERGUNTAR QUANTOS BILHETES
        int bilhetesAComprar = 0;
        do {
            try {
                System.out.print("Quantos bilhetes deseja comprar? ");
                bilhetesAComprar = leitor.nextInt();
                if (bilhetesAComprar < 0) {
                    System.out.println("ERRO: O número de bilhetes não pode ser negativo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERRO: Entrada inválida. Por favor, insira um número.");
                leitor.next();
            }
        } while (bilhetesAComprar < 0);
        leitor.nextLine(); // Limpa o buffer

        // 2. LOOP PARA COMPRAR CADA BILHETE
        for (int i = 1; i <= bilhetesAComprar; i++) {
            System.out.printf("\n--- A comprar bilhete %d de %d ---\n", i, bilhetesAComprar);

            System.out.println("Setores disponíveis:");
            for (Setor setor : estadio.getSetores()) {
                System.out.printf("Setor %s: %.2f€ [%d/%d lugares disponíveis]\n",
                        setor.getId(), setor.getPrecoBase(), setor.verificarDisponibilidade(), setor.getCapacidade());
            }

            Setor setorEscolhido = null;
            while (setorEscolhido == null) {
                System.out.print("Selecione um setor (A, B, C, D): ");
                String idSetor = leitor.nextLine().trim().toUpperCase();
                for (Setor setor : estadio.getSetores()) {
                    if (setor.getId().equals(idSetor)) {
                        setorEscolhido = setor;
                        break;
                    }
                }
                if (setorEscolhido == null) System.out.println("ERRO: Setor inválido.");
            }

            setorEscolhido.mostrarLugares();

            boolean lugarComprado = false;
            while (!lugarComprado) {
                // 3. PEDIR APENAS O NÚMERO DO LUGAR
                int numLugar = -1;
                do {
                    try {
                        System.out.print("Selecione o número do lugar (1-25): ");
                        numLugar = leitor.nextInt();
                    } catch (InputMismatchException e) {
                        leitor.next();
                    }
                } while (numLugar < 1 || numLugar > 25);
                leitor.nextLine(); // Limpa o buffer

                // Converter número do lugar (1-25) em coordenadas de matriz (0-4, 0-4)
                int fila = (numLugar - 1) / 5;
                int coluna = (numLugar - 1) % 5;

                if (setorEscolhido.isLugarOcupado(fila, coluna)) {
                    System.out.println("ERRO: Esse lugar já está ocupado. Por favor, escolha outro.");
                } else {
                    // Lugar válido e livre, processar compra
                    String idBilhete = setorEscolhido.getId() + numLugar;
                    String descricaoLugar = String.format("%d (Fila %d, Posição %d)", numLugar, fila + 1, coluna + 1);
                    Bilhete novoBilhete = new Bilhete(idBilhete, setorEscolhido, descricaoLugar, setorEscolhido.getPrecoBase());

                    double carteiraAnterior = adepto.getCarteira();

                    if (adepto.comprarBilhete(novoBilhete)) {
                        // Se a compra foi bem-sucedida, o adepto já tem o saldo atualizado.
                        setorEscolhido.ocuparLugar(fila, coluna);
                        estadio.adicionarBilheteVendido(novoBilhete);

                        System.out.println("\n=== Confirmação do bilhete ===");
                        System.out.println(novoBilhete.toString()); // Usa o toString() que já formata os detalhes

                        System.out.println("\nProcessando pagamento...");
                        System.out.printf("Carteira anterior: %.2f€\n", carteiraAnterior);
                        System.out.printf("Pagamento efetuado: -%.2f€\n", novoBilhete.getPreco());
                        System.out.printf("Carteira atual: %.2f€\n", adepto.getCarteira());

                        lugarComprado = true; // Avança para o próximo bilhete (se houver)
                    } else {
                        // Não tinha dinheiro, a compra falhou
                        System.out.println("A compra falhou.");
                        return;
                    }
                }
            }
        }
        System.out.println("\nSeja bem-vindo ao " + estadio.getNome());
        // A mensagem "Menu" será impressa pelo loop principal quando este método terminar.
    }

    /**
     * Permite que um adepto já existente compre bilhetes adicionais.
     * Começa por pedir o ID do adepto.
     */
    private void processoComprarMaisBilhetes() {
        System.out.println("\n--- Comprar Mais Bilhetes ---");

        // Verifica se existe algum adepto registado
        if (estadio.getAdeptos().isEmpty()) {
            System.out.println("Não existem adeptos registados. Por favor, crie um novo adepto primeiro (opção 2).");
            return; // Volta para o menu principal
        }

        System.out.print("Por favor, insira o seu ID de Adepto (ex: AD001): ");
        String idAdeptoInput = leitor.nextLine().trim();

        Adepto adeptoEncontrado = null;
        // Procura o adepto na lista do estádio
        for (Adepto adepto : estadio.getAdeptos()) {
            // Compara os IDs ignorando maiúsculas/minúsculas para ser mais amigável
            if (adepto.getId().equalsIgnoreCase(idAdeptoInput)) {
                adeptoEncontrado = adepto;
                break; // Encontrou o adepto, pode parar de procurar
            }
        }

        // Verifica se o adepto foi encontrado
        if (adeptoEncontrado != null) {
            // Se encontrou, reutiliza o processo de compra de bilhete que já temos!
            processoCompraBilhete(adeptoEncontrado);
        } else {
            System.out.println("ERRO: Adepto com o ID '" + idAdeptoInput + "' não foi encontrado.");
        }
    }

    /**
     * Guia um adepto através do processo de compra de produtos numa rolote.
     */
    private void processoComprarComida() {
        System.out.println("\n--- Comprar Comida ---");

        // Passo 1: Identificar o Adepto (reutilização de lógica)
        if (estadio.getAdeptos().isEmpty()) {
            System.out.println("Não existem adeptos registados.");
            return;
        }
        System.out.print("Por favor, insira o seu ID de Adepto (ex: AD001): ");
        String idAdeptoInput = leitor.nextLine().trim();
        Adepto adeptoComprador = null;
        for (Adepto adepto : estadio.getAdeptos()) {
            if (adepto.getId().equalsIgnoreCase(idAdeptoInput)) {
                adeptoComprador = adepto;
                break;
            }
        }
        if (adeptoComprador == null) {
            System.out.println("ERRO: Adepto com o ID '" + idAdeptoInput + "' não foi encontrado.");
            return;
        }

        // Mostra o nome e o saldo do adepto antes de começar a compra.
        System.out.printf("Bem-vindo, %s(%s) O seu saldo atual é de %.2f€\n",
                adeptoComprador.getNome(), adeptoComprador.getId(), adeptoComprador.getCarteira());

        // Passo 2: Escolher a Rolote
        List<Rolote> rolotesAbertas = new ArrayList<>();
        for (Rolote rolote : estadio.getRolotes()) {
            if (rolote.isAberto()) {
                rolotesAbertas.add(rolote);
            }
        }
        if (rolotesAbertas.isEmpty()) {
            System.out.println("Lamentamos, não existem rolotes abertas de momento.");
            return;
        }
        System.out.println("\n=== Rolotes disponíveis: ===");
        for (int i = 0; i < rolotesAbertas.size(); i++) {
            System.out.println((i + 1) + ". " + rolotesAbertas.get(i).getNome());
        }
        int escolhaRolote = -1;
        do {
            try {
                System.out.print("Escolha a rolote (pelo número): ");
                escolhaRolote = leitor.nextInt();
            } catch (InputMismatchException e) {
                leitor.next();
            }
        } while (escolhaRolote < 1 || escolhaRolote > rolotesAbertas.size());
        leitor.nextLine(); // Limpa o buffer
        Rolote roloteEscolhida = rolotesAbertas.get(escolhaRolote - 1);

        // Passo 3: Criar o carrinho e adicionar produtos
        Map<Produto, Integer> carrinho = new HashMap<>();
        String continuarAComprar;
        do {
            System.out.println("\n--- Menu da Rolote: " + roloteEscolhida.getNome() + " ---");
            List<Produto> produtosDisponiveis = roloteEscolhida.getProdutos();
            for (int i = 0; i < produtosDisponiveis.size(); i++) {
                System.out.println( produtosDisponiveis.get(i).toString());
            }

            int escolhaProduto = -1;
            do {
                try {
                    System.out.print("Escolha um produto para adicionar ao carrinho (pelo número): ");
                    escolhaProduto = leitor.nextInt();
                } catch (InputMismatchException e) {
                    leitor.next();
                }
            } while (escolhaProduto < 1 || escolhaProduto > produtosDisponiveis.size());
            Produto produtoEscolhido = produtosDisponiveis.get(escolhaProduto - 1);

            int quantidade = 0;
            do {
                try {
                    System.out.print("Quantidade: ");
                    quantidade = leitor.nextInt();
                } catch (InputMismatchException e) {
                    leitor.next();
                }
            } while (quantidade <= 0);
            leitor.nextLine(); // Limpa o buffer

            // Adiciona ao carrinho (soma a quantidade se o produto já lá estiver)
            carrinho.put(produtoEscolhido, carrinho.getOrDefault(produtoEscolhido, 0) + quantidade);
            System.out.println(quantidade + "x " + produtoEscolhido.getNome() + " adicionado(s) ao carrinho.");

            System.out.print("Deseja adicionar mais algum item? (S/N): ");
            continuarAComprar = leitor.nextLine().trim();
        } while (continuarAComprar.equalsIgnoreCase("S"));

        // Passo 4: Checkout
        if (carrinho.isEmpty()) {
            System.out.println("Carrinho vazio. Nenhuma compra efetuada.");
            return;
        }

        // 4.1: Pré-verificação do stock de TODOS os itens no carrinho
        boolean stockSuficienteParaTudo = true;
        for (Map.Entry<Produto, Integer> itemCarrinho : carrinho.entrySet()) {
            Produto produto = itemCarrinho.getKey();
            Integer quantidadePedida = itemCarrinho.getValue();
            if (produto.getQuantidadeStock() < quantidadePedida) {
                System.out.printf("ERRO: Stock insuficiente para '%s'. Pedido: %d, Disponível: %d\n",
                        produto.getNome(), quantidadePedida, produto.getQuantidadeStock());
                stockSuficienteParaTudo = false;
            }
        }

        // 4.2: Só avança para o pagamento se houver stock para tudo
        if (stockSuficienteParaTudo) {
            double totalCompra = 0;
            for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
                totalCompra += entry.getKey().getPreco() * entry.getValue();
            }
            System.out.printf("\nTotal da sua compra: %.2f€\n", totalCompra);

            // Tenta processar o pagamento
            if (adeptoComprador.comprarComida(totalCompra)) {
                // Se o pagamento for bem-sucedido, atualiza o stock de cada produto
                for (Map.Entry<Produto, Integer> entry : carrinho.entrySet()) {
                    // Aqui já sabemos que a venda será bem-sucedida porque já verificámos o stock
                    roloteEscolhida.vendeProduto(entry.getKey(), entry.getValue());
                }
                System.out.println("Obrigado pela sua compra!");
            } else {
                // A mensagem de erro de saldo insuficiente já é mostrada pelo método do adepto
                System.out.println("A transação foi cancelada.");
            }
        } else {
            // Se o stock não era suficiente, informa o utilizador e cancela tudo.
            System.out.println("\nA sua compra não pode ser processada devido a falta de stock. A transação foi cancelada.");
        }
    }

    /**
     * Recolhe e exibe um relatório completo do estado atual do estádio.
     */
    private void processoVerEstatisticas() {
        System.out.println("\n--- RELATÓRIO DE ESTATÍSTICAS DO ESTÁDIO ---");

        // 1. Informações Gerais
        System.out.println("\n=== GERAL ===");
        System.out.println("Nome: " + estadio.getNome());
        System.out.println("Capacidade Máxima: " + estadio.getCapacidadeMaxima() + " lugares");
        Jogo jogo = estadio.getJogo();
        if (jogo != null) {
            LocalDateTime dataDoJogo = LocalDateTime.of(2025, 9, 11, 20, 45);
            DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern("HH:mm");
            System.out.println("Jogo Agendado: " + jogo.getEquipaCasa().getNome() + " vs " + jogo.getEquipaVisitante().getNome());
            System.out.println("\nData: " + dataDoJogo.format(formatadorData));
            System.out.println("Hora: " + dataDoJogo.format(formatadorHora));
        }

        // 2. Ocupação dos Setores
        System.out.println("\n=== OCUPAÇÃO ===");
        int totalLugaresOcupados = 0;
        for (Setor setor : estadio.getSetores()) {
            totalLugaresOcupados += (setor.getCapacidade() - setor.verificarDisponibilidade());
        }
        double percentagemOcupacao = (double) totalLugaresOcupados / estadio.getCapacidadeMaxima() * 100;
        System.out.printf("Ocupação Geral: %.2f%% (%d/%d)\n", percentagemOcupacao, totalLugaresOcupados, estadio.getCapacidadeMaxima());

        System.out.println("\nDetalhes por Setor:");
        for (Setor setor : estadio.getSetores()) {
            int ocupadosSetor = setor.getCapacidade() - setor.verificarDisponibilidade();
            System.out.printf("- Setor %s: %d/%d lugares ocupados.\n", setor.getId(), ocupadosSetor, setor.getCapacidade());
            setor.mostrarLugares(); // Mostra o mapa visual de cada setor
            System.out.println();
        }

        // 3. Rolotes
        System.out.println("\n=== ROLOTES ===");
        if (estadio.getRolotes().isEmpty()) {
            System.out.println("Nenhuma rolote registada no estádio.");
        } else {
            for (Rolote rolote : estadio.getRolotes()) {
                System.out.printf("- Rolote #%d: '%s'\n",
                        rolote.getId(),
                        rolote.getNome());

                System.out.printf("  Estado: %s\n", (rolote.isAberto() ? "Aberta" : "Fechada"));

                System.out.println("  Produtos disponíveis:");
                List<Produto> produtos = rolote.getProdutos();
                if (produtos.isEmpty()) {
                    System.out.println("     (Sem produtos para mostrar)");
                } else {
                    for (Produto produto : produtos) {
                        System.out.printf("  * %s - %.2f€ (stock: %d)\n",
                                produto.getNome(),
                                produto.getPreco(),
                                produto.getQuantidadeStock());
                    }
                }
                System.out.printf("  Faturação: %.2f€\n\n", rolote.getFaturamentoDiario());
            }
        }

        // 4. Adeptos
        System.out.println("\n=== ADEPTOS ===");
        if (estadio.getAdeptos().isEmpty()) {
            System.out.println("Nenhum adepto registado no sistema.");
        } else {
            for (Adepto adepto : estadio.getAdeptos()) {
                System.out.printf("- Nome: %s\n", adepto.getNome());
                System.out.printf("  ID: %s\n", adepto.getId());

                List<Bilhete> bilhetes = adepto.getBilhetes();
                System.out.printf("  Bilhetes comprados: %d\n", bilhetes.size());

                // Ciclo para listar os detalhes de cada bilhete
                if (!bilhetes.isEmpty()) {
                    for (Bilhete bilhete : bilhetes) {
                        System.out.printf("  * Setor %s, Lugar %s, Preço: %.2f€\n",
                                bilhete.getSetor().getId(),
                                bilhete.getLugar(),
                                bilhete.getPreco());
                    }
                }
                System.out.println(); // Adiciona uma linha em branco para separar os adeptos
            }
        }
        System.out.println("\n--- FIM DO RELATÓRIO ---");
    }
}