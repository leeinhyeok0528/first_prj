package inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;

public class AdminInquiryDAO {
	private static AdminInquiryDAO aiDAO;
	
	private AdminInquiryDAO() {
		
		
	}//constructor
	
	private static AdminInquiryDAO getInstance() {
		if(aiDAO==null) {
			aiDAO=new AdminInquiryDAO();
			
		}//end if
		return aiDAO;
		
	}//getInstance
	

	/**
	 * 문의사항에 대한 정보를 받아서 하나의 문의사항을 선택하는 일
	 * 
	 * @param iVO
	 * @return list(선택한 문의사항에 대한 정보)
	 */
	public List<InquiryVO> selectOneInquiry(InquiryVO iVO)throws SQLException{
		List<InquiryVO> list = new ArrayList<InquiryVO>();

		//db작업 수행하기
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		return list;
	}// selectOneInquiry

	/**
	 * 문의에 대한 정보를 받아 모든 문의를 검색하는 일.
	 * 
	 * @param iVO
	 * @return
	 */
	public List<InquirySearchVO> selectAllInquiry(InquiryVO iVO) {
		List<InquirySearchVO> list = new ArrayList<InquirySearchVO>();

		return list;
	}// selectAllInquiry

	public List<InquiryVO> updateHandleInquiry(InquiryVO iVO) {
		List<InquiryVO> list = new ArrayList<InquiryVO>();

		return list;
	}// updateHandleInquiry

	
	public int deleteInquiry(InquiryVO iVo) {
		int deleteCnt=0;
		return deleteCnt;
	}//deleteInquiry
	
}// class
