import javax.swing.ImageIcon;

public class Interruptor extends Componente{

	//aqui vai o same 
	public Interruptor(int l,int c){
		 super(l,c,1,1,new ImageIcon(System.getProperty("user.dir")+ "/OFFSwitch.png"));

		//TODO: perguntar ao prof pq  tem que iniciar aqui dentro se já está iniciando dentro do super.
		//entradas = new boolean[0];
		//saidas = new boolean[1];
	}

	/*@
	requires n >= 0;	  	
	@*/
	//tem de verificar se foi setado mas nao tem nada no metodo
	public void setE(int n,boolean val){
		//n tem entrada
		//vazio aqui
	}

	
	/*@ 
	    requires n >= 0;
	@*/
	public void setS(int n,boolean val){

		if(val == true){
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/ONSwitch.png"));
			saidas[0] = true ;
		}else{
			setImagem(new ImageIcon(System.getProperty("user.dir")+ "/OFFSwitch.png"));
			saidas[0] = false;
		}
	}


	@Override
	/*@ pure @*/
	public boolean exec(){
		if(saidas[0] == true){
			return true;
		}
		return false;
	}
}
