package library_mgmt_system;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Color;

import javax.swing.JScrollPane;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



@SuppressWarnings("serial")
public class Book_Search_Screen extends JFrame {

	private JPanel contentPane;
	private JTextField tFbookId;
	private JTextField tFauthor;
	private JTextField tFtitle;
	private JTable searchresulttable;
	private JScrollPane scrollPane;
	public static String focusedbookid=null;
    public static int focusedbranchid;
    private JButton btnBack;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Book_Search_Screen frame = new Book_Search_Screen();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setSize(600, 600);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Book_Search_Screen() {
		setTitle("Book_Search_Screen");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 609, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tFbookId = new JTextField();
		tFbookId.setBounds(68, 62, 132, 19);
		contentPane.add(tFbookId);
		tFbookId.setColumns(10);
		
		tFauthor = new JTextField();
		tFauthor.setColumns(10);
		tFauthor.setBounds(280, 62, 132, 19);
		contentPane.add(tFauthor);
		
		tFtitle = new JTextField();
		tFtitle.setColumns(10);
		tFtitle.setBounds(455, 62, 132, 19);
		contentPane.add(tFtitle);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblBookId.setBounds(11, 63, 47, 19);
		contentPane.add(lblBookId);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 13));
		lblTitle.setBounds(424, 63, 31, 19);
		contentPane.add(lblTitle);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblAuthor.setBounds(228, 63, 43, 19);
		contentPane.add(lblAuthor);
		
		JButton btnCheckOut = new JButton("Check Out");
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCheckOut.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnCheckOut.setBounds(455, 406, 116, 23);
		btnCheckOut.setEnabled(false);
		contentPane.add(btnCheckOut);
		btnCheckOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new BookCheckOut().setVisible(true);
			}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnSearch.setBounds(498, 92, 89, 23);
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			
				int linect = 0;
				Queries db = new Queries();
				String book_id = tFbookId.getText();
				String title = tFtitle.getText();
				String author = tFauthor.getText();				
				ResultSet rs01 = null;
				if(book_id.isEmpty()==true&&author.isEmpty()==true&&title.isEmpty()==true)
					JOptionPane.showMessageDialog(null, "You did not enter search value");
				else{
					try {
						rs01 = db.book_search_query(book_id, author, title);
					} catch (SQLException e) {
						
						e.printStackTrace();
					}	
					if(rs01!=null){
						searchresulttable.setModel(DbUtils.resultSetToTableModel(rs01));
						if(searchresulttable.getRowCount()==0)
							JOptionPane.showMessageDialog(null, "No books found :(");
					}
					else
						JOptionPane.showMessageDialog(null, "No Results found!!");					
				}	
			}
		});
		contentPane.add(btnSearch);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 126, 567, 253);
		contentPane.add(scrollPane);
		
		searchresulttable = new JTable();
		scrollPane.setViewportView(searchresulttable);
		searchresulttable.setForeground(new Color(0, 0, 0));

		
		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new HomeScreen().setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(20, 406, 89, 23);
		contentPane.add(btnBack);
		
		JLabel lblEnterBookId = new JLabel("Enter Book ID or Author or Title to Search a Book :");
		lblEnterBookId.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEnterBookId.setBounds(11, 11, 516, 25);
		contentPane.add(lblEnterBookId);
		
        
		searchresulttable.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = searchresulttable.getSelectedRow();
				int col = searchresulttable.getSelectedColumn();
				if(row != -1 && col!=-1 ){
					btnCheckOut.setEnabled(true);
					System.out.print("value at row:"+row+", column:"+col+", is:"+searchresulttable.getValueAt(row, col));
					focusedbookid = (String) searchresulttable.getValueAt(row, 0);					
					focusedbranchid = (int) searchresulttable.getValueAt(row, 2);
				}
			}
		});
		
		
		
		
		
		
		
	}
}
