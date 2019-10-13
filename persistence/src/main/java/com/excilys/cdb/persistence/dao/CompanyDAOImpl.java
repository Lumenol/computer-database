package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.entity.CompanyEntity;
import com.excilys.cdb.persistence.entity.QCompanyEntity;
import com.excilys.cdb.persistence.exception.CompanyDAOException;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class CompanyDAOImpl implements CompanyDAO {

    public static final QCompanyEntity Q_COMPANY_ENTITY = QCompanyEntity.companyEntity;
    private final Mapper<CompanyEntity, Company> companyEntityToCompanyMapper;
    private final Mapper<Company, CompanyEntity> companyToCompanyEntityMapper;
    private final JPAQueryFactory jpaQueryFactory;
    private EntityManager entityManager;

    public CompanyDAOImpl(Mapper<CompanyEntity, Company> companyEntityToCompanyMapper, Mapper<Company, CompanyEntity> companyToCompanyEntityMapper, JPAQueryFactory jpaQueryFactory) {
        this.companyEntityToCompanyMapper = companyEntityToCompanyMapper;
        this.companyToCompanyEntityMapper = companyToCompanyEntityMapper;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public long count() {
        return jpaQueryFactory.from(Q_COMPANY_ENTITY).fetchCount();
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    @Transactional
    public void deleteById(long id) {
        jpaQueryFactory.delete(Q_COMPANY_ENTITY).where(Q_COMPANY_ENTITY.id.eq(id)).execute();
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public boolean exist(long id) {
        return findById(id).isPresent();
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public List<Company> findAll() {
        List<CompanyEntity> companyEntities = queryFindAll().fetch();
        return mapAll(companyEntities);
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public List<Company> findAll(Page page) {
        List<CompanyEntity> companyEntities = queryFindAll().offset(page.getOffset()).limit(page.getSize()).fetch();
        return mapAll(companyEntities);
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    public Optional<Company> findById(long id) {
        return Optional.ofNullable(entityManager).map(em -> em.find(CompanyEntity.class, id))
                .map(companyEntityToCompanyMapper::map);
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    @Transactional
    public void create(Company company) {
        CompanyEntity companyEntity = companyToCompanyEntityMapper.map(company);
        Objects.requireNonNull(entityManager).persist(companyEntity);
        company.setId(companyEntity.getId());
    }

    @Override
    @LogAndWrapException(logger = CompanyDAO.class, exception = CompanyDAOException.class)
    @Transactional
    public void update(Company company) {
        final CompanyEntity companyEntity = companyToCompanyEntityMapper.map(company);
        Objects.requireNonNull(entityManager).merge(companyEntity);
    }

    private List<Company> mapAll(List<CompanyEntity> list) {
        return list.stream().map(companyEntityToCompanyMapper::map).collect(Collectors.toList());
    }

    private JPAQuery<CompanyEntity> queryFindAll() {
        return jpaQueryFactory.selectFrom(Q_COMPANY_ENTITY).orderBy(Q_COMPANY_ENTITY.name.asc());
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
