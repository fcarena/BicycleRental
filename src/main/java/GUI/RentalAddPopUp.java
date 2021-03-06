/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controlers.BicycleRental.Bicycle;
import Controlers.BicycleRental.BicycleManager;
import Controlers.BicycleRental.Customer;
import Controlers.BicycleRental.CustomerManager;
import Controlers.BicycleRental.Rental;
import Controlers.BicycleRental.RentalManager;
import Controlers.BicycleRental.ServiceFailureException;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Francisco Carena
 */
public class RentalAddPopUp extends javax.swing.JDialog {
    
    private static final Logger log = LoggerFactory.getLogger(RentalAddPopUp.class);
    private Rental rental;
    private final RentalManager rm;
    private final RentalTableModel rtm;
    private final BicycleManager bm;
    private final CustomerManager cm;
    
    private Bicycle[] allBikes(){
        List<Bicycle> bikes = bm.findAllBicycles();
        return bikes.toArray(new Bicycle[bikes.size()]);
    }
    private Customer[] allCus(){
        List<Customer> cus = cm.findAllCustomers();
        return cus.toArray(new Customer[cus.size()]);
    }
    
    private class RAddSW extends SwingWorker <Integer,Void>{
        private final Rental rental;
        
        public RAddSW(Rental r){
            this.rental = r;
        }
        
        @Override
        protected Integer doInBackground() throws Exception {
            log.debug("RAddSW.doInBG");
            rm.createRental(rental);
            return 0;
        }
        
        @Override
        protected void done(){
            log.debug("RAddSW.done");
            try{
                get();
                rtm.reload();
            }catch(ExecutionException exex){
                if(exex.getCause().getClass().equals(IllegalArgumentException.class)){
                    if(exex.getCause().getMessage().equals("Rental already exists")){
                        log.debug("IAE:existing rental re-created");
                        JOptionPane.showMessageDialog(rootPane,MainWindow.getMessage("texts", "ErrorREx"));
                    }
                    else{
                        log.debug("IAE:fields@rental creation");
                        JOptionPane.showMessageDialog(rootPane,MainWindow.getMessage("texts", "Error_Fields"));
                    }
                }
                if(exex.getCause().getClass().equals(ServiceFailureException.class)){
                    log.debug("SFE:failed rental creation");
                    JOptionPane.showMessageDialog(rootPane, MainWindow.getMessage("texts", "ErrorCreateRental"));
                }
            }catch(InterruptedException iex){
                log.error("Done method creating rental interrupted");
                throw new RuntimeException(MainWindow.getMessage("texts", "ErrorCreatingInterruptedRen"),iex);
            }
        }       
    }

    /**
     * Creates new form RentalPopUp
     */
    public RentalAddPopUp(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        log.debug("RentalAddPopUp ctor");
        this.cm = MainWindow.getCustomerManager();
        this.bm = MainWindow.getBicycleManager();
        this.rm = MainWindow.getRentalManager();
        rtm = ((MainWindow)getParent()).getRentalTableModel();
        initComponents();
    }
    
    private void addRental(RentalManager rm){
        log.debug("Adding rental");
        Rental r = new Rental();
        try{
            r.setRentedFrom(dateFromChooser.getDate());
            r.setRentedTo(dateUntilChooser.getDate());
            r.setBicycle((Bicycle) bicycleSelector.getSelectedItem());
            r.setCustomer((Customer) customerSelector.getSelectedItem());
            RAddSW rasw = new RAddSW(r);
            rasw.execute();
        }catch(ServiceFailureException ex){
            log.error("Error creating rental");
            throw new ServiceFailureException(MainWindow.getMessage("texts", "ErrorCreateRental"),ex);
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

        rentedFromLabel = new javax.swing.JLabel();
        rentedToLabel = new javax.swing.JLabel();
        customerLabel = new javax.swing.JLabel();
        bicycleIDLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        customerSelector = new javax.swing.JComboBox();
        dateFromChooser = new com.toedter.calendar.JDateChooser();
        dateUntilChooser = new com.toedter.calendar.JDateChooser();
        bicycleSelector = new javax.swing.JComboBox();
        OKButton = new javax.swing.JButton();
        NOButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        rentedFromLabel.setText(MainWindow.getMessage("texts", "RFrom"));

        rentedToLabel.setText(MainWindow.getMessage("texts", "RUntil"));

        customerLabel.setText(MainWindow.getMessage("texts", "RCustomer"));

        bicycleIDLabel.setText(MainWindow.getMessage("texts", "RBicycle"));

        descriptionLabel.setText(MainWindow.getMessage("texts", "RAP_desc"));

        customerSelector.setModel(new javax.swing.DefaultComboBoxModel(allCus()));

        bicycleSelector.setModel(new javax.swing.DefaultComboBoxModel(allBikes()));

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
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(NOButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(OKButton)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bicycleSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(bicycleIDLabel)
                        .addGap(44, 44, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(customerSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customerLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rentedToLabel)
                                    .addComponent(rentedFromLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dateFromChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(dateUntilChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(descriptionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateFromChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rentedToLabel)
                            .addComponent(dateUntilChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(customerSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(customerLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bicycleSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bicycleIDLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(NOButton)
                            .addComponent(OKButton))
                        .addGap(21, 21, 21))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rentedFromLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        addRental(rm);
        rtm.reload();
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
            java.util.logging.Logger.getLogger(RentalAddPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RentalAddPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RentalAddPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RentalAddPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                RentalAddPopUp popup = new RentalAddPopUp(new javax.swing.JFrame(),true);
                popup.addWindowListener(new java.awt.event.WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent ev){
                    System.exit(0);
                }
                });
                popup.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NOButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JLabel bicycleIDLabel;
    private javax.swing.JComboBox bicycleSelector;
    private javax.swing.JLabel customerLabel;
    private javax.swing.JComboBox customerSelector;
    private com.toedter.calendar.JDateChooser dateFromChooser;
    private com.toedter.calendar.JDateChooser dateUntilChooser;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel rentedFromLabel;
    private javax.swing.JLabel rentedToLabel;
    // End of variables declaration//GEN-END:variables
}
