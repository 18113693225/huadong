package com.demo.app.bean;


/**
 * 注册用户bean
 * @author Yong Ren
 *
 */
public class UserBean extends DateBean {
	//注册类别  1:终端用户  2：加盟商用户 3:电工用户  4：E推广用户  5：普通用户
	private int user_type ;
	//短信验证码code
	private String sms_code = "";
	//用户姓名
	private String user_name = "";
	//登录密码
	private String password ;
	//性别
	private char sex = '0' ;
	//身份证
	private String card_id = "";
	//专业
	private String professional = "";
	//证书
	private String coa = "" ;
	//证书编号
	private String coa_number = "";
	//电话
	private String phone = "";
	//地址
	private String address = "";
	//开卡银行
	private String card_bank = "";
	//银行卡号
	private String card_number = ""  ;
	//公司名称
	private String company_name = "";
	//执照编号
	private String license_number = "";
	//公司地址
	private String company_address = "" ;
	//类别
	private String category = "" ;
	//电压等级
	private String voltage_level = "" ;
	//规模/容量
	private int scale = 0;
	//台数/长度
	private int number = 0 ;
	//行业类别
	private String industry_categories = "";
	//合作意向
	private String cooperation_intention ="";
	//加盟区域
	private String joining_region = "" ;
	//联系人
	private String contacts = "" ;
	//注册资金
	private int registered_capital  ;
	
	//公司/个人  0：公司  1：个人
	private int company_or_personal ;
	
	//注册步骤  1：第一步  2：第二步
	private int regest_setup = 1 ;
	
	//推荐码  6位  0-9  a-z   A-Z
	private String referral_code = "";
	
	//联系方式
	private String contact_information = "";
	
	//注册信息中的其他信息
	
	private String other_info_a = "";
	private String other_info_b = "";
	private String other_info_c = "";
	private String other_info_d = "";
	
	
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getProfessional() {
		return professional;
	}
	public void setProfessional(String professional) {
		this.professional = professional;
	}
	public String getCoa() {
		return coa;
	}
	public void setCoa(String coa) {
		this.coa = coa;
	}
	public String getCoa_number() {
		return coa_number;
	}
	public void setCoa_number(String coa_number) {
		this.coa_number = coa_number;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCard_bank() {
		return card_bank;
	}
	public void setCard_bank(String card_bank) {
		this.card_bank = card_bank;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getLicense_number() {
		return license_number;
	}
	public void setLicense_number(String license_number) {
		this.license_number = license_number;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getVoltage_level() {
		return voltage_level;
	}
	public void setVoltage_level(String voltage_level) {
		this.voltage_level = voltage_level;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getIndustry_categories() {
		return industry_categories;
	}
	public void setIndustry_categories(String industry_categories) {
		this.industry_categories = industry_categories;
	}
	public String getCooperation_intention() {
		return cooperation_intention;
	}
	public void setCooperation_intention(String cooperation_intention) {
		this.cooperation_intention = cooperation_intention;
	}
	public String getJoining_region() {
		return joining_region;
	}
	public void setJoining_region(String joining_region) {
		this.joining_region = joining_region;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public int getRegistered_capital() {
		return registered_capital;
	}
	public void setRegistered_capital(int registered_capital) {
		this.registered_capital = registered_capital;
	}
	public int getUser_type() {
		return user_type;
	}
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getCompany_or_personal() {
		return company_or_personal;
	}
	public void setCompany_or_personal(int company_or_personal) {
		this.company_or_personal = company_or_personal;
	}
	public String getSms_code() {
		return sms_code;
	}
	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}
	public int getRegest_setup() {
		return regest_setup;
	}
	public void setRegest_setup(int regest_setup) {
		this.regest_setup = regest_setup;
	}
	public String getReferral_code() {
		return referral_code;
	}
	public void setReferral_code(String referral_code) {
		this.referral_code = referral_code;
	}
	public String getContact_information() {
		return contact_information;
	}
	public void setContact_information(String contact_information) {
		this.contact_information = contact_information;
	}
	public String getOther_info_a() {
		return other_info_a;
	}
	public void setOther_info_a(String other_info_a) {
		this.other_info_a = other_info_a;
	}
	public String getOther_info_b() {
		return other_info_b;
	}
	public void setOther_info_b(String other_info_b) {
		this.other_info_b = other_info_b;
	}
	public String getOther_info_c() {
		return other_info_c;
	}
	public void setOther_info_c(String other_info_c) {
		this.other_info_c = other_info_c;
	}
	public String getOther_info_d() {
		return other_info_d;
	}
	public void setOther_info_d(String other_info_d) {
		this.other_info_d = other_info_d;
	}
	
}
