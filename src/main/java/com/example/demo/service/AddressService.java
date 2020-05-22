package com.example.demo.service;

import com.example.demo.model.Address;
import com.example.demo.repository.AddressRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AddressService implements AddressRepository {

    private final String regexGetNumber = "(\\d{1,5}\\w{0,5}+)(?!\\.)";
    private final String regexGetStreet = "[^\\d\\w\\säöüÄÖÜß´`'().-]+";

    public Address getAddressFromAddressString(String addressStr) {
        Address address = new Address();
        if (addressStr == null || addressStr.isEmpty()) {
            return address;
        }

        Pattern numbersPattern = Pattern.compile(regexGetNumber);
        Matcher numbersMatcher = numbersPattern.matcher(addressStr);

        int numberCount = getNumberCount(numbersMatcher);
        if (numberCount == 0) {
            address.setStreet(addressStr);
        }
        else if (numberCount == 1) {
            numbersMatcher.find();
            address = setAddressInfoSingleNumber(addressStr, numbersMatcher);
        }
        else {
            numbersMatcher.find();
            address = setAddressInfoMultipleNumber(addressStr, numbersMatcher);
        }
        return address;
    }

    private int getNumberCount(Matcher numbersMatcher) {
        int numberCount = 0;
        while (numbersMatcher.find())
            numberCount++;
        numbersMatcher.reset();
        return numberCount;
    }

    private Address setAddressInfoSingleNumber(String addressStr, Matcher numbersMatcher) {
        Address address = new Address();
        String streetStr, houseNumberStr, temp;

        int startIndexOfNumber = numbersMatcher.start();
        int endIndexOfNumber = numbersMatcher.end();
        if (startIndexOfNumber == 0) {
            houseNumberStr = numbersMatcher.group().trim();
            temp = addressStr.substring(endIndexOfNumber).trim();
        }
        else {
            houseNumberStr = addressStr.substring(startIndexOfNumber).trim();
            temp = addressStr.substring(0, startIndexOfNumber).trim();
        }
        streetStr = temp.replaceAll(regexGetStreet, "").trim();
        address.setHouseNumber(houseNumberStr);
        address.setStreet(streetStr);
        return address;
    }

    private Address setAddressInfoMultipleNumber(String addressStr, Matcher numbersMatcher) {
        Address address = new Address();
        String streetStr, houseNumberStr, temp;

        houseNumberStr = getHouseNumberMutipleCase(addressStr);
        int indexHouseNumberStr = addressStr.indexOf(houseNumberStr);
        int lengthHouseNumberStr = houseNumberStr.length();
        int nextIndex = indexHouseNumberStr + lengthHouseNumberStr;

        streetStr = addressStr.substring(0, indexHouseNumberStr)+addressStr.substring(nextIndex);
        streetStr = streetStr.replaceAll(",", "").replaceAll(":", "").trim();

        address.setHouseNumber(houseNumberStr);
        address.setStreet(streetStr);
        return address;
    }


    private String getHouseNumberMutipleCase(String addressStr)
    {
        String houseNumber = containsNumberSynonym(addressStr);
        if(houseNumber==null)
        {
            addressStr = addressStr.replaceAll(",", "").replaceAll(":", "");
            String[] addressArray = addressStr.split(" ");
            for (int i = 0; i < addressArray.length; i++)
            {
                // Street Number is at the beginning of the address
                if(addressArray[0].matches(regexGetNumber)) {
                    houseNumber = addressArray[0];
                    break;
                }

                // Street Number is at the end of the address
                else if (addressArray[addressArray.length-1].matches(regexGetNumber)) {
                    houseNumber = addressArray[addressArray.length-1];
                    break;
                }
            }
        }
        return houseNumber;
    }

    // Cases where the house number occurred after synonym of "No"
    private String containsNumberSynonym(String addressStr)
    {
        addressStr = addressStr.replaceAll(",", "");
        String[] addressArray = addressStr.split(" ");
        for (int i = 0; i < addressArray.length; i++)
        {
            if( addressArray[i].isEmpty())
                continue;
            if(addressArray[i].toLowerCase().contains("nr") || addressArray[i].toLowerCase().contains("nr.") ||
                addressArray[i].toLowerCase().contains("no")|| addressArray[i].toLowerCase().contains("no.") ||
                addressArray[i].toLowerCase().contains("number"))
            {
                return addressArray[i] + " " +addressArray[i+1];
            }
        }
        return null;
    }

}