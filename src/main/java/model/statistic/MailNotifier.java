package model.statistic;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Player;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class MailNotifier implements INotifier {
    private static final String FROM = "bagnogamestudio@gmail.com";
    private static final String USERNAME = "bagnogamestudio";
    private static final String PASSWORD = "M1a2s3t4e5r6^";
    private final Session session;


    public MailNotifier() {

        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                }
        );
    }

    @Override
    public void sendNotification(String message, Player player) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    Message mail = new MimeMessage(session);
                    mail.setFrom(new InternetAddress(FROM));
                    mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(player.getEmail()));
                    mail.setSubject(message);

                    mail.setText(getMailContent(message, player.getName(), null));

                    Transport.send(mail);
                } catch (MessagingException e) {
                    System.out.println("Encountered problem with sending email notification to: "
                            + player.getName() + " : " + player.getEmail());
                }
            }
        };
        thread.start();
    }

    @Override
    public <T> void sendNotification(String message, Player player, List<T> list) {
        try {
            final int[] i = {0};

            List<String> gamesNotes = list.stream()
                    .map(item -> {
                        i[0] += 1;
                        return String.format("%-5d %-100s\n",
                                i[0], item.toString());
                    })
                    .collect(Collectors.toList());
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress(FROM));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(player.getEmail()));
            mail.setSubject(message);

            mail.setText(getMailContent(message, player.getName(), gamesNotes));

            Transport.send(mail);

        } catch (MessagingException e) {
            System.out.println("Encountered problem with sending email notification to: "
                    + player.getName() + " : " + player.getEmail());
        }
    }

    @Override
    public void sendNotification(String message, Observable<Player> players) {
        players.subscribeOn(Schedulers.newThread())
                .subscribe(player -> sendNotification(message, player));
    }


    @Override
    public <T> void sendNotification(String message, Observable<Player> players, List<T> list) {
        players.subscribeOn(Schedulers.newThread())
                .subscribe(player -> sendNotification(message, player, list));
    }

    private String getMailContent() {
        return """
                Dear player,
                You got this notification, because some problem occurred. Pleas log into your MasterMind game account and verify all important communicates.
                                    
                                  
                Yours sincerely,
                BagnoGameStudio game development
                """;
    }

    private String getMailContent(String message) {
        return """
                Dear player,
                You got this notification, because we want provide you with important information:
                                
                """ + message + """
                    
                Yours sincerely,
                BagnoGameStudio game development
                """;
    }


    private String getMailContent(String message, String name, List<String> notes) {

        StringBuilder mailContent = new StringBuilder("Dear " + name + "!\n\n"
                + message + System.lineSeparator());

        if (notes != null) {
            mailContent.append("\n");
            for (String note : notes) {
                mailContent.append(note);
            }
            mailContent.append("\n");
        }

        return mailContent + ("\nYours sincerely,\n" +
                "BagnoGameStudio game development");
    }
}
