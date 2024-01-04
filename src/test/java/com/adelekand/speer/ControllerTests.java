package com.adelekand.speer;

import com.adelekand.speer.enums.ERole;
import com.adelekand.speer.model.User;
import com.adelekand.speer.service.JwtService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ControllerTests extends TestInitializer {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	void init() {
		var password = passwordEncoder.encode("password");
		List<String[]> usersToBuild = List.of(
				new String[] {"johndoe", "john", "doe", password},
				new String[] {"janecrow", "jane", "crow", password},
				new String[] {"maryjane", "mary", "jane", password}
		);
		List<User> users = mongoTemplate.insertAll(dataBuilder.buildUsers(usersToBuild)).stream().toList();

		List<Object[]> notesToBuild = List.of(
				new Object[] {"Note  title 1", "Note content 1", users.get(0)},
				new Object[] {"Note  title 2", "Note content 2", users.get(0)},
				new Object[] {"Note  title 3", "Note content 3", users.get(0)},
				new Object[] {"Note  title 4", "Note content 4", users.get(0)},
				new Object[] {"Note  title 5", "Note content 5", users.get(1)},
				new Object[] {"Note  title 6", "Note content 6", users.get(1)}
		);
		mongoTemplate.insertAll(dataBuilder.buildNotes(notesToBuild));
	}

	@Test
	public void getAllNotes() throws Exception {
		var user = userRepository.findByUsername("johndoe").get();
		var token = jwtService.generateToken(user);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/api/notes").accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
				)
				.andDo(print())
				.andExpect(status().isOk());
	}
}
