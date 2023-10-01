package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.BotListenerRepository;
import pro.sky.telegrambot.tasks.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private NotificationTaskService notificationTaskService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String text = update.message().text();
            Long chatId = update.message().chat().id();

            if (text.equals("/start")) {
                SendMessage sendMessage = new SendMessage(update.message().chat().id(), "Hi");
                telegramBot.execute(sendMessage);
                logger.info("Sending message");
            } else {
                Pattern pattern = Pattern.compile("^([\\d.:\\s]{16})(\\s)(.+)$");
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    String dateAndTime = matcher.group(1);
                    String notifyText = matcher.group(3);
                    LocalDateTime localDateTime = LocalDateTime.parse(dateAndTime,
                            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
                    notificationTaskService.addNewTask(localDateTime, notifyText, chatId);
                    SendMessage sendMessage = new SendMessage(chatId, "Your message is sent");
                    telegramBot.execute(sendMessage);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduleChecker() {
        List<NotificationTask> notificationTasks = notificationTaskService.findByDateTime();
        for (NotificationTask task : notificationTasks) {
            SendMessage sendMessage = new SendMessage(task.getIdChat(), task.getNotifyText());
            telegramBot.execute(sendMessage);
            logger.info("Notification is sent");
        }
    }
}