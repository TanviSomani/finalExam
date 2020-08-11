package com.company;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public interface Generate {
    //interface
    DefaultTableModel generateTable(double loan_amt, int yrs) throws SQLException;
}
