package dev.mestizos.pos.ticketsnum;

import com.unicenta.basic.BasicException;
import com.unicenta.data.gui.ComboBoxValModel;
import com.unicenta.data.user.DirtyManager;
import com.unicenta.data.user.EditorRecord;
import com.unicenta.format.Formats;
import dev.mestizos.error.ErrorMessage;
import dev.mestizos.types.TransctionType;

import java.awt.Component;
import javax.swing.JPanel;

/**
 * @author jorgeluis
 */
public class TicketsNumEditor extends JPanel implements EditorRecord {

    private ComboBoxValModel comboModelTypeTransaction;
    private String peopleId; // Is equal to combobox at the filter screen

    public TicketsNumEditor(String peopleId, DirtyManager dirty) {
        initComponents();

        this.peopleId = peopleId;

        cbxCode.addActionListener(dirty);
        txtId.getDocument().addDocumentListener(dirty);
        txtSerie.getDocument().addDocumentListener(dirty);
        radPrimary.addActionListener(dirty);
        radAlternative.addActionListener(dirty);
        chkStatus.addActionListener(dirty);

        comboModelTypeTransaction = new ComboBoxValModel();
        comboModelTypeTransaction.add(new TransctionType("FV", "Factura Venta"));
        comboModelTypeTransaction.add(new TransctionType("BV", "Boleta o Nota Venta"));
        comboModelTypeTransaction.add(new TransctionType("EV", "Entrega Venta"));

        cbxCode.setModel(comboModelTypeTransaction);
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    @Override
    public void writeValueEOF() {
        comboModelTypeTransaction.setSelectedFirst();
        txtId.setText(null);
        txtSerie.setText(null);
        radPrimary.setSelected(false);
        radAlternative.setSelected(false);
        chkStatus.setText(null);

        cbxCode.setEnabled(false);
        txtId.setEnabled(false);
        txtSerie.setEnabled(false);
        radPrimary.setEnabled(false);
        radAlternative.setEnabled(false);
        chkStatus.setEnabled(false);
    }

    @Override
    public void writeValueInsert() {
        comboModelTypeTransaction.setSelectedFirst();
        txtId.setText(null);
        txtSerie.setText(null);
        radPrimary.setSelected(false);
        radAlternative.setSelected(false);
        chkStatus.setText(null);

        cbxCode.setEnabled(true);
        txtId.setEnabled(true);
        txtSerie.setEnabled(true);
        radPrimary.setEnabled(true);
        radAlternative.setEnabled(true);
        chkStatus.setEnabled(true);

        cbxCode.requestFocus();
    }

    @Override
    public void writeValueEdit(Object value) {
        Object[] ticketsNum = (Object[]) value;

        comboModelTypeTransaction.setSelectedKey(Formats.STRING.formatValue(ticketsNum[0]));
        txtSerie.setText(Formats.STRING.formatValue(ticketsNum[2]));
        txtId.setText(Formats.INT.formatValue(ticketsNum[3]));

        if (ticketsNum[4].equals("primary")) {
            radPrimary.setSelected(true);
        } else {
            radAlternative.setSelected(true);
        }

        if (ticketsNum[5].equals("Active")) {
            chkStatus.setSelected(true);
            chkStatus.setText("Activo");
        } else {
            chkStatus.setSelected(false);
            chkStatus.setText("Inactivo");
        }

        lblSerieId.setText(Formats.STRING.formatValue(ticketsNum[2]) + " - "
                + Formats.INT.formatValue(ticketsNum[3]).toString());

        cbxCode.setEnabled(false);
        txtId.setEnabled(true);
        txtSerie.setEnabled(true);
        radPrimary.setEnabled(true);
        radAlternative.setEnabled(true);
        chkStatus.setEnabled(true);
    }

    @Override
    public void writeValueDelete(Object value) {
        Object[] ticketsNum = (Object[]) value;

        comboModelTypeTransaction.setSelectedKey(Formats.STRING.formatValue(ticketsNum[0]));
        txtSerie.setText(Formats.STRING.formatValue(ticketsNum[2]));
        txtId.setText(Formats.INT.formatValue(ticketsNum[3]));

        if (ticketsNum[4].equals("primary")) {
            radPrimary.setSelected(true);
        } else {
            radAlternative.setSelected(true);
        }

        if (ticketsNum[5].equals("Active")) {
            chkStatus.setSelected(true);
            chkStatus.setText("Activo");
        } else {
            chkStatus.setSelected(false);
            chkStatus.setText("Inactivo");
        }

        lblSerieId.setText(Formats.STRING.formatValue(ticketsNum[2]) + " - "
                + Formats.INT.formatValue(ticketsNum[3]));

        cbxCode.setEnabled(false);
        txtId.setEnabled(false);
        txtSerie.setEnabled(false);
        radPrimary.setEnabled(false);
        radAlternative.setEnabled(false);
        chkStatus.setEnabled(false);
    }

    @Override
    public void refresh() {
    }

    @Override
    public Component getComponent() {
        return this;
    }

    @Override
    public Object createValue() throws BasicException {
        ErrorMessage validate = validateData();

        if (validate.getIsError()) {
            throw new BasicException(validate.getMessage());
        }

        Object[] ticketsNum = new Object[6];

        ticketsNum[0] = comboModelTypeTransaction.getSelectedKey();
        ticketsNum[1] = peopleId;
        ticketsNum[2] = txtSerie.getText();
        ticketsNum[3] = Formats.INT.parseValue(txtId.getText());

        if (radPrimary.isSelected()) {
            ticketsNum[4] = "primary";
        } else {
            ticketsNum[4] = "alternative";
        }

        if (chkStatus.isSelected()) {
            ticketsNum[5] = "Active";
        } else {
            ticketsNum[5] = "Inactive";
        }

        return ticketsNum;
    }

    private ErrorMessage validateData() {
        try {
            int number = Integer.parseInt(txtId.getText());

            if (number < 0) {
                return new ErrorMessage("El secuencial debe ser un número mayor a cero");
            }

            return new ErrorMessage();
        } catch (NumberFormatException ex) {
            return new ErrorMessage("El secuencial debe ser un número");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groRadio = new javax.swing.ButtonGroup();
        txtId = new javax.swing.JTextField();
        txtSerie = new javax.swing.JTextField();
        chkStatus = new javax.swing.JCheckBox();
        lblCode = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblSerie = new javax.swing.JLabel();
        lblPriority = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblSerieId = new javax.swing.JLabel();
        radPrimary = new javax.swing.JRadioButton();
        radAlternative = new javax.swing.JRadioButton();
        cbxCode = new javax.swing.JComboBox<>();

        setPreferredSize(new java.awt.Dimension(640, 420));

        txtId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtSerie.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        chkStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        chkStatus.setText("Activo / Inactivo");
        chkStatus.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkStatusStateChanged(evt);
            }
        });

        lblCode.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblCode.setText("Código");

        lblId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblId.setText("Secuencial");

        lblSerie.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblSerie.setText("Serie");

        lblPriority.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblPriority.setText("Prioridad");

        lblStatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblStatus.setText("Estado");

        lblSerieId.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblSerieId.setText("...");

        groRadio.add(radPrimary);
        radPrimary.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        radPrimary.setText("Primaria");

        groRadio.add(radAlternative);
        radAlternative.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        radAlternative.setSelected(true);
        radAlternative.setText("Alternativa");

        cbxCode.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCode)
                    .addComponent(lblPriority)
                    .addComponent(lblStatus)
                    .addComponent(lblSerie)
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chkStatus)
                    .addComponent(lblSerieId, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(radPrimary)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radAlternative))
                    .addComponent(txtSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(cbxCode, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(231, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(lblSerieId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCode)
                    .addComponent(cbxCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSerie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPriority)
                    .addComponent(radPrimary)
                    .addComponent(radAlternative))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkStatus)
                    .addComponent(lblStatus))
                .addContainerGap(187, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkStatusStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkStatusStateChanged
        if (chkStatus.isSelected()) {
            chkStatus.setText("Activo");
        } else {
            chkStatus.setText("Inactivo");
        }
    }//GEN-LAST:event_chkStatusStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbxCode;
    private javax.swing.JCheckBox chkStatus;
    private javax.swing.ButtonGroup groRadio;
    private javax.swing.JLabel lblCode;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblPriority;
    private javax.swing.JLabel lblSerie;
    private javax.swing.JLabel lblSerieId;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JRadioButton radAlternative;
    private javax.swing.JRadioButton radPrimary;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtSerie;
    // End of variables declaration//GEN-END:variables
}
