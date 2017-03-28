import javax.swing.ImageIcon;

public class NotGate extends Componente{
	//int l(linha), int c(coluna), int ne(num. entradas), int ns(num. saidas), ImageIcon img(fota):
	
	/*@ requires l >=0 && c>= 0;
	@*/
	//aqui vai o same 
	public NotGate(int l,int c){		
		 super(l,c,1,1,new ImageIcon(System.getProperty("user.dir")+ "/NotGate.png"));
	}	
	
	
	@Override
	//aqui vai o same 
	public boolean exec(){
		if(entradas[0] == true){
			return false;
		}
		return true;
	}
	
	 //aqui vai o same 
	public void setE(int n,boolean val){
		entradas[n] = val;
    }

     //aqui vai o same 
    public void setS(int n,boolean val){
    	saidas[n] = val;
    }
	
}