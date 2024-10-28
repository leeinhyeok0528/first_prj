package inquiry;

public class InquiryVO {
private int inquiry_id,admin_ad,userid;
private String category,title,content,create_at,status;
public int getInquiry_id() {
	return inquiry_id;
}
public void setInquiry_id(int inquiry_id) {
	this.inquiry_id = inquiry_id;
}
public int getAdmin_ad() {
	return admin_ad;
}
public void setAdmin_ad(int admin_ad) {
	this.admin_ad = admin_ad;
}
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
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
public String getCreate_at() {
	return create_at;
}
public void setCreate_at(String create_at) {
	this.create_at = create_at;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
@Override
public String toString() {
	return "InquiryVO [inquiry_id=" + inquiry_id + ", admin_ad=" + admin_ad + ", userid=" + userid + ", category="
			+ category + ", title=" + title + ", content=" + content + ", create_at=" + create_at + ", status=" + status
			+ "]";
}


}//class
