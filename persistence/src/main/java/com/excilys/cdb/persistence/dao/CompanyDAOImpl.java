package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.CompanyEntity_;
import com.excilys.cdb.persistence.exception.CompanyDAOException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class CompanyDAOImpl implements CompanyDAO {

    private final Mapper<CompanyEntity, Company> companyEntityToCompanyMapper;
    private EntityManager entityManager;

    public CompanyDAOImpl(Mapper<CompanyEntity, Company> companyEntityToCompanyMapper) {
        this.companyEntityToCompanyMapper = companyEntityToCompanyMapper;
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public long count() {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Long> cQuery = cBuilder.createQuery(Long.class);
        cQuery.select(cBuilder.count(cQuery.from(CompanyEntity.class)));
        final TypedQuery<Long> query = entityManager.createQuery(cQuery);
        return query.getSingleResult();
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    @Transactional
    public void deleteById(long id) {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaDelete<CompanyEntity> cQuery = cBuilder.createCriteriaDelete(CompanyEntity.class);
        final Root<CompanyEntity> c = cQuery.from(CompanyEntity.class);
        cQuery.where(cBuilder.equal(c.get(CompanyEntity_.ID), id));
        entityManager.createQuery(cQuery).executeUpdate();
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public boolean exist(long id) {
        return findById(id).isPresent();
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public List<Company> findAll() {
        return mapAll(queryFindAll().getResultList());
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public List<Company> findAll(Page page) {
        final TypedQuery<CompanyEntity> query = queryFindAll().setFirstResult((int) page.getOffset())
                .setMaxResults((int) page.getSize());
        return mapAll(query.getResultList());
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public Optional<Company> findById(long id) {
        return Optional.ofNullable(entityManager.find(CompanyEntity.class, id))
                .map(companyEntityToCompanyMapper::map);
    }

    private List<Company> mapAll(List<CompanyEntity> list) {
        return list.stream().map(companyEntityToCompanyMapper::map).collect(Collectors.toList());
    }

    private TypedQuery<CompanyEntity> queryFindAll() {
        final CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<CompanyEntity> cQuery = cBuilder.createQuery(CompanyEntity.class);
        final Root<CompanyEntity> c = cQuery.from(CompanyEntity.class);
        cQuery.select(c).orderBy(cBuilder.asc(c.get(CompanyEntity_.NAME)));
        return entityManager.createQuery(cQuery);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
