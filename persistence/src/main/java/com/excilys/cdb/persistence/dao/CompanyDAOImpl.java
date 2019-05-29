package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.exception.CompanyDAOException;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    private EntityManager entityManager;

    private final Mapper<CompanyEntity, Company> companyEntityToCompanyMapper;

    public CompanyDAOImpl(Mapper<CompanyEntity, Company> companyEntityToCompanyMapper) {
	this.companyEntityToCompanyMapper = companyEntityToCompanyMapper;
    }

    @Override
    public long count() {
	try {
	    final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
	    cQuery.select(cBuilder.count(cQuery.from(CompanyEntity.class)));
	    final TypedQuery<Long> query = entityManager.createQuery(cQuery);
	    return query.getSingleResult();
	} catch (PersistenceException e) {
	    LOGGER.error("count()", e);
	    throw new CompanyDAOException(e);
	}
    }

    @Override
    public void deleteById(long id) {
	try {
	    final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	    final CriteriaDelete<CompanyEntity> cQuery = cBuilder.createCriteriaDelete(CompanyEntity.class);
	    final Root<CompanyEntity> c = cQuery.from(CompanyEntity.class);
	    cQuery.where(cBuilder.equal(c.get("id"), id));
	    entityManager.createQuery(cQuery).executeUpdate();
	} catch (PersistenceException e) {
	    LOGGER.error("deleteById(" + id + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    @Override
    public boolean exist(long id) {
	try {
	    return findById(id).isPresent();
	} catch (CompanyDAOException e) {
	    LOGGER.error("exist(" + id + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    @Override
    public List<Company> findAll() {
	try {
	    return mapAll(queryFindAll().getResultList());
	} catch (PersistenceException e) {
	    LOGGER.error("findAll()", e);
	    throw new CompanyDAOException(e);
	}
    }

    @Override
    public List<Company> findAll(Page page) {
	try {
	    final TypedQuery<CompanyEntity> query = queryFindAll().setFirstResult((int) page.getOffset())
		    .setMaxResults((int) page.getSize());
	    return mapAll(query.getResultList());
	} catch (PersistenceException e) {
	    LOGGER.error("findAll(" + page + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    @Override
    public Optional<Company> findById(long id) {
	try {
	    return Optional.ofNullable(entityManager.find(CompanyEntity.class, id))
		    .map(companyEntityToCompanyMapper::map);
	} catch (PersistenceException e) {
	    LOGGER.error("findById(" + id + ")", e);
	    throw new CompanyDAOException(e);
	}
    }

    private List<Company> mapAll(List<CompanyEntity> list) {
	return list.stream().map(companyEntityToCompanyMapper::map).collect(Collectors.toList());
    }

    private TypedQuery<CompanyEntity> queryFindAll() {
	final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
	final CriteriaQuery<CompanyEntity> cQuery = cBuilder.createQuery(CompanyEntity.class);
	final Root<CompanyEntity> c = cQuery.from(CompanyEntity.class);
	cQuery.select(c).orderBy(cBuilder.asc(c.get("name")));
	return entityManager.createQuery(cQuery);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

}
