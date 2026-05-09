package a1_ver_e1;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowEvent;

/**
 * Oberflaeche und Eventbehandlung des Taschenrechners. Hier wird noch die aeltere Bibliothek AWT verwendet. AWT hat den Vorteil, dass AWT zum Standard gehoert und nichts zusaetzlich installiert werden muss. In Programmieren II werden Sie mit dem modernen JavaFX arbeiten.
 * Sequenz und Selektion (SEQ)
 * @version         2.0
 */
public class Taschenrechner extends Frame{
    /**
     * erste eingegebene Zahl
     */
    private int z1 = 0;
    /**
     * gewünschte Rechenoperation
     */
    private char operation = ' ';
    /**
     * Label für die Anzeige der Zahlen
     */
    private Label display = new Label();

    /**
     * Konstruktor aktiviert die Windows Events und startet die Initialisierung
     */
    public Taschenrechner() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        init();
    }

    /**
     * init Methode Erstellung der Oberflaeche mit GridLayout ein Display
     *
     */
    private void init() {
    	Font fDisplay = new Font("fDisplay", Font.BOLD, 27);
    	Font fBtn = new Font("fBtn", Font.BOLD, 16);
    	
        // Einstellungen fuer den Frame
        this.setLayout(new GridLayout(6, 1));
        this.setSize(new Dimension(198, 295));
        this.setBackground(Color.lightGray);
        this.setTitle("Taschenrechner");
        this.setResizable(false);

        Button[] ziffern = new Button[10];
        // Anhaengen der Fonts, Farbe und der Actionlistener an die Ziffernuttons
        for(int i=0; i<10; i++)
        {
        	Button b = new Button("" + i);
        	b.setFont(fBtn);
        	b.addActionListener(e->this.ziffernbutton(b.getLabel()));
        	ziffern[i] = b;
        }
        // Rechenzeichen-Buttons
        Button btnPlus = new Button("+");
        Button btnMinus = new Button("-");
        Button btnMal = new Button("*");
        Button btnDurch = new Button("/");
        Button[] rechenzeichen = {btnPlus, btnMinus, btnMal, btnDurch};
        for(Button b: rechenzeichen)
        {
	        b.setFont(fBtn);
	        b.addActionListener(e -> operationsbutton(b.getLabel().charAt(0)));
	        b.setForeground(Color.blue);
        }
        
        //die restlichen Buttons:
        Button btnGleich = new Button("=");
        Button btnLeer1 = new Button("");
        Button btnC = new Button("C");
        btnGleich.setFont(fBtn);
        btnGleich.addActionListener(e -> gleichbutton());
        btnGleich.setForeground(Color.red);
        btnC.setFont(fBtn);
        btnC.addActionListener(e -> cButton());
        btnC.setForeground(new Color(19, 140, 44));

        display.setAlignment(Label.RIGHT);
        display.setFont(fDisplay);
        display.setForeground(Color.green);
        display.setBackground(Color.black);
        display.setText("0");

        // Panels fuer das Layout
        Panel pnDisplay = new Panel(new BorderLayout()); // Rechnerdisplay oben
        Panel pnTasten1 = new Panel(new GridLayout(1, 4)); // Zahlenreihe 7-9 und /
        Panel pnTasten2 = new Panel(new GridLayout(1, 4)); // Zahlenreihe 4-6 und *
        Panel pnTasten3 = new Panel(new GridLayout(1, 4)); // Zahlenreihe 1-3 und -
        Panel pnTasten4 = new Panel(new GridLayout(1, 4)); // Leer, 0, C und +
        Panel pnGleich = new Panel(new BorderLayout()); // = Taste
        
        // Hinzufuegen der einzelnen Komponenten zu den Panels
        pnDisplay.add(display);
        pnTasten1.add(ziffern[7]);
        pnTasten1.add(ziffern[8]);
        pnTasten1.add(ziffern[9]);
        pnTasten1.add(btnDurch);
        pnTasten2.add(ziffern[4]);
        pnTasten2.add(ziffern[5]);
        pnTasten2.add(ziffern[6]);
        pnTasten2.add(btnMal);
        pnTasten3.add(ziffern[1]);
        pnTasten3.add(ziffern[2]);
        pnTasten3.add(ziffern[3]);
        pnTasten3.add(btnMinus);
        pnTasten4.add(btnLeer1);
        pnTasten4.add(ziffern[0]);
        pnTasten4.add(btnC);
        pnTasten4.add(btnPlus);
        pnGleich.add(btnGleich);

        // Panels dem Frame hinzufuegen
        this.add(pnDisplay);
        this.add(pnTasten1);
        this.add(pnTasten2);
        this.add(pnTasten3);
        this.add(pnTasten4);
        this.add(pnGleich);
    }


    /**
     * processWindowEvent fuer die Windowsnachrichten zum Schliessen des Programmes
     * ueber den X Button
     *
     * @param WindowEvent
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }
    
    /**
     * programmiert den Druck auf Clear-Button
     */
    private void cButton() {
        display.setText("0");
        operation = ' ';
    }
    
    /**
     * programmiert den Druck auf einer der Ziffern-Buttons
     */
    private void ziffernbutton(String ziffer)
    {
    	String zahl = display.getText();
    	if (zahl.length()==0 || Integer.parseInt(zahl) == 0) {
    		display.setText(ziffer);
        } else if (zahl.length() < 8) {
        	display.setText(zahl + ziffer);
        }
    }
    
    /**
     * programmiert den Druck auf einen der Rechenzeichen-Buttons
     */
    private void operationsbutton(char rechenzeichen)
    {
        if (operation == ' ') {
            operation = rechenzeichen;
            z1 = Integer.parseInt(display.getText());
            display.setText("0");
        }
    }
    
    /**
     * programmiert den Druck auf den Gleich-Button
     */
    private void gleichbutton()
    {
        if (operation != ' ') {
            int erg = 0;
            int z2 = Integer.parseInt(display.getText());
            erg = Rechenwerk.rechnen(z1, z2, operation);
            display.setText("" + erg);
            operation = ' ';
        }
    }
    
    /**
     * bringt einen Taschenrechner auf den Bildschirm
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args)
    {
        Taschenrechner tr = new Taschenrechner();
        tr.setVisible(true);
    }
}// Mein Taschenrechner
