package pl.edu.uj.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.bo.GradingRound;

@Repository
public class GradingRoundDao extends AbstractDao {
    @Transactional
    public void insert(GradingRound gradingRound) {
        Session session = getCurrentSession();
        session.save(gradingRound);
    }

    @Transactional
    public void update(GradingRound gradingRound) {
        Session session = getCurrentSession();
        session.save(gradingRound);
    }
}