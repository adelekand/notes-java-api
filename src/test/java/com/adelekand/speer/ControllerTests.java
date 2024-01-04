package com.adelekand.speer;

import com.adelekand.speer.dto.response.NoteResponse;
import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import com.adelekand.speer.service.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void getAllNotes() throws Exception {
		var initValue = initDB(2, 4);

		var savedUser = ((List<User>)initValue.get("users")).get(0);
		var savedNotes = (List<Note>)initValue.get("notes");

		var user = userRepository.findByUsername(savedUser.getUsername()).get();
		var token = jwtService.generateToken(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/notes").accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();

		String responseBody  = result.getResponse().getContentAsString();

		List<NoteResponse> returnedNotes = objectMapper.readValue(responseBody, new TypeReference<>() {});
		assertEquals(returnedNotes.size(), 4);
		assertEquals(returnedNotes.get(0).getId(), savedNotes.get(0).getId());
		assertEquals(returnedNotes.get(1).getId(), savedNotes.get(1).getId());
		assertEquals(returnedNotes.get(2).getId(), savedNotes.get(2).getId());
		assertEquals(returnedNotes.get(3).getId(), savedNotes.get(3).getId());
	}

	@Test
	public void getNoteById() throws Exception {
		var initValue = initDB(1, 1);

		var savedUser = ((List<User>)initValue.get("users")).get(0);
		var savedNote = ((List<Note>)initValue.get("notes")).get(0);

		var user = userRepository.findByUsername(savedUser.getUsername()).get();
		var token = jwtService.generateToken(user);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
						.get("/api/notes/" + savedNote.getId()).accept(MediaType.APPLICATION_JSON)
						.header("Authorization", "Bearer " + token)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andReturn();

		String responseBody  = result.getResponse().getContentAsString();

		NoteResponse returnedNote = objectMapper.readValue(responseBody, new TypeReference<>() {});
		assertEquals(returnedNote.getId(), savedNote.getId());
		assertEquals(returnedNote.getTitle(), savedNote.getTitle());
		assertEquals(returnedNote.getContent(), savedNote.getContent());
	}

	Map<String, Object> initDB(int numOfUsers, int numOfNotes) {
		Map<String, Object> returnValue = new HashMap<>();

		var password = passwordEncoder.encode("password");
		List<String[]> usersToBuild = new ArrayList<>();
		for (int i = 0; i < numOfUsers; i++) {
			usersToBuild.add(new String[] {"username"+i, "firstname"+i, "lastname"+i, password});
		}

		List<User> users = mongoTemplate.insertAll(dataBuilder.buildUsers(usersToBuild)).stream().toList();
		returnValue.put("users", users);

		List<Object[]> notesToBuild = new ArrayList<>();

		for (int i = 0; i < numOfNotes; i++) {
			notesToBuild.add(new Object[] {"Note  title " + i, "Note content " + i, users.get(0)});
		}

		List<Note> notes = mongoTemplate.insertAll(dataBuilder.buildNotes(notesToBuild)).stream().toList();
		returnValue.put("notes", notes);

		return returnValue;
	}
}
