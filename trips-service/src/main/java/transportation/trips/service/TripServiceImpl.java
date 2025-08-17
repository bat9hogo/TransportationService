package transportation.trips.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transportation.trips.dto.TripRequestDto;
import transportation.trips.dto.TripResponseDto;
import transportation.trips.dto.TripStatusUpdateDto;
import transportation.trips.entity.Trip;
import transportation.trips.entity.TripStatus;
import transportation.trips.exception.TripNotFoundException;
import transportation.trips.mapper.TripMapper;
import transportation.trips.repository.TripRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public TripServiceImpl(TripRepository tripRepository, TripMapper tripMapper) {
        this.tripRepository = tripRepository;
        this.tripMapper = tripMapper;
    }

    @Override
    public TripResponseDto createTrip(TripRequestDto request) {
        Trip trip = tripMapper.toEntity(request);
        trip.setId(UUID.randomUUID().toString());
        trip.setStatus(TripStatus.CREATED.name());
        trip.setOrderTime(Instant.now());
        Trip saved = tripRepository.save(trip);
        return tripMapper.toDto(saved);
    }

    @Override
    public TripResponseDto getTripById(String id) {
        Trip trip = tripRepository.findById(id)
                .filter(t -> !t.isDeleted())
                .orElseThrow(() -> new TripNotFoundException("Trip with id " + id + " not found"));
        return tripMapper.toDto(trip);
    }


    @Override
    public List<TripResponseDto> getAllTrips() {
        return tripRepository.findAll().stream()
                .filter(t -> !t.isDeleted())
                .map(tripMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TripResponseDto updateTrip(String id, TripRequestDto request) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip with id " + id + " not found"));

        trip.setDriverId(request.getDriverId());
        trip.setPassengerId(request.getPassengerId());
        trip.setFromAddress(request.getFromAddress());
        trip.setToAddress(request.getToAddress());
        trip.setPrice(request.getPrice());

        Trip updated = tripRepository.save(trip);
        return tripMapper.toDto(updated);
    }

    @Override
    public void deleteTrip(String id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip with id " + id + " not found"));
        trip.setDeleted(true);
        tripRepository.save(trip);
    }

    @Override
    public TripResponseDto updateTripStatus(String id, TripStatusUpdateDto statusUpdate) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new TripNotFoundException("Trip with id " + id + " not found"));

        trip.setStatus(statusUpdate.getStatus().name());
        Trip updated = tripRepository.save(trip);
        return tripMapper.toDto(updated);
    }
}
