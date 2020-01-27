package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

@Entity
public class User {
	
	private Integer userid;
	private String email;
	private String userName;
	private String contact;
	private String password;
	private Role usertype;

	
	@JsonIgnore
	private List<RoomDetails>roomlist=new ArrayList<>();
	
	@JsonIgnore
	private List<CustomerBooking>booklist=new ArrayList<>();
	
	public User() {
		System.out.println("Inside the UserPojo");
	}
	
	
	public User(String email, String userName, String password)
	  { 
		  super();
		  this.email = email;
		  this.userName = userName;
		  this.password = password;
	  
	  }

	public User(String email, String userName, String contact, String password) {
		super();
		this.email = email;
		this.userName = userName;
		this.contact = contact;
		this.password = password;
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	@Column(unique = true,nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(unique = true,nullable = false)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Enumerated(EnumType.STRING)
	public Role getUsertype() {
		return usertype;
	}

	public void setUsertype(Role usertype) {
		this.usertype = usertype;
	}
	
	@OneToMany(mappedBy = "userid",cascade = CascadeType.ALL,orphanRemoval = true)
	public List<RoomDetails> getRoomlist() {
		return roomlist;
	}


	public void setRoomlist(List<RoomDetails> roomlist) {
		this.roomlist = roomlist;
	}
	//convinience methods
	public void addRoom(RoomDetails rm) {
	    roomlist.add(rm);
		rm.setUserid(this);
	}
	
	public void removeRoom(RoomDetails rm) {
		System.out.println(rm);
		roomlist.remove(rm);
		rm.setUserid(null);
	}

	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	public List<CustomerBooking> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<CustomerBooking> booklist) {
		this.booklist = booklist;
	}


	@Override
	public String toString() {
		return "User [userid=" + userid + ", email=" + email + ", userName=" + userName + ", contact=" + contact
				+ ", password=" + password + ", usertype=" + usertype + "]";
	}

	

	
	
}
