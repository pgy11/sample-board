package simple.practice.board.entity;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 8, max = 30)
    @Column(nullable = false,
            updatable = false,
            unique = true,
            length = 30
    )
    private String userId;

    @Length(min = 8, max = 30)
    @Column(nullable = false, length = 30)
    private String password;

    @Column(nullable = false,
            unique = true,
            updatable = false)
    private String email;

}
