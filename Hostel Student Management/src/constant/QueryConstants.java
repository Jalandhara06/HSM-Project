package constant;

import db.DBManager;
import java.util.Set;

public class QueryConstants {

    static DBManager db ;

    public static final String PROMPT_INSERT_PEOPLE_TEMPLATE = "INSERT INTO %s.people(name, joined_date, image_path, consent_form) VALUES (?, ?, ?, ?)";
    public static final String PROMPT_INSERT_ROOM_TEMPLATE = "INSERT INTO %s.room_details(bed_sharing, person1_name, person2_name, floor_no, person1_id, person2_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String PROMPT_INSERT_JOB_TEMPLATE = "INSERT INTO %s.job_details(person_name, job_name, person_id) VALUES (?, ?, ?)";

    public static final String UPDATE_QUERY_TEMPLATE = "UPDATE %s.%s SET %s = ? WHERE name = ?";
    public static final String UPDATE_QUERY_TEMPLATE1 = "UPDATE %s.%s SET %s = ? WHERE %s = ?";

    public static final String DELETE_QUERY_TEMPLATE = "DELETE FROM %s.%s WHERE person_id = ?";
    public static final String DELETE_QUERY_TEMPLATE1 = "DELETE FROM %s.%s WHERE person1_id = ?";

    public static final String SELECT_QUERY_TEMPLATE = "SELECT * FROM %s.%s ";
    public static final String SELECT_ID_QUERY_TEMPLATE = "SELECT person_id FROM %s.people WHERE name = ?";

    public static final Set<String> ALLOWED_UPDATE_COLUMNS = Set.of("name", "joined_date", "image_path", "consent_form", "bed_sharing", "person1_name", "person2_name", "floor_no", "job_name", "person_name");

    public static final String PROMPT_DROP_QUERY_TEMPLATE = "DROP TABLE %s.%s CASCADE";

    public static final String PROMPT_TRUNCATE_QUERY_TEMPLATE = "TRUNCATE TABLE %s.%s CASCADE";

}