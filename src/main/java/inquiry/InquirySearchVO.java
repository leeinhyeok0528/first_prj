package inquiry;

import java.util.Date;

public class InquirySearchVO {
private Date startDate,endDate;
private String category;
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
@Override
public String toString() {
	return "InquirySearchVO [startDate=" + startDate + ", endDate=" + endDate + ", category=" + category + "]";
}


	
	
}

