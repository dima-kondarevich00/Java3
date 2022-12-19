
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private double BeginOfDioposone = 0.f;
    private double EndOfDioposone = 0.f;
    int clue = 0;
    boolean IsPrevFirstColumBlack = true;
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);

        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

    }

    public void setBeginOfDioposone(double beginOfDioposone) {
        BeginOfDioposone = beginOfDioposone;

    }

    public void setEndOfDioposone(double endOfDioposone) {
        EndOfDioposone = endOfDioposone;
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        String formattedDouble = formatter.format(value);
        label.setText(formattedDouble);


        if (needle!=null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.RED);
            clue++;
        } else {

            boolean c = (clue % 4) == 0;
            if(c){
                if(IsPrevFirstColumBlack){
                    label.setForeground(Color.BLACK);
                    panel.setBackground(Color.WHITE);
                    IsPrevFirstColumBlack = false;
                } else {
                    label.setForeground(Color.WHITE);
                    panel.setBackground(Color.BLACK);
                    IsPrevFirstColumBlack = true;
                }
                clue++;
                return panel;
            }

            boolean b = (clue % 2) == 0;
            if(b){
                if(IsPrevFirstColumBlack){
                    label.setForeground(Color.WHITE);
                    panel.setBackground(Color.BLACK);
                } else {
                    label.setForeground(Color.BLACK);
                    panel.setBackground(Color.WHITE);
                }

            } else {
                if(IsPrevFirstColumBlack){
                    label.setForeground(Color.BLACK);
                    panel.setBackground(Color.WHITE);
                } else {
                    label.setForeground(Color.WHITE);
                    panel.setBackground(Color.BLACK);
                }
            }

            double compear = Double.valueOf(label.getText());
            if(compear < EndOfDioposone && compear > BeginOfDioposone){
                panel.setBackground(Color.ORANGE);
            }

            clue++;
        }

        return panel;
    }
    public void setNeedle(String needle) {
        this.needle = needle;
    }
}