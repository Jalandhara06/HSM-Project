package beans;

public class RoomDetails {
    private String bed_sharing;
    private String person1_name;
    private String person2_name;
    private int floor_no;
    private Integer person1_id;
    private Integer person2_id;

    public RoomDetails(String bed_sharing, String person1_name, String person2_name, int floor_no) {
        this.bed_sharing = bed_sharing;
        this.person1_name = person1_name;
        this.person2_name = person2_name;
        this.floor_no = floor_no;
    }

    public String getBed_sharing() {
        return bed_sharing;
    }
    public void setBed_sharing(String bed_sharing) {
        this.bed_sharing = bed_sharing;
    }
    public String getPerson1_name() {
        return person1_name;
    }
    public void setPerson1_name(String person1_name) {
        this.person1_name = person1_name;
    }
    public String getPerson2_name() {
        return person2_name;
    }
    public void setPerson2_name(String person2_name) {
        this.person2_name = person2_name;
    }
    public int getFloor_no() {
        return floor_no;
    }
    public void setFloor_no(int floor_no) {
        this.floor_no = floor_no;
    }

    public Integer getPerson1_id() {
        return person1_id;
    }

    public void setPerson1_id(Integer person1_id) {
        this.person1_id = person1_id;
    }

    public Integer getPerson2_id(){
        return person2_id;
    }

    public void setPerson2_id(Integer person2_id){
        this.person2_id = person2_id;
    }

    @Override
    public String toString() {
        return "Room_Details{"
                + "bed_sharing=" + bed_sharing + '\''
                + ", person1_name=" + person1_name + '\''
                + ", person2_name=" + person2_name + '\''
                + ", floor_no=" + floor_no + '\''
                + '}';
    }
}
