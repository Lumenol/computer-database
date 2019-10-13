package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.entity.ComputerEntity;
import com.excilys.cdb.persistence.entity.QComputerEntity;
import com.excilys.cdb.persistence.exception.ComputerDAOException;
import com.excilys.cdb.shared.logexception.LogAndWrapException;
import com.excilys.cdb.shared.mapper.Mapper;
import com.excilys.cdb.shared.pagination.OrderBy;
import com.excilys.cdb.shared.pagination.Pageable;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class ComputerDAOImpl implements ComputerDAO {
    public static final QComputerEntity Q_COMPUTER_ENTITY = QComputerEntity.computerEntity;
    private final Mapper<ComputerEntity, Computer> computerEntityToComputerMapper;
    private final Mapper<Computer, ComputerEntity> computerToComputerEntityMapper;
    private final JPAQueryFactory jpaQueryFactory;
    private EntityManager entityManager;

    public ComputerDAOImpl(Mapper<ComputerEntity, Computer> computerEntityToComputerMapper,
                           Mapper<Computer, ComputerEntity> computerToComputerEntityMapper, JPAQueryFactory jpaQueryFactory) {
        this.computerEntityToComputerMapper = computerEntityToComputerMapper;
        this.computerToComputerEntityMapper = computerToComputerEntityMapper;
        this.jpaQueryFactory = jpaQueryFactory;
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
        final String pattern = "%" + name.toUpperCase(Locale.ENGLISH) + "%";
        return jpaQueryFactory.from(Q_COMPUTER_ENTITY).leftJoin(Q_COMPUTER_ENTITY.manufacturer).where(Q_COMPUTER_ENTITY.name.toUpperCase().like(pattern).or(Q_COMPUTER_ENTITY.manufacturer.name.toUpperCase().like(pattern))).fetchCount();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    @Transactional
    public void create(Computer computer) {
        final ComputerEntity cEntity = computerToComputerEntityMapper.map(computer);
        Objects.requireNonNull(entityManager).persist(cEntity);
        computer.setId(cEntity.getId());
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    @Transactional
    public void deleteById(long id) {
        jpaQueryFactory.delete(Q_COMPUTER_ENTITY).where(Q_COMPUTER_ENTITY.id.eq(id)).execute();
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    @Transactional
    public void deleteBymanufacturerId(long id) {
        jpaQueryFactory.delete(Q_COMPUTER_ENTITY).where(Q_COMPUTER_ENTITY.manufacturer.id.eq(id)).execute();
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
        final String pattern = "%" + name.toUpperCase(Locale.ENGLISH) + "%";
        return jpaQueryFactory.selectFrom(Q_COMPUTER_ENTITY).leftJoin(Q_COMPUTER_ENTITY.manufacturer)
                .where(Q_COMPUTER_ENTITY.name.toUpperCase().like(pattern).or(Q_COMPUTER_ENTITY.manufacturer.name.toUpperCase().like(pattern)))
                .offset(pageable.getPage().getOffset()).limit(pageable.getPage().getSize())
                .orderBy(orderByToOrder(pageable.getOrderBy()))
                .fetch()
                .stream().map(computerEntityToComputerMapper::map).collect(Collectors.toList());
    }

    @Override
    @LogAndWrapException(logger = ComputerDAO.class, exception = ComputerDAOException.class)
    public Optional<Computer> findById(long id) {
        return Optional.ofNullable(entityManager).map(em -> em.find(ComputerEntity.class, id))
                .map(computerEntityToComputerMapper::map);
    }

    private OrderSpecifier[] orderByToOrder(OrderBy orderBy) {
        final ComparableExpressionBase field;
        final Function<ComparableExpressionBase, OrderSpecifier> direction;
        StringPath name = Q_COMPUTER_ENTITY.name;
        NumberPath<Long> id = Q_COMPUTER_ENTITY.id;
        switch (orderBy.getField()) {
            default:
            case ID:
                field = id;
                break;
            case NAME:
                field = name;
                break;
            case INTRODUCED:
                field = Q_COMPUTER_ENTITY.introduced;
                break;
            case DISCONTINUED:
                field = Q_COMPUTER_ENTITY.discontinued;
                break;
            case COMPANY:
                field = Q_COMPUTER_ENTITY.manufacturer.name;
                break;
        }
        if (orderBy.getDirection() == OrderBy.Direction.DESC) {
            direction = ComparableExpressionBase::desc;
        } else {
            direction = ComparableExpressionBase::asc;
        }

        return new OrderSpecifier[]{direction.apply(field).nullsLast(), direction.apply(name), direction.apply(id)};
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
    @Transactional
    public void update(Computer computer) {
        final ComputerEntity entity = computerToComputerEntityMapper.map(computer);
        Objects.requireNonNull(entityManager).merge(entity);
    }
}
