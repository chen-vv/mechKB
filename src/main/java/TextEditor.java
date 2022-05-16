import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextEditor extends JFrame implements ActionListener {
    // Text editor
    private static JTextArea area;
    private Volume volume;

    public TextEditor() { run(); }

    public void run() {
        // Main window
        JFrame frame = new JFrame("Text Editor");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            volume = new Volume();
        } catch(Exception e) {
            Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, e);
        }

        // Set attributes of the app window
        area = new JTextArea();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(area);
        frame.setSize(640, 480);
        frame.setVisible(true);

        // Build the menu
        JMenuBar menu_main = new JMenuBar();
        JMenu menu_file = new JMenu("File");
        JMenuItem menuItem_new = new JMenuItem("New");
        JMenuItem menuItem_open = new JMenuItem("Open");
        JMenuItem menuItem_save = new JMenuItem("Save");
        JMenuItem menuItem_quit = new JMenuItem("Quit");

        menuItem_new.addActionListener(this);
        menuItem_save.addActionListener(this);
        menuItem_open.addActionListener(this);
        menuItem_quit.addActionListener(this);

        menu_main.add(menu_file);
        menu_file.add(menuItem_new);
        menu_file.add(menuItem_save);
        menu_file.add(menuItem_open);
        menu_file.add(menuItem_quit);

        frame.setJMenuBar(menu_main);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        int returnValue = 0;
        if (ae.equals("Open")) {
            returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                try {
                    FileReader read = new FileReader(f);
                    Scanner scan = new Scanner(read);
//                    while(scan.hasNextLine()) {
//                        String line = scan.nextLine() + "\n";
//                        ingest = ingest + line;
//                    }
                    while(scan.hasNext()) {
                        String line = scan.next().toUpperCase();
                        switch (line) {
                            case("A"):
                                volume.playMusic(volume.s1);
                            default:
                                volume.playMusic(volume.s2);
                        }
                        ingest = ingest + line;
                    }
                    System.out.println(ingest);
                    area.setText(ingest);
                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                }
            }
        }

        // SAVE
        else if (ae.equals("Save")) {
            returnValue = jfc.showSaveDialog(null);
            try {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                FileWriter out = new FileWriter(f);
                out.write(area.getText());
                out.close();
            } catch (FileNotFoundException fnfe) {
                Component f = null;
                JOptionPane.showMessageDialog(f, "File not found.");
            } catch (IOException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f, "Error.");
            }
        } else if (ae.equals("New")) {
            area.setText("");
        } else if (ae.equals("Quit")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        TextEditor test = new TextEditor();
    }
}
