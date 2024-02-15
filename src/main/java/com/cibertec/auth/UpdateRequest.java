package com.cibertec.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
	private String firstname;
	private String lastname;
	private String celular;
	private String country;
	private String dni;
}
