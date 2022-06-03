package it.unibs.progettoarnaldo.rovineperdute;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public class Main {

	private static final String TEAM_1 = "Tonatiuh";
	private static final String TEAM_2 = "Metztli";

	public static void main(String[] args) {
		
		int chiave = 0;
		double x = 0;
		double y = 0;
		double h = 0;
		double carburante1 = 0.0;
		double carburante2 = 0.0;
		String nome = "";
		Percorso percorso = new Percorso();
		Scrittura scrittura = new Scrittura();
		ArrayList<Integer> sentieri = new ArrayList<Integer>();
		ArrayList<Integer> sentieriTemp = new ArrayList<Integer>();
		ArrayList<Integer> percorsiPossibili = new ArrayList<Integer>();
		ArrayList<Integer> cittaToccate1 = new ArrayList<Integer>();
		ArrayList<Integer> cittaToccate2 = new ArrayList<Integer>();
		HashMap<Integer,Insediamento> mappaInsediamenti = new HashMap<>();
		
		XMLInputFactory xmlif = null;
		XMLStreamReader xmlr = null;
		//Lettura del file xml;
		try {
			xmlif = XMLInputFactory.newInstance();
			xmlr = xmlif.createXMLStreamReader("src/it/unibs/progettoarnaldo/rovineperdute/Dati2000.xml", new FileInputStream("src/it/unibs/progettoarnaldo/rovineperdute/Dati2000.xml"));
		} catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader:");
			System.out.println(e.getMessage());
		}
		
		try {
			while(xmlr.hasNext()) {
				
				if(xmlr.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if(xmlr.getLocalName().equals("city")) {
						for (int i = 0; i < xmlr.getAttributeCount(); i++) {
							switch(i) {
								case 0:
									chiave = Integer.parseInt(xmlr.getAttributeValue(i));
									break;
								case 1:
									nome = xmlr.getAttributeValue(i);
									break;
								case 2:
									x = Double.parseDouble(xmlr.getAttributeValue(i));
									break;
								case 3:
									y = Double.parseDouble(xmlr.getAttributeValue(i));
									break;
								case 4:
									h = Double.parseDouble(xmlr.getAttributeValue(i));
									break;
							}
						}
					}
					else if(xmlr.getLocalName().equals("link")) {
						sentieri.add(Integer.parseInt(xmlr.getAttributeValue(0)));
					}
				}
				else if(xmlr.getEventType() == XMLStreamConstants.END_ELEMENT && xmlr.getLocalName().equals("city")) {
					sentieriTemp = (ArrayList<Integer>) sentieri.clone();
					mappaInsediamenti.put(chiave, new Insediamento(nome, 0, -1.0, x, y, h, sentieriTemp));
					sentieri.clear();
				}
				
				xmlr.next();
			}
		}
		 catch (Exception e)
        {
            System.out.println("Errore: non esiste una nuova riga da leggere\n");
        }
		percorsiPossibili.add(0); 
		mappaInsediamenti.get(0).setCarburante(0);
		//chiamata metodo per trovare il percorso migliore per il team 1
		percorso.calcoloPercorso(TEAM_1, mappaInsediamenti, percorsiPossibili);
		//salvataggio carburante totale speso e citta in cui si è passati 
		carburante1 = percorso.carburanteFinale(mappaInsediamenti);
		percorso.cittaToccate(mappaInsediamenti, cittaToccate1);
		
		//tolgo i valori di carburante e le città precendenti per richiamare il metodo con team 2
		for (HashMap.Entry<Integer, Insediamento> entry : mappaInsediamenti.entrySet()) {
			 mappaInsediamenti.get(entry.getKey()).setCarburante(-1.0);
			 mappaInsediamenti.get(entry.getKey()).setPrecedente(0);
		}
		percorsiPossibili.clear();
		percorsiPossibili.add(0);
		mappaInsediamenti.get(0).setCarburante(0);
		
		//chiamata metodo per trovare il percorso migliore per il team 2
		percorso.calcoloPercorso(TEAM_2, mappaInsediamenti, percorsiPossibili);
		//salvataggio carburante totale speso e citta in cui si è passati 
		carburante2 = percorso.carburanteFinale(mappaInsediamenti);
		percorso.cittaToccate(mappaInsediamenti, cittaToccate2);
		//chiamata metodo per la scrittura del file Routes.xml
		scrittura.scritturaOutput(mappaInsediamenti, carburante1, carburante2, cittaToccate1, cittaToccate2);
		
	}

}
