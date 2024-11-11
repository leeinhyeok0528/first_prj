package admin;

import java.util.ArrayList;
import java.util.List;

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
	 * 신규주문수를 받아서 대시보드에 출력하는 일
	 * 
	 * @return
	 */
	public int selectCountNewOrders() {

		int newOrderCnt = 0;
		int OrderCnt;
		return newOrderCnt;
	}// selectCountNewOrders

	/**
	 * 
	 * 배송상황에 대해 체크하여 대시보드에 출력하는 일
	 * 
	 * @return shippingCnt
	 */
	public int selectCountOrdersByShippingStatus() {

		int ShippingCnt = 0;

		return ShippingCnt;

	}// selectCountOrdersByShippingStatus

	/**
	 * 취소요청에 대한 횟수를 받아 대시보드에 출력하는 일
	 * 
	 * @return cancelCnt
	 */
	public int selectCountCancelRequest() {
		int cancelCnt = 0;

		return cancelCnt;

	}// selectCountCancelRequest

	/**
	 * 구매확정 건수를 받아 대시보드에 출력하는 일
	 * 
	 * @return confirmedCnt
	 */
	public int selectCountConfirmed() {
		int confirmedCnt = 0;

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

}// class
