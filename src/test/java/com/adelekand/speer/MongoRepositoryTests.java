package com.adelekand.speer;

import com.adelekand.speer.model.Note;
import com.adelekand.speer.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class MongoRepositoryTests extends TestInitializer {

	@Test
	public void testSaveAndFindUser() {
		var savedUser = buildUser();

		var queriedUser = userRepository.findByUsername(savedUser.getUsername()).get();
		assertEquals(savedUser.getId(), queriedUser.getId());
		assertEquals(savedUser.getFirstName(), queriedUser.getFirstName());
		assertEquals(savedUser.getLastName(), queriedUser.getLastName());
		assertEquals(savedUser.getUsername(), queriedUser.getUsername());
	}

	@Test
	public void testSaveAndFindNote() {
		var savedUser = buildUser();
		var savedNote = buildNote(savedUser);

		var queriedNote = noteRepository.findByIdAndCreatorOrSharedWith(savedNote.getId(), savedUser);
		assertEquals(savedNote.getId(), queriedNote.getId());
		assertEquals(savedNote.getTitle(), queriedNote.getTitle());
		assertEquals(savedNote.getContent(), queriedNote.getContent());
		assertEquals(savedNote.getCreator().getUsername(), queriedNote.getCreator().getUsername());
	}

	private User buildUser() {
		var user = User.builder()
				.username("johndoe")
				.firstName("john")
				.lastName("doe")
				.password("password")
				.build();

		return mongoTemplate.insert(user);
	}

	private Note buildNote(User user) {
		var note = Note.builder()
				.title("Sample Note Title")
				.content("Sample Note Content")
				.creator(user)
				.build();

		return mongoTemplate.insert(note);
	}
}
