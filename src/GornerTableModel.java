import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }
    public int getColumnCount() {
        return 4;
    }
    public int getRowCount() {
        return Double.valueOf(Math.ceil((to-from)/step)).intValue()+1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        Double result = 0.0;
        switch (col) {
            case 0:
                return x;
            case 1: {
                for (int i = coefficients.length - 1; i >= 0; i--) {
                    result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
                }
                return result;
            }
            case 2: {
                for (int i = 0; i < coefficients.length; i++) {
                    result += coefficients[i] * Math.pow(x, i);
                }
                return result;
            }
            case 3: {
                double valForward = 0.f;
                for (int i = coefficients.length - 1; i >= 0; i--) {
                    valForward += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
                }

                double valReverse = 0.f;
                for (int i = 0; i < coefficients.length; i++) {
                    valReverse += coefficients[i] * Math.pow(x, i);
                }
                return valForward - valReverse;
            }
        }


        return result;
    }
    public String getColumnName(int col) {
        switch (col) {
            case 0: return "Value of X";
            case 1: return "Forward value";
            case 2: return "Reverse value";
            case 3: return "Difference (forward - reverse)";
        }
        return "";
    }
    public Class<?> getColumnClass(int col) {
        return Double.class;
    }
}