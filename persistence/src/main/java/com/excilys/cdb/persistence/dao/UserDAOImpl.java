package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.entity.UserEntity;
import com.excilys.cdb.persistence.entity.UserEntity_;
import com.excilys.cdb.persistence.exception.UserDAOException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class UserDAOImpl implements UserDAO {
    private final Mapper<UserEntity, User> userEntityToUserMapper;
    private final Mapper<User, UserEntity> userToUserEntityMapper;
    private EntityManager entityManager;

    public UserDAOImpl(Mapper<UserEntity, User> userEntityToUserMapper,
                       Mapper<User, UserEntity> userToUserEntityMapper) {
        this.userEntityToUserMapper = userEntityToUserMapper;
        this.userToUserEntityMapper = userToUserEntityMapper;
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    @Transactional
    public void create(User user) {
        final UserEntity userEntity = userToUserEntityMapper.map(user);
        entityManager.persist(userEntity);
        user.setId(userEntity.getId());
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    @Transactional
    public void deleteByLogin(String login) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaDelete<UserEntity> cQuery = cBuilder.createCriteriaDelete(UserEntity.class);
        final Root<UserEntity> c = cQuery.from(UserEntity.class);
        cQuery.where(cBuilder.equal(c.get(UserEntity_.LOGIN), login));
        entityManager.createQuery(cQuery).executeUpdate();
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public boolean existByLogin(String login) {
        return findByLogin(login).isPresent();
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public Optional<User> findByLogin(String login) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<UserEntity> cQuery = cBuilder.createQuery(UserEntity.class);
        final Root<UserEntity> c = cQuery.from(UserEntity.class);
        cQuery.select(c).where(cBuilder.equal(c.get(UserEntity_.LOGIN), login));

        return Optional.of(cQuery).map(entityManager::createQuery).map(TypedQuery::getResultList)
                .filter(l -> !l.isEmpty()).map(l -> l.get(0))
                .map(userEntityToUserMapper::map);
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    @Transactional
    public void deleteById(long id) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaDelete<UserEntity> cQuery = cBuilder.createCriteriaDelete(UserEntity.class);
        final Root<UserEntity> c = cQuery.from(UserEntity.class);
        cQuery.where(cBuilder.equal(c.get(UserEntity_.ID), id));
        entityManager.createQuery(cQuery).executeUpdate();
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id)).map(userEntityToUserMapper::map);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
