package com.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;

public abstract class DownloadAttachment {

	private final void download(final Message msg) throws IOException, MessagingException {
		final String folderPath = makeFolder();
		final Multipart m = (Multipart) msg.getContent();
		final List<MimeBodyPart> bodyParts = getBodyParts(m);
		saveThe(bodyParts, folderPath);
	}

	private String makeFolder() {
		final String dir = System.getProperty("user.dir") + "/Downloads";
		final File folder = new File(dir);
		folder.mkdir();
		return dir;
	}

	private final List<MimeBodyPart> getBodyParts(final Multipart content) throws MessagingException {
		final List<MimeBodyPart> bodyParts = new ArrayList<>();
		for (int i = 0; i < content.getCount(); i++) {
			final MimeBodyPart part = (MimeBodyPart) content.getBodyPart(i);
			if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
				bodyParts.add(part);
			}
		}
		return bodyParts;
		
	}

	private void saveThe(final List<MimeBodyPart> bodyParts, final String folderPath)
			throws MessagingException, IOException {
		for (final MimeBodyPart part : bodyParts) {
			final String name = part.getFileName();
			part.saveFile(folderPath + "/" + name);
		}
	}

	public final void downloadAll() {
		try {
			final List<Message> msgsToDownload = getMessagesToDownloadAndLog();
			for (final Message msg : msgsToDownload) {
				download(msg);
			}
		} catch (MessagingException | IOException e) {
			e.getStackTrace();
		} finally {
			System.out.println();
		}
	}

	private final List<Message> getMessagesToDownloadAndLog() throws MessagingException {

		final List<Message> msgsToDownload = new ArrayList<>();

		final Folder folder = Connection.getReadOnlyOpenedInbox();
		final Message[] msg = folder.getMessages();

		
		int lookedFiles = 0;
		int foundFiles = 0;
		
		for (final Message message : msg) {
			lookedFiles++;
			if (shouldDownload(message)) {
				msgsToDownload.add(message);
				foundFiles++;
			}
			printLog(lookedFiles, foundFiles);
		}
		// It's important to close the folder.
		return msgsToDownload;
	}
	private void printLog(int lookedFiles, int foundFiles) {
		String log = String.format("\rLooked (Found) ============ %d (%d)", lookedFiles, foundFiles);
		System.out.print(log);
		
	}
	protected abstract boolean shouldDownload(Message msg);
}