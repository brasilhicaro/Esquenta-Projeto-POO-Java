package excecoes;

public class SemProgramaNaDataAtualException extends Exception{

	public SemProgramaNaDataAtualException () {
		super("N�o existem programas para hoje!");
		
	}
}
