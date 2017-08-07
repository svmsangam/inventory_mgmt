package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.*;


@Entity
@Table(name="city")
public class CityInfo extends AbstractEntity<Long> {
	
	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.EAGER)
	private StateInfo stateInfo;
	
	@Column(nullable = false)
	private Status status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StateInfo getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
