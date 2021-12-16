package com.demo.service;

import java.util.Calendar;

import javax.mail.Message;
import javax.mail.MessagingException;


public class DeleteByDate extends AbstractDelete {

	private final Calendar C;

	public DeleteByDate(int date, int month, int year) {
		C = Calendar.getInstance();
		C.set(year, month, date);
	}

	@Override
	protected boolean shouldDelete(Message element) {
		try {
			final Calendar dt = Calendar.getInstance();
			dt.setTime(element.getReceivedDate());
			return (dt.before(C));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;

	}
}