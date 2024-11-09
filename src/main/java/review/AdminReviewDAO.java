package review;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.DbConnection;

public class AdminReviewDAO {
	private static AdminReviewDAO adDAO;

	private AdminReviewDAO() {

	}// constructor

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

		/*
		 * 날짜필터, 내용에서 키워드로 검색하기, 작성자로 검색하기
		 */
		try {
			con = dbCon.getConn();
			StringBuilder selectCount = new StringBuilder();
			selectCount.append("SELECT COUNT(review_id) AS cnt FROM review ");

			boolean hasWhere = false;
			int paramIndex = 1; // 바인드 변수 인덱스 관리

			// 날짜 필터가 있을 때
			if (sVO.getStartDate() != null && sVO.getEndDate() != null) {
				selectCount.append(" WHERE created_at BETWEEN ? AND ? ");
				hasWhere = true;
			}

			// 유형 필터가 있을 때 ('all'이 아닐 경우에만 조건 추가)
			if (sVO.getFilter() != null && !"".equals(sVO.getFilter()) && !"0".equalsIgnoreCase(sVO.getFilter())) {
				if (!hasWhere) {
					selectCount.append(" WHERE ");
					hasWhere = true;
				} else {
					selectCount.append(" AND ");
				}
				selectCount.append("category = ? ");
			}//end else

			// 검색 키워드가 있을 때
			if (sVO.getKeyword() != null && !"".equals(sVO.getKeyword())) {
				if (!hasWhere) {
					selectCount.append(" WHERE ");
					hasWhere = true;
				} else {
					selectCount.append(" AND ");
				}
				selectCount.append("instr(").append(ReviewUtil.numToField(sVO.getField())).append(", ?) != 0");
			}

			pstmt = con.prepareStatement(selectCount.toString());

			// 바인드 변수에 값 설정
			if (sVO.getStartDate() != null && sVO.getEndDate() != null) {
				pstmt.setDate(paramIndex++, java.sql.Date.valueOf(sVO.getStartDate()));
				pstmt.setDate(paramIndex++, java.sql.Date.valueOf(sVO.getEndDate()));
			}

			if (sVO.getFilter() != null && !"".equals(sVO.getFilter()) && !"all".equalsIgnoreCase(sVO.getFilter())) {
				pstmt.setString(paramIndex++, sVO.getFilter());
			}

			if (sVO.getKeyword() != null && !"".equals(sVO.getKeyword())) {
				pstmt.setString(paramIndex++, sVO.getKeyword());
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

}// class
