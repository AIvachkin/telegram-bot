package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.model.NotificationTask;

import java.util.List;

@Repository
public interface NotificationTaskRepository extends JpaRepository<NotificationTask, Long> {

    @Query (value = "SELECT * FROM notification_task WHERE date_and_time = date_trunc ('MINUTE', current_timestamp)", nativeQuery = true)
    List<NotificationTask> findByDateAndTime();



}


