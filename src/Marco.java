import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Clase que representa el marco donde esta la calculadora
 * y que se encarga de las operaciónes
 * 
 * @author Ismael Catoira Rial
 */
public class Marco extends JFrame{
    
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

        Lamina lamina = new Lamina();
        add(lamina);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Marco();
    }
}
