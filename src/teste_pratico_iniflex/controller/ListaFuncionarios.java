package teste_pratico_iniflex.controller;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import teste_pratico_iniflex.model.Funcionario;

public class ListaFuncionarios {
	
	private String path;
	
	public ListaFuncionarios(String path) {
		this.path = path;
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> criarLista() {
		JSONObject jsonObject;
		JSONParser parser = new JSONParser();
		List<Funcionario> funcionarios = new ArrayList<>();
		
		try {
			
			jsonObject  = (JSONObject) parser.parse(new FileReader(path));
			JSONArray lista = (JSONArray) jsonObject.get("funcionarios");
			lista.forEach(l ->  funcionarios.add(parserFuncionario((JSONObject) l)));

			
		} catch (ParseException |IOException e) {
			e.printStackTrace();
		}
		return funcionarios;
	}
	
	public Map<String, List<Funcionario>> criarMap(List<Funcionario> funcionarios) {
		Map<String, List<Funcionario>> mapFuncionarios = new HashMap<String, List<Funcionario>>();
		for (Funcionario funcionario : funcionarios) {
			String funcao = funcionario.getFuncao();
			List<Funcionario> funcionariosEncontrados = mapFuncionarios.get(funcao);
			if (funcionariosEncontrados == null) {
				funcionariosEncontrados = new ArrayList<>();
				funcionariosEncontrados.add(funcionario);
				mapFuncionarios.put(funcao, funcionariosEncontrados);
				continue;
			}
			funcionariosEncontrados.add(funcionario);
		}
		return mapFuncionarios;
	}
	
	public void removerUsuario(String nome, List<Funcionario> funcionarios) {
		Integer cont = 0;
		for (Funcionario funcionario : funcionarios) {
			cont ++;
			if (funcionario.getNome().equals(nome)) {
				break;
			}
		}
		funcionarios.remove(cont - 1);
	}
	
	private Funcionario parserFuncionario(JSONObject funcionario) {
		LocalDate data_nascimento = LocalDate.parse(funcionario.get("data_nascimento").toString());
		BigDecimal salario = new BigDecimal(funcionario.get("salario").toString());
		Funcionario funcionario_add = new Funcionario(
				funcionario.get("nome").toString(), data_nascimento, salario, funcionario.get("funcao").toString()
				);
		return funcionario_add;
	}

	public void aumentarSalario(BigDecimal porcentagem, List<Funcionario> funcionarios) {
		for (Funcionario funcionario : funcionarios) {
				BigDecimal salarioAtual = funcionario.getSalario();
				BigDecimal salarioComAumento = salarioAtual.add(salarioAtual.multiply(porcentagem));
				funcionario.setSalario(new BigDecimal(salarioComAumento.toString()));
		}
	}
}
