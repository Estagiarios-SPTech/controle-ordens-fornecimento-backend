package com.stefanini.controle_de_ofs.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserTest {
    @Test
    void testUserConstructor() {
        User testUser = new User(1, "Fulano", "fulano@gmail.com", "colaborador", "senha123");

        assertEquals(1, testUser.getId());
        assertEquals("Fulano", testUser.getName());
        assertEquals("fulano@gmail.com", testUser.getEmail());
        assertEquals("colaborador", testUser.getRole());
        assertEquals("senha123", testUser.getPassword());
    }
}