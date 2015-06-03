package com.aloha.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileImage {
	public byte []img;
	public long size;
	public int user_id;
	public int img_id;
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] image) {
		this.img = image;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long l) {
		this.size = l;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	public void writeToResources() throws IOException {
		FileOutputStream fos = null;
		try {
 			fos = new FileOutputStream("//resources//userimages"+img_id+".jpeg");
			fos.write(img);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{

			fos.close();
		}
	}
}
