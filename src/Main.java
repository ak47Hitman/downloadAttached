import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
//
//import org.apache.commons.lang.time.DateUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws MessagingException, IOException {
        // write your code here

        final String user = "aka47Hitman@gmail.com"; // имя пользователя
        final String pass = "sisi47sisi";    // пароль
        final String host = "imap.gmail.com";     // адрес почтового сервера


        // Создание свойств
        Properties props = new Properties();

        //включение debug-режима
        props.put("mail.debug", "false");

        //Указываем протокол - IMAP с SSL
        props.put("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props);
        Store store = session.getStore();

        //подключаемся к почтовому серверу
        store.connect(host, user, pass);

        //получаем папку с входящими сообщениями
        Folder inbox = store.getFolder("[Gmail]/Помеченные");
//        Folder inbox = store.getFolder("INBOX");

        //открываем её только для чтения
//        inbox.open(Folder.READ_ONLY);

        //получаем последнее сообщение (самое старое будет под номером 1)
//        Message m = inbox.getMessage(inbox.getMessageCount());
//
//        System.out.println(inbox.getMessageCount());
//        Multipart mp = (Multipart) m.getContent();
////        BodyPart bp = mp.getBodyPart(0);

//        for (int j = 0; j < mp.getCount(); j++) {
//            BodyPart bodyPart = mp.getBodyPart(j);
//            InputStream stream = bodyPart.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
//
//            while (br.ready()) {
//                System.out.println(br.readLine());
//            }
//        }

//открываем её только для чтения
        inbox.open(Folder.READ_ONLY);
        System.out.println(inbox.getMessageCount());
        //получаем последнее сообщение (самое старое будет под номером 1)
        Message m = inbox.getMessage(inbox.getMessageCount());
        System.out.println("Data message: " + m.getReceivedDate());
        System.out.println(m.getFolder());
        System.out.println(m.getContent());
        System.out.println(m.getContentType());
        System.out.println(m.getSentDate());
//        Multipart mp = (Multipart) m.getContent();

//        BodyPart bp = mp.getBodyPart(0);
        Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        List<File> attachments = new ArrayList<File>();
        System.out.println("Test: 0" + messages.length);
//        for (Message message : messages) {
            Multipart multipart = (Multipart) m.getContent();
            System.out.println("Test");

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
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
//            }
//        for (Message message : messages) {
//            Multipart multipart = (Multipart) message.getContent();
//            System.out.println("Test");
//
//            for (int i = 0; i < multipart.getCount(); i++) {
//                BodyPart bodyPart = multipart.getBodyPart(i);
//                System.out.println("File Name: " + bodyPart.getFileName());
//                if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) &&
//                        !StringUtils.isNotBlank(bodyPart.getFileName())) {
//                    continue; // dealing with attachments only
//                }
//                InputStream is = bodyPart.getInputStream();
//                File f = new File(bodyPart.getFileName());
//                FileOutputStream fos = new FileOutputStream(f);
//                byte[] buf = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = is.read(buf)) != -1) {
//                    fos.write(buf, 0, bytesRead);
//                }
//                fos.close();
//                attachments.add(f);
//            }
//        Message m2 = null; // полученое письмо
//        EMail email = new EMail();
//        int level = 0;
//
//        ProcessingPart(email, m2, level);


            //Выводим содержимое на экран
//            System.out.println(bp.getContent());

//        String POP_AUTH_USER = "aka47Hitman@gmail.com";
//        String POP_AUTH_PWD = "sisi47sisi";
//
//        String FOLDER_INDOX = "INBOX"; // имя папки "Входящие"
//        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//
//        Properties pop3Props = new Properties();
//
//        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
//        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
//        pop3Props.setProperty("mail.pop3.port", "995");
//        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
//
//        URLName url = new URLName("pop3", "pop.gmail.com", 995, "", POP_AUTH_USER, POP_AUTH_PWD);
//
//        Session session = Session.getInstance(pop3Props, null);
//        Store store = session.getStore(url);
//        store.connect();
//
//        Folder folder = store.getFolder(FOLDER_INDOX);
//        try {
//            folder.open(Folder.READ_WRITE);
//        } catch (MessagingException ex) {
//            folder.open(Folder.READ_ONLY);
//        }
//        Message[] messages = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
//// обработка сообщений
//        System.out.println(messages[0]);
//        folder.close(false);
//        store.close();
            System.out.println("Test Window!");
        }
    }
}

//    /**
//     * Обработка части письма
//     *
//     * @param email - объект EMail, который заполняеться из письма
//     * @param m - Часть письма
//     * @param level - Уровень структуры письма
//     * @throws MessagingException
//     * @throws IOException
//     */
//    private static void ProcessingPart(EMail email, Part m, Integer level)
//            throws MessagingException, IOException {
//        if (m instanceof Message)
//            ProcessingInfoPart(email, (Message) m);
//
//        String filename = m.getFileName();
//
//        if ((filename == null || filename == "") && m.isMimeType("text/plain")) {
//// Текст сообщения
//            email.setContent((String) m.getContent());
//        } else if (m.isMimeType("multipart/*")) {
//// Рекурсивный разбор иерархии
//            Multipart mp = (Multipart) m.getContent();
//            level++;
//            int count = mp.getCount();
//            for (int i = 0; i < count; i++) {
//                ProcessingPart(email, mp.getBodyPart(i), level);
//            }
//        } else if (m.isMimeType("message/rfc822")) {
//// Вложенное сообщение
//            level++;
//            ProcessingPart(email, (Part) m.getContent(), level);
//            level--;
//        }
//
//        if (level != 0 && !m.isMimeType("multipart/*") && filename != null) {
//// Сохранения атачей
//            String disp = m.getDisposition();
//// many mailers don't include a Content-Disposition
//            if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
//
//                List<EFile> efiles = email.getAttachedFile();
//                InputStream in = null;
//                try {
//                    in = ((MimeBodyPart) m).getInputStream();
////                    byte[] content = DateUtils.GetBytesFromStream(in);
//
//                    EFile efile = new EFile();
//                    efile.setContent(content);
//                    efile.setMimeType(((MimeBodyPart) m).getContentType());
//                    efile.setFileName(MimeUtility.decodeText(filename));
//                    efiles.add(efile);
//                } catch (IOException ex) {
//                    try {
//                        if (in != null)
//                            in.close();
//                    } catch (IOException e) {
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Обработка информационной части письма
//     *
//     * @param email - объект для сохранения email
//     * @param m - входящий объект письма
//     * @throws MessagingException
//     * @throws UnsupportedEncodingException
//     */
//    private static void ProcessingInfoPart(EMail email, Message m)
//            throws MessagingException, UnsupportedEncodingException {
//
//// Обработка основных параметров письма
//        AddAddress(m.getFrom(), email, false, true);
//        AddAddress(m.getRecipients(Message.RecipientType.TO), email, true,
//                false);
//
//        email.setSubject(m.getSubject());
//
//        Date d = (m.getSentDate());
//        if (d != null)
//            email.setSendDate(d);
//
//        String msgId = GetElementOrNull(m.getHeader("Message-Id"));
//        if (msgId != null)
//            email.setMessageId(msgId);
//
//        String refs = GetElementOrNull(m.getHeader("References"));
//        if (refs == null) {
//            refs = GetElementOrNull(m.getHeader("In-Reply-To"));
//        }
//        if (msgId != null) {
//            if (refs != null)
//                refs = MimeUtility.unfold(refs) + " " + msgId;
//            else
//                refs = msgId;
//        }
//        if (refs != null)
//            email.setReferences(MimeUtility.fold(12, refs));
//
//// Обработка флагов сообщения
//        Flags flags = m.getFlags();
//        Flags.Flag[] sf = flags.getSystemFlags();
//        for (int i = 0; i < sf.length; i++) {
//            Flags.Flag f = sf[i];
//            if (f == Flags.Flag.ANSWERED)
//                email.setEmailType(EMail.EMailType.Answered);
//            else if (f == Flags.Flag.DELETED)
//                email.setEmailType(EMail.EMailType.Deleted);
//            else if (f == Flags.Flag.DRAFT)
//                email.setEmailType(EMail.EMailType.Draft);
//            else if (f == Flags.Flag.FLAGGED)
//                email.setEmailType(EMail.EMailType.Flagged);
//            else if (f == Flags.Flag.RECENT)
//                email.setEmailType(EMail.EMailType.Recent);
//            else if (f == Flags.Flag.SEEN)
//                email.setEmailType(EMail.EMailType.Seen);
//            else
//                continue;
//        }
//
//// x-mail
////        String[] hdrs = m.getHeader("X-Mailer");
////        if (hdrs != null)
////            email.setXMailer(hdrs[0]);
////        else
////            email.setXMailer("");
//    }
//
//    /**
//     * Добавляет адрес получателя/отправителя
//     *
//     * @throws UnsupportedEncodingException
//     */
//    private static void AddAddress(Address[] address, EMail email,
//                                   Boolean addTo, Boolean addFrom) throws UnsupportedEncodingException {
//        if ((!addTo && !addFrom) || (addTo && addFrom)) {
//            throw new IllegalArgumentException(
//                    "Не установлен не один из флагов addTo, addFrom или оба установлены!");
//        }
//
//        List<String> result = new ArrayList<String>();
//        if (address == null || address.length == 0)
//            return;
//        for (int i = 0; i < address.length; i++)
//            result.add(MimeUtility.decodeText(address[i].toString()));
//        if (addTo)
//            email.setTo(result);
//        if (addFrom)
//            email.setFrom(result);
//    }
//
//    /**
//     * Возвращает первый не null элемент массива и если таких элементов нет, то
//     * null
//     *
//     * @param mas - массив
//     * @return Первый не null элемент массива и если таких элементов нет, то
//     *         null
//     */
//    private static String GetElementOrNull(String[] mas) {
//        if (mas != null && mas.length > 0) {
//            for (int i = 0; i < mas.length; i++)
//                if (mas[i] != null && mas[i] != "")
//                    return mas[i];
//        }
//        return null;
//    }
//}
