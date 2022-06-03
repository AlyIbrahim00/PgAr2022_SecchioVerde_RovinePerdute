package it.unibs.progettoarnaldo.rovineperdute;

import java.util.ArrayList;

public class Insediamento {

	private String nome;
	private int precedente;
	private double carburante;
	private double x;
	private double y;
	private double h;
	private ArrayList<Integer> sentieri;
	
	public Insediamento(String nome, int precedente, double carburante, double x, double y, double z, ArrayList<Integer> sentieri) {
		this.nome = nome;
		this.precedente = precedente;
		this.carburante = carburante;
		this.x = x;
		this.y = y;
		this.h = z;
		this.sentieri = sentieri;
	}

	public String getNome() {
		return nome;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getH() {
		return h;
	}

	public ArrayList<Integer> getSentieri() {
		return sentieri;
	}

	public int getPrecedente() {
		return precedente;
	}

	public void setPrecedente(int precedente) {
		this.precedente = precedente;
	}

	public double getCarburante() {
		return carburante;
	}

	public void setCarburante(double carburante) {
		this.carburante = carburante;
	}

	
	
	
	
	
}
