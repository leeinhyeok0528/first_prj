package review;

import java.util.Date;

public class ReviewVO {
	private int reviewId, productId, rating;
	private String productName, userId, content;
	private Date createAt;


public ReviewVO() {
	
}


public ReviewVO(int reviewId, int productId, int rating, String productName, String userId, String content,
		Date createAt) {
	super();
	this.reviewId = reviewId;
	this.productId = productId;
	this.rating = rating;
	this.productName = productName;
	this.userId = userId;
	this.content = content;
	this.createAt = createAt;
}


public int getReviewId() {
	return reviewId;
}


public void setReviewId(int reviewId) {
	this.reviewId = reviewId;
}


public int getProductId() {
	return productId;
}


public void setProductId(int productId) {
	this.productId = productId;
}


public int getRating() {
	return rating;
}


public void setRating(int rating) {
	this.rating = rating;
}


public String getProductName() {
	return productName;
}


public void setProductName(String productName) {
	this.productName = productName;
}


public String getUserId() {
	return userId;
}


public void setUserId(String userId) {
	this.userId = userId;
}


public String getContent() {
	return content;
}


public void setContent(String content) {
	this.content = content;
}


public Date getCreateAt() {
	return createAt;
}


public void setCreateAt(Date createAt) {
	this.createAt = createAt;
}


@Override
public String toString() {
	return "ReviewVO [reviewId=" + reviewId + ", productId=" + productId + ", rating=" + rating + ", productName="
			+ productName + ", userId=" + userId + ", content=" + content + ", createAt=" + createAt + "]";
}






}