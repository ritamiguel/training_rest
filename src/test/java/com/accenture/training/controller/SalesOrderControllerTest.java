package com.accenture.training.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
import com.accenture.training.dto.ProductsDTO;
import com.accenture.training.dto.SalesOrderDTO;
import com.accenture.training.dto.SalesOrderItemDTO;
import com.accenture.training.dto.UserDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MyApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SalesOrderControllerTest {

	@Autowired
	private SalesOrderController controller;

	// Mock testing variables
	private static MockMvc mockMvc;
	private static ObjectMapper mapper;
	private static SalesOrderDTO salesOrder;

	private static void getProductTest() {
		ClientsDTO clientsDTO = new ClientsDTO();
		clientsDTO.setName("client Test");
		clientsDTO.setAge(22);
		clientsDTO.setId("1");
		clientsDTO.setFamilyName("Fernandes");
		
		UserDTO userDTO = new UserDTO();
		userDTO.setName("user Test");
		userDTO.setId("1");
		
		ProductsDTO productsDTO = new ProductsDTO();
		productsDTO.setName("Smartphone");
		productsDTO.setQuantity(24);
		productsDTO.setSalesPrice(1265);
		productsDTO.setBasePrice(2345);
		productsDTO.setManufacturer("Samsung");
		productsDTO.setId("42bed363-bbfb-4e92-a7ff-bdf0b1275bbe");
		
		SalesOrderItemDTO so_items = new SalesOrderItemDTO();
		so_items.setProducts(productsDTO);
		so_items.setSalesOrder("1");
		so_items.setStatus("C");
		so_items.setId("2");
		
		
		List<SalesOrderItemDTO> salesOrderItemDTOs = new ArrayList<SalesOrderItemDTO>();
		salesOrderItemDTOs.add(so_items);
		
		SalesOrderDTO salesOrderDTO = new SalesOrderDTO();
		salesOrderDTO.setItems(salesOrderItemDTOs);
		salesOrderDTO.setUserID(userDTO);
		salesOrderDTO.setClientID(clientsDTO);
		salesOrderDTO.setStatus("C");
		salesOrderDTO.setId("1");
		
		
		salesOrder = salesOrderDTO;
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
	public void aa_saveSalesOrder() throws UnsupportedEncodingException, Exception {
		final byte[] salesOrderAsByteArray = mapper.writeValueAsBytes(salesOrder);

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/SalesOrder")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(salesOrderAsByteArray);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final SalesOrderDTO objResult = mapper.readValue(result, SalesOrderDTO.class);

		assertThat(objResult.getId()).isNotEmpty();
		salesOrder.setId(objResult.getId());
	}
	
	@Test
	public void ab_changeSalesOrder() throws UnsupportedEncodingException, Exception {
		String newStatus = "D";
		salesOrder.setStatus(newStatus);
		
		final byte[] salesOrdeAsByteArray = mapper.writeValueAsBytes(salesOrder);

		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/SalesOrder/"+salesOrder.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(salesOrdeAsByteArray);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final SalesOrderDTO objResult = mapper.readValue(result, SalesOrderDTO.class);

		assertThat(objResult.getId()).isEqualTo(salesOrder.getId());
		assertThat(objResult.getStatus()).isEqualTo(newStatus);
	}
	
	@Test
	public void ac_getAllSalesOrder() throws UnsupportedEncodingException, Exception {
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/SalesOrder")
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final List<SalesOrderDTO> objResult = Arrays.asList(mapper.readValue(result, SalesOrderDTO[].class));
		assertThat(objResult.size()).isGreaterThan(0);
	}
	
	@Test
	public void ad_getOneSale() throws UnsupportedEncodingException, Exception {
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/SalesOrder/"+salesOrder.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		final String result = mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value()))
				.andReturn().getResponse().getContentAsString();

		assertThat(result).isNotNull();
		assertThat(result).isNotEmpty();

		final SalesOrderDTO objResult = mapper.readValue(result, SalesOrderDTO.class);
		assertThat(objResult.getId()).isEqualTo(salesOrder.getId());
		
		assertThat(objResult.getItems().size()).isGreaterThan(0);
		assertThat(objResult.getItems().get(0).getProducts().equals("Samsung"));
	}
	
	@Test
	public void az_deleteSalesOrder() throws UnsupportedEncodingException, Exception {
		final MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/SalesOrder/"+salesOrder.getId())
				.characterEncoding(StandardCharsets.UTF_8.name()).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(request).andDo(print()).andExpect(status().is(HttpStatus.OK.value())).andReturn().getResponse()
				.getContentAsString();

	}
}
