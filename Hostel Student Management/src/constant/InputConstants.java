package constant;


public class InputConstants {
    public static final String DB_SCHEMA = "hostel_management";

    public static final String TABLE_PEOPLE = "people";
    public static final String TABLE_ROOM = "room_details";
    public static final String TABLE_JOB = "job_details";

    public static final String PROMPT_NAME = "Enter name : ";
    public static final String PROMPT_NAME1 = "Enter person1 name : ";
    public static final String PROMPT_NAME2 = "Enter person2 name : ";
    public static final String PROMPT_JOINED_DATE = "Enter joined date (YYYY-MM-DD) : ";
    public static final String PROMPT_IMAGE =  "Enter image path : ";
    public static final String PROMPT_CONSENT_FORM = "Enter consent form path : ";

    public static final String PROMPT_BED_SHARING = "Enter bed sharing (SINGLE / DOUBLE) : ";
    public static final String PROMPT_FLOOR_NO = "Enter floor no : ";

    public static final String PROMPT_JOB_NAME = "Enter job name (Working / Student) : ";

    public static final String PROMPT_TABLE_PERSON = "Enter values for \"Person\"";
    public static final String PROMPT_TABLE_ROOM = "Enter values for \"Room_Details\"";
    public static final String PROMPT_TABLE_JOB = "Enter values for \"Job_Details\"";
    public static final String PROMPT_TABLENAME_UPDATE = "Enter table name to update (people/room_details/job_details): ";
    public static final String PROMPT_DELETE = "Enter person_id to delete related records: ";

    public static final String PROMPT_UPDATE_PEOPLE = "Enter column to update (e.g. image_path, joined_date, consent_form): ";
    public static final String PROMPT_UPDATE_ROOM = "Enter column to update (e.g., floor_no): ";
    public static final String PROMPT_NEW_VALUE = "Enter new value: ";
    public static final String PROMPT_NAME_MATCH = "Enter column to where (e.g. name): ";
    public static final String PROMPT_WHERE_COLUMN = "Enter column to where (e.g. person1_name / person_name): ";
    public static final String PROMPT_WHERE_VALUE = "Enter value to match record: ";
    public static final String PROMPT_UPDATE_JOB = "Enter column to update (person_name, job_name): ";

    public static final String PROMPT_DISPLAY_TABLE = "Enter display table name (people / room_details / job_details): ";

    public static final String PROMPT_DROP_TABLE = "Enter drop table name (people/room_details/job_details): ";

    public static final String PROMPT_TRUNCATE_TABLE = "Enter truncate table name (people/room_details/job_details): ";
}
