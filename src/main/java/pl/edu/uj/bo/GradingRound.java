package pl.edu.uj.bo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "grading_rounds")
public class GradingRound {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "deadline")
    private Date deadline;
    @OneToMany(mappedBy = "gradingRound", fetch = FetchType.EAGER)
    private Set<Application> applications = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "GradingRound{" +
                "deadline=" + deadline +
                ", applications=" + applications.toString() +
                '}';
    }
}
