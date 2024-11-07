package inquiry;

import java.util.Date;

public class InquiryVO {
//,제목,처리상태,등록일 작성자
	private int inquiryId;
	private String category, title, content, status, userId, adminAd;
	private Date createAt;

	public InquiryVO() {

	}

	public InquiryVO(int inquiryId, String category, String title, String content, String status, String userId,
			String adminAd, Date createAt) {
		super();
		this.inquiryId = inquiryId;
		this.category = category;
		this.title = title;
		this.content = content;
		this.status = status;
		this.userId = userId;
		this.adminAd = adminAd;
		this.createAt = createAt;
	}

	public int getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAdminAd() {
		return adminAd;
	}

	public void setAdminAd(String adminAd) {
		this.adminAd = adminAd;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "InquiryVO [inquiryId=" + inquiryId + ", category=" + category + ", title=" + title + ", content="
				+ content + ", status=" + status + ", userId=" + userId + ", adminAd=" + adminAd + ", createAt="
				+ createAt + "]";
	}




}