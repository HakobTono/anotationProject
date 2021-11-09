import dto.CustomerDto;
import validate.CustomerDtoValidate;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setLocalDate(LocalDate.of(1995,5,16));
        customerDto.setName("Hakob");
        customerDto.setDiscountRate(-1);
        customerDto.setEmail("hakob@mail.com");
        CustomerDtoValidate.checkCustomerDto(customerDto);

    }
}