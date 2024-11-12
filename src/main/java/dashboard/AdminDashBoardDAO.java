package dashboard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;

public class AdminDashBoardDAO {
	private static AdminDashBoardDAO adbDAO;

	private AdminDashBoardDAO() {

	}// constructor

	public static AdminDashBoardDAO getInstance() {
		if (adbDAO == null) {
			adbDAO = new AdminDashBoardDAO();
		} // end if
		return adbDAO;

	}// getInstance

	/**
	 * 신규주문수를 받아서 대시보드에 출력하는 일 orders 테이블에서 order_status를 가져온다 이때 '결제완료' 인 상태를 가져오면
	 * 된다
	 * 
	 * @return
	 */
	public int selectCountNewOrders() throws SQLException {
		int newOrderCnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();
		ResultSet rs = null;
		try {
			con = dbCon.getConn();

			StringBuilder selectNewOrder = new StringBuilder();
			selectNewOrder.append("  	select count(order_status) ").append(" 	from ORDERS  ")
					.append("WHERE order_status = '결제완료'  ");

			pstmt = con.prepareStatement(selectNewOrder.toString());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				newOrderCnt = rs.getInt(1);
			} // end if

		} finally {
			dbCon.dbClose(rs, pstmt, con);

		}
		System.out.println("신규주문" + newOrderCnt);
		return newOrderCnt;
	}// selectCountNewOrders

	/**
	 * 
	 * 배송상황에 대해 체크하여 대시보드에 출력하는 일
	 * 
	 * @return shippingCnt
	 */
	public List<DeliveryStatusVO> selectDeliveryStatus() throws SQLException {
	    List<DeliveryStatusVO> list = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();

	    try {
	    	con= dbCon.getConn();
	    	StringBuilder selectStatus = new StringBuilder();
	    	selectStatus
	    	.append(" 	SELECT  status, COUNT(*) AS count	")
	    	.append(" 	FROM DELIVERY	")
	    	.append(" 	GROUP BY status	");
	    	
	    	pstmt =con.prepareStatement(selectStatus.toString());
	    	rs =pstmt.executeQuery();
	    	
	        while (rs.next()) {
	            String status = rs.getString("status");
	            int count = rs.getInt("count");
	            list.add(new DeliveryStatusVO(status, count));
	        }
	    }finally {
			dbCon.dbClose(rs, pstmt, con);
		}
	    System.out.println(list);

		return list;

	}// selectCountOrdersByShippingStatus

	/**
	 * 구매확정 건수를 받아 대시보드에 출력하는 일
	 * 
	 * @return confirmedCnt
	 */
	public int selectCountConfirmed() throws SQLException {
		int confirmedCnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();
		ResultSet rs = null;
		try {
			con = dbCon.getConn();

			StringBuilder selectConfirm = new StringBuilder();
			selectConfirm.append("  	select count(order_status) ").append(" 	from ORDERS  ")
					.append("WHERE order_status = '구매확정'  ");

			pstmt = con.prepareStatement(selectConfirm.toString());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				confirmedCnt = rs.getInt(1);
			} // end if

		} finally {
			dbCon.dbClose(rs, pstmt, con);

		}
		System.out.println("구매확정" + confirmedCnt);
		return confirmedCnt;

	}// selectCountConfirmed

	/**
	 * 일일 매출을 담아서 대시보드 그래프에 출력하는 일
	 * 
	 * @return list
	 */
	public List<String> selectSumDailySales() {
		List<String> list = new ArrayList<String>();

		return list;
	}// selectSumDailySales

	/**
	 * 미답변 문의 숫자를 반환하는 일.
	 * 
	 * @return
	 */
	public int inquiryCnt() throws SQLException {
		int inquiryCnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();

			StringBuilder selectCnt = new StringBuilder();
			selectCnt.append(" 	select count(status)	").append(" 	from INQUIRY	")
					.append(" 	where status ='미처리'	");
			pstmt = con.prepareStatement(selectCnt.toString());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				inquiryCnt = rs.getInt(1);
			} // end if

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		} // finally

		System.out.println("미답변 문의" + inquiryCnt);
		return inquiryCnt;
	}// inquiryCnt

	/**
	 * 총 회원수를 반환하는 일
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int totalMemberCnt() throws SQLException {
		int memberCnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();

			StringBuilder selectCnt = new StringBuilder();
			selectCnt.append(" 	select count(user_id)	").append(" 	from MEMBER	");
			pstmt = con.prepareStatement(selectCnt.toString());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberCnt = rs.getInt(1);
			} // end if

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		} // finally

		System.out.println("총 회원수 " + memberCnt);
		return memberCnt;
	}// inquiryCnt

	public int newMemberCnt() throws SQLException {
		int memberCnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();

			StringBuilder selectCnt = new StringBuilder();
			selectCnt.append(" 	select count(user_id)	").append(" 	from MEMBER	")
					.append("    where trunc(join_date)=trunc(sysdate)   ");
			pstmt = con.prepareStatement(selectCnt.toString());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberCnt = rs.getInt(1);
			} // end if

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		} // finally

		System.out.println("신규 회원수 " + memberCnt);
		return memberCnt;
	}// inquiryCnt

	/**
	 * 주문취소 건수를 가져오는 일
	 * 
	 * @return 주문취소건수
	 * @throws SQLException
	 */
	public int orderCancel() throws SQLException {
		int cancelCnt = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();

			StringBuilder selectCnt = new StringBuilder();
			selectCnt.append(" 	select count(order_id)	").append(" 	from ORDER_CANCEL	");
			pstmt = con.prepareStatement(selectCnt.toString());

			rs = pstmt.executeQuery();
			if (rs.next()) {
				cancelCnt = rs.getInt(1);
			} // end if

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		} // finally

		System.out.println("주문취소 수 " + cancelCnt);
		return cancelCnt;
	}// inquiryCnt

	public static void main(String[] args) {
		AdminDashBoardDAO adbDAO = AdminDashBoardDAO.getInstance();
		try {
			;
			System.out.println(adbDAO.selectDeliveryStatus());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}// class
