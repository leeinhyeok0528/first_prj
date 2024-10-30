package inquiry;

import java.util.Date;

public class InquirySearchVO {
private Date startDate,endDate;
private String category;
private int startNum, endNum, currentPage, totalPage, totalCount;

private String field = "0", keyword, url;// 검색할 field에 대응되는 숫자, 검색 값,이동할 URL

public Date getStartDate() {
	return startDate;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public Date getEndDate() {
	return endDate;
}

public void setEndDate(Date endDate) {
	this.endDate = endDate;
}

public String getCategory() {
	return category;
}

public void setCategory(String category) {
	this.category = category;
}

public int getStartNum() {
	return startNum;
}

public void setStartNum(int startNum) {
	this.startNum = startNum;
}

public int getEndNum() {
	return endNum;
}

public void setEndNum(int endNum) {
	this.endNum = endNum;
}

public int getCurrentPage() {
	return currentPage;
}

public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
}

public int getTotalPage() {
	return totalPage;
}

public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
}

public int getTotalCount() {
	return totalCount;
}

public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
}

public String getField() {
	return field;
}

public void setField(String field) {
	this.field = field;
}

public String getKeyword() {
	return keyword;
}

public void setKeyword(String keyword) {
	this.keyword = keyword;
}

public String getUrl() {
	return url;
}

public void setUrl(String url) {
	this.url = url;
}

@Override
public String toString() {
	return "InquirySearchVO [startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + ", startNum="
			+ startNum + ", endNum=" + endNum + ", currentPage=" + currentPage + ", totalPage=" + totalPage
			+ ", totalCount=" + totalCount + ", field=" + field + ", keyword=" + keyword + ", url=" + url + "]";
}

// 검색을 위한시작번호,끝 번호,현재 페이지 번호, 총 페이지 수,총게시물의 수



	
	
}//class

