package com.demo.service;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Connection {
	
	@Value("${mail.host:qasid.iitk.ac.in}")
	private static String host = "qasid.iitk.ac.in";
	@Value("${mail.port:143}")
	private static String port = "143";
	private static Store store;

	public static final void close() {
		try {
			store.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static boolean connectByCred(String usr, String pw) {
		try {
			initializeStoreAndConnect(usr, pw);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}

	public final static Folder getFolder(final String S) {
		try {
			return store.getFolder(S);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Session getSession() {
		Properties props = System.getProperties();
		props.setProperty("mail.imap.host", host);
		props.setProperty("mail.imap.port", port);
		Session session = Session.getDefaultInstance(props, null);
		return session;
	}

	private static void initializeStoreAndConnect(String usr, String pw)
			throws NoSuchProviderException, MessagingException {
		Session session = getSession();
		final Store s = session.getStore("imaps");
		s.connect(host, usr, pw);
		System.out.println("Connected Successfully\n===========\n");
		store = s;
	}
	public static Folder getReadOnlyOpenedInbox() throws MessagingException {
		final Folder inbox = getFolder("INBOX");
		inbox.open(Folder.READ_ONLY);
		return inbox;
	}
	private Connection() {
	}
}
