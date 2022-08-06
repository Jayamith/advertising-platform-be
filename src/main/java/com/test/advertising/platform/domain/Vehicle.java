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
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer vehicleId;
	private String vname;
	private String moreInfo;
	private String email;
	private String contact;
	private String location;
	private String price;
	private String model;
	private String transmission;
	private String fuelType;
	private String vCondition;
	private String seller;
	private String manufacturer;
	private String vStatus;
	private Date addedDate;
	private String[] photos;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "vehicle_images", joinColumns = { @JoinColumn(name = "vehicle_id") }, inverseJoinColumns = {
			@JoinColumn(name = "images_id") })
	private Set<ImageModel> vehicleImages;

	public Vehicle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vehicle(Integer vehicleId, String vname, String moreInfo, String email, String contact, String location,
			String price, String model, String transmission, String fuelType, String vCondition, String seller,
			String manufacturer, String vStatus, Date addedDate, String[] photos, Set<ImageModel> vehicleImages) {
		super();
		this.vehicleId = vehicleId;
		this.vname = vname;
		this.moreInfo = moreInfo;
		this.email = email;
		this.contact = contact;
		this.location = location;
		this.price = price;
		this.model = model;
		this.transmission = transmission;
		this.fuelType = fuelType;
		this.vCondition = vCondition;
		this.seller = seller;
		this.manufacturer = manufacturer;
		this.vStatus = vStatus;
		this.addedDate = addedDate;
		this.photos = photos;
		this.vehicleImages = vehicleImages;
	}

	public Integer getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Integer vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVname() {
		return vname;
	}

	public void setVname(String vname) {
		this.vname = vname;
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

	public String getvCondition() {
		return vCondition;
	}

	public void setvCondition(String vCondition) {
		this.vCondition = vCondition;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getvStatus() {
		return vStatus;
	}

	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public String[] getPhotos() {
		return photos;
	}

	public void setPhotos(String[] photos) {
		this.photos = photos;
	}

	public Set<ImageModel> getVehicleImages() {
		return vehicleImages;
	}

	public void setVehicleImages(Set<ImageModel> vehicleImages) {
		this.vehicleImages = vehicleImages;
	}

}
