package CustomerGui;

import Database.DBHandler;
import Objects.InvItem;
import Objects.InvLog;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author LENOVO
 */
public class Cart extends javax.swing.JPanel {
    
    Store store;
    float bill  = 0f;
    DBHandler db = new DBHandler();
    
    ArrayList<ItemReciept> cartItems = new ArrayList<>();

    public Cart(Store store) {
        initComponents();
        
        this.store = store;
        EventQueue.invokeLater(()-> initDesign());
    }

    private void initDesign(){
        loadCart();
        jPanel1.setMaximumSize(new Dimension(566, Integer.MAX_VALUE));
        jScrollPane1.setHorizontalScrollBarPolicy(jScrollPane1.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(jScrollPane1.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        jLabel3.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // design for the button
                jLabel3.setForeground(new Color(224,82,83));
                jLabel3.setFont(new Font("montserrat",1, 18));
                
            }
            public void mouseEntered(MouseEvent e) {
                jLabel3.setForeground(new Color(224,82,83));
            }
            public void mouseExited(MouseEvent e) {
                jLabel3.setForeground(new Color(80,80,80));
                jLabel3.setFont(new Font("montserrat",0, 18));
            }
        });
        
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkOut();
                jPanel1.removeAll();
                cartItems.clear();
                bill = 0f;
                
                jLabel2.setText(String.valueOf(bill));

                jPanel1.revalidate();
                jPanel1.repaint();
            }
        });
    }
    
    public void loadCart(){
        
        jPanel1.removeAll();
        cartItems.clear();
        bill = 0f;

        for (InvItem invItem : store.cartItems) {
            ItemReciept item = new ItemReciept(invItem, this);
            jPanel1.add(item);
            cartItems.add(item);
            bill += invItem.getPrice();
        }
        
        store.cartItems.clear();
        jLabel2.setText(String.valueOf(bill));
        
        jPanel1.revalidate();
        jPanel1.repaint();


    }
    
    public void checkOut(){
        for (ItemReciept cartItem : cartItems) {
            
        }
        JOptionPane.showMessageDialog(null, "Thank you for your purchase", "checkout", JOptionPane.INFORMATION_MESSAGE);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRoundPanel1 = new Objects.JRoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(243, 243, 243));
        setMaximumSize(new java.awt.Dimension(200000, 200000));
        setMinimumSize(new java.awt.Dimension(400, 300));
        setPreferredSize(new java.awt.Dimension(700, 700));
        setRequestFocusEnabled(false);

        jRoundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jRoundPanel1.setCurve(50);

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(80, 80, 80));
        jLabel1.setText("Checkout");

        jScrollPane1.setViewportView(jPanel1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(null);
        jPanel1.setMinimumSize(new java.awt.Dimension(566, 464));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jPanel1);

        jButton1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/red1..png"))); // NOI18N
        jButton1.setText("Checkout");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setPreferredSize(new java.awt.Dimension(170, 50));
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/red2..png"))); // NOI18N
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/red3..png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(80, 80, 80));
        jLabel2.setText("Price");

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(80, 80, 80));
        jLabel3.setText("Clear");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jRoundPanel1Layout = new javax.swing.GroupLayout(jRoundPanel1);
        jRoundPanel1.setLayout(jRoundPanel1Layout);
        jRoundPanel1Layout.setHorizontalGroup(
            jRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jRoundPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 234, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jRoundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );
        jRoundPanel1Layout.setVerticalGroup(
            jRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jRoundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private Objects.JRoundPanel jRoundPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
