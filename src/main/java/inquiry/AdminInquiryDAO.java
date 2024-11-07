package inquiry;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import kr.co.sist.dao.DbConnection;

public class AdminInquiryDAO {
	private static AdminInquiryDAO aiDAO;

	private AdminInquiryDAO() {
	}

	public static AdminInquiryDAO getInstance() {
		if (aiDAO == null) {
			aiDAO = new AdminInquiryDAO();
		}
		return aiDAO;
	}

	/**
	 * 총 게시물의 수 검색 (필터 및 날짜 필터 적용)
	 * 
	 * @param sVO 검색 조건을 담은 SearchVO 객체
	 * @return 게시물의 수
	 * @throws SQLException 데이터베이스 오류 발생 시
	 */
	public int selectTotalCount(InquirySearchVO sVO) throws SQLException {
		int totalCount = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder selectCount = new StringBuilder();
			selectCount.append("SELECT COUNT(inquiry_id) AS cnt FROM inquiry ");

			List<String> conditions = new ArrayList<>();
			List<Object> params = new ArrayList<>();

			if (sVO.getFilter() != null && !"all".equalsIgnoreCase(sVO.getFilter())) {
				conditions.add("category = ?");
				params.add(sVO.getFilter());
			}

			if (sVO.getStartDate() != null && !sVO.getStartDate().isEmpty()) {
				conditions.add("create_at >= ?");
				params.add(sVO.getStartDate());
			}

			if (sVO.getEndDate() != null && !sVO.getEndDate().isEmpty()) {
				conditions.add("create_at <= ?");
				params.add(sVO.getEndDate());
			}

			if (!conditions.isEmpty()) {
				selectCount.append(" WHERE ");
				selectCount.append(String.join(" AND ", conditions));
			}

			pstmt = con.prepareStatement(selectCount.toString());

			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				totalCount = rs.getInt("cnt");
			}
		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}

		return totalCount;
	}

	/**
	 * 페이지네이션이 적용된 모든 문의사항을 조회하는 메서드 (필터 및 날짜 필터 적용)
	 * 
	 * @param sVO 검색 및 페이징 조건을 담은 SearchVO 객체
	 * @return 모든 문의사항 정보를 담은 List<InquiryVO>
	 * @throws SQLException 데이터베이스 오류 발생 시
	 */
	public List<InquiryVO> selectAllInquiry(InquirySearchVO sVO) throws SQLException {
		List<InquiryVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder selectBoard = new StringBuilder();
			selectBoard.append("SELECT inquiry_id, title, user_id, create_at, category, status ").append("FROM ( ")
					.append("    SELECT inquiry_id, title, user_id, create_at, category, status, ")
					.append("           ROW_NUMBER() OVER (ORDER BY create_at DESC) AS rnum ")
					.append("    FROM inquiry ");

			List<String> conditions = new ArrayList<>();
			List<Object> params = new ArrayList<>();

			if (sVO.getFilter() != null && !"all".equalsIgnoreCase(sVO.getFilter())) {
				conditions.add("category = ?");
				params.add(sVO.getFilter());
			}

			if (sVO.getStartDate() != null && !sVO.getStartDate().isEmpty()) {
				conditions.add("create_at >= ?");
				params.add(sVO.getStartDate());
			}

			if (sVO.getEndDate() != null && !sVO.getEndDate().isEmpty()) {
				conditions.add("create_at <= ?");
				params.add(sVO.getEndDate());
			}

			if (!conditions.isEmpty()) {
				selectBoard.append(" WHERE ");
				selectBoard.append(String.join(" AND ", conditions));
			}

			selectBoard.append(" ) ").append("WHERE rnum BETWEEN ? AND ?");

			pstmt = con.prepareStatement(selectBoard.toString());

			int bindIndex = 1;
			for (Object param : params) {
				pstmt.setObject(bindIndex++, param);
			}
			pstmt.setInt(bindIndex++, sVO.getStartNum());
			pstmt.setInt(bindIndex++, sVO.getEndNum());

			rs = pstmt.executeQuery();

			InquiryVO iVO = null;
			while (rs.next()) {
				iVO = new InquiryVO();
				iVO.setInquiryId(rs.getInt("inquiry_id"));
				iVO.setTitle(rs.getString("title"));
				iVO.setUserId(rs.getString("user_id"));
				iVO.setCreateAt(rs.getTimestamp("create_at"));
				iVO.setCategory(rs.getString("category"));
				iVO.setStatus(rs.getString("status"));
				list.add(iVO);
			}

		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}

		return list;
	}

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
	}// selectOneInquiry

	/**
	 * 관리자의 문의 답변내용을 추가하는 일
	 * 
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
			StringBuilder insertQuery = new StringBuilder();
			insertQuery.append("UPDATE inquiry ").append("SET status = ?, ").append("    admin_ad = ? ")
					.append("WHERE inquiry_id = ?");

			pstmt = con.prepareStatement(insertQuery.toString());

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
	}// updateHandleInquiry

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
			String delete = "DELETE FROM inquiry WHERE inquiry_id = ?";
			pstmt = con.prepareStatement(delete);
			pstmt.setInt(1, iVo.getInquiryId());

			deleteCnt = pstmt.executeUpdate();
		} finally {
			dbCon.dbClose(null, pstmt, con);
		}
		return deleteCnt;
	}// deleteInquiry

	/**
	 * 날짜, 유형별로 검색하여 문의를 가져오는 일.
	 * 
	 * @param filter
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	public List<InquiryVO> selectInquiriesByFilter(String filter, String startDate, String endDate)
			throws SQLException {
		List<InquiryVO> inquiryList = new ArrayList<>();
		StringBuilder selectFilter = new StringBuilder("SELECT * FROM inquiry WHERE 1=1");

		// 조건 추가: filter가 전체가 아니고 값이 있을 때
		if (filter != null && !"all".equals(filter)) {
			selectFilter.append(" AND category = ?");
		}

		// 날짜 범위가 제공될 때만 날짜 조건 추가
		if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
			selectFilter.append(" AND create_at >= ? AND create_at < ?");
		}

		try (Connection con = DbConnection.getInstance().getConn();
				PreparedStatement pstmt = con.prepareStatement(selectFilter.toString())) {

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
					InquiryVO iVO = new InquiryVO();
					iVO.setInquiryId(rs.getInt("inquiry_id"));
					iVO.setUserId(rs.getString("user_id"));
					iVO.setAdminAd(rs.getString("admin_ad"));
					iVO.setCategory(rs.getString("category"));
					iVO.setTitle(rs.getString("title"));
					iVO.setCreateAt(rs.getTimestamp("create_at")); // TIMESTAMP 사용
					iVO.setStatus(rs.getString("status"));
					inquiryList.add(iVO);
				}
			}
		}
		return inquiryList;
	}// selectInquiriesByFilter

}// class
