import javax.swing.ImageIcon;

public class Lampada extends Componente{
	
	//aqui vai o same 
	public Lampada(int l,int c){
         super(l,c,1,0,new ImageIcon(System.getProperty("user.dir")+ "/LightOff.png"));

         //TODO: perguntar ao prof pq  tem que iniciar aqui dentro se já está iniciando dentro do super.
         //entradas = new boolean[0];
         //saidas = new boolean[1];


    }
	/*@ 
            requires n >=0;		
	 @*/
	public void setE(int n,boolean val){
		entradas[0] = val;
    }

	/*@ 
	    requires n >= 0;
	@*/
	//tem de verificar se foi setado mas nao tem nada no metodo
    public void setS(int n,boolean val){
    	//noval
    }
	/*@ 
	requires n >=0;	
	 @*/
	@Override
	public boolean getS(int n){
		return(entradas[n]);
	}

	

	@Override
	public/*@ pure @*/ boolean exec(){
		if(entradas[0] == true){
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/LightOn.png"));
			return true;
		}
		setImagem(new ImageIcon(System.getProperty("user.dir")+ "/LightOff.png"));
		return false;
	}
} 
