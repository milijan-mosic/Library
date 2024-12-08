package library.windows;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class BookWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField emailTextField;
	private JTextField phoneTextField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserWindow frame = new UserWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public BookWindow() {
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(12, 12, 64, 16);
		contentPane.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setBounds(12, 40, 256, 21);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		//
		
		JLabel emailLabel = new JLabel("Author");
		emailLabel.setBounds(12, 73, 64, 16);
		contentPane.add(emailLabel);
		
		emailTextField = new JTextField();
		emailTextField.setColumns(10);
		emailTextField.setBounds(12, 101, 164, 21);
		contentPane.add(emailTextField);
		
		//
		
		JLabel phoneLabel = new JLabel("Category");
		phoneLabel.setBounds(12, 134, 64, 16);
		contentPane.add(phoneLabel);
		
		phoneTextField = new JTextField();
		phoneTextField.setColumns(10);
		phoneTextField.setBounds(12, 162, 164, 21);
		contentPane.add(phoneTextField);
		
		//

        JLabel releaseDateLabel = new JLabel("Release date");
        releaseDateLabel.setBounds(12, 190, 81, 16);
        contentPane.add(releaseDateLabel);

        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(12, 218, 164, 27);
        contentPane.add(datePicker);
		
		//
		
		JButton closeButton = new JButton("Close");
		closeButton.setBounds(12, 382, 106, 27);
		closeButton.addActionListener(e -> dispose());
		contentPane.add(closeButton);
		
		JButton confirmButton = new JButton("Insert");
		confirmButton.setBounds(330, 382, 106, 27);
		contentPane.add(confirmButton);
	}
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private static final long serialVersionUID = 1L;

    @Override
    public Object stringToValue(String text) {
        return text;
    }
    
    @Override
    public String valueToString(Object value) {
        if (value != null) {
            if (value instanceof Calendar) {
                Date date = ((Calendar) value).getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format(date);
            }
            
            if (value instanceof Date) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format((Date) value);
            }
        }
        return "";
    }
}
