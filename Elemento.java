
/**
 * Escreva uma descrição da classe Elemento aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Elemento {
    private int valor;
    private Celula celula;
    
    public Elemento(int valor) {
        this.valor = valor;
        this.celula = new Celula();
    }

    public int getValor() {
        return valor;
    }

    public Celula getCelula() {
        return celula;
    }
}
