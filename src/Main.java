import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private Double[] coefficients;
    private JFileChooser fileChooser = null;
    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem saveToCVSMenuItem;
    private JMenuItem searchValueMenuItem;



    private JMenuItem getInfoAboutAuther;
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;

    private Box hBoxResult;
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;

    public MainFrame(Double[] coefficients) {
        super("Gornor count polinomial");
        this.coefficients = coefficients;
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Table");
        menuBar.add(tableMenu);

        JMenu AboutMenu = new JMenu("About program");
        menuBar.add(AboutMenu);
        Action saveToTextAction = new AbstractAction("Save txt file") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION)
                    saveToTextFile(fileChooser.getSelectedFile());
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);
        Action saveToGraphicsAction = new AbstractAction("Save data to create grapics") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION) ;
                saveToGraphicsFile(
                        fileChooser.getSelectedFile());
            }
        };

        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);

        Action saveToCSVAction = new AbstractAction("Save as CSV file") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {

                    saveToCSVFile(fileChooser.getSelectedFile());

                }
            }
        };

        saveToCVSMenuItem = fileMenu.add(saveToCSVAction);
        saveToCVSMenuItem.setEnabled(false);
        Action searchValueAction = new AbstractAction("Find polinamial value") {
            public void actionPerformed(ActionEvent event) {
                String value =
                        JOptionPane.showInputDialog(MainFrame.this, "Enter fiend value",
                                "Find value", JOptionPane.QUESTION_MESSAGE);
                renderer.setNeedle(value);
                getContentPane().repaint();
            }
        };

        Action searchDiaposineValueAction = new AbstractAction("Find values in dioposone") {
            public void actionPerformed(ActionEvent event) {

                String begin =
                        JOptionPane.showInputDialog(MainFrame.this, "Enter begin of the dioposone:",
                                "Enter begin of the dioposone", JOptionPane.QUESTION_MESSAGE);

                String end = JOptionPane.showInputDialog(MainFrame.this, "Enter end of the dioposone",
                        "Enter end of the dioposone", JOptionPane.QUESTION_MESSAGE);

                double Begin = Double.valueOf(begin);
                double End = Double.valueOf(end);


                renderer.setBeginOfDioposone(Begin);
                renderer.setEndOfDioposone(End);
                getContentPane().repaint();

            }
        };

        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem = tableMenu.add(searchDiaposineValueAction);
        searchValueMenuItem.setEnabled(false);

        File file = new File("./Auther.jpg");
        Image img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ImageIcon imgIcon = new ImageIcon(img);
        Image image = imgIcon.getImage();
        Image newimg = image.getScaledInstance(150, 150,  java.awt.Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(newimg);
        ImageIcon finalImgIcon = imgIcon;
        Action GetInfo = new AbstractAction("About auther") {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(null,"Dima, Kondarevich, groop 9", "Auther",
                        JOptionPane.INFORMATION_MESSAGE, finalImgIcon);
            }
        };

        getInfoAboutAuther = AboutMenu.add(GetInfo);
        getInfoAboutAuther.setEnabled(true);

        JLabel labelForFrom = new JLabel("X changes from:");
        textFieldFrom = new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
        JLabel labelForTo = new JLabel("to:");
        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());
        JLabel labelForStep = new JLabel("by step:");
        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());
        Box hboxRange = Box.createHorizontalBox();
        hboxRange.setBorder(BorderFactory.createBevelBorder(1));
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.add(labelForFrom);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldFrom);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForTo);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldTo);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForStep);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(textFieldStep);
        hboxRange.add(Box.createHorizontalGlue());


        hboxRange.setPreferredSize(new Dimension(
                Double.valueOf(hboxRange.getMaximumSize().getWidth()).intValue(),
                Double.valueOf(hboxRange.getMinimumSize().getHeight()).intValue() * 2));
        getContentPane().add(hboxRange, BorderLayout.NORTH);
        JButton buttonCalc = new JButton("Count");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {

                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());
                    data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);
                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setRowHeight(30);
                    hBoxResult.removeAll();
                    hBoxResult.add(new JScrollPane(table));
                    getContentPane().validate();
                    saveToTextMenuItem.setEnabled(true);
                    saveToGraphicsMenuItem.setEnabled(true);
                    saveToCVSMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Error folatin num enering", "Error numer format",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton buttonReset = new JButton("Clear");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");
                hBoxResult.removeAll();
                hBoxResult.add(new JPanel());
                saveToTextMenuItem.setEnabled(false);
                saveToGraphicsMenuItem.setEnabled(false);
                saveToCVSMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
                getContentPane().validate();
            }
        });
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setPreferredSize(new Dimension(Double.valueOf(hboxButtons.getMaximumSize().getWidth()).intValue(),
                Double.valueOf(hboxButtons.getMinimumSize().getHeight()).intValue() * 2));
        getContentPane().add(hboxButtons, BorderLayout.SOUTH);
        hBoxResult = Box.createHorizontalBox();
        hBoxResult.add(new JPanel());
        getContentPane().add(hBoxResult, BorderLayout.CENTER);
    }

    protected void saveToGraphicsFile(File selectedFile) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            for (int i = 0; i < data.getRowCount(); i++) {
                out.writeDouble((Double) data.getValueAt(i, 0));
                out.writeDouble((Double) data.getValueAt(i, 1));
                out.writeDouble((Double) data.getValueAt(i, 2));
                out.writeDouble((Double) data.getValueAt(i, 3));
            }
            out.close();
        } catch (Exception e) {
        }
    }

    protected void saveToCSVFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(selectedFile));
            for (int i = 0; i < data.getRowCount(); i++) {
                out.println(String.valueOf(data.getValueAt(i, 0)) + ";" +
                        String.valueOf(data.getValueAt(i, 1)) + ";" +
                        String.valueOf(data.getValueAt(i, 2)) + ";" +
                        String.valueOf(data.getValueAt(i, 3)));
            }
            out.close();
        } catch (Exception e) {
        }
    }

    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);

            out.println("Resualt of polinomail by Gornar");
            out.print("Polinomial: ");
            for (int i = 0; i < coefficients.length; i++) {
                out.print(coefficients[i] + "*X^" +
                        (coefficients.length - i - 1));

                if (i != coefficients.length - 1)
                    out.print(" + ");
            }
            out.println("");
            out.println("Interval from " + data.getFrom() + " to " +
                    data.getTo() + " bt step " + data.getStep());
            out.println("====================================================");
            for (int i = 0; i < data.getRowCount(); i++) {
                out.println("Val in the spot " + data.getValueAt(i, 0)
                        + " equael " + data.getValueAt(i, 1) +
                        " reverse value: " + data.getValueAt(i, 2) +
                        " diffrence: " + data.getValueAt(i, 3));
            }

            out.close();
        } catch (FileNotFoundException e) {
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Try to add some coeffitients in the args[]!");
            System.exit(-1);
        }
        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
            for (String arg : args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error in string converting '" +
                    args[i] + "' in Double");
            System.exit(-2);
        }

        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}