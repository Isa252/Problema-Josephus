import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import ListaCircular.*;
import ListaCircular.ListaDuplamenteLigadaCircular;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

/**
 * Esta classe representa a interface gráfica para resolver o problema de Josephus
 * usando uma lista duplamente ligada circular.
 * 
 * @author Isabela Groke
 * @version 20/06/2024
 */
public class InterfaceJosephus extends JFrame{
    //Variaveis de estado e componentes da interface
    private JPanel prinPanel;
    private JFrame jPrin;

    //Variaveis para configuracao inicial
    int k = 0;
    int linhas = 0;
    int individuosExecutados = 0;
    int tempo = 0;

    JLabel individuoExecutadoLabel;
    JLabel individuosVivosLabel;
    JLabel[] _labels = null; 
    Container _pAmbiente = null;

    //componentes da interface grafica
    Container botoesSouth = null;   //container dos botoes
    JButton botaoSobre = null;
    JButton  botaoExecutar= null;
    JButton  botaoReiniciar = null;
    JButton botaoConfigurar = null;
    JButton  botaoSair = null;

    Container textosNorth = null;   //container dos textos e campo de texto
    JTextField numIndivField = null;
    JTextField intervaloField = null;
    JTextField tempField = null;
    JLabel numIndivLabel = null;
    JLabel intervaloLabel = null;
    JLabel tempLabel = null;

    //estrutura de dados para os individuos
    IListaDuplamenteLigadaCircular list = new ListaDuplamenteLigadaCircular();
    Elemento elemento = null;

    /**
     * Construtor da classe InterfaceJosephus.
     * 
     */
    InterfaceJosephus(String titulo){
        super(titulo);
        textosNorth = new JPanel();
        textosNorth.setLayout(new FlowLayout());

        //numero padrao inicial das variaveis
        linhas = 120;
        k = 13;
        tempo = 100;

        jPrin = new JFrame();
        jPrin.setSize(800,600); // Define o tamanho da janela principal
        jPrin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // painel de ambiente grafico
        _pAmbiente = new JPanel(); // cria painel
        _pAmbiente.setLayout(new GridLayout(linhas,15)); // define layout
        _labels = new JLabel [linhas]; // cria array de jlabels

        // Painel de botoes
        botoesSouth = new JPanel();
        botoesSouth.setLayout(new FlowLayout());
    }

    /**
     * Método para adicionar componentes à janela principal.
     */
    public void adicionaComponentes() {
        // Pega o container da janela principal
        Container pane = jPrin.getContentPane();

        pane.removeAll(); // Remove todos os componentes existentes

        // Insere painel de ambiente e as celulas(jlabels)
        inserePainelAmbiente(pane);
        textosFields(); // Insere os campos de texto e rótulos
        inserePainelBotoes(pane); // Insere os botoes

        jPrin.pack();
        jPrin.setVisible(true);  // Torna a janela visivel
    }

    /**
     * Metodo para desenhar o ambiente gráfico com as células dos indivíduos mortos.
     */
    private void desenhaAmbiente(){
        int individuosVivos = 0;
        No elem = list.getInicio(); //obtem o primeiro elemento da lista ligada circular
        int quant = list.getQtdNos(); //obtem a quantidade de elementos da lista
        int i = 0;
        int count = 1;
        Elemento ele = null;        //variavel do elemento    
        ImageIcon imagem = null;    
        int j = 1;

        while ((elem != null) && (count != quant)){
            //vai pegando os elementos de acordo com o intervalo
            while(j != i + k){
                elem = elem.getProximo(); 
                j++;
                ele = (Elemento) elem.getConteudo();
                //se o elemento for false ele aumenta a quantidade
                if(ele.getCelula().getValorCelula() == false){
                    j++;
                    i+=2;
                }
            }
            ele = (Elemento) elem.getConteudo();
            if (ele.getCelula().getValorCelula() == true) {
                imagem = new ImageIcon("imagens/morto.png");
                //mostra o numero do morto
                individuosExecutados = ele.getValor() + 1;
                individuoExecutadoLabel.setText("Individuo executado: " + individuosExecutados);
                //coloca o valor da celula como false
                ele.getCelula().setValorCelula(false);
                count++; //contagem para parar
            }else if(ele.getCelula().getValorCelula() == false){
                //pega a proximo se ele for negativo, precisa para a primeira volta depois de trocar para negativo 
                No prox = elem.getProximo();
                ele = (Elemento) prox.getConteudo();
                imagem = new ImageIcon("imagens/morto.png");
                //mostra o numero do morto
                individuosExecutados = ele.getValor() + 1;
                individuoExecutadoLabel.setText("Individuo executado: " + individuosExecutados);
                //coloca o valor da celula como false
                ele.getCelula().setValorCelula(false);
                count++; //contagem para parar  
            }
            int num = ele.getValor(); //pega o numero que esta na lista
            _labels[num].setIcon(imagem); //coloca as imagens nos numeros
            _labels[num].setPreferredSize(new Dimension(40,40));
            try {
                Thread.sleep((long)(tempo)); //para o tempo
            } catch (InterruptedException e) {
                System.out.println("ERRO INESPERADO");
                System.exit(0);
            }
            //volta a contagem para 1, fazendo com que ele nao pule elemntos
            i = 1;
            j = 1;
        }

        //percorre a lista para ver qual foi o elemento vivo:
        No vivo = list.getInicio(); //obtem o inicio
        int c = list.getQtdNos(); //obtem o tamanho
        int n = 0;
        Elemento alive = null;
        //estora se vivo for null e o tamanho da lista for igual a variavel n 
        while ((vivo != null) && (c != n)){
            alive = (Elemento) vivo.getConteudo();
            if(alive.getCelula().getValorCelula() == true){ 
                imagem = new ImageIcon("imagens/sobrevivente.gif"); //coloca a imagem de sobrevivente
                //tira a parte que fica contando os mortos
                botoesSouth.remove(individuoExecutadoLabel);
                _pAmbiente.remove(individuoExecutadoLabel);
                //acrescenta a parte que mostra o individuo que sobreviveu 
                individuosVivos = alive.getValor() + 1;
                botoesSouth.add(individuosVivosLabel);
                individuosVivosLabel.setText("Individuo sobrevivente:" + individuosVivos); 
                //coloca a imagem no lugar necessario
                _labels[alive.getValor()].setIcon(imagem);
                _labels[alive.getValor()].setPreferredSize(new Dimension(40,40));                
            } 
            vivo = vivo.getProximo(); //pega o proximo
            n++; //aumenta a variavel
        }
        _pAmbiente.revalidate();
        _pAmbiente.repaint();

        //volta com os botoes depois do termino
        botaoReiniciar.setEnabled(true); 
        botaoConfigurar.setEnabled(true);    
    }

    /**
     * Método que mostra a interface com os componentes inseridos
     *
     */
    public void mostraAmbienteGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true); // Define o estilo de decoração da janela 

        //Define os componentes.
        adicionaComponentes();

        //Mostra a janela.
        jPrin.pack();
        jPrin.setVisible(true);
    }

    /**
     * Método que constroi a matriz
     *
     * @param pane Um parâmetro
     */
    private void inserePainelAmbiente(Container pane){
        int numLinhas = (_labels.length + 9) / 10; // Calcula o número de linhas necessárias para 10 colunas
        int numColunas = (_labels.length + numLinhas - 1) / numLinhas;
        _pAmbiente.setLayout(new GridLayout(numLinhas, numColunas));
        

        for (int i = 0; i < _labels.length; i++){   
            final int numero = i;
            _labels[i] =  new JLabel (); 
            _labels[i].setPreferredSize(new Dimension (40, 40));
            // Define o ícone como "vivo" inicialmente
            ImageIcon imagem = new ImageIcon("imagens/vivo.png");
            _labels[i].setIcon(imagem);

            elemento = new Elemento(i);
            elemento.getCelula().setValorCelula(true);
            list.inserirFim(elemento); //coloca na lista

            // Adiciona o listener de mouse para mostrar o número da célula ao passar o mouse
            _labels[i].addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        //para começar como 1
                        int numCelula = numero + 1;
                        _labels[numero].setToolTipText("" + numCelula); // Define o tooltip com o número da célula
                    }
                });

            //Adiciona o jlabel para o painel de ambiente
            _pAmbiente.add(_labels[i]);
        }

        // Adiciona o painel de ambiente ao container principal
        pane.add("Center", _pAmbiente);
    }

    /**
     * parte que coloca os botoes na interface e tem o addActionListener para fazer o que
     * precisa quando eles forem precionados.
     *
     */
    private void inserePainelBotoes(Container pane){
        // Cria e adiciona botoes ao painel de botoes
        botaoExecutar = new JButton("Executar");
        botaoExecutar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botaoExecutar.setEnabled(false);
                    botaoReiniciar.setEnabled(false);
                    botaoConfigurar.setEnabled(false);
                    //adiciona a parte que o texto dos individuos mortos
                    individuoExecutadoLabel = new JLabel("Individuo executado: ", JLabel.RIGHT);
                    botoesSouth.add(individuoExecutadoLabel);
                    individuosVivosLabel = new JLabel("Individuo sobrevivente: ", JLabel.RIGHT);
                    new Thread(() -> desenhaAmbiente()).start();     
                }
            });
        botoesSouth.add(botaoExecutar);

        botaoReiniciar = new JButton("Reiniciar");
        botaoReiniciar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try{
                        linhas = Integer.parseInt(numIndivField.getText());
                        if (InvalidoIndividuo(linhas)) {
                            return; // Retorna sem fazer nada se o número for inválido
                        }
                        InvalidoInt(linhas);

                        k = Integer.parseInt(intervaloField.getText());
                        if (InvalidoIntervalo(k)) {
                            return; // Retorna sem fazer nada se o número for inválido
                        }
                        InvalidoInt(k);

                        tempo = Integer.parseInt(tempField.getText());
                        if (InvalidoInt(tempo)) {
                            return; // Retorna sem fazer nada se o tempo de espera for inválido
                        }
                    }catch(Exception ex){     
                        JOptionPane.showMessageDialog(jPrin, "Outra Exceção: " + ex.getMessage());
                    }
                    _pAmbiente.removeAll();
                    textosNorth.removeAll();
                    botoesSouth.removeAll();

                    //jo = new Josephus(linhas);
                    list = new ListaDuplamenteLigadaCircular();
                    _labels = new JLabel[linhas];

                    // Recria os componentes de ambiente e botões
                    inserePainelAmbiente(jPrin.getContentPane());
                    textosFields();
                    inserePainelBotoes(jPrin.getContentPane());

                    // Revalida e repinta o container principal (pane)
                    jPrin.getContentPane().revalidate();
                    jPrin.getContentPane().repaint();

                    // Habilita ou desabilita campos de texto conforme necessário
                    setTextFieldsEnabled(false);
                    //mortosArea.setVisible(false);
                    botaoReiniciar.setEnabled(false); 
                    botaoExecutar.setEnabled(true);
                }
            });
        botaoReiniciar.setEnabled(false); 
        botoesSouth.add(botaoReiniciar);

        botaoConfigurar = new JButton("Configurar");
        botaoConfigurar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    botaoReiniciar.setEnabled(true); 
                    setTextFieldsEnabled(true);
                    botaoExecutar.setEnabled(false); 
                }
            });
        botoesSouth.add(botaoConfigurar);

        botaoSair = new JButton("Sair");
        botaoSair.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); 
                }
            });
        botoesSouth.add(botaoSair);

        // Adiciona o painel de botoes ao container principal
        jPrin.add(botoesSouth, "South");
    }

    /**
     *  coloca os labels e os fields necessarios
     */
    private void textosFields(){
        numIndivLabel = new JLabel("Numero de individuos: ", JLabel.RIGHT);
        numIndivField = new JTextField(String.valueOf(linhas),5);
        numIndivField.setEnabled(false); 
        textosNorth.add(numIndivLabel);
        textosNorth.add(numIndivField);

        intervaloLabel = new JLabel("Intervalo: ", JLabel.RIGHT);
        intervaloField = new JTextField(String.valueOf(k),5);
        intervaloField.setEnabled(false);
        textosNorth.add(intervaloLabel);
        textosNorth.add(intervaloField);

        tempLabel = new JLabel("Tempo de espera: ", JLabel.RIGHT);
        tempField = new JTextField(String.valueOf(tempo),5);
        tempField.setEnabled(false); 
        textosNorth.add(tempLabel);
        textosNorth.add(tempField);

        botaoSobre = new JButton("Sobre");
        botaoSobre.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    sobre(); 
                }
            });
        textosNorth.add(botaoSobre);

        // Adiciona o painel de botoes ao container principal
        jPrin.add(textosNorth, "North");
    }
    
    /**
     *  breve discrição
     */
    private void sobre(){
        String texto = "Problema Josephus\n"
            + "Esta aplicação implementa o algoritmo do Josephus com Lista Duplamente Ligada Circular"
            + "LED - Laboratorio de Estruturas Dinamicas\n"
            + "Aluna. Isabela Groke\nCiencia da Computacao - PUCSP";

        JOptionPane.showMessageDialog(null, texto, "Sobre Josephus", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     *  metodo que vai colocar os textos como pusseiveis de digitar(true) ou nao(false)
     */
    private void setTextFieldsEnabled(boolean enabled) {
        numIndivField.setEnabled(enabled);
        intervaloField.setEnabled(enabled);
        tempField.setEnabled(enabled);
    }

    /**
     * devolve um boolean false se o numero for menor que zero
     */
    public boolean InvalidoInt(int x) {
        if (x < 0) {
            JOptionPane.showMessageDialog(jPrin, "Erro: o tempo de espera não pode ser negativo");
            return true; // Retorna true se o número for inválido
        }
        return false; // Retorna false se o número for válido
    }
    
    /**
     * devolve um boolean false se o individuo nao seguir a quantidade aceita
     */
    public boolean InvalidoIndividuo(int x) {
        if (x < 14) {
            JOptionPane.showMessageDialog(jPrin, "Número inválido: coloque um número maior que 13");
            return true; // Retorna true se o número for inválido
        } else if (x > 120) {
            JOptionPane.showMessageDialog(jPrin, "Número inválido: o número de indivíduos deve ser menor que 120");
            return true; // Retorna true se o número for inválido
        }
        return false; // Retorna false se o número for válido
    }
    
    /**
     * devolve um boolean false se o intervalo nao seguir a quantidade aceita
     */
    public boolean InvalidoIntervalo(int x) {
        if (x < 2 || x >= linhas) {
            JOptionPane.showMessageDialog(jPrin, "Número inválido: o intervalo deve ser maior que 1 e menor que o número de indivíduos");
            return true; // Retorna true se o número for inválido
        }
        return false; // Retorna false se o número for válido
    }
}