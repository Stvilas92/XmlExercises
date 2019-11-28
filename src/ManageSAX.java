import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class ManageSAX {


	//Ejercicio 13
	public void getSax(String entradaXML) throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		ParserSAX16 parserSax = new ParserSAX16();
		parser.parse(entradaXML, parserSax);
	}

}


//Ejercicio 14
class ParserSAX extends DefaultHandler{
	String qName="";

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Comienzo del documento XML");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Fin del documento XML");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.qName=qName;
		System.out.print("<"+qName+">");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.print("</"+qName+">");

		this.qName="";
	}

	@Override
	public void characters(char[] ch,int start,int length)throws SAXException {
		String cad=new String(ch,start,length);
			System.out.print(cad);
	}
}


//Ejercicio 15
class ParserSAX15 extends DefaultHandler{
	String qName="";

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Comienzo del documento XML");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Fin del documento XML");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.qName=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("pelicula")) 
			System.out.println();
		this.qName="";
	}

	@Override
	public void characters(char[] ch,int start,int length)throws SAXException {
		String cad=new String(ch,start,length);
		if (qName.equals("titulo")) 
			System.out.print("Titulo = "+cad+"");
		if (qName.equals("nombre")) 
			System.out.print(", Nombre = "+cad+" ,");
		if (qName.equals("apellido")) 
			System.out.print(" Apellido = "+cad+" ");
	}
}



//Ejercicio 16
class ParserSAX16 extends DefaultHandler{
	String qName="";
	int directors = 0;
	String print ="";

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Comienzo del documento XML");
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("Fin del documento XML");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("director")) 
			directors++;
		this.qName=qName;
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		if (qName.equals("pelicula")) {
			if(directors >= 2) {
				System.out.println(print);				
			}else {
				System.out.println();
			}
			directors = 0;
			print="";
		}
		this.qName="";
	}

	@Override
	public void characters(char[] ch,int start,int length)throws SAXException {
		String cad=new String(ch,start,length);
		if (qName.equals("titulo")) 
			print += "Titulo = "+cad+"";
		if (qName.equals("nombre")) 
			print +=", Nombre = "+cad+" ,";
		if (qName.equals("apellido")) 
			print +=" Apellido = "+cad+" ";
	}
}

//Ejercicio 17
class ParserSAX17 extends DefaultHandler{
	String qName="";
	ArrayList<String> gender = new ArrayList<String>();

	@Override
	public void startDocument() throws SAXException {
		System.out.println("Comienzo del documento XML");
	}

	@Override
	public void endDocument() throws SAXException {
		for(String g : gender) {
			System.out.println(" - "+g);
		}
		
		System.out.println("Fin del documento XML");
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		this.qName=qName;
		if(qName.equals("pelicula")) {
			for(int i = 0; i< attributes.getLength(); i++) {
				attributes.ge
				if(attributes.getLocalName(i).equals("genero"))
					checkGenero(attributes.getValue(i));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		this.qName="";
	}

	@Override
	public void characters(char[] ch,int start,int length)throws SAXException {
		String cad=new String(ch,start,length);
	}
	
	
	public void checkGenero(String genderToCheck) {
		boolean flagRepeats = false;
		for(String g : gender) {
			if(g.equals(genderToCheck)) {
				flagRepeats = true;
			}
		}
		if(!flagRepeats)
			gender.add(genderToCheck);
	}
}




