package inquiry;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import kr.co.sist.dao.DbConnection;
import kr.co.sist.util.*;

public class AdminInquiryDAO {
	private static AdminInquiryDAO aiDAO;

	private AdminInquiryDAO() {

	}// constructor

	public static AdminInquiryDAO getInstance() {
		if (aiDAO == null) {
			aiDAO = new AdminInquiryDAO();

		} // end if
		return aiDAO;

	}// getInstance


    public InquiryVO selectOneInquiry(int num) throws SQLException {
        InquiryVO iVO = null;
        String sql = "SELECT inquiry_id, user_id, admin_ad, category, title, create_at, status, content FROM inquiry WHERE inquiry_id = ?";

        try (Connection con = DbConnection.getInstance().getConn();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, num);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    iVO = new InquiryVO();
                    iVO.setInquiryId(rs.getInt("inquiry_id"));
                    iVO.setUserId(rs.getString("user_id"));
                    iVO.setAdminAd(rs.getString("admin_ad"));
                    iVO.setCategory(rs.getString("category"));
                    iVO.setTitle(rs.getString("title"));
                    iVO.setCreateAt(rs.getTimestamp("create_at"));
                    iVO.setStatus(rs.getString("status"));

                    try {
                        BufferedReader br = new BufferedReader(rs.getClob("content").getCharacterStream());
                        StringBuilder content = new StringBuilder();
                        String temp;
                        while ((temp = br.readLine()) != null) {
                            content.append(temp).append("\n");
                        }
                        iVO.setContent(content.toString());
                    } catch (IOException ie) {
                        throw new SQLException("CLOB 데이터를 읽는 중 오류가 발생했습니다.", ie);
                    }
                }
            }
        }
        return iVO;
    }//selectOneInquiry


	/**
	 * 모든 문의사항을 조회하는 메서드
	 * 
	 * @return 모든 문의사항 정보를 담은 List<InquiryVO>
	 * @throws SQLException 데이터베이스 오류 발생 시
	 */
	public List<InquiryVO> selectAllInquiry() throws SQLException {
		List<InquiryVO> inquiryList = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			// 데이터베이스 연결
			con = dbCon.getConn();

			// SQL 쿼리 작성
			StringBuilder selectQuery = new StringBuilder();


			
			// 쿼리문 작성
			selectQuery.append("select inquiry_id,user_id,admin_ad,category,title,create_at,status ")
			           .append("FROM INQUIRY ");

			pstmt = con.prepareStatement(selectQuery.toString());

			// 쿼리 실행
			rs = pstmt.executeQuery();

			// 결과를 InquiryVO 객체에 저장
			while (rs.next()) {
			    InquiryVO iVO = new InquiryVO();
			    
			    // 매핑 작업
			    iVO.setInquiryId(rs.getInt("inquiry_id"));
			    iVO.setUserId(rs.getString("user_id"));
			    iVO.setAdminAd(rs.getString("admin_ad"));
			    iVO.setCategory(rs.getString("category"));
			    iVO.setTitle(rs.getString("title"));
			    iVO.setCreateAt(rs.getTimestamp("create_at"));
			    iVO.setStatus(rs.getString("status"));
			    
			    // 필요한 리스트나 컬렉션에 추가하는 코드 추가 가능

				// 리스트에 추가
				inquiryList.add(iVO);
			}
		} finally {
			// 자원 해제
			dbCon.dbClose(rs, pstmt, con);
		}

		return inquiryList; // 모든 문의사항 리스트 반환
	}//selectAllInquiry

	/**
	 * 관리자의 문의 답변내용을 추가하는 일
	 * @param 
	 * @return
	 * @throws SQLException
	 */
	public int insertHandleInquiry(InquiryVO iVO) throws SQLException {
		int rowCount = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			// 데이터베이스 연결
			con = dbCon.getConn();

			// SQL 업데이트 쿼리 작성
			StringBuilder updateQuery = new StringBuilder();
			updateQuery.append("UPDATE inquiry ").append("SET status = ?, ").append("    admin_ad = ? ")
					.append("WHERE inquiry_id = ?");

			pstmt = con.prepareStatement(updateQuery.toString());

			// 바인드 변수 설정
			pstmt.setString(1, iVO.getStatus()); // 상태를 업데이트
			pstmt.setString(2, iVO.getAdminAd()); // 관리자 답변을 업데이트
			pstmt.setInt(3, iVO.getInquiryId()); // 특정 문의 ID에 대해 업데이트

			// 쿼리문 실행 후 영향받은 행의 수를 반환
			rowCount = pstmt.executeUpdate();
		} finally {
			// 자원 해제
			dbCon.dbClose(null, pstmt, con);
		}
		return rowCount; // 업데이트된 행의 개수를 반환
	}//updateHandleInquiry

	/**
	 * 특정 문의사항을 삭제하는 메서드
	 * 
	 * @param iVo
	 * @return 삭제된 행 수
	 */
	public int deleteInquiry(InquiryVO iVo) throws SQLException {
		int deleteCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			String query = "DELETE FROM inquiry WHERE inquiry_id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, iVo.getInquiryId());

			deleteCnt = pstmt.executeUpdate();
		} finally {
			dbCon.dbClose(null, pstmt, con);
		}
		return deleteCnt;
	}// deleteInquiry
	
	
	/**
	 * 날짜, 유형별로 검색하여 문의를 가져오는 일.
	 * @param filter
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	public List<InquiryVO> selectInquiriesByFilter(String filter, String startDate, String endDate) throws SQLException {
	    List<InquiryVO> inquiryList = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM inquiry WHERE 1=1");

	    // 조건 추가: filter가 전체가 아니고 값이 있을 때
	    if (filter != null && !"all".equals(filter)) {
	        sql.append(" AND category = ?");
	    }

	    // 날짜 범위가 제공될 때만 날짜 조건 추가
	    if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
	        sql.append(" AND create_at >= ? AND create_at < ?");
	    }

	    try (Connection con = DbConnection.getInstance().getConn();
	         PreparedStatement pstmt = con.prepareStatement(sql.toString())) {

	        int paramIndex = 1;

	        // 카테고리 필터 바인딩
	        if (filter != null && !"all".equals(filter)) {
	            pstmt.setString(paramIndex++, filter);
	        }

	        // 날짜 필터 바인딩
	        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            try {
	                Date parsedStartDate = dateFormat.parse(startDate);
	                Date parsedEndDate = dateFormat.parse(endDate);

	                // endDate에 하루를 더하여 해당 날짜의 끝까지 포함하도록 함
	                Calendar cal = Calendar.getInstance();
	                cal.setTime(parsedEndDate);
	                cal.add(Calendar.DATE, 1);
	                Date adjustedEndDate = cal.getTime();

	                pstmt.setDate(paramIndex++, new java.sql.Date(parsedStartDate.getTime()));
	                pstmt.setDate(paramIndex++, new java.sql.Date(adjustedEndDate.getTime()));
	            } catch (ParseException e) {
	                throw new SQLException("날짜 변환 중 오류가 발생했습니다.", e);
	            }
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                InquiryVO inquiry = new InquiryVO();
	                inquiry.setInquiryId(rs.getInt("inquiry_id"));
	                inquiry.setUserId(rs.getString("user_id"));
	                inquiry.setAdminAd(rs.getString("admin_ad"));
	                inquiry.setCategory(rs.getString("category"));
	                inquiry.setTitle(rs.getString("title"));
	                inquiry.setCreateAt(rs.getTimestamp("create_at"));  // TIMESTAMP 사용
	                inquiry.setStatus(rs.getString("status"));
	                inquiryList.add(inquiry);
	            }
	        }
	    }
	    return inquiryList;
	}//selectInquiriesByFilter

	
	

    // 총 문의 수 가져오기 (필터 적용)
    public int getTotalInquiryCount(SearchVO sVO) throws SQLException {
        int totalCount = 0;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DbConnection dbCon = DbConnection.getInstance();

        try {
            con = dbCon.getConn();
            StringBuilder query = new StringBuilder("SELECT COUNT(*) AS total FROM inquiry WHERE 1=1");

            List<Object> parameters = new ArrayList<>();

            // 필터 적용
            if (!sVO.getFilter().equals("all")) {
                query.append(" AND category = ?");
                parameters.add(sVO.getFilter());
            }

            // 검색 키워드 적용
            if (sVO.getKeyword() != null && !sVO.getKeyword().isEmpty()) {
                query.append(" AND ").append( BoardUtil.numToField(sVO.getField())).append(" LIKE ?");
                parameters.add("%" + sVO.getKeyword() + "%");
            }

            // 날짜 범위 적용
            if (sVO.getStartDate() != null && !sVO.getStartDate().isEmpty() &&
                sVO.getEndDate() != null && !sVO.getEndDate().isEmpty()) {
                query.append(" AND create_at BETWEEN ? AND ?");
                parameters.add(Timestamp.valueOf(sVO.getStartDate() + " 00:00:00"));
                parameters.add(Timestamp.valueOf(sVO.getEndDate() + " 23:59:59"));
            }

            pstmt = con.prepareStatement(query.toString());

            // 파라미터 설정
            for (int i = 0; i < parameters.size(); i++) {
                pstmt.setObject(i + 1, parameters.get(i));
            }

            rs = pstmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt("total");
            }
        } finally {
            dbCon.dbClose(rs, pstmt, con);
        }

        return totalCount;
    }
    // 문의 목록 가져오기 (필터 및 페이지네이션 적용)
    public List<InquiryVO> getInquiries(SearchVO sVO) throws SQLException {
        List<InquiryVO> inquiryList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        DbConnection dbCon = DbConnection.getInstance();

        try {
            con = dbCon.getConn();
            StringBuilder query = new StringBuilder("SELECT * FROM (");
            query.append("SELECT ROW_NUMBER() OVER (ORDER BY inquiry_id DESC) AS row_num, inquiry.* FROM inquiry WHERE 1=1");

            List<Object> parameters = new ArrayList<>();

            // 필터 적용
            if (!sVO.getFilter().equals("all")) {
                query.append(" AND category = ?");
                parameters.add(sVO.getFilter());
            }

            // 검색 키워드 적용
            if (sVO.getKeyword() != null && !sVO.getKeyword().isEmpty()) {
                query.append(" AND ").append(BoardUtil.numToField(sVO.getField())).append(" LIKE ?");
                parameters.add("%" + sVO.getKeyword() + "%");
            }

            // 날짜 범위 적용
            if (sVO.getStartDate() != null && !sVO.getStartDate().isEmpty() &&
                sVO.getEndDate() != null && !sVO.getEndDate().isEmpty()) {
                query.append(" AND create_at BETWEEN ? AND ?");
                parameters.add(Timestamp.valueOf(sVO.getStartDate() + " 00:00:00"));
                parameters.add(Timestamp.valueOf(sVO.getEndDate() + " 23:59:59"));
            }

            query.append(") temp WHERE row_num BETWEEN ? AND ?");

            pstmt = con.prepareStatement(query.toString());

            // 파라미터 설정
            int paramIndex = 1;
            for (Object param : parameters) {
                pstmt.setObject(paramIndex++, param);
            }
            pstmt.setInt(paramIndex++, sVO.getStartNum());
            pstmt.setInt(paramIndex, sVO.getEndNum());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                InquiryVO iVO = new InquiryVO();
                iVO.setInquiryId(rs.getInt("inquiry_id"));
                iVO.setTitle(rs.getString("title"));
                iVO.setUserId(rs.getString("user_id"));
                iVO.setCategory(rs.getString("category"));
                iVO.setStatus(rs.getString("status"));
                iVO.setContent(rs.getString("content")); // CLOB 처리 필요 시 수정
                iVO.setCreateAt(rs.getTimestamp("create_at"));
                inquiryList.add(iVO);
            }
        } finally {
            dbCon.dbClose(rs, pstmt, con);
        }

        return inquiryList;
    }

	
	
}// class
