package review;

import java.util.ArrayList;
import java.util.List;

public class AdminReviewDAO {
	private static AdminReviewDAO adDAO;
	
	private AdminReviewDAO() {
		
	}//constructr
	
	public static AdminReviewDAO getInstance() {
		if(adDAO==null) {
			adDAO= new AdminReviewDAO();
			
		}//end if
		return adDAO;
		
	}//getInstance
	
	
	/**
	 * 
	 * @param rdVO
	 * @return
	 */
	public List<ReviewVO> selectAllReview(ReviewDateVO rdVO) {
		List<ReviewVO> list = new ArrayList<ReviewVO>();

		return list;
	}// selectAllReview

	/**
	 * @param rVO
	 * @return
	 */
	public int deleteReview(ReviewVO rVO) {
		int deleteCnt = 0;

		return deleteCnt;
	}// deleteReview

}// class
