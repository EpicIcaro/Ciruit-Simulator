import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ComponenteMatriz extends JPanel{
	private ImageIcon None;
	private Componente componente;

	/*@ 
	ensures getComponente() == null;
	@*/
	public ComponenteMatriz(){
		super(new BorderLayout());

		None  = new ImageIcon(System.getProperty("user.dir")+ "/None.png");
		componente = null;

		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(new JLabel(None),BorderLayout.CENTER);
	}

	public Componente getComponente(){
		return(componente);
	}
	
	/*@ 
	requires c != null;
	ensures getComponente() == c;
	@*/
	public void put(Componente c){
		componente = c;

		// Apaga imagem antiga
		for (int z = 0; z <this.getComponentCount(); z++){
			if (this.getComponent(z).getClass().toString().indexOf("JLabel") > -1){
				 this.remove(z);
				 this.repaint();
			}
		}

		// Insere imagem nova
		this.add(new JLabel(componente.getImg()),BorderLayout.CENTER);
		this.validate();
	}

	/*@ 
	ensures getComponente() == null;
	@*/	
	//em processo padrao ta ok, nescessita um also/tratamento de excessao ?
	public void remove(){
		if(componente == null){
			System.out.println("ja está vazio ");
		}else{

			componente=null;
			System.out.println("Cheguei até aqui e apaguei o componente.");

			// Apaga imagem antiga
			for (int z = 0; z <this.getComponentCount(); z++){
				if (this.getComponent(z).getClass().toString().indexOf("JLabel") > -1){
					 this.remove(z);
					 this.repaint();
				}
			}
			//insere uma imagem em branco
			this.add(new JLabel(None,0));
			this.validate();
		}
	}

	
	public /*@ pure @*/ void repintar(){
		for (int z = 0; z <this.getComponentCount(); z++){
			if (this.getComponent(z).getClass().toString().indexOf("JLabel") > -1){
				 this.remove(z);
				 this.repaint();
			}
		}
		this.add(new JLabel(componente.getImg()),BorderLayout.CENTER);
		this.validate();
	}
	
	/*@ 
	requires nrSaida >= 0 && nrEntrada >=0 && dest != null;	
	@*/
	// botar ensures para verificar se foi conectado (nao sei verificar)

	public void conect(int nrSaida, Componente dest, int nrEntrada){
		getComponente().conecta(nrSaida, dest,nrEntrada);
	}
}