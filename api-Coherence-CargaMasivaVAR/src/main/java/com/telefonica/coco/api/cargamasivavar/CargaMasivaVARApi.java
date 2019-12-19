package com.telefonica.coco.api.cargamasivavar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.csvreader.CsvReader;
import com.telefonica.coco.api.flowvars.*;
import com.telefonica.coco.api.flowvars.service.FlowVarsApi;
import com.telefonica.coco.api.flowvars.service.impl.FlowVarsApiImpl;
import com.telefonica.prov.var.idallocatedresources.IdAllocatedResources_VAR;
import com.telefonica.prov.var.idallocatedresources.IdAllocatedResources_VAR_KEY;
import com.telefonica.prov.var.idallocationparameters.IDAllocationParameters_VAR;
import com.telefonica.prov.var.idallocationparameters.IDAllocationParameters_VAR_KEY;
import com.telefonica.prov.var.idcompatibilitybafresources.IdCompatibilityBAFResources_VAR;
import com.telefonica.prov.var.idcompatibilitybafresources.IdCompatibilityBAFResources_VAR_KEY;
import com.telefonica.prov.var.idcompatibilityresourcespec.IdCompatibilityResourceSpec_VAR;
import com.telefonica.prov.var.idcompatibilityresourcespec.IdCompatibilityResourceSpec_VAR_KEY;
import com.telefonica.prov.var.idforcedresource.IdForcedResource_VAR;
import com.telefonica.prov.var.idforcedresource.IdForcedResource_VAR_KEY;
import com.telefonica.prov.var.idgroupmembers.IdGroupMembers_VAR;
import com.telefonica.prov.var.idgroupmembers.IdGroupMembers_VAR_KEY;
import com.telefonica.prov.var.idinstanceswrongininventory.IdInstancesWrongInInventory_VAR;
import com.telefonica.prov.var.idinstanceswrongininventory.IdInstancesWrongInInventory_VAR_KEY;
import com.telefonica.prov.var.idparentresource.IdParentResource_VAR;
import com.telefonica.prov.var.idparentresource.IdParentResource_VAR_KEY;
import com.telefonica.prov.var.idplanmodulators.IdPlanModulators_VAR;
import com.telefonica.prov.var.idplanmodulators.IdPlanModulators_VAR_KEY;
import com.telefonica.prov.var.idplantraversalmap.IdPlanTraversalMap_VAR;
import com.telefonica.prov.var.idplantraversalmap.IdPlanTraversalMap_VAR_KEY;
import com.telefonica.prov.var.idtecsrvsimplementations.IdTechnicalServicesImplementations_VAR;
import com.telefonica.prov.var.idtecsrvsimplementations.IdTechnicalServicesImplementations_VAR_KEY;
import com.telefonica.prov.var.po.PO_VAR;
import com.telefonica.prov.var.po.PO_VAR_KEY;
import com.telefonica.prov.var.scene.Scene_VAR;
import com.telefonica.prov.var.scene.Scene_VAR_KEY;

public class CargaMasivaVARApi {

    public CargaMasivaVARApi() {
        super();
    }

    /**
     * Se analiza el tipo de VAR insertada, si es correcta, se procede a convertir la VAR y VAR_KEY de XML a objeto
     * (llamando al méotodo xml2var) y se llama a la API_FLOWVARS para su registro.
     * 
     * @param typeVar
     *            Tipo de variable a insertar
     * @param rutaFicheroCargaVar
     *            Ruta en local del fichero VAR.xml
     * @param rutaFicheroCargaKey
     *            Ruta en local del fichero VAR_KEY.xml
     * @throws JAXBException
     */
    public static void cargaUnitaria(String typeVar, String rutaFicheroCargaVar, String rutaFicheroCargaKey) {
        FlowVarsApi api = new FlowVarsApiImpl();
        
        switch (typeVar) {
        case "VAR_IdPlanTransversalMap":
            api.storeFlowVar((IdPlanTraversalMap_VAR_KEY) xml2var("IdPlanTraversalMap_VAR_KEY", rutaFicheroCargaKey),
            		(IdPlanTraversalMap_VAR) xml2var("IdPlanTraversalMap_VAR", rutaFicheroCargaVar));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;

        case "VAR_IdParentResource":
            api.storeFlowVar((IdParentResource_VAR) xml2var("IdParentResource_VAR", rutaFicheroCargaVar),
                    (IdParentResource_VAR_KEY) xml2var("IdParentResource_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdCompatibilityResourceSpec":
            api.storeFlowVar(
                    (IdCompatibilityResourceSpec_VAR) xml2var("IdCompatibilityResourceSpec_VAR", rutaFicheroCargaVar),
                    (IdCompatibilityResourceSpec_VAR_KEY) xml2var("IdCompatibilityResourceSpec_VAR_KEY",
                            rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdTecSrvsImplementations":
            api.storeFlowVar(
                    (IdTechnicalServicesImplementations_VAR) xml2var("IdTechnicalServicesImplementations_VAR", rutaFicheroCargaVar),
                    (IdTechnicalServicesImplementations_VAR_KEY) xml2var("IdTechnicalServicesImplementations_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdAllocatedResources":
            api.storeFlowVar((IdAllocatedResources_VAR) xml2var("IdAllocatedResources_VAR", rutaFicheroCargaVar),
                    (IdAllocatedResources_VAR_KEY) xml2var("IdAllocatedResources_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdInstancesWrongInInventory":
            api.storeFlowVar(
                    (IdInstancesWrongInInventory_VAR) xml2var("IdInstancesWrongInInventory_VAR", rutaFicheroCargaVar),
                    (IdInstancesWrongInInventory_VAR_KEY) xml2var("IdInstancesWrongInInventory_VAR_KEY",rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdCompatibilityResources":
            api.storeFlowVar(
                    (IdCompatibilityResourceSpec_VAR) xml2var("IdCompatibilityResourceSpec_VAR", rutaFicheroCargaVar),
                    (IdCompatibilityResourceSpec_VAR_KEY) xml2var("IdCompatibilityResourceSpec_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdGroupMembers":
            api.storeFlowVar((IdGroupMembers_VAR) xml2var("IdGroupMembers_VAR", rutaFicheroCargaVar),
                    (IdGroupMembers_VAR_KEY) xml2var("IdGroupMembers_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_Scene":
            api.storeFlowVar((Scene_VAR) xml2var("Scene_VAR", rutaFicheroCargaVar),
                    (Scene_VAR_KEY) xml2var("Scene_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdCompatibilityBAFResources":
            api.storeFlowVar(
                    (IdCompatibilityBAFResources_VAR) xml2var("IdCompatibilityBAFResources_VAR", rutaFicheroCargaVar),
                    (IdCompatibilityBAFResources_VAR_KEY) xml2var("IdCompatibilityBAFResources_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdPlanModulators":
            api.storeFlowVar((IdPlanModulators_VAR) xml2var("IdPlanModulators_VAR", rutaFicheroCargaVar),
                    (IdPlanModulators_VAR_KEY) xml2var("IdPlanModulators_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdForcedResource":
            api.storeFlowVar((IdForcedResource_VAR) xml2var("IdForcedResource_VAR", rutaFicheroCargaVar),
                    (IdForcedResource_VAR_KEY) xml2var("IdForcedResource_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_PO":
            api.storeFlowVar((PO_VAR) xml2var("PO_VAR", rutaFicheroCargaVar),
                    (PO_VAR_KEY) xml2var("PO_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        case "VAR_IdAllocationParameters":
            api.storeFlowVar((IDAllocationParameters_VAR) xml2var("IDAllocationParameters_VAR", rutaFicheroCargaVar),
                    (IDAllocationParameters_VAR_KEY) xml2var("IDAllocationParameters_VAR_KEY", rutaFicheroCargaKey));
            System.out.println("Cargado activo VAR: " + typeVar);
            break;
            
        default:
            throw new IllegalArgumentException("Tipo de VAR incorrecta: " + typeVar);
        }

    }

    /**
     * Convierte el xml a objeto.
     * 
     * @param aux
     * @param rutaFicheroCarga
     * @return
     * @throws Exception
     */
    private static Object xml2var(String var, String rutaFicheroCarga) {
        JAXBContext jaxbContext;
        try {
            String[] aux = var.split("_");
            jaxbContext = JAXBContext
                    .newInstance(Class.forName("com.telefonica.prov.var." + aux[0].toLowerCase() + "." + var));
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return jaxbUnmarshaller.unmarshal(new File(rutaFicheroCarga));
        } catch (ClassNotFoundException | JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Recibe la ruta de un fichero .csv que contiene 3 columnas: TipoVar, RutaFicheroVar, RutaFicheroKey. Hace una
     * lectura del archivo e invoca por cada registro al método cargaUnitaria
     * 
     * @param rutaArchivo
     */
    public static void cargaMasivaVar(String rutaArchivo) {
        try {
            List<ArchivoCSV> archivo = new ArrayList<ArchivoCSV>();
            CsvReader lectura = new CsvReader(rutaArchivo);
            //CsvReader lectura = new CsvReader("C:/Users/julio.llerena/Desktop/CargaVAR.csv");// args[0]);
            lectura.readHeaders();

            while (lectura.readRecord()) {
                String tipoVar = lectura.get("TipoVar");
                String rutaFicheroVAR = lectura.get("RutaFicheroVAR");
                String rutaFicheroKEY = lectura.get("RutaFicheroKEY");
                archivo.add(new ArchivoCSV(tipoVar, rutaFicheroVAR, rutaFicheroKEY));
            }
            lectura.close();
            for (ArchivoCSV us : archivo) {

                System.out.println(us.getTipoVar() + " : " + us.getRutaFicheroVAR() + " " + us.getRutaFicheroKEY());
                cargaUnitaria(us.getTipoVar(), us.getRutaFicheroVAR(), us.getRutaFicheroKEY());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
           System.err.println(e.getMessage());
        }
    }
    

    public static void main(String[] args) {
        cargaMasivaVar(args[0]);
    }
       
}
