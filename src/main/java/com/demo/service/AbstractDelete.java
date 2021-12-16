package com.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;

public abstract class AbstractDelete {
	private int deleteCount = 0;
	private final String folder = "INBOX";
	private int len = 0;
	private int folderOpenFlag = Folder.READ_WRITE;

	private final void logMessageFrom(String from) {
		++deleteCount;
		len = Integer.max(len, from.length());
		while (from.length() < len) {
			from = from + " ";
		}
		String toLog = "\rDeleted Mail: " + from + " .... " + deleteCount;
		System.out.println(toLog);
	}

	private final Folder getOpenedFolder() throws MessagingException {
		final Folder f = Connection.getFolder(folder);
		f.open(folderOpenFlag);
		return f;
	}

	private final void deleteMessagesAndLog(List<Message> msgs) throws MessagingException {
		for (Message msg : msgs) {
			logMessageFrom(msg.getFrom()[0].toString());
			msg.setFlag(Flags.Flag.DELETED, true);
		}
	}

	private final void tryDelete() throws MessagingException {
		Folder folder = getOpenedFolder();
		report();
		List<Message> messages = getConcernedMessagesOfFolder(folder);
		deleteMessagesAndLog(messages);
		folder.close();
	}

	public final void delete() {
		try {
			tryDelete();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private List<Message> getConcernedMessagesOfFolder(Folder folder) throws MessagingException {
		final Message msgs[] = folder.getMessages();
		ArrayList<Message> concernedMessages = new ArrayList<>();
		for (Message message : msgs) {
			if (shouldDelete(message)) {
				concernedMessages.add(message);
			}
		}
		return concernedMessages;
	}

	protected abstract boolean shouldDelete(Message msg);

	private final void report() {
		if (folderOpenFlag == Folder.READ_ONLY)
			System.out.println("Opening in READ Mode");
		else
			System.out.println("Opening in WRITE Mode");
	}

	public final void setReadMode() {
		this.folderOpenFlag = Folder.READ_ONLY;
	}
}
