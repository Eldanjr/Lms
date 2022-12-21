/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.basicapp.training.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author bas200134
 */
@WebServlet("/login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uemail = req.getParameter("username");
        String upwd = req.getParameter("password");
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://bassure.in:3306/danjr", "danjr", "el007");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM lusers WHERE uemail=? AND upwd=? ");
            pstmt.setString(1, uemail);
            pstmt.setString(2, upwd);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                session.setAttribute("name", rs.getString("uname"));
                dispatcher = req.getRequestDispatcher("index.jsp");
            } else {
                req.setAttribute("status", "error");
                dispatcher = req.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(req, resp);

        } catch (Exception e) {
        }
    }

}
