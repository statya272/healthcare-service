package ru.netology.patient.service.medical;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicalServiceImplTest {

    @Test
    void checkBloodPressureSend() {
        String id = "user1";
        String message = String.format("Warning, patient with id: %s, need help", id);
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(id))
                .thenReturn(new PatientInfo(
                        id,
                        "Семен",
                        "Михайлов",
                        LocalDate.of(1982, 1, 16),
                        new HealthInfo(new BigDecimal("36.6"),
                                new BloodPressure(125, 78))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkBloodPressure(id, new BloodPressure(120, 80));

        Mockito.verify(alertService, Mockito.times(1)).send(message);
    }

    @Test
    void checkBloodPressureNotSend() {
        String id = "user1";
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(id))
                .thenReturn(new PatientInfo(
                        id,
                        "Семен",
                        "Михайлов",
                        LocalDate.of(1982, 1, 16),
                        new HealthInfo(new BigDecimal("36.6"),
                                new BloodPressure(125, 78))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkBloodPressure(id, new BloodPressure(125, 78));

        Mockito.verify(alertService, Mockito.times(0)).send(Mockito.anyString());
    }

    @Test
    void checkTemperatureNotSend() {
        String id = "user1";
        String message = String.format("Warning, patient with id: %s, need help", id);
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(id))
                .thenReturn(new PatientInfo(
                        id,
                        "Семен",
                        "Михайлов",
                        LocalDate.of(1982, 1, 16),
                        new HealthInfo(new BigDecimal("36.6"),
                                new BloodPressure(125, 78))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkTemperature(id, new BigDecimal("37.9"));

        Mockito.verify(alertService, Mockito.times(0)).send(message);

    }

    @Test
    void checkTemperatureSend() {
        String id = "user1";
        String message = String.format("Warning, patient with id: %s, need help", id);
        PatientInfoRepository repository = Mockito.mock(PatientInfoRepository.class);
        Mockito.when(repository.getById(id))
                .thenReturn(new PatientInfo(
                        id,
                        "Семен",
                        "Михайлов",
                        LocalDate.of(1982, 1, 16),
                        new HealthInfo(new BigDecimal("36.6"),
                                new BloodPressure(125, 78))));

        SendAlertService alertService = Mockito.mock(SendAlertService.class);

        MedicalService service = new MedicalServiceImpl(repository, alertService);
        service.checkTemperature(id, new BigDecimal("35.0"));

        Mockito.verify(alertService, Mockito.times(1)).send(message);

    }
}