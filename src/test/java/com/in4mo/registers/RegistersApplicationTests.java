package com.in4mo.registers;

import com.in4mo.registers.dao.RegistryRepository;
import com.in4mo.registers.model.Registry;
import com.in4mo.registers.rest.dto.RegistryRechargeDto;
import com.in4mo.registers.rest.dto.RegistryTransferDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistersApplicationTests {

	private static final String TRANSFER_URL = "/transfer";
	private static final String RECHARGE_URL = "/recharge";

	private static final String fromRegistry = "Wallet";
	private static final String toRegistry = "Savings";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RegistryRepository registryRepository;

	@Test
	void checkHealth() {
		assertThat(this.restTemplate.getForObject(getDefaultUrl() + "/health", String.class)).contains("OK");
	}

	@Test
	void checkRecharge() {
		Long amount = 1000L;
		Registry walletBefore = registryRepository.findOneByName(fromRegistry).orElse(null);

		RegistryRechargeDto request = new RegistryRechargeDto(fromRegistry, amount);
		ResponseEntity<Void> response = this.restTemplate.postForEntity(getDefaultUrl() + RECHARGE_URL, request, Void.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		Registry walletAfter = registryRepository.findOneByName(fromRegistry).orElse(null);
		assertThat(walletAfter.getAmount()).isEqualTo(walletBefore.getAmount() + amount);
	}

	@Test
	void checkTransfer() {
		Long fromRegistryAmountBefore = registryRepository.findOneByName(fromRegistry).map(Registry::getAmount).orElse(null);
		Long toRegistryAmountBefore = registryRepository.findOneByName(toRegistry).map(Registry::getAmount).orElse(null);
		Long amount = 10L;

		RegistryTransferDto request = RegistryTransferDto.builder()
				.fromRegistry(fromRegistry)
				.toRegistry(toRegistry)
				.amount(amount)
				.build();
		ResponseEntity<Void> response = this.restTemplate.postForEntity( getDefaultUrl() + TRANSFER_URL, request, Void.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		Registry fromRegistryAfter = registryRepository.findOneByName(fromRegistry).orElse(null);
		Registry toRegistryAfter = registryRepository.findOneByName(toRegistry).orElse(null);
		assertThat(fromRegistryAfter.getAmount()).isEqualTo(fromRegistryAmountBefore - amount);
		assertThat(toRegistryAfter.getAmount()).isEqualTo(toRegistryAmountBefore + amount);
	}

	@Test
	void invalidRegistryName() {
		String fromRegistry = "NotExistingRegistry";

		RegistryRechargeDto request = new RegistryRechargeDto(fromRegistry, 10L);
		ResponseEntity<String> response = this.restTemplate.postForEntity( getDefaultUrl() + RECHARGE_URL, request, String.class);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo(String.format("Registry with name: '%s' was not found", fromRegistry));
	}

	@Test
	void invalidAmount() {
		Long amount = registryRepository.findOneByName(fromRegistry).map(Registry::getAmount).orElse(null);

		RegistryTransferDto request = RegistryTransferDto.builder()
				.fromRegistry(fromRegistry)
				.toRegistry(toRegistry)
				.amount(amount+1)
				.build();
		ResponseEntity<String> response = this.restTemplate.postForEntity( getDefaultUrl() + TRANSFER_URL, request, String.class);
		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).isEqualTo(String.format("Insufficient Funds (%d) for registry: %s", amount+1, fromRegistry));
	}

	private String getDefaultUrl() {
		return "http://localhost:" + port;
	}

}
