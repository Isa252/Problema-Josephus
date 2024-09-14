
/**
 * Escreva uma descrição da classe Celula aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Celula
{
    
    boolean _valorCelula;
    

    Celula(){
        _valorCelula = false;
    }

    public boolean getValorCelula() {
       return _valorCelula;
    }

    /**
     * setValorCelula
     *
     * parametros:
     *  entrada:    int valorCelula: false - Morta, true - Viva
     *  saida:
     *
     * retorno:
     */
    public void setValorCelula(boolean valorCelula) {
       _valorCelula = valorCelula;
    }
}
