package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseId;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseName;
import com.sqm.dashboard.VO.AlmReleaseVO;
import com.sqm.dashboard.VO.SchedularReleaseDefectsVO;
import com.sqm.dashboard.dao.impl.AlmReleaseSchedularDAOImpl;
import com.sqm.dashboard.schedular.AlmReleasesSchedularService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almReleasesSchedularServiceImpl")
public class AlmReleasesSchedularServiceImpl implements AlmReleasesSchedularService {

	final Logger log = Logger.getLogger(AlmReleasesSchedularServiceImpl.class);
	
	/*@Autowired*/
	AlmSchedDomainServiceImpl almSchedDomainServiceImpl = new AlmSchedDomainServiceImpl();
	
	/*@Autowired*/
	AlmSchedProjectServiceImpl almSchedProjectServiceImpl = new AlmSchedProjectServiceImpl();

	/*@Autowired*/
	AlmSchedReleaseServiceImpl almSchedReleaseServiceImpl = new AlmSchedReleaseServiceImpl();

	/*@Autowired*/
	AlmDomainProject almDomainProject = new AlmDomainProject();
	
	/*@Autowired*/
	AlmDomainProjectReleaseId almDomainProjectReleaseId = new AlmDomainProjectReleaseId();
	
	/*@Autowired*/
	AlmDomainProjectReleaseName almDomainProjectReleaseName = new AlmDomainProjectReleaseName();

	public void saveReleasesDetails(RestConnectorUtility connection, Map<String, String> requestHeaders, 
									String username, String password, DBCollection collection) throws Exception {
		
		log.info("##AlmReleasesSchedularServiceImpl saveReleasesDetails##");
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		ArrayList<AlmDomainProject> almDomainProj = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectReleaseId> almDomainProjReleaseId = new ArrayList<AlmDomainProjectReleaseId>();
		ArrayList<AlmDomainProjectReleaseName> almDomainProjectReleaseName = new ArrayList<AlmDomainProjectReleaseName>();
		
		AlmReleaseSchedularDAOImpl almReleaseSchedularDaoImpl = new AlmReleaseSchedularDAOImpl();
		AlmReleaseVO almReleaseVO = new AlmReleaseVO();
		
		try {
			jsonDomainsMap = almSchedDomainServiceImpl.getAlmDomains(connection, requestHeaders);
			log.info("jsonDomainsMap : "  + jsonDomainsMap);
			
			Set<String> domains = jsonDomainsMap.keySet();
			log.info("Domains : " + domains);
			
			String domainName = null;
			String projectName = null;
			
			for(String domain : domains){ 
				log.info("Value of "+ domain +" is: "+ jsonDomainsMap.get(domain));
				domainName = jsonDomainsMap.get(domain);
				
				jsonProjectsMap = almSchedProjectServiceImpl.getAlmProjects(connection, requestHeaders, domainName);
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
					
				    String releasesUrl = connection.buildEntityCollectionUrl("release", almDomainProj.get(i).getDomain(), almDomainProj.get(i).getProject());
				    log.info("releasesUrl : " + releasesUrl);
					
				    List<String> releaseIds = almSchedReleaseServiceImpl.getAlmReleasesIds(connection, releasesUrl, requestHeaders);
				    log.info("ALM Release Ids : " + releaseIds.toString());
				    
				    List<String> releaseNames = almSchedReleaseServiceImpl.getAlmReleasesNames(connection, releasesUrl, requestHeaders);
				    log.info("ALM Release Names : " + releaseNames.toString());
				    
				    for (String releaseId : releaseIds) {
				    	almDomainProjReleaseId.add(new AlmDomainProjectReleaseId(domainName, projectName, releaseId));
					}
				   
				    for (String releaseName : releaseNames) {
				    	almDomainProjectReleaseName.add(new AlmDomainProjectReleaseName(domainName, projectName, releaseName));
					}
				    
				    for(int j=0; j<almDomainProjReleaseId.size(); j++) {
						
						almReleaseVO.setDomain(almDomainProjReleaseId.get(j).getDomain());
						almReleaseVO.setProject(almDomainProjReleaseId.get(j).getProject());
						almReleaseVO.setReleaseName(almDomainProjectReleaseName.get(j).getReleaseName());
						
						String releaseCyclesUrl = connection.buildEntityCollectionUrl("release-cycle", almDomainProj.get(i).getDomain(), almDomainProj.get(i).getProject());
						log.info("releaseCyclesUrl : " + releaseCyclesUrl);
						    
						ArrayList<String> cycleNames = almSchedReleaseServiceImpl.getAlmReleaseCycleNames(connection, releaseCyclesUrl, requestHeaders, almDomainProjReleaseId.get(j).getReleaseId());
						log.info("ALM Release cycleNames : " + cycleNames.toString());
						almReleaseVO.setCycleNames(cycleNames);
						
						String releaseDefectsUrl = connection.buildEntityCollectionUrl("defect", almDomainProj.get(i).getDomain(), almDomainProj.get(i).getProject());
						log.info("releaseDefectsUrl : " + releaseDefectsUrl);
						
						log.info("Domain# : " + almDomainProjReleaseId.get(j).getDomain());
						log.info("Project# : " + almDomainProjReleaseId.get(j).getProject());
						log.info("Release# : " + almDomainProjectReleaseName.get(j).getReleaseName());
						
						System.out.println("Domain# : " + almDomainProjReleaseId.get(j).getDomain());
						System.out.println("Project# : " + almDomainProjReleaseId.get(j).getProject());
						System.out.println("Release# : " + almDomainProjectReleaseName.get(j).getReleaseName());
						
						SchedularReleaseDefectsVO schedReleaseDefectsVO = almSchedReleaseServiceImpl.getAlmReleaseDefectsData(connection, requestHeaders, releaseDefectsUrl, almDomainProjReleaseId.get(j).getReleaseId());
						almReleaseVO.setSchedReleaseDefectsVO(schedReleaseDefectsVO);

						//almReleaseVO.setStatus(status);
						almReleaseSchedularDaoImpl.storeAlmReleasesToDb(almReleaseVO, collection);
				    }
			}
		} catch (Exception e) {
			log.error("Error in Alm Release Schedular : " + e.getMessage());
			throw e;
		}
	}
}
