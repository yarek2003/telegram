package pro.sky.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Setter
@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_task_id")
    private Long id;
    @Column(name = "chat_id")
    private Long idChat;
    @Column(name = "text_message")
    private String notifyText;
    @Column(name = "schedule_date_time")
    private LocalDateTime dateTime;

    public NotificationTask() {
    }

    public NotificationTask(Long idChat, String notifyText, LocalDateTime dateTime) {
        this.idChat = idChat;
        this.notifyText = notifyText;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getNotifyText() {
        return notifyText;
    }

    public void setNotifyText(String notifyText) {
        this.notifyText = notifyText;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return id.equals(that.id) && idChat.equals(that.idChat) && notifyText.equals(that.notifyText) && dateTime.equals(that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idChat, notifyText, dateTime);
    }
}
