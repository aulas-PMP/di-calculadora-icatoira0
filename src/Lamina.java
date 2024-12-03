import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Clase que contiene la calculadora y la lógica de esta
 * 
 * @author Ismael Catoira Rial
 */
public class Lamina extends JPanel implements ActionListener, KeyListener {
    /** Campo de texto que guarda el dato anterior */
    JTextField datoAlmacenado = new JTextField();
    /** Campo de texto donde se introduciran los números */
    JTextField texto = new JTextField();
    /** Botón para cambiar el tipo de como introduciremos los datos */
    JButton cambioEntrada;
    /** Panel que contiene los botones númericos */
    JPanel panelOp;
    /** Panel que contiene los botones de operadores */
    JPanel panelNum;

    /** Número 1 de la operación a realiazar */
    private double num1 = 0;
    /** Números 2 de la operación a realizar */
    private double num2 = 0;
    /** Operador para saber que operación se esta realizando */
    private String operador = "";
    /** Si se acaba de hacer un calculo */
    private boolean recienCalc = false;

    public Lamina() {
        // Cambiamos el tipo de Layout del panel principal
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(188, 188, 188));

        // Creación del botón para cambiar el tipo de entrada
        cambioEntrada = new JButton("Libre");
        cambioEntrada.setFont(new Font("Arial", Font.BOLD, 45));
        cambioEntrada.setBackground(new Color(242, 242, 242));
        cambioEntrada.setFocusable(false);
        add(cambioEntrada);
        cambioEntrada.setAlignmentX(Component.CENTER_ALIGNMENT);
        cambioEntrada.addActionListener(this);

        // Creación del JTextField que almacenara el dato anterior
        datoAlmacenado.setHorizontalAlignment(SwingConstants.RIGHT);
        datoAlmacenado.setFont(new Font("Arial", Font.BOLD, 50));
        datoAlmacenado.setEditable(false);
        datoAlmacenado.setFocusable(false);
        add(datoAlmacenado);

        // Creación del JTextField en el que se pondra los números y mostrara el
        // resultado de las operaciones
        texto.setHorizontalAlignment(SwingConstants.RIGHT);
        texto.setFont(new Font("Arial", Font.BOLD, 50));
        texto.setEditable(false);
        add(texto);

        // Creación del panel de botones con los números, operadores y el de borrar
        JPanel botones = crearPanelBotones();
        add(botones);

        modoLibre();
    }

    /**
     * Crea el panel que contiene a los 2 paneles con los botones de la calculadora
     * 
     * @return El panel creado
     */
    private JPanel crearPanelBotones() {
        // Creamos el panel que contendra los 2 paneles
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 6, 6));
        // Creación del panel numérico y de operadores
        panelNum = crearPanelNum();
        panelOp = crearPanelOp();
        panelBotones.add(panelNum);
        panelBotones.add(panelOp);
        panelBotones.setBackground(new Color(188, 188, 188));
        return panelBotones;
    }

    /**
     * Crea el panel que contiene los botones
     * 
     * @return El panel creado
     */
    private JPanel crearPanelNum() {
        JPanel panelNum = new JPanel();
        panelNum.setLayout(new GridLayout(4, 3, 6, 6));
        String[] botones = { "7", "8", "9", "4", "5", "6", "1", "2", "3", "0", "," };
        for (String boton : botones) {
            JButton button = new JButton(boton);
            button.setFont(new Font("Arial", Font.BOLD, 45));
            panelNum.add(button);
            button.setFocusable(false);
            button.setBackground(new Color(242, 242, 242));
        }
        panelNum.setBackground(new Color(188, 188, 188));
        return panelNum;
    }

    /**
     * Crea el panel que contine los operadores
     * 
     * @return El panel creado
     */
    private JPanel crearPanelOp() {
        JPanel panelNum = new JPanel();
        panelNum.setLayout(new GridLayout(2, 3, 6, 6));
        String[] botones = { "/", "*", "-", "+", "CE", "=" };
        for (String boton : botones) {
            JButton button = new JButton(boton);
            button.setFont(new Font("Arial", Font.BOLD, 45));
            panelNum.add(button);
            button.setFocusable(false);
            if (!boton.equals("=")) {
                button.setBackground(new Color(242, 242, 242));

            } else {
                button.setBackground(new Color(255, 108, 0));
            }
        }
        panelNum.setBackground(new Color(188, 188, 188));
        return panelNum;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pulsado = e.getActionCommand();
        if (!"Libre Teclado Ratón".contains(pulsado)) {
            insertar(pulsado, pulsado.equals("="));
        } else {
            if (pulsado.equals("Libre")) {
                modoTeclado();
                cambioEntrada.setText("Teclado");
            } else if (pulsado.equals("Teclado")) {
                modoRaton();
                cambioEntrada.setText("Ratón");
            } else {
                modoLibre();
                cambioEntrada.setText("Libre");
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD) {
            String pulsado = e.getKeyChar() + "";
            insertar(pulsado, e.getKeyCode() == 10);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Realiza la operación
     * 
     * @param num1     Número 1 usado en la operación
     * @param num2     Número 2 usado en la operación
     * @param operador Operador para determinar que operación se realiza
     * @return El resultado de la operación
     */
    private static double calcular(double num1, double num2, String operador) {
        double resultado;
        switch (operador) {
            case "+":
                resultado = num1 + num2;
                break;
            case "-":
                resultado = num1 - num2;
                break;
            case "*":
                resultado = num1 * num2;
                break;
            case "/":
                resultado = num1 / num2;
                break;
            default:
                resultado = 0;
                break;
        }
        return resultado;
    }

    /**
     * Método con el que añadimos números o limpiamos el texto cuano clicamos un
     * botón o pulsamos una tecla
     * 
     * @param pulsado  El valor en formato String pulsado
     * @param introPad Si el valor pulsado fue Intro o el botón "="
     */
    private void insertar(String pulsado, boolean introPad) {
        try {
            texto.setForeground(Color.BLACK);
            String dato = "";
            if ("+-*/".contains(pulsado) && !texto.getText().isEmpty()) {
                if (num1 == 0) {
                    // Añade el texto que haya en el texto convirtiendolo en número
                    num1 = Double.parseDouble(texto.getText().replace(",", "."));
                    // Añade el operador de la operación que realizara
                    operador = pulsado;
                    // Muestra el dato anterior en parte de ariba
                    dato = num1 + "";
                    dato = dato.replace(".", ",");
                    if (dato.endsWith(",0")) {
                        dato = dato.replace(",0", "");
                    }
                    recienCalc = false;
                    datoAlmacenado.setText(dato);
                    // Limpia el texto para introducir el num2
                    texto.setText("");
                } else {
                    // Pone el texto que haya en num2 y realiza la operación dejando el
                    num2 = Double.parseDouble(texto.getText().replace(",", "."));
                    num1 = calcular(num1, num2, operador);
                    // Muestra el resultado de la operación anterior en parte de arriba
                    dato = num1 + "";
                    dato = dato.replace(".", ",");
                    if (dato.endsWith(",0")) {
                        dato = dato.replace(",0", "");
                    }
                    datoAlmacenado.setText(dato);
                    // Limpia el num2 y pone el operador para la nueva suma
                    operador = pulsado;
                    num2 = 0;
                    texto.setText("");
                }
            } else if ("0123456789,.".contains(pulsado)) {
                // Comprueba que si se hizo una operación recientemente y Limpia la operación de
                // la pantalla
                if (recienCalc) {
                    texto.setText("");
                    datoAlmacenado.setText("");
                    recienCalc = false;
                }
                // Si se quiere poner una "," se comprueba si ya hay alguno escrito y si no hay lo
                // pone
                if (pulsado.equals(".") || pulsado.equals(",")) {
                    if (!texto.getText().contains(",")) {
                        texto.setText(texto.getText() + ",");
                    }
                } else {
                    texto.setText(texto.getText() + pulsado);
                }
            } else if (introPad) {
                // Si no es un igual despues de hacer una operación
                if (!recienCalc) {
                    // Guarda el num2 y calcula
                    num2 = Double.parseDouble(texto.getText().replace(",", "."));
                    String resultado = calcular(num1, num2, operador) + "";
                    resultado = resultado.replace(".", ",");
                    recienCalc = true;

                    // Si el resultado no tiene decimales quita el .0
                    if (resultado.endsWith(",0")) {
                        resultado = resultado.replace(",0", "");
                        texto.setText(resultado);
                    } else {
                        texto.setText(resultado);
                    }

                    // Si el num2 no tiene decimales quita el .0 y despues lo muestra arriba
                    dato = datoAlmacenado.getText() + operador + num2;
                    dato = dato.replace(".", ",");
                    if (dato.endsWith(",0")) {
                        dato = dato.replace(",0", "");
                    }
                    datoAlmacenado.setText(dato);

                    // Si el resultado es negativo cambia el color a Rojo
                    if (Double.parseDouble(texto.getText().replace(",", ".")) < 0) {
                        texto.setForeground(Color.RED);
                    }
                    num1 = 0;
                    num2 = 0;
                    operador = "";
                } else {
                    texto.setText("");
                    datoAlmacenado.setText("");
                }
            } else if (texto.getText().isEmpty() && pulsado.equals("-")) {
                // Poner - para números negativos
                texto.setText(pulsado);
            } else {
                // Limpia el texto si es algo que no reconoce
                texto.setText("");
                num1 = 0;
                num2 = 0;
                datoAlmacenado.setText("");
            }
        } catch (NullPointerException | NumberFormatException e) {
            num1 = 0;
            num2 = 0;
            texto.setText("");
        }
    }

    /**
     * Cambia el modo de entrada para que solo se pueda usar el teclado
     */
    private void modoTeclado() {
        Component[] botonesNum = panelNum.getComponents();
        Component[] botonesOp = panelOp.getComponents();

        for (Component component : botonesNum) {
            JButton boton = (JButton) component;
            boton.removeActionListener(this);
        }

        for (Component component : botonesOp) {
            JButton boton = (JButton) component;
            boton.removeActionListener(this);
        }
    }

    /**
     * Cambia el modo de entrada para que solo se pueda usar el raton
     */
    private void modoRaton() {
        Component[] botonesNum = panelNum.getComponents();
        Component[] botonesOp = panelOp.getComponents();

        for (Component component : botonesNum) {
            JButton boton = (JButton) component;
            boton.addActionListener(this);
        }

        for (Component component : botonesOp) {
            JButton boton = (JButton) component;
            boton.addActionListener(this);
        }

        texto.removeKeyListener(this);
    }

    /**
     * Cambia el modo de entrada en el que se puede usar el raton y el teclado
     */
    private void modoLibre() {
        Component[] botonesNum = panelNum.getComponents();
        Component[] botonesOp = panelOp.getComponents();

        for (Component component : botonesNum) {
            JButton boton = (JButton) component;
            boton.removeActionListener(this);
            boton.addActionListener(this);
        }

        for (Component component : botonesOp) {
            JButton boton = (JButton) component;
            boton.removeActionListener(this);
            boton.addActionListener(this);
        }

        texto.addKeyListener(this);
    }

    /**
     * Limpia los datos de todo
     */
    public void limpiar() {
        texto.setText("");
        datoAlmacenado.setText("");
        num1 = 0;
        num2 = 0;
        operador = "";
    }
}
