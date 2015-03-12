package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseId;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseName;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.AlmReleaseVO;
import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.VO.SchedularTCExecStatusVO;
import com.sqm.dashboard.dao.impl.AlmReleaseSchedularDAOImpl;
import com.sqm.dashboard.dao.impl.AlmSchedularDAOImpl;
import com.sqm.dashboard.schedular.AlmSchedularImpl;
import com.sqm.dashboard.util.RestConnectorUtility;

public class AlmReleasesSchedularServiceImpl {

	
	
	
	
static final Log log = LogFactory.getLog(DashboardSchedularServiceImpl.class);
	
	/*@Autowired*/
	DashboardDomainServiceImpl dbDomainServiceImpl = new DashboardDomainServiceImpl();
	
	/*@Autowired*/
	DashboardProjectServiceImpl dbProjectServiceImpl = new DashboardProjectServiceImpl();
	
	/*@Autowired*/
	DashboardReleaseServiceImpl dbReleaseServiceImpl = new DashboardReleaseServiceImpl();
	
	/*@Autowired*/
	DashboardDefectServiceImpl dbDefectServiceImpl = new DashboardDefectServiceImpl();
	
	/*@Autowired*/
	DashboardTestcaseServiceImpl dbTestcaseServiceImpl = new DashboardTestcaseServiceImpl();
	
	/*@Autowired*/
	AlmDomainProject almDomainProject = new AlmDomainProject();
	
	/*@Autowired*/
	AlmDomainProjectReleaseId almDomainProjectReleaseId = new AlmDomainProjectReleaseId();
	
	
	AlmDomainProjectReleaseName almDomainProjectReleaseName = new AlmDomainProjectReleaseName();
	
	
	
	

	public String saveReleasesDetails(RestConnectorUtility connection,
			Map<String, String> requestHeaders, String username, String password)
			throws Exception {
		Response jsonDomains = null;
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		HashMap<String,String> jsonNo = new HashMap<String,String>();
		ArrayList<AlmDomainProject> almDomainProj = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectReleaseId> almDomainProjReleaseId = new ArrayList<AlmDomainProjectReleaseId>();

		AlmReleaseVO almReleaseVO = new AlmReleaseVO();
		AlmSchedularDAOImpl almDaoObj = new AlmSchedularDAOImpl();
		
		try {
		
			jsonDomainsMap = dbDomainServiceImpl.getAlmDomains(connection, requestHeaders);
			log.info("###jsonDomainsMap### : "  + jsonDomainsMap);
			
			Set<String> domains = jsonDomainsMap.keySet();
			log.info("Domains : " + domains);
			
			String domainName = null;
			String projectName = null;
			
			for(String domain : domains){ 
				log.info("Value of "+ domain +" is: "+ jsonDomainsMap.get(domain));
				domainName = jsonDomainsMap.get(domain);
				
				jsonProjectsMap = dbProjectServiceImpl.getAlmProjects(connection, requestHeaders, domainName);
				log.info("###jsonProjectsMap### : "  + jsonProjectsMap);
				
				Set<String> projects = jsonProjectsMap.keySet();
				log.info("Projects : " + projects);
				
				for(String project : projects){ 
					log.info("domainName : " + domainName);
					projectName = jsonProjectsMap.get(project);
					log.info("Value of "+ project +" is: "+ jsonProjectsMap.get(project));

					log.info("domainName : " + domainName + "##" + "projectName : " + projectName);
					
					almDomainProj.add(new AlmDomainProject(domainName, projectName));
				}
			}
			
			log.info("Size of almDomainProj : " + almDomainProj.size());
			
			for(int i=0; i<almDomainProj.size(); i++) {
					
					log.info("**Domain #" + i + "##" + almDomainProj.get(i).getDomain());
					log.info("**Project #" + i + "##" + almDomainProj.get(i).getProject());
		    
				    String releasesUrl = connection.buildEntityCollectionUrl("release", almDomainProj.get(i).getDomain(), almDomainProj.get(i).getProject());
				    log.info("releasesUrl : " + releasesUrl);
					
				    List<String> releaseIds = dbReleaseServiceImpl.getAlmReleasesIds(connection, releasesUrl, requestHeaders);
				    log.info("ALM Release Ids : " + releaseIds.toString());
				    
				    for (String releaseId : releaseIds) {
				    	almDomainProjReleaseId.add(new AlmDomainProjectReleaseId(domainName, projectName, releaseId));
					}
				   
				    
				    for(int j=0; j<almDomainProjReleaseId.size(); j++) {
						
						log.info("Domain #" + j + "##" + almDomainProjReleaseId.get(j).getDomain());
						log.info("Project #" + j + "##" + almDomainProjReleaseId.get(j).getProject());
						log.info("ReleaseId #" + j + "##" + almDomainProjReleaseId.get(j).getReleaseId());
						
			    
						almReleaseVO.setDomain(almDomainProjReleaseId.get(j).getDomain());
						almReleaseVO.setProject(almDomainProjReleaseId.get(j).getProject());
						almReleaseVO.setId(almDomainProjReleaseId.get(j).getReleaseId());
						
						System.out.println("The domain is : " + almReleaseVO.getDomain());
						System.out.println("The project is : " + almReleaseVO.getProject());
						System.out.println("The id is : " + almReleaseVO.getId());
						
						log.info("Release VO is :" + almReleaseVO.toString());
						
						System.out.println(almReleaseVO.toString());
						
						
						
						AlmReleaseSchedularDAOImpl almReleaseSchedularDaoImpl=new AlmReleaseSchedularDAOImpl();
						almReleaseSchedularDaoImpl.insertAlmReleasesToDb(almReleaseVO);
					
				    }
						
			}
		} catch (Exception e) {
			log.error("Error in schedular : " + e.getMessage());
			throw e;
		}
		return null;
	}


}
