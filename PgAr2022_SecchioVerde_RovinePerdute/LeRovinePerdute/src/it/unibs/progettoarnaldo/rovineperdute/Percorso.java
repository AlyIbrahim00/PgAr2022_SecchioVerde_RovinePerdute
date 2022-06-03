package it.unibs.progettoarnaldo.rovineperdute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Percorso {
	
	private static final String TEAM_1 = "Tonatiuh";
	private static final String TEAM_2 = "Metztli";

	/**
	 * metodo per calcolare il percorso migliore
	 * @param team
	 * @param mappaInsediamenti
	 * @param percorsiPossibili
	 */
	public void calcoloPercorso(String team, HashMap<Integer,Insediamento> mappaInsediamenti, ArrayList<Integer> percorsiPossibili) {
		
		int nodoMigliore = 0;
		//imposto trova rovine all'id delle rovine perdute
		int trovaRovine = trovaIdRovine(mappaInsediamenti);
		double costoMinimo = 0.0;
		boolean cambio = false;
		boolean presente = false;
		ArrayList<Integer> strade = new ArrayList<Integer>();
		
		//imposto il nodo migliore al primo elemento dell'array percorsiPossibili e il costoMinimo al suo carburante + il corburante neccessario per arrivare alle rovine perdute
		if(team.compareTo(TEAM_1) == 0) {
			costoMinimo = mappaInsediamenti.get(percorsiPossibili.get(0)).getCarburante() + algoritmo1(mappaInsediamenti, percorsiPossibili.get(0));
			nodoMigliore = percorsiPossibili.get(0);
		}
		else {
			costoMinimo = mappaInsediamenti.get(percorsiPossibili.get(0)).getCarburante() + algoritmo2(mappaInsediamenti, percorsiPossibili.get(0));
			nodoMigliore = percorsiPossibili.get(0);
		}
		//se percorsiPossibili ha piùi un elemento cerco se c'è un nodo migliore e imposto il nuovo costoMinimo
		if(percorsiPossibili.size() > 1) {
			for(int i = 1; i < percorsiPossibili.size(); i++) {
				if(team.compareTo(TEAM_1) == 0) {
					if(mappaInsediamenti.get(percorsiPossibili.get(i)).getCarburante() + algoritmo1(mappaInsediamenti, percorsiPossibili.get(i)) < costoMinimo) {
						nodoMigliore = percorsiPossibili.get(i);
						costoMinimo = mappaInsediamenti.get(percorsiPossibili.get(i)).getCarburante() + algoritmo1(mappaInsediamenti, percorsiPossibili.get(i));
					}
				}
				else {
					if(mappaInsediamenti.get(percorsiPossibili.get(i)).getCarburante() + algoritmo2(mappaInsediamenti, percorsiPossibili.get(i)) < costoMinimo) {
						nodoMigliore = percorsiPossibili.get(i);
						costoMinimo = mappaInsediamenti.get(percorsiPossibili.get(i)).getCarburante() + algoritmo2(mappaInsediamenti, percorsiPossibili.get(i));
					}
				}
			}
		}
		//se il nodoMigliore non è l'id delle rovine perdute aggiungo, se possibile, ai percorsiPossibili le città collegate al nodoMigliore 
		if(nodoMigliore != trovaRovine) {
			//clono l'array delle città collegate al nodoMigliore
			strade = (ArrayList<Integer>) mappaInsediamenti.get(nodoMigliore).getSentieri().clone();
			for(int i = 0; i < strade.size(); i++) {
				cambio = false;
				presente = false;
				if(team.compareTo(TEAM_1) == 0) {
					//se non è già presente un valore di carburante lo calcolo e lo aggiungo con il metodo calcolaCarburante
					if(mappaInsediamenti.get(strade.get(i)).getCarburante() == -1) {
						mappaInsediamenti.get(strade.get(i)).setCarburante(calcolaCarburante1(mappaInsediamenti, strade.get(i), nodoMigliore));
						cambio = true;
					}
					//se è già presente un valore di carburante controllo che quello nuovo sia minore e nel caso lo reinserisco
					else if(calcolaCarburante1(mappaInsediamenti, strade.get(i), nodoMigliore) < mappaInsediamenti.get(strade.get(i)).getCarburante()) {
						mappaInsediamenti.get(strade.get(i)).setCarburante(calcolaCarburante1(mappaInsediamenti, strade.get(i), nodoMigliore));
						cambio = true;
					}
					//se sono uguali controllo se il nuovo percorso tocca meno città
					else if(calcolaCarburante1(mappaInsediamenti, strade.get(i), nodoMigliore) == mappaInsediamenti.get(strade.get(i)).getCarburante()) {
						if(numCittaToccate(mappaInsediamenti, nodoMigliore) + 1 < numCittaToccate(mappaInsediamenti, strade.get(i))) {
							cambio = true;
						}
						//se i percorsi toccano lo stesso numero di città controllo quale tocca la città con id maggiore
						else if(numCittaToccate(mappaInsediamenti, nodoMigliore) + 1 == numCittaToccate(mappaInsediamenti, strade.get(i))) {
							if(idPiuAlto(mappaInsediamenti, nodoMigliore) < idPiuAlto(mappaInsediamenti, mappaInsediamenti.get(strade.get(i)).getPrecedente())) {
								cambio = true;
							}
						}
					}
					
				}
				//idem se è stato passato TEAM_2 
				else {
					if(mappaInsediamenti.get(strade.get(i)).getCarburante() == -1) {
						mappaInsediamenti.get(strade.get(i)).setCarburante(calcolaCarburante2(mappaInsediamenti, strade.get(i), nodoMigliore));
						cambio = true;
					}
					else if(calcolaCarburante2(mappaInsediamenti, strade.get(i), nodoMigliore) /*+ algoritmo2(mappaInsediamenti, strade.get(i))*/ < mappaInsediamenti.get(strade.get(i)).getCarburante()) {
						mappaInsediamenti.get(strade.get(i)).setCarburante(calcolaCarburante2(mappaInsediamenti, strade.get(i), nodoMigliore));
						cambio = true;
					}
					else if(calcolaCarburante2(mappaInsediamenti, strade.get(i), nodoMigliore) == mappaInsediamenti.get(strade.get(i)).getCarburante()) {
						if(numCittaToccate(mappaInsediamenti, nodoMigliore) + 1 < numCittaToccate(mappaInsediamenti, strade.get(i))) {
							cambio = true;
						}
						else if(numCittaToccate(mappaInsediamenti, nodoMigliore) + 1 == numCittaToccate(mappaInsediamenti, strade.get(i))) {
							if(idPiuAlto(mappaInsediamenti, nodoMigliore) < idPiuAlto(mappaInsediamenti, mappaInsediamenti.get(strade.get(i)).getPrecedente())) {
								cambio = true;
							}
						}
					}
				}
				//se una delle condizioni sovrastanti è vera inserisco nel precedenete dell'i-esimo elemento di strade il nodo migliore
				if(cambio) {
					mappaInsediamenti.get(strade.get(i)).setPrecedente(nodoMigliore);
					//controllo che l'i-esimo elemento di strade non ci sia già nell'array dei nodiPossibili, se così non fosse lo aggiungo
					for(int j = 0; j < percorsiPossibili.size(); j++) {
						if(percorsiPossibili.get(j).equals(strade.get(i))) {
							presente = true;
						}
					}
					if(!presente) {
						percorsiPossibili.add(strade.get(i));		
					}
				}
			}
			//tolgo nodoMigliore dai nodiPossibili
			for(int i = 0; i < percorsiPossibili.size(); i++) {
				if(percorsiPossibili.get(i) == nodoMigliore) {
					percorsiPossibili.remove(i);
					break;
				}	
			}			
			calcoloPercorso(team, mappaInsediamenti, percorsiPossibili);
		}
			
	}
	
	/**
	 * metodo per calcolare il carburante del team1
	 * @param mappaInsediamenti
	 * @param pos
	 * @param prec
	 * @return
	 */
	public double calcolaCarburante1(HashMap<Integer,Insediamento> mappaInsediamenti, int pos, int prec) {
		
		double cramorant = 0.0;
		double x1 = mappaInsediamenti.get(pos).getX();
		double y1 = mappaInsediamenti.get(pos).getY();
		double x2 = mappaInsediamenti.get(prec).getX();
		double y2 = mappaInsediamenti.get(prec).getY();
		
		cramorant = Math.sqrt(Math.pow(x2-x1, 2)+Math.pow(y2-y1, 2)) + mappaInsediamenti.get(prec).getCarburante();
		return cramorant;
	}
	
	/**
	 * metodo per calcolare il carburante del team 2
	 * @param mappaInsediamenti
	 * @param pos
	 * @param prec
	 * @return
	 */
	public double calcolaCarburante2(HashMap<Integer,Insediamento> mappaInsediamenti, int pos, int prec) {
		
		double lechonk = 0.0;
		int trovaRovine = 0;
		double h1 = mappaInsediamenti.get(pos).getH();
		double h2 = mappaInsediamenti.get(prec).getH();

		lechonk = Math.abs(h2 - h1) + mappaInsediamenti.get(prec).getCarburante();
		return lechonk;
	}
	
	/**
	 * metodo per calcolare la distanza tra il nodo attuale e le rovine perdute per team 1
	 * @param mappaInsediamenti
	 * @param pos
	 * @return
	 */
	public double algoritmo1(HashMap<Integer,Insediamento> mappaInsediamenti, int pos) {
		
		int trovaRovine = trovaIdRovine(mappaInsediamenti);
		double aggiunta = 0.0;
		double x1 = mappaInsediamenti.get(pos).getX();
		double y1 = mappaInsediamenti.get(pos).getY();
		double xRovine = mappaInsediamenti.get(trovaRovine).getX();
		double yRovine = mappaInsediamenti.get(trovaRovine).getY();
		
		aggiunta = Math.sqrt(Math.pow(xRovine-x1, 2)+Math.pow(yRovine-y1, 2));
		return aggiunta;
	}
	
	/**
	 *metodo per calcolare la distanza tra il nodo attuale e le rovine perdute per team 2
	 * @param mappaInsediamenti
	 * @param pos
	 * @return
	 */
	public double algoritmo2(HashMap<Integer,Insediamento> mappaInsediamenti, int pos) {
		
		int trovaRovine = trovaIdRovine(mappaInsediamenti);
		double aggiunta = 0.0;
		double h1 = mappaInsediamenti.get(pos).getH();
		double hRovine = mappaInsediamenti.get(trovaRovine).getH();
		
		aggiunta = Math.abs(hRovine - h1);
		return aggiunta;
	}
	
	/**
	 * metodo per trovare il carbirante totale per arrivare alle rovine perdute
	 * @param mappaInsediamenti
	 * @return
	 */
	public double carburanteFinale(HashMap<Integer,Insediamento> mappaInsediamenti) {
		
		int trovaRovine = trovaIdRovine(mappaInsediamenti);
		
		return mappaInsediamenti.get(trovaRovine).getCarburante();
	}
	
	/**
	 * metodo per trovare le città attraversate in un percorso
	 * @param mappaInsediamenti
	 * @param cittaAttraversate
	 */
	public void cittaToccate(HashMap<Integer,Insediamento> mappaInsediamenti, ArrayList<Integer> cittaAttraversate) {
		
		int idCitta = trovaIdRovine(mappaInsediamenti);
		do {
			cittaAttraversate.add(idCitta);
			idCitta = mappaInsediamenti.get(idCitta).getPrecedente();
		}while(idCitta != 0);
		cittaAttraversate.add(idCitta);
	}
	
	/**
	 * metodo per trovare l'id delle rovine perdute, che è sempre il più alto
	 * @param mappaInsediamenti
	 * @return
	 */
	public int trovaIdRovine(HashMap<Integer,Insediamento> mappaInsediamenti) {
		int trovaRovine = 0;
		for (HashMap.Entry<Integer, Insediamento> entry : mappaInsediamenti.entrySet()) {
			 if(entry.getKey() > trovaRovine) {
				 trovaRovine = entry.getKey();
			 }
		}
		return trovaRovine;
	}
	
	/**
	 * metodo per trovare il numero di città attraversate
	 * @param mappaInsediamenti
	 * @param pos
	 * @return
	 */
	public int numCittaToccate(HashMap<Integer,Insediamento> mappaInsediamenti, int pos) {
		
		int cnt = 0;
		do {
			cnt++;
			pos = mappaInsediamenti.get(pos).getPrecedente();
		}while(pos != 0);
		return cnt;
	}
	
	/**
	 * metodo per trovare l'id più alto tra le città attraversate
	 * @param mappaInsediamenti
	 * @param pos
	 * @return
	 */
	public int idPiuAlto(HashMap<Integer,Insediamento> mappaInsediamenti, int pos) {
		
		int alto = 0;
		do {
			if(pos > alto) {
				alto = pos;
			}
			pos = mappaInsediamenti.get(pos).getPrecedente();
		}while(pos != 0);
		return alto;
	}
	
}


