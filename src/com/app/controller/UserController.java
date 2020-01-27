package com.app.controller;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IUserDao;
import com.app.pojos.Otp;
import com.app.pojos.RoomDetails;
import com.app.pojos.User;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserDao iuserDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@PostMapping("/register")
	public Integer register(@RequestBody User user)
	{
		System.out.println(user);
		
		if(iuserDao.registerUser(user)!=0)
		{
			String msg="Your email id is = "+user.getEmail()+"and password is"+user.getPassword();
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(user.getEmail());
			mailMessage.setSubject("Registration Mail");
			mailMessage.setText(msg);
			
			try
			{
				mailSender.send(mailMessage);
			}
			catch (MailException e) 
			{
				System.out.println("inside mail exception");
				e.printStackTrace();
		}
			return 1;
		}
		else 
		{
			System.out.println("error sending mail");
			return 0;
		}
	}
	@PostMapping("/forgot")
	public Integer forgotPassword(@RequestBody User user)
	{

		user = iuserDao.findByEmail(user);
		System.out.println(user);
		try
		{		System.out.println(user);
			if(user !=null)
			{
				Otp otp=new Otp();
				otp.setOtp(iuserDao.generateOtp());
				iuserDao.saveOtp(otp);
				String msg="Your one time password for forgot password is = "+otp;
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("One Time Password");
				mailMessage.setText(msg);
				try
				{
					mailSender.send(mailMessage);
				}
				catch (MailException e) 
	            {
					System.out.println("inside mail exception");
					e.printStackTrace();
				}
				return 1;
			}
		} catch (NoResultException e) 
		{
			System.out.println("in the exception");
			e.printStackTrace();
		}
		return 0;
	}
	@PostMapping("/confirmOtp")
	public boolean confirmOtp(@RequestBody Otp otp)
	{
		Otp o=iuserDao.getOtp();
		System.out.println(otp.getOtp());
		System.out.println(o.getOtp());
		if(otp.getOtp()==o.getOtp())
		{
			iuserDao.deleteOtp();
			return true;
		}
		else
		{
			System.out.println("in false");
			return false;
		}
	}
	@PostMapping("/resetpassword")
	public boolean resetPassword(@RequestBody User user)
	{	System.out.println(user.getPassword());
	System.out.println(user);
		iuserDao.resetPassword(user);
	
		return true;
	}

	@PostMapping("/login")
	public User login(@RequestBody User user) {
		System.out.println(user);
		User u=iuserDao.login(user);
		System.out.println(u);
		return u;
		
	}
	@GetMapping("/getuserbyid/{userid}")
	public User getUserByid(@PathVariable int userid)
	{
		return iuserDao.getUserByid(userid);
	}
	@PutMapping("/update/{userid}")
	public ResponseEntity<?> updateRoom(@PathVariable int userid, @RequestBody User u) {
		try {
			System.out.println(u);
			if (u == null)
				throw new RuntimeException("User id invalid");
			else {
				return new ResponseEntity<Integer>(iuserDao.updateuser(userid, u), HttpStatus.OK);
			}
		} catch (RuntimeException e1) {
			e1.printStackTrace();
			return new ResponseEntity<String>(e1.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}