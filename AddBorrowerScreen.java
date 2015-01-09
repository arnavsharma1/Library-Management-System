package library_mgmt_system;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Add_Borrower_Screen extends JFrame {

	private JPanel contentPane;
	private JTextField mLastname;
	private JTextField mFirstname;
	private JTextField mAddress;
	private JLabel lblAddress;
	private JTextField mPhone;
	private JLabel lblPhone;

	static int mCardno = 9042;
	private JButton btnBack;
	private JLabel lblEnterTheFollowing;
	private JLabel lblMandatoryFiled;

	
	public Add_Borrower_Screen() {
		setTitle("Add_Borrower_Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 429, 495);
		contentPane = new JPanel();

	
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("First Name* :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 90, 89, 14);
		contentPane.add(lblNewLabel);

		mFirstname = new JTextField();
		mFirstname.setBounds(134, 88, 140, 20);
		contentPane.add(mFirstname);
		mFirstname.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name* :");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLastName.setBounds(10, 161, 89, 14);
		contentPane.add(lblLastName);

		mLastname = new JTextField();
		mLastname.setBounds(134, 159, 140, 20);
		contentPane.add(mLastname);
		mLastname.setColumns(10);

		JButton btnClickMe = new JButton("Add Borrower");
		btnClickMe.setBounds(134, 331, 140, 45);
		btnClickMe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("I am here");
				Borrower mBorrower = null;
				if (mFirstname.getText() != null || mAddress.getText() != null
						|| mPhone.getText() != null
						|| mLastname.getText() != null) {
					mBorrower = new Borrower(mFirstname.getText(), mAddress
							.getText(), mPhone.getText(), mLastname.getText(),
							++mCardno);
				} else {
					System.out.println("Enter all values");
				}

				Queries db = new Queries();
				
				db.add_Borrower(mBorrower);

			}
		});
		contentPane.add(btnClickMe);

		lblPhone = new JLabel("Phone Number* :");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPhone.setBounds(10, 225, 114, 14);
		contentPane.add(lblPhone);

		mPhone = new JTextField();
		mPhone.setBounds(134, 223, 140, 20);
		contentPane.add(mPhone);
		mPhone.setColumns(10);

		lblAddress = new JLabel("Address* :");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAddress.setBounds(10, 285, 89, 14);
		contentPane.add(lblAddress);

		mAddress = new JTextField();
		mAddress.setBounds(134, 283, 140, 20);
		contentPane.add(mAddress);
		mAddress.setColumns(10);
		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HomeScreen().setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(10, 422, 89, 23);
		contentPane.add(btnBack);
		
		lblEnterTheFollowing = new JLabel("Enter the following details to add a new borrower :");
		lblEnterTheFollowing.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterTheFollowing.setBounds(10, 11, 414, 14);
		contentPane.add(lblEnterTheFollowing);
		
		lblMandatoryFiled = new JLabel("(* mandatory feild)");
		lblMandatoryFiled.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblMandatoryFiled.setBounds(10, 36, 167, 14);
		contentPane.add(lblMandatoryFiled);

		
	}

}
