package kr.co.sist.notice;

import kr.co.sist.user.AdminMemberVO;

public class BoardUtil {
    private static String[] columnName = {"subject", "content", "writer"};

    // numToField 메서드: 숫자에 해당하는 필드명을 반환
    public static String numToField(String fieldNum) {
        return columnName[Integer.parseInt(fieldNum)];
    }

    /**
     * 페이지네이션을 생성하는 메서드
     * @param amVO - AdminMemberVO 객체, 페이징 및 검색 관련 정보 포함
     * @return String - 페이지네이션 HTML 코드
     */
    public String pagination(AdminMemberVO amVO) {
        StringBuilder pagination = new StringBuilder();

        if (amVO.getTotalCount() != 0) {
            // 1. 한 화면에 보여줄 페이지 인덱스 수 [1][2][3]
            int pageNumber = 3;
            // 2. 화면에 보여줄 시작 페이지 번호 (1, 2, 3 -> 1, 4, 5, 6 -> 4, 7, 8, 9 -> 7)
            int startPage = ((amVO.getCurrentPage() - 1) / pageNumber) * pageNumber + 1;
            // 3. 화면에 보여줄 마지막 페이지 번호
            int endPage = startPage + pageNumber - 1;
            // 4. 총 페이지 수가 연산된 마지막 페이지 수보다 작다면 총 페이지수가 마지막 페이지수로 설정
            if (amVO.getTotalPage() <= endPage) {
                endPage = amVO.getTotalPage();
            }

            // 5. 첫 페이지가 인덱스 화면이 아닌 경우 (3보다 큰 경우)
            int movePage = 0;
            StringBuilder prevMark = new StringBuilder();
            prevMark.append("[ &lt;&lt; ]");

            // 이전 페이지로 이동
            if (amVO.getCurrentPage() > pageNumber) {
                prevMark.delete(0, prevMark.length());
                movePage = startPage - 1; // 예: 4, 5, 6 -> 1, 7, 8, 9 -> 4
                prevMark.append("[ <a href=\"")
                        .append(amVO.getUrl())
                        .append("?currentPage=")
                        .append(movePage);
                // 검색 키워드가 존재할 때
                if (amVO.getKeyword() != null && !"".equals(amVO.getKeyword())) {
                    prevMark.append("&field=").append(amVO.getField())
                            .append("&keyword=").append(amVO.getKeyword());
                }
                prevMark.append("\">&lt;&lt;</a> ]");
            }

            prevMark.append(" ... ");
            pagination.append(prevMark);

            // 페이지 번호 링크 생성
            movePage = startPage;
            StringBuilder pageLink = new StringBuilder();

            while (movePage <= endPage) {
                if (movePage == amVO.getCurrentPage()) {
                    // 현재 페이지는 링크를 설정하지 않는다.
                    pageLink.append("[ ").append(movePage).append(" ]");
                } else {
                    // 현재 페이지가 아니면 링크를 설정한다.
                    pageLink.append("[ <a href='")
                            .append(amVO.getUrl())
                            .append("?currentPage=")
                            .append(movePage);

                    // 검색 키워드가 존재할 때
                    if (amVO.getKeyword() != null && !"".equals(amVO.getKeyword())) {
                        pageLink.append("&field=").append(amVO.getField())
                                .append("&keyword=").append(amVO.getKeyword());
                    }
                    pageLink.append("'>").append(movePage).append("</a> ]");
                }
                movePage++;
            }

            pagination.append(pageLink);
            pagination.append(" ... ");

            // 7. 뒤에 페이지가 더 있는 경우
            StringBuilder nextMark = new StringBuilder();
            nextMark.append("[ &gt;&gt; ]");

            if (amVO.getTotalPage() > endPage) {
                nextMark.delete(0, nextMark.length());
                movePage = endPage + 1;

                nextMark.append("[ <a href='")
                        .append(amVO.getUrl()).append("?currentPage=").append(movePage);

                // 검색 키워드가 존재할 때
                if (amVO.getKeyword() != null && !"".equals(amVO.getKeyword())) {
                    nextMark.append("&field=").append(amVO.getField())
                            .append("&keyword=").append(amVO.getKeyword());
                }

                nextMark.append("'> &gt;&gt;</a> ]");
            }

            pagination.append(nextMark);
        }

        return pagination.toString();
    }
}
