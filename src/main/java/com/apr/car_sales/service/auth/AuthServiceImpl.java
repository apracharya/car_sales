package com.apr.car_sales.service.auth;

import com.apr.car_sales.dtos.auth.RegisterRequest;
import com.apr.car_sales.dtos.auth.RegisterResponse;
import com.apr.car_sales.exception.AlreadyExistsException;
import com.apr.car_sales.persistence.user.RoleEntity;
import com.apr.car_sales.persistence.user.RoleRepository;
import com.apr.car_sales.persistence.user.UserEntity;
import com.apr.car_sales.persistence.user.UserRepository;
import com.apr.car_sales.security.JwtService;
import com.apr.car_sales.service.BaseService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthServiceImpl extends BaseService implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthServiceImpl(UserDetailsService userDetailsService, AuthenticationManager manager, JwtService helper, AuthenticationManager authenticationManager, UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
        this.jwtService = helper;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void login(JwtRequest request, HttpServletResponse response) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserEntity user = (UserEntity) authenticate.getPrincipal();
        String token = jwtService.generateToken(user);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false) // Change to true in production if using HTTPS
                .path("/")
                .maxAge(86400)
                .sameSite("Lax")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.findByEmailIgnoreCase(request.getEmail()).isPresent()) {
            throw new AlreadyExistsException(request.getEmail() + " has already been registered.");
        }
        if(userRepository.findByUsernameIgnoreCase(request.getUsername()).isPresent()) {
            throw new AlreadyExistsException(request.getUsername() + " is not available.");
        }
        if(userRepository.findByPhone((request.getPhone())).isPresent()) {
            throw new AlreadyExistsException(request.getPhone() + " is not available.");
        }

      /*  if(!request.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{6,}$")) {
            throw new MismatchException("Password must be at least 6 characters and contain at least one uppercase, one lowercase, one number and one special character");
        }*/

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .isActive(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        RoleEntity userRole = findEntityByIdOrThrow("Role", 1001L, roleRepository::findById);
        user.setRoles(Set.of(userRole));

        UserEntity created = userRepository.save(user);

        return mapper.map(created, RegisterResponse.class);
    }

    @Override
    public JwtResponse loginHeader(JwtRequest request) {
        this.doAuthenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtService.generateToken(userDetails);
        return JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .build();
    }

    public void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password!");
        }
    }
}
