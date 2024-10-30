package inquiry;

import java.util.Date;

/**
 * 문의관리에서는 테이블을 조인하여(inquiry , products, ordered_product)사용하기에 VO데이터 양이 추가됨
 */
public class InquiryVO {

	private int inquiryId, orderId, productId;
	private String category, title, content, status, userId, adminAd,name;
	private Date createAt;

	public InquiryVO() {

	}

	public InquiryVO(int inquiryId, int orderId, int productId, String name, String category, String title, String content,
			String status, String userId, String adminAd, Date createAt) {
		super();
		this.inquiryId = inquiryId;
		this.orderId = orderId;
		this.productId = productId;
		this.category = category;
		this.title = title;
		this.content = content;
		this.status = status;
		this.userId = userId;
		this.adminAd = adminAd;
		this.createAt = createAt;
		this.name = name;
	}

	public int getInquiryId() {
		return inquiryId;
	}

	public void setInquiryId(int inquiryId) {
		this.inquiryId = inquiryId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int producdId) {
		this.productId = producdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return "InquiryVO [inquiryId=" + inquiryId + ", orderId=" + orderId + ", producdId=" + productId + ", name="
				+ name + ", category=" + category + ", title=" + title + ", content=" + content + ", status=" + status
				+ ", userId=" + userId + ", adminAd=" + adminAd + ", createAt=" + createAt + "]";
	}

}// class