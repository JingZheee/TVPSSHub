package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "schools")
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "district")
	private String district;

	@Column(name = "representative")
	private String representative;

	@Column(name = "code")
	private String code;

	@Column(name = "address")
	private String address;

	@Column(name = "postcode")
	private String postcode;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "phone")
	private String phone;

	@Column(name = "studio")
	private boolean studio;

	@Column(name = "school_recording")
	private boolean schoolRecording;

	@Column(name = "upload_youtube")
	private boolean uploadYoutube;

	@Column(name = "recording")
	private boolean recording;

	@Column(name = "collaborate")
	private boolean collaborate;

	@Column(name = "greenscreen")
	private boolean greenscreen;

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isStudio() {
		return studio;
	}

	public void setStudio(boolean studio) {
		this.studio = studio;
	}

	public boolean isSchoolRecording() {
		return schoolRecording;
	}

	public void setSchoolRecording(boolean schoolRecording) {
		this.schoolRecording = schoolRecording;
	}

	public boolean isUploadYoutube() {
		return uploadYoutube;
	}

	public void setUploadYoutube(boolean uploadYoutube) {
		this.uploadYoutube = uploadYoutube;
	}

	public boolean isRecording() {
		return recording;
	}

	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	public boolean isCollaborate() {
		return collaborate;
	}

	public void setCollaborate(boolean collaborate) {
		this.collaborate = collaborate;
	}

	public boolean isGreenscreen() {
		return greenscreen;
	}

	public void setGreenscreen(boolean greenscreen) {
		this.greenscreen = greenscreen;
	}
}
