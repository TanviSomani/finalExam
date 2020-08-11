package com.company;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class formTest {

    @Test
    void connectMethod() throws SQLException, ClassNotFoundException {
        //testing the connection with databse
        assertTrue(form.connectMethod("root", ""));
    }

    @Test
    void updateTable() throws SQLException, ClassNotFoundException {
        //testing read part of CRUD
        form f = new form();
        assertTrue(f.updateTable());
    }
}