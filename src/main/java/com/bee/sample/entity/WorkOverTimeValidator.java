package com.bee.sample.entity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WorkOverTimeValidator implements
 ConstraintValidator<WorkOverTime, Integer>{
 WorkOverTime workOverTime;
 int max;
 
 @Override
 public void initialize(WorkOverTime aWorkOverTime) {
	 workOverTime = aWorkOverTime;
	 max = aWorkOverTime.max();
 }
 
@Override
public boolean isValid(Integer value, ConstraintValidatorContext context) {
	if(value==null) {
		 return true;
	 }
	 return value<max;
}
}
