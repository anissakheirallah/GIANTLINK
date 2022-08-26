package com.giantlink.project.mappers;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.giantlink.project.entities.Appointment;
import com.giantlink.project.models.requests.AppointmentRequest;
import com.giantlink.project.models.responses.AppointmentResponse;

@Mapper
public interface AppointmentMapper {

	AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

	AppointmentResponse mapEntity(Appointment entity);

	Appointment mapRequest(AppointmentRequest entity);

	Appointment mapResponse(AppointmentResponse entity);

	Set<AppointmentResponse> mapResponses(Set<Appointment> entities);

	List<AppointmentResponse> mapResponses(List<Appointment> findAll);
}
