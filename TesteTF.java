import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class TesteTF extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L; //só pq o warning é MUITO chato	
	private JTextField cmdLine;
	private ComponenteMatriz editArea[][];
	private static final int DIM = 8; //não alterar dim;

	public TesteTF() {
		initUI();
		montaCircuitoExemplo();
	}


	// pesca comandos
	public void actionPerformed(ActionEvent e) {
		Comandos calculadora = new Comandos(cmdLine.getText());		
		calculadora.processa(editArea);				
	}

	private void montaCircuitoExemplo(){		
		//Posiciona Interruptores
		editArea[0][0].put(new Interruptor(0,0));
		editArea[2][0].put(new Interruptor(2,0));
		editArea[4][0].put(new Interruptor(4,0));
		editArea[6][0].put(new Interruptor(6,0));		

		//Posiciona portas logicas e saida
		editArea[1][2].put(new AndGate(1,2));
		editArea[5][2].put(new AndGate(5,2));
		editArea[3][4].put(new  OrGate(3,4));
		editArea[3][5].put(new NotGate(3,5));		
		editArea[3][6].put(new Lampada(3,6));
		editArea[0][4].put(new Lampada(0,4));	
		editArea[6][4].put(new Lampada(6,4));	

		//posiciona fios
		editArea[0][1].put(new Signal(0,1,"<v"));
		editArea[4][1].put(new Signal(4,1,"<v"));
		editArea[1][3].put(new Signal(1,3,"t"));
		editArea[1][3].put(new Signal(1,3,"t"));
		editArea[0][3].put(new Signal(0,3,"v>"));
		

		editArea[2][1].put(new Signal(2,1,"<^"));
		editArea[6][1].put(new Signal(6,1,"<^"));
		editArea[5][3].put(new Signal(5,3,"t"));
		editArea[6][3].put(new Signal(6,3,"^>"));

		editArea[2][3].put(new Signal(1,1,"vertical"));
		editArea[4][3].put(new Signal(5,1,"vertical"));

		editArea[1][1].put(new Signal(1,1,"="));
		editArea[5][1].put(new Signal(5,1,"="));
		editArea[3][3].put(new Signal(3,3,"="));
		
		
		//fazendo as ligações
		//primeira coluna
		editArea[0][0].getComponente().conecta(0, editArea[0][1].getComponente(), 0);
		editArea[2][0].getComponente().conecta(0, editArea[2][1].getComponente(), 0);
		editArea[4][0].getComponente().conecta(0, editArea[4][1].getComponente(), 0);
		editArea[6][0].getComponente().conecta(0, editArea[6][1].getComponente(), 0);

		//segunda coluna
		editArea[0][1].getComponente().conecta(0, editArea[1][1].getComponente(), 0);
		editArea[2][1].getComponente().conecta(0, editArea[1][1].getComponente(), 1);		
		editArea[4][1].getComponente().conecta(0, editArea[5][1].getComponente(), 0);
		editArea[6][1].getComponente().conecta(0, editArea[5][1].getComponente(), 1);

		editArea[1][1].getComponente().conecta(0, editArea[1][2].getComponente(), 0);
		editArea[1][1].getComponente().conecta(1, editArea[1][2].getComponente(), 1);		
		editArea[5][1].getComponente().conecta(0, editArea[5][2].getComponente(), 0);
		editArea[5][1].getComponente().conecta(1, editArea[5][2].getComponente(), 1);

		//terceira coluna
		editArea[1][2].getComponente().conecta(0, editArea[1][3].getComponente(), 0);
		editArea[5][2].getComponente().conecta(0, editArea[5][3].getComponente(), 0);

		//quarta coluna 
		editArea[0][3].getComponente().conecta(0, editArea[0][4].getComponente(), 0);
		editArea[1][3].getComponente().conecta(0, editArea[0][3].getComponente(), 0);
		editArea[1][3].getComponente().conecta(1, editArea[2][3].getComponente(), 0);
		editArea[2][3].getComponente().conecta(0, editArea[3][3].getComponente(), 0);
		editArea[3][3].getComponente().conecta(0, editArea[3][4].getComponente(), 0);
		editArea[3][3].getComponente().conecta(1, editArea[3][4].getComponente(), 1);
		editArea[4][3].getComponente().conecta(0, editArea[3][3].getComponente(), 1);
		editArea[5][3].getComponente().conecta(0, editArea[4][3].getComponente(), 0);
		editArea[5][3].getComponente().conecta(1, editArea[6][3].getComponente(), 0);
		editArea[6][3].getComponente().conecta(0, editArea[6][4].getComponente(), 0);
		
		//quinta coluna
		editArea[3][4].getComponente().conecta(0, editArea[3][5].getComponente(), 0);
		
		//sexta
		editArea[3][5].getComponente().conecta(0, editArea[3][6].getComponente(), 0);
		



	}

	private void initUI() {
		// Frame geral da Janela
		Container base = getContentPane();
		base.setLayout(new BorderLayout());
		// Monta area de edicao
		editArea = new ComponenteMatriz[DIM][DIM];
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(DIM,DIM));
		for(int i=0;i<DIM;i++){
			for(int j=0; j<DIM; j++){
				editArea[i][j] = new ComponenteMatriz();
				pane.add(editArea[i][j]);
			}
		}

		// Monta área de entrada de dados
		JPanel cmdArea = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		cmdArea.add(new JLabel("Comando:"));
		cmdLine = new JTextField(20);
		cmdArea.add(cmdLine);
		JButton butDo = new JButton("DO");
		butDo.addActionListener(this);
		cmdArea.add(butDo);		

		// Monta janela da aplicação
		base.add(pane,BorderLayout.CENTER);
		base.add(cmdArea,BorderLayout.SOUTH);

		pack();

		setTitle("Editor de Circuitos Combinacionais");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				TesteTF ex = new TesteTF();
				ex.setVisible(true);
			}
		});
	}
}