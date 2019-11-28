import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XMLExercises {
	
	public static void main(String args[]) {
		ManageXML manageXML = new ManageXML(new File("C:\\Users\\alejandro\\eclipse-workspace\\XmlExercises\\src\\filmoteca.xml"));
		//XML
		try {
			
			manageXML.doc = manageXML.makeDocument(manageXML.getXmlFile().getAbsoluteFile().toString());
			
			//String[] peliculas= manageXML.getElemntValues("titulo");
			//Pelicula [] peliculasDir= manageXML.getPeliculas();
			//ArrayList<Pelicula> peliculasWithDir= manageXML.getPeliculasWhitDirectors(2);
			//ArrayList<String> generos = manageXML.getDiferentsGenero();
			//manageXML.tituloAddAtribute("Dune", "duracion", "120");	
//			boolean add = manageXML.addPelicula("Interestellar",
//					new Director[]{new Director("Christoper","Nolan"),new Director("Sam","Smith")},
//					2014, "Ficcion", "en");
//			if(add)
//				manageXML.grabarDOM(doc, "C:\\Users\\alejandro\\eclipse-workspace\\XmlExercises\\src\\filmoteca.xml");
			
			manageXML.addDirector("Sam", "Smith", "Alfredo", "Landa");
			manageXML.modifyDirector("Sam", "Smith", "Peter", "Jackson",manageXML.doc);
			
			//manageXML.deletePeliculaByTitulo("Interestellar");
//			Document docCompañia = manageXML.createNewDocument("C:\\Users\\alejandro\\eclipse-workspace\\XmlExercises\\src\\comp.xml",
//					manageXML.employeesArray);
//			
//			manageXML.grabarDOM(docCompañia, "C:\\Users\\alejandro\\eclipse-workspace\\XmlExercises\\src\\comp.xml");
			
			//SAX
			//ManageSAX manageSAX = new ManageSAX();
			//manageSAX.getSax("C:\\Users\\alejandro\\eclipse-workspace\\XmlExercises\\src\\filmoteca.xml");
//			System.out.println("Fin");
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :"+e.getMessage());
		}
		
		
		
		//SAX
		try {
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error :"+e.getMessage());
		}
	}
	
	
	
}
