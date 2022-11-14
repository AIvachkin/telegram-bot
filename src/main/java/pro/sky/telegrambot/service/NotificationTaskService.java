package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TelegramBot telegramBot;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public int processTheMessage(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message messageEmployee = update.message();
            Long chatId = update.message().chat().id();
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            Matcher matcher = pattern.matcher("01.01.2022 20:00 Сделать домашнюю работу");

            if (update.message().text().equals("/start")) {
                SendMessage message = new SendMessage(update.message().chat().id(), "Привет! Начинаем работу!");
                SendResponse response = telegramBot.execute(message);
                if (response.isOk()) {
                    System.out.println("Сообщение пользователю было доставлено!");
                } else {
                    System.out.println("Сообщение пользователю не было доставлено. Код ошибки: " + response.errorCode());
                }
            }

            if (matcher.matches()){
                String dateTime = matcher.group(1);
                String text = matcher.group(3);
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime,
                        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                NotificationTask notificationTask = new NotificationTask(chatId, text, localDateTime);
                System.out.println("ChatID: " + chatId + " ; Дата и время: " + localDateTime + " ; Напоминание: " + text);
                System.out.println(notificationTask);
                notificationTaskRepository.save(notificationTask);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }
}
