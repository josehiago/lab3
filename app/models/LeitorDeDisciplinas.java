package models;

import java.io.*;
import java.util.*;


public class LeitorDeDisciplinas {
	
	private Map<String, List<String>> mapa;
	private static LeitorDeDisciplinas instancia;
	
	private LeitorDeDisciplinas() {
		mapa = new HashMap<String, List<String>>();
		try {
			carregaDisciplinasDoArquivo();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void carregaDisciplinasDoArquivo() throws IOException {
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader("disciplinas-do-curso.txt"));
			String line;
			while ((line = inputStream.readLine()) != null) {
				String[] dados = line.split(", ");
				mapa.put(dados[0]+"-"+dados[1], new ArrayList<String> ());
				String[] nomesDosPreRequisitos = dados[6].split("-");
				for(String nomeDoPreRequisito : nomesDosPreRequisitos) {
					mapa.get(dados[0]+"-"+dados[1]).add(nomeDoPreRequisito);
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	} 
	
	
	public static LeitorDeDisciplinas getInstance() {
		if(instancia == null) {
			instancia = new LeitorDeDisciplinas();
		}
		return instancia;
	}
	
	
	public String[] getInformacoesDasDisciplinas() {
		String[] informacoes = mapa.keySet().toArray(new String[mapa.keySet().size()]);
		return informacoes;
	}
	
	
	public List<String> getNomesDosPreRequisitosDeDisciplina(String nomeDaDisciplina) {
		for(String informacaoDeDisciplina : mapa.keySet()) {
			if(informacaoDeDisciplina.split("-")[0].equals(nomeDaDisciplina))
				return mapa.get(informacaoDeDisciplina);
		}
		return null;
	}
	
	
	public int getNumeroDeCreditosDeDisciplina(String nomeDaDisciplina) {
		for(int i = 0; i < getInformacoesDasDisciplinas().length; i++) {
			if(getInformacoesDasDisciplinas()[i].split("-")[0].equals(nomeDaDisciplina))
				return Integer.parseInt(getInformacoesDasDisciplinas()[i].split("-")[1]);
		}
		return 0;
	}

}
