public class Conex{
    public Componente componente;
    public int entrada;
    
    
    /*@ requires c != null && e >=0;
	ensures getComponente() == componente;
    @*/
     
    public Conex(Componente c,int e){
        componente = c;
        entrada = e;
    }

    public /*@ pure @*/Componente getComponente(){
    	return componente;
    }
    
    public /*@ pure @*/ int getEntrada(){
    	return entrada;
    }
}
