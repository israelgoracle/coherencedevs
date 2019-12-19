package com.telefonica.cache.registry;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.CacheLoader;
import com.tangosol.util.Base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;

import oracle.jdbc.OracleConnection;

public class RegistryKeyLoader extends Base  implements CacheLoader {
    

    private String tableName;
    private String provider;
    private String datasource;
    protected OracleConnection m_con;
    javax.sql.DataSource ds;
    private String hora;
    
    public RegistryKeyLoader() {
     
    }
    
    public RegistryKeyLoader(String provider,String datasource, String table) {

        this.tableName = table;
        this.provider = provider;
        this.datasource = datasource;
        m_con = null;
        configureConnection();
        Calendar cal = Calendar.getInstance();
        
        
    }
    
    
    /** 
            * Set up the DB connection.
            */
            protected void configureConnection()
                    {
                        Hashtable env = new Hashtable();
                        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
                        String[] providers = this.provider.split(",");
                        boolean conectado = false;
                        int i = 0;
                        while ((conectado) || (i<providers.length))
                        {
                                env.put(Context.PROVIDER_URL, "t3://" + providers[i].trim());
                                i++;
                                try {
                                    Calendar cal = Calendar.getInstance();
                                    hora = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
                                    Context context = new InitialContext(env);
                                    //you will need to have create a Data Source with JNDI name testDS
                                    ds = (javax.sql.DataSource) context.lookup(datasource);  
                                    //System.out.println(hora + " ********************** OBTENIENDO DATASOURCE");
                                } catch (NameNotFoundException nne) {
                                    System.err.println(nne.getMessage());
                                } catch (Exception ex) {
                                    //handle the exception
                                    System.err.println(ex.getMessage());
                                }
                        }
                    }

    protected void openConnection() 
    {
        long espera = 0;
    
            try {
                Calendar cal = Calendar.getInstance();
                hora = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
                //System.out.println(hora + " ********************** OBTENIENDO CONEXION");
                m_con = (OracleConnection) ds.getConnection();
                m_con.setAutoCommit(true);

                //System.out.println(hora + " ********************** CONEXION OBTENIDA");
            } catch (SQLException sqle) {
                // TODO: Add catch code
                System.err.println(sqle.getMessage());
                sqle.printStackTrace();
            }
                        
    }
    protected void closeConnection()
    {
        try {
            if (m_con!=null)
            {
                Calendar cal = Calendar.getInstance();
                hora = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
                m_con.close();
                //System.out.println(hora + " ********************** CONEXION CERRADA");
                
            }
        } catch (SQLException sqle) {
            // TODO: Add catch code
            System.err.println(sqle.getMessage());
            sqle.printStackTrace();
        }
     }
    
    @Override
    public Object load(Object oKey) {

        String     oValue = null;

        String     sSQL   = "SELECT SERV_ID_KEY, SERV_NO_URL FROM " + getTableName()
                          + " WHERE SERV_ID_KEY = ?";
        
        try
            {
            openConnection();
                Calendar cal = Calendar.getInstance();
                hora = cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
                //System.out.println(hora + " ********************** PREPARANDO SENTENCIA");
            PreparedStatement stmt = m_con.prepareStatement(sSQL);

            stmt.setString(1, String.valueOf(String.valueOf(oKey)));
                
            ResultSet rslt = stmt.executeQuery();
            if (rslt.next())
            {
                oValue = rslt.getString(2);
                //System.out.println(String.valueOf(oKey) + " OK");
                stmt.close();
                //System.out.println(hora + " ********************** CERRANDO SENTENCIA");
                closeConnection();
            }
            else {
                //System.out.println(String.valueOf(oKey) + " No existe");
                stmt.close();
                //System.out.println(hora + " ********************** CERRANDO SENTENCIA");
                closeConnection();
                
                return null;
            }
                
            }
        catch (SQLException e)
            {
                closeConnection();
                System.err.println("Tabla: " + this.tableName);
                System.err.println("Datasource: " + this.datasource);
                System.err.println("Clave: " + oKey);
                e.printStackTrace();
                throw ensureRuntimeException(e, "Load failed: key=" + oKey);
            }
        catch (Exception ex) {
            closeConnection();
            System.err.println(ex.toString());
            System.err.println("Tabla: " + this.tableName);
            System.err.println("Datasource: " + this.datasource);
            System.err.println("Clave: " + oKey);
            ex.printStackTrace();
            return null;
        }
        
        return oValue;
    }

    @Override
    public Map loadAll(Collection collection) {
        throw new UnsupportedOperationException();
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

        
    

        
    public static void main(String[] args) {
        
        RegistryKeyLoader loader = new RegistryKeyLoader("enhol269.serv.dev.dc.es.telefonica:7003","jdbc/COCO/UDDI_CACHE_DS", "UMD_COCOTE1.CCGEA_SERVICES");
        //System.out.println("" + loader.load("uddi:logi.srv-nuc-MngBIStatus-v3"));
    }
      
}
