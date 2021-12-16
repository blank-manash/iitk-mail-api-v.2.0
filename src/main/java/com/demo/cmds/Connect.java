package com.demo.cmds;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.demo.service.Connection;

@ShellComponent
public class Connect {
	@ShellMethod(value = "Connect to a Mail ID")
	public void connect(
			@ShellOption(value = { "--user", "-u" }, help = "Username (without @iitk.ac.in)") String username,
			@ShellOption(value = { "--password", "-p" }, help = "Password") String password) {
		Connection.connectByCred(username, password);
	}
}
