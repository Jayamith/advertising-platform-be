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
@Table
public class Sparepart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sparepartId ;
	private String sname;
	private String moreInfo;
	private String email;
	private String contact;
	private String location;
	private String price;
	private String model;
	private String transmission;
	private String fuelType;
	private String sCondition;
	private String seller;
	private String manufacturer;
	private boolean sStatus;
	private Date addedDate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "sparepart_images", 
	joinColumns = { @JoinColumn(name = "sparepart_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "images_id") })
	private Set<ImageModel> vehicleImages;

	public Sparepart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Sparepart(Integer sparepartId, String sname, String moreInfo, String email, String contact, String location,
			String price, String model, String transmission, String fuelType, String sCondition, String seller,
			String manufacturer, boolean sStatus, Date addedDate, Set<ImageModel> vehicleImages) {
		super();
		this.sparepartId = sparepartId;
		this.sname = sname;
		this.moreInfo = moreInfo;
		this.email = email;
		this.contact = contact;
		this.location = location;
		this.price = price;
		this.model = model;
		this.transmission = transmission;
		this.fuelType = fuelType;
		this.sCondition = sCondition;
		this.seller = seller;
		this.manufacturer = manufacturer;
		this.sStatus = sStatus;
		this.addedDate = addedDate;
		this.vehicleImages = vehicleImages;
	}

	public Integer getSparepartId() {
		return sparepartId;
	}

	public void setSparepartId(Integer sparepartId) {
		this.sparepartId = sparepartId;
	}


	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getsCondition() {
		return sCondition;
	}

	public void setsCondition(String sCondition) {
		this.sCondition = sCondition;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getmanufacturer() {
		return manufacturer;
	}

	public void setmanufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
		return vehicleImages;
	}

	public void setVehicleImages(Set<ImageModel> vehicleImages) {
		this.vehicleImages = vehicleImages;
	}

}
