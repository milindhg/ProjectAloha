package com.aloha.common.entities.user;

public class UserEducation{
	int ed_id;
	String school;
	String area;
	public void setEducation(String school, String area){
		this.school = school;
		this.area = area;
	}
	public void setSchool(String school){
		this.school= school;
	}
	public void setArea(String area){
		this.area = area;
	}
	public String getSchool(){
		return school;
	}
	public String getArea(){
		return area;
	}
	public int getID()
	{
		return ed_id;
	}
	public void setID(int id)
	{
		this.ed_id = id;
	}
}
