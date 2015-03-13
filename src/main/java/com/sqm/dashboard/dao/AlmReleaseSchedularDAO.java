package com.sqm.dashboard.dao;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmReleaseVO;

public interface AlmReleaseSchedularDAO {

	public void storeAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection collection) throws Exception;
	
	public void insertAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection collection, String keyValue) throws Exception;
	
	public void updateAlmReleasesToDb(AlmReleaseVO almReleaseVO, DBCollection collection, String keyValue) throws Exception;
	
}
