package pl.edu.uj.bo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "budget_pools")
public class BudgetPool {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private Integer value;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "budget_name", nullable = false)
    private Budget budget;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetPool")
    private Set<Application> applications = new HashSet<>();

    public BudgetPool() {
    }

    public BudgetPool(String name, Integer value, Budget budget) {
        this.name = name;
        this.value = value;
        this.budget = budget;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }
}
