package com.app.pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Otp {

	private Integer otpid;
	private int otp;
	
	public Otp() {
	System.out.println("in otp pojo");
	}

	public Otp(int otp) {
	
		this.otp = otp;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getOtpid() 
	{
		return otpid;
	}

	public void setOtpid(Integer otpid) {
		this.otpid = otpid;
	}
	
	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "Otp [otpid=" + otpid + ", otp=" + otp + "]";
	}

	
	
}