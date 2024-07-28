package bank.management.system;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame {

    MiniStatement(String pinnumber) {
        setTitle("Mini Statement");
        setLayout(null);

        JLabel bank = new JLabel("Indian Bank");
        bank.setBounds(150, 20, 200, 20);
        bank.setFont(new Font("Arial", Font.BOLD, 18));
        add(bank);

        JLabel card = new JLabel();
        card.setBounds(20, 60, 300, 20);
        card.setFont(new Font("Arial", Font.PLAIN, 16));
        add(card);

        JLabel balance = new JLabel();
        balance.setBounds(20, 550, 300, 20);
        balance.setFont(new Font("Arial", Font.PLAIN, 16));
        add(balance);

        JLabel mini = new JLabel();
        mini.setVerticalAlignment(JLabel.TOP);
        
        JScrollPane scrollPane = new JScrollPane(mini);
        scrollPane.setBounds(20, 140, 360, 400);
        add(scrollPane);

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from login where pin = '" + pinnumber + "'");
            if (rs.next()) {
                card.setText("Card Number: " + rs.getString("cardnumber").substring(0, 4) + "XXXXXXXX" + rs.getString("cardnumber").substring(12));
            }
            int bal = 0;
            rs = conn.s.executeQuery("select * from bank where pin = '" + pinnumber + "'");
            StringBuilder statement = new StringBuilder("<html>");
            while (rs.next()) {
                statement.append(rs.getString("date")).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                         .append(rs.getString("type")).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                         .append(rs.getString("amount")).append("<br><br>");

                if (rs.getString("type").equals("Deposit")) {
                    bal += Integer.parseInt(rs.getString("amount"));
                } else {
                    bal -= Integer.parseInt(rs.getString("amount"));
                }
            }
            statement.append("</html>");
            mini.setText(statement.toString());
            balance.setText("Your Current account balance is Rs " + bal);
        } catch (Exception e) {
            System.out.println(e);
        }

        setLocation(20, 20);
        setSize(400, 650);
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MiniStatement("");  // Sample pin number for testing
    }
}
