package me.test.first.spring.boot.test;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author dangqian.zll
 * @date 2019-07-23
 */
public class HibernateValitorTest {


    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void manufacturerIsNull() {
        Car car1 = new Car(null, "DD-AB-123", 4);
        Car car = new Car("xxx", "DD-AB-123", 1);
        car.setCar(car1);
        car.setCarList(Arrays.asList(car1));

        Map<String, Car> cars = new HashMap<>(2);
        cars.put("aaa", car1);
        car.setCars(cars);

        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car, B.class);


        assertEquals(1, constraintViolations.size());
//        assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
    }

    public interface A {
    }

    public interface B {
    }

    public class Car {

        @Valid
        private Car car;

        @Valid
        private List<Car> carList;

        @Valid
        private Map<String, Car> cars;

        @NotNull
        private String manufacturer;

        @NotNull
        @Size(min = 2, max = 14)
        private String licensePlate;

        @Min(value = 5, groups = A.class)
        @Min(value = 2, groups = B.class)
        private int seatCount;

        public Car getCar() {
            return car;
        }

        public void setCar(Car car) {
            this.car = car;
        }

        public Car(String manufacturer, String licencePlate, int seatCount) {
            this.manufacturer = manufacturer;
            this.licensePlate = licencePlate;
            this.seatCount = seatCount;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getLicensePlate() {
            return licensePlate;
        }

        public void setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
        }

        public int getSeatCount() {
            return seatCount;
        }

        public void setSeatCount(int seatCount) {
            this.seatCount = seatCount;
        }

        public List<Car> getCarList() {
            return carList;
        }

        public void setCarList(List<Car> carList) {
            this.carList = carList;
        }

        public Map<String, Car> getCars() {
            return cars;
        }

        public void setCars(Map<String, Car> cars) {
            this.cars = cars;
        }
    }

}
