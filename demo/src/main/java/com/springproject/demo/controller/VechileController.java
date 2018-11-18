package com.springproject.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springproject.demo.model.Alert;
import com.springproject.demo.model.Reading;
import com.springproject.demo.model.Tires;
import com.springproject.demo.model.Vehicle;
import com.springproject.demo.repository.AlertJpaRepo;
import com.springproject.demo.repository.ReadingJpaRepo;
import com.springproject.demo.repository.TipeJpaRepo;
import com.springproject.demo.repository.VehicleJpaRepo;

@RestController
public class VechileController {

	@Autowired
	private VehicleJpaRepo vehicleJpaRepo;

	@Autowired
	private ReadingJpaRepo readingJpaRepo;

	@Autowired
	private AlertJpaRepo alertJpaRepo;

	@Autowired
	private TipeJpaRepo tipeJpaRepo;

	// Method to load vehicle details in bulk via a PUT /vehicles endpoint.
	// Method will update the record in db, if the vehicle with same VIN is already
	// present.
	@CrossOrigin
	@PutMapping(value = "/vehicles")
	public void setVechiles(@RequestBody List<Vehicle> payload) {

		for (Vehicle vehicle : payload) {
			System.out.println(vehicle);
			if (vehicleJpaRepo.existsById(vehicle.getVin())) {
				vehicleJpaRepo.deleteById(vehicle.getVin());
				vehicleJpaRepo.save(vehicle);
				System.out.println("Ols Vehicle ID  -->" + vehicle.getVin());
				System.out.println("Old Vehicle -->" + vehicle);
			} else {
				vehicleJpaRepo.save(vehicle);
				System.out.println("New Vehicle ID  -->" + vehicle.getVin());
				System.out.println("New Vehicle -->" + vehicle);
			}

		}

	}

	// Method to ingest readings from these vehicles via a POST /readings
	@CrossOrigin
	@PostMapping(value = "/readings")
	public void setReadings(@RequestBody Reading reading) {

		System.out.println(reading);

		Optional<Vehicle> v = vehicleJpaRepo.findById(reading.getVin());
		// Optional<Tires> t = tipeJpaRepo.findById(reading.getVin());
		if (readingJpaRepo.existsById(reading.getVin())) {
			// tipeJpaRepo.deleteById(reading.getVin());
			readingJpaRepo.deleteById(reading.getVin());

			Tires tires = new Tires();
			tires.setFrontLeft(reading.getTires().getFrontLeft());
			tires.setFrontRight(reading.getTires().getFrontRight());
			tires.setRearLeft(reading.getTires().getRearLeft());
			tires.setRearRight(reading.getTires().getRearRight());
			tires.setVin(reading.getVin());
			tipeJpaRepo.save(tires);

			Reading r = new Reading();
			r.setVin(reading.getVin());
			r.setCheckEngineLightOn(reading.getCheckEngineLightOn());
			r.setCruiseControlOn(reading.getCruiseControlOn());
			r.setEngineCoolantLow(reading.getEngineCoolantLow());
			r.setEngineHp(reading.getEngineHp());
			r.setEngineRpm(reading.getEngineRpm());
			r.setFuelVolume(reading.getFuelVolume());
			r.setLatitude(reading.getLatitude());
			r.setLongitude(reading.getLongitude());
			r.setSpeed(reading.getSpeed());
			r.setTimestamp(reading.getTimestamp());
			r.setTires(tires);
			readingJpaRepo.save(r);
			System.out.println(v.get().getRedlineRpm());
			System.out.println(r.getEngineRpm());
			if (r.getEngineRpm() > v.get().getRedlineRpm()) {
				Alert alert = new Alert();
				alert.setPriority("HIGH");
				alert.setVin(reading.getVin());
				alert.setDate(new Date());
				alertJpaRepo.save(alert);
				System.out.println("High alert generated");
			}

			if (r.getFuelVolume() < (v.get().getRedlineRpm() / 10)) {

				Alert alert = new Alert();
				alert.setPriority("MEDIUM");
				alert.setVin(reading.getVin());
				alert.setDate(new Date());
				alertJpaRepo.save(alert);
				System.out.println("Medium alert generated");
			}

			System.out.println("Old Vehicle Reading  ID  -->" + reading.getVin());
			System.out.println("Old Vehicle Reading -->" + reading);

		} else {

			Tires tires = new Tires();
			tires.setFrontLeft(reading.getTires().getFrontLeft());
			tires.setFrontRight(reading.getTires().getFrontRight());
			tires.setRearLeft(reading.getTires().getRearLeft());
			tires.setRearRight(reading.getTires().getRearRight());
			tires.setVin(reading.getVin());
			System.out.println(tires);
			tipeJpaRepo.save(tires);

			Reading r = new Reading();
			r.setVin(reading.getVin());
			r.setCheckEngineLightOn(reading.getCheckEngineLightOn());
			r.setCruiseControlOn(reading.getCruiseControlOn());
			r.setEngineCoolantLow(reading.getEngineCoolantLow());
			r.setEngineHp(reading.getEngineHp());
			r.setEngineRpm(reading.getEngineRpm());
			r.setFuelVolume(reading.getFuelVolume());
			r.setLatitude(reading.getLatitude());
			r.setLongitude(reading.getLongitude());
			r.setSpeed(reading.getSpeed());
			r.setTimestamp(reading.getTimestamp());
			r.setTires(tires);
			readingJpaRepo.save(r);
			System.out.println(v.get().getRedlineRpm());
			System.out.println(reading.getEngineRpm());
			if (reading.getEngineRpm() > v.get().getRedlineRpm()) {
				Alert alert = new Alert();
				alert.setPriority("HIGH");
				alert.setVin(reading.getVin());
				alert.setDate(new Date());
				alertJpaRepo.save(alert);
				System.out.println("High alert generated");
			}

			if (reading.getFuelVolume() < (v.get().getRedlineRpm() / 10)) {

				Alert alert = new Alert();
				alert.setPriority("MEDIUM");
				alert.setVin(reading.getVin());
				alert.setDate(new Date());
				alertJpaRepo.save(alert);
				System.out.println("Medium alert generated");
			}

			System.out.println("New Vehicle Reading ID  -->" + reading.getVin());
			System.out.println("New Vehicle Reading -->" + reading);
		}
	}

	// Method to fetch details of all the vehicles like VIN, make, model, year etc
	@CrossOrigin
	@GetMapping(value = "/allvehicles")
	public void getVehicles() {

		List<Vehicle> v = vehicleJpaRepo.findAll();

		for (Vehicle vehicle : v) {
			System.out.println(vehicle.getVin());
		}

	}

	// Method to fetch HIGH alerts within last 2 hours for all the vehicles.
	@CrossOrigin
	@GetMapping(value = "/allhighalerts")
	public void getHignAlerts() throws java.text.ParseException {
		int i = 1;
		List<Alert> a = alertJpaRepo.findByPriority("HIGH");
		Date d = new Date();
		SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
		Date lowerLimit = parser.parse(d.getHours() - 2 + ":" + d.getMinutes());
		Date higherLimit = parser.parse(d.getHours() + ":" + d.getMinutes());

		System.out.println(lowerLimit);
		System.out.println(higherLimit);

		try {
			for (Alert alert : a) {
				System.out.println(alert.getPriority());
				Date userDate = parser.parse(alert.getDate().getHours() + ":" + alert.getDate().getMinutes());
				if (userDate.after(lowerLimit) && userDate.before(higherLimit)) {
					System.out.println("Found a high alert in last last two hours");
				} else {
					a.remove(alert);
				}
			}

		} catch (ParseException e) {
			// Invalid date was entered
		}	
		

		for(Alert alert : a) {
			System.out.println(alert.getPriority());
		}
		
	}

	// Method to gives the ability to list a vehicle's all historical alerts.
	@CrossOrigin
	@GetMapping(value = "/vehiclehistory/{vin}")
	public String getVehicleHistory(@PathVariable final String vin) {

		List<Alert> a = alertJpaRepo.findByVin(vin);

		for(Alert alert : a) {
			System.out.println(alert.getVin());
			return alert.getVin();
		}
		return ("No historical alert found for vehicle");
		
	}

}
