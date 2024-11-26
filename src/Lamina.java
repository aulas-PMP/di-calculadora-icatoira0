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

        datoAlmacenado.setHorizontalAlignment(SwingConstants.RIGHT);
        datoAlmacenado.setFont(new Font("Arial", Font.BOLD, 50));
        datoAlmacenado.setEditable(false);
        datoAlmacenado.setFocusable(false);
        add(datoAlmacenado);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        texto.setHorizontalAlignment(SwingConstants.RIGHT);
        texto.setFont(new Font("Arial", Font.BOLD, 50));
        texto.setEditable(false);
        add(texto);

        JPanel botones = crearPanel();
        add(botones);
        texto.addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private JPanel crearPanel() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 4, 6, 6));
        String[] botones = { "7", "8", "9", "CE", "4", "5", "6", "+", "1", "2", "3", "-", "0", "*", "/", "=", "." };
        for (String boton : botones) {
            JButton button = new JButton(boton);
            button.setFont(new Font("Arial", Font.BOLD, 45));
            panelBotones.add(button);
            button.addActionListener(this);
            button.setFocusable(false);
            if (!boton.equals("=")) {
                button.setBackground(new Color(242, 242, 242));

            } else {
                button.setBackground(new Color(255, 108, 0));
            }
        }

        panelBotones.setBackground(new Color(188, 188, 188));
        return panelBotones;
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
        double resultado = 0;
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
            case "":
                resultado = 0;
                break;
        }
        return resultado;
    }

    private void insertar(String pulsado, boolean introPad) {
        try {
            texto.setForeground(Color.BLACK);
            String dato ="";
            if ("+-*/".contains(pulsado) && !texto.getText().isEmpty()) {
                if (num1 == 0) {
                    num1 = Double.parseDouble(texto.getText());
                    operador = pulsado;

                    dato = num1 + "";
                    if (dato.endsWith(".0")) {
                        dato = dato.replace(".0", "");
                    }
                    datoAlmacenado.setText(dato);

                    texto.setText("");
                } else {
                    num2 = Double.parseDouble(texto.getText());
                    num1 = calcular(num1, num2, operador);

                    dato = num1 + "";
                    if (dato.endsWith(".0")) {
                        dato = dato.replace(".0", "");
                    }
                    datoAlmacenado.setText(dato);

                    operador = "";
                    num2 = 0;
                    texto.setText("");
                }
            } else if ("0123456789.".contains(pulsado)) {
                if (recienCalc) {
                    texto.setText("");
                    datoAlmacenado.setText("");
                }
                
                if (pulsado.equals(".")) {
                    if (!texto.getText().contains(".")) {
                        texto.setText(texto.getText() + pulsado);
                    }
                } else {
                    texto.setText(texto.getText() + pulsado);
                }
            } else if (introPad) {
                num2 = Double.parseDouble(texto.getText());
                String resultado = calcular(num1, num2, operador) + "";

                if (resultado.endsWith(".0")) {
                    resultado = resultado.replace(".0", "");
                    texto.setText(resultado);
                } else {
                    texto.setText(resultado);
                }

                dato = datoAlmacenado.getText()+ operador + num2;
                if (dato.endsWith(".0")) {
                    dato = dato.replace(".0", "");
                }
                datoAlmacenado.setText(dato);

                if (Double.parseDouble(texto.getText()) < 0) {
                    texto.setForeground(Color.RED);
                }
                num1 = 0;
                num2 = 0;
                operador = "";
            } else if (texto.getText().isEmpty() && pulsado.equals("-")) {
                texto.setText(pulsado);
            } else {
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
