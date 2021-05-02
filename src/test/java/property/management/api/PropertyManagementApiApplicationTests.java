package property.management.api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import property.management.api.crud.PropertyRepository;
import property.management.api.model.*;
import property.management.api.service.ManagementService;
import property.management.api.utils.PropertySearchQuery;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PropertyManagementApiApplicationTests {


	@Autowired
	private ManagementService managementService;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private WebApplicationContext context;

	long createdPropertyID;

	private MockMvc mvc;


	@BeforeAll
	public void createPropertiesForTest(){
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();


		Property property = new Property();

		property.setLocation("SG");
		property.setGoal(PropertyFor.RENT);
		property.setSize(54.0f);
		property.setDeposit(BigDecimal.TEN);
		property.setFee(BigDecimal.ONE);
		property.setPrice(BigDecimal.TEN);
		property.setSeller(Seller.AGENT);
		property.setNegotiable(true);
		property.setStatus(PropertyStatus.PENDING);
		property.setBedrooms(1);
		property.setBathrooms(1);
		property.setFurnish(Furnish.FULL);
		property.setType(PropertyType.HIGHRISE);
		property.setDescription("Test property");

		managementService.createProperty(property);
		createdPropertyID = property.getId();
	}


	@Test
	void creationWithMissingData(){


		Property property = new Property();

		property.setLocation("SG");
		property.setGoal(PropertyFor.RENT);
		property.setSize(54.0f);
		property.setDeposit(BigDecimal.TEN);
		property.setFee(BigDecimal.ONE);
		property.setPrice(BigDecimal.TEN);
		property.setSeller(Seller.AGENT);
		property.setNegotiable(true);
		property.setStatus(PropertyStatus.PENDING);
		property.setBedrooms(1);
		property.setBathrooms(1);
		property.setFurnish(Furnish.FULL);
		property.setType(PropertyType.HIGHRISE);

		assert managementService.createProperty(property) == HttpStatus.BAD_REQUEST.value();
	}

	@Test
	void creationTest(){


		Property property = new Property();

		property.setLocation("SG");
		property.setGoal(PropertyFor.RENT);
		property.setSize(54.0f);
		property.setDeposit(BigDecimal.TEN);
		property.setFee(BigDecimal.ONE);
		property.setPrice(BigDecimal.TEN);
		property.setSeller(Seller.AGENT);
		property.setNegotiable(true);
		property.setStatus(PropertyStatus.PENDING);
		property.setBedrooms(1);
		property.setBathrooms(1);
		property.setFurnish(Furnish.FULL);
		property.setType(PropertyType.HIGHRISE);
		property.setDescription("Test property");


		assert managementService.createProperty(property) == HttpStatus.CREATED.value();
		assert property.getId() != 0l;


	}

	@Test
	void updateTest(){
		Property updatedValues = new Property();
		updatedValues.setId(createdPropertyID);
		updatedValues.setBedrooms(2);
		updatedValues.setDescription("Updated Description");



		assert managementService.updateProperty(updatedValues) == HttpStatus.OK.value();


		Optional<Property> property = propertyRepository.findById(createdPropertyID);

		assert property.isPresent();
		assert property.get().getBedrooms() == 2;
		assert property.get().getBathrooms() == 1;

	}

	@Test
	void approveTest(){

		Property updatedValues = new Property();
		updatedValues.setId(createdPropertyID);

		assert propertyRepository.findById(createdPropertyID).get().getStatus() == PropertyStatus.PENDING;
		assert managementService.approveProperty(updatedValues) == HttpStatus.OK.value();
		assert propertyRepository.findById(createdPropertyID).get().getStatus() == PropertyStatus.APPROVED;


	}

	@Test
	void searchTest(){
		PropertySearchQuery searchQuery = new PropertySearchQuery();
		searchQuery.setLocation("SG");

		assert managementService.searchProperty(searchQuery).size() > 0;
	}

	@Test
	@WithMockUser("admin")
	void authTest() throws Exception {
		mvc.perform(post("/api/manage/search").contentType(MediaType.APPLICATION_JSON).content("{}"))
			.andExpect(status().isOk());
	}





}
