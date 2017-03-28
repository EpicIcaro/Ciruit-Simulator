import javax.swing.ImageIcon;

public class AndGate extends Componente{	
	//int l(linha), int c(coluna), int ne(num. entradas), int ns(num. saidas), ImageIcon img(fota):
	
	/*@ requires l >=0 && c >=0;
    	@*/
	//aqui vai o same 
	public AndGate(int l,int c){
         super(l,c,2,1,new ImageIcon(System.getProperty("user.dir")+ "/AndGate.png"));
     }
	
	
	@Override
	 //aqui vai o same 
	public boolean exec(){
		if(entradas[0] == true && entradas[1] == true){
			return true;
		}
		return false;
	}
	//aqui vai o same /
	
	public void setE(int n,boolean val){
		entradas[n] = val;
    }
    //aqui vai o same /
    public void setS(int n,boolean val){
    	saidas[0] = val;
    }
} 