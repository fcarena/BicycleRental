/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controlers.BicycleRental.Bicycle;
import Controlers.BicycleRental.BicycleManager;
import Controlers.BicycleRental.ServiceFailureException;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francisco Carena
 */
public class BicycleDeletePopUp extends javax.swing.JDialog {

    private static final BicycleManager bicycleManager = MainWindow.getBicycleManager();
    private static BicycleTableModel bicycleTableModel;
    private static final Logger log = LoggerFactory.getLogger(BicycleEditPopUp.class);
    private final int selectedRow;
    private final Bicycle bicycle;
    
    private class BicycleDeleteSwingWorker extends SwingWorker<Integer,Void>{
        private final Bicycle bicycle;
        
        public BicycleDeleteSwingWorker(Bicycle b){
            this.bicycle=b;
        }
        
        @Override
        protected Integer doInBackground() throws Exception{
            log.debug("Deleting bicycle in background");
            bicycleManager.deleteBicycle(bicycle.getId());
            return 0;
        }
        
        @Override
        protected void done() {
            log.debug("BicycleDeletePopUp:done");
            try {
                get();
                bicycleTableModel.reload();
            }
            catch (ExecutionException exex) {
                if(exex.getCause().getClass().equals(ServiceFailureException.class)){
                    JOptionPane.showMessageDialog(rootPane,MainWindow.getMessage("texts", "ErrorDeleteBicycle"));
                }                
            }
            catch(InterruptedException iex){
                log.error("Done method deleting bicycle interrupted");
                throw new RuntimeException("Done method deleting bicycle interrupted",iex);
            }
        }   
    }
     private void deleteBicycle() {
        log.debug("Deleting bicycle");
        BicycleDeleteSwingWorker bdsw = new BicycleDeleteSwingWorker(bicycle);
        bdsw.execute();
    }
    
    /**
     * Creates new form BicycleDeletePopUp
     */
    public BicycleDeletePopUp(java.awt.Frame parent, boolean modal) {
        super(parent,modal);
        bicycleTableModel = ((MainWindow) getParent()).getBicycleTableModel();
        selectedRow = ((MainWindow) getParent()).getBicycleTable().getSelectedRow();
        bicycle = bicycleManager.getBicycleByID((Long)bicycleTableModel.getValueAt(selectedRow, 0));
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        descriptionLabel = new javax.swing.JLabel();
        OKButton = new javax.swing.JButton();
        NOButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        descriptionLabel.setText(MainWindow.getMessage("texts", "BDP_desc") +" " + this.bicycle.getId()+" " + MainWindow.getMessage("texts", "BDP_desc2") +" "+ this.bicycle.getPrice());

        OKButton.setText(MainWindow.getMessage("texts", "Popup_OK"));
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        NOButton.setText(MainWindow.getMessage("texts", "Popup_NO"));
        NOButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NOButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(OKButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NOButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(descriptionLabel)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NOButton)
                    .addComponent(OKButton))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        deleteBicycle();
        bicycleTableModel.reload();
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_OKButtonActionPerformed

    private void NOButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NOButtonActionPerformed
        this.setVisible(false);
        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_NOButtonActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BicycleDeletePopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BicycleDeletePopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BicycleDeletePopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BicycleDeletePopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                BicycleDeletePopUp dialog = new BicycleDeletePopUp(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NOButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JLabel descriptionLabel;
    // End of variables declaration//GEN-END:variables
}
