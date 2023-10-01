package pro.sky.telegrambot.tasks;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.BotListenerRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationTaskService {
    private BotListenerRepository botListenerRepository;
    public NotificationTaskService(BotListenerRepository botListenerRepository) {
        this.botListenerRepository = botListenerRepository;
    }
    public void addNewTask(LocalDateTime localDateTime, String allSymbols, Long chatID) {
        botListenerRepository.save(new NotificationTask(chatID, allSymbols, localDateTime));
    }

    public List<NotificationTask> findByDateTime() {
        return botListenerRepository.findByDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}
