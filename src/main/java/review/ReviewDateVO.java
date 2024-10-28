package review;

import java.util.Date;

public class ReviewDateVO {
	private Date startDate, endDate;

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

	@Override
	public String toString() {
		return "ReviewDateVO [startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
