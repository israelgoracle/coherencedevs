package com.telefonica.cache.loader;

import com.tangosol.net.cache.CacheLoader;
import com.tangosol.util.Base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import oracle.jdbc.OracleConnection;


public class TelcoKeyLoader extends Base implements CacheLoader {


    private String tableName;
    private String colNames;
    private String datasource;
    //protected OracleConnection m_con;
    javax.sql.DataSource ds;
    public TelcoKeyLoader() {

    }

    public TelcoKeyLoader(String datasource, String table, String cols) {
        this.tableName = table;
        this.colNames = cols;
        this.datasource = datasource;
        configureConnection();
    }


    /**
     * Set up the DB connection.
     */
    protected void configureConnection() {

        System.out.println("[ACME] Configurando conexión");
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

        //PROBANDO EN LOCAL env.put(Context.PROVIDER_URL, "t3://" + providers[i].trim());

        try {
            Context context = new InitialContext(env);
            //you will need to have create a Data Source with JNDI name testDS
            System.out.println("[ACME] Obteniendo DS");
            ds = (javax.sql.DataSource) context.lookup(datasource);
            System.out.println("[ACME] DS OK " + datasource);

        } catch (NameNotFoundException nne) {
            System.err.println(nne.getMessage());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Cols: " + this.colNames);
            System.err.println("Datasource: " + this.datasource);
        } catch (Exception ex) {
            //handle the exception
            System.err.println(ex.getMessage());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Cols: " + this.colNames);
            System.err.println("Datasource: " + this.datasource);
        }

    }
    protected OracleConnection openConnection(OracleConnection m_con) 
    {
        try {
            
            m_con = (OracleConnection) ds.getConnection();
            m_con.setAutoCommit(true);
            System.out.println("[ACME] Abriendo Conexión " + m_con.getCurrentSchema());
            return m_con;
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
        }
        return null;
    }
    protected void closeConnection(OracleConnection m_con)
    {
        try {
            
            if (m_con!=null)
            {
                System.out.println("[ACME] Cerrando conexión "  + m_con.getCurrentSchema());
                m_con.close();
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
        }
     }
    
    private String getDataAsJson(ResultSet rslt)  throws SQLException {
        ResultSetMetaData rsmd = rslt.getMetaData();
        int numColumns = rsmd.getColumnCount();
        String oValue = "";
        for (int i = 1; i <= numColumns; i++) {
            String column_name = rsmd.getColumnName(i);
            oValue += "\""+column_name+"\":\""+escape(rslt.getString(column_name))+"\"";
            if (i + 1 <= numColumns)
                oValue += ",";
        }
        
        
        return oValue;
    }
      
    @Override
    public Object load(Object oKey) {

        String oValue = null;
        OracleConnection m_con = null;
        
        try {
            StringTokenizer tokens = new StringTokenizer((String)oKey,"=");
            List keyParts = new ArrayList<String>();
            while (tokens.hasMoreTokens()) {
                     keyParts.add(tokens.nextToken());
                 }
            m_con = openConnection(m_con);
            //String sSQL = "SELECT " + getColNames() + " FROM " + getTableName() + " WHERE " + keyParts.get(0) + " = " + keyParts.get(1);
            String sSQL = "SELECT " + getColNames() + " FROM " + getTableName() + " WHERE " + keyParts.get(0) + " = ?";
            PreparedStatement stmt = m_con.prepareStatement(sSQL);
            System.out.println("[ACME] SQL "  + sSQL);
            
            //stmt.setString(1, String.valueOf(keyParts.get(1)));
            stmt.setObject(1, keyParts.get(1));
            //stmt.setInt(1,Integer.valueOf(String.valueOf(keyParts.get(1))));
            oValue = "{" + 
            "  \"records\": [ ";
            Long start = System.currentTimeMillis();
            System.out.println("[ACME] Ejecutando query para " + getTableName() + "  WHERE --> "  + keyParts.get(0) + " = " + keyParts.get(1));
            ResultSet rslt = stmt.executeQuery();
            Long end = System.currentTimeMillis();
            System.out.println("[ACME] Query "+ getTableName()+" Ejecutada WHERE --> "  + keyParts.get(0) + " = " + keyParts.get(1) + " Tiempo " + (end - start) + " ms.");
            int i = 0;
            while (rslt.next()) {
                System.out.print("[ACME] Generando JSON ");
                if (i>0) oValue+=",";
                oValue+="{";
                oValue += getDataAsJson(rslt);
                oValue+="}";
                i++;
                System.out.print(".");
            }
            oValue += "]" + 
            "}";    
            if (oValue.length()>16)
                System.out.println(oValue.substring(0, 15)+" ... ");
            stmt.close();
            closeConnection(m_con);
        } catch (SQLException e) {
            closeConnection(m_con);
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Cols: " + this.colNames);
            System.err.println("Datasource: " + this.datasource);
            System.err.println("Clave: " + oKey);
            e.printStackTrace();
            throw ensureRuntimeException(e, "Load failed: key=" + oKey);
            
        } catch (Exception ex) {
            closeConnection(m_con);
            System.err.println(ex.toString());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Cols: " + this.colNames);
            System.err.println("Datasource: " + this.datasource);
            System.err.println("Clave: " + oKey);
            ex.printStackTrace();
            return null;
        }

        return oValue;
    }

    @Override
    public Map loadAll(Collection collection) {
        HashMap res = new HashMap();
        Iterator<String> iterator = collection.iterator();
        OracleConnection m_con = null;
        while (iterator.hasNext()) {
            String oKey = iterator.next();
            
            
            try {
                StringTokenizer tokens = new StringTokenizer((String)oKey,"=");
                List keyParts = new ArrayList<String>();
                while (tokens.hasMoreTokens()) {
                         keyParts.add(tokens.nextToken());
                     }
                String oValue = null;
                String sSQL = "SELECT " + getColNames() + " FROM " + getTableName() + " WHERE " + keyParts.get(0) + " = ?";
                m_con = openConnection(m_con);
                PreparedStatement stmt = m_con.prepareStatement(sSQL);

                stmt.setString(1, String.valueOf(String.valueOf(keyParts.get(1))));
                oValue = "{" + 
                "  \"records\": [ ";
                ResultSet rslt = stmt.executeQuery();
                int i = 0;
                while (rslt.next()) {
                    if (i>0) oValue+=",";
                    oValue+="{";
                    oValue += getDataAsJson(rslt);
                    oValue+="}";
                    i++;
                    
                }
                oValue += "]" + 
                "}";
                res.put(oKey, oValue);
                stmt.close();
                closeConnection(m_con);
            } catch (SQLException e) {
                closeConnection(m_con);
                System.err.println("Tabla: " + this.tableName);
                System.err.println("Cols: " + this.colNames);
                System.err.println("Datasource: " + this.datasource);
                System.err.println("Clave: " + oKey);
                throw ensureRuntimeException(e, "Load failed: key=" + oKey);
            } catch (Exception ex) {
                closeConnection(m_con);
                System.err.println(ex.toString());
                System.err.println("Tabla: " + this.tableName);
                System.err.println("Cols: " + this.colNames);
                System.err.println("Datasource: " + this.datasource);
                System.err.println("Clave: " + oKey);
                return null;
            }
            
        }
        return res;
    }
    
    
    private String escape(String raw) {
        String escaped = raw;
        try {
            escaped = escaped.replace("\\", "\\\\");
            escaped = escaped.replace("\"", "\\\"");
            escaped = escaped.replace("\b", "\\b");
            escaped = escaped.replace("\f", "\\f");
            escaped = escaped.replace("\n", "\\n");
            escaped = escaped.replace("\r", "\\r");
            escaped = escaped.replace("\t", "\\t");
        } catch (Exception e) {
            // TODO: Add catch code
            return "";
        }
        // TODO: escape other non-printing characters using uXXXX notation
        return escaped;
    }
    

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }


    public void setColNames(String colNames) {
        this.colNames = colNames;
    }

    public String getColNames() {
        return colNames;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return datasource;
    }



    public static void main(String[] args) {
        //String datasource, String tables, String cols, String keys
        TelcoKeyLoader loader =
            new TelcoKeyLoader("jdbc/AAT/SCOTT", "SCOTT.EMP",
                               "EMPNO,ENAME,JOB,MGR,HIREDATE,SAL,COMM,DEPTNO");
        System.out.println("" + loader.load("12"));
    }

}
