package com.demo.cmds;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.demo.service.DownloadAttachment;
import com.demo.service.DownloadAttachmentFromSender;

@ShellComponent
public class Download {
	@ShellMethod("Download all attachments recieved from a mail address, stored in downloads folder.")
	public void download(
			@ShellOption(value = {"--email", "-m"}, help = "The Sender of the attachments") String email) {
		DownloadAttachment attachmentDownloader = new DownloadAttachmentFromSender(email);
		attachmentDownloader.downloadAll();
	}
}
