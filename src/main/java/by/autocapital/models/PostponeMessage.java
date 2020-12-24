package by.autocapital.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "postpone_message")
public class PostponeMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Text", nullable = false, columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "PictureUrl", columnDefinition = "LONGTEXT")
    private String pictureUrl;

    @Column(name = "Date")
    private Date date;

    @Column(name = "ViberId", nullable = false)
    private String viberId;

    @Column(name = "IsLast", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean isLast;
}
