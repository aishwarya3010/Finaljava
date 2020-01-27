package com.app.pojos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RoomDetails {
   private Integer roomid;
   private RoomType roomtype;
   private String city;
   private int pincode;
   private String area;
   private String resiadd;
    private byte[] roomimage;
   private float rent;
   private RoomStatus status;
   private String facilities;
   private User userid;
   @JsonIgnore
   private List<CustomerBooking> booklist=new ArrayList<>();

   
   
   public RoomDetails() {
	// TODO Auto-generated constructor stub
   }

	public RoomDetails(String city) {
		super();
		this.city = city;
	}
	


public RoomDetails( RoomType roomtype, String city, int pincode, String area, String resiadd,
			byte[] roomimage, float rent, RoomStatus status, String facilities) {
		super();
		
		this.roomtype = roomtype;
		this.city = city;
		this.pincode = pincode;
		this.area = area;
		this.resiadd = resiadd;
		this.roomimage = roomimage;
		this.rent = rent;
		this.status = status;
		this.facilities = facilities;
	}

public RoomDetails(RoomType roomtype, String city, int pincode, String area, String resiadd, float rent, RoomStatus status,
		String facilities) {
	super();
	this.roomtype = roomtype;
	this.city = city;
	this.pincode = pincode;
	this.area = area;
	this.resiadd = resiadd;
	this.rent = rent;
	this.status = status;
	this.facilities = facilities;
}

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Integer getRoomid() {
	return roomid;
}

public void setRoomid(Integer roomid) {
	this.roomid = roomid;
}
 @Enumerated(EnumType.STRING)
public RoomType getRoomtype() {
	return roomtype;
}

public void setRoomtype(RoomType roomtype) {
	this.roomtype = roomtype;
}

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public int getPincode() {
	return pincode;
}

public void setPincode(int pincode) {
	this.pincode = pincode;
}

public String getArea() {
	return area;
}

public void setArea(String area) {
	this.area = area;
}

public String getResiadd() {
	return resiadd;
}

public void setResiadd(String resiadd) {
	this.resiadd = resiadd;
}
@Lob
@Column(length=16777215)
public byte[] getRoomimage() {
	return roomimage;
}

public void setRoomimage(byte[] roomimage) {
	this.roomimage = roomimage;
}

public float getRent() {
	return rent;
}

public void setRent(float rent) {
	this.rent = rent;
}
@Enumerated(EnumType.STRING)
public RoomStatus getStatus() {
	return status;
}

public void setStatus(RoomStatus status) {
	this.status = status;
}

public String getFacilities() {
	return facilities;
}

public void setFacilities(String facilities) {
	this.facilities = facilities;
}

@ManyToOne
@JoinColumn(name="userid")
public User getUserid() {
	return userid;
}

public void setUserid(User userid) {
	this.userid = userid;
}



@OneToMany(mappedBy = "room",cascade = CascadeType.ALL,orphanRemoval = true)
public List<CustomerBooking> getBooklist() {
	return booklist;
}

public void setBooklist(List<CustomerBooking> booklist) {
	this.booklist = booklist;
}

@Override
public String toString() {
	return "RoomDetails [roomid=" + roomid + ", roomtype=" + roomtype + ", city=" + city + ", pincode=" + pincode
			+ ", area=" + area + ", resiadd=" + resiadd + ", roomimage=" + Arrays.toString(roomimage) + ", rent=" + rent
			+ ", status=" + status + ", facilities=" + facilities + "]";
}
   
   
}
