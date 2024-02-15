package com.cibertec.auth;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.auth.exceptions.UsuarioExistenteException;
import com.cibertec.auth.exceptions.UsuarioNoEncontradoException;
import com.cibertec.jwt.JwtService;
import com.cibertec.model.Role;
import com.cibertec.model.User;
import com.cibertec.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public AuthResponse login(LoginRequest request) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

			if (optionalUser.isPresent()) {
				UserDetails user = optionalUser.get();
				String token = jwtService.getToken(user);
				return AuthResponse.builder().token(token).build();
			} else {
				throw new UsuarioNoEncontradoException("Usuario no encontrado");
			}
		} catch (Exception e) {
			throw new RuntimeException("Error al iniciar sesión", e);
		}
	}

	public AuthResponse registerStudent(RegisterRequest request) {
		try {
			userRepository.findByUsername(request.getUsername()).ifPresent(existingUser -> {
				throw new UsuarioExistenteException("El correo electrónico ya está registrado");
			});

			User user = User.builder().username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword())).firstname(request.getFirstname())
					.lastname(request.getLastname()).dni(request.getDni()).country(request.getCountry())
					.celular(request.getCelular()).role(Role.STUDENT).build();

			userRepository.save(user);

			return AuthResponse.builder().token(jwtService.getToken(user)).build();
		} catch (UsuarioExistenteException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Error al registrar usuario", e);
		}
	}

	public AuthResponse registerAdmin(RegisterRequest request) {
		try {
			userRepository.findByUsername(request.getUsername()).ifPresent(existingUser -> {
				throw new UsuarioExistenteException("El correo electrónico ya está registrado");
			});

			User user = User.builder().username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword())).firstname(request.getFirstname())
					.lastname(request.getLastname()).dni(request.getDni()).country(request.getCountry())
					.celular(request.getCelular()).role(Role.ADMIN).build();

			userRepository.save(user);

			return AuthResponse.builder().token(jwtService.getToken(user)).build();
		} catch (UsuarioExistenteException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException("Error al registrar usuario", e);
		}
	}

	public ResponseEntity<String> updateUser(Integer userId, UpdateRequest updateRequest) {
		try {
			Optional<User> optionalUser = userRepository.findById(userId);

			if (optionalUser.isPresent()) {
				User existingUser = optionalUser.get();

				// Utiliza el builder de User para construir un nuevo objeto User con los campos
				// actualizados
				User updatedUser = User.builder().id(existingUser.getId()).username(existingUser.getUsername())
						.password(existingUser.getPassword()).firstname(updateRequest.getFirstname())
						.lastname(updateRequest.getLastname()).celular(updateRequest.getCelular())
						.country(updateRequest.getCountry()).dni(updateRequest.getDni()).role(existingUser.getRole())
						.build();

				userRepository.save(updatedUser);

				return ResponseEntity.status(HttpStatus.OK)
						.body("Usuario con ID " + userId + " ha sido actualizado correctamente.");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con ID " + userId);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al actualizar usuario: " + e.getMessage());
		}
	}
}