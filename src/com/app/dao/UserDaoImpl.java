package com.app.dao;

import java.util.Random;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.Otp;
import com.app.pojos.User;

@Service
@Transactional
public class UserDaoImpl implements IUserDao {

	@Autowired
	private SessionFactory sf;
	@Override
	public Integer registerUser(User user) {
		
		return (Integer)sf.getCurrentSession().save(user);
	}
	@Override
	public User login(User user) {
	String jpql="select u from User u where u.email=:email and u.password=:password";
	return sf.getCurrentSession().createQuery(jpql,User.class).setParameter("email",user.getEmail()).setParameter("password",user.getPassword()).getSingleResult();
	}
	@Override
	public User findByEmail(User user) {
		String jpql="select u from User u where u.email=:em";
		return sf.getCurrentSession().createQuery(jpql,User.class).setParameter("em",user.getEmail()).getSingleResult();
	}
	@Override
	public int generateOtp() {
		Random random = new Random();
		int num = random.nextInt(99999) + 99999;
		if (num < 100000 || num > 999999) 
		{
			num = random.nextInt(99999) + 99999;
			if (num < 100000 || num > 999999)
			{
				System.out.println("Unable to generate OTP at this time..");
			}
		}
		return num;
	
	}
	@Override
	public void saveOtp(Otp otp) {
		
		sf.getCurrentSession().save(otp);
	}
	@Override
	public Otp getOtp() {
		
		return sf.getCurrentSession().createQuery("select o from Otp o",Otp.class).getSingleResult();
	}
	@Override
	public void deleteOtp() {
		// TODO Auto-generated method stub
		Otp o=sf.getCurrentSession().createQuery("select o from Otp o",Otp.class).getSingleResult();
	    sf.getCurrentSession().delete(o);
	}
	@Override
	public void resetPassword(User user) {
		System.out.println(user.getEmail());
		String str = "select u from User u where u.email=:em";
		User u = sf.getCurrentSession().createQuery(str, User.class).setParameter("em", user.getEmail())
				.getSingleResult();
		System.out.println(u);
		u.setPassword(user.getPassword());
		// otp.setOtp(0);
		sf.getCurrentSession().save(u);
	}
	@Override
	public User getUserByid(int userid) {
		System.out.println("inside dao  " + userid);
		return sf.getCurrentSession().get(User.class,userid);
	}
	@Override
	public User findByEmailadd(String email) {
		System.out.println(email);
		String str = "select u from User u where u.email=:em";
		User u = sf.getCurrentSession().createQuery(str, User.class).setParameter("em",email)
				.getSingleResult();
		System.out.println(u);
		return u;
	}
	@Override
	public Integer updateuser(int userid, User u) {
		User user=sf.getCurrentSession().get(User.class, userid);
		user.setUserName(u.getUserName());
		user.setEmail(u.getEmail());
		user.setContact(u.getContact());
		return 1;
	}

}
