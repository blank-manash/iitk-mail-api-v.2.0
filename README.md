## IITK MAIL API

This is a very basic implementation on the basis of understanding smtp / imaps protocol. To use this package,
You can run `` java -jar your_file.jar `` after you download the file from the release section. 

After successful authentication, run ``help`` to see all the commands, and to get help on a specific commands, use `` help command_name``.

#### READ MODE

The application runs in two modes which are read and write. The read mode is the default mode in the app so that no huge data is deleted accidently. To actually delete the mails you need to supplement a ``--write`` flag to the command which will then irreversibly delete your mails.


#### ID.log
The option for ID.log file requires you to have a ID.log file in the same directory as where you have the app. It should contain all the email address from which you wish to delete all your mails, each on a separate line. Make sure to have no empty or invalid email addresses in the lines.

Here is an example,

```
abc@gmail.com
cde@gmail.com
```

Running the application will delete all the mails which are from either ``abc@gmail.com`` or ``cde@gmail.com``. 

-------------
### Contribution
Tests and bug issues are welcome, please report them in github issues section.
If anyone wants to contribute, they free to do so and may contact me in my email.