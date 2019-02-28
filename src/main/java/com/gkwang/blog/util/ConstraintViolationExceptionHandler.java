package com.gkwang.blog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;

/**
 * ConstraintViolationException异常处理器
 * @Title: ConstraintViolationExceptionHandler.java
 * @Package:com.gkwang.blog.util
 * @author:Wanggk 
 * @date:2018年10月18日
 * @version:V1.0
 */
public class ConstraintViolationExceptionHandler extends ConstraintViolationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConstraintViolationExceptionHandler(Set<? extends ConstraintViolation<?>> constraintViolations) {
		super(constraintViolations);
		// TODO Auto-generated constructor stub
	}

	public static String getMessage(ConstraintViolationException e) {
		List<String> msglist = new ArrayList<>();
		for (ConstraintViolation<?> constraintviolation : e.getConstraintViolations()) {
			msglist.add(constraintviolation.getMessage());
		}
		String messages = StringUtils.join(msglist.toArray(),";");
		return messages;
	}
}
