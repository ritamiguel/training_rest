package com.accenture.training.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.accenture.training.MyApplication;
import com.accenture.training.dto.ClientsDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MyApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientsControllerTest {

	@Autowired
	private ClientsController controller;

	// Mock testing variables
	private static MockMvc mockMvc;
	private static ObjectMapper mapper;
	private static ClientsDTO client;

	private static void getProductTest() {
		ClientsDTO clientsDTO = new ClientsDTO();
		clientsDTO.setName("Client Test");
		clientsDTO.setAge(20);
		clientsDTO.setFamilyName("FamilyNameTest");
		client = clientsDTO;
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		getProductTest();
	}

	@Before
	public void setUpBefore() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void aa_saveClient() throws UnsupportedEncodingException, Exception {
		final byte[] clientAsByteArray = mapper.writeValueAsBytes(client);

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/Clients")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(clientAsByteArray);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final ClientsDTO objResult = mapper.readValue(result, ClientsDTO.class);

		assertThat(objResult.getId()).isNotEmpty();
		client.setId(objResult.getId());
	}
	
	@Test
	public void ab_changeClient() throws UnsupportedEncodingException, Exception {
		String newName = "Produto Teste Alterado";
		client.setName(newName);
		String newFamilyName = "Family Name Alterado";
		client.setFamilyName(newFamilyName);
		
		final byte[] clientAsByteArray = mapper.writeValueAsBytes(client);

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/Clients/"+client.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(clientAsByteArray);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final ClientsDTO objResult = mapper.readValue(result, ClientsDTO.class);

		assertThat(objResult.getId()).isEqualTo(client.getId());
		assertThat(objResult.getName()).isEqualTo(newName);
		assertThat(objResult.getFamilyName()).isEqualTo(newFamilyName);
	}
	
	@Test
	public void ac_getAllClients() throws UnsupportedEncodingException, Exception {
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/Clients")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final List<ClientsDTO> objResult = Arrays.asList(mapper.readValue(result, ClientsDTO[].class));
		assertThat(objResult.size()).isGreaterThan(0);
	}
	
	@Test
	public void ad_getOneClient() throws UnsupportedEncodingException, Exception {
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/Clients/"+client.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final ClientsDTO objResult = mapper.readValue(result, ClientsDTO.class);
		assertThat(objResult.getId()).isEqualTo(client.getId());
	}
	
	@Test
	public void az_deleteClient() throws UnsupportedEncodingException, Exception {
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/Clients/"+client.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
				.getContentAsString();

	}
}
