package com.demo.service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;


public class DownloadAttachmentFromSender extends DownloadAttachment {
	private Address internetAddress;

	public DownloadAttachmentFromSender(String emailAddress) {
		internetAddress = Convert.toInternetAddress(emailAddress);
	}

	@Override
	protected boolean shouldDownload(Message msg) {
		try {
			Address sender = msg.getFrom()[0];
			return sender.equals(internetAddress) && msg.getContentType().contains("multipart");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}

}
