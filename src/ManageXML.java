import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class ManageXML {
	private File xmlFile;
	public Document doc ;
	private String tab = "\t";
	public Empleado[] employeesArray;

	private static final String ATRIBUTE_NAME_AÑO = "año";
	private static final String ATRIBUTE_NAME_GENERO = "genero";
	private static final String ATRIBUTE_NAME_IDIOMA = "idioma";
	private static final String NODE_NAME_PELICULA = "pelicula";
	private static final String NODE_NAME_DIRECTOR = "director";
	private static final String NODE_NAME_TITULO = "titulo";
	private static final String NODE_NAME_NOMBRE = "nombre";
	private static final String NODE_NAME_APELLIDO = "apellido";

	public ManageXML(File xmlFile){
		this.xmlFile = xmlFile;
		employeesArray = new Empleado[] {new Empleado("0", "Alejandro", "Vilas", "Alex", "1200"),
				new Empleado("1", "Alejandro", "Fernandez", "Nando", "1100"),
				new Empleado("2", "Arturo", "Vilas", "Turo", "12000")};
	}

	public File getXmlFile() {
		return xmlFile;
	}

	//Ejercicio 1
	public Document makeDocument(String path) {
		Document doc = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setIgnoringComments(true);
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			doc = docBuilder.newDocument();
		}catch(Exception e) {
			System.out.println("Unexpected error. Type : "+e.getMessage());
			e.printStackTrace();
		}
		return doc;
	}




	//Ejercicio 2
	/**
	 * get the values of an element if itself have a textNode as a first child
	 * @param name Node that have a text node as a first child 
	 * @return values of the first child of an element node
	 */
	public String[] getElemntValues(String name){
		String[] values;
		NodeList elementsInXML = doc.getElementsByTagName(name);
		int nodeLenght = elementsInXML.getLength();


		if(elementsInXML.getLength() > 0) {
			values = new String[nodeLenght]; 
			for (int i = 0; i < nodeLenght; i++) {
				values[i] = elementsInXML.item(i).getFirstChild().getNodeValue();
			}
			return values;
		} 
		return null;

	} 

	//Ejercicio 3
	public Pelicula[]  getPeliculas() {
		NodeList node = doc.getElementsByTagName(NODE_NAME_PELICULA);
		Pelicula[] peliculas = new Pelicula[node.getLength()];

		for (int i = 0; i < node.getLength(); i++) {
			NodeList listNodes = node.item(i).getChildNodes();
			NamedNodeMap listAtributes = node.item(i).hasAttributes()? node.item(i).getAttributes() : null;
			peliculas[i] = new Pelicula(); 

			for (int j = 0; j < listNodes.getLength(); j++) {
				Node item = listNodes.item(j);

				if (item.getNodeType() == Node.ELEMENT_NODE) {
					if (item.getNodeName().equals(NODE_NAME_TITULO)) {
						peliculas[i].setTitulo(item.getFirstChild().getNodeValue());
					}
					if (item.getNodeName().equals(NODE_NAME_DIRECTOR)) {
						Director director = new Director();
						NodeList itemDirector = item.getChildNodes();

						for (int k = 0; k < itemDirector.getLength(); k++) {
							if(itemDirector.item(k).getNodeName().equals("apellido"))
								director.setApellidos(itemDirector.item(k).getFirstChild().getNodeValue());
							if(itemDirector.item(k).getNodeName().equals("nombre"))
								director.setNombre(itemDirector.item(k).getFirstChild().getNodeValue());
						}
						peliculas[i].addDirector(director);
					} 
				}
			}

			for (int j = 0; j < listAtributes.getLength(); j++) {
				Node nodeAttribute = listAtributes.item(j);
				Object o = nodeAttribute.getNodeValue();
				if(nodeAttribute.getNodeName().equals(ATRIBUTE_NAME_AÑO)) {
					peliculas[i].setAño(Integer.parseInt((String)o));
				}else if(nodeAttribute.getNodeName().equals(ATRIBUTE_NAME_GENERO)){
					peliculas[i].setGenero((String)o);
				}else if(nodeAttribute.getNodeName().equals(ATRIBUTE_NAME_IDIOMA)){
					peliculas[i].setIdioma((String)o);
				}
			}
		}

		return peliculas;
	}


	//Funcion aparte
	public String[][] getListOfValues(String ... nodeNames) {
		String[][]  values = new String[nodeNames.length][];
		for (int i = 0; i <  nodeNames.length; i++) {
			String[] nodeValues = getElemntValues(nodeNames[i]);
			values[i] = nodeValues;
		}

		return values;
	}


	//Ejercicio 4
	public String getTree(Node node) {
		NodeList nodeList = node.getChildNodes();
		String tree = "";

		if(nodeList.getLength()>0) {
			tab += tab;
			tree = "\n"+tab+node.getNodeName();
			for(int i = 0; i < nodeList.getLength(); i++)
				tree += tab+getTree(nodeList.item(i))+"\n";
			return tree;
		}else {
			if(node.getNodeType() == Node.TEXT_NODE) {
				tab += tab;
				return "\n"+tab+node.getNodeValue();				
			}
			//else
			//System.out.println("\n\t\t\t"+node.getNodeName());
		}

		return tree;
	}


	//Ejercicio 5
	public ArrayList<Pelicula> getPeliculasWhitDirectors(int n) {
		Pelicula[] peliculas = getPeliculas();
		ArrayList<Pelicula> peliculasToReturn = new ArrayList<Pelicula>();

		for (Pelicula p : peliculas) {
			if(p.getDirector().size() == n)
				peliculasToReturn.add(p);
		}

		return peliculasToReturn;
	}

	//Ejercicio 6
	public ArrayList<String> getDiferentsGenero() {
		Pelicula[] peliculas = getPeliculas();
		ArrayList<String>genero = new ArrayList<String>();
		for (Pelicula p : peliculas) {
			if(genero.size() == 0)
				genero.add(p.getGenero());
			else {
				boolean flagRepeats = false;
				for(String s : genero) {
					if(s.equals(p.getGenero())) {
						flagRepeats = true;
					}
				}
				if(!flagRepeats)
					genero.add(p.getGenero());
			}
		}
		return genero;
	}


	//Ejercicio 7
	public void tituloAddAtribute(String title , String atributeName, String atributeValue) {
		Node tituloToModify = getElementByValue(title,NODE_NAME_TITULO);

		if(hasAtributeByName(tituloToModify, atributeName))
			return;
		else {
			Element e = (Element)tituloToModify;
			e.setAttribute(atributeName, atributeValue);
		}

	}


	public void peliculaDeleteByAtribute(String title , String atributeName) {
		Node tituloToModify = getElementByValue(title,NODE_NAME_TITULO);

		if(hasAtributeByName(tituloToModify, atributeName)) {
			Element e = (Element)tituloToModify;
			e.removeAttribute(atributeName);
		}
		else {
			return;
		}
	}

	//Support function
	public Node getElementByValue(String value,String elementName) {
		Node node;
		NodeList nodeList = doc.getElementsByTagName(elementName);

		for(int i = 0; i < nodeList.getLength(); i++){
			if(nodeList.item(i).getFirstChild().getNodeValue().equals(value))
				return nodeList.item(i);
		}
		return null;
	}

	//Support function
	public boolean hasAtributeByName(Node node, String atributeName) {
		if(!node.hasAttributes())
			return false;

		NamedNodeMap atributesList = node.getAttributes();
		for(int i = 0; i < atributesList.getLength(); i++){
			if(atributesList.item(i).getNodeName().equals(atributeName))
				return true;
		}

		return false;
	}


	//Ejercicio 8
	public boolean addPelicula(String titulo,Director[] director,int año, String genero, String idioma) {
		try {
			Element peliculaElement = doc.createElement(NODE_NAME_PELICULA);
			addPeliculaAtributes(año,genero,idioma,peliculaElement);

			peliculaElement.appendChild(doc.createTextNode("\n"));
			peliculaElement.appendChild(createTituloElement(titulo));
			peliculaElement.appendChild(doc.createTextNode("\n"));

			for(int i = 0 ; i < director.length; i++) {
				peliculaElement.appendChild(createDirectorElement(director[i]));
				peliculaElement.appendChild(doc.createTextNode("\n"));
			}

			Node mainTree =doc.getFirstChild();
			mainTree.appendChild(peliculaElement);
			return true;
		}catch(DOMException e) {
			e.printStackTrace();
			return false;
		}
	}

	//Support function
	public boolean addPeliculaAtributes(int año, String genero, String idioma,Element peliculaElement) {
		try {
			peliculaElement.setAttribute(ATRIBUTE_NAME_AÑO, "" + año);
			peliculaElement.setAttribute(ATRIBUTE_NAME_GENERO, genero);
			peliculaElement.setAttribute(ATRIBUTE_NAME_IDIOMA, idioma);
			return true;
		} catch (DOMException e) {
			return false;
		}
	}

	//Support function
	public Element createTituloElement(String titulo) {
		Element tituloElement =  doc.createElement(NODE_NAME_TITULO);
		tituloElement.appendChild(doc.createTextNode(titulo));

		return tituloElement;
	}

	//Support function
	public Element createDirectorElement(Director d) {
		Element directorElement =  doc.createElement(NODE_NAME_DIRECTOR);
		Element nameElement =  doc.createElement(NODE_NAME_NOMBRE);
		Element secondNameElement =  doc.createElement(NODE_NAME_APELLIDO);

		
		nameElement.appendChild(doc.createTextNode(d.getNombre()));
		secondNameElement.appendChild(doc.createTextNode(d.getApellidos()));

		directorElement.appendChild(doc.createTextNode("\n"));
		directorElement.appendChild(nameElement);
		directorElement.appendChild(doc.createTextNode("\n"));
		directorElement.appendChild(secondNameElement);
		directorElement.appendChild(doc.createTextNode("\n"));
		return directorElement;
		
	}
	
	//Ejercicio 9
	public void modifyDirector(String nameToModify, String secondNameToModify,String newName,String newSecondName,Document doc){
		Element director = getDirector(nameToModify, secondNameToModify,doc);
		for(int i = 0; i< director.getChildNodes().getLength();i++) {
			if(director.getChildNodes().item(i).getNodeName().equals(NODE_NAME_NOMBRE))
				director.getChildNodes().item(i).getFirstChild().setNodeValue(newName);
			if(director.getChildNodes().item(i).getNodeName().equals(NODE_NAME_APELLIDO))
				director.getChildNodes().item(i).getFirstChild().setNodeValue(newSecondName);
		}
	}
	

	public Element getDirector(String name, String secondName,Document doc) {
		NodeList nList = doc.getElementsByTagName(NODE_NAME_DIRECTOR);
		boolean flagName = false, flagSecondName = false;
		for(int i = 0; i< nList.getLength();i++) {
			NodeList directorChilds =nList.item(i).getChildNodes();

			for(int j = 0; j< directorChilds.getLength();j++) {
				if(directorChilds.item(j).getNodeName().equals(NODE_NAME_NOMBRE)){
					directorChilds.item(j).getFirstChild().getNodeValue().equals(name);
					flagName = true;
				}

				if(directorChilds.item(j).getNodeName().equals(NODE_NAME_APELLIDO)){
					directorChilds.item(j).getFirstChild().getNodeValue().equals(secondName);
					flagSecondName = true;
				}
			}
			
			if(flagName && flagSecondName) {
				return (Element) nList.item(i);
			}
		}


		return null;
	}

	//Ejercicio 10
	public void addDirector(String nameToAdd, String secondNameToAdd,String newName,String newSecondName){
		Element director = getDirector(nameToAdd, secondNameToAdd);
		Element pelicula = (Element)director.getParentNode();
		pelicula.appendChild(createDirectorElement(new Director("Alfredo","landa")));
	}
	
	
	//Ejercio 11
	public void deletePeliculaByTitulo(String title) {
		Node nodeTitle = getElementByValue(title,NODE_NAME_TITULO).getParentNode();
		doc.getFirstChild().removeChild(nodeTitle);
	} 
	
	
	//Ejercicio 12
	public Document createNewDocument(String pathNewDoc,Empleado ... employees) {
		Document document = makeDocument(pathNewDoc);
		addCompañia(document,employees);
		return document;
	}
	
	
	public void addCompañia(Document document,Empleado ... employees) {
		//document.appendChild(document.createTextNode("\n"));
		Node company = document.createElement("compañia");
		
		for(Empleado employe : employees) {
			addEmpleado(employe,company,document);
		}

		document.appendChild(company);
		int a = 1;
		//document.getFirstChild().appendChild(document.createTextNode("\n"));
	}
	
	public void addEmpleado(Empleado employee,Node parentElement,Document doc) {
		Element employeeElement = doc.createElement("empleado");
		employeeElement.appendChild(doc.createTextNode("\n"));
		employeeElement.setAttribute("id", employee.getId());
		Element nombre = doc.createElement("nombre");
		nombre.appendChild(doc.createTextNode(employee.getNombre()));
		Element apellidos = doc.createElement("apellidos");
		apellidos.appendChild(doc.createTextNode(employee.getApellidos()));
		Element apodo = doc.createElement("apodo");
		apodo.appendChild(doc.createTextNode(employee.getApodo()));
		Element salario = doc.createElement("salario");
		salario.appendChild(doc.createTextNode(employee.getSalario()));

		//addElementValue(employeeElement, (Element)(doc.createElement("nombre")).appendChild(doc.createTextNode(employee.getNombre())));
		addElementValue(employeeElement,nombre,doc);
		addElementValue(employeeElement,apellidos,doc);
		addElementValue(employeeElement,apodo,doc);
		addElementValue(employeeElement,salario,doc);
//		addElementValue(employeeElement, (Element)(doc.createElement("apellido")).appendChild(doc.createTextNode(employee.getApellidos())),doc);
//		addElementValue(employeeElement, (Element)(doc.createElement("apodo")).appendChild(doc.createTextNode(employee.getApodo())),doc);
//		addElementValue(employeeElement, (Element)(doc.createElement("salario")).appendChild(doc.createTextNode(employee.getSalario())), doc);
		parentElement.appendChild(employeeElement);
		parentElement.appendChild(doc.createTextNode("\n"));
	}
	
	public void addElementValue(Node parentElement,Node element,Document doc) {
		parentElement.appendChild(element);
		parentElement.appendChild(doc.createTextNode("\n"));
	}
	
	
	
	//Save the DOM changes on disk
	public void grabarDOM(Document document, String ficheroSalida)throws ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException
	{   																																														
		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();   
		DOMImplementationLS ls=(DOMImplementationLS)registry.getDOMImplementation("XML 3.0 LS 3.0");
		
		// Se crea un destino vacio   
		LSOutput output =ls.createLSOutput();
		output.setEncoding("UTF-8");
		
		// Se establece el flujo de salida
		output.setByteStream(new FileOutputStream(ficheroSalida));
		//output.setByteStream(System.out);
		
		// Permite escribir un documento DOMen XML   
		LSSerializer serializer =ls.createLSSerializer();
		
		// Se establecen las propiedades del serializador
		serializer.setNewLine("\r\n");
		serializer.getDomConfig().setParameter("format-pretty-print",true);
		
		// Se escribe el documento ya sea en un fichero o en una cadena de texto 
		serializer.write(document,output);
		 String xmlCad=serializer.writeToString(document);
	}

}
