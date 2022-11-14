package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final NotificationTaskService notificationTaskService;

    public TelegramBotUpdatesListener(NotificationTaskService notificationTaskService) {
        this.notificationTaskService = notificationTaskService;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
//        updates.forEach(update -> {
//            logger.info("Processing update: {}", update);
//            if (update.message().text().equals("/start")) {
//                SendMessage message = new SendMessage(update.message().chat().id(), "Привет! Начинаем работу!");
//                SendResponse response = telegramBot.execute(message);
//                if (response.isOk()) {
//                    System.out.println("Сообщение пользователю было доставлено!");
//                } else {
//                    System.out.println("Сообщение пользователю не было доставлено. Код ошибки: " + response.errorCode());
//                }
//            }
//        });
//        return UpdatesListener.CONFIRMED_UPDATES_ALL;
        return notificationTaskService.processTheMessage(updates);
    }

}
