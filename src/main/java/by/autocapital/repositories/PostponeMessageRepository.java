package by.autocapital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import by.autocapital.models.PostponeMessage;

public interface PostponeMessageRepository extends JpaRepository<PostponeMessage, Long> {
    public List<PostponeMessage> findByViberId(String viberId);
}
