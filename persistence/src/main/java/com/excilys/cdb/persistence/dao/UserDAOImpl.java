package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.entity.QUserEntity;
import com.excilys.cdb.persistence.entity.UserEntity;
import com.excilys.cdb.persistence.exception.UserDAOException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class UserDAOImpl implements UserDAO {
    public static final QUserEntity Q_USER_ENTITY = QUserEntity.userEntity;
    private final Mapper<UserEntity, User> userEntityToUserMapper;
    private final Mapper<User, UserEntity> userToUserEntityMapper;
    private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;


    public UserDAOImpl(Mapper<UserEntity, User> userEntityToUserMapper,
                       Mapper<User, UserEntity> userToUserEntityMapper, JPAQueryFactory jpaQueryFactory) {
        this.userEntityToUserMapper = userEntityToUserMapper;
        this.userToUserEntityMapper = userToUserEntityMapper;
        this.jpaQueryFactory = jpaQueryFactory;
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
    public void update(User user) {
        final UserEntity entity = userToUserEntityMapper.map(user);
        entityManager.merge(entity);
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    @Transactional
    public void deleteByLogin(String login) {
        jpaQueryFactory.delete(Q_USER_ENTITY).where(Q_USER_ENTITY.login.eq(login)).execute();
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public boolean existByLogin(String login) {
        return findByLogin(login).isPresent();
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public Optional<User> findByLogin(String login) {
        UserEntity userEntity = queryFindAll().where(Q_USER_ENTITY.login.eq(login)).fetchFirst();
        return Optional.ofNullable(userEntity).map(userEntityToUserMapper::map);
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    @Transactional
    public void deleteById(long id) {
        jpaQueryFactory.delete(Q_USER_ENTITY).where(Q_USER_ENTITY.id.eq(id)).execute();
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id)).map(userEntityToUserMapper::map);
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public List<User> findAll() {
        return mapAll(queryFindAll().fetch());
    }

    private JPAQuery<UserEntity> queryFindAll() {
        return jpaQueryFactory.selectFrom(Q_USER_ENTITY).leftJoin(Q_USER_ENTITY.roles);
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public List<User> findAll(Page page) {
        List<UserEntity> companyEntities = queryFindAll().offset(page.getOffset()).limit(page.getSize()).fetch();
        return mapAll(companyEntities);
    }

    @Override
    @LogAndWrapException(logger = UserDAO.class, exception = UserDAOException.class)
    public long count() {
        return jpaQueryFactory.from(Q_USER_ENTITY).fetchCount();
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    private List<User> mapAll(List<UserEntity> list) {
        return list.stream().map(userEntityToUserMapper::map).collect(Collectors.toList());
    }
}
