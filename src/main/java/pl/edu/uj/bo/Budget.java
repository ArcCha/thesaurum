package pl.edu.uj.bo;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "begin_date")
    private Date beginDate;
    @Column(name = "end_date")
    private Date endDate;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "budget", cascade = { CascadeType.ALL })
    private Set<BudgetPool> budgetPools = new HashSet<>();

    public Budget() {
    }

    public Budget(String name, Date beginDate, Date endDate) {
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
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

    public Set<BudgetPool> getBudgetPools() {
        return budgetPools;
    }

    public void setBudgetPools(Set<BudgetPool> budgetPools) {
        this.budgetPools = budgetPools;
    }

    public void addBudgetPool(BudgetPool pool) { budgetPools.add(pool); pool.setBudget(this); }
}
