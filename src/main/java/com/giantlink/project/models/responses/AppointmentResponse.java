package com.giantlink.project.models.responses;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
	private Long id;

	private Date appointmentDate;

	private Date appointmentTime;
}
