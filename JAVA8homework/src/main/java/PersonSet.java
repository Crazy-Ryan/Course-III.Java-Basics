import entity.Address;
import entity.Email;
import entity.MasterNumber;
import entity.Person;
import entity.Telephone;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class PersonSet {
    private List<MasterNumber> masterNumbers;
    private List<Telephone> telephones;
    private List<Address> addresses;
    private List<Email> emails;

    public Stream<Person> groupToPeople() {
        Map<String, List<Telephone>> telephonesMap = telephones.stream().collect(Collectors.groupingBy(Telephone::getMasterNumber));
        Map<String, Address> addressesMap = addresses.stream().collect(Collectors.toMap(Address::getMasterNumber, a -> a, (a1, a2) -> a1));
        Map<String, List<Email>> emailsMap = emails.stream().collect(Collectors.groupingBy(Email::getMasterNumber));
        return masterNumbers.stream().map(mn -> new Person(
                mn.getNumber(),
                telephonesMap.getOrDefault(mn.getNumber(), new LinkedList<>()),
                addressesMap.getOrDefault(mn.getNumber(), null),
                emailsMap.getOrDefault(mn.getNumber(), new LinkedList<>())));
//      //Deprecated//
//      return masterNumbers.stream().map(masterNumber -> new Person(
//              masterNumber.getNumber(),
//              telephones.stream().filter(telephone -> telephone.getMasterNumber().equals(masterNumber.getNumber())).collect(Collectors.toList()),
//              addresses.stream().filter(address -> address.getMasterNumber().equals(masterNumber.getNumber())).findAny().orElse(null),
//              emails.stream().filter(email -> email.getMasterNumber().equals(masterNumber.getNumber())).collect(Collectors.toList())));
    }
}
