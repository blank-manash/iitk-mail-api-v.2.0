package com.demo.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider {

	@Override
	public AttributedString getPrompt() {
		final String prompt = "api ==> ";
		final AttributedStyle style = AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN);
		final AttributedString attributedString = new AttributedString(prompt, style);
		return attributedString;
	}

}
