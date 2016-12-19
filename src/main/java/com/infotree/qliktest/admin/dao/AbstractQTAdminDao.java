/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.infotree.qliktest.admin.auditing.AuditManager;
import com.infotree.qliktest.admin.common.constants.QTAdminCommonConstants;
import com.infotree.qliktest.admin.entity.BaseQTAdminEntity;


/**
 * Generic DAO to implement CRUD operations for Hibernate entities.
 * This allows boiler plate code to maintained in one place while providing type-safe
 * DOA implementations, with no generic casting warnings, for all entities.
 * @param <T> the type of entity that the sub-class instance of this class works with.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractQTAdminDao<T extends BaseQTAdminEntity> implements QTAdminDao<T> {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractQTAdminDao.class);
	
	private SessionFactory sessionFactory;
	
	private AuditManager auditManager;
	
	protected final Class<T> entityClass;
	
	/**
	 * Constructor. Deduces the class type of T and determines level of query
	 * caching to apply based on the usage property of the Cache annotation on the entity.
	 */
	protected AbstractQTAdminDao() {
		//derive what type T is (for this instance) using reflection
		ParameterizedType parameterizedType = (ParameterizedType)getClass().getGenericSuperclass();
		this.entityClass = (Class<T>)parameterizedType.getActualTypeArguments()[0];
		LOG.info("======== entityClass ======== " + entityClass);
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setAuditManager(AuditManager auditManager) {
		this.auditManager = auditManager;
	}
		
	/**
	 * Get an entity from the database.
	 * @param id - the primary key of the entity to return
	 * @return a single entity specified by the primary key or null if an entity could not be found 
	 */
	public T getById(Serializable id){
		T t = (T)getSession().get(this.entityClass, id);;
		return t;
	}
	
	/**
	 * Insert a new entity into the database.
	 * @param t - instance of the entity class containing initial data to insert into the database.
	 * @return the same object that was passed to this method, with the primary key property set
	 */
	private T create(T t) {
		
		try {
			//t.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			//t.setCreatedDate(new Date());
			getSession().save(t);
			auditManager.onEntityCreation(t);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * Update an entity in the database. If the object does not already exist in the database a
	 * new one is inserted with the initial data specified by the object supplied.
	 * @param t - an object containing all data to set in the database.
	 * @return the updated persistent object (assume different to argument supplied to method)
	 */
	private T update(T t) {

		try {
			auditManager.onEntityModification(t);
			//t.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			//t.setModifiedDate(new Date());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return (T)getSession().merge(t);
	}
	
	/**
	 * Saved an entity into the database. If the entity is new a new row is inserted,
	 * otherwise an existing row is updated. The created*,modified* properties of the
	 * entity are also updated appropriately. 
	 */
	public T save(T t) {
		if(t.getId() == null) {
			return this.create(t);
		} else {
			return this.update(t);
		}
	}
	
	/**
	 * Deletes an entity from the database.
	 * @param t - the persistent object or a transient object with the primary key set.
	 */
	public String delete(T t) {
		
		try {
			getSession().delete(t);
			auditManager.onEntityDeletion(t);
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return QTAdminCommonConstants.SUCCESS;
	}
	
	/**
	 * Return all entities from the database of this type.
	 */
	public List<T> list() {
		return findMany(createBaseCriteria());		
	}	
	
	/**
	 * Return a single of entity from the database based on the supplied criteria.
	 * @param searchCriteria - Hibernate Criteria object populated with all necessary filters
	 * @return A single entity from the database. 
	 */
	protected T findOne(Criteria searchCriteria) {
		T result=null;
		result = (T)searchCriteria.uniqueResult();
		/*try {
			result = (T)searchCriteria.uniqueResult();
			if(result == null) {
				throw new NoResultException("No results found");
			}
		} catch (HibernateException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}*/
		return result;
	}

	/**
	 * Return a collection of entities from the database based on the supplied criteria.
	 * @param searchCriteria - Hibernate Criteria object populated with all necessary filters
	 * @return A collection of entities from the database. 
	 */
	protected List<T> findMany(Criteria searchCriteria) {
		return (List<T>)searchCriteria.list();
	}


	/**
	 * Gets the currently open Hibernate session in the current transaction.
	 * This this method should be used instead of createSession() or getCurrentSession()
	 * as it will ensure that the current transactional context is used.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void closeSession(){
		sessionFactory.getCurrentSession().close();
	}
	
	/**
	 * Create a new Criteria instance attached to the current session and
	 * correctly configured for caching.
	 * @param entityClass - the type of entity that is being queries for.
	 */
	public Criteria createBaseCriteria() {
		
		Criteria crit=null;
		try {
			//determine when to cache queries, based on Cache attribute of entity
			boolean cacheQueries = false;
			Cache cacheAnnotation = entityClass.getAnnotation(Cache.class);
			if(cacheAnnotation != null) {
				switch(cacheAnnotation.usage()) {
					case READ_ONLY:  cacheQueries = true;  break;
					case READ_WRITE: cacheQueries = true;  break;
					default:         cacheQueries = false; break;
				}
			}

			crit = getSession().createCriteria(entityClass);
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			crit.setCacheable(cacheQueries);
			//searchCriteria.setCacheRegion("query.myQueryRegion");
			//crit.setMaxResults(DEFAULT_MAX_RESULTS);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return crit;
	}
}
