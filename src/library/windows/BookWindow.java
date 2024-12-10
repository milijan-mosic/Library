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
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import library.Library;
import library.models.Book;

public class BookWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField titleTextField;
    private JTextField authorTextField;
    private JTextField categoryTextField;
    private JDatePickerImpl datePicker;
    private JButton closeButton;
    private JButton confirmButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BookWindow frame = new BookWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BookWindow(Book book) {
        this();

        titleTextField.setText(book.getTitle());
        authorTextField.setText(book.getAuthor());
        categoryTextField.setText(book.getCategory());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, book.getReleaseDate());
        datePicker.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getModel().setSelected(true);
    }
    
    public BookWindow() {
        setBounds(100, 100, 450, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(12, 12, 64, 16);
        contentPane.add(titleLabel);

        titleTextField = new JTextField();
        titleTextField.setBounds(12, 40, 256, 21);
        contentPane.add(titleTextField);
        titleTextField.setColumns(10);
        
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setBounds(12, 73, 64, 16);
        contentPane.add(authorLabel);
        
        authorTextField = new JTextField();
        authorTextField.setColumns(10);
        authorTextField.setBounds(12, 101, 164, 21);
        contentPane.add(authorTextField);
        
        JLabel phoneNumberLabel = new JLabel("Category");
        phoneNumberLabel.setBounds(12, 134, 64, 16);
        contentPane.add(phoneNumberLabel);
        
        categoryTextField = new JTextField();
        categoryTextField.setColumns(10);
        categoryTextField.setBounds(12, 162, 164, 21);
        contentPane.add(categoryTextField);

        JLabel releaseDateLabel = new JLabel("Release date");
        releaseDateLabel.setBounds(12, 195, 81, 16);
        contentPane.add(releaseDateLabel);

        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(12, 223, 164, 27);
        contentPane.add(datePicker);
        
        closeButton = new JButton("Close");
        closeButton.setBounds(12, 382, 106, 27);
        closeButton.addActionListener(e -> dispose());
        contentPane.add(closeButton);
        
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(330, 382, 106, 27);
        confirmButton.addActionListener(e -> {
            if (Library.bookForUpdating == null) {
                insertBookToDatabase();
            } else {
                saveBookChanges();
            }
        });
        contentPane.add(confirmButton);
    }

    private void insertBookToDatabase() {
        String title = titleTextField.getText();
        String author = authorTextField.getText();
        String category = categoryTextField.getText();

        Date selectedDate = (Date) datePicker.getModel().getValue();
        int releaseDate = 0;
        
        if (selectedDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            releaseDate = calendar.get(Calendar.YEAR);
        }

        if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
            System.out.println("All fields must be filled");
        } else {
            Book.insertBook(title, author, category, "1", releaseDate);
            Library.LoadBooksIntoList();
            System.out.println("Book inserted successfully");
            dispose();
        }
    }

    private void saveBookChanges() {
        String updatedTitle = titleTextField.getText();
        String updatedAuthor = authorTextField.getText();
        String updatedCategory = categoryTextField.getText();
    
        Date selectedDate = (Date) datePicker.getModel().getValue();
        int releaseDate = 0;
    
        if (selectedDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            releaseDate = calendar.get(Calendar.YEAR);
        }

        Book originalBook = Library.bookForUpdating;
    
        if (updatedTitle.isEmpty() || updatedAuthor.isEmpty() || updatedCategory.isEmpty()) {
            System.out.println("All fields must be filled");
        } else {
            Book.updateBook(Integer.parseInt(originalBook.getId()), updatedTitle, updatedAuthor, updatedCategory, originalBook.getOwnerId(), releaseDate);
            Library.bookForUpdating = null;
            Library.LoadBooksIntoList();
            System.out.println("Book updated successfully");
            dispose();
        }
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
