package beans;

public class JobDetails {
    private String person_name;
    private String job_name;

    public JobDetails() {}

    public JobDetails(String person_name, String job_name){
        this.person_name = person_name;
        this.job_name = job_name;
    }

    public String getPerson_name() {
        return person_name;
    }
    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }
    public String getJob_name() {
        return job_name;
    }
    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    @Override
    public String toString() {
        return "Job_Details{" +
                ", person_name = " + person_name + '\'' +
                ", job_name = " + job_name + '\'' +
                '}';
    }
}
