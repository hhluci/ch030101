package com.bee.sample.entity;

public class WorkOverTimeForm {
	@WorkOverTime(max=2)
	 int workOverTime;

	public int getWorkOverTime() {
		return workOverTime;
	}

	public void setWorkOverTime(int workOverTime) {
		this.workOverTime = workOverTime;
	}
	
}
