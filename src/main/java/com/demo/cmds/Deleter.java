package com.demo.cmds;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.demo.service.DeleteByDate;
import com.demo.service.DeleteByID;

@ShellComponent
public class Deleter {
	@ShellMethod("Delete all the mails before the specified date")
	public void dbd(@ShellOption(value = { "--date", "-d" }, help = "An Integer between 1-31") int date,
			@ShellOption(value = { "--month", "-m" }, help = "An Integer between 1-12") int month,
			@ShellOption(value = { "--year", "-y" }, help = "An Integer of the format XXXX") int year,
			@ShellOption(value = { "--write",
					"-w" }, defaultValue = "false", help = "Requires to publish Changes, if true deletes from mail and by default is false") boolean writeMode) {
		// -1 to month is necessary
		DeleteByDate byDate = new DeleteByDate(date, month - 1, year);
		if (!writeMode) {
			byDate.setReadMode();
		}
		byDate.delete();
	}

	@ShellMethod("Delete All Mails based on ID.log file. Should be present on the directory of the app.")
	public void dbi(@ShellOption(value = { "--write",
			"-w" }, defaultValue = "false", help = "Requires to publish Changes, if true deletes from mail and by default is false") boolean writeMode) {

		DeleteByID dbID = new DeleteByID();
		if (!writeMode) {
			dbID.setReadMode();
		}
		dbID.delete();
	}

}
