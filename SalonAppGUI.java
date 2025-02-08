import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SalonAppGUI {
    private JFrame frame;
    private Salon salon;
    private JTextArea textArea;

    public SalonAppGUI() {
        salon = new Salon(); // Asumăm că există o instanță de Salon deja creată
        initialize();
    }

    private void initialize() {
        // Creare fereastră principală
        frame = new JFrame("Salon App");
        frame.setBounds(100, 100, 600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(3, 3, 10, 10)); // 3x3 grid pentru 9 butoane

        // Crearea butoanelor
        JButton btnProgrameaza = new JButton("Programează");
        btnProgrameaza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraProgramare();
            }
        });
        frame.getContentPane().add(btnProgrameaza);

        JButton btnAdaugaFrizer = new JButton("Adaugă Frizer");
        btnAdaugaFrizer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraAdaugaFrizer();
            }
        });
        frame.getContentPane().add(btnAdaugaFrizer);

        JButton btnVizualizeazaProgramari = new JButton("Vizualizează Programări");
        btnVizualizeazaProgramari.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraVizualizareProgramari();
            }
        });
        frame.getContentPane().add(btnVizualizeazaProgramari);

        JButton btnVizualizeazaClienti = new JButton("Vizualizează Clienți");
        btnVizualizeazaClienti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraVizualizareClienti();
            }
        });
        frame.getContentPane().add(btnVizualizeazaClienti);

        JButton btnAnuleazaProgramare = new JButton("Anulează Programare");
        btnAnuleazaProgramare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraAnulareProgramare();
            }
        });
        frame.getContentPane().add(btnAnuleazaProgramare);

        JButton btnVizualizeazaFrizeri = new JButton("Vizualizează Frizeri");
        btnVizualizeazaFrizeri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraVizualizareFrizeri();
            }
        });
        frame.getContentPane().add(btnVizualizeazaFrizeri);

        JButton btnStergeClient = new JButton("Șterge Client");
        btnStergeClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraStergeClient();
            }
        });
        frame.getContentPane().add(btnStergeClient);

        JButton btnStergeFrizer = new JButton("Șterge Frizer");
        btnStergeFrizer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deschideFereastraStergeFrizer();
            }
        });
        frame.getContentPane().add(btnStergeFrizer);

        JButton btnExit = new JButton("Ieșire");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(btnExit);

        // Afișează fereastra principală
        frame.setVisible(true);
    }

    // Fereastra pentru crearea unei programări
    private void deschideFereastraProgramare() {
        JFrame fereastraProgramare = new JFrame("Programează");
        fereastraProgramare.setSize(300, 200);
        fereastraProgramare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField numeClient = new JTextField();
        JTextField numeFrizer = new JTextField();
        JTextField serviciu = new JTextField();
        JTextField dataOra = new JTextField();

        panel.add(new JLabel("Nume Client:"));
        panel.add(numeClient);
        panel.add(new JLabel("Nume Frizer:"));
        panel.add(numeFrizer);
        panel.add(new JLabel("Serviciu:"));
        panel.add(serviciu);
        panel.add(new JLabel("Data și Ora:"));
        panel.add(dataOra);

        JButton btnProgrameaza = new JButton("Programează");
        btnProgrameaza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logica pentru programare
                textArea.append("Programare realizată pentru " + numeClient.getText() + "\n");
                fereastraProgramare.dispose();
            }
        });

        panel.add(btnProgrameaza);
        fereastraProgramare.add(panel);
        fereastraProgramare.setVisible(true);
    }

    // Fereastra pentru adăugarea unui frizer
    private void deschideFereastraAdaugaFrizer() {
        JFrame fereastraFrizer = new JFrame("Adaugă Frizer");
        fereastraFrizer.setSize(300, 200);
        fereastraFrizer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField numeFrizer = new JTextField();
        JTextField telefonFrizer = new JTextField();

        panel.add(new JLabel("Nume Frizer:"));
        panel.add(numeFrizer);
        panel.add(new JLabel("Telefon Frizer:"));
        panel.add(telefonFrizer);

        JButton btnAdaugaFrizer = new JButton("Adaugă Frizer");
        btnAdaugaFrizer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Logica pentru adăugare frizer
                textArea.append("Frizer adăugat: " + numeFrizer.getText() + "\n");
                fereastraFrizer.dispose();
            }
        });

        panel.add(btnAdaugaFrizer);
        fereastraFrizer.add(panel);
        fereastraFrizer.setVisible(true);
    }

    // Fereastra pentru vizualizarea programărilor
    private void deschideFereastraVizualizareProgramari() {
        JFrame fereastraVizualizare = new JFrame("Programări");
        fereastraVizualizare.setSize(400, 300);
        fereastraVizualizare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textArea = new JTextArea();
        fereastraVizualizare.add(new JScrollPane(textArea));
        fereastraVizualizare.setVisible(true);
    }

    // Fereastra pentru vizualizarea clienților
    private void deschideFereastraVizualizareClienti() {
        JFrame fereastraVizualizare = new JFrame("Clienți");
        fereastraVizualizare.setSize(400, 300);
        fereastraVizualizare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textArea = new JTextArea();
        fereastraVizualizare.add(new JScrollPane(textArea));
        fereastraVizualizare.setVisible(true);
    }

    // Fereastra pentru anularea unei programări
    private void deschideFereastraAnulareProgramare() {
        String idProgramare = JOptionPane.showInputDialog("Introduceți ID-ul programării de anulat:");
        // Logica pentru anulare
        textArea.append("Programare cu ID " + idProgramare + " a fost anulată.\n");
    }

    // Fereastra pentru vizualizarea frizerilor
    private void deschideFereastraVizualizareFrizeri() {
        JFrame fereastraVizualizare = new JFrame("Frizeri");
        fereastraVizualizare.setSize(400, 300);
        fereastraVizualizare.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textArea = new JTextArea();
        fereastraVizualizare.add(new JScrollPane(textArea));
        fereastraVizualizare.setVisible(true);
    }

    // Fereastra pentru ștergerea unui client
    private void deschideFereastraStergeClient() {
        String numeClient = JOptionPane.showInputDialog("Introduceți numele clientului de șters:");
        // Logica pentru ștergere client
        textArea.append("Clientul " + numeClient + " a fost șters.\n");
    }

    // Fereastra pentru ștergerea unui frizer
    private void deschideFereastraStergeFrizer() {
        String numeFrizer = JOptionPane.showInputDialog("Introduceți numele frizerului de șters:");
        // Logica pentru ștergere frizer
        textArea.append("Frizerul " + numeFrizer + " a fost șters.\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    SalonAppGUI window = new SalonAppGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
