package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final NotificationTaskService notificationTaskService;

    private final TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskService notificationTaskService, TelegramBot telegramBot) {
        this.notificationTaskService = notificationTaskService;
        this.telegramBot = telegramBot;
    }

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
