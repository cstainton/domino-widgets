package io.instanto.domino.client;
import java.util.ArrayList;
import java.util.List;
/** Deterministic local data replaces the original showcase's generated HTTP service. */
public final class SampleContacts {
 public static List<Contact> create(){
  List<Contact> contacts=new ArrayList<>();
  for(int i=0;i<100;i++){
   Contact c=new Contact();c.setIndex(i);c.setName(i==0?"Alice Example":i==1?"Bob Example":"Contact "+i);
   c.setActive(i%3!=0);c.setBalance(1000.0+i*100);c.setAge((short)(25+i));c.setGender(i%2==0?Gender.female:Gender.male);
   c.setEyeColor(EyeColor.blue);c.setCompany("Example company");c.setEmail("person"+i+"@example.invalid");
   c.setPhone("020 0000 "+i);c.setAddress("Sample address");c.setAbout("Deterministic showcase contact");c.setPicture("showcase-image.jpg");c.setHasChildren(false);
   contacts.add(c);
  }
  return contacts;
 }
}
