package com.ipbsoft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = AppConfig.class)
@ContextConfiguration
@WebAppConfiguration
//@WebMvcTest
//@Import(SecurityConfig.class)
public class BasicAuthTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMVC;

	@Before
	public void setup() {
		mockMVC = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	public void everyone_can_access_public_resource_without_authentication() throws Exception {

		MvcResult result = mockMVC.perform(get("/resources/public")).andReturn();

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void not_authenticated_users_cannot_access_private_resource() throws Exception {

		//MvcResult result = mockMVC.perform(get("/resources/private")).andReturn();
		MvcResult result = mockMVC.perform(get("/resources/private")).andReturn();

		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
	}

	@Test
	@WithUserDetails(value = "ipbsoft", userDetailsServiceBeanName = "service")
	public void authenticated_users_can_access_private_resources() throws Exception {

		MvcResult result = mockMVC.perform(get("/resources/private")).andReturn();

		System.out.println(result.getResponse().getContentAsString());

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	@WithAnonymousUser
	public void not_valid_users_cannot_access_private_resources() throws Exception {

		MvcResult result = mockMVC.perform(get("/resources/private")).andReturn();

		System.out.println(result.getResponse().getContentAsString());

		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
	}

	@Test
	@WithUserDetails(value = "ipbsoft", userDetailsServiceBeanName = "service")
	public void normal_users_cannot_access_admin_resources() throws Exception {

		MvcResult result = mockMVC.perform(get("/resources/admin")).andReturn();

		System.out.println(result.getResponse().getContentAsString());

		assertEquals(HttpStatus.FORBIDDEN.value(), result.getResponse().getStatus());
	}

	@Test
	@WithUserDetails(value = "admin", userDetailsServiceBeanName = "service")
	public void admin_users_can_access_private_resources() throws Exception {

		MvcResult result = mockMVC.perform(get("/resources/private")).andReturn();

		System.out.println(result.getResponse().getContentAsString());

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	@WithUserDetails(value = "admin", userDetailsServiceBeanName = "service")
	public void admin_users_can_access_admin_resources() throws Exception {

		MvcResult result = mockMVC.perform(get("/resources/admin")).andReturn();

		System.out.println(result.getResponse().getContentAsString());

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

}
