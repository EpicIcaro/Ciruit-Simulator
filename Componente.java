import javax.swing.ImageIcon;

public abstract class Componente implements IComponente{
	protected int lin,col;
	public boolean entradas[];
	public boolean saidas[];
	protected Conex conexoes[];
	protected Conex conectados[];
	protected ImageIcon imagem;

	/*@
		requires l>=0 && c>=0 && ne>=0 && ns>=0;
		ensures getLin() == l;
		ensures getCol() == c;
		ensures getNroEntradas() == ne;
		ensures getNroSaidas() == ns;
		ensures getNroConexoes() == ns;
	 @*/
	
	//componente com imagem vazia para criar signal indeterminado.
	public Componente(int l,int c){
		lin = l;
		col = c;
	}

	/*@
		requires l>=0 && c>=0 && ne>=0 && ns>=0;
		requires img != null;
		ensures getLin() == l;
		ensures getCol() == c;
		ensures getNroEntradas() == ne;
		ensures getNroSaidas() == ns;
		ensures getNroConexoes() == ns;
		ensures getImg() == img;
	 @*/
	public Componente(int l,int c,int ne,int ns,ImageIcon img){
		lin = l;
		col = c;
		entradas = new boolean[ne];
		saidas = new boolean[ns];
		conexoes = new Conex[ns];
		imagem = img;
	}
	/*@
	requires i != null;
	ensures getImg() == i;
	 @*/
	public void setImagem(ImageIcon i){
		imagem = i;
	}

	public /*@ pure @*/int getLin(){
		return(lin);
	}


	public /*@ pure @*/int getCol(){
		return(col);
	}


	public /*@ pure @*/ImageIcon getImg(){
		return(imagem);
	}


	public /*@ pure @*/int getNroEntradas(){
		return(entradas.length);
	}


	public /*@ pure @*/int getNroSaidas(){
		return(saidas.length);
	}


	public /*@ pure @*/int getNroConexoes(){
		return(conexoes.length);
	}


	/*@
		requires n>=0 && n<getNroEntradas();
	@*/
	public /*@ pure @*/boolean getE(int n){
		return(entradas[n]);
	}


	/*@
		requires n>=0 && n<getNroSaidas();
	@*/
	public /*@ pure @*/boolean getS(int n){
		return(saidas[n]);
	}


	/*@
		requires n>=0 && n<getNroConexoes();
	@*/
	public /*@ pure @*/Conex getConexao(int n){
		return(conexoes[n]);
	}

	/*@
	requires x != null && np >= 0;
	ensures getConexao(0) != null;		
	@*/ 
	// Conecta a saida ns do componente corrente na entrada np do componente x	
	public void conecta(int ns, Componente x,int np){
		if(x == null){
			System.out.println("ta tentando conectar a um componente que nao existe");
		}  
		if(x.getNroEntradas() >= np){			
			conexoes[ns] = new Conex(x,np);			
		}else{
			System.out.println("ta tentando conectar uma saida que nao existe");
		}
			
	}
	
	//conecta a entrada i com a saida s do componente com
	
	


	/*@
		requires n>=0 && n<getNroEntradas();
		ensures getE(n) == val;
	@*/
	public abstract void setE(int n,boolean val);

	@Override
	public String toString(){
		return (this.getClass()+" "+lin+","+col);
	}
	
	public String toSave(){
		return getClass().getName()+",0";
	}
	
	/*@
		requires n>=0 && n<getNroSaidas();
		ensures getS(n) == val;
	@*/
	public abstract void setS(int n,boolean val);


	// Executa a operacao especifica do componente
	// Com base nos valores das entradas atualiza as
	// saidas correspondentes
	
	//a fazer...
	public abstract boolean exec();

}