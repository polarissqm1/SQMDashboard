package com.sqm.dashboard.schedular.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.ws.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sqm.dashboard.schedular.DashboardSchedularService;
import com.sqm.dashboard.util.RestConnectorUtility;
import com.sqm.dashboard.VO.AlmDomainProject;
import com.sqm.dashboard.VO.AlmDomainProjectReleaseId;
import com.sqm.dashboard.dao.impl.AlmSchedularDAOImpl;

@Service("dbSchedularServiceImpl")
public class DashboardSchedularServiceImpl implements DashboardSchedularService{
	
	static final Log log = LogFactory.getLog(DashboardSchedularServiceImpl.class);
	
	@Autowired
	DashboardDomainServiceImpl dbDomainServiceImpl;
	
	@Autowired
	DashboardProjectServiceImpl dbProjectServiceImpl;
	
	@Autowired
	DashboardReleaseServiceImpl dbReleaseServiceImpl;
	
	@Autowired
	DashboardDefectServiceImpl dbDefectServiceImpl;
	
	@Autowired
	DashboardTestcaseServiceImpl dbTestcaseServiceImpl;
	
	@Autowired
	AlmDomainProject almDomainProject;
	
	@Autowired
	AlmDomainProjectReleaseId almDomainProjectReleaseId;
	
	public String saveAlmDetails(RestConnectorUtility conn, Map<String, String> requestHeaders, 
									String username, String password) throws Exception {
		
		Response jsonDomains = null;
		HashMap<String,String> jsonDomainsMap = new HashMap<String,String>();
		HashMap<String,String> jsonProjectsMap = null;
		HashMap<String,String> jsonNo = new HashMap<String,String>();
		ArrayList<AlmDomainProject> almDomainProj = new ArrayList<AlmDomainProject>();
		ArrayList<AlmDomainProjectReleaseId> almDomainProjReleaseId = new ArrayList<AlmDomainProjectReleaseId>();
		String defects = null;
		String testcases = null;
		
		try {
		
			jsonDomainsMap = dbDomainServiceImpl.getAlmDomains(conn, requestHeaders);
			log.info("###jsonDomainsMap### : "  + jsonDomainsMap);
			
			Set<String> domains = jsonDomainsMap.keySet();
			log.info("Domains : " + domains);
			
			String domainName = null;
			String projectName = null;
			
			for(String domain : domains){ 
				log.info("Value of "+ domain +" is: "+ jsonDomainsMap.get(domain));
				domainName = jsonDomainsMap.get(domain);
				
				jsonProjectsMap = dbProjectServiceImpl.getAlmProjects(conn, requestHeaders, domainName);
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
					
				    List<String> releaseIds = dbReleaseServiceImpl.getAlmReleases(conn, releasesUrl, requestHeaders);
				    log.info("ALM Release Ids : " + releaseIds.toString());
				    
				    for (String releaseId : releaseIds) {
				    	almDomainProjReleaseId.add(new AlmDomainProjectReleaseId(domainName, projectName, releaseId));
					}
				    
				    for(int j=0; j<almDomainProjReleaseId.size(); j++) {
						
						log.info("Domain #" + j + "##" + almDomainProjReleaseId.get(j).getDomain());
						log.info("Project #" + j + "##" + almDomainProjReleaseId.get(j).getProject());
						log.info("ReleaseId #" + j + "##" + almDomainProjReleaseId.get(j).getReleaseId());
			    
						String defectsUrl = conn.buildEntityCollectionUrl("defect", almDomainProjReleaseId.get(j).getDomain(), almDomainProjReleaseId.get(j).getProject());
						log.info("defectsUrl : " + defectsUrl);
					
						defects = dbDefectServiceImpl.getAlmDefects(conn, requestHeaders, defectsUrl, almDomainProjReleaseId.get(j).getReleaseId());
						
						String testcasesUrl = conn.buildEntityCollectionUrl("run", almDomainProjReleaseId.get(j).getDomain(), almDomainProjReleaseId.get(j).getProject());
						log.info("testcasesUrl : " + testcasesUrl);
						
						testcases = dbTestcaseServiceImpl.getAlmTestcases(conn, requestHeaders, testcasesUrl, almDomainProjReleaseId.get(j).getReleaseId());
						
						AlmSchedularDAOImpl almDaoObj=new AlmSchedularDAOImpl();
						//almDaoObj.insertAlmToDb(almVO);
				    }
						
			}
		} catch (Exception e) {
			log.error("Error in schedular : " + e.getMessage());
			throw e;
		}
		return null;
	}

}
