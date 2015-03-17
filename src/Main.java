import org.apache.commons.lang.StringUtils;

import javax.mail.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) throws MessagingException, IOException, ParseException, InterruptedException {

        final String user = "*******************"; // имя пользователя
        final String pass = "********";    // пароль
        final String host = "mail.gmail.ru";     // адрес почтового сервера
        final String fromOnEmail = "**************";

        final String toOnEmail = "*******************";
        final String mailDebug = "false";
        final String protocolToRead = "imap";
        final String folderToFind = "INBOX"; //"[Gmail]/Помеченные"
        final String dateFormat = "dd.MM.yyyy HH:mm:ss";
        final String fileNameToSearch = "Test.xlsx";

        File file = new File(fileNameToSearch);
        System.out.println(file.getAbsolutePath());

        SimpleDateFormat format1 = new SimpleDateFormat(dateFormat, Locale.UK);
        System.out.println(format1.format(file.lastModified()));

        // Создание свойств
        Properties props = new Properties();

        //включение debug-режима
        props.put("mail.debug", mailDebug);

        //Указываем протокол - IMAP с SSL
        props.put("mail.store.protocol", protocolToRead);
//        props.put( "mail.imap.ssl.enable", "false" );
//        props.put("", "false")
        props.put("mail.imaps.ssl.trust", "*");
        Session session = Session.getInstance(props);
        Store store = session.getStore();

        //подключаемся к почтовому серверу
        store.connect(host, 143, user, pass);

        //получаем папку с входящими сообщениями
        Folder inbox = store.getFolder(folderToFind);

        //открываем её только для чтения
        inbox.open(Folder.READ_ONLY);
        System.out.println(inbox.getMessageCount());
        Message[] myMessages = inbox.getMessages();
        List<File> attachments = new ArrayList<File>();
        for(Message myMessage : myMessages) {
            System.out.println("Read message and find attached files");
            if(myMessage.getFrom()[0].toString().contains(fromOnEmail) && myMessage.getAllRecipients()[0].toString().contains(toOnEmail))
            {
                Multipart myMultipart = (Multipart) myMessage.getContent();
                for (int i = 1; i < myMultipart.getCount(); i++) {
                    BodyPart bodyPart = myMultipart.getBodyPart(i);
                    System.out.println("I: " + i);
                    System.out.println("Data Handler: " + myMessage.getReceivedDate());
                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.UK);
                    Date date = format.parse(format.format(file.lastModified()));
                    System.out.println("Message Date: " + format.format(myMessage.getReceivedDate()) + " File Date: " + format.format(date));
                    if(myMessage.getReceivedDate().after(date)) {
                        System.out.println("Data is after");
                        System.out.println("File Name: " + bodyPart.getFileName());
                        if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
                                !StringUtils.isNotBlank(bodyPart.getFileName())) {
                            continue; // dealing with attachments only
                        }
                        InputStream is = bodyPart.getInputStream();
                        File f = new File(bodyPart.getFileName());
                        FileOutputStream fos = new FileOutputStream(f);
                        byte[] buf = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buf)) != -1) {
                            fos.write(buf, 0, bytesRead);
                        }
                        fos.close();
                        attachments.add(f);
                        System.out.println("Test Window!");
                    } else {
                        System.out.println("File is not after Date!!!!!!!!!!!!!!!");
                    }
                }
                System.out.println("My test is worked");
                System.out.println("From: " + myMessage.getFrom()[0]);
                System.out.println("To: " + myMessage.getAllRecipients()[0]);
            } else {
                System.out.println("Files not found");
            }
        }
    }
}