package com.demo;

import java.io.Console;

import org.jline.terminal.Terminal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import com.demo.service.Connection;
import com.demo.shell.ShellHelper;

@SpringBootApplication
public class SpringShellDemoApplication {
	
	private static String username;
	private static String password;

	private static void readUserAndPassword() {
		Console console = System.console();
		System.out.print("Enter Your User-Name (without @iitk.ac.in): ");
		username = console.readLine();
		System.out.print("Enter Your Password: ");
		password = String.copyValueOf(console.readPassword());
	}

	public static void main(String[] args) {
		readUserAndPassword();
		Connection.connectByCred(username, password);
		SpringApplication.run(SpringShellDemoApplication.class, args);
	}

	@Bean
	public ShellHelper shellHelper(@Lazy Terminal terminal) {
		return new ShellHelper(terminal);
	}
}
