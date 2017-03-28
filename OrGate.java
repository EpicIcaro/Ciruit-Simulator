
import javax.swing.ImageIcon;

public class OrGate extends Componente{    

	//aqui vai o same 
	public OrGate(int l,int c){
         super(l,c,2,1,new ImageIcon(System.getProperty("user.dir")+ "/OrGate.png"));
     }
	
	@Override
	 //aqui vai o same 
	public boolean exec(){
		if(entradas[0] == true || entradas[1] == true){
			return true;
		}
		return false;
	}
	
	 //aqui vai o same /
	public void setE(int n,boolean val){
		entradas[n] = val;
    }

     //aqui vai o same 
    public void setS(int n,boolean val){
    	saidas[n] = val;
    }
} 