package capaPresentacion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
/**
 *
 * @author scipio
 */
public class PantallaJugando extends JFrame {

    private ControladorPresentacion CP = ControladorPresentacion.getInstance();
    
    private JPanel GridPanel = null;
    JPanel gridPanel = new JPanel(new GridBagLayout());
    private int tips = 0;
    private JFormattedTextField[][] arraySudoku;
    //private ArrayList<ArrayList<String>> tablero = new ArrayList<ArrayList<String>>();
    //private ArrayList<String> modificables = new ArrayList<String>();
    private ArrayList<ArrayList<String>> partida = new ArrayList<ArrayList<String>>();
    private JLabel AyudasRestantes = new javax.swing.JLabel();
    
   /* public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaJugando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaJugando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaJugando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaJugando.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PantallaJugando(args).setVisible(true);
            }
        });
    }   */ 
    
    /** Creates new form NewJFrame */
    public PantallaJugando(ArrayList<ArrayList<String>> p, boolean newGame) {
        setTitle("not(Sudoku) Player 1.0");
        partida = p;
        ArrayList<String> modificables = partida.get(12);
        //System.out.println(modificables); 
        ArrayList<ArrayList<String>> tablero = cargarTablero();
        //System.out.println(tablero);
        //arraySudoku = tablero.toArray();
        setAyuda(partida.get(8),newGame);
        //System.out.println("Valor: " + partida.get(8));
        int n = Integer.parseInt(partida.get(11).get(0));
        int m = Integer.parseInt(partida.get(11).get(1));
        arraySudoku = new JFormattedTextField[n][m]; // standard sudoku size
        JPanel BottomPanel = createBottomPanel();
        //BottomPanel.setMinimumSize(new Dimension(410,50));
        BottomPanel.setPreferredSize(new Dimension(410,50));
        getContentPane().add(BottomPanel, BorderLayout.SOUTH);
        gridPanel.add(createGridPanel(n,m,tablero, modificables), getWholeCellConstraints());
        //gridPanel.setMinimumSize(new Dimension(200*m,200*n));
        //gridPanel.setPreferredSize(new Dimension(400*m,400*n));
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        JPanel TopPanel = createTopPanel();
        //TopPanel.setMinimumSize(new Dimension(410,50));
        TopPanel.setPreferredSize(new Dimension(410,50));
        getContentPane().add(TopPanel, BorderLayout.NORTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    private ArrayList<ArrayList<String>> cargarTablero(){
        ArrayList<ArrayList<String>> tablero = new ArrayList<ArrayList<String>>(); 
        for(int i = 13; i< partida.size(); i++ ){
            tablero.add(partida.get(i));
        }
        return tablero;
    }

    
    /** 
     * @param nivelAyuda
     * @param b
     */
    private void setAyuda(ArrayList<String> nivelAyuda, boolean b){
        //System.out.println(nivelAyuda);
        //System.out.println(nivelAyuda.get(0));
        if(b){
            if(nivelAyuda.get(0).equals("Nada")) tips = 0;
            else if (nivelAyuda.get(0).equals("Poco")) tips = 1;
            else if(nivelAyuda.get(0).equals("Medio")) tips = 2;
            else if(nivelAyuda.get(0).equals("Mucho")) tips = 3;
        }
        else tips = Integer.parseInt(partida.get(7).get(0));
        AyudasRestantes.setText("Tips: " + tips);
    }

    
    /** 
     * @return GridBagConstraints
     */
    private GridBagConstraints getWholeCellConstraints() {
        GridBagConstraints wholePanelCnstr = new GridBagConstraints();
        wholePanelCnstr.fill = java.awt.GridBagConstraints.BOTH;
        wholePanelCnstr.weightx = 1.0;
        wholePanelCnstr.weighty = 1.0;
        return wholePanelCnstr;
    }
    
    
    /** 
     * @return JPanel
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        //JButton Salir = new javax.swing.JButton();
        JButton Guardar = new javax.swing.JButton();

        /*Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });*/
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

       // Salir.setText("Salir");
       // Salir.setPreferredSize(new Dimension(100,40));
        Guardar.setText("Guardar y salir");
        Guardar.setPreferredSize(new Dimension(200,40));
        //bottomPanel.add(new JSeparator(JSeparator.VERTICAL));
        //bottomPanel.add(Salir);
        //bottomPanel.add(new JSeparator(JSeparator.VERTICAL));
        bottomPanel.add(Guardar);
        bottomPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        return bottomPanel;
    }
    
    
    /** 
     * @return JPanel
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        JButton Rendirse = new javax.swing.JButton();
        JButton Comprobar = new javax.swing.JButton();
        JButton Ayuda = new javax.swing.JButton();

        Rendirse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RendirseActionPerformed(evt);
            }
        });
        Comprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComprobarActionPerformed(evt);
            }
        });
        Ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AyudaActionPerformed(evt);
            }
        });

        AyudasRestantes.setPreferredSize(new Dimension(100,40));
        AyudasRestantes.setHorizontalAlignment(SwingConstants.CENTER);
        AyudasRestantes.setBorder(new BevelBorder(BevelBorder.LOWERED));
        Ayuda.setText("Ayuda");
        Ayuda.setPreferredSize(new Dimension(100,40));
        Rendirse.setText("Rendirse");
        Rendirse.setPreferredSize(new Dimension(100,40));
        Comprobar.setText("Comprobar");
        Comprobar.setPreferredSize(new Dimension(100,40));
        //controller.bindLeftLabel(leftLabel);
        //controller.bindRightLabel(rightLabel);
        topPanel.add(AyudasRestantes);
        topPanel.add(new JSeparator(JSeparator.VERTICAL));
        topPanel.add(Ayuda);
        topPanel.add(Rendirse);
        topPanel.add(new JSeparator(JSeparator.VERTICAL));
        topPanel.add(Comprobar);
        topPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
        return topPanel;
    }
    
    
    /** 
     * @param n
     * @param m
     * @param tablero
     * @param modificables
     * @return JPanel
     */
    private JPanel createGridPanel(int n, int m, ArrayList<ArrayList<String>> tablero,ArrayList<String> modificables ) {
        GridPanel = createnxmPanel(n, m);
        //GridPanel.setMinimumSize(new Dimension(70*m, 70*n));
        fillPanelWithEditable(n, m,tablero,modificables);
        GridPanel.setBorder(new EmptyBorder(new Insets(6,6,6,6)));
        return GridPanel;
    }

    
    /** 
     * @param majorRow
     * @param majorColumn
     * @param tablero
     * @param modificables
     */
    private void fillPanelWithEditable(int majorRow, int majorColumn,ArrayList<ArrayList<String>> tablero,ArrayList<String> modificables) {
        for (int i = 0; i < majorRow; i++) {
            for (int j = 0; j < majorColumn; j++) {
                String pos = tablero.get(i).get(j);
                String mod = modificables.get(i*majorColumn+j);
               // System.out.println("Modificable "+ mod + "Contenido "+ pos);
                final JFormattedTextField editableField;
                if (mod.equals("0")) {
                    if(pos.contains("F") || pos.contains("C") ||pos.contains("*") ) editableField = createNonEditableFieldBlack(pos);
                    else editableField = createNonEditableField(pos);
                } else {
                    editableField = createEditableField(pos);
                }
                SudokuCell(i, j, editableField);
                GridPanel.add(editableField);
            }
        }
    }

    
    /** 
     * @param row
     * @param column
     * @param field
     */
    void SudokuCell(final int row, final int column, JFormattedTextField field) {
        field.addPropertyChangeListener("value", new PropertyChangeListener() {

            // if user edits field than You could do something about it here
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue() != null) {
                    String newValue = (String) evt.getNewValue();
                    userEditedValueAt(row, column, Integer.valueOf(newValue));
                }
            }
        });
        arraySudoku[row][column] = field;
    }
    
    
    /** 
     * @param row
     * @param column
     * @param value
     */
    void userEditedValueAt(int row, int column, int value) {
       // System.out.println("Value changed at row:" + row + ", column:" + column + " to " + value);
    }
    
    
    /** 
     * @param n
     * @param m
     * @return JPanel
     */
    private JPanel createnxmPanel(int n, int m) {
        final GridLayout gridLayout = new GridLayout(n, m, 1, 1);
        gridLayout.setHgap(4);
        gridLayout.setVgap(4);
        JPanel panel = new JPanel(gridLayout);
        return panel;
    }

    
    /** 
     * @param pos
     * @return JFormattedTextField
     */
    private JFormattedTextField createNonEditableFieldBlack(String pos) {
        JFormattedTextField field = new JFormattedTextField();
        field.setMinimumSize(new Dimension(70, 70));
        field.setPreferredSize(new Dimension(70, 70));
        field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        field.setForeground(Color.white);
        field.setBackground(Color.black); // otherwise non-editable gets gray
        String putStr = "";
        //if (!pos.equals("*")) putStr = ""; 
        if(pos.contains("C") && pos.contains("F")){
            putStr = pos.substring(pos.indexOf("C")+1, pos.indexOf("F")) +"\\" + pos.substring(pos.indexOf("F")+1, pos.length()) ;

        }
        else if  (pos.contains("C")){
            putStr = pos.substring(pos.indexOf("C")+1, pos.length())+ "\\0";
        }  
        else if (pos.contains("F")){
            putStr = "0\\" + pos.substring(pos.indexOf("F")+1, pos.length());
        }
            field.setText(putStr);
        
        field.setEditable(false);
        return field;
    }

    
    /** 
     * @param pos
     * @return JFormattedTextField
     */
    private JFormattedTextField createNonEditableField(String pos) {
        JFormattedTextField field = new JFormattedTextField();
        field.setMinimumSize(new Dimension(70, 70));
        field.setPreferredSize(new Dimension(70, 70));
        field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        field.setForeground(Color.black);
        float hue = (float)0.27;
        float sat = (float)0.8;
        float bri = (float) 0.7;
        field.setBackground(Color.getHSBColor(hue, sat, bri)); // otherwise non-editable gets gray
        field.setText(pos);
        field.setEditable(false);
        return field;
    }
    

    
    /** 
     * @param pos
     * @return JFormattedTextField
     */
    private JFormattedTextField createEditableField(String pos) {
        JFormattedTextField field = new JFormattedTextField();
        // accept only one digit and nothing else
        try {
            MaskFormatter F = new MaskFormatter("#");
            F.setValidCharacters("123456789");
            field.setFormatterFactory(new DefaultFormatterFactory(F));
            if(!pos.equals("?")) field.setText(pos);
        } catch (java.text.ParseException ex) {
        }
        field.setMinimumSize(new Dimension(70, 70));
        field.setPreferredSize(new Dimension(70, 70));
        field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        field.setText(" ");
        return field;
    }

    
    /** 
     * @return ArrayList<ArrayList<String>>
     */
    private ArrayList<ArrayList<String>> stringPharseTablero() {
        ArrayList<ArrayList<String>> x = new ArrayList<ArrayList<String>> ();
        for (int i = 0 ; i < arraySudoku.length ; i++) x.add(new ArrayList<String>());
        
        for (int i = 0 ; i < arraySudoku.length ; i++) {
            for (int j = 0 ; j < arraySudoku[i].length ; j++) {
                //System.out.println("pos: " + i + " " + j);
                x.get(i).add(arraySudoku[i][j].getText());
            }
        }
      // System.out.println(partida);
        for(int i = 13; i < partida.size(); i++){
            //System.out.println("fila: "+ partida.get(i).size() );
            for(int j = 0; j < partida.get(i).size(); j++){
              //  System.out.println("Valor: " + x.get(i-13).get(j));
                if(!x.get(i-13).get(j).equals(" ") && !x.get(i-13).get(j).equals("") && !x.get(i-13).get(j).contains("\\") )partida.get(i).set(j,x.get(i-13).get(j)); 
            }
            
        }
       // System.out.println(partida);
        partida.get(7).set(0,AyudasRestantes.getText().split(" ")[1]);
        return partida;
    }


    
    /** 
     * @param evt
     */
    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {
       // System.out.println("Guardar");
        int i = CP.guardarPartida(this.stringPharseTablero(),false,false);
        //System.out.println(i);
        JOptionPane.showMessageDialog(null, "La partida se ha guardado correctamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        PantallaSelecionPartida ventanaSelecionPartida = new PantallaSelecionPartida();
        ventanaSelecionPartida.setVisible(true);
        dispose();

    }
    
    /** 
     * @param evt
     */
    private void AyudaActionPerformed(java.awt.event.ActionEvent evt) {
        if(tips > 0){
            tips = tips-1;
            AyudasRestantes.setText("Tips: " + tips);
            stringPharseTablero();
            ArrayList<String> help = CP.pedirAyudas(partida);
           // System.out.println(help);
            if(help.contains("-1")) return;
            //en la posicion poner el numero
            int i = Integer.parseInt(help.get(0));
            int j = Integer.parseInt(help.get(1));
            
           // System.out.println("Test");
           // System.out.println(partida.get(12));
           
            ArrayList<String> mod = partida.get(12);
            int n = Integer.parseInt(partida.get(11).get(0));
            int m = Integer.parseInt(partida.get(11).get(1));
            mod.set(i*m+j,"0");
           //System.out.println(partida.get(13+i));
            partida.get(13+i).set(j, help.get(2));
          // System.out.println(partida.get(13+i));
            partida.set(12, mod);
           // System.out.println(partida.get(12));
            ArrayList<String> modificables = partida.get(12);
            ArrayList<ArrayList<String>> tablero = cargarTablero();
            //System.out.println(modificables);
            //System.out.println(tablero);
            GridPanel = null;
            gridPanel.removeAll();
            gridPanel.add(createGridPanel(n,m,tablero, modificables), getWholeCellConstraints(),0);
            getContentPane().add(gridPanel, BorderLayout.CENTER);
            
        } 

        //System.out.println("Ayuda");
    }
    
    /** 
     * @param evt
     */
    private void RendirseActionPerformed(java.awt.event.ActionEvent evt) {
       // System.out.println("Rendirse");
        int result = JOptionPane.showConfirmDialog(null,"¿Estas seguro de que te quieres rendir?", "Swing Tester",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){
            int i = CP.guardarPartida(this.stringPharseTablero(),true,true);
            //  System.out.println(i);
            PantallaSelecionPartida ventanaSelecionPartida = new PantallaSelecionPartida();
            ventanaSelecionPartida.setVisible(true);
            dispose();
        }
    }
    
    /** 
     * @param evt
     */
    private void ComprobarActionPerformed(java.awt.event.ActionEvent evt) {
        //System.out.println("Comprobar");
        stringPharseTablero();
        boolean bien =CP.comprobarPartida(partida);
        if(bien){
            CP.guardarPartida(partida, true, false);
            String punt = CP.puntuacionPartida(Integer.parseInt(partida.get(0).get(0)));
            PantallaPartidaAcabada ventanaPartidaAcabada = new PantallaPartidaAcabada(punt);
            ventanaPartidaAcabada.setVisible(true);
            dispose();
            //JOptionPane.showMessageDialog(null, "El kakuro está bien solucionado", "Partida Correcta", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "El kakuro no está bien solucionado", "Partida Incorrecta", JOptionPane.INFORMATION_MESSAGE);
        }

    }
}