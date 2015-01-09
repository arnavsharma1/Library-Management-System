package library_mgmt_system;
import java.sql.Date;

public class CheckInObj {

	String mLoanId;
	String mBookId;
	int mCardNo;
	Date mDate;

	public CheckInObj(String mLoanId, String mBookId, int mCardNo, Date mDate) {
		super();
		this.mLoanId = mLoanId;
		this.mBookId = mBookId;
		this.mCardNo = mCardNo;
		this.mDate = mDate;
	}

}
