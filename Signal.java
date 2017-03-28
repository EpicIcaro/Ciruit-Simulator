import javax.swing.ImageIcon;

public class Signal extends Componente{			
	private String type;

	//aqui vai o same 
	public Signal(int l,int c, String type){
		super(l,c);	
		this.type = type;
		switch(type){
		case "horizontal":				
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnMM.png"));	
			setNE(1);
			setNS(1);

			break;
		case "vertical":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnSD.png"));
			setNE(1);
			setNS(1);
			;
			break;
		case "t":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/Cn12.png"));
			conexoes = new Conex[2];
			saidas = new boolean[2];						
			setNE(1); //seta numero de entradas

			break;
		case "=":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/Cn21.png"));	
			conexoes = new Conex[2];
			saidas = new boolean[2];						
			setNE(2); //seta numero de entradas

			break;
		case "v>":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnDM.png"));
			setNE(1);
			setNS(1);

			break;
		case "^>":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnSM.png"));
			setNE(1);
			setNS(1);

			break;
		case "<^":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnMS.png"));
			setNE(1);
			setNS(1);

			break;
		case "<v":
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnMD.png"));
			setNE(1);
			setNS(1);

			break;
		default:
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/CnXX.png"));
			System.out.println("Peça inexistente, revisar nome da peça");
			setNE(1);
			setNS(1);
			break;
		}
	}
	//a fazer
	@Override
	public boolean exec(){
		return entradas[0];
	}

	public boolean[] execmult(){
		switch(type){
		case"=":
			setS(0, getE(0));
			setS(1, getE(1));
			System.out.println("lincol"+lin+col+"= com sucesso");
			break;
		case "t":
			setS(0, getE(0));
			setS(1, getE(0));
			System.out.println("lincol"+lin+col+"= com sucesso");
			break;
		default:
			break;
		}


		return saidas;
	}

	public void setNS(int n){
		conexoes = new Conex[n];
		saidas = new boolean[n];
		for(int i = 0; i<saidas.length; i++){
			saidas[i] = false;
		}
	}

	public void setNE(int n){
		entradas = new boolean[n];
		for(int i = 0; i<entradas.length; i++){
			entradas[i] = false;
		}
	}

	public void setE(int n,boolean val){
		entradas[n] = val;
	}

	public void setS(int n,boolean val){
		saidas[n] = val;
	}

	public boolean getS(int n){
		if(n < saidas.length){
			return(saidas[n]);
		}else{
			System.out.println("ta pedindo onde nao alcança");
			return false;
		}
	}
	
	@Override
	public String toSave(){
		return getClass().getName()+","+type;
	}

	@Override
	public String toString(){
		return("signal, L"+lin+" C"+col+" tipo:"+type);
	}
}
