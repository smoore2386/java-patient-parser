import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import java.time.LocalDate;
import java.time.Period;


/**
 * Main entry point for xml to json parser.
 */
public class Main {

  public static String PATIENT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><patients><patient><id>1234</id><gender>m</gender><name>John Smith</name><state>Michigan</state><dateOfBirth>03/04/1962</dateOfBirth></patient><patient><id>5678</id><gender>f</gender><name>Jane Smith</name><state>Ohio</state><dateOfBirth>08/24/1971</dateOfBirth></patient></patients>";

  /**
   * Parse the xml string into a json object. Convert and print to console.
   *
   * @param args- cli args currently n/a
   */
  public static void main(String[] args) {
    try {
      // move from string to file
      JSONObject patientCollection = XML.toJSONObject(PATIENT_XML);
      JSONArray updatedCollection = new Main().transformJSON(patientCollection.getJSONObject("patients").getJSONArray("patient"));
      System.out.println(updatedCollection.toString(4));
    } catch (Exception je) {
      System.out.println(je.toString());
    }
  }

  /**
   * Remap the json into appropriate values for consumption
   *
   * @param patientCollection json collection of patients parsed from xml
   */
  private JSONArray transformJSON(JSONArray patients) {
      JSONArray patientCollection = new JSONArray();
      for (int i = 0; i < patients.length(); i++) {
        JSONObject currPatient = patients.getJSONObject(i);
        currPatient.put("patientid", (Integer) currPatient.get("id"));
        currPatient.put("sex", patientSex( (String) currPatient.get("gender")));
        currPatient.put("age", this.calculateAge((String) currPatient.get("dateOfBirth")) );
        currPatient.put("state", new MappingConstants().STATE_MAP.get(currPatient.get("state")));
        currPatient.remove("gender");
        currPatient.remove("id");
        currPatient.remove("dateOfBirth");
        patientCollection.put(currPatient);
      }
    return patientCollection;
  }

  /**
   * Ancillary function for returning the patient sex
   * @param abbr ("m" | "f")
   * @return ("male" | "female")
   */
  private String patientSex(String abbr) {
    if(abbr.equals("m")){
      return "male";
    }
    if(abbr.equals("f")){
      return "female";
    }
    return abbr;
  }

  /**
   * Calculate the age of the patient from dob
   * @param dob
   * @return Integer - age of patient
   */
  private Integer calculateAge(String dob) {
    String[] dobSplit = dob.split("/");
    try {
      LocalDate birthDate = LocalDate.of(Integer.parseInt(dobSplit[2]), Integer.parseInt(dobSplit[0]), Integer.parseInt(dobSplit[1]));
      LocalDate now = LocalDate.now();
      return Period.between(birthDate, now).getYears();
    } catch (Exception e){
      System.out.println("Error converting birth-date for patient");
    }
    return 0;
  }

}
