package com.example.itmo.service.impl;
import com.example.itmo.model.dto.request.CarInfoRequest;
import com.example.itmo.model.dto.response.CarInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final ObjectMapper mapper;

    private List<CarInfoResponse> cars = new ArrayList<>();
    private long id = 1;
    @Override
    public CarInfoResponse createCar(CarInfoRequest request) {
        CarInfoResponse car = mapper.convertValue(request, CarInfoResponse.class);
        car.setId(id++);
        cars.add(car);
        return car;
    }

    @Override
    public CarInfoResponse getCar(Long id) {
        List<CarInfoResponse> all = this.cars.stream()
                .filter(u -> u.getId().equals(id))
                .collect(Collectors.toList());

        CarInfoResponse car = null;
        if (CollectionUtils.isEmpty(all)) {
            log.error(String.format("car with id:%s not found", id));
            return car;
        }
          car = all.get(0);
          return car;
    }

    @Override
    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        CarInfoResponse car = getCar(id);
        if (Objects.isNull(car)) {
            log.error("Car not deleted");
            return null;
        }
        CarInfoResponse response = mapper.convertValue(request, CarInfoResponse.class);
        response.setId(car.getId());
        return response;
    }
    @Override
    public void deleteCar(Long id) {
        CarInfoResponse car = getCar(id);

        if (Objects.isNull(car)) {
            log.error("car not deleted");
            return;
        }

        this.cars.remove(car);

    }

    @Override
    public List<CarInfoResponse> getAllCars() {
        return cars;
    }
}
