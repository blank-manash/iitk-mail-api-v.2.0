package com.demo.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class DeleteByID extends AbstractDelete {

	private final static HashSet<Address> memory = new HashSet<>();
	private final static String dir = System.getProperty("user.dir") + "/ID.log";

	private final List<Address> getAddressesFromFile(final File file) throws IOException, AddressException {

		final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		List<Address> addresses = new ArrayList<>();
		String line = bufferedReader.readLine();

		while (line != null && !line.equals("")) {
			Address internetAddress = new InternetAddress(line);
			addresses.add(internetAddress);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return addresses;
	}

	public DeleteByID() {
		try {
			loadAddress();
		} catch (AddressException | IOException e) {
			e.printStackTrace();
		}
	}

	private void loadAddress() throws IOException, AddressException {
		final File file = new File(dir);
		List<Address> addrs = getAddressesFromFile(file);
		addToMemoryAddrsOf(addrs);
	}

	private void addToMemoryAddrsOf(List<Address> addrs) {
		for (Address addr : addrs) {
			memory.add(addr);
		}
	}

	public final void refresh() {
		try {
			writeToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeToFile() throws IOException {
		final PrintWriter wr = getPrintWriter();
		for (Address addr : memory) {
			wr.println(addr);
		}
		wr.close();
	}

	private PrintWriter getPrintWriter() throws IOException {
		return new PrintWriter(new BufferedWriter(new FileWriter(dir)));
	}

	public final String exists(final String id) {
		Address internetAddress = Convert.toInternetAddress(id);
		if (memory.contains(internetAddress))
			return "Mail Exists";
		else
			return "Doesn't Exist";
	}

	public final void insert(final String id) {
		Address internetAddress = Convert.toInternetAddress(id);
		memory.add(internetAddress);
	}

	public final void erase(String id) {
		Address internetAddress = Convert.toInternetAddress(id);
		memory.remove(internetAddress);
	}

	@Override
	protected boolean shouldDelete(Message msg) {
		Address addr = Convert.InternetAddressOfSenderOfMsg(msg);
		return memory.contains(addr);
	}

}

class Convert {
	public static InternetAddress toInternetAddress(String id) {
		InternetAddress addr;
		try {
			addr = new InternetAddress(id);
			return addr;
		} catch (AddressException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Address InternetAddressOfSenderOfMsg(Message msg) {
		Address addr = null;
		try {
			addr = msg.getFrom()[0];
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return addr;
	}
}
