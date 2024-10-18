package exercise.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class GuestDTO {
    private long id;

    @NotNull(message = "Имя пользователя должно быть не пустым")
    private String name;

    @Email(message = "Электронная почта должна быть валидной")
    private String email;

    @Size(min = 11, max = 13)
    @Pattern(regexp = "\\G[+][\\d]{11,13}", message = "Номер телефона должен начинаться с символа + и содержать от 11 до 13 цифр")
    private String phoneNumber;

    @Pattern(regexp = "[\\d]{4}", message = "Номер клубной карты должен состоять ровно из четырех цифр")
    private String clubCard;

    @Future(message = "Срок действия клубной карты должен быть еще не истекшим")
    private LocalDate cardValidUntil;

    private LocalDate createdAt;
}
