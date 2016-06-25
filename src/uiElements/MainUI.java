/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uiElements;
import ChatUtility.PeerConnection;
import ChatUtility.peerNode;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author 
 */
public class MainUI extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    
    private static DefaultListModel listModel1 = new DefaultListModel();
    private static DefaultListModel listModel2 = new DefaultListModel();
 
    private static PeerConnection connection = null;
    private static boolean connected = false;
    public static peerNode peerMe = new peerNode();

    private static String peerToPeerIP = null;
    private static String peerToPeerPort = "7777";
    private static String peerToPeerNick = null;
    private static String protocol = null;
    private static String scope = null;
    private static String mode = null;
    private static String subnetid = null;
    
    private static Thread t = null;
 
    public static DefaultListModel getListModel1(){
        return listModel1;
    }
    public static DefaultListModel getListModel2(){
        return listModel2;
    }
    public static JList<String> getjList1() {
        return jList1;
    }

    public static JList<String> getjList2() {
        return jList2;
    }
    /**
     * Creates new form NewJFrame
     */
    public MainUI() {
        super("               				"
            + "   Anonymous Chat Application");
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();     
        this.setLocation((int)screenSize.getWidth()/3, (int)screenSize.getHeight()/3);   
        
        MainUI.peerMe.ip = "1.2.3.4";
        MainUI.peerMe.nick = "Burhan";
        MainUI.peerMe.port = "7777";
        protocol = "";
        subnetid = "123456";
        scope = "Local";
        mode = "Gateway";
       
        connection = new PeerConnection();        
    }
    
    public void displayIncomingMessage(){
        
        if(t != null){
            this.t = new Thread(new Runnable(){
                public void run(){
                     HashMap<String,String> incomingMessage = connection.getMessage();
                     while(true){
                         if(incomingMessage != null){
                             jTextArea1.append(String.format("%s:  %s\n", incomingMessage.keySet(), incomingMessage.get(incomingMessage.keySet())));
                             incomingMessage = null;
                             incomingMessage = connection.getMessage();
                         }
                     }
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainUI.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<String>(listModel1);
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<String>(listModel2);
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();

        jMenuItem7.setText("Ignore");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem7);

        jMenuItem11.setText("Show Key");
        jPopupMenu1.add(jMenuItem11);

        jMenuItem12.setText("Private Message");
        jPopupMenu1.add(jMenuItem12);

        jMenuItem13.setText("Add Friend List");
        jPopupMenu1.add(jMenuItem13);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setText("Send");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jTextField1.setText("Type a Message");
        jTextField1.setToolTipText("Type a Message");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField1MousePressed(evt);
            }
        });

        jList1.setModel(listModel1);
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jList1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Global   List ", jPanel2);

        jList2.setModel(listModel2);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${componentPopupMenu.background}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jPopupMenu1, eLProperty, jList2);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${Item4}"));
        bindingGroup.addBinding(jListBinding);

        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jList2MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jList2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Local   List  ", jPanel1);

        jButton2.setText("Clear");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        jMenu1.setText("Connections");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Connect to Peer");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem17.setText("Connect to Local");
        jMenuItem17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem17MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem17);

        jMenuItem18.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem18.setText("Connect to Golobal");
        jMenuItem18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem18MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem18);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Disconnect");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem4MousePressed();
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu5.setText("Mode");

        jMenuItem16.setText("Client Mode");
        jMenuItem16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem16MousePressed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem15.setText("Gateway Mode");
        jMenuItem15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem15MousePressed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuBar1.add(jMenu5);

        jMenu2.setText("Security");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Encript");
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem5MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Decrypt");
        jMenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem6MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Generate Key");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText("About the Program");
        jMenuItem8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem8MousePressed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Check for Updates");
        jMenuItem9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem9MousePressed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Install Plugins");
        jMenuItem10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem10MousePressed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addGap(7, 7, 7))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        

        if((!jTextArea1.getText().contains("You have just disconnected from the chat room")) 
                && (!"Type a Message".equals(jTextField1.getText()) 
                && !"".equals(jTextField1.getText()))
                && connected == true
                && protocol.equalsIgnoreCase("Messaging")){
            
            String message = jTextField1.getText();
            jTextArea1.append(MainUI.peerMe.nick + ":   "+ message + "\n");
            connection.sendMessage(protocol, scope, mode,subnetid, PeerConnection.isEncrypted(), message);
            jTextField1.setText("Type a Message");
                      
        }else {
            jTextArea1.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                    + "\t         No Chat Room Connected");
        }
        jTextField1.setText("Type a Message");
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        // TODO add your handling code here:
        jTextField1.setText("Type a Message");
        jTextArea1.setText("");
    }//GEN-LAST:event_jButton2MousePressed

    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
        // TODO add your handling code here:
        if(connected == true){
            jMenuItem4MousePressed();
        }    
        protocol = "";
        connection.setPeerToPeerFlag(false);
        connected = false;
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1MousePressed

    private void jMenuItem4MousePressed() {//GEN-FIRST:event_jMenuItem4MousePressed
        jTextArea1.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "\tYou have just disconnected from the chat room");
        jTextField1.setText("Type a Message");
        protocol = "/Quit";
        connection.sendMessage(protocol, scope, mode, subnetid, PeerConnection.isEncrypted(), null);
        protocol = "";
        connection.setPeerToPeerFlag(false);
        connected = false;
        connection.accepting = null;
        connection.getIncomingMessageList().clear();
    }//GEN-LAST:event_jMenuItem4MousePressed

    private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem3MousePressed
        // TODO add your handling code here:
        InputPanel inPanel = new InputPanel();
        JOptionPane.showConfirmDialog(null, inPanel, " Enter the IP / Port and Nick Name",
                                      JOptionPane.OK_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
        if(connected == true){
            jMenuItem4MousePressed();
        }       
        displayIncomingMessage();       
        peerToPeerIP = inPanel.getjTextField2().getText();
        peerToPeerPort = inPanel.getjTextField3().getText();
        peerToPeerNick = inPanel.getjTextField4().getText();
        connection.setPeerToPeerIP(peerToPeerIP);
        connection.setPeerToPeerPort(peerToPeerPort);
        connection.setPeerToPeerNick(peerToPeerNick);
        connection.setPeerToPeerFlag(true);
        protocol = "/Join";
        jTextField1.setText("Type a Message");
        jTextArea1.setText("");
        connection.sendMessage(protocol, scope, mode, subnetid, PeerConnection.isEncrypted(), null);
        protocol = "Messaging";
        connection.setPeerToPeerFlag(true);
        connected = true;
    }//GEN-LAST:event_jMenuItem3MousePressed

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
        // TODO add your handling code here:
       
       if(!connection.getEncryption().isKeyPresent()) {
           connection.getEncryption().generateKey();
       }
    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jMenuItem6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem6MousePressed
        // TODO add your handling code here:
        connection.setEncrypted("Decrypted");
    }//GEN-LAST:event_jMenuItem6MousePressed

    private void jMenuItem5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem5MousePressed
        // TODO add your handling code here:
        connection.setEncrypted("Encrypted");         
    }//GEN-LAST:event_jMenuItem5MousePressed

    private void jTextField1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MousePressed
        // TODO add your handling code here:
        if(jTextField1.getText().contains("Type a Message")){
            jTextField1.setText("");
            if(jTextArea1.getText().contains("You have just disconnected from the chat room")){
                jTextArea1.setText("");
            }
        }
    }//GEN-LAST:event_jTextField1MousePressed

    private void jMenuItem8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem8MousePressed
        // TODO add your handling code here:
        String about = "Anonymous Chat Application was designed for those who do not want to  \n"
                     + "share  their  identity info during any online conversation to communicate  \n"
                     + "secretly  and was introduced  on trial in a course project. So, any contri-\n"
                     + "bution and/or bug report will be considered and deeply appreciated.\n";        
        JOptionPane.showMessageDialog(getParent(),about,"           About"
                                       , JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem8MousePressed

    private void jMenuItem9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem9MousePressed
        // TODO add your handling code here:
        UpdateJFrame1 updateUI = new UpdateJFrame1();
    }//GEN-LAST:event_jMenuItem9MousePressed

    private void jList2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MousePressed
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt)){
            jPopupMenu1.show(jPanel1, evt.getX(), evt.getY());      
        }
    }//GEN-LAST:event_jList2MousePressed

    private void jList1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MousePressed
        // TODO add your handling code here:
        if(SwingUtilities.isRightMouseButton(evt)){
            jPopupMenu1.show(jPanel2, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jList1MousePressed

    private void jMenuItem10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem10MousePressed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(getParent(), "No Plugin Available");
    }//GEN-LAST:event_jMenuItem10MousePressed

    private void jMenuItem17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem17MousePressed
        
        if(connected == true){
           jMenuItem4MousePressed();
        }     
        displayIncomingMessage();
        protocol = "/Join";
        scope = "Local";
        jTextField1.setText("Type a Message");
        jTextArea1.setText("");
        if(connection.accepting == null) {
            connection.accept();
        }
        connection.sendMessage(protocol, scope, mode, subnetid, PeerConnection.isEncrypted(), null);
        protocol = "Messaging";
        connection.setPeerToPeerFlag(false);
        connected = true;
    }//GEN-LAST:event_jMenuItem17MousePressed

    private void jMenuItem18MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem18MousePressed
        
        if(connected == true){        
            jMenuItem4MousePressed();
        }
        displayIncomingMessage();
        protocol = "/Join";
        scope = "Global";
        jTextField1.setText("Type a Message");
        jTextArea1.setText("");
        if(connection.accepting == null) {
            connection.accept();
        }
        connection.sendMessage(protocol, scope, mode, subnetid, PeerConnection.isEncrypted(), null);
        protocol = "Messaging";
        connection.setPeerToPeerFlag(false);
        connected = true;
    }//GEN-LAST:event_jMenuItem18MousePressed
    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt){
          
    }
    private void jMenuItem16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem16MousePressed
        MainUI.mode = "Client";
    }//GEN-LAST:event_jMenuItem16MousePressed

    private void jMenuItem15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem15MousePressed
        MainUI.mode = "Gateway";
    }//GEN-LAST:event_jMenuItem15MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        UIManager.installLookAndFeel("InfoNode", "net.infonode.gui.laf.InfoNodeLookAndFeel");
        UIManager.installLookAndFeel("Nimrod", "com.nilo.plaf.nimrod.NimRODLookAndFeel");
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo ( "TinyLaF", "de.muntjak.tinylookandfeel.TinyLookAndFeel" ));
        UIManager.installLookAndFeel("JGoodies Plastic 3D","com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
        
        //UIManager.installLookAndFeel();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private static javax.swing.JList<String> jList1;
    private static javax.swing.JList<String> jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
