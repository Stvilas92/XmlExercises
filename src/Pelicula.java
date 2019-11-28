import java.util.ArrayList;

public class Pelicula {
	private String titulo,genero,idioma;
	private ArrayList<Director> director;
	private int a�o;
	public Pelicula(String titulo, String genero, String idioma,int a�o,Director ... director) {
		this.titulo = titulo;
		this.genero = genero;
		this.idioma = idioma;
		this.director = new ArrayList<Director>();
		this.a�o = a�o;
		
		for(Director d : director)
			this.director.add(d);
	}
	
	public Pelicula() {
		this.titulo = "";
		this.genero = "";
		this.idioma = "";
		this.director = new ArrayList<Director>();
		this.a�o = -1;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	public void addDirector(Director director) {
		this.director.add(director);
	}

	public ArrayList<Director> getDirector() {
		return director;
	}

	public void setDirector(ArrayList<Director> director) {
		this.director = director;
	}

	public int getA�o() {
		return a�o;
	}

	public void setA�o(int a�o) {
		this.a�o = a�o;
	}
	
	
}
