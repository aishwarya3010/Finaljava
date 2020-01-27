package com.app.pojos;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CustomerBooking {
	private Integer bookid;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date    bookdate;
	private float   paidamt;
	private RoomDetails room;
	private User user;
	
	
	public CustomerBooking() {
		// TODO Auto-generated constructor stub
	}

	
	
	public CustomerBooking(Integer bookid, Date bookdate, float paidamt, RoomDetails room, User user) {
		super();
		this.bookid = bookid;
		this.bookdate = bookdate;
		this.paidamt = paidamt;
		this.room = room;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getBookid() {
		return bookid;
	}


	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	@Temporal(TemporalType.DATE)
	public Date getBookdate() {
		return bookdate;
	}


	public void setBookdate(Date bookdate) {
		this.bookdate = bookdate;
	}


	public float getPaidamt() {
		return paidamt;
	}


	public void setPaidamt(float paidamt) {
		this.paidamt = paidamt;
	}

	@ManyToOne
	@JoinColumn(name="room")
	public RoomDetails getRoom() {
		return room;
	}


	public void setRoom(RoomDetails room) {
		this.room = room;
	}

	
	@ManyToOne
	@JoinColumn(name = "user")
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "CustomerBooking [bookid=" + bookid + ", bookdate=" + bookdate + ", paidamt=" + paidamt + "]";
	}	
	

}
