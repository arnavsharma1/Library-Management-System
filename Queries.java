package library_mgmt_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Queries {
	
	static Connection conn = null;
    
	Borrower mBorrower;
	private Statement updateableStmt;
	Statement stmt;
	String mBookId, mBorrowername;
	int mCardNo;
	
	
	public void finalize() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Queries() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/",
					"root", "password");
			stmt = conn.createStatement();
			stmt.execute("use library_system;");
			updateableStmt = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
		} catch (SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}
	}


	
	public void setmBorroweer(Borrower mBorrower) {
		this.mBorrower = mBorrower;
	}
    public int checkfine(String book_id, String branch_id, String card_no) throws SQLException{
    	ResultSet qrs1=null;
	
		String query = "select count(*) from borrower where card_no = '"+card_no+"';";
		qrs1 = stmt.executeQuery(query);
		qrs1.next();
		if(qrs1.getInt(1) == 0)
			return -1;
		query = "select count(*) from book_loans where card_no = '"+card_no+"' and book_id='"+book_id+"'"
					+"and branch_id='"+branch_id+"' and due_date<curdate() and date_in is NULL;";
		qrs1 = stmt.executeQuery(query);
		qrs1.next();
		if(qrs1.getInt(1) == 0)
			return 0;
		else 
			return 1;
		
    }
    
    public int no_of_booksissued(String card_no) throws SQLException{
    	ResultSet qrs2=null;
		
		String query = "select count(*) from book_loans where card_no = '"+card_no+"' and date_in is NULL;";
		qrs2  = stmt.executeQuery(query);
		int c;
		
		qrs2.next();
			 c= qrs2.getInt(1);
			
		return c;    	
    }
    
    public void checkout_book(String book_id, String branch_id, String card_no) throws SQLException{
    	ResultSet qrs3=null;
	
		String count = "select coalesce(MAX(loan_id),0) from book_loans";
		qrs3 = stmt.executeQuery(count);
		qrs3.next();
		int max_loan_id = qrs3.getInt(1);
		
		String query = "INSERT INTO book_loans (`loan_id`, `book_id`, `branch_id`, `card_no`, `date_out`, `due_date`, `date_in`) VALUES ('"+(max_loan_id+1)+"', '"+book_id+"', '"+branch_id+"','"+card_no+"', curdate(), date_add(curdate(),INTERVAL 14 Day), NULL);";
		int rSet = stmt.executeUpdate(query);
    }
	

	public void set_Check_In_Details(String bookid, int cardno, String borrowername) {
		this.mBookId = bookid;
		this.mCardNo = cardno;
		this.mBorrowername = borrowername;
	}

	public ArrayList<CheckInObj> CheckinDetails() {
		ArrayList<CheckInObj> mArrayList = new ArrayList<CheckInObj>();
	

		String mCheckinQuery = "select B.card_no,Loan_id,Fname,B.book_id,B.due_date from book_loans as B join borrower as L on L.card_no = B.card_no where B.Date_in is Null AND  (B.book_id='"
				+ mBookId
				+ "' or L.Card_no="
				+ mCardNo
				+ " or L.fname = '"
				+ mBorrowername + "')";

		try {
			ResultSet qrs4 = stmt.executeQuery(mCheckinQuery);
			while (qrs4.next()) {

				CheckInObj mCheckInObj = new CheckInObj(
						qrs4.getString("loan_id"),
						qrs4.getString("book_id"),
						qrs4.getInt("card_no"),
						qrs4.getDate("due_date"));

				mArrayList.add(mCheckInObj);

			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return mArrayList;
	}

	public int checkBookValidity(int cardno) {
		String mCheckValid = "select count(*) from book_loans as B join Fines as F on B.loan_id=F.loan_id where B.card_no ="
				+ cardno;
		try {
			ResultSet qrs5 = stmt.executeQuery(mCheckValid);
			qrs5.next();
			return qrs5.getInt(1);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	public void enterDateIn(int loanid) {
		String mInsertDate = " update book_loans set date_in=CURRENT_TIMESTAMP () where loan_id="
				+ loanid;
		try {
			stmt.executeUpdate(mInsertDate);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	

	public void enterPriceIn(int loanid) {
		String mInsertPrice = " update fines set paid=1  where loan_id="
				+ loanid;
		try {
			stmt.executeUpdate(mInsertPrice);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public float calculatefine(int loanid) {
		String mFine = " Select F.loan_id, (DATEDIFF( now(),B.Due_date))*.25 as Fine_amt from Book_loans as B join Fines as F on B.Loan_id = F.Loan_id where F.Loan_id ="
				+ loanid;
		
		try {
			ResultSet qrs6 = stmt.executeQuery(mFine);
			qrs6.next();
			return qrs6.getFloat(2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	public ResultSet book_search_query(String book_id, String author, String title) throws SQLException{
		ResultSet qrs7=null;
	
		if(book_id.isEmpty()!=true && author.isEmpty()!=true && title.isEmpty()!=true){
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where b.book_id LIKE '%"+book_id+"%' and bk.title LIKE '%"+title+"%' and ba.author LIKE '%"+author+"%' and ba.book_id=B.book_id and bk.book_id=B.book_id;";
			qrs7 = stmt.executeQuery(searchquery);return qrs7;	
		}else if(book_id.isEmpty()!=true && author.isEmpty()!=true && title.isEmpty()==true){
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where b.book_id LIKE '%"+book_id+"%' and ba.author LIKE '%"+author+"%' and ba.book_id=B.book_id and bk.book_id=B.book_id;";
			qrs7 = stmt.executeQuery(searchquery);	return qrs7;		
		}else if(book_id.isEmpty()!=true && author.isEmpty()==true && title.isEmpty()!=true){
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where b.book_id LIKE '%"+book_id+"%' and bk.title LIKE '%"+title+"%' and ba.book_id=B.book_id and bk.book_id=B.book_id;";
			qrs7 = stmt.executeQuery(searchquery); return qrs7;
		}else if(book_id.isEmpty()==true && author.isEmpty()!=true && title.isEmpty()!=true){
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where bk.title LIKE '%"+title+"%' and ba.author LIKE '%"+author+"%' and ba.book_id=B.book_id and bk.book_id=B.book_id;";
			qrs7 = stmt.executeQuery(searchquery); return qrs7;
		}else if(book_id.isEmpty()!=true && author.isEmpty()==true && title.isEmpty()==true){
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where B.book_id LIKE '%"+book_id+"%' and bk.book_id=B.book_id AND ba.book_id=bk.book_id;";
			qrs7 = stmt.executeQuery(searchquery); return qrs7;
		}else if(book_id.isEmpty()==true && author.isEmpty()!=true && title.isEmpty()==true){
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where ba.author LIKE '%"+author+"%' and ba.book_id=B.book_id AND ba.book_id=bk.book_id;";
			qrs7 = stmt.executeQuery(searchquery); return qrs7;
		}else {
			
			String searchquery =  "select B.book_id,bk.title, B.branch_id, B.No_of_copies as Copies_Inventoried,"
					+"(B.No_of_copies - (select count(Book_id) from book_loans B1 where B1.branch_id=B.branch_id and B1.book_id=B.book_id and date_in is NULL)) as Available_Copies" 
					+" from book_copies B, book as bk, book_authors as ba where bk.title LIKE '%"+title+"%' and bk.book_id=B.book_id AND ba.book_id=bk.book_id;";
			qrs7 = stmt.executeQuery(searchquery); return qrs7;
		}		
		
	
		}
	
	public ResultSet searchfine() throws SQLException{
		
		ResultSet qrs8=null;
		String fq= "Select loan_id,card_no,due_date,date_in from book_loans where due_date < sysdate();";
		//String fq="select card_no,fine.loan_id,SUM(fine_amt),paid from fine, book_loans bl where bl.loan_id=fine.loan_id Group BY card_no ;";
		try{qrs8=stmt.executeQuery(fq);}
		catch(SQLException e){e.printStackTrace();}
		return qrs8;
		}

		public void populatefine(String iq) throws SQLException{
			
			try {
				stmt.executeUpdate(iq);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		public void clearfinefirst(String cq) throws SQLException{
			 
			try {
				stmt.executeUpdate(cq);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		
		public ResultSet totalfinedisplay() throws SQLException{
		
			ResultSet qrs9=null;
			
			String fq="select br.fname,bl.card_no,fine.loan_id,SUM(fine_amt),paid from borrower br,fine, book_loans bl where br.card_no=bl.card_no AND bl.loan_id=fine.loan_id Group BY card_no ;";
			try{qrs9=stmt.executeQuery(fq);}
			catch(SQLException e){e.printStackTrace();}
			return qrs9;
			}
		
		public ResultSet duedisplay() throws SQLException{
			
			ResultSet qrs10=null;
	
			String dq="select card_no,fine.loan_id,SUM(fine_amt),paid from fine, book_loans bl where bl.loan_id=fine.loan_id AND date_in IS NULL Group BY card_no ;";
			try{qrs10=stmt.executeQuery(dq);}
			catch(SQLException e){e.printStackTrace();}
			return qrs10;
			}


		public ResultSet paiddisplay() throws SQLException{
		
			ResultSet qrs11=null;
			
			String pq="select card_no,fine.loan_id,SUM(fine_amt),paid from fine, book_loans bl where bl.loan_id=fine.loan_id AND date_in IS NOT NULL Group BY card_no ;";
			try{qrs11=stmt.executeQuery(pq);}
			catch(SQLException e){e.printStackTrace();}
			return qrs11;
			}
		
	

	public void add_Borrower(Borrower mBorrower) {
		String book_id;
		String title;

		int linect = 0;

		try {
		

			String mCount = "select count(card_no) from borrower where fname= "
					+ "'" + mBorrower.FName + "'" + " and lname ='"
					+ mBorrower.LName + "'" + " and address=" + "'"
					+ mBorrower.Address + "'" + "and phone='" + mBorrower.Phone
					+ "'";

			ResultSet qrs13 = stmt.executeQuery(mCount);

			qrs13.next();

			if (qrs13.getInt(1) > 0) {
				System.out.println("Entry Present");
			} else {

				String mCountStat = "select max(card_no) from borrower";

				qrs13 = stmt.executeQuery(mCountStat);
				qrs13.next();

				mBorrower.CardNo = qrs13.getInt(1);

				stmt.executeUpdate("INSERT INTO `library_system`.`borrower` (`card_no`, `fname`, `lname`, `address`, `phone`) VALUES ("
						+ ++(mBorrower.CardNo)
						+ ",'"
						+ mBorrower.FName
						+ "','"
						+ mBorrower.LName
						+ "','"
						+ mBorrower.Address
						+ "','"
						+ mBorrower.Phone + "')");
			}

			
			System.out.println("Success!!");
		} catch (SQLException ex) {
			System.out.println("Error in connection: " + ex.getMessage());
		}

	}
}
