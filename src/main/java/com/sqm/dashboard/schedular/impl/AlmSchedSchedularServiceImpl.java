package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseId;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseName;
import com.sqm.dashboard.VO.AlmTestcaseVO;
import com.sqm.dashboard.VO.AlmVO;
import com.sqm.dashboard.VO.SchedularDefectsVO;
import com.sqm.dashboard.dao.impl.AlmSchedularDAOImpl;
import com.sqm.dashboard.schedular.AlmSchedularService;
import com.sqm.dashboard.util.RestConnectorUtility;

@Service("almSchedSchedularServiceImpl")
public class AlmSchedSchedularServiceImpl implements AlmSchedularService{	
	static final Log log = LogFactory.getLog(AlmSchedSchedularServiceImpl.class);
	
	/*@Autowired*/
	AlmSchedDomainServiceImpl almSchedDomainServiceImpl = new AlmSchedDomainServiceImpl();
	
	/*@Autowired*/
	AlmSchedProjectServiceImpl almSchedProjectServiceImpl = new AlmSchedProjectServiceImpl();
	
	/*@Autowired*/
	AlmSchedReleaseServiceImpl almSchedReleaseServiceImpl = new AlmSchedReleaseServiceImpl();
	
	/*@Autowired*/
	AlmSchedDefectServiceImpl almSchedDefectServiceImpl = new AlmSchedDefectServiceImpl();
	
	/*@Autowired*/
	AlmSchedTestcaseServiceImpl almSchedTestcaseServiceImpl = new AlmSchedTestcaseServiceImpl();
	
	/*@Autowired*/
	AlmDomainProject almDomainProject = new AlmDomainProject();
	
	/*@Autowired*/
	AlmDomainProjectReleaseId almDomainProjectReleaseId = new AlmDomainProjectReleaseId();
	
	/*@Autowired*/
	AlmDomainProjectReleaseName almDomainProjectReleaseName = new AlmDomainProjectReleaseName();
	
	public void saveAlmDetails(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String username, String password, DBCollection collection) throws Exception {
		
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		ArrayList<AlmDomainProject> almDomainProj = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectReleaseId> almDomainProjReleaseId = new ArrayList<AlmDomainProjectReleaseId>();
		ArrayList<AlmDomainProjectReleaseName> almDomainProjReleaseName = new ArrayList<AlmDomainProjectReleaseName>();
		
		AlmTestcaseVO almTCVO = new AlmTestcaseVO();
		SchedularDefectsVO schedDefectsVO = new SchedularDefectsVO();
		AlmVO almVO = new AlmVO();
		AlmSchedularDAOImpl almSchedularDAOImpl = new AlmSchedularDAOImpl();
		
		try {
		
			jsonDomainsMap = almSchedDomainServiceImpl.getAlmDomains(conn, requestHeaders);
			log.info("###jsonDomainsMap### : "  + jsonDomainsMap);
			
			Set<String> domains = jsonDomainsMap.keySet();
			log.info("Domains : " + domains);
			
			String domainName = null;
			String projectName = null;
			
			for(String domain : domains){ 
				log.info("Value of "+ domain +" is: "+ jsonDomainsMap.get(domain));
				domainName = jsonDomainsMap.get(domain);
				
				jsonProjectsMap = almSchedProjectServiceImpl.getAlmProjects(conn, requestHeaders, domainName);
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
		    
				    String releasesUrl = conn.buildEntityCollectionUrl("release", almDomainProj.get(i).getDomain(), almDomainProj.get(i).getProject());
				    log.info("releasesUrl : " + releasesUrl);
					
				    List<String> releaseIds = almSchedReleaseServiceImpl.getAlmReleasesIds(conn, releasesUrl, requestHeaders);
				    log.info("ALM Release Ids : " + releaseIds.toString());
				    
				    for (String releaseId : releaseIds) {
				    	almDomainProjReleaseId.add(new AlmDomainProjectReleaseId(domainName, projectName, releaseId));
					}
				    
				    List<String> releaseNames = almSchedReleaseServiceImpl.getAlmReleasesNames(conn, releasesUrl, requestHeaders);
				    log.info("ALM Release Names : " + releaseNames.toString());
				    
				    for (String releaseName : releaseNames) {
				    	almDomainProjReleaseName.add(new AlmDomainProjectReleaseName(domainName, projectName, releaseName));
					}
				    
				    for(int j=0; j<almDomainProjReleaseId.size(); j++) {
						
						log.info("Domain #" + j + "##" + almDomainProjReleaseId.get(j).getDomain());
						log.info("Project #" + j + "##" + almDomainProjReleaseId.get(j).getProject());
						log.info("ReleaseId #" + j + "##" + almDomainProjReleaseId.get(j).getReleaseId());
						log.info("ReleaseName #" + j + "##" + almDomainProjReleaseName.get(j).getReleaseName());
			    
						String defectsUrl = conn.buildEntityCollectionUrl("defect", almDomainProjReleaseId.get(j).getDomain(), almDomainProjReleaseId.get(j).getProject());
						log.info("defectsUrl : " + defectsUrl);
					
						schedDefectsVO = almSchedDefectServiceImpl.getAlmDefects(conn, requestHeaders, defectsUrl, almDomainProjReleaseId.get(j).getReleaseId());
						
						String testcasesUrl = conn.buildEntityCollectionUrl("run", almDomainProjReleaseId.get(j).getDomain(), almDomainProjReleaseId.get(j).getProject());
						log.info("testcasesUrl : " + testcasesUrl);
						
						almTCVO = almSchedTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, almDomainProjReleaseId.get(j).getReleaseId());
						
						almVO.setDomain(almDomainProjReleaseId.get(j).getDomain());
						almVO.setProject(almDomainProjReleaseId.get(j).getProject());
						almVO.setRelease(almDomainProjReleaseName.get(j).getReleaseName());
						almVO.setDefectsVO(schedDefectsVO);
						almVO.setAlmTCVO(almTCVO);
						
						almSchedularDAOImpl.validatorInsertion(almVO, collection);
				    }
			}
		} catch (Exception e) {
			log.error("Error in schedular : " + e.getMessage());
			throw e;
		}
	}
}
