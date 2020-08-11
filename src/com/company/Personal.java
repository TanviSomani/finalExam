package com.company;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class Personal extends LoanP implements Generate{   private double monthly_payment = 0.0;
    private double monthlyRate = 0.0;
    private int termInMonths = 0;

    public Personal(){

    }

    public Personal(int cno, String cname, double loan_amt, int yrs, String ltype) {
        super(cno, cname, loan_amt, yrs, ltype);
    }

    public double compute_amortization(double loan_amt, int yrs) {

        monthlyRate = (0.06 / 12);
        termInMonths = (yrs * 12);

        monthly_payment = (loan_amt * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, -termInMonths));

        return monthly_payment;
    }

    @Override
    public DefaultTableModel generateTable(double loan_amt, int yrs) throws SQLException {
        DecimalFormat df = new DecimalFormat("#.##");

        double balance = loan_amt;

        String[][] newTable2 = new String[termInMonths+1][5];

        String[] cols = {"Payment", "Principal", "Interest", "Monthly Payment", "Balance"};

        for(double rows = 0.0; rows <= termInMonths; rows++) {

            if(rows == 0.0) {

                newTable2[(int)rows][0] = String.valueOf(0.0);
                newTable2[(int)rows][1] = String.valueOf(0.0);
                newTable2[(int)rows][2] = String.valueOf(0.0);
                newTable2[(int)rows][3] = String.valueOf(0.0);
                newTable2[(int)rows][4] = String.valueOf(loan_amt);
            }

            else {
                //payments
                newTable2[(int)rows][0] = String.valueOf(rows);

                //Interest
                double interest = (balance * monthlyRate);
                newTable2[(int)rows][2]= String.valueOf(String.format("%.2f",interest));

                //principal
                double principal = (monthly_payment-interest);
                newTable2[(int)rows][1]= String.valueOf(String.format("%.2f",principal));

                //monthly payment
                newTable2[(int)rows][3]= String.valueOf(String.format("%.2f",monthly_payment));

                //Balance
                balance = balance - principal;
                newTable2[(int)rows][4]= String.valueOf(String.format("%.2f",Math.abs(balance)));
            }

        }
        return new DefaultTableModel(newTable2, cols);
    }
}
