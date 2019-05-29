package com.excilys.cdb.persistence.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Pageable;

@Repository
public class ComputerDAOImpl implements ComputerDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private EntityManager entityManager;
    private final Mapper<ComputerEntity, Computer> computerEntityToComputerMapper;
    private final Mapper<Computer, ComputerEntity> computerToComputerEntityMapper;

    public ComputerDAOImpl(Mapper<ComputerEntity, Computer> computerEntityToComputerMapper,
	    Mapper<Computer, ComputerEntity> computerToComputerEntityMapper) {
	this.computerEntityToComputerMapper = computerEntityToComputerMapper;
	this.computerToComputerEntityMapper = computerToComputerEntityMapper;
    }

    @Override
    public long count() {
	try {
	    return countWithNameOrCompanyNameLike("");
	} catch (PersistenceException e) {
	    LOGGER.error("count()", e);
	    throw new ComputerDAOException(e);
	}
    }

    @Override
    public long countByNameOrCompanyName(String name) {
	Objects.requireNonNull(name);
	try {
	    return countWithNameOrCompanyNameLike(name);
	} catch (PersistenceException e) {
	    LOGGER.error("countByNameOrCompanyName(" + name + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    private Long countWithNameOrCompanyNameLike(String name) {
	final String pattern = "%" + name.toUpperCase() + "%";
	final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	final CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
	final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
	c.join("manufacturer", JoinType.LEFT);
	final Predicate namePredicate = cBuilder.like(cBuilder.upper(c.get("name")), pattern);
	final Predicate companyNamePredicate = cBuilder.like(cBuilder.upper(c.get("manufacturer").get("name")),
		pattern);

	cQuery.select(cBuilder.count(c)).where(cBuilder.or(namePredicate, companyNamePredicate));

	return entityManager.createQuery(cQuery).getSingleResult();
    }

    @Override
    public long create(Computer computer) {
	try {
	    final ComputerEntity cEntity = computerToComputerEntityMapper.map(computer);
	    entityManager.persist(cEntity);
	    return cEntity.getId();
	} catch (PersistenceException e) {
	    LOGGER.error("create(" + computer + ")", e);
	    throw new ComputerDAOException(e);
	}

    }

    @Override
    public void deleteById(long id) {
	try {
	    final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaDelete<ComputerEntity> cQuery = cBuilder.createCriteriaDelete(ComputerEntity.class);
	    final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
	    cQuery.where(cBuilder.equal(c.get("id"), id));
	    entityManager.createQuery(cQuery).executeUpdate();
	} catch (PersistenceException e) {
	    LOGGER.error("deleteById(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    @Override
    public void deleteBymanufacturerId(long id) {
	try {
	    final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaDelete<ComputerEntity> cQuery = cBuilder.createCriteriaDelete(ComputerEntity.class);
	    final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);
	    cQuery.where(cBuilder.equal(c.get("manufacturer").get("id"), id));
	    entityManager.createQuery(cQuery).executeUpdate();
	} catch (PersistenceException e) {
	    LOGGER.error("deleteBymanufacturerId(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    @Override
    public boolean exist(long id) {
	try {
	    return findById(id).isPresent();
	} catch (ComputerDAOException e) {
	    LOGGER.error("exist(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    @Override
    public List<Computer> findAll(Pageable pageable) {
	try {
	    return findAllWithNameOrCompanyNameLike("", pageable);
	} catch (PersistenceException e) {
	    LOGGER.error("findAll(" + pageable + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    private List<Computer> findAllWithNameOrCompanyNameLike(String name, Pageable pageable) {
	final String pattern = "%" + name.toUpperCase() + "%";
	final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	final CriteriaQuery<ComputerEntity> cQuery = cBuilder.createQuery(ComputerEntity.class);
	final Root<ComputerEntity> c = cQuery.from(ComputerEntity.class);

	final Predicate namePredicate = cBuilder.like(cBuilder.upper(c.get("name")), pattern);
	final Predicate companyNamePredicate = cBuilder.like(cBuilder.upper(c.get("manufacturer").get("name")),
		pattern);

	cQuery.select(c).where(cBuilder.or(namePredicate, companyNamePredicate))
		.orderBy(orderByToOrder(cBuilder, c, pageable.getOrderBy()));
	final TypedQuery<ComputerEntity> query = entityManager.createQuery(cQuery)
		.setFirstResult((int) pageable.getPage().getOffset()).setMaxResults((int) pageable.getPage().getSize());

	return query.getResultList().stream().map(computerEntityToComputerMapper::map).collect(Collectors.toList());
    }

    @Override
    public Optional<Computer> findById(long id) {
	try {
	    return Optional.ofNullable(entityManager.find(ComputerEntity.class, id))
		    .map(computerEntityToComputerMapper::map);
	} catch (PersistenceException e) {
	    LOGGER.error("findById(" + id + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    private List<Order> orderByToOrder(CriteriaBuilder cBuilder, Root<ComputerEntity> c, OrderBy orderBy) {
	final Path<Object> field;
	final Function<Expression<?>, Order> direction;
	final Path<Object> name = c.get("name");
	final Path<Object> id = c.get("id");
	switch (orderBy.getField()) {
	default:
	case ID:
	    field = id;
	    break;
	case NAME:
	    field = name;
	    break;
	case INTRODUCED:
	    field = c.get("introduced");
	    break;
	case DISCONTINUED:
	    field = c.get("discontinued");
	    break;
	case COMPANY:
	    field = c.get("manufacturer").get("name");
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
    public List<Computer> searchByNameOrCompanyName(Pageable pageable, String name) {
	Objects.requireNonNull(name);
	try {
	    return findAllWithNameOrCompanyNameLike(name, pageable);
	} catch (PersistenceException e) {
	    LOGGER.error("searchByNameOrCompanyName(" + pageable + "," + name + ")", e);
	    throw new ComputerDAOException(e);
	}
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    @Override
    public void update(Computer computer) {
	try {
	    final ComputerEntity entity = computerToComputerEntityMapper.map(computer);
	    entityManager.merge(entity);
	} catch (PersistenceException e) {
	    LOGGER.error("update(" + computer + ")", e);
	    throw new ComputerDAOException(e);
	}

    }
}
