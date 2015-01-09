package library_mgmt_system;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JButton;


public class BookCheckOut extends JFrame {

	private JPanel contentPane;
	private JTextField cardIDtextField;
	private JTextField bookidtextField;
	private JTextField branchidtextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookCheckOut frame = new BookCheckOut();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookCheckOut() {
		setTitle("Check_Out_Under_Process");
		Book_Search_Screen bksearch = new Book_Search_Screen();
		String book_id = bksearch.focusedbookid;
		int branch_id = bksearch.focusedbranchid;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseEnterThe = new JLabel("Please enter the borrower card no. :");
		lblPleaseEnterThe.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblPleaseEnterThe.setBounds(30, 21, 390, 20);
		contentPane.add(lblPleaseEnterThe);
		
		cardIDtextField = new JTextField();
		cardIDtextField.setBounds(118, 52, 116, 20);
		contentPane.add(cardIDtextField);
		cardIDtextField.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnOk.setBounds(180, 178, 89, 23);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnCancel.setBounds(301, 178, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblCardNumber.setBounds(30, 55, 72, 14);
		contentPane.add(lblCardNumber);
		
		JLabel lblBookid = new JLabel("Book_ID");
		lblBookid.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblBookid.setBounds(30, 103, 46, 14);
		contentPane.add(lblBookid);
		
		bookidtextField = new JTextField();
		bookidtextField.setBounds(118, 100, 116, 20);
		contentPane.add(bookidtextField);
		bookidtextField.setColumns(10);
		bookidtextField.setText(book_id);
		
		JLabel lblBranch = new JLabel("Branch_ID");
		lblBranch.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblBranch.setBounds(340, 103, 57, 14);
		contentPane.add(lblBranch);
		
		branchidtextField = new JTextField();
		branchidtextField.setColumns(10);
		branchidtextField.setBounds(421, 100, 116, 20);
		contentPane.add(branchidtextField);
		branchidtextField.setText(Integer.toString(branch_id));
		
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Queries db = new Queries();
				
				if(cardIDtextField.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Please enter card number");
				}else if(cardIDtextField.getText().isEmpty()==false && bookidtextField.getText().isEmpty()==false && branchidtextField.getText().isEmpty()==false){
					String card_no = cardIDtextField.getText();
					try {
						if(db.checkfine(bookidtextField.getText(), branchidtextField.getText(), card_no) == 1){
							JOptionPane.showMessageDialog(null,"User has unpaid Fine, Book check out not allowed");
						}else if(db.checkfine(bookidtextField.getText(), branchidtextField.getText(), card_no) == -1){
							JOptionPane.showMessageDialog(null,"Invalid card number");
						}
						
						else if(db.no_of_booksissued(card_no)>=3){
							JOptionPane.showMessageDialog(null,"Maximum check out reached");
						}else{						
							db.checkout_book(bookidtextField.getText(),branchidtextField.getText(), card_no);
							JOptionPane.showMessageDialog(null, "Succesfully Checked Out");
							setVisible(false);
							
						}
					} catch (HeadlessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		
		
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				setVisible(false);
			}
		});
	}
}
