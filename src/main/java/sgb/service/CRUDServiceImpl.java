package sgb.service;


import org.hibernate.LockMode;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sgb.dao.CRUDDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service
//@Transactional //para um thread que nao depende de seccao, mas se for para abrir nova transacao, nao funciona
public class CRUDServiceImpl implements CRUDService {

    @Autowired
    private CRUDDao cruddao;

    @Transactional(readOnly = true)
    public <T> List<T> getAll(Class<T> klass) {
        return cruddao.getAll(klass);
    }

    @Transactional(readOnly = true)
    public <T> List<T> getAllQuery(String s) {
        return cruddao.getAllQuery(s);
    }

    @Transactional
    public <T> void Save(T klass) {
        cruddao.Save(klass);
    }

    @Transactional
    public <T> void refresh(T klass) {
        cruddao.refresh(klass);
    }

    public <T> void Saves(T klass) {
        cruddao.Saves(klass);
    }

    @Transactional
    public <T> void delete(T klass) {
        cruddao.delete(klass);
    }

    public <T> void deletes(T klass) {
        cruddao.deletes(klass);
    }

    public <T> void lock(T klass) {
        cruddao.lock(klass);
    }

    @Transactional
    public <T> void update(T klass) {
        cruddao.update(klass);
    }

    public <T> void updates(T klass) {
        cruddao.updates(klass);
    }

    public <T> LockMode bloqueado(T klass) {
        return cruddao.bloqueado(klass);
    }

    @Transactional
    public <T> boolean exist(T klass) {
        return cruddao.exist(klass);
    }

    // @Transactional
    public <T> Transaction getTransacao() {
        return cruddao.getTransacao();
    }

    @Transactional
    public <T> int updateQuery(String query, Object... params) {
        return cruddao.updateQuery(query, params);
    }

    @Transactional
    public <T> int count(Class<T> klass) {
        return cruddao.count(klass);
    }

    @Transactional
    public <T> Long countJPQuery(String hql, Map<String, Object> namedParams) {
        return cruddao.countJPQuery(hql, namedParams);
    }

    @Transactional
    public <T> Float sumJPQuery(String hql, Map<String, Object> namedParams) {
        return cruddao.sumJPQuery(hql, namedParams);
    }

    @Transactional
    public <T> T GetUniqueEntityByNamedQuery(String query, Object... params) {
        return cruddao.GetUniqueEntityByNamedQuery(query, params);
    }

    @Transactional
    public <T> List<T> GetAllEntityByNamedQuery(String query, Object... params) {
        return cruddao.GetAllEntityByNamedQuery(query, params);
    }

    @Transactional
    public <T> List<T> findByQuery(String hql, Map<String, Object> entidade, Map<String, Object> namedParams) {
        return cruddao.findByQuery(hql, entidade, namedParams);
    }

    @Transactional
    public <T> List<T> findByQueryFilter(String hql, Map<String, Object> entidade, Map<String, Object> namedParams, int f, int m) {
        return cruddao.findByQueryFilter(hql, entidade, namedParams, f, m);
    }

    // @Transactional
    public <T> List<T> findByJPQuery(String hql, Map<String, Object> namedParams) {
        return cruddao.findByJPQuery(hql, namedParams);
    }

    //@Transactional
    public <T> T findEntByJPQuery(String hql, Map<String, Object> namedParams) {
        return cruddao.findEntByJPQuery(hql, namedParams);
    }

    @Transactional
    public <T> T findEntByJPQueryT(String hql, Map<String, Object> namedParams) {//com transacao
        return cruddao.findEntByJPQueryT(hql, namedParams);
    }

    //@Transactional
    public <T> T findEntByJPQueryLock(String hql, Map<String, Object> namedParams) {
        return cruddao.findEntByJPQueryLock(hql, namedParams);
    }

    @Transactional
    public <T> List<T> findByJPQueryFilter(String hql, Map<String, Object> namedParams, int f, int m) {
        return cruddao.findByJPQueryFilter(hql, namedParams, f, m);
    }

    //@Transactional
    public <T> T load(Class<T> klass, Serializable id) {
        return cruddao.load(klass, id);
    }

    // @Transactional
    public <T> T get(Class<T> klass, Serializable id) {
        return cruddao.get(klass, id);
    }

    //@Transactional
    public <T> T loadLocked(Class<T> klass, Serializable id) {
        return cruddao.loadLocked(klass, id);
    }

    //@Transactional
    public <T> T getLocked(Class<T> klass, Serializable id) {
        return cruddao.getLocked(klass, id);
    }

    ////////////////////////////////////////tenant param///////////////////////////////////////////

    public <T> T getSes(Class<T> klass, Serializable id, String tenant) {
        return cruddao.getSes(klass, id, tenant);
    }

    public <T> T findEntByJPQuerySes(String hql, Map<String, Object> namedParams, String tenant) {
        return cruddao.findEntByJPQuerySes(hql, namedParams, tenant);
    }

    public <T> void updateSes(T klass, String tenant) {
        cruddao.updateSes(klass, tenant);
    }

    public <T> void SaveSes(T klass, String tenant) {
        cruddao.SaveSes(klass, tenant);
    }

    public <T> void refreshSes(T klass, String tenant) {
        cruddao.refreshSes(klass, tenant);
    }

    public <T> int updateQuerySes(String query, String tenant) {
        return cruddao.updateQuerySes(query, tenant);
    }

}
