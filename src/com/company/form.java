/*
 * Created by JFormDesigner on Tue Aug 11 12:28:22 PDT 2020
 */

package com.company;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.sql.*;
import java.util.Vector;

/**
 * @author Tanvi Dinesh Somani
 */
public class form extends JFrame {

    //declaring variables
    static PreparedStatement query;
    static Connection con;
    static ResultSet rs;
    static String[][] data = new String[100][5];
    private static String username = "root";
    private static String pass = "";
    static String no = "";

    public form() {

        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Tanvi Dinesh Somani
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        label5 = new JLabel();
        comboBox1 = new JComboBox();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        scrollPane2 = new JScrollPane();
        table2 = new JTable();
        addButton = new JButton();
        editButton = new JButton();
        deleteButton = new JButton();
        label6 = new JLabel();
        textField5 = new JTextField();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Enter the client number:");
        contentPane.add(label1, "cell 0 0");

        //---- textField1 ----
        textField1.setColumns(20);
        contentPane.add(textField1, "cell 2 0");

        //---- label2 ----
        label2.setText("Enter the client name:");
        contentPane.add(label2, "cell 0 1");

        //---- textField2 ----
        textField2.setColumns(20);
        contentPane.add(textField2, "cell 2 1");

        //---- label3 ----
        label3.setText("Enter the customer loan amount:");
        contentPane.add(label3, "cell 0 2");
        contentPane.add(textField3, "cell 2 2");

        //---- label4 ----
        label4.setText("Enter the number of years to pay:");
        contentPane.add(label4, "cell 0 3");
        contentPane.add(textField4, "cell 2 3");

        //---- label5 ----
        label5.setText("Enter the loan type:");
        contentPane.add(label5, "cell 0 4");
        contentPane.add(comboBox1, "cell 2 4");

        //======== scrollPane1 ========
        {
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        table1MouseClicked(e);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 0 5");

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(table2);
        }
        contentPane.add(scrollPane2, "cell 2 5");

        //---- addButton ----
        addButton.setText("Add");
        addButton.addActionListener(e -> addButtonActionPerformed(e));
        contentPane.add(addButton, "cell 0 6");

        //---- editButton ----
        editButton.setText("Edit");
        editButton.addActionListener(e -> {
            try {
                editButtonActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        contentPane.add(editButton, "cell 0 6");

        //---- deleteButton ----
        deleteButton.setText("Delete");
        deleteButton.addActionListener(e -> {
            try {
                deleteButtonActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        contentPane.add(deleteButton, "cell 0 6");

        //---- label6 ----
        label6.setText("Monthly Payment");
        contentPane.add(label6, "cell 2 6");
        contentPane.add(textField5, "cell 2 6");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Tanvi Dinesh Somani
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JTextField textField4;
    private JLabel label5;
    private JComboBox comboBox1;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JScrollPane scrollPane2;
    private JTable table2;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JLabel label6;
    private JTextField textField5;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    //connection method for the database
    public static boolean connectMethod(String u, String p) throws SQLException, ClassNotFoundException {
        try {
            //LOAD DRIVER
            Class.forName("com.mysql.cj.jdbc.Driver");

            //connection to database
            con = DriverManager.getConnection("jdbc:mysql://localhost/loan", u, p);

            return true;
        }
        //throwing error if the database doesnot connect
        catch(Exception e1){
            JOptionPane.showMessageDialog(null, e1);

            return false;
        }
    }

    //adding table one
    public void addTable() throws SQLException, ClassNotFoundException {
        String[] cols = {"Number", "Name", "Amount", "Years", "Type of Loan"};

        DefaultTableModel model = new DefaultTableModel(data, cols);
        table1.setModel(model);

        //giving to values for the comboBox
        comboBox1.addItem("Business");
        comboBox1.addItem("Personal");

        updateTable();
    }

    //showing the data in table 1
    public boolean updateTable() throws SQLException, ClassNotFoundException {
        try {
            int c;

            connectMethod(username, pass);

            query = con.prepareStatement("Select * from loantable");

            rs = query.executeQuery();

            ResultSetMetaData rmd = rs.getMetaData();
            c = rmd.getColumnCount();
            DefaultTableModel df = (DefaultTableModel) table1.getModel();
            df.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();

                for (int a = 1; a <= c; a++) {
                    v2.add(rs.getString("clientno"));
                    v2.add(rs.getString("clientname"));
                    v2.add(rs.getString("loanamount"));
                    v2.add(rs.getString("years"));
                    v2.add(rs.getString("loantype"));
                }
                df.addRow(v2);
            }
            return true;
        }
        //if data doesnot show up exception will throw
        catch (Exception ReadEx){
            System.out.println(ReadEx);
            JOptionPane.showMessageDialog(null,ReadEx);
            return false;
        }
    }

    //AddButton method
    private void addButtonActionPerformed(ActionEvent e) {
        //checking if the user has entered data in the textfield or not
        if (textField1.getText().isBlank() || textField2.getText().isBlank() || textField3.getText().isBlank() || textField4.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Field is empty");
        }
        else if (textField1.getText().isBlank() && textField2.getText().isBlank() && textField3.getText().isBlank() && textField4.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Both Fields are empty");
        }
        else {
            try {
                int cno = Integer.parseInt(textField1.getText());
                String cn = textField2.getText();
                double la = Double.parseDouble(textField3.getText());
                int ny = Integer.parseInt(textField4.getText());
                String lt = comboBox1.getSelectedItem().toString();

                LoanP lp = new LoanP(cno, cn, la, ny, lt);

                connectMethod(username, pass);

                if (e.getSource() == addButton) {
                    //Select query
                    query = con.prepareStatement("Select * from loantable where clientno = ?");

                    query.setString(1, String.valueOf(cno));

                    rs = query.executeQuery();

                    if (rs.isBeforeFirst()) {
                        JOptionPane.showMessageDialog(null, "Customer code already exists! ");

                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");

                        textField1.requestFocus();

                        return;
                    }
                }

                Integer.parseInt(String.valueOf(cno));
                //insert table query
                query = con.prepareStatement("INSERT into loantable values(?,?,?,?,?)");
                query.setString(1, String.valueOf(cno));
                query.setString(2, cn);
                query.setString(3, String.valueOf(la));
                query.setString(4, String.valueOf(ny));
                query.setString(5, lt);

                query.executeUpdate();

                //data added in the table message
                JOptionPane.showMessageDialog(null, "One record added.");

                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");

                textField1.requestFocus();

                updateTable();
            }
            catch (Exception e1) {
                e1.getMessage();
                JOptionPane.showMessageDialog(null, "invalid input");
                textField1.setText("");
            }
        }
    }

    //method so that user can edit through the textfield as well
    private void table1MouseClicked(MouseEvent e) throws SQLException {
        DefaultTableModel df = (DefaultTableModel)table1.getModel();

        int index1 = table1.getSelectedRow();

        textField1.setText(df.getValueAt(index1,0).toString());

        no = textField1.getText();
        textField2.setText(df.getValueAt(index1,1).toString());

        textField3.setText(df.getValueAt(index1,2).toString());

        textField4.setText(df.getValueAt(index1,3).toString());

        comboBox1.setSelectedItem(df.getValueAt(index1, 4).toString());

        //displaying the table 2 on the basis of the selection
        if(comboBox1.getSelectedIndex() == 0){
            Business b = new Business();
            double loanAmount = Double.parseDouble((String.valueOf( df.getValueAt(index1, 2))));
            int years = Integer.parseInt((String.valueOf(df.getValueAt(index1, 3))));

            b.compute_amortization(loanAmount,years);
            DefaultTableModel newModel = b.generateTable(loanAmount, years);
            table2.setModel(newModel);
        }
        else{
            Personal p = new Personal();
            double loanAmount = Double.parseDouble((String.valueOf( df.getValueAt(index1, 2))));
            int years = Integer.parseInt((String.valueOf(df.getValueAt(index1, 3))));

            p.compute_amortization(loanAmount,years);
            DefaultTableModel newModel = p.generateTable(loanAmount, years);
            table2.setModel(newModel);
        }
    }

    //edit method
    private void editButtonActionPerformed(ActionEvent e) throws SQLException, ClassNotFoundException{
        // TODO add your code here
        if (textField1.getText().isBlank() || textField2.getText().isBlank() || textField3.getText().isBlank() || textField4.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Field is empty");
        }
        else {
            connectMethod(username, pass);

            int cno = Integer.parseInt(textField1.getText());
            String cn = textField2.getText();
            double la = Double.parseDouble(textField3.getText());
            int ny = Integer.parseInt(textField4.getText());
            String lt = comboBox1.getSelectedItem().toString();
            no = textField1.getText();

            LoanP lp = new LoanP(cno, cn, la, ny, lt);

            if (rs.isBeforeFirst()) {
                //res.isBeforeFirst() is true if the cursor
                JOptionPane.showMessageDialog(null, "The client number you are trying to enter already exists ");

                textField1.setText("");
                textField2.setText("");
                textField3.setText("");
                textField4.setText("");
                textField1.requestFocus();

                return;
            }

            //update query
            query = con.prepareStatement("update loantable set clientno = ?, clientname = ?, loanamount = ?, years = ?, loantype = ? where clientno = ?");

            query.setString(1, String.valueOf(cno));
            query.setString(2, cn);
            query.setString(3, String.valueOf(la));
            query.setString(4, String.valueOf(ny));
            query.setString(5, lt);
            query.setString(6, no);

            query.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record updated");

            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");

            updateTable();
        }
    }

    //delete method
    private void deleteButtonActionPerformed(ActionEvent e) throws SQLException, ClassNotFoundException {
        connectMethod(username, pass);

        int cno = Integer.parseInt(textField1.getText());

        String no = textField1.getText();

        query = con.prepareStatement("Select * from loantable where clientno = ?");

        query.setString(1, String.valueOf(cno));
        rs = query.executeQuery();

        if (!rs.isBeforeFirst()) {
            JOptionPane.showMessageDialog(null, "No Data");
            return;
        }

        int result = JOptionPane.showConfirmDialog(null, "Do you really want to delete this record?", "Delete",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            //delete query
            query = con.prepareStatement("delete from loantable where clientno = ?");

            query.setString(1, String.valueOf(cno));
            query.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record Deleted");

            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");

            updateTable();
        }
    }

    //main method
    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        form fd = new form();

        fd.addTable();

        fd.setVisible(true);
    }
}
