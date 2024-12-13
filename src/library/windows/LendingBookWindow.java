package library.windows;

import javax.swing.JFrame;
import javax.swing.*;
import java.util.List;
import javax.swing.border.EmptyBorder;

import library.Library;
import library.models.Transaction;

public class LendingBookWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField bookField;
    private JComboBox<String> userDropdown;
    private JButton cancelButton;
    private JButton confirmButton;
    //
    private String bookTitle;

    public LendingBookWindow(String bookName, List<String> userNames) {
        setBounds(100, 100, 330, 280);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel bookLabel = new JLabel("Book");
        bookLabel.setBounds(12, 12, 80, 25);
        getContentPane().add(bookLabel);

        bookField = new JTextField();
        bookField.setBounds(12, 49, 180, 25);
        bookField.setEditable(false);
        getContentPane().add(bookField);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(12, 86, 80, 25);
        getContentPane().add(userLabel);

        userDropdown = new JComboBox<>();
        userDropdown.setBounds(12, 123, 180, 25);
        getContentPane().add(userDropdown);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(12, 209, 100, 30);
        cancelButton.addActionListener(e -> dispose());
        getContentPane().add(cancelButton);

        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(216, 209, 100, 30);
        confirmButton.addActionListener(e -> {
            String selectedUser = (String) userDropdown.getSelectedItem();

            Transaction.makeTransaction(bookTitle, selectedUser);
            
            Library.LoadTransactionsIntoList();
            dispose();
        });
        getContentPane().add(confirmButton);

        setLocationRelativeTo(null);

        //

        bookField.setText(bookName);
        bookTitle = bookName;

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(userNames.toArray(new String[0]));
        userDropdown.setModel(model);
    }
}
