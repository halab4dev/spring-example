package com.github.halab4dev;

import com.github.halab4dev.domain.Role;
import com.github.halab4dev.domain.User;
import com.github.halab4dev.repository.role.RoleRepository;
import com.github.halab4dev.repository.user.UserRepository;
import com.github.halab4dev.repository.userrole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpringJdbcJooqApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcJooqApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		roleRepository.deleteAll();
		userRoleRepository.deleteAll();

		List<Role> roles = List.of(
				new Role("Admin"),
				new Role("User")
		);
		roleRepository.insertMany(roles);


		User user = new User();
		user.setName("halab");
		user.setNickNames(new ArrayList<>(List.of("apollo", "knight")));
		user.setRoles(new ArrayList<>(roles));

		userRepository.insert(user);
		userRoleRepository.saveUserRole(user);

		user.setName("Ha LAB");
		userRepository.update(user);

		User after = userRepository.findByIdWithRoles(user.getId());
		log.info("User from DB: {}", after);

		userRepository.delete(user);

		List<User> users = userRepository.findAll();
		log.info("users: {}", users);
	}
}
