/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author ikattey
 */
public class MainForm extends JFrame{

    private void init() {
         JPanel panel = new JPanel(new MigLayout());

    panel.add(new JLabel("First name"));
    panel.add(new JTextField(20),  "wrap");
    panel.add(new JLabel("Last name"),       "gap unrelated");
    panel.add(new JTextField(20),   "wrap");
    panel.add(new JLabel("Address"));
    panel.add(new JTextField(40),    "span, grow");

       add(panel);
       pack();
       setVisible(true);
    }

    public static void main(String[] args)
    {
        MainForm form = new MainForm();
        form.init();
    }
}
