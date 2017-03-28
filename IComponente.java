import javax.swing.ImageIcon;

public interface IComponente{ 
				
	public /*@ pure @*/int getLin();        
        
    public /*@ pure @*/int getCol();    
    
    public /*@ pure @*/ImageIcon getImg();

    public /*@ pure @*/int getNroEntradas();

    public /*@ pure @*/int getNroSaidas();
    
    public /*@ pure @*/int getNroConexoes();
    
    /*@
    	requires n>=0 && n<getNroEntradas();
    @*/
    public /*@ pure @*/boolean getE(int n);

    /*@
        requires n>=0 && n<getNroSaidas();
    @*/
    public /*@ pure @*/boolean getS(int n);
    
    /*@
        requires n>=0 && n<getNroConexoes();
    @*/
    public /*@ pure @*/Conex getConexao(int n);
    
    // Conecta o componente corrente com o componente x na porta np
    public void conecta(int ns, Componente x,int np);    
        
    /*@
        requires n>=0 && n<getNroEntradas();
        ensures getE(n) == val;
    @*/
    public abstract void setE(int n,boolean val);
    
    /*@
        requires n>=0 && n<getNroSaidas();
        ensures getS(n) == val;
    @*/
    public abstract void setS(int n,boolean val);
    
    // Executa a operacao especifica do componente
    // Com base nos valores das entradas atualiza as
    // saidas correspondentes
    public abstract boolean exec();	
}