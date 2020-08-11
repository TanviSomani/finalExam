package com.company;

public class LoanP {

    //declaring the variables
    private int clientno = 0;
    private String clientname = "";
    private double loanamount = 0.0;
    private int years = 0;
    private String loantype = "";

    public LoanP(){

    }

    //constructor
    public LoanP(int cno, String cname, double loan_amt, int yrs, String ltype) {
        this.clientno = cno;
        this.clientname = cname;
        this.loanamount = loan_amt;
        this.years = yrs;
        this.loantype = ltype;
    }
}
