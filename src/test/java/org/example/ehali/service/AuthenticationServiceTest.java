package org.example.ehali.service;

import org.example.ehali.dto.AuthRequest;
import org.example.ehali.dto.AuthenticationResponse;
import org.example.ehali.dto.RegisterRequest;
import org.example.ehali.entity.Kullanici;
import org.example.ehali.entity.Musteri;
import org.example.ehali.entity.Rol;
import org.example.ehali.repository.KullaniciRepository;
import org.example.ehali.repository.MusteriRepository;
import org.example.ehali.repository.SaticiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private KullaniciRepository repository;
    @Mock
    private MusteriRepository musteriRepository;
    @Mock
    private SaticiRepository saticiRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private AuthRequest authRequest;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@ehali.com");
        registerRequest.setSifre("12345");
        registerRequest.setAd("Emine");
        registerRequest.setSoyad("Test");
        registerRequest.setRol(Rol.MUSTERI);
        registerRequest.setAdres("Malatya");

        authRequest = new AuthRequest();
        authRequest.setEmail("test@ehali.com");
        authRequest.setPassword("12345");
    }

    @Test
    void register_EmailZatenVarsa_HataFirlatmali() {
        when(repository.findByEmail("test@ehali.com")).thenReturn(Optional.of(new Kullanici()));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authenticationService.register(registerRequest);
        });

        assertEquals("Bu e-posta adresi zaten kayıtlı!", exception.getMessage());
        verify(musteriRepository, never()).save(any());
    }

    @Test
    void register_YeniMusteri_BasariylaKaydetmeliVeTokenDonmeli() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(Kullanici.class))).thenReturn("mock-jwt-token");

        AuthenticationResponse response = authenticationService.register(registerRequest);

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getToken());
        verify(musteriRepository, times(1)).save(any(Musteri.class));
    }

    @Test
    void authenticate_GecerliBilgilerle_TokenDonmeli() {
        Kullanici user = new Kullanici();
        user.setEmail("test@ehali.com");

        when(repository.findByEmail("test@ehali.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("auth-token");

        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        assertNotNull(response);
        assertEquals("auth-token", response.getToken());
        verify(authenticationManager).authenticate(any());
    }
}