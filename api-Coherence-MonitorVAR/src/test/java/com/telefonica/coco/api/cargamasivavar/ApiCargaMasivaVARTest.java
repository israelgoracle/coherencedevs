package com.telefonica.coco.api.cargamasivavar;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.telefonica.prov.var.idplantraversalmap.CacheAccessKey_DTO;
import com.telefonica.prov.var.idplantraversalmap.CacheAccessKey_DTO_KEY;
import com.telefonica.prov.var.idplantraversalmap.IdPlanTraversalMap_DTO;
import com.telefonica.prov.var.idplantraversalmap.IdPlanTraversalMap_VAR;
import com.telefonica.prov.var.idplantraversalmap.IdPlanTraversalMap_VAR_KEY;
import com.telefonica.prov.var.idplantraversalmap.MemoryStructure_DTO;
import com.telefonica.prov.var.idplantraversalmap.MemoryStructure_DTO_KEY;
import com.telefonica.prov.var.idplantraversalmap.ProvisionableElementSp_DTO;
import com.telefonica.prov.var.idplantraversalmap.ProvisionableElementSp_DTO_1;

import java.io.File;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/spring/api-FlowVars-context-test.xml"})
public class ApiCargaMasivaVARTest {
/*
	@Test
	public void testVARtoXML(){
	    IdPlanTraversalMap_VAR var=new IdPlanTraversalMap_VAR();
	    
	    CacheAccessKey_DTO cacheAccessKey_DTO=new CacheAccessKey_DTO();
	    cacheAccessKey_DTO.setKeyType(1L);
	    cacheAccessKey_DTO.setKeyValue(2L);
	    var.setCacheAccessKey(cacheAccessKey_DTO);
	    
	    MemoryStructure_DTO mDto= new MemoryStructure_DTO();
	    mDto.setStructureName("Israel te queremos");
	    var.setMemoryStructure(mDto);
	    
	    ProvisionableElementSp_DTO_1 sp_DTO_1=new ProvisionableElementSp_DTO_1();
        sp_DTO_1.setId(new BigDecimal(7));
	    
	    IdPlanTraversalMap_DTO iDto=new IdPlanTraversalMap_DTO();
	    ProvisionableElementSp_DTO pDto =new ProvisionableElementSp_DTO();
        pDto.setId(new BigDecimal(7));
        pDto.setOptional(false);
	    
	    ProvisionableElementSp_DTO[] array={pDto,pDto};
	    
	    pDto.setSkipTreatmentChain(true);
        pDto.setChildrenTreatmentFirst(false);
	    pDto.setAlternativeElement(sp_DTO_1);
	    pDto.setNextElement(sp_DTO_1);
	    
	    iDto.setProvisionableElementSps(array);
	    iDto.setStartElement(sp_DTO_1);
	    iDto.setProvisionableElementSps(array);
	    IdPlanTraversalMap_DTO[] a2={iDto,iDto};
	    var.setIdPlanTraversalMaps(a2);
	    

	    try {

	        File file = new File("src/test/resources/VarRellenable.xml");
	        JAXBContext jaxbContext = JAXBContext.newInstance(IdPlanTraversalMap_VAR.class);
	        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

	        // output pretty printed
	        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        jaxbMarshaller.marshal(var, file);
	        jaxbMarshaller.marshal(var, System.out);

	          } catch (JAXBException e) {
	        e.printStackTrace();
	          }
	}
	
	@Test
    public void testKeytoXML(){
	    IdPlanTraversalMap_VAR_KEY key =new IdPlanTraversalMap_VAR_KEY();
	    CacheAccessKey_DTO_KEY cacheAccessKey_DTO=new CacheAccessKey_DTO_KEY();
        cacheAccessKey_DTO.setKeyType(1L);
        cacheAccessKey_DTO.setKeyValue(2L);
	    key.setCacheAccessKey(cacheAccessKey_DTO);
	    
	    MemoryStructure_DTO_KEY mDto= new MemoryStructure_DTO_KEY();
        mDto.setStructureName("Israel es mi héroe");
        key.setMemoryStructure(mDto);
        try {

            File file = new File("src/test/resources/KeyRellenable.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(IdPlanTraversalMap_VAR_KEY.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(key, file);
            jaxbMarshaller.marshal(key, System.out);

              } catch (JAXBException e) {
            e.printStackTrace();
              }
	    
	}
	*/
	@Test
    public void testCargaUnitariaVAR(){
	     CargaMasivaVARApi cApi=new CargaMasivaVARApi();
	     cApi.cargaMasivaVar("D:\\temp\\CargaVAR.csv");
	}
}
