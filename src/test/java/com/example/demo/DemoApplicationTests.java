package com.example.demo;

import com.example.demo.controller.AddressController;
import com.example.demo.model.Address;
import org.junit.jupiter.api.Test;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.Collection;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private AddressController addressController;

    @Parameterized.Parameters
    public static Collection<Object[]> getSimpleAddressesSingleNumber() {
        return Arrays.asList(new Object[][]{
                {"", new Address()},
                {"Winterallee", new Address("Winterallee", "")},
                {"Winterallee 3", new Address("Winterallee", "3")},
                {"Musterstrasse 45", new Address("Musterstrasse", "45")},
                {"Blaufeldweg 123B", new Address("Blaufeldweg", "123B")}
        });
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getComplicatedAddressesSingleNumber() {
        return Arrays.asList(new Object[][]{
                {"Am Bächle 23", new Address("Am Bächle", "23")},
                {"Auf der Vogelwiese 23 b", new Address("Auf der Vogelwiese", "23 b")},
                {"", new Address()}
        });
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getComplicatedAddressesMultipleNumber() {
        return Arrays.asList(new Object[][]{
                {"4, rue de la revolution", new Address("rue de la revolution", "4")},
                {"200 Broadway Av", new Address("Broadway Av", "200")},
                {"200D ,Broadway Av", new Address("Broadway Av", "200D")},
                {"Calle Aduana, 29", new Address("Calle Aduana", "29")},
                {"Calle 39 No 1540", new Address("Calle 39", "No 1540")},

                {"Calle 39-12 No 1540", new Address("Calle 39-12", "No 1540")},
                {"Calle 39 Nr 1540", new Address("Calle 39", "Nr 1540")},
                {"Calle 39, No: 1540", new Address("Calle 39", "No: 1540")},
                {"Calle 39, Nr: 1540", new Address("Calle 39", "Nr: 1540")},
                {"Calle 39, 5", new Address("Calle 39", "5")},
                {"5 Calle 39", new Address("Calle 39", "5")},
                {"5, Calle 39", new Address("Calle 39", "5")},
        });
    }

    @Test
    void parseAddressStringWithSingleNumber(){
        String addressStrInput = "";
        Address addressOutputExpected;
        Collection<Object[]> simpleAddSingleNumberData = getSimpleAddressesSingleNumber();
        for (Object[] addressTest : simpleAddSingleNumberData)
        {
            addressStrInput = (String) addressTest[0];
            addressOutputExpected = (Address) addressTest[1];
            Address addressOutputActual = addressController.processAddress(addressStrInput);
            assertThat(addressOutputActual.getStreet()).isEqualTo(addressOutputExpected.getStreet());
            assertThat(addressOutputActual.getHouseNumber()).isEqualTo(addressOutputExpected.getHouseNumber());
        }
    }

    @Test
    void parseComlexAddressStringWithSingleNumber(){
        String addressStrInput = "";
        Address addressOutputExpected;
        Collection<Object[]> complexAddSingleNumberData = getComplicatedAddressesSingleNumber();
        for (Object[] addressTest : complexAddSingleNumberData)
        {
            addressStrInput = (String) addressTest[0];
            addressOutputExpected = (Address) addressTest[1];
            Address addressOutputActual = addressController.processAddress(addressStrInput);
            assertThat(addressOutputActual.getStreet()).isEqualTo(addressOutputExpected.getStreet());
            assertThat(addressOutputActual.getHouseNumber()).isEqualTo(addressOutputExpected.getHouseNumber());
        }
    }

    @Test
    void parseComlexAddressStringWithMultipleNumber(){
        String addressStrInput = "";
        Address addressOutputExpected;
        Collection<Object[]> complexAddSingleMultipleData = getComplicatedAddressesMultipleNumber();
        for (Object[] addressTest : complexAddSingleMultipleData)
        {
            addressStrInput = (String) addressTest[0];
            addressOutputExpected = (Address) addressTest[1];
            Address addressOutputActual = addressController.processAddress(addressStrInput);
            assertThat(addressOutputActual.getStreet()).isEqualTo(addressOutputExpected.getStreet());
            assertThat(addressOutputActual.getHouseNumber()).isEqualTo(addressOutputExpected.getHouseNumber());
        }
    }


}
