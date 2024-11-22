import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Lamina extends JPanel implements ActionListener, KeyListener {

    JTextField texto = new JTextField();

    private double num1 = 0;
    private double num2 = 0;
    private String operador = "";

    public Lamina() {
        setLayout(new BorderLayout());
        texto.setHorizontalAlignment(SwingConstants.RIGHT);
        texto.setFont(new Font("Arial", Font.BOLD, 80));
        texto.setEditable(false);
        add(texto, BorderLayout.NORTH);
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
        try {
            String pulsado = e.getActionCommand();
            if ("+-*/".contains(pulsado) && !texto.getText().isEmpty()) {
                if (num1 == 0) {
                    num1 = Double.parseDouble(texto.getText());
                    operador = pulsado;
                    texto.setText("");
                } else {
                    num2 = Double.parseDouble(texto.getText());
                    num1 = calcular(num1, num2, operador);
                    operador = "";
                    num2 = 0;
                    texto.setText("");
                }
            } else if ("0123456789.".contains(pulsado)) {
                texto.setText(texto.getText() + pulsado);
            } else if (pulsado.equals("=")) {
                num2 = Double.parseDouble(texto.getText());
                texto.setText(calcular(num1, num2, operador) + "");
                num1 = 0;
                num2 = 0;
                operador = "";
            } else {
                texto.setText("");
                num1 = 0;
                num2 = 0;
            }
        } catch (NullPointerException | NumberFormatException f) {
            f.printStackTrace();
            num1 = 0;
            num2 = 0;
            texto.setText("");
        }
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
        }
        return resultado;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD) {
            try {
                String pulsado = e.getKeyChar() + "";
                if ("+-*/".contains(pulsado) && !texto.getText().isEmpty()) {
                    if (num1 == 0) {
                        num1 = Double.parseDouble(texto.getText());
                        operador = pulsado;
                        texto.setText("");
                    } else {
                        num2 = Double.parseDouble(texto.getText());
                        num1 = calcular(num1, num2, operador);
                        operador = "";
                        num2 = 0;
                        texto.setText("");
                    }
                } else if ("0123456789.".contains(pulsado)) {
                    if (!pulsado.equals(".")) {
                        texto.setText(texto.getText() + pulsado);
                    } else {
                        if (!texto.getText().contains(".")) {
                            texto.setText(texto.getText() + pulsado);
                        }
                    }
                } else if (e.getKeyCode() == 10) {
                    num2 = Double.parseDouble(texto.getText());
                    texto.setText(calcular(num1, num2, operador) + "");
                    num1 = 0;
                    num2 = 0;
                    operador = "";
                } else {
                    texto.setText("");
                    num1 = 0;
                    num2 = 0;
                }
            } catch (NullPointerException | NumberFormatException f) {
                f.printStackTrace();
                num1 = 0;
                num2 = 0;
                texto.setText("");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
