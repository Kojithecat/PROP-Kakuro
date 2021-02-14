/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaPresentacion;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Raúl
 */
public class PantallaCreacionKakuros extends javax.swing.JFrame {
    private ControladorPresentacion CP = ControladorPresentacion.getInstance();
    /**
     * Creates new form PantallaCreacionKakuros
     */
    public PantallaCreacionKakuros() {
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

        BotonImportar = new javax.swing.JButton();
        BotonGenerar = new javax.swing.JButton();
        BotonVolver = new javax.swing.JButton();
        LabelResultadoCreacion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BotonImportar.setText("Importar kakuro desde un fichero");
        BotonImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonImportarActionPerformed(evt);
            }
        });
        BotonGenerar.setText("Generar kakuro");
        BotonGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonGenerarActionPerformed(evt);
            }
        });
        BotonVolver.setText("Volver atrás");
        BotonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonVolverActionPerformed(evt);
            }
        });
        LabelResultadoCreacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(BotonGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(BotonImportar, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                    .addComponent(BotonVolver, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(LabelResultadoCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(BotonImportar, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(LabelResultadoCreacion, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(BotonGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(BotonVolver)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /** 
     * @param evt
     */
    private void BotonImportarActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser();
        int returnValue = jfc.showOpenDialog(null);
        // int returnValue = jfc.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedPath = jfc.getSelectedFile().getAbsolutePath();
            System.out.println(selectedPath);
            try{
                int id = CP.crearKakuroPath(selectedPath);
                //Mandar el file a CD y mostrar si es correcto
                //Mostrar si el kakuro se ha creado o no
                if(id >= 0){
                LabelResultadoCreacion.setText("Se ha añadido el kakuro " + id);
                }
                else if(id == -1){
                LabelResultadoCreacion.setText("Fichero no guardado");    
                }
                else if(id == -2){
                    LabelResultadoCreacion.setText("Error, kakuro no válido"); 
                }
            }
            catch(IndexOutOfBoundsException e){
                LabelResultadoCreacion.setText("Error de formato");
            }
            
        }
       
        //vistaArchivo.setVisible(true);
        //dispose();

    }  
    
    /** 
     * @param evt
     */
    private void  BotonGenerarActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO Toda la ventana de generar
        PantallaGeneracionKakuro vistaGeneracion = new PantallaGeneracionKakuro();
        vistaGeneracion.setVisible(true);
        dispose();
    }  
    
    /** 
     * @param evt
     */
    private void BotonVolverActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
        PantallaGestion vistaGestion = new PantallaGestion();
        vistaGestion.setVisible(true);
        dispose();

    }  


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
            java.util.logging.Logger.getLogger(PantallaCreacionKakuros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaCreacionKakuros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaCreacionKakuros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaCreacionKakuros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaCreacionKakuros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonGenerar;
    private javax.swing.JButton BotonImportar;
    private javax.swing.JButton BotonVolver;
    private javax.swing.JLabel LabelResultadoCreacion;
    // End of variables declaration//GEN-END:variables
}