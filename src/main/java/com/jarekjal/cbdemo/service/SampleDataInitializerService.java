package com.jarekjal.cbdemo.service;

import com.jarekjal.cbdemo.model.Address;
import com.jarekjal.cbdemo.model.Person;
import com.jarekjal.cbdemo.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleDataInitializerService {

    private final PersonRepo personRepo;

    public void init() {
        ZoneId zoneId = ZoneId.of("Europe/Warsaw");
        var effectiveFrom1 = ZonedDateTime.of(1985, 1, 1, 3, 2, 1, 0, zoneId);
        var effectiveTo1 = effectiveFrom1.plusYears(5);

        var effectiveFrom2 = ZonedDateTime.of(1980, 4, 10, 10, 25, 0, 0, zoneId);
        var effectiveTo2 = effectiveFrom2.plusYears(5);

        var effectiveFrom3 = ZonedDateTime.of(1983, 5, 17, 18, 30, 15, 0, zoneId);
        var effectiveTo3 = effectiveFrom3.plusYears(5);

        var effectiveFrom4 = ZonedDateTime.of(1947, 6, 17, 0, 1, 0, 0, zoneId);
        var effectiveTo4 = effectiveFrom4.plusYears(5);

        var effectiveFrom5 = ZonedDateTime.of(1984, 12, 3, 12, 59, 1, 0, zoneId);
        var effectiveTo5 = effectiveFrom5.plusYears(5);

        var effectiveFrom6 = ZonedDateTime.of(1981, 7, 13, 0, 0, 0, 0, zoneId);
        var effectiveTo6 = effectiveFrom6.plusYears(5);

        personRepo.saveAll(List.of(
                Person.builder()
                        .name("Domysław")
                        .surname("Domyślny")
                        .id("cowr")
                        .pid("1")
                        .mobile("+48999777555")
                        .aliases(List.of("Gruby"))
                        .address(Address.builder()
                                .street("Ogrodowa 5/4")
                                .city("Legnica")
                                .zipCode("59-220")
                                .build())
                        .effectiveFrom(effectiveFrom1)
                        .effectiveTo(effectiveTo1)
                        .active(true)
                        .lastVersion(true)
                        .versionNumber(1L)
                        .build(),
                Person.builder()
                        .name("Jarosław")
                        .surname("Jałoszyński")
                        .id("xtye")
                        .pid("2")
                        .mobile("+48503397737")
                        .aliases(List.of("Misiak", "JJ"))
                        .address(Address.builder()
                                .street("3. Powstania Śląskiego 3/8")
                                .city("Dabrowa Górnicza")
                                .zipCode("41-303")
                                .build())
                        .effectiveFrom(effectiveFrom2)
                        .effectiveTo(effectiveTo2)
                        .active(false)
                        .lastVersion(true)
                        .versionNumber(1L)
                        .build(),
                Person.builder()
                        .name("Anna")
                        .surname("Giełżecka")
                        .id("poiu")
                        .pid("3")
                        .mobile("+48503123456")
                        .aliases(List.of("Biedronka"))
                        .address(Address.builder()
                                .street("3. Powstania Śląskiego 3/8")
                                .city("Dabrowa Górnicza")
                                .zipCode("41-303")
                                .build())
                        .effectiveFrom(effectiveFrom3)
                        .effectiveTo(effectiveTo3)
                        .active(true)
                        .lastVersion(true)
                        .versionNumber(1L)
                        .build(),
                Person.builder()
                        .name("Danuta")
                        .surname("Chudzicka")
                        .id("nyes")
                        .pid("4")
                        .mobile("+48505909765")
                        .address(Address.builder()
                                .street("Kasprzaka 22/69")
                                .city("Dabrowa Górnicza")
                                .zipCode("41-303")
                                .build())
                        .effectiveFrom(effectiveFrom4)
                        .effectiveTo(effectiveTo4)
                        .active(true)
                        .lastVersion(true)
                        .versionNumber(1L)
                        .build(),
                Person.builder()
                        .name("Piotr")
                        .surname("Dobrowolski")
                        .id("lkjh")
                        .pid("5")
                        .mobile("+48666999357")
                        .aliases(List.of("Gruby", "Varg", "Szopen"))
                        .address(Address.builder()
                                .street("Sawy 14")
                                .city("Legnica")
                                .zipCode("59-220")
                                .build())
                        .effectiveFrom(effectiveFrom5)
                        .effectiveTo(effectiveTo5)
                        .active(false)
                        .lastVersion(true)
                        .versionNumber(1L)
                        .build(),
                Person.builder()
                        .name("Marcin")
                        .surname("Płaszewski")
                        .id("pnbv")
                        .pid("6")
                        .mobile("+48387645909")
                        .aliases(List.of("Gruby"))
                        .address(Address.builder()
                                .street("Piłsudskiego 90/34")
                                .city("Sosnowiec")
                                .zipCode("41-234")
                                .build())
                        .effectiveFrom(effectiveFrom6)
                        .effectiveTo(effectiveTo6)
                        .active(false)
                        .lastVersion(true)
                        .versionNumber(1L)
                        .build()
        ));

    }
}
