package review;

public class ReviewVO {
private int reviewId,prdNum,rating;
private String prdName,userId,content,createdAt;
public int getReviewId() {
	return reviewId;
}
public void setReviewId(int reviewId) {
	this.reviewId = reviewId;
}
public int getPrdNum() {
	return prdNum;
}
public void setPrdNum(int prdNum) {
	this.prdNum = prdNum;
}
public int getRating() {
	return rating;
}
public void setRating(int rating) {
	this.rating = rating;
}
public String getPrdName() {
	return prdName;
}
public void setPrdName(String prdName) {
	this.prdName = prdName;
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
public String getCreatedAt() {
	return createdAt;
}
public void setCreatedAt(String createdAt) {
	this.createdAt = createdAt;
}
@Override
public String toString() {
	return "ReviewVO [reviewId=" + reviewId + ", prdNum=" + prdNum + ", rating=" + rating + ", prdName=" + prdName
			+ ", userId=" + userId + ", content=" + content + ", createdAt=" + createdAt + "]";
}



	
	
	
	
}//class
