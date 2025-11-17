# Gestão de Estádio - Projeto Final de POO

Este projeto é o trabalho final da disciplina de Programação Orientada a Objetos (POO) do curso de Software Developer na CESAE Digital. O objetivo é simular um sistema de gestão de um estádio de futebol, aplicando os conceitos fundamentais de POO.

## 📖 Sobre o Projeto

O sistema é uma aplicação de consola em Java que permite gerir as diferentes facetas de um estádio de futebol, incluindo a venda de bilhetes, a gestão de equipas e o controlo de custos e receitas.

## ✨ Funcionalidades Principais

* **Gestão de Jogos:** Criar e agendar novos jogos.
* **Venda de Bilhetes:** Vender bilhetes para jogos, distinguindo entre tipos de bilhete (normal, VIP, etc.).
* **Gestão de Pessoal:** Administrar funcionários e equipas (`Equipa`).
* **Gestão de Adeptos:** Manter um registo de adeptos e sócios (`Adepto`).
* **Controlo Financeiro:** Calcular receitas de bilheteira (`Bilhete`) e custos operacionais.
* **Gestão de Recursos:** Controlar produtos de merchandising ou restauração (`Produto`).

## 🛠️ Tecnologias e Conceitos Aplicados

O foco principal deste projeto foi a correta aplicação dos pilares da Programação Orientada a Objetos:

* **Linguagem:** Java 24
* **Encapsulamento:** Proteção dos dados das classes (atributos privados com `getters` e `setters`).
* **Composição:** Relação "tem-um" (o `Estadio` "tem-um" conjunto de `Equipas`, `Jogos`, etc.).
* **Estruturas de Dados:** Uso de `ArrayLists` para gerir coleções de objetos.
* **Separação de Responsabilidades:** Divisão clara da lógica em classes (`MenuController` para a interação com o utilizador, classes de Modelo como `Jogo` e `Adepto` para os dados).

## 🚀 Como Executar

Este é um projeto de consola Java. Para o executar, siga estes passos:

1.  Certifique-se de que tem o Java Development Kit (JDK) instalado.

2.  Clone o repositório:
    ```bash
    git clone https://github.com/JPMartyns/java-final-project.git
    cd java-final-project
    ```
    
3.  Compile todos os ficheiros `.java`:
    ```bash
    javac *.java
    ```
4.  Execute o programa principal:
    ```bash
    java Main
    ```
