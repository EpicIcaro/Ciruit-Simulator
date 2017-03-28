

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.Line;

public class Comandos{
	private String[] cmdList;
	private String[] args;
	public static boolean simulando;
	String nomeArq;

	/*@
		requires entrada != null;
	 @*/	
	public Comandos(String entrada){
		//separa comando em duas partes: comando e argumentos.
		cmdList = new String[2];
		cmdList = entrada.split(" ");		

		//separa argumentos em partes.		
		if(cmdList.length > 1){
			args    = cmdList[1].split(",");
		};
	}

	public void processa(ComponenteMatriz[][] matriz){
		int linO, colO, nrSaida, linD, colD, nrEntrada, lin,col;	

		if(cmdList.length >1){ 
			switch (cmdList[0].toLowerCase()){
			case "put":
				if (simulando == false) {
					lin = Integer.parseInt(args[0]);
					col = Integer.parseInt(args[1]);
					//switch para adicionar X peça na posição determinada
					switch (args[2]) {
					case ("andgate"):
						matriz[lin][col].put(new AndGate(lin, col));
					break;
					case ("orgate"):
						matriz[lin][col].put(new OrGate(lin, col));
					break;
					case ("notgate"):
						matriz[lin][col].put(new NotGate(lin, col));
					break;
					case ("signal"):
						matriz[lin][col].put(new Signal(lin, col, args[3]));
					break;
					case ("interruptor"):
						matriz[lin][col].put(new Interruptor(lin, col));
					break;
					case ("lampada"):
						matriz[lin][col].put(new Lampada(lin, col));
					break;
					default:
						System.out.println("peça inexistente");
						break;
					}
				}else{
					System.out.println("você deve parar de simular para alterar o circuito");
				}
				break;

			case "rmv":
				if (simulando == false) {
					lin = Integer.parseInt(args[0]);
					col = Integer.parseInt(args[1]);
					//checaremos se existe um objeto para remover
					if (matriz[lin][col].getComponente() != null) {
						//antes de remover o componente, temos que dizer que a entrada do proximo componente é false

						if (matriz[lin][col].getComponente().getConexao(0) != null) {
							for (int i = 0; i < matriz[lin][col].getComponente().getNroSaidas(); i++) {
								matriz[lin][col].getComponente().getConexao(0).getComponente().setE(i, false);
							}

						}
						matriz[lin][col].remove();
					} 
				}else{
					System.out.println("você deve parar de simular para alterar o circuito");
				}

				break;

			case "con":
				if (simulando == false) {
					// Conectar a conexão de saída do elemento localizado na posição 1,1 com a
					// 	conexão de entrada 1 do elemento localizado na posição 1,2.
					linO = Integer.parseInt(args[0]);
					colO = Integer.parseInt(args[1]);
					nrSaida = Integer.parseInt(args[2]);
					linD = Integer.parseInt(args[3]);
					colD = Integer.parseInt(args[4]);
					nrEntrada = Integer.parseInt(args[5]);
					Componente orig = matriz[linO][colO].getComponente();
					Componente dest = matriz[linD][colD].getComponente();
					if (orig != null && dest != null) {
						if (orig instanceof Lampada) {
							System.out.println("você não pode conectar algo na saida de uma lâmpada");
						} else {
							matriz[linO][colO].getComponente().conecta(nrSaida, dest, nrEntrada);
						}
					} else {
						System.out.println("origem ou Destino nulos!");
					} 
				}else{
					System.out.println("você deve parar de simular para alterar o circuito");
				}
				break;

			case "switch":
				lin = Integer.parseInt(args[0]);
				col = Integer.parseInt(args[1]);
				if (matriz[lin][col].getComponente() instanceof Interruptor) {
					switch (args[2].toLowerCase()) {
					case "on":
						matriz[lin][col].getComponente().setS(0, true);
						matriz[lin][col].repintar();
						break;
					case "off":
						matriz[lin][col].getComponente().setS(0, false);
						matriz[lin][col].repintar();
						break;
					default:
						break;
					}
				} else {
					System.out.println("Você só pode ligar/desligar um interruptor!");
				} 
				simulate(matriz);
				break;	

			case "save":
				nomeArq = args[0]; 
				save(matriz, nomeArq);			
			break;

		case "load":
			nomeArq = args[0]; 
			load(matriz, nomeArq);
			loadConex(matriz, nomeArq);
			
			break;

		case "!getconex":

			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);
			int ter		= Integer.parseInt(args[2]);
			int opt 	= Integer.parseInt(args[3]);

			if(opt == 0){
				System.out.println(matriz[linO][colO].getComponente().getConexao(ter)+" na porta "+matriz[linO][colO].getComponente().getConexao(ter).getEntrada());
			}else{
				System.out.println(matriz[linO][colO].getComponente().getConexao(ter).getComponente()+" na porta "+matriz[linO][colO].getComponente().getConexao(ter).getEntrada());
			}
			break;

		case "!getexec":
			//comando de depuração para printar o resultado de exec.
			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);		  		

			System.out.println(matriz[linO][colO].getComponente().exec());
			break;

		case "!getcomp":
			//comando de depuração para pintar o componente
			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);		  		

			System.out.println(matriz[linO][colO].getComponente());				  		
			break;

		case "!gets":
			//comando de depuração para retornar a saida #ter
			if(args[2] == null){
				System.out.println("falta ou sobra argumento");
			}else{
				linO 		= Integer.parseInt(args[0]);
				colO 		= Integer.parseInt(args[1]);				
				ter			= Integer.parseInt(args[2])
						;

				System.out.println(matriz[linO][colO].getComponente().getS(ter));}
			break;

		case "!getns":
			//comando de depuração para retornar a saida #ter
			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);

			System.out.println(matriz[linO][colO].getComponente().getNroSaidas());
			break;

		case "!getne":
			//comando de depuração para retornar a saida #ter
			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);

			System.out.println(matriz[linO][colO].getComponente().getNroEntradas());
			break;

		case "!gete":
			//comando de depuração para retornar a entrada #ter
			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);
			ter			= Integer.parseInt(args[2]);

			System.out.println(matriz[linO][colO].getComponente().getE(ter));
			break;	


		case "!getimg":
			//comando de depuração para retornar a imagem
			linO 		= Integer.parseInt(args[0]);
			colO 		= Integer.parseInt(args[1]);			  		

			System.out.println(matriz[linO][colO].getComponente().imagem.toString());
			break;

		case "simulate":
			if(args[0].equalsIgnoreCase("on")){
				simulando = true;
				simulate(matriz);
			}else{
				simulando=false;
			}	
			break;
		}



	}else{
		switch (cmdList[0].toLowerCase()){	 
		case "clear":
			//comando que limpa a tela;
			if (simulando == false) {
				for (int j = 0; j < 8; j++) {
					for (int i = 0; i < 8; i++) {
						if (matriz[i][j]
								.getComponente() != null) {
							matriz[i][j].remove();
						}
					}
				}
				for (int j = 0; j < 8; j++) {
					for (int i = 0; i < 8; i++) {
						if (matriz[i][j]
								.getComponente() != null) {
							matriz[i][j].repintar();
						}
					}
				} 
			}else{
				System.out.println("você deve parar de simular para alterar o circuito");
			}
			break;		

		case "off":
			//off();
			//setDefaultCloseOperation(EXIT_ON_CLOSE);
			break;	
		default:
			System.out.println("insira um comando válido");
			break;
		}

	}

}
	
private void load(ComponenteMatriz[][] matriz, String nomeArq) {
		Path path = Paths.get(nomeArq+".txt");
		String[] data;
		int lin,col,ne,ns;
		int saida1 = 0,linDest1 = 0,colDest1 = 0,entDest1 = 0;
		int saida2 = 0,linDest2 = 0,colDest2 = 0,entDest2 = 0;
		String comp, tipo;
		Componente alvo1 = null ,alvo2 = null;

		String texto = null;
		int l = 0;	

		//primeiro reader somente adiciona as peças. o segundo é o responsável por adicionar as conexoes
		try (BufferedReader rd = Files.newBufferedReader(path,Charset.defaultCharset())){
			String line = " ";
			while ((line = rd.readLine()) != null) {			
				data = line.split(",");
				lin  = Integer.parseInt(data[0]);			
				col  = Integer.parseInt(data[1]);
				comp = data[2];
				tipo = data[3];
				ne 	 = Integer.parseInt(data[4]);
				ns 	 = Integer.parseInt(data[5]);				
				if(ns>=1){
					saida1 = Integer.parseInt(data[6]);
					linDest1 = Integer.parseInt(data[7]);
					colDest1 = Integer.parseInt(data[8]);
					entDest1 = Integer.parseInt(data[9]);
					if(ns>=2){
						saida2 = Integer.parseInt(data[10]);
						linDest2 = Integer.parseInt(data[11]);
						colDest2 = Integer.parseInt(data[12]);
						entDest2 = Integer.parseInt(data[13]);
					}
				}
				switch (comp.toLowerCase()) {
				case ("andgate"):
					matriz[lin][col].put(new AndGate(lin, col));
				break;
				case ("orgate"):
					matriz[lin][col].put(new OrGate(lin, col));
				break;
				case ("notgate"):
					matriz[lin][col].put(new NotGate(lin, col));
				break;
				case ("signal"):
					matriz[lin][col].put(new Signal(lin, col, data[3]));
				break;
				case ("interruptor"):
					matriz[lin][col].put(new Interruptor(lin, col));
				break;
				case ("lampada"):
					matriz[lin][col].put(new Lampada(lin, col));
				break;
				default:
					System.out.println("peça inexistente");
					break;
				}					
				texto = (line+"\n");
				System.out.println("linha "+l+":\n"+texto);
				l++;
				texto="";
			}			

	}catch(IOException y){
		System.err.format("Erro de E/S: %s%n", y);
	}
}

private void loadConex(ComponenteMatriz[][] matriz, String nomeArq) {
	Path path = Paths.get(nomeArq+".txt");
	String[] data;
	int lin,col,ne,ns;
	int saida1 = 0,linDest1 = 0,colDest1 = 0,entDest1 = 0;
	int saida2 = 0,linDest2 = 0,colDest2 = 0,entDest2 = 0;
	String comp, tipo;
	Componente alvo1 = null ,alvo2 = null;

	String texto = null;
	int l = 0;	

	//primeiro reader somente adiciona as peças. o segundo é o responsável por adicionar as conexoes
	try (BufferedReader rd = Files.newBufferedReader(path,Charset.defaultCharset())){
		String line = " ";
		while ((line = rd.readLine()) != null) {			
			data = line.split(",");
			lin  = Integer.parseInt(data[0]);			
			col  = Integer.parseInt(data[1]);
			comp = data[2];
			tipo = data[3];
			ne 	 = Integer.parseInt(data[4]);
			ns 	 = Integer.parseInt(data[5]);				
			if(ns>=1){
				saida1 = Integer.parseInt(data[6]);
				linDest1 = Integer.parseInt(data[7]);
				colDest1 = Integer.parseInt(data[8]);
				entDest1 = Integer.parseInt(data[9]);
				if(ns>=2){
					saida2 = Integer.parseInt(data[10]);
					linDest2 = Integer.parseInt(data[11]);
					colDest2 = Integer.parseInt(data[12]);
					entDest2 = Integer.parseInt(data[13]);
				}
			}
			if(ns==1){
				alvo1 = matriz[linDest1][colDest1].getComponente();
				matriz[lin][col].getComponente().conecta(saida1, alvo1, entDest1);
				if(ns==2){
					alvo1 = matriz[linDest2][colDest2].getComponente();
					matriz[lin][col].getComponente().conecta(saida2, alvo2, entDest2);

				}
			}


			texto = (line+"\n");
			System.out.println("linha "+l+":\n"+texto);
			l++;
			texto="";
		}			

	}catch(IOException y){
		System.err.format("Erro de E/S: %s%n", y);
	}
}
	

	


private void save(ComponenteMatriz[][] matriz, String nomeArq) {
	StringBuilder linha = new StringBuilder();
	try(PrintStream output = new PrintStream(new File(nomeArq+".txt"))){
        for(int j=7;j>=0;j=j-1){		                
            for (int i=7;i>=0;i=i-1){               	
            	if (matriz[i][j].getComponente() != null) {
            		//posição
					linha.append(i + ",");
					linha.append(j + ",");
					//nome do componente + 
					linha.append(matriz[i][j].getComponente().toSave() + ",");
					linha.append(matriz[i][j].getComponente().getNroEntradas() + ",");
					linha.append(matriz[i][j].getComponente().getNroSaidas());
					
					for (int k = 0; k < matriz[i][j].getComponente().getNroConexoes(); k++) {
						linha.append(",");
						linha.append(k+ ",");
						linha.append(matriz[i][j].getComponente().getConexao(k).getComponente().getLin() + ",");
						linha.append(matriz[i][j].getComponente().getConexao(k).getComponente().getCol() + ",");
						linha.append(matriz[i][j].getComponente().getConexao(k).getEntrada());
					}
					output.println(linha.toString());
					linha.setLength(0);
				}
            }		                
        }
        output.close();

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
		
}

public void simulate(ComponenteMatriz[][] matriz){
	for(int k= 0; k<8; k++){
		for(int j = 0; j < 8; j++){
			for(int i = 0; i < 8; i++){
				if(	matriz[i][j].getComponente() != null){		
					//excluimos a classe lampada pois não é necessário dar execute nela			  						
					if((matriz[i][j].getComponente() instanceof Lampada) == false){	
						int portaAlvo1,portaAlvo2;
						boolean valor1,valor2;		  								
						Componente componenteCurr, componenteAlvo1,componenteAlvo2;
						componenteCurr = matriz[i][j].getComponente();

						if(componenteCurr.getNroSaidas() == 1){ 										
							componenteCurr.setS(0, componenteCurr.exec());
							valor1			 = componenteCurr.getS(0);
							componenteAlvo1 = componenteCurr.getConexao(0).getComponente();
							portaAlvo1		 = componenteCurr.getConexao(0).getEntrada();
							componenteAlvo1.setE(portaAlvo1, valor1);
						}
						if(componenteCurr instanceof Signal && componenteCurr.getNroSaidas() >1){										
							boolean[] saidas;
							saidas = ((Signal) componenteCurr).execmult();										
							componenteCurr.setS(0,saidas[0]);
							valor1			= componenteCurr.getS(0);
							componenteAlvo1 = componenteCurr.getConexao(0).getComponente();
							componenteCurr.setS(1,saidas[1]);
							valor2			= componenteCurr.getS(1);
							componenteAlvo2 = componenteCurr.getConexao(1).getComponente();
							portaAlvo1 		= componenteCurr.getConexao(0).getEntrada();
							portaAlvo2		= componenteCurr.getConexao(1).getEntrada();
							componenteAlvo1.setE(portaAlvo1, valor1);		  								
							componenteAlvo2.setE(portaAlvo2, valor2);
						}

					}
				}

			}
		}		  					
	}
	//repintar tudo, novamente
	for(int j = 0; j < 8; j++){
		for(int i = 0; i < 8; i++){
			if(	matriz[i][j].getComponente() != null){	
				matriz[i][j].getComponente().exec();
				matriz[i][j].repintar();
			}		  					
		}
	}
}
}

