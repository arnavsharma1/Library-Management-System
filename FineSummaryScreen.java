package library_mgmt_system;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;



import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Fine_Summary_Screen extends JFrame {

	private JPanel contentPane;
	private JTable finetable;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fine_Summary_Screen frame = new Fine_Summary_Screen();
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
	public Fine_Summary_Screen() {
		setTitle("Fines_Summary_Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 606);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnRefreshFine = new JButton("Refresh Fine Table");
		btnRefreshFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Queries db= new Queries();
				ResultSet rs4=null;
				try {Queries db2 = new Queries();
					rs4= db2.searchfine();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				try{
					String cq= "TRUNCATE TABLE fine;";
					Queries db0 = new Queries();
					db0.clearfinefirst(cq);	
					
				while(rs4.next()){
				int loan_id= Integer.parseInt(rs4.getString("loan_id"));//Integer.parseInt
				String card_no= (rs4.getString("card_no"));
				String due_date= rs4.getString("due_date");
				String date_in= rs4.getString("date_in");
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date due_date1;
				Date due_date2;Date date_in1;
				try {
					due_date1 = simpleDateFormat.parse(due_date);
					if(loan_id != 0 || card_no != null){
						
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date todaydate = new Date();	
				
				Long diff = (todaydate.getTime() - due_date1.getTime());
				Long days=diff / (1000 * 60 * 60 * 24);
				Double fines= (days)*0.25;
				int paid=0;
				if(date_in != null ){
				
					due_date2 = simpleDateFormat.parse(due_date);
					date_in1 = simpleDateFormat.parse(date_in);
					Long diff1 = (date_in1.getTime() - due_date2.getTime());
					if(diff1>0){Long days1=diff / (1000 * 60 * 60 * 24);
					Double fine_amt= (days1)*0.25;
					String iq= "Insert into fine values ('"+loan_id+"','"+fine_amt+"','"+1+"');";
					Queries db7 = new Queries();
					db7.populatefine(iq);}
					else{String iq= "Insert into fine values ('"+loan_id+"','"+0+"','"+1+"');";
					Queries db10 = new Queries();
					db10.populatefine(iq);
					}}
				else{ 
					String iq= "Insert into fine values ('"+loan_id+"','"+fines+"','"+0+"');";
					Queries db1 = new Queries();
					db1.populatefine(iq);
						
					}
					}
				}
				
				 catch (ParseException e) {
					
					e.printStackTrace();
				}
				
				}
				ResultSet rs5=null;
				try {Queries db6 = new Queries();
					rs5= db6.totalfinedisplay();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}if(rs5!=null){
					finetable.setModel(DbUtils.resultSetToTableModel(rs5));
					
				}
				
				
				rs4.close();
				}	catch(SQLException e){e.printStackTrace();}	
				}
		});
		btnRefreshFine.setBounds(20, 28, 162, 23);
		contentPane.add(btnRefreshFine);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 80, 568, 153);
		contentPane.add(scrollPane);
		
		finetable = new JTable();
		scrollPane.setViewportView(finetable);
		finetable.setForeground(new Color(0, 0, 0));
		finetable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}));
		
		JButton btnDisplayTheFines = new JButton("Display the fines that are due");
		btnDisplayTheFines.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs11=null;
				try {Queries db11 = new Queries();
					rs11= db11.duedisplay();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}if(rs11!=null){
					table.setModel(DbUtils.resultSetToTableModel(rs11));
					
				}
				
			}
		});
		btnDisplayTheFines.setBounds(20, 283, 210, 23);
		contentPane.add(btnDisplayTheFines);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 317, 568, 153);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		scrollPane_1.setViewportView(table);
		table.setForeground(new Color(0, 0, 0));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}));
		
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HomeScreen().setVisible(true);
				setVisible(false);
			}
		});
		btnBack.setBounds(20, 533, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnDisplayTheFines_1 = new JButton("Display the fines that are already paid");
		btnDisplayTheFines_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ResultSet rs13=null;
				try {Queries db13 = new Queries();
					rs13= db13.paiddisplay();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}if(rs13!=null){
					table.setModel(DbUtils.resultSetToTableModel(rs13));
					
				}
			}
		});
		btnDisplayTheFines_1.setBounds(315, 283, 273, 23);
		contentPane.add(btnDisplayTheFines_1);
		
			}
			}
	
