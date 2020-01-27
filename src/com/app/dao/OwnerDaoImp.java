package com.app.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pojos.CustomerBooking;
import com.app.pojos.RoomDetails;
import com.app.pojos.RoomStatus;
import com.app.pojos.User;

import sun.awt.geom.AreaOp.EOWindOp;

@Service
@Transactional
public class OwnerDaoImp implements IOwnerDao {

	@Autowired
	private SessionFactory sf;
	@Autowired
	private IUserDao dao;
	
	@Override
	public Integer registerRoom(int userid,RoomDetails room) 
	{
		Integer roomid=(Integer) sf.getCurrentSession().save(room);
		RoomDetails rm=sf.getCurrentSession().get(RoomDetails.class, roomid);
		User u=dao.getUserByid(userid);
		rm.setUserid(u);
	
		return roomid;

	}

	@Override
	public Integer deleteRoomInfo(RoomDetails rm) {
		sf.getCurrentSession().delete(rm);
		return 1;
	}
	@Override
	public RoomDetails getRoomDetails(int roomid) {
		// TODO Auto-generated method stub
		return sf.getCurrentSession().get(RoomDetails.class, roomid);
	}
	
	@Override
	public Integer updateRoom(int roomid,RoomDetails room) {
		RoomDetails rm=sf.getCurrentSession().get(RoomDetails.class,roomid);
		rm.setArea(room.getArea());
		rm.setCity(room.getCity());
		rm.setPincode(room.getPincode());
		rm.setRent(room.getRent());
		rm.setResiadd(room.getResiadd());
		rm.setRoomtype(room.getRoomtype());
		rm.setFacilities(room.getFacilities());
		rm.setStatus(room.getStatus());		
		return 1;
	}

	@Override
	public List<RoomDetails> filtercity(String city) {
		System.out.println(city);
		String jpql = "SELECT DISTINCT r FROM RoomDetails r Left Join r.userid WHERE lower(r.city) like lower(concat('%',:city,'%')) and r.status='AVAILABLE'";
		return sf.getCurrentSession().createQuery(jpql,RoomDetails.class).setParameter("city",city).getResultList();		
	}

	@Override
	public CustomerBooking bookRoom(CustomerBooking booking) {
		
	      Integer bookid=(Integer) sf.getCurrentSession().save(booking);
	      return sf.getCurrentSession().get(CustomerBooking.class,bookid);
	}

	@Override
	public void changeRoomStatusToBooked(int roomid) {
		RoomDetails room=sf.getCurrentSession().get(RoomDetails.class,roomid);
		room.setStatus(RoomStatus.NOTAVAILABLE);
		sf.getCurrentSession().save(room);
	}

	@Override
	public List<RoomDetails> specificownerlist(int userid) {
		System.out.println(userid);
		User u=dao.getUserByid(userid);
		System.out.println(u);
		String jpql="select r from RoomDetails r left outer join r.userid where r.userid.userid=:uid";
		System.out.println(jpql);
		return sf.getCurrentSession().createQuery(jpql,RoomDetails.class).setParameter("uid",u.getUserid()).getResultList();
	}

	@Override
	public List<RoomDetails> allroomlist() {
		String jpql = "SELECT DISTINCT r FROM RoomDetails r Left Join r.userid WHERE r.status='AVAILABLE'";
		return sf.getCurrentSession().createQuery(jpql,RoomDetails.class).getResultList();
	}

	@Override
	public List<CustomerBooking> getallbookdetails(int roomid) {
		String jpql="select r from CustomerBooking r left outer join r.room where r.room.roomid=:roomid";
		return sf.getCurrentSession().createQuery(jpql,CustomerBooking.class).setParameter("roomid",roomid).getResultList();
	}

	
	
}
