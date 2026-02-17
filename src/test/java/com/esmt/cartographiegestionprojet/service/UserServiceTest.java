package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.dto.UserDTO;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.mapper.UserMapper;
import com.esmt.cartographiegestionprojet.repository.UserRepository;
import com.esmt.cartographiegestionprojet.utils.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();

        // Créer un utilisateur de test
        testUser = new User();
        testUser.setId(testUserId);
        testUser.setNom("Diallo");
        testUser.setPrenom("Amadou");
        testUser.setEmail("amadou@esmt.sn");
        testUser.setRole(Role.CANDIDAT);
        testUser.setActif(true);

        // Créer le DTO correspondant
        testUserDTO = new UserDTO();
        testUserDTO.setId(testUserId);
        testUserDTO.setNom("Diallo");
        testUserDTO.setPrenom("Amadou");
        testUserDTO.setEmail("amadou@esmt.sn");
        testUserDTO.setRole(Role.CANDIDAT);
    }

    @Test
    void testGetAllUsers_Success() {
        // GIVEN
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(any(User.class))).thenReturn(testUserDTO);

        // WHEN
        List<UserDTO> result = userService.getAllUsers();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Amadou", result.get(0).getPrenom());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Success() {
        // GIVEN
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // WHEN
        UserDTO result = userService.getUserById(testUserId);

        // THEN
        assertNotNull(result);
        assertEquals(testUserId, result.getId());
        assertEquals("amadou@esmt.sn", result.getEmail());
        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void testGetUserById_NotFound() {
        // GIVEN
        UUID nonExistentId = UUID.randomUUID();
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(nonExistentId);
        });
    }

    @Test
    void testChangeUserRole_Success() {
        // GIVEN
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toDTO(any(User.class))).thenReturn(testUserDTO);

        // WHEN
        UserDTO result = userService.changeUserRole(testUserId, Role.GESTIONNAIRE);

        // THEN
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserByEmail_Success() {
        // GIVEN
        when(userRepository.findByEmail("amadou@esmt.sn")).thenReturn(Optional.of(testUser));
        when(userMapper.toDTO(testUser)).thenReturn(testUserDTO);

        // WHEN
        UserDTO result = userService.getUserByEmail("amadou@esmt.sn");

        // THEN
        assertNotNull(result);
        assertEquals("amadou@esmt.sn", result.getEmail());
    }
}