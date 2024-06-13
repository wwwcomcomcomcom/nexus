package avengers.nexus.shared.entity;

import avengers.nexus.utils.StringListConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long id;
    private String name;
    private String password;

    @Convert(converter = StringListConverter.class)
    private List<String> followers;
    @Convert(converter = StringListConverter.class)
    private List<String> followings;
}
