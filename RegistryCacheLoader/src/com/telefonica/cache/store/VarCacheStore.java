package com.telefonica.cache.store;

import com.tangosol.net.cache.CacheStore;
import com.tangosol.util.Base;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import oracle.jdbc.OracleConnection;


/**
 * Implementación del CacheStore
 *
 *
 * @author udat 2019.10.14
 */
public class VarCacheStore extends Base implements CacheStore {  
    private String tableName;
    private String datasource;
    protected OracleConnection m_con;
    javax.sql.DataSource ds;
    // ----- constructors ---------------------------------------------------
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return datasource;
    }
    /**
     * Constructor.
     *
     * @param sTableName the db table name
     */
    public VarCacheStore(String datasource, String table) {
        this.tableName = table;
        this.datasource = datasource;
        configureConnection();
    }

    /**
     * Set up the DB connection.
     */
    protected void configureConnection() {


        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

        //PROBANDO EN LOCAL env.put(Context.PROVIDER_URL, "t3://" + providers[i].trim());

        try {
            Context context = new InitialContext(env);
            //you will need to have create a Data Source with JNDI name testDS
            ds = (javax.sql.DataSource) context.lookup(datasource);


        } catch (NameNotFoundException nne) {
            System.err.println(nne.getMessage());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Datasource: " + this.datasource);
        } catch (Exception ex) {
            //handle the exception
            System.err.println(ex.getMessage());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Datasource: " + this.datasource);
        }

    }

    protected void openConnection() {
        try {
            m_con = (OracleConnection) ds.getConnection();
            m_con.setAutoCommit(true);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
        }
    }

    protected void closeConnection() {
        try {
            if (m_con != null)
                m_con.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
        }
    }

    // ----- CacheStore Interface --------------------------------------------

    /**
     * Return the value associated with the specified key, or null if the
     * key does not have an associated value in the underlying store.
     *
     * @param oKey  key whose associated value is to be returned
     *
     * @return the value associated with the specified key, or
     *         <tt>null</tt> if no value is available for that key
     */


    public Object load(Object oKey) {
        Object oValue = null;
        openConnection();
        String sSQL = "SELECT id, descripcion FROM " + getTableName() + " WHERE id = ?";

        System.out.println("------------- load " + oKey);

        try {
            PreparedStatement stmt = m_con.prepareStatement(sSQL);

            stmt.setString(1, String.valueOf(oKey));

            ResultSet rslt = stmt.executeQuery();
            if (rslt.next()) {
                oValue = rslt.getString(2);
                if (rslt.next()) {
                    closeConnection();
                    throw new SQLException("Not a unique key: " + oKey);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            closeConnection();
            throw ensureRuntimeException(e, "Load failed: key=" + oKey);
        }
        closeConnection();
        return oValue;
    }

    /**
     * Store the specified value under the specific key in the underlying
     * store. This method is intended to support both key/value creation
     * and value update for a specific key.
     *
     * @param oKey    key to store the value under
     * @param oValue  value to be stored
     *
     * @throws UnsupportedOperationException  if this implementation or the
     *         underlying store is read-only
     */
    public void store(Object oKey, Object oValue) {
       
        String sTable = getTableName();
        String sSQL;
        System.out.println("------------- store " + oKey + " -- " + oValue);
        // the following is very inefficient; it is recommended to use DB
        // specific functionality that is, REPLACE for MySQL or MERGE for Oracle
        if (load(oKey) != null) {
            // key exists - update
            sSQL = "UPDATE " + sTable + " SET descripcion = ? where id = ?";
        } else {
            // new key - insert
            sSQL = "INSERT INTO " + sTable + " (descripcion,id) VALUES (?,?)";
        }
        
        openConnection();
        try {
            
            PreparedStatement stmt = m_con.prepareStatement(sSQL);
            int i = 0;
            
            stmt.setString(++i, String.valueOf(oValue));
            stmt.setString(++i, String.valueOf(oKey));
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            closeConnection();
            throw ensureRuntimeException(e, "Store failed: key=" + oKey);
        }
        closeConnection();
    }

    /**
     * Remove the specified key from the underlying store if present.
     *
     * @param oKey key whose mapping is to be removed from the map
     *
     * @throws UnsupportedOperationException  if this implementation or the
     *         underlying store is read-only
     */


    public void erase(Object oKey) {
        openConnection();
        String sSQL = "DELETE FROM " + getTableName() + " WHERE id=?";
        try {
            PreparedStatement stmt = m_con.prepareStatement(sSQL);

            stmt.setString(1, String.valueOf(oKey));
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            closeConnection();
            throw ensureRuntimeException(e, "Erase failed: key=" + oKey);

        }
        closeConnection();
    }

    /**
     * Remove the specified keys from the underlying store if present.
     *
     * @param colKeys  keys whose mappings are being removed from the cache
     *
     * @throws UnsupportedOperationException  if this implementation or the
     *         underlying store is read-only
     */

    public void eraseAll(Collection colKeys) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the values associated with each the specified keys in the
     * passed collection. If a key does not have an associated value in
     * the underlying store, then the return map does not have an entry
     * for that key.
     *
     * @param colKeys  a collection of keys to load
     *
     * @return a Map of keys to associated values for the specified keys
     */
    public Map loadAll(Collection colKeys) {
        throw new UnsupportedOperationException();
    }

    /**
     * Store the specified values under the specified keys in the underlying
     * store. This method is intended to support both key/value creation
     * and value update for the specified keys.
     *
     * @param mapEntries   a Map of any number of keys and values to store
     *
     * @throws UnsupportedOperationException  if this implementation or the
     *         underlying store is read-only
     */
    public void storeAll(Map mapEntries) {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterate all keys in the underlying store.
     *
     * @return a read-only iterator of the keys in the underlying store
     */
    public Iterator keys() {
        openConnection();
        String sSQL = "SELECT id FROM " + getTableName();
        List list = new LinkedList();

        try {
            PreparedStatement stmt = m_con.prepareStatement(sSQL);
            ResultSet rslt = stmt.executeQuery();
            while (rslt.next()) {
                Object oKey = rslt.getString(1);
                list.add(oKey);
            }
            stmt.close();
        } catch (SQLException e) {
            closeConnection();
            throw ensureRuntimeException(e, "Iterator failed");
        }
        closeConnection();
        return list.iterator();
    }


    // ----- data members ---------------------------------------------------


}
