package User.Patient;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class PatientController {

//    private int id_matricule;
//    public String fname;
//    public String lname;
//    private String email;
//    private String passw;

    PatientModel patientModel;

    public PatientController () {
        patientModel = new PatientModel();
    }

    public boolean authenticate (String email, String passw) {
        boolean isValid = patientModel.isPatientExist(email, passw);
        patientModel.closeQuery();
        return isValid;
    }

    public Map<String, String> getPatientData (long id_matricule) {
        Map<String, String> patientMap = null;
        ResultSet result = patientModel.getPatientByMatricule(id_matricule);
        try{
            patientMap = new HashMap<>(Map.ofEntries(
                    Map.entry("matricule", String.valueOf(result. getLong("id_matricule"))),
                    Map.entry("fname", result.getString("prenom")),
                    Map.entry("lname", result.getString("nom")),
                    Map.entry("email", result.getString("email"))
            ));
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            patientModel.closeQuery();
            return patientMap;
        }
    }
    public Map<String, String> getPatientDataByEmail (String email) {
        Map<String, String> patientMap = null;
        ResultSet result = patientModel.getPatientByEmail(email);
        try{
            patientMap = new HashMap<>(Map.ofEntries(
                    Map.entry("matricule", String.valueOf(result. getLong("id_matricule"))),
                    Map.entry("fname", result.getString("prenom")),
                    Map.entry("lname", result.getString("nom")),
                    Map.entry("email", result.getString("email"))
            ));
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            patientModel.closeQuery();
            return patientMap;
        }
    }

    public long getPatientMatricule (String email) {
        Map<String, String> patientMap = getPatientDataByEmail(email);
        return Long.parseLong(patientMap.get("matricule"));

    }
    public void closeDBConnection () {
        patientModel.closeDBConnection();
    }
    public boolean checkPatientIsAvailable(long matrecule){
        return patientModel.isPatientExistByMatrecule(matrecule);
    }
}
