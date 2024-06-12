package principal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import funcionario.Funcionario;

public class Principal {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario(
                "Maria", LocalDate.parse("18/10/2000", formatter),
                new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(
                new Funcionario("João", LocalDate.parse("12/05/1990", formatter),
                        new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(
                new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter),
                        new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(
                new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter),
                        new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(
                new Funcionario("Alice", LocalDate.parse("05/01/1998", formatter),
                        new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(
                new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter),
                        new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(
                new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter),
                        new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(
                new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter),
                        new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(
                new Funcionario("Heloísa", LocalDate.parse("24/05/2003", formatter),
                        new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(
                new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter),
                        new BigDecimal("2799.93"), "Gerente"));

        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("3.1 - Inserir todos os funcionários, na mesma ordem e informações da tabela acima.");
        System.out.println();
        imprimirFuncionarios(funcionarios, formatter);

        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("3.2 - Remover o funcionário João da lista.");
        System.out.println("3.3 - Imprimir todos os funcionários com todas suas informações, sendo que:\r\n" + //
                "- informação de data deve ser exibido no formato dd/mm/aaaa;\r\n" + //
                "- informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.");
        System.out.println();
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        imprimirFuncionarios(funcionarios, formatter);
        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println(
                "3.4 - Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.");
        System.out.println();

        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista
        // de funcionários com novo valor.
        aumentarSalarios(funcionarios, new BigDecimal("1.10"));
        imprimirFuncionarios(funcionarios, formatter);

        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função”
        // e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        // 3.6 – Imprimir os funcionários, agrupados por função.
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao, formatter);

        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        imprimirFuncionariosAniversariantes(funcionarios, Arrays.asList(Month.OCTOBER, Month.DECEMBER), formatter);

        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e
        // idade.
        imprimirFuncionarioMaiorIdade(funcionarios, formatter);

        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("3.10 - Imprimir a lista de funcionários por ordem alfabética.");
        System.out.println();
        ordenarFuncionariosPorNome(funcionarios);
        imprimirFuncionarios(funcionarios, formatter);

        System.out.println("-------------------------------------");
        System.out.println();

        // 3.11 – Imprimir o total dos salários dos funcionários.
        imprimirTotalSalarios(funcionarios);

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando
        // que o salário mínimo é R$1212.00.
        imprimirSalariosMinimos(funcionarios, new BigDecimal("1212.00"));
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios,
            DateTimeFormatter formatter) {
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
            System.out.println("Salário: " + formatarNumero(funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        }
    }

    private static void aumentarSalarios(List<Funcionario> funcionarios, BigDecimal percentualAumento) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(percentualAumento);
            funcionario.setSalario(novoSalario);
        }
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        return funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao,
            DateTimeFormatter formatter) {
        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("3.6 - Imprimir os funcionários, agrupados por função.");
        System.out.println();

        funcionariosPorFuncao.entrySet().stream()
                .forEach(entry -> {
                    String funcao = entry.getKey();
                    List<Funcionario> funcionarios = entry.getValue();

                    System.out.println("##### " + funcao + " #####");
                    imprimirFuncionarios(funcionarios, formatter);
                });
    }

    private static void imprimirFuncionariosAniversariantes(List<Funcionario> funcionarios, List<Month> meses,
            DateTimeFormatter formatter) {
        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("3.8 - Imprimir os funcionários que fazem aniversário no mês 10 e 12.");
        System.out.println();
        for (Funcionario funcionario : funcionarios) {
            if (meses.contains(funcionario.getDataNascimento().getMonth())) {
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
                System.out.println();
            }
        }
    }

    private static void imprimirFuncionarioMaiorIdade(List<Funcionario> funcionarios, DateTimeFormatter formatter) {
        System.out.println("-------------------------------------");
        System.out.println();
        System.out.println("3.9 - Encontrar funcionário com maior idade");
        System.out.println();

        Funcionario maiorIdade = Collections.max(funcionarios,
                Comparator.comparing(Funcionario::getDataNascimento).reversed());

        long idade = LocalDate.now().getYear() - maiorIdade.getDataNascimento().getYear();
        System.out.println("Nome: " + maiorIdade.getNome());
        System.out.println("Idade: " + idade + " anos");
        System.out.println();
    }

    private static void ordenarFuncionariosPorNome(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(funcionario -> funcionario.getNome()));
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        System.out.println("3.11 - Imprimir o total dos salários dos funcionários.");
        System.out.println();
        System.out.println(formatarNumero(
                funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add)));
        System.out.println();
        System.out.println("-------------------------------------");
        System.out.println();
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios, BigDecimal salarioMinimo) {
        System.out.println(
                "3.12 - Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.");
        System.out.println();

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosPorFuncionario = funcionario.getSalario().divide(salarioMinimo, 2,
                    BigDecimal.ROUND_HALF_UP);

            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Salários Mínimos: " + formatarNumero(salariosPorFuncionario));
            System.out.println();
        }
        System.out.println("-------------------------------------");
    }

    private static String formatarNumero(BigDecimal numero) {
        return String.format("%,.2f", numero);
    }
}