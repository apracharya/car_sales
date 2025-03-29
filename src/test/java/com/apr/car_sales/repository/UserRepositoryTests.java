package com.apr.car_sales.repository;

import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserEntity createUser() {
        return UserEntity.builder()
                .firstName("Apurva")
                .lastName("Acharya")
                .email("apr.acharya@gmail.com")
                .username("apracharya")
                .password(passwordEncoder.encode("123Aa!"))
                .build();
    }

    @Test
    void UserRepository_Save_ReturnNotNull() {
        UserEntity user = createUser();
        UserEntity saved = userRepository.save(user);
        Assertions.assertThat(saved).isNotNull();
    }

    @Test
    void UserRepository_FindById_ReturnUser() {
        UserEntity user = createUser();
        UserEntity saved = userRepository.save(user);

        UserEntity found = userRepository.findById(saved.getId()).get();
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getId()).isPositive();
    }

    @Test
    void UserRepository_Update_ReturnNotNull() {
        UserEntity user = createUser();
        UserEntity saved = userRepository.save(user);

        UserEntity found = userRepository.findById(saved.getId()).get();
        found.setFirstName("Ayure");
        found.setLastName("Smith");

        UserEntity updated = userRepository.save(found);

        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.getId()).isEqualTo(saved.getId());
        Assertions.assertThat(updated.getFirstName()).isEqualTo("Ayure");
        Assertions.assertThat(updated.getLastName()).isEqualTo("Smith");

    }

    @Test
    void UserRepository_DeleteById_ReturnEmpty() {
        UserEntity user = createUser();
        UserEntity saved = userRepository.save(user);

        userRepository.deleteById(saved.getId());
        Optional<UserEntity> deletedUser = userRepository.findById(saved.getId());
        Assertions.assertThat(deletedUser).isEmpty();
    }

    @Test
    void UserRepository_Delete_ReturnEmpty() {
        UserEntity user = createUser();
        UserEntity saved = userRepository.save(user);
        UserEntity found = userRepository.findById(saved.getId()).get();

        userRepository.delete(found);
        Optional<UserEntity> deletedUser = userRepository.findById(saved.getId());
        Assertions.assertThat(deletedUser).isEmpty();
    }
}
