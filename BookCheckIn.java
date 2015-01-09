package library_mgmt_system;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class BookCheckIn extends JFrame {

	private JPanel contentPane;
	private JTextField mBookID;
	private JTextField mCardNo;
	private JTextField mBorrowerName;
	ArrayList<CheckInObj> mArrayList;
	JList list;

	private JScrollPane scrollPane;

	public Queries mQueries;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookCheckIn frame = new BookCheckIn();
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
	public BookCheckIn() {
		setTitle("Book_Check_In_Screen");
		mQueries = new Queries();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 704, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mBookID = new JTextField();
		mBookID.setBounds(286, 72, 107, 20);
		contentPane.add(mBookID);
		mBookID.setColumns(10);

		mCardNo = new JTextField();
		mCardNo.setBounds(76, 72, 107, 20);
		contentPane.add(mCardNo);
		mCardNo.setColumns(10);

		mBorrowerName = new JTextField();
		mBorrowerName.setBounds(507, 72, 86, 20);
		contentPane.add(mBorrowerName);
		mBorrowerName.setColumns(10);

		JLabel lblBookId = new JLabel("Book Id");
		lblBookId.setBounds(230, 75, 46, 14);
		contentPane.add(lblBookId);

		JLabel lblCardNo = new JLabel("Card No");
		lblCardNo.setBounds(20, 75, 46, 14);
		contentPane.add(lblCardNo);

		JLabel lblBorrowerName = new JLabel("Borrower Name");
		lblBorrowerName.setBounds(403, 75, 94, 14);
		contentPane.add(lblBorrowerName);

		JButton btnCheckIn = new JButton("Search");
		btnCheckIn.setBounds(507, 108, 89, 23);
		contentPane.add(btnCheckIn);

		// contentPane.add(list);

		JScrollPane mJScrollPane = new JScrollPane();
		mJScrollPane.setBounds(20, 153, 573, 241);

		contentPane.add(mJScrollPane);
		
				list = new JList();
				mJScrollPane.setViewportView(list);
				
						list.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								JList list = (JList) evt.getSource();
								if (evt.getClickCount() == 2) {
									int index = list.locationToIndex(evt.getPoint());
									int check = mQueries.checkBookValidity(mArrayList.get(index).mCardNo);
									if (check == 0) {
										mQueries.enterDateIn(Integer.parseInt(mArrayList
												.get(index).mLoanId));
				
										refresh(index);
				
										JOptionPane.showMessageDialog(null, "Checked In");
									} else {
										float mFine = mQueries.calculatefine(Integer
												.parseInt(mArrayList.get(index).mLoanId));
				
										int result = JOptionPane.showConfirmDialog(
												(Component) null, "Fine Amount is Pending : $"
														+ mFine, "alert",
												JOptionPane.OK_CANCEL_OPTION);
				
										if (result == JOptionPane.OK_OPTION) {
											mQueries.enterDateIn(Integer.parseInt(mArrayList
													.get(index).mLoanId));
											mQueries.enterPriceIn(Integer.parseInt(mArrayList
													.get(index).mLoanId));
											refresh(index);
											
											JOptionPane.showMessageDialog(null, "Checked In");
										} else {
				
										}
				
									}
				
								}
							}
						});

		JLabel lblLoanId = new JLabel("Loan ID");
		lblLoanId.setBounds(26, 128, 46, 14);
		contentPane.add(lblLoanId);

		JLabel lblBookId_1 = new JLabel("Book ID");
		lblBookId_1.setBounds(128, 128, 46, 14);
		contentPane.add(lblBookId_1);

		JLabel lblCardNumber = new JLabel("Card No.");
		lblCardNumber.setBounds(230, 128, 60, 14);
		contentPane.add(lblCardNumber);

		JLabel lblNewLabel = new JLabel("Due Date");
		lblNewLabel.setBounds(340, 128, 78, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				new HomeScreen().setVisible(true);
				setVisible(false);
			}
			
		});
		btnBack.setBounds(20, 404, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblEnterFollowingDetails = new JLabel("Enter following details to Check-In a Book :");
		lblEnterFollowingDetails.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterFollowingDetails.setBounds(20, 11, 573, 31);
		contentPane.add(lblEnterFollowingDetails);
		String[] mStrings = null;
		btnCheckIn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!mCardNo.getText().equals("")
						&& !mBookID.getText().equals("")
						&& !mBorrowerName.getText().equals("")) {

					mQueries.set_Check_In_Details(mBookID.getText(),
							Integer.parseInt(mCardNo.getText()),
							mBorrowerName.getText());

				} else if (!mCardNo.getText().equals("")
						&& !mBookID.getText().equals("")) {

					mQueries.set_Check_In_Details(mBookID.getText(),
							Integer.parseInt(mCardNo.getText()), "");

				} else if (!mCardNo.getText().equals("")
						&& !mBorrowerName.getText().equals("")) {
					mQueries.set_Check_In_Details("",
							Integer.parseInt(mCardNo.getText()),
							mBorrowerName.getText());
				} else if (!mBookID.getText().equals("")
						&& !mBorrowerName.getText().equals("")) {
					mQueries.set_Check_In_Details(mBookID.getText(), -1,
							mBorrowerName.getText());
				}

				else if (!mCardNo.getText().equals("")) {

					mQueries.set_Check_In_Details("",
							Integer.parseInt(mCardNo.getText()), "");

				} else if (!mBorrowerName.getText().equals("")) {
					mQueries.set_Check_In_Details("", -1, mBorrowerName.getText());
				} else if (!mBookID.getText().equals("")) {
					mQueries.set_Check_In_Details(mBookID.getText(), -1, "");
				}

				mArrayList = mQueries.CheckinDetails();

				String[] mStrings = new String[mArrayList.size()];

				for (int i = 0; i < mStrings.length; i++) {
					mStrings[i] = mArrayList.get(i).mLoanId
							+ "                        "
							+ mArrayList.get(i).mBookId
							+ "                     "
							+ mArrayList.get(i).mCardNo + "              "
							+ mArrayList.get(i).mDate;
				}

				list.setListData(mStrings);

			}

		});

	}

	public void refresh(int index) {
		mArrayList.remove(index);

		String[] mStrings = new String[mArrayList.size()];

		for (int i = 0; i < mStrings.length; i++) {
			mStrings[i] = mArrayList.get(i).mLoanId
					+ "                        "
					+ mArrayList.get(i).mBookId
					+ "                     "
					+ mArrayList.get(i).mCardNo
					+ "              "
					+ mArrayList.get(i).mDate;
		}
		list.setListData(mStrings);
	}
}
