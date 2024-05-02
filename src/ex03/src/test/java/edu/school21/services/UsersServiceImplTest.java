package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    UsersServiceImpl service;
    @Mock
    UsersRepository repository;

    @BeforeEach
    void init() {
        service = new UsersServiceImpl(repository);
    }

    @Test
    void checkAuthenticate() {
        String login = "123";
        String password = "123";
        User user = new User(1L, login, password, false);
        Mockito.when(repository.findByLogin(login)).thenReturn(user);
        Assertions.assertTrue(service.authenticate(login, password));
    }

    @Test
    void checkAuthenticateFail() {
        String login = "123";
        String password = "1234";
        User user = new User(1L, login, password, false);
        Mockito.when(repository.findByLogin(login)).thenReturn(user);
        Assertions.assertThrows(RuntimeException.class,
                () -> service.authenticate(login, "123"));
    }

    @Test
    void checkAuthenticateFailAlreadyAuthenticated() {
        String login = "123";
        String password = "1234";
        User user = new User(1L, login, password, true);
        Mockito.when(repository.findByLogin(login)).thenReturn(user);
        Assertions.assertThrows(AlreadyAuthenticatedException.class,
                () -> service.authenticate(login, password));
    }
}
