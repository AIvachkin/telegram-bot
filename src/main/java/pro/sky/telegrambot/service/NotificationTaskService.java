package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.TelegramBotUpdatesListener;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationTaskService {

    private final NotificationTaskRepository notificationTaskRepository;

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Autowired
    private TelegramBot telegramBot;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public int processTheMessage(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Long chatId = update.message().chat().id();
            String userText = update.message().text();

            Matcher matcher = pattern.matcher(userText);

            if (update.message().text().equals("/start")) {
                SendMessage message = new SendMessage(update.message().chat().id(), "Привет! Начинаем работу!");
                SendResponse response = telegramBot.execute(message);
                if (response.isOk()) {
                    logger.info("Сообщение пользователю было доставлено!");
                } else {
                    logger.error("Сообщение пользователю не было доставлено. Код ошибки: " + response.errorCode());
                }
            }

            if (matcher.matches()) {
                String dateTime = matcher.group(1);
                String text = matcher.group(3);
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);

                NotificationTask notificationTask = new NotificationTask(chatId, text, localDateTime);
                logger.info("ChatID: " + chatId + " ; Дата и время: " + localDateTime + " ; Напоминание: " + text);
//                System.out.println(notificationTask);
                notificationTaskRepository.save(notificationTask);
            }
            
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }
    @Scheduled(cron = "0 0/1 * * * *")
//    public List<NotificationTask> searchRecordsByCurrentMinute() {
    public void searchRecordsByCurrentMinute() {
//        logger.info("Создан запрос в БД на поиск событий");
//        System.out.println(notificationTaskRepository.findByDateAndTime());
        List<NotificationTask> searchRecords = notificationTaskRepository.findByDateAndTime();
//        if(!searchRecords.isEmpty()){
            for (NotificationTask task: searchRecords)
            {
                SendMessage message = new SendMessage(task.getChatId(),
                        "Задача: " + task.getResponse() + "; Время исполнения: " + task.getDateAndTime().toString());
//                SendResponse response = telegramBot.execute(message);
                telegramBot.execute(message);
            }
//        }
    }
}
