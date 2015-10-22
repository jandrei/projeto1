package comum;

/**
 *
 * Utilizado em conjunto com o servidor, facilita a integração com um sistema desktop.
 * as telas do sistema saberão quando algum evento ocorreu com sensores ou atuadores.
 * então o servidor chamará os respectivos métodos para atualizar algo na tela.
 *
 */
public interface IServidorTela {
	public void atualizaAtuadores();
	public void atualizaSensores();

}
