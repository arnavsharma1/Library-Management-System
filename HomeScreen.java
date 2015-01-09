package library_mgmt_system;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class HomeScreen extends JFrame {

	private JPanel contentPane;
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeScreen frame = new HomeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HomeScreen() {
		setTitle("Library Management System");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 763, 294);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Book Search and Check out");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				
				new Book_Search_Screen().setVisible(true);
				setVisible(false);
			}
		});
		
		btnNewButton.setBounds(10, 11, 220, 46);
		contentPane.add(btnNewButton);
		
		JButton btnClickToStart = new JButton("Add Borrower");
		btnClickToStart.setBounds(10, 68, 220, 46);
		contentPane.add(btnClickToStart);
		
		JButton btnCheckin = new JButton("Check_In");
		btnCheckin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new BookCheckIn().setVisible(true);
				setVisible(false);
			}
		});
		btnCheckin.setBounds(10, 125, 220, 46);
		contentPane.add(btnCheckin);
		
		JButton btnRefreshFine = new JButton("Fines ");
		btnRefreshFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Fine_Summary_Screen().setVisible(true);
				setVisible(false);
				
			}
		});
		btnRefreshFine.setBounds(10, 182, 220, 46);
		contentPane.add(btnRefreshFine);
		
		JLabel lbltoSearchBooks = new JLabel(":To Search books, check availability and check-out a book");
		lbltoSearchBooks.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbltoSearchBooks.setBounds(258, 25, 415, 14);
		contentPane.add(lbltoSearchBooks);
		
		JLabel lblToAdd = new JLabel(": To add a new borrower in the record ");
		lblToAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblToAdd.setBounds(258, 82, 398, 14);
		contentPane.add(lblToAdd);
		
		JLabel lblNewLabel = new JLabel(": To check-in a borrowed book ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(258, 139, 398, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblToDisplay = new JLabel(": To display the fines summary and to display the fines due till today");
		lblToDisplay.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblToDisplay.setBounds(258, 192, 479, 23);
		contentPane.add(lblToDisplay);
		btnClickToStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				
				new Add_Borrower_Screen().setVisible(true);
				setVisible(false);
			}

		});

	}
}
