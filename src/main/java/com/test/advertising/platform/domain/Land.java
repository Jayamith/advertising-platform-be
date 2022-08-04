package com.test.advertising.platform.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="land")
public class Land {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer landId ;
	private String lname;
	private String moreInfo;
	private String email;
	private String contact;
	private String location;
	private String price;
	private String lCondition;
	private String seller;
	private String size;
	private boolean sStatus;
	private Date addedDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "land_images", 
	joinColumns = { @JoinColumn(name = "land_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "images_id") })
	private Set<ImageModel> landImages;

	public Land() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Land(Integer landId, String lname, String moreInfo, String email, String contact, String location,
			String price, String model, String transmission, String fuelType, String lCondition, String seller,
			String size, boolean sStatus, Date addedDate, Set<ImageModel> landImages) {
		super();
		this.landId = landId;
		this.lname = lname;
		this.moreInfo = moreInfo;
		this.email = email;
		this.contact = contact;
		this.location = location;
		this.price = price;
		this.lCondition = lCondition;
		this.seller = seller;
		this.size = size;
		this.sStatus = sStatus;
		this.addedDate = addedDate;
		this.landImages = landImages;
	}

	public Integer getLandId() {
		return landId;
	}

	public void setLandId(Integer landId) {
		this.landId = landId;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public String getlCondition() {
		return lCondition;
	}

	public void setlCondition(String lCondition) {
		this.lCondition = lCondition;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public boolean issStatus() {
		return sStatus;
	}

	public void setsStatus(boolean sStatus) {
		this.sStatus = sStatus;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Set<ImageModel> getVehicleImages() {
		return landImages;
	}

	public void setVehicleImages(Set<ImageModel> landImages) {
		this.landImages = landImages;
	}
	
	

}