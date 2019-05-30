package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.entity.CompanyEntity_;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.persistence.entity.ComputerEntity_;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
    private final Mapper<ComputerEntity, Computer> computerEntityToComputerMapper;
    private final Mapper<Computer, ComputerEntity> computerToComputerEntityMapper;
    private EntityManager entityManager;

    public ComputerDAOImpl(Mapper<ComputerEntity, Computer> computerEntityToComputerMapper,
                           Mapper<Computer, ComputerEntity> computerToComputerEntityMapper) {
        this.computerEntityToComputerMapper = computerEntityToComputerMapper;
        this.computerToComputerEntityMapper = computerToComputerEntityMapper;
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public long count() {
        return countWithNameOrCompanyNameLike("");
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public long countByNameOrCompanyName(String name) {
        Objects.requireNonNull(name);
        return countWithNameOrCompanyNameLike(name);
    }

    private Long countWithNameOrCompanyNameLike(String name) {
        final String pattern = "%" + name.toUpperCase() + "%";
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
        final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
        c.join(ComputerEntity_.MANUFACTURER, JoinType.LEFT);
        final Predicate namePredicate = cBuilder.like(cBuilder.upper(c.get(ComputerEntity_.NAME)), pattern);
        final Predicate companyNamePredicate = cBuilder.like(cBuilder.upper(c.get(ComputerEntity_.MANUFACTURER).get(CompanyEntity_.NAME)),
                pattern);

        cQuery.select(cBuilder.count(c)).where(cBuilder.or(namePredicate, companyNamePredicate));

        return entityManager.createQuery(cQuery).getSingleResult();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public long create(Computer computer) {
        final ComputerEntity cEntity = computerToComputerEntityMapper.map(computer);
        entityManager.persist(cEntity);
        return cEntity.getId();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public void deleteById(long id) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaDelete<ComputerEntity> cQuery = cBuilder.createCriteriaDelete(ComputerEntity.class);
        final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
        cQuery.where(cBuilder.equal(c.get(ComputerEntity_.ID), id));
        entityManager.createQuery(cQuery).executeUpdate();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public void deleteBymanufacturerId(long id) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaDelete<ComputerEntity> cQuery = cBuilder.createCriteriaDelete(ComputerEntity.class);
        final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
        cQuery.where(cBuilder.equal(c.get(ComputerEntity_.MANUFACTURER).get(CompanyEntity_.ID), id));
        entityManager.createQuery(cQuery).executeUpdate();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public boolean exist(long id) {
        return findById(id).isPresent();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public List<Computer> findAll(Pageable pageable) {
        return findAllWithNameOrCompanyNameLike("", pageable);
    }

    private List<Computer> findAllWithNameOrCompanyNameLike(String name, Pageable pageable) {
        final String pattern = "%" + name.toUpperCase() + "%";
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ComputerEntity> cQuery = cBuilder.createQuery(ComputerEntity.class);
        final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
        c.join(ComputerEntity_.MANUFACTURER, JoinType.LEFT);
        final Predicate namePredicate = cBuilder.like(cBuilder.upper(c.get(ComputerEntity_.NAME)), pattern);
        final Predicate companyNamePredicate = cBuilder.like(cBuilder.upper(c.get(ComputerEntity_.MANUFACTURER).get(CompanyEntity_.NAME)),
                pattern);

        cQuery.select(c).where(cBuilder.or(namePredicate, companyNamePredicate))
                .orderBy(orderByToOrder(cBuilder, c, pageable.getOrderBy()));
        final TypedQuery<ComputerEntity> query = entityManager.createQuery(cQuery)
                .setFirstResult((int) pageable.getPage().getOffset()).setMaxResults((int) pageable.getPage().getSize());

        return query.getResultList().stream().map(computerEntityToComputerMapper::map).collect(Collectors.toList());
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public Optional<Computer> findById(long id) {
        return Optional.ofNullable(entityManager.find(ComputerEntity.class, id))
                .map(computerEntityToComputerMapper::map);
    }

    private List<Order> orderByToOrder(CriteriaBuilder cBuilder, Root<ComputerEntity> c, OrderBy orderBy) {
        final Path<Object> field;
        final Function<Expression<?>, Order> direction;
        final Path<Object> name = c.get(ComputerEntity_.NAME);
        final Path<Object> id = c.get(ComputerEntity_.ID);
        switch (orderBy.getField()) {
            default:
            case ID:
                field = id;
                break;
            case NAME:
                field = name;
                break;
            case INTRODUCED:
                field = c.get(ComputerEntity_.INTRODUCED);
                break;
            case DISCONTINUED:
                field = c.get(ComputerEntity_.DISCONTINUED);
                break;
            case COMPANY:
                field = c.get(ComputerEntity_.MANUFACTURER).get(CompanyEntity_.NAME);
                break;
        }
        if (orderBy.getDirection() == OrderBy.Direction.DESC) {
            direction = cBuilder::desc;
        } else {
            direction = cBuilder::asc;
        }

        return Arrays.asList(direction.apply(field), direction.apply(name), direction.apply(id));
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public List<Computer> searchByNameOrCompanyName(Pageable pageable, String name) {
        Objects.requireNonNull(name);
        return findAllWithNameOrCompanyNameLike(name, pageable);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public void update(Computer computer) {
        final ComputerEntity entity = computerToComputerEntityMapper.map(computer);
        entityManager.merge(entity);
    }
}
