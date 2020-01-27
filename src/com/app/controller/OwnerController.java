package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dao.IOwnerDao;
import com.app.dao.IUserDao;
import com.app.pojos.CustomerBooking;
import com.app.pojos.RoomDetails;
import com.app.pojos.RoomStatus;
import com.app.pojos.RoomType;
import com.app.pojos.User;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/owner")
public class OwnerController {
	
	@Autowired
	private IOwnerDao dao;
	@Autowired
	private IUserDao iuserdao;
	@Autowired
	private JavaMailSender mailSender;

	public OwnerController() {
		// TODO Auto-generated constructor stub
	}

	
//	  @PostMapping("/insertroom/{userid}") 
//	  public Integer registerRoom(@PathVariable int userid, @RequestBody RoomDetails room) 
//	  {
//	  System.out.println(room + "    " + userid);
//	  
//	  if (userid != 0) { Integer id = dao.registerRoom(userid, room);
//	  System.out.println(id); return id; } else return 0;
//	  
//	  }
	 
	@PostMapping("/insertroom/{userid}") 
	public ResponseEntity<?> registerRoom(@PathVariable int userid,@RequestParam String roomtype,@RequestParam String city,
			@RequestParam String area,@RequestParam int pincode,@RequestParam String resiadd, 
			@RequestParam float rent, @RequestParam String status,@RequestParam String facilities,@RequestParam(value = "roomimage", required = false) MultipartFile roomimage)
	{
		
		RoomDetails room=new RoomDetails(RoomType.valueOf(roomtype), city,pincode, area, resiadd, rent, RoomStatus.valueOf(status), facilities);
		if (roomimage != null) {
			try {
				System.out.println(roomimage.getOriginalFilename());
				room.setRoomimage(roomimage.getBytes());
				System.out.println(room);
				return new ResponseEntity<Integer>(dao.registerRoom(userid, room), HttpStatus.CREATED);
			} catch (Exception e) {

				return new ResponseEntity<RoomDetails>(new RoomDetails(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return null;
	
	}
	
	@DeleteMapping("/{roomid}")
	public ResponseEntity<?> removeRoom(@PathVariable int roomid) {
		try {
			RoomDetails rm = dao.getRoomDetails(roomid);
			if (rm == null)
				throw new RuntimeException("Emp ID invalid");
			else {
				return new ResponseEntity<Integer>(dao.deleteRoomInfo(rm), HttpStatus.OK);
			}
		} catch (RuntimeException e1) {
			e1.printStackTrace();
			return new ResponseEntity<String>(e1.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{roomid}")
	public ResponseEntity<?> updateRoom(@PathVariable int roomid, @RequestBody RoomDetails room) {
		try {
			System.out.println(room);
			if (room == null)
				throw new RuntimeException("Room ID invalid");
			else {
				return new ResponseEntity<Integer>(dao.updateRoom(roomid, room), HttpStatus.OK);
			}
		} catch (RuntimeException e1) {
			e1.printStackTrace();
			return new ResponseEntity<String>(e1.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/getroom/{roomid}")
	public RoomDetails findroombyid(@PathVariable int roomid)
	{
			return dao.getRoomDetails(roomid);		
	}
	
	

	@GetMapping("/{city}")
	public ResponseEntity<?> filterByCity(@PathVariable String city) {
		return new ResponseEntity<List<RoomDetails>>(dao.filtercity(city), HttpStatus.OK);
	}
	@GetMapping("/allroomlist")
	public ResponseEntity<?> getAllRooms() {
		return new ResponseEntity<List<RoomDetails>>(dao.allroomlist(), HttpStatus.OK);
	}
	
	
	@GetMapping("/getroomlist/{userid}")
	public ResponseEntity<?> getUserlist(@PathVariable int userid) {
		System.out.println("inside list");
		return new ResponseEntity<List<RoomDetails>>(dao.specificownerlist(userid), HttpStatus.OK);
	}
	
	
	@PostMapping("/bookroom/{userid}/{roomid}")
	public CustomerBooking bookRoom(@RequestBody CustomerBooking booking, @PathVariable int userid, @PathVariable int roomid) {

		System.out.println(booking);
		System.out.println(userid);
		System.out.println(roomid);
		
		RoomDetails room=dao.getRoomDetails(roomid);
		User u=iuserdao.getUserByid(userid);
		User user=iuserdao.getUserByid(room.getUserid().getUserid());
		System.out.println(user);
		System.out.println(u);
		booking.setUser(iuserdao.getUserByid(userid));
		booking.setRoom(room);
		System.out.println(booking);
		CustomerBooking cust=dao.bookRoom(booking);
		if (cust!= null) 
		{
			dao.changeRoomStatusToBooked(roomid);
			String msg="Your room is book";
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			
			mailMessage.setTo(u.getEmail());
			mailMessage.setSubject("Room Confirmation  Mail");
			mailMessage.setText(msg);
			String msg1="Your Room is booked by "+u.getUserName()+" email "+u.getEmail();
			SimpleMailMessage mailMessage1 = new SimpleMailMessage();
			mailMessage1.setTo(user.getEmail());
			mailMessage1.setSubject("New Room Booking Details");
			mailMessage1.setText(msg1);
						
			try
			{
				mailSender.send(mailMessage);
				mailSender.send(mailMessage1);
				
			}
			catch (MailException e) 
			{
				System.out.println("inside mail exception");
				e.printStackTrace();
			}
				
		}
		
		return cust;
	}
	@GetMapping("/bookdetails/{roomid}")
	public ResponseEntity<?> getbooklist(@PathVariable int roomid)
	{
		
		
		return new ResponseEntity<List<CustomerBooking>>(dao.getallbookdetails(roomid), HttpStatus.OK);
		
	}
}
