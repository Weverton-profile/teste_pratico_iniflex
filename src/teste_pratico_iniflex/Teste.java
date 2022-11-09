package teste_pratico_iniflex;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import teste_pratico_iniflex.controller.ListaFuncionarios;
import teste_pratico_iniflex.controller.OrdenarPorNome;
import teste_pratico_iniflex.model.Funcionario;

public class Teste {
	
	public static void main(String[] args) {
		
		List<Funcionario> funcionarios = new ArrayList<>();
		Map<String, List<Funcionario>> mapFuncionarios = new HashMap<String, List<Funcionario>>();
		
		// CRIANDO A LISTA DE FUNCIONARIOS APARTIR DE UM ARQUIVO JSON
		ListaFuncionarios cf = new ListaFuncionarios("./src/funcionarios.json");
		funcionarios = cf.criarLista();
		
		// LISTANDO TODOS OS FUNCIONARIOS NA ORDEM PASSADA
		System.out.println("Nome   |  Nascimento  |  Salario  |   Funcão |");
		funcionarios.forEach(f -> System.out.println(f));
		
		// FUNCIONARIOS SEM O JOAO
		cf.removerUsuario("João", funcionarios);
		System.out.println();
		System.out.println("Lista sem o João");
		System.out.println("Nome   |  Nascimento  |  Salario  |   Funcão |");
		funcionarios.forEach(f -> System.out.println(f));
		
		// AUMENTO DE SALARIO EM 10%
		System.out.println();
		cf.aumentarSalario(new BigDecimal("0.1"), funcionarios);
		System.out.println("Salario com aumento de 10%");
		System.out.println("Nome   |  Nascimento  |  Salario  |   Funcão |");
		funcionarios.forEach(f -> System.out.println(f));
		
		// FUNCIONARIOS POR FUNCAO
		System.out.println();
		System.out.println("Organizados por Função");
		mapFuncionarios = cf.criarMap(funcionarios);
		for (Iterator<Map.Entry<String, List<Funcionario>>> entries = mapFuncionarios.entrySet().iterator(); entries.hasNext(); ) {
		    Map.Entry<String, List<Funcionario>> entry = entries.next();
		    System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		
		// FUNCIONARIOS COM ANIVERSARIO NOS MESES 10 E 12
		System.out.println();
		System.out.println("Aniversário no mês 10 e 12");
		for (Funcionario funcionario : funcionarios) {
			if(funcionario.getDataNascimento().getMonthValue() == 10 || funcionario.getDataNascimento().getMonthValue() == 12) {
				System.out.println(funcionario);
			}
		}
		
		// FUNCIONARIO COM A MAIOR IDADE
		System.out.println();
		System.out.println("Funcionário com a maior idade");
		Funcionario maisVelho = funcionarios.get(1);
		System.out.println(maisVelho);
		for (Funcionario funcionario : funcionarios) {
            if (funcionario.getDataNascimento().getYear() <= maisVelho.getDataNascimento().getYear()) {
            		maisVelho = funcionario;
            }  		
        }
		Period diff = Period.between(maisVelho.getDataNascimento(), LocalDate.now());
		System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + diff.getYears());
		
		// FUNCIONARIOS ORDENADOR POR NOME
		System.out.println();
		System.out.println("Funcionários ordenados por nome");
		Collections.sort(funcionarios, new OrdenarPorNome());
		System.out.println("Nome   |  Nascimento  |  Salario  |   Funcão |");
		funcionarios.forEach(f -> System.out.println(f));
		
		// TOTAL DOS SALARIOS
		System.out.println();
		System.out.println("Total dos salários dos funcionários");
		BigDecimal total = new BigDecimal("0.0");
		for (Funcionario funcionario : funcionarios) {
			total = total.add(funcionario.getSalario());
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		System.out.println(df.format(total));
		
		// QUANTOS SALARIOS MINIMOS CADA FUNCIONARIO RECEBE
		System.out.println();
		df = new DecimalFormat("#");
		System.out.println("Quantos salários mínimos ganha cada funcionário");
		for (Funcionario funcionario : funcionarios) {
			System.out.println(
					"Nome: " + funcionario.getNome() + ", Função: " + funcionario.getFuncao() +
					", Recebe: " + df.format(funcionario.getSalario().divideToIntegralValue(new BigDecimal("1212.00"))) + " salários minimos.");
		}
	}
}
