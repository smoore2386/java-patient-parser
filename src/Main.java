import org.json.JSONObject;
import org.json.XML;
import Patient;
import MappingConstants.java;

/**
 * Main entry point for xml to json parser.
 */
public class Main {

  public static String PATIENT_XML = "<?xml version="1.0" encoding="UTF-8"?><patients><patient><id>1234</id><gender>m</gender><name>John Smith</name><state>Michigan</state><dateOfBirth>03/04/1962</dateOfBirth></patient><patient><id>5678</id><gender>f</gender><name>Jane Smith</name><state>Ohio</state><dateOfBirth>08/24/1971</dateOfBirth></patient></patients>";

  /**
   * Parse the xml string into a json object. Convert and print to console.
   *
   * @param args- cli args currently n/a
   */
  public static void main(String[] args) {
    try {
      JSONObject[] patientCollection = this.transformJSON(XML.toJSONObject(PATIENT_XML));
      System.out.println(patientCollection.toString());
    } catch (JSONException je) {
      System.out.println(je.toString());
    }
  }

  /**
   * Remap the json into appropriate values for consumption
   *
   * @param patientCollection json collection of patients parsed from xml
   */
  private transformJSON(JSONObject[] patientCollection) {
    JSONObject[] newCollection;
      for (int i = 0; i < patientCollection.length; i++) {
        patientCollection[i].put('patientid', patientCollection[i].get('id'));
        patientCollection[i].remove('id');
        patientCollection[i].put('age', this.calculateAge(patient.get('dateOfBirth')) );
        patientCollection[i].remove('dateOfBirth');
        patientCollection[i].set('state') = MappingConstants.STATE_MAP.get('state');
        patientCollection[i]
      }
  }

  /**
   * Ancillary function for returning the patient sex
   * @param abbr ('m' | 'f')
   * @return ('male' | 'female')
   */
  private String patientSex(String abbr) {
    if(abbr == 'm') return 'male';
    if(abbr == 'f') return 'female';
  }

  /**
   * Calculate the age of the patient from dob
   * @param dob
   * @return Integer - age of patient
   */
  private Integer calculateAge(String dob) {
    Int[] dobSplit = String.split(dob);
    try {
      LocalDate birthdate = new LocalDate(dob[2].toInt(), dob[3].toInt(), dob[1].toInt());
      LocalDate now = new LocalDate();
      return Years.yearsBetween(birthdate, now);
    } catch (Exception e){
      // handle and rethrow
      System.out.println('Error converting date for patient');
      throw(new Exception('Age conversion error'));
    }
  }

}
