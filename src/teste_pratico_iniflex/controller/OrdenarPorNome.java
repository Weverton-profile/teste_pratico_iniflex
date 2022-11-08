package teste_pratico_iniflex.controller;

import java.util.Comparator;

import teste_pratico_iniflex.model.Funcionario;

public class OrdenarPorNome implements Comparator<Funcionario> {

	@Override
	public int compare(Funcionario a, Funcionario b) {
		return a.getNome().compareTo(b.getNome());
	}
}
