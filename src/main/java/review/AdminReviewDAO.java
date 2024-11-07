package review;

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

public class AdminReviewDAO {
	private static AdminReviewDAO adDAO;

	private AdminReviewDAO() {

	}// constructr

	public static AdminReviewDAO getInstance() {
		if (adDAO == null) {
			adDAO = new AdminReviewDAO();

		} // end if
		return adDAO;

	}// getInstance

	public int selectTotalCount(ReviewSearchVO sVO) throws SQLException {
		int totalCount = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder selectCount = new StringBuilder();
			selectCount.append("SELECT COUNT(review_id) AS cnt FROM review ");

			List<String> conditions = new ArrayList<>();
			List<Object> params = new ArrayList<>();

			if (sVO.getFilter() != null && !"all".equalsIgnoreCase(sVO.getFilter())) {
				conditions.add("category = ?");
				params.add(sVO.getFilter());
			}

			if (sVO.getStartDate() != null && !sVO.getStartDate().isEmpty()) {
				conditions.add("created_at >= ?");
				params.add(sVO.getStartDate());
			}

			if (sVO.getEndDate() != null && !sVO.getEndDate().isEmpty()) {
				conditions.add("created_at <= ?");
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
	}// selectTotalCount

	public ReviewVO selectOneReview(int reviewId) throws SQLException {
		ReviewVO rVO = null;
		StringBuilder selectReview = new StringBuilder();
		selectReview.append("SELECT review_id, user_id, product_id, content, created_at, rating ")
				.append("FROM review ").append("WHERE review_id = ?");

		try (Connection con = DbConnection.getInstance().getConn();
				PreparedStatement pstmt = con.prepareStatement(selectReview.toString())) {

			pstmt.setInt(1, reviewId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					rVO = new ReviewVO();
					rVO.setReviewId(rs.getInt("review_id"));
					rVO.setUserId(rs.getString("user_id"));
					rVO.setProductId(rs.getInt("product_id"));
					rVO.setCreateAt(rs.getTimestamp("created_at"));
					rVO.setRating(rs.getInt("rating"));

					try {
						BufferedReader br = new BufferedReader(rs.getClob("content").getCharacterStream());
						StringBuilder content = new StringBuilder();
						String temp;
						while ((temp = br.readLine()) != null) {
							content.append(temp).append("\n");
						}
						rVO.setContent(content.toString());
					} catch (IOException ie) {
						throw new SQLException("CLOB 데이터를 읽는 중 오류가 발생했습니다.", ie);
					} // catch
				} // end if
			} // try
		} // try
		return rVO;
	}// selectOneReview

	/**
	 * 
	 * @param rdVO
	 * @return
	 */
	public List<ReviewVO> selectAllReview() throws SQLException {
		List<ReviewVO> reviewList = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			// 데이터베이스 연결
			con = dbCon.getConn();

			// SQL 쿼리 작성
			StringBuilder selectQuery = new StringBuilder();
			selectQuery.append("SELECT r.review_id, r.user_id, r.product_id, p.name AS product_name, ")
					.append("r.content, r.created_at, r.rating ").append("FROM review r ")
					.append("JOIN products p ON r.product_id = p.product_id");

			pstmt = con.prepareStatement(selectQuery.toString());

			// 쿼리 실행
			rs = pstmt.executeQuery();

			// 결과를 ReviewVO 객체에 저장
			String productName = null;
			while (rs.next()) {
				ReviewVO rVO = new ReviewVO();
				// 매핑 작업
				rVO.setReviewId(rs.getInt("review_id"));
				rVO.setUserId(rs.getString("user_id"));
				rVO.setProductId(rs.getInt("product_id"));
				rVO.setProductName(rs.getString("product_name")); // 제품 이름 설정
				rVO.setCreateAt(rs.getTimestamp("created_at"));
				rVO.setRating(rs.getInt("rating"));

				try (BufferedReader br = new BufferedReader(rs.getClob("content").getCharacterStream())) {
					StringBuilder content = new StringBuilder();
					String temp;
					while ((temp = br.readLine()) != null) {
						content.append(temp).append("\n");
					}
					rVO.setContent(content.toString());
				} catch (IOException ie) {
					throw new SQLException("CLOB 데이터를 읽는 중 오류가 발생했습니다.", ie);
				}

				// 리스트에 추가
				reviewList.add(rVO);
			}
		} finally {
			// 자원 해제
			dbCon.dbClose(rs, pstmt, con);
		}

		return reviewList; // 모든 리뷰 리스트 반환
	}// selectAllReview

	/**
	 * @param rVO
	 * @return
	 */
	public int deleteReview(ReviewVO rVO) throws SQLException {
		int deleteCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			String deleteQuery = "DELETE FROM review WHERE review_id = ?";
			pstmt = con.prepareStatement(deleteQuery);
			pstmt.setInt(1, rVO.getReviewId());

			deleteCnt = pstmt.executeUpdate();
		} finally {
			dbCon.dbClose(null, pstmt, con);
		}
		return deleteCnt;
	}// deleteReview

	/**
	 * 날짜, 유형별로 검색하여 문의를 가져오는 일.
	 * 
	 * @param filter
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	public List<ReviewVO> selectInquiriesByFilter(String filter, String startDate, String endDate) throws SQLException {
		List<ReviewVO> reviewList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM review WHERE 1=1");

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
					ReviewVO rVO = new ReviewVO();
					rVO.setReviewId(rs.getInt("review_id"));
					rVO.setUserId(rs.getString("user_id"));
					rVO.setProductId(rs.getInt("product_id"));
					rVO.setCreateAt(rs.getTimestamp("created_at")); // TIMESTAMP 사용
					rVO.setRating(rs.getInt("rating"));
					reviewList.add(rVO);
				}
			}
		}
		return reviewList;
	}// selectInquiriesByFilter

}// class
