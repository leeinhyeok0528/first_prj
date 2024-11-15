package kr.co.sist.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kr.co.sist.dao.DbConnection;
import kr.co.sist.user.AdminMemberVO;

public class MangeNoticeDAO {
	
	private static MangeNoticeDAO mgDAO;
	
	private MangeNoticeDAO() {}
	
	public static MangeNoticeDAO getInstance() { // 접근 제어자 수정
		if (mgDAO == null) {
			mgDAO = new MangeNoticeDAO();
		}
		return mgDAO;
	}

	public int insertNotice(NoticeVO nVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();
		NoticeCategoryVO ncVO = new NoticeCategoryVO();
		
		try {
			con = dbCon.getConn();
			String insertBoard = "INSERT INTO notice (category, title, content) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(insertBoard);

			// 사용자 입력을 통해 받은 값을 바인딩
			pstmt.setString(1, ncVO.getCategory());
			pstmt.setString(2, nVO.getTitle());
			pstmt.setString(3, nVO.getContent());

			return pstmt.executeUpdate();
		} finally {
			dbCon.dbClose(null, pstmt, con);
		}
	}

	public NoticeVO selectOneNotice(int noticeId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();
		NoticeVO nVO = new NoticeVO();
		NoticeCategoryVO ncVO=new NoticeCategoryVO();
		
		try {
			con = dbCon.getConn();
			String query = "SELECT inquiry_id, category, title, content, createAt FROM notice WHERE inquiry_id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				nVO.setInquiry_id(rs.getInt("inquiry_id"));
				ncVO.setCategory(rs.getString("category"));
				nVO.setTitle(rs.getString("title"));
				nVO.setContent(rs.getString("content"));
				nVO.setCreateAt(rs.getString("createAt"));
			}
		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		return nVO;
	}

	public List<NoticeVO> selectAllNotice() throws SQLException {
		List<NoticeVO> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();
		NoticeCategoryVO ncVO=new NoticeCategoryVO();
		
		try {
			con = dbCon.getConn();
			String query = "SELECT inquiry_id, category, title, admin_id, createAt FROM notice";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeVO nVO = new NoticeVO();
				nVO.setInquiry_id(rs.getInt("inquiry_id"));
				ncVO.setCategory(rs.getString("category"));
				nVO.setTitle(rs.getString("title"));
				nVO.setAdmin_Id(rs.getString("admin_id"));
				nVO.setCreateAt(rs.getString("createAt"));
				list.add(nVO);
			}
		} finally {
			dbCon.dbClose(rs, pstmt, con);
		}
		return list;
	}

	public int updateOneNotice(NoticeVO nVO) throws SQLException {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();
		NoticeCategoryVO ncVO = new NoticeCategoryVO();
		
		try {
			con = dbCon.getConn();
			String updateMember = "UPDATE notice SET category = ?, title = ?, content = ? WHERE inquiry_id = ?";
			pstmt = con.prepareStatement(updateMember);
			pstmt.setString(1, ncVO.getCategory());
			pstmt.setString(2, nVO.getTitle());
			pstmt.setString(3, nVO.getContent());
			pstmt.setInt(4, nVO.getInquiry_id());
			result = pstmt.executeUpdate();
		} finally {
			dbCon.dbClose(null, pstmt, con);
		}
		return result;
	}

	public int deleteNotice(int noticeId) throws SQLException {
		int result = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
			String sql = "DELETE FROM notice WHERE inquiry_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, noticeId);
			result = pstmt.executeUpdate();
		} finally {
			dbCon.dbClose(null, pstmt, con);
		}
		return result;
	}
	public int selectTotalCount( AdminMemberVO amVO)throws SQLException{
		int totalCount=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		//1.JNDI 사용객체 생성
		//2.DBCP에서 DataSource 얻기
		
		try {
		//3.Connection얻기
			con=dbCon.getConn();
		//4.쿼리문생성객체 얻기
			StringBuilder selectCount=new StringBuilder();
			selectCount
			.append("select count(notice_Id) cnt from notice ");
			
			//dynamic query : 검색 키워드를 판단 기준으로 where절이 동적생성되어야한다.
			if( amVO.getKeyword() != null && !"".equals(amVO.getKeyword()) ) {
				selectCount.append(" where instr(")
				.append( BoardUtil.numToField( amVO.getField() ) )
				.append(",?) != 0");
			}//end if
			
			pstmt=con.prepareStatement(selectCount.toString());
		//5.바인드 변수에 값 설정
			if( amVO.getKeyword() != null && !"".equals(amVO.getKeyword()) ) {
				pstmt.setString(1, amVO.getKeyword());
			}//end if
			
		//6.쿼리문 수행후 결과 얻기
			rs=pstmt.executeQuery();
			if(rs.next()) {
				totalCount=rs.getInt("cnt");
			}//end if
		}finally {
		//7.연결 끊기
			dbCon.dbClose(rs, pstmt, con);
		}//end finally
		return totalCount;
	}//selectTotalCount
	
	public List<NoticeVO> selectNotice( AdminMemberVO amVO )throws SQLException{
		List<NoticeVO> list=new ArrayList<NoticeVO>();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		try {
			//connection얻기
			con=dbCon.getConn();
			//쿼리문 생성객체 얻기
			StringBuilder selectNotice=new StringBuilder();
			selectNotice
			.append("	select  noticeId,categolyId, title,admin_Id,createAt	")
			.append("	from	(select noticeId,categolyId, title,admin_Id,createAt,	")
			.append("	row_number() over( order by input_date desc) rnum	")
			.append("	from notice	");
			
			//dynamic query : 검색 키워드를 판단 기준으로 where절이 동적생성되어야한다.
			if( amVO.getKeyword() != null && !"".equals(amVO.getKeyword()) ) {
				selectNotice.append(" where instr(")
				.append( BoardUtil.numToField( amVO.getField()) )
				.append(",?) != 0");
			}//end if
			
			selectNotice.append("	)where rnum between ? and ?	");
			
			pstmt=con.prepareStatement(selectNotice.toString());
			//바인드 변수에 값 설정
			int bindInd=0;
			if( amVO.getKeyword() != null && !"".equals(amVO.getKeyword()) ) {
				pstmt.setString( ++bindInd, amVO.getKeyword());
			}//end if
			pstmt.setInt(++bindInd, amVO.getStartNum());
			pstmt.setInt(++bindInd, amVO.getEndNum());
						
			//쿼리문 수행 후 결과 얻기
			rs=pstmt.executeQuery();
			
			NoticeVO nVO=null;
			while( rs.next() ) {
				nVO=new NoticeVO();
				nVO.setNoticeId(rs.getInt("noticeId"));
				nVO.setCategolyId(rs.getInt("categolyId"));
				nVO.setTitle(rs.getString("title"));
				nVO.setAdmin_Id(rs.getString("admin_Id"));
				nVO.setCreateAt(rs.getString("createAt"));
				
				list.add(nVO);
			}//end while
			
			
		}finally {
			dbCon.dbClose(rs, pstmt, con);
		}//end finally
		
		return list;
	}//selectBoard
}
