package kr.co.sist.user;

public class UserVO {
private int zipcode;
private String userId,password,name,email,phone,gender,address1,address2,birth,
securityQuestion_id,securityAnswer,joinDate,user_status_flag,question_id;
public int getZipcode() {
	return zipcode;
}
public void setZipcode(int zipcode) {
	this.zipcode = zipcode;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getAddress1() {
	return address1;
}
public void setAddress1(String address1) {
	this.address1 = address1;
}
public String getAddress2() {
	return address2;
}
public void setAddress2(String address2) {
	this.address2 = address2;
}
public String getBirth() {
	return birth;
}
public void setBirth(String birth) {
	this.birth = birth;
}
public String getSecurityQuestion_id() {
	return securityQuestion_id;
}
public void setSecurityQuestion_id(String securityQuestion_id) {
	this.securityQuestion_id = securityQuestion_id;
}
public String getSecurityAnswer() {
	return securityAnswer;
}
public void setSecurityAnswer(String securityAnswer) {
	this.securityAnswer = securityAnswer;
}
public String getJoinDate() {
	return joinDate;
}
public void setJoinDate(String joinDate) {
	this.joinDate = joinDate;
}
public String getUser_status_flag() {
	return user_status_flag;
}
public void setUser_status_flag(String user_status_flag) {
	this.user_status_flag = user_status_flag;
}
public String getQuestion_id() {
	return question_id;
}
public void setQuestion_id(String question_id) {
	this.question_id = question_id;
}
@Override
public String toString() {
	return "UserVO [zipcode=" + zipcode + ", userId=" + userId + ", password=" + password + ", name=" + name
			+ ", email=" + email + ", phone=" + phone + ", gender=" + gender + ", address1=" + address1 + ", address2="
			+ address2 + ", birth=" + birth + ", securityQuestion_id=" + securityQuestion_id + ", securityAnswer="
			+ securityAnswer + ", joinDate=" + joinDate + ", user_status_flag=" + user_status_flag + ", question_id="
			+ question_id + "]";
}


}
