package kr.co.sist.notice;

public class NoticeVO {
 private int noticeId,categolyId, inquiry_id;
 private String title, createAt,content,admin_Id,status;

 
 public NoticeVO() {
}
 
public NoticeVO(int noticeId, int categolyId, int inquiry_id, String title, String createAt, String content,
		String admin_Id, String status) {
	this.noticeId = noticeId;
	this.categolyId = categolyId;
	this.inquiry_id = inquiry_id;
	this.title = title;
	this.createAt = createAt;
	this.content = content;
	this.admin_Id = admin_Id;
	this.status = status;
}

public int getNoticeId() {
	return noticeId;
}
public void setNoticeId(int noticeId) {
	this.noticeId = noticeId;
}
public int getCategolyId() {
	return categolyId;
}
public void setCategolyId(int categolyId) {
	this.categolyId = categolyId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getCreateAt() {
	return createAt;
}
public void setCreateAt(String createAt) {
	this.createAt = createAt;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getAdmin_Id() {
	return admin_Id;
}
public void setAdmin_Id(String admin_Id) {
	this.admin_Id = admin_Id;
}

public int getInquiry_id() {
	return inquiry_id;
}

public void setInquiry_id(int inquiry_id) {
	this.inquiry_id = inquiry_id;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

@Override
public String toString() {
	return "NoticeVO [noticeId=" + noticeId + ", categolyId=" + categolyId + ", inquiry_id=" + inquiry_id + ", title="
			+ title + ", createAt=" + createAt + ", content=" + content + ", admin_Id=" + admin_Id + ", status="
			+ status + "]";
}


 
 
 
}//calss
