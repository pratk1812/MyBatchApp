package com.finan.MyBatchApp.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonPropertyOrder(value = {"Name","Class","Division","Id","Address","Fee"})
@JsonRootName(value = "Student")
public class StudentBean {

	@JsonProperty(value = "Id")
	private int id;
	@JsonProperty(value = "Name")
	private String name;
	@JsonProperty(value = "Class")
	private String classString;
	@JsonProperty(value = "Division")
	private String division;
	@JsonProperty(value = "Address")
	private String address;
	@JsonProperty(value = "Fee")
	private double fee;
	

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

	public String getClassString() {
		return classString;
	}

	public void setClassString(String classString) {
		this.classString = classString;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", classString=" + classString + ", division=" + division
				+ ", address=" + address + ", fee=" + fee + "]";
	}
}
