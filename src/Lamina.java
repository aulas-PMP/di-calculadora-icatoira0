import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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

public class Lamina extends JPanel implements ActionListener, KeyListener {
    JTextField datoAlmacenado = new JTextField();
    JTextField texto = new JTextField();

    private double num1 = 0;
    private double num2 = 0;
    private String operador = "";
    private boolean recienCalc = false;

    public Lamina() {
        //Cambiamos el tipo de Layout del panel principal
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //Creación del JTextField que almacenara el dato anterior
        datoAlmacenado.setHorizontalAlignment(SwingConstants.RIGHT);
        datoAlmacenado.setFont(new Font("Arial", Font.BOLD, 50));
        datoAlmacenado.setEditable(false);
        datoAlmacenado.setFocusable(false);
        add(datoAlmacenado);
        //Creación del JTextField en el que se pondra los números y mostrara el resultado de las operaciones
        texto.setHorizontalAlignment(SwingConstants.RIGHT);
        texto.setFont(new Font("Arial", Font.BOLD, 50));
        texto.setEditable(false);
        add(texto);
        //Creación del panel de botones con los números, operadores y el de borrar
        JPanel botones = crearPanelBotones();
        add(botones);
        texto.addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Crea el panel que contiene a los 2 paneles con los botones de la calculadora
     * @return El panel creado
     */
    private JPanel crearPanelBotones() {
        //Creamos el panel que contendra los 2 paneles
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 2, 6, 6));
        //Creación del panel numérico y de operadores
        JPanel panelNum = crearPanelNum();
        JPanel panelOp = crearPanelOp();
        panelBotones.add(panelNum);
        panelBotones.add(panelOp);
        panelBotones.setBackground(new Color(188, 188, 188));
        return panelBotones;
    }

    private JPanel crearPanelNum() {
        JPanel panelNum = new JPanel();
        panelNum.setLayout(new GridLayout(4, 3, 6, 6));
        String[] botones = {"7","8","9","4","5","6","1","2","3","0","."};
        for (String boton : botones) {
            JButton button = new JButton(boton);
            button.setFont(new Font("Arial", Font.BOLD, 45));
            panelNum.add(button);
            button.addActionListener(this);
            button.setFocusable(false);
            button.setBackground(new Color(242, 242, 242));
        }
        panelNum.setBackground(new Color(188, 188, 188));
        return panelNum;
    }

    private JPanel crearPanelOp() {
        JPanel panelNum = new JPanel();
        panelNum.setLayout(new GridLayout(2, 3, 6, 6));
        String[] botones = {"/","*","-","+","CE","="};
        for (String boton : botones) {
            JButton button = new JButton(boton);
            button.setFont(new Font("Arial", Font.BOLD, 45));
            panelNum.add(button);
            button.addActionListener(this);
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
        insertar(pulsado, pulsado == "=");
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

    private void insertar(String pulsado, boolean introPad) {
        try {
            texto.setForeground(Color.BLACK);
            String dato ="";
            if ("+-*/".contains(pulsado) && !texto.getText().isEmpty()) {//El operador para introducir el segundo número o encadenar otra operación
                if (num1 == 0) {
                    //Añade el texto que haya en el texto convirtiendolo en número
                    num1 = Double.parseDouble(texto.getText());
                    //Añade el operador de la operación que realizara
                    operador = pulsado;
                    //Muestra el dato anterior en parte de ariba
                    dato = num1 + "";
                    if (dato.endsWith(".0")) {
                        dato = dato.replace(".0", "");
                    }
                    recienCalc = false;
                    datoAlmacenado.setText(dato);
                    //Limpia el texto para introducir el num2
                    texto.setText("");
                }else {
                    //Pone el texto que haya en num2 y realiza la operación dejando el 
                    num2 = Double.parseDouble(texto.getText());
                    num1 = calcular(num1, num2, operador);
                    //Muestra el resultado de la operación anterior en parte de arriba
                    dato = num1 + "";
                    if (dato.endsWith(".0")) {
                        dato = dato.replace(".0", "");
                    }
                    datoAlmacenado.setText(dato);
                    //Limpia el num2 y pone el operador para la nueva suma
                    operador = pulsado;
                    num2 = 0;
                    texto.setText("");
                }
            }else if ("0123456789.".contains(pulsado)) {//Poner un número en la pantalla
                //Comprueba que si se hizo una operación recientemente y Limpia la operación de la pantalla
                if (recienCalc) {
                    texto.setText("");
                    datoAlmacenado.setText("");
                    recienCalc = false;
                }
                //Si se quiere poner un . se comprueba si ya hay alguno escrito y si no hay lo pone
                if (pulsado.equals(".")) {
                    if (!texto.getText().contains(".")) {
                        texto.setText(texto.getText() + pulsado);
                    }
                } else {
                    texto.setText(texto.getText() + pulsado);
                }
            }else if (introPad) {//Si el bóton o tecla pulsado es = o Intro
                //Guarda el num2 y calcula, generando un resultado además de poner el recienCalc en true
                num2 = Double.parseDouble(texto.getText());
                String resultado = calcular(num1, num2, operador) + "";
                recienCalc = true;

                //Si el resultado no tiene decimales quita el .0
                if (resultado.endsWith(".0")) {
                    resultado = resultado.replace(".0", "");
                    texto.setText(resultado);
                } else {
                    texto.setText(resultado);
                }

                //Si el num2 no tiene decimales quita el .0 y despues lo muestra arriba
                dato = datoAlmacenado.getText()+ operador + num2;
                if (dato.endsWith(".0")) {
                    dato = dato.replace(".0", "");
                }
                datoAlmacenado.setText(dato);

                //Si el resultado es negativo cambia el color a Rojo
                if (Double.parseDouble(texto.getText()) < 0) {
                    texto.setForeground(Color.RED);
                }
                num1 = 0;
                num2 = 0;
                operador = "";
            }else if (texto.getText().isEmpty() && pulsado.equals("-")) {//Poner - para números negativos
                texto.setText(pulsado);
            }else{
                texto.setText("");
                num1 = 0;
                num2 = 0;
                datoAlmacenado.setText("");
            }
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            num1 = 0;
            num2 = 0;
            texto.setText("");
        }
    }
}
