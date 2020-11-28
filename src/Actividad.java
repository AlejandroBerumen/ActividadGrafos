import java.util.Scanner;

class Vertice {
	private String nombre;
    private int numVertice;
    
    public Vertice() {}
    public Vertice(String x){
    	nombre = x;
	    numVertice = -1;
	}
    public String getNombre() {
		return nombre;
    }
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getNumVertice() {
		return numVertice;
	}
	public void setNumVertice(int numVertice) {
		this.numVertice = numVertice;
	}
    public String nomVertice() {
    	return nombre;
    }
    public boolean equals(Vertice n) {
    	return nombre.equals(n.nombre);
    }
    public void asigVert(int n){
        numVertice = n;
    }
    public String toString(){
    	return nombre + " (" + numVertice + ")";
    }
      
}

class GrafoMatriz{
    int numVerts;
    static int maxVerts;
    Vertice [] verts;
    int [][] matAd;
    
    public GrafoMatriz(){
    	this(maxVerts);
    }
    public GrafoMatriz(int mx){
    	maxVerts=mx;
        matAd = new int [mx][mx];
        verts = new Vertice[mx];
        for (int i = 0; i < mx; i++)
        	for (int j = 0; i < mx; i++)
        		matAd[i][j] = 0;
        numVerts = 0;
    }
    public int numVertice(String vs) {
         Vertice v = new Vertice(vs);
         boolean encontrado = false;
         int i = 0;
         for (; (i < numVerts) && !encontrado;){
        	 encontrado = verts[i].equals(v);
        	 if (!encontrado) 
        		 i++ ;
        	 }
         return (i < numVerts) ? i : -1 ;
    }
    public void nuevoVertice (String nom){
    	boolean esta = numVertice(nom) >= 0;
    	if (!esta){
    		Vertice v = new Vertice(nom);
    		v.asigVert(numVerts);
    		verts[numVerts++] = v;
    	}
    }
    public void nuevoArco(String a, String b)throws Exception{
    	int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        if (va < 0 || vb < 0) throw new Exception ("V?tice no existe");
        matAd[va][vb] = 1;
    }
    public boolean adyacente(String a, String b)throws Exception{
    	int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        if (va < 0 || vb < 0) throw new Exception ("V?tice no existe");
        return matAd[va][vb] == 1;
    }
    public static int[]recorrerAnchura(GrafoMatriz g, String org) throws Exception{
    	int w, v;
        int [] m;
        v = g.numVertice(org);
        int CLAVE =-1;
        if (v < 0) throw new Exception("V?tice origen no existe");
        ColaLista cola = new ColaLista();
        m = new int[g.numVerts];
        for (int i = 0; i < g.numVerts; i++)
        m[i] = CLAVE;
        m[v] = 0;
        cola.insertar(new Integer(v));
        while (! cola.colaVacia()){
        	Integer cw;
        	cw = (Integer) cola.quitar();
        	w = cw.intValue();
        	System.out.println("Vértice " + g.verts[w] + "visitado");
        	for (int u = 0; u < g.numVerts; u++)
        	if ((g.matAd[w][u] == 1) && (m[u] == CLAVE))
        	{
        	m[u] = m[w] + 1;
        	cola.insertar(new Integer(u));
        	}
        }
        return m;
        }
}

class Nodo {
	Object elemento;
	Nodo siguiente;
	int dato;
	
	public Nodo(Object x){
		elemento = x;
		siguiente = null;
		}
	public Nodo(int x){
		dato = x;
	    siguiente = null;
	}
	public Nodo(int x, Nodo n){
	    dato = x;
	    siguiente = n;
	}
	
	public int getDato(){
	    return dato;
	}
	public Nodo getEnlace(){
	    return siguiente;
	}
	public void setEnlace(Nodo enlace){
	    this.siguiente = enlace;
	}
}

class ColaLista { 
	protected Nodo frente;
	protected Nodo fin;
	
	public ColaLista(){
		frente = fin = null;
	}
    public void insertar(Object elemento){
    	Nodo a;
        a = new Nodo(elemento);
        if (colaVacia()){
        	frente = a;
        	}else{
        		fin.siguiente = a;
        	}
        fin = a;
        }
    public Object quitar() throws Exception{
    	Object aux;
    	if (!colaVacia()){
    		aux = frente.elemento;
    		frente = frente.siguiente;
    	}else
    		throw new Exception("Eliminar de una cola vacía");
    	return aux;
    }
    public void borrarCola(){
    	for (; frente != null;){
    		frente = frente.siguiente;
        }
    	System.gc();
    }
    public Object frenteCola() throws Exception{
    	if (colaVacia()){
    		throw new Exception("Error: cola vacía");
        }
    	return (frente.elemento);
    }
    public boolean colaVacia(){
    	return (frente == null);
    	}
}

class Arco{
	int destino;
    double peso;
    
    public Arco(int d){
    	destino = d;
    }
    public Arco(int d, double p){
    	this(d);
    	peso = p;
    	} 
    public int getDestino(){
        return destino;
    }
    public boolean equals(Object n){
    	Arco a = (Arco)n;
    	return destino == a.destino;
    }
}

public class Actividad {

	public static void main(String[] args) {
		Scanner x = new Scanner(System.in);
		System.out.println("\nIngrese un máximo de vertices");
		int max = x.nextInt();
		GrafoMatriz grafo = new GrafoMatriz(max);
	    int elec = 0;
	    while(elec!=5) {
		System.out.println("\nElige una opción:\n");
		System.out.println("1.- Agregar un vértice");
		System.out.println("2.- Agregar un arco");
		System.out.println("3.- Saber si son adyacentes");
		System.out.println("4.- Recorrer en anchura");
		System.out.println("5.- Salir");
	    elec = x.nextByte();
	    x.nextLine();
	    switch(elec) {
	    case 1: 
	    	System.out.println("\nIngrese el nombre del vértice");
			String nom = x.nextLine();
			grafo.nuevoVertice(nom);
			System.out.println("\nSe agregó un vertice nuevo");
			break;
	    case 2:
	    	System.out.println("\nIngrese el nombre del vértice de origen");
			String a = x.nextLine();
			System.out.println("\nIngrese el nombre del vértice de destino");
			String b = x.nextLine();
			try {
				grafo.nuevoArco(a, b);
				System.out.println("\nSe agregó el nuevo arco con éxito");
			} catch (Exception e) {
				System.out.println("Error al agregar el arco, por favor, ingrese vértices válidos");
			}
			break;
	    case 3: 
	    	System.out.println("\nIngrese el nombre del primer vértice:");
			a = x.nextLine();
			System.out.println("\nIngrese el nombre del segundo vértice");
			b = x.nextLine();
			try {
				if(grafo.adyacente(a, b))
					System.out.println("\nSon adyacentes");
				else System.out.println("\nNo son adyacentes");
			} catch (Exception e) {
				System.out.println("Error al comprobar adyacencia, por favor, ingrese vértices válidos");
			}
			break;
	    case 4: 
	    	System.out.println("\nIngrese el nombre del vértice origen:");
			String origen = x.nextLine();
			try {
				grafo.recorrerAnchura(grafo, origen);
				System.out.println();
			} catch (Exception e) {
				System.out.println("\nError al recorrer, por favor, ingrese un vértice válido");
			}
			break;
	    default:
	    	System.out.println("\nIngrese una opción válida");
	    }
	    }

	}

}
