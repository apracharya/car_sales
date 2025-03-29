package com.apr.car_sales;

import com.apr.car_sales.persistence.user.RoleEntity;
import com.apr.car_sales.persistence.user.RoleRepository;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;


//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@Slf4j
public class CarSalesApplication  implements CommandLineRunner {

	private final RoleRepository roleRepository;

    public CarSalesApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(CarSalesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			RoleEntity adminRole = new RoleEntity();
			adminRole.setId(501L);
			adminRole.setName("ROLE_ADMIN");

			RoleEntity userRole = new RoleEntity();
			userRole.setId(1001L);
			userRole.setName("ROLE_USER");

			Set<RoleEntity> roles = Set.of(adminRole, userRole);
			List<RoleEntity> result = roleRepository.saveAll(roles);

			result.forEach(r -> log.info(r.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
