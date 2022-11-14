package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue
    private long id;

    private long chatId;
    private String response;
    private LocalDateTime dateAndTime;

    public NotificationTask(long id, long chatId, String response, LocalDateTime dateAndTime) {
        this.id = id;
        this.chatId = chatId;
        this.response = response;
        this.dateAndTime = dateAndTime;
    }

    public NotificationTask() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id == that.id && chatId == that.chatId && Objects.equals(response, that.response) && Objects.equals(dateAndTime, that.dateAndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, response, dateAndTime);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
