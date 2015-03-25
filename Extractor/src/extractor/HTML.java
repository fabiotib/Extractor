package extractor;

import entity.Steps;


/*
 * Premissa: A busca parte de um conjunto de tags até encontrar um texto expecifico.
 *           Toda saida de um comando é a entrada do próximo, exceto, nos casos de condições 
 * Possibilidades existentes:
 * 1- Recuperar uma tag por id
 * 2- Recuperar uma tag por class
 * 3- Recuperar um conjunto de um mesmo tipo de tag
 * 3.1- Executar as anteriores
 * 3.2- 
 * 
 * 
 * Existirão situações que poderemos estar num loop e queremos entrar dentro de uma outra tag e realizar outro loop e outro 
 * e se encontrar o texto ok, senão voltaremos para o 1 loop para testar outra tag e assim por diante. Recursividade
 * Recursividade
 * */

public class HTML extends Command {

	public void extract(Steps steps){
		try {
			executeCommand(steps);
			steps.setCurrencyIndex(1);
			executeSteps(steps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
