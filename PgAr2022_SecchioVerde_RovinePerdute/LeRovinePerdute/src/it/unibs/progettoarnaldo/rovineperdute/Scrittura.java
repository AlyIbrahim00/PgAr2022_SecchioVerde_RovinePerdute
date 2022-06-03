package it.unibs.progettoarnaldo.rovineperdute;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class Scrittura {
	
	private static final String UTF = "utf-8";
	private static final String VERSION = "1.0";
	private static final String TEAM_1 = "Tonatiuh";
	private static final String TEAM_2 = "Metztli";

	/**
	 * metodo che scrive in output il file Routes.xml
	 * @param mappaInsediamenti
	 * @param carburante1
	 * @param carburante2
	 * @param citta1
	 * @param citta2
	 */
	public void scritturaOutput(HashMap<Integer,Insediamento> mappaInsediamenti, double carburante1, double carburante2, ArrayList<Integer> citta1, ArrayList<Integer> citta2) {
		
		XMLOutputFactory xmlof = null;
		XMLStreamWriter xmlw = null;
		try {
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("src/it/unibs/progettoarnaldo/rovineperdute/Routes.xml"), UTF);
			xmlw.writeStartDocument(UTF, VERSION);
		} 
		catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del writer:");
			System.out.println(e.getMessage());
		}
		
		try {
			xmlw.writeStartElement("routes");
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("team", TEAM_1);
			xmlw.writeAttribute("cost", Double.toString(carburante1));
			xmlw.writeAttribute("cities", Integer.toString(citta1.size()));
			for(int i = citta1.size()-1; i >= 0; i--) {
				xmlw.writeStartElement("city");
				xmlw.writeAttribute("id", Integer.toString(citta1.get(i)));
				xmlw.writeAttribute("name", mappaInsediamenti.get(citta1.get(i)).getNome());
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			xmlw.writeStartElement("route");
			xmlw.writeAttribute("team", TEAM_2);
			xmlw.writeAttribute("cost", Double.toString(carburante2));
			xmlw.writeAttribute("cities", Integer.toString(citta2.size()));
			for(int i = citta2.size()-1; i >= 0; i--) {
				xmlw.writeStartElement("city");
				xmlw.writeAttribute("id", Integer.toString(citta2.get(i)));
				xmlw.writeAttribute("name", mappaInsediamenti.get(citta2.get(i)).getNome());
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			xmlw.writeEndDocument();
			xmlw.flush();
			xmlw.close();

		}
		catch (Exception e) {
			System.out.println("Errore nella scrittura");
		}
		
	}
	
}
