package jogovelha.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TabelaGenerica extends JTable {

  private static final long serialVersionUID = 1L;

  private final DefaultTableModel dados;

  public TabelaGenerica(String[] colunas) {
    this.dados = (DefaultTableModel) getModel();
    dados.setColumnIdentifiers(colunas);
    setFont(getFont().deriveFont(20f));
    setRowHeight(30);
    setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    setColumnSelectionAllowed(false);
    setRowSelectionAllowed(true);
    setAutoResizeMode(AUTO_RESIZE_OFF);
  }

  public void configuraCorColuna(int coluna, Color corTexto, Color corFundo, boolean centralizado) {
    final TableColumn tColumn = getColumnModel().getColumn(coluna);
    tColumn.setCellRenderer(new ColumnColorRenderer(corTexto, corFundo, centralizado));
  }

  public void atualizaDados(String[][] dados) {
    while (this.dados.getRowCount() > 0) {
      this.dados.removeRow(0);
    }
    // this.dados.setRowCount(0);
    for (String[] linha : dados) {
      this.dados.addRow(linha);
    }
    this.dados.fireTableDataChanged();
  }

  @Override
  public boolean isCellEditable(int nRow, int nCol) {
    return false;
  }

  @Override
  public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
    final Component component = super.prepareRenderer(renderer, row, column);
    int rendererWidth = component.getPreferredSize().width;
    final TableColumn tableColumn = getColumnModel().getColumn(column);
    tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width + 10, tableColumn.getPreferredWidth()));
    return component;
  }

  /**
   * Seleciona a linha que estiver com determinado texto na coluna informada
   */
  public void selecionaLinha(int coluna, Set<String> textosColuna) {
    boolean add = false;
    for (String textoColuna : textosColuna) {
      for (int linha = 0; linha < dados.getRowCount(); linha++) {
        if (dados.getValueAt(linha, coluna).equals(textoColuna)) {
          if (add) {
            addRowSelectionInterval(linha, linha);
          } else {
            setRowSelectionInterval(linha, linha);
            add = true;
          }
        }
      }
    }
  }

  public static class ColumnColorRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    private final Color corFundo;

    private final Color corTexto;

    public ColumnColorRenderer(Color corTexto, Color corFundo, boolean centralizado) {
      this.corFundo = corFundo;
      this.corTexto = corTexto;
      if (centralizado) {
        setHorizontalAlignment(JLabel.CENTER);
      }
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
        int column) {
      final Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      cell.setBackground(corFundo);
      cell.setForeground(corTexto);
      return cell;
    }
  }

}
