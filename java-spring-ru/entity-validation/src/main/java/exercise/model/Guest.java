package exercise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;




@Entity
@Table(name = "guests")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // BEGIN
    @Column
    @NotNull(message = "Имя пользователя должно быть не пустым")
    private String name;

    @Column
    @Email(message = "Электронная почта должна быть валидной")
    private String email;

    @Column
    @Size(min = 11, max = 13)
    @Pattern(regexp = "\\G[+][\\d]{11,13}", message = "Номер телефона должен начинаться с символа + и содержать от 11 до 13 цифр")
    private String phoneNumber;

    @Column
    @Pattern(regexp = "[\\d]{4}", message = "Номер клубной карты должен состоять ровно из четырех цифр")
    private String clubCard;

    @Column
    @Future(message = "Срок действия клубной карты должен быть еще не истекшим")
    private LocalDate cardValidUntil;
    // END

    @CreatedDate
    private LocalDate createdAt;
}
