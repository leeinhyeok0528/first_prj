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
import kr.co.sist.util.BoardUtil;

public class AdminInquiryDAO {
	private static AdminInquiryDAO aiDAO;
	
	private AdminInquiryDAO() {
		
		
	}//constructor
	
	
	/**
	 * 총 게시물의 s수 검색
	 * @param sVO
	 * @return 게시물의 수
	 * @throws SQLException
	 */
	/*
	 * public int selectTotalCount( SearchVO sVO )throws SQLException{ int
	 * totalCount=0;
	 * 
	 * Connection con=null; PreparedStatement pstmt=null; ResultSet rs=null;
	 * 
	 * DbConnection dbCon=DbConnection.getInstance(); //1.JNDI 사용객체 생성 //2.DBCP에서
	 * DataSource 얻기
	 * 
	 * try { //3.Connection얻기 con=dbCon.getConn(); //4.쿼리문생성객체 얻기 StringBuilder
	 * selectCount=new StringBuilder(); selectCount
	 * .append("select count(num) cnt from board ");
	 * 
	 * //dynamic query : 검색 키워드를 판단 기준으로 where절이 동적생성되어야한다. if( sVO.getKeyword() !=
	 * null && !"".equals(sVO.getKeyword()) ) { selectCount.append(" where instr(")
	 * .append(BoardUtil.numToField(sVO.getField())) .append(",?) != 0"); }//end if
	 * 
	 * pstmt=con.prepareStatement(selectCount.toString()); //5.바인드 변수에 값 설정 if(
	 * sVO.getKeyword() != null && !"".equals(sVO.getKeyword()) ) {
	 * pstmt.setString(1, sVO.getKeyword()); }//end if
	 * 
	 * //6.쿼리문 수행후 결과 얻기 rs=pstmt.executeQuery(); if(rs.next()) {
	 * totalCount=rs.getInt("cnt"); }//end if }finally { //7.연결 끊기 dbCon.dbClose(rs,
	 * pstmt, con); }//end finally return totalCount; }//selectTotalCount
	 */
	
	public static AdminInquiryDAO getInstance() {
		if(aiDAO==null) {
			aiDAO=new AdminInquiryDAO();
			
		}//end if
		return aiDAO;
		
	}//getInstance
	

	/**
	 * 문의사항에 대한 정보를 받아서 하나의 문의사항을 선택하여 상세정보를 보는 일.
	 * @param num
	 * @return 문의사항 정보를 담은iVO
	 */
	public InquiryVO selectOneInquiry(int num)throws SQLException{
		InquiryVO iVO =null;

		//db작업 수행하기
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		DbConnection dbCon=DbConnection.getInstance();
		
		try {
			//커넥션얻고
			con= dbCon.getConn();
			//쿼리문 생성객체 얻기
			StringBuilder selectBoard=new StringBuilder();
			selectBoard
			.append("	select *	")
			.append("    from inquiry    ")
			.append("	where num=?	");
			
			//쿼리문 수행
			pstmt= con.prepareStatement(selectBoard.toString());
			//바인드 변수에 값 설정하기
			pstmt.setInt(1, num);
			
			rs=pstmt.executeQuery();
			if( rs.next() ) {
				 
				iVO = new InquiryVO();
				iVO.setInquiryId(num);
				iVO.setUserId(rs.getString("userId"));
				//iVO.setAdminAd(rs.getString("admin_ad"));
				iVO.setCategory(rs.getString("category"));
				iVO.setTitle(rs.getString("title"));
				iVO.setCreateAt(rs.getDate("create_at"));;
				iVO.setStatus(rs.getString("status"));
				
				//CLOB데이터를 읽어들이기 위해서 별도의 stream을 연결한다.
				BufferedReader br=new BufferedReader(
						rs.getClob("content").getCharacterStream());
				
				StringBuilder content=new StringBuilder();
				String temp;//한줄 읽어들인 데이터를 저장할 변수
				try {
					while( (temp=br.readLine()) != null) {
						//한줄 읽어들여 content에 저장
						content.append(temp).append("\n");
					}//end while
					//모든 줄을 읽어들여 저장한 변수를 BoardVO객체에 할당한다.
					iVO.setContent(content.toString());
					
				}catch(IOException ie) {
					ie.printStackTrace();
				}//end catch
			}//end if
			
		}finally { //db연결 끊기.
			dbCon.dbClose(rs, pstmt, con);
		}//end finally
		
		
		return iVO;
	}// selectOneInquiry

	/**
	 * 문의에 대한 정보를 받아 모든 문의를 검색하는 일.
	 * @param iVO
	 * @return
	 */
	/*
	 * public List<InquirySearchVO> selectAllInquiry(InquirySearchVO isVO) {
	 * List<InquirySearchVO> list = new ArrayList<InquirySearchVO>(); Connection
	 * con=null; PreparedStatement pstmt=null; ResultSet rs=null;
	 * 
	 * DbConnection dbCon=DbConnection.getInstance();
	 * 
	 * try { //connection얻기 con=dbCon.getConn(); //쿼리문 생성객체 얻기 StringBuilder
	 * selectBoard=new StringBuilder(); selectBoard
	 * .append("	select num,subject, writer,input_date,ip	")
	 * .append("	from	(select num,subject, writer,input_date,ip,	")
	 * .append("	row_number() over( order by input_date desc) rnum	")
	 * .append("	from board	");
	 * 
	 * //dynamic query : 검색 키워드를 판단 기준으로 where절이 동적생성되어야한다. if( sVO.getKeyword() !=
	 * null && !"".equals(sVO.getKeyword()) ) { selectBoard.append(" where instr(")
	 * .append(BoardUtil.numToField(sVO.getField())) .append(",?) != 0"); }//end if
	 * 
	 * selectBoard.append("	)where rnum between ? and ?	");
	 * 
	 * System.out.println( selectBoard );
	 * 
	 * pstmt=con.prepareStatement(selectBoard.toString()); //바인드 변수에 값 설정 int
	 * bindInd=0; if( sVO.getKeyword() != null && !"".equals(sVO.getKeyword()) ) {
	 * pstmt.setString( ++bindInd, sVO.getKeyword()); }//end if
	 * pstmt.setInt(++bindInd, sVO.getStartNum()); pstmt.setInt(++bindInd,
	 * sVO.getEndNum());
	 * 
	 * //쿼리문 수행 후 결과 얻기 rs=pstmt.executeQuery();
	 * 
	 * BoardVO bVO=null; while( rs.next() ) { bVO=new BoardVO(); bVO.setNum(
	 * rs.getInt("num")); bVO.setSubject(rs.getString("subject"));
	 * bVO.setWriter(rs.getString("writer"));
	 * bVO.setInput_date(rs.getDate("input_date")); bVO.setIp(rs.getString("ip"));
	 * 
	 * list.add(bVO); }//end while
	 * 
	 * 
	 * }finally { dbCon.dbClose(rs, pstmt, con); }//end finally return list; }//
	 * selectAllInquiry
	 */
	
	
	/**
	 * 사용자의 문의에 관리자의 답변과 상태를 업데이트하는 메서드
	 * @param iVO 업데이트할 문의 정보를 담고 있는 InquiryVO 객체
	 * @return 업데이트된 행의 개수
	 * @throws SQLException 데이터베이스 오류 발생 시
	 */
	
	
	/**
	 * 모든 문의사항을 조회하는 메서드
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
	        
	        
	/*
	* SELECT i.CREATE_AT, i.STATUS , i.CATEGORY , o.ORDER_ID , i.TITLE , p.PRODUCT_ID , p.NAME
	*  FROM INQUIRY i
	*   JOIN ORDERS o ON i.USER_ID = o.USER_ID
	 * JOIN ORDERED_PRODUCT op ON o.ORDER_ID = op.ORDER_ID 
	 * JOIN PRODUCTS p ON op.PRODUCT_ID = p.PRODUCT_ID ;
		*/
	        selectQuery
	        .append("SELECT i.CREATE_AT, i.STATUS, i.CATEGORY, o.ORDER_ID, i.TITLE, p.PRODUCT_ID, p.NAME ")
	        .append("FROM INQUIRY i ")
	        .append("JOIN ORDERS o ON i.USER_ID = o.USER_ID ")
	        .append("JOIN ORDERED_PRODUCT op ON o.ORDER_ID = op.ORDER_ID ")
	        .append("JOIN PRODUCTS p ON op.PRODUCT_ID = p.PRODUCT_ID");

	    pstmt = con.prepareStatement(selectQuery.toString());

	    // 쿼리 실행
	    rs = pstmt.executeQuery();

	    // 결과를 InquiryVO 객체에 저장
	    while (rs.next()) {
	        InquiryVO iVO = new InquiryVO();
	        
	        // 매핑 작업
	        iVO.setCreateAt(rs.getTimestamp("CREATE_AT"));  // TIMESTAMP 형식을 Date로 변환하여 설정
	        iVO.setStatus(rs.getString("STATUS"));
	        iVO.setCategory(rs.getString("CATEGORY"));
	        iVO.setOrderId(rs.getInt("ORDER_ID"));
	        iVO.setTitle(rs.getString("TITLE"));
	        iVO.setProductId(rs.getInt("PRODUCT_ID"));
	        iVO.setName(rs.getString("NAME"));  // name을 String으로 변경하여 매핑

	        // 리스트에 추가
	        inquiryList.add(iVO);
	    }
	    } finally {
	        // 자원 해제
	        dbCon.dbClose(rs, pstmt, con);
	    }

	    return inquiryList; // 모든 문의사항 리스트 반환
	}

	
	
	
	
	
	public int updateHandleInquiry(InquiryVO iVO) throws SQLException {
	    int rowCount = 0;
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    DbConnection dbCon = DbConnection.getInstance();

	    try {
	        // 데이터베이스 연결
	        con = dbCon.getConn();

	        // SQL 업데이트 쿼리 작성
	        StringBuilder updateQuery = new StringBuilder();
	        updateQuery
	            .append("UPDATE inquiry ")
	            .append("SET status = ?, ")
	            .append("    admin_ad = ? ")
	            .append("WHERE inquiry_id = ?");

	        pstmt = con.prepareStatement(updateQuery.toString());

	        // 바인드 변수 설정
	        pstmt.setString(1, iVO.getStatus());       // 상태를 업데이트
	        pstmt.setString(2, iVO.getAdminAd());      // 관리자 답변을 업데이트
	        pstmt.setInt(3, iVO.getInquiryId());       // 특정 문의 ID에 대해 업데이트

	        // 쿼리문 실행 후 영향받은 행의 수를 반환
	        rowCount = pstmt.executeUpdate();
	    } finally {
	        // 자원 해제
	        dbCon.dbClose(null, pstmt, con);
	    }
	    return rowCount; // 업데이트된 행의 개수를 반환
	}

	
	
	
	
	

    /**
     * 특정 문의사항을 삭제하는 메서드
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
    }//deleteInquiry

}// class
