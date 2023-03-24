package com.example.shoutboxapp.messageapi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    private Long id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String author;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;
    private LocalDateTime publishedTime = LocalDateTime.now();

    public Message(String author, String message) {
        this.author = author;
        this.message = message;
    }

    /**
     * Calculate and return formatted string with information when message was sent. For example "35 seconds ago".
     * @return String with time since published
     */
    public String getTimeSincePublished() {
        LocalDateTime now = LocalDateTime.now();
        Long diff = ChronoUnit.YEARS.between(publishedTime, now);
        if (diff >= 1) {
            if (diff == 1) {
                return String.format("%s year ago", diff);
            }
            return String.format("%s years ago", diff);
        }

        diff = ChronoUnit.MONTHS.between(publishedTime, now);
        if (diff >= 1) {
            if (diff == 1) {
                return String.format("%s month ago", diff);
            }
            return String.format("%s months ago", diff);
        }

        diff = ChronoUnit.DAYS.between(publishedTime, now);
        if (diff >= 1) {
            if (diff == 1) {
                return String.format("%s day ago", diff);
            }
            return String.format("%s days ago", diff);
        }

        diff = ChronoUnit.HOURS.between(publishedTime, now);
        if (diff >= 1) {
            if (diff == 1) {
                return String.format("%s hour ago", diff);
            }
            return String.format("%s hours ago", diff);
        }

        diff = ChronoUnit.MINUTES.between(publishedTime, now);
        if (diff >= 1) {
            if (diff == 1) {
                return String.format("%s minute ago", diff);
            }
            return String.format("%s minutes ago", diff);
        }

        // if nothing don't fit time have to be less than minute
        diff = ChronoUnit.SECONDS.between(publishedTime, now);
        if (diff == 1) {
            return String.format("%s second ago", diff);
        }
        return String.format("%s seconds ago", diff);
    }
}
