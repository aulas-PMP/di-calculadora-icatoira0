import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Clase que representa el marco donde esta la calculadora
 * y que se encarga de las operaciónes
 * 
 * @author Ismael Catoira Rial
 */
public class Marco extends JFrame implements WindowListener {

    /** Lamina que contiene la calculadora */
    private Lamina lamina;

    /**
     * Constructor paramétrizado
     */
    public Marco() {
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamanoPant = pantalla.getScreenSize();
        int alturaPant = tamanoPant.height;
        int anchoPant = tamanoPant.width;

        setTitle("Calculadora de Ismael");
        setSize(anchoPant / 2, 600);
        setLocation(anchoPant / 4, alturaPant / 4);

        lamina = new Lamina();
        add(lamina);
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Marco();
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        lamina.limpiar();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }
}
