package simple.practice.board.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Builder
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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,
            unique = true,
            updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    private Role role;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private String salt;

}
