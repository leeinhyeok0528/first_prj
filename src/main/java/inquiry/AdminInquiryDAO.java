package inquiry;

import java.io.BufferedReader;
import java.io.IOException;
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

	public static AdminInquiryDAO getInstance() {
		if (aiDAO == null) {
			aiDAO = new AdminInquiryDAO();
		}//end if
		return aiDAO;
	}//getInstance

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

	        boolean hasWhere = false;
	        int paramIndex = 1; // 바인드 변수 인덱스 관리

	        // 날짜 필터가 있을 때
	        if (sVO.getStartDate() != null && sVO.getEndDate() != null) {
	            selectCount.append(" WHERE create_at BETWEEN ? AND ? ");
	            hasWhere = true;
	        }

	        // 유형 필터가 있을 때 ('all'이 아닐 경우에만 조건 추가)
	        if (sVO.getFilter() != null && !"".equals(sVO.getFilter()) && !"all".equalsIgnoreCase(sVO.getFilter())) {
	            if (!hasWhere) {
	                selectCount.append(" WHERE ");
	                hasWhere = true;
	            } else {
	                selectCount.append(" AND ");
	            }
	            selectCount.append("category = ? ");
	        }

	        // 검색 키워드가 있을 때
	        if (sVO.getKeyword() != null && !"".equals(sVO.getKeyword())) {
	            if (!hasWhere) {
	                selectCount.append(" WHERE ");
	                hasWhere = true;
	            } else {
	                selectCount.append(" AND ");
	            }
	            selectCount.append("instr(")
	                    .append(InquiryUtil.numToField(sVO.getField()))
	                    .append(", ?) != 0");
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
	}

	public List<InquiryVO> selectAllInquiry(InquirySearchVO sVO) throws SQLException {
	    List<InquiryVO> list = new ArrayList<>();
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    DbConnection dbCon = DbConnection.getInstance();

	    try {
	        // 1. 데이터베이스 연결 생성
	        con = dbCon.getConn();

	        // 2. SQL 쿼리문 생성
	        StringBuilder selectInquiry = new StringBuilder();
	        selectInquiry.append("SELECT inquiry_id, title, user_id, create_at, category, status ")
	                .append("FROM ( ")
	                .append("    SELECT inquiry_id, title, user_id, create_at, category, status, ")
	                .append("           ROW_NUMBER() OVER (ORDER BY create_at DESC) AS rnum ")
	                .append("    FROM inquiry ");

	        boolean hasWhere = false;
	        int paramIndex = 1; // 바인드 변수 인덱스 관리

	        // 날짜 필터 적용
	        if (sVO.getStartDate() != null && !"".equals(sVO.getStartDate()) &&
	            sVO.getEndDate() != null && !"".equals(sVO.getEndDate())) {
	            selectInquiry.append(" WHERE create_at BETWEEN ? AND ? ");
	            hasWhere = true;
	        }

	        // 유형 필터 적용
	        if (sVO.getFilter() != null && !"".equals(sVO.getFilter()) && !"all".equalsIgnoreCase(sVO.getFilter())) {
	            if (!hasWhere) {
	                selectInquiry.append(" WHERE ");
	                hasWhere = true;
	            } else {
	                selectInquiry.append(" AND ");
	            }
	            selectInquiry.append("category = ? ");
	        }

	        // 키워드 검색 적용
	        if (sVO.getKeyword() != null && !"".equals(sVO.getKeyword())) {
	            if (!hasWhere) {
	                selectInquiry.append(" WHERE ");
	                hasWhere = true;
	            } else {
	                selectInquiry.append(" AND ");
	            }
	            selectInquiry.append("instr(")
	                    .append(InquiryUtil.numToField(sVO.getField()))
	                    .append(", ?) != 0");
	        }

	        // 서브쿼리 닫기 및 페이징 처리
	        selectInquiry.append(" ) WHERE rnum BETWEEN ? AND ?");

	        // 3. PreparedStatement 생성
	        pstmt = con.prepareStatement(selectInquiry.toString());

	        // 4. 바인드 변수 설정
	        if (sVO.getStartDate() != null && !"".equals(sVO.getStartDate()) &&
	            sVO.getEndDate() != null && !"".equals(sVO.getEndDate())) {
	            pstmt.setDate(paramIndex++, java.sql.Date.valueOf(sVO.getStartDate()));
	            pstmt.setDate(paramIndex++, java.sql.Date.valueOf(sVO.getEndDate()));
	        }

	        if (sVO.getFilter() != null && !"".equals(sVO.getFilter()) && !"all".equalsIgnoreCase(sVO.getFilter())) {
	            pstmt.setString(paramIndex++, sVO.getFilter());
	        }

	        if (sVO.getKeyword() != null && !"".equals(sVO.getKeyword())) {
	            pstmt.setString(paramIndex++, sVO.getKeyword());
	        }

	        // 페이징 바인드 변수 설정
	        pstmt.setInt(paramIndex++, sVO.getStartNum());
	        pstmt.setInt(paramIndex++, sVO.getEndNum());

	        // 5. 쿼리 실행 및 결과 처리
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            InquiryVO iVO = new InquiryVO();
	            iVO.setInquiryId(rs.getInt("inquiry_id"));
	            iVO.setTitle(rs.getString("title"));
	            iVO.setUserId(rs.getString("user_id"));
	            iVO.setCreateAt(rs.getTimestamp("create_at"));
	            iVO.setCategory(rs.getString("category"));
	            iVO.setStatus(rs.getString("status"));
	            list.add(iVO);
	        }

	    } finally {
	        // 6. 자원 해제
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

	
	public int updateBoard(InquiryVO  iVO)throws SQLException{
		int rowCnt=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		try {
			//connection얻기
			con=dbCon.getConn();
			//쿼리문 생성객체 얻기
			StringBuilder updateBoard=new StringBuilder();
			updateBoard
			.append("	update	inquiry	")
			.append("	set		admin_ad =?	")
			.append("	where	inquiry_id =? ");
			
			pstmt=con.prepareStatement(updateBoard.toString());
			//바인드 변수에 값 설정
			pstmt.setString(1,iVO.getAdminAd());
			pstmt.setInt(2,iVO.getInquiryId());
						
			//쿼리문 수행 후 결과 얻기
			rowCnt=pstmt.executeUpdate();
			
		}finally {
			dbCon.dbClose(null, pstmt, con);
		}//end finally
		
		
		return rowCnt;
	}//updateBoard
	
	
	
	

}// class
