package com.app.dao;

import java.util.List;

import com.app.pojos.CustomerBooking;
import com.app.pojos.RoomDetails;
import com.app.pojos.User;

public interface IOwnerDao {

	public Integer registerRoom(int userid,RoomDetails room);
	public Integer updateRoom(int roomid, RoomDetails room);
	public RoomDetails getRoomDetails(int roomid);
	public Integer deleteRoomInfo(RoomDetails rm);
	public List<RoomDetails> filtercity(String city);
	public CustomerBooking bookRoom(CustomerBooking booking);
	void changeRoomStatusToBooked(int roomid);
	public List<RoomDetails> specificownerlist(int userid);
	public List<RoomDetails> allroomlist();
	public List<CustomerBooking> getallbookdetails(int roomid);
	
	
	
}
