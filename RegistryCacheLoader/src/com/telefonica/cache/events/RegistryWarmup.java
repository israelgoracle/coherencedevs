package com.telefonica.cache.events;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import com.telefonica.cache.registry.RegistryKeyLoader;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import javax.sql.DataSource;

import oracle.jdbc.OracleConnection;

    

public class RegistryWarmup {
    
    private String tableName;
    private String provider;
    private String datasource;
    protected OracleConnection m_con;
    javax.sql.DataSource ds;
    
    public RegistryWarmup() {
        super();
    }
    
    public RegistryWarmup(String provider,String datasource, String table) {

        this.tableName = table;
        this.provider = provider;
        this.datasource = datasource;
        configureConnection();
    }

    protected void configureConnection() {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        String[] providers = this.provider.split(",");
        boolean conectado = false;
        int i = 0;
        while ((conectado) || (i < providers.length)) {
            env.put(Context.PROVIDER_URL, "t3://" + providers[i].trim());
            i++;
            try {
                Context context = new InitialContext(env);
                //you will need to have create a Data Source with JNDI name testDS
                ds = (javax.sql.DataSource) context.lookup(datasource);
            } catch (NameNotFoundException nne) {
                System.err.println(nne.getMessage());
            } catch (Exception ex) {
                //handle the exception
                System.err.println(ex.getMessage());
            }
        }
    }

    protected void openConnection() {
        try {
            m_con = (OracleConnection) ds.getConnection();
            m_con.setAutoCommit(true);
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
            sqle.printStackTrace();
        }
    }

    protected void closeConnection() {
        try {
            if (m_con != null)
                m_con.close();
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
            sqle.printStackTrace();
        }
    }
    
    public void load() {

        String  sSQL   = "SELECT SERV_ID_KEY, SERV_NO_URL FROM " + getTableName();
        ArrayList<String> key = new ArrayList<String>();
        ArrayList<String> value = new ArrayList<String>();
        try
            {
            openConnection();
            
            PreparedStatement stmt = m_con.prepareStatement(sSQL);

            ResultSet rslt = stmt.executeQuery();
            while (rslt.next())
                {
                    key.add(rslt.getString(1));
                    value.add(rslt.getString(2));
                   System.out.print(".");
               
                }
                stmt.close();
                closeConnection();
                System.out.print(" ");
            }
        catch (SQLException e)
            {
                closeConnection();
                System.err.println("Tabla: " + this.tableName);
                System.err.println("Datasource: " + this.datasource);

                e.printStackTrace();
              
            }
        catch (Exception ex) {
            closeConnection();
            System.err.println(ex.toString());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Datasource: " + this.datasource);

            ex.printStackTrace();
            
        }
        NamedCache cache = CacheFactory.getCache("CACHE_UDDI");
        for (int i = 0; i < key.size(); i++) {
            System.out.println(key.get(i)+":"+value.get(i));
            cache.put(key.get(i),value.get(i));
        }

    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setCon(OracleConnection m_con) {
        this.m_con = m_con;
    }

    public OracleConnection getCon() {
        return m_con;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    public DataSource getDs() {
        return ds;
    }

    public static void main(String[] args) {
        RegistryWarmup loader = new RegistryWarmup("enhol269.serv.dev.dc.es.telefonica:7003","jdbc/COCO/UDDI_CACHE_DS", "UMD_COCOTE1.CCGEA_SERVICES");
        loader.load();
    }
}
