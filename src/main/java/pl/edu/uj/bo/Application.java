package pl.edu.uj.bo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "application")
public class Application {
    public enum State {
        NEW, SUBMITTED, SCHEDULED, FUNDED, REJECTED
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "localization")
    private String localization;
    @Column(name = "begin_date")
    private Date beginDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "decription")
    private String description;
    @Column(name = "justification")
    private String justification;
    @Column(name = "action_plan")
    private String actionPlan;
    @Column(name = "state")
    private State state;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BudgetPool budgetPool;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User owner;

    public Application() {
        state = State.NEW;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public BudgetPool getBudgetPool() {
        return budgetPool;
    }

    public void setBudgetPool(BudgetPool budgetPool) {
        this.budgetPool = budgetPool;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}