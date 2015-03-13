package com.sqm.dashboard.dao;

import com.mongodb.DBCollection;
import com.sqm.dashboard.VO.AlmVO;

public interface AlmSchedularDAO {

	public void validatorInsertion(AlmVO almVO, DBCollection table) throws Exception;
	
	public  void insertAlmToDb(AlmVO almVO, DBCollection table, String keyValue)throws Exception;
	
	public void updateAlmToDb(AlmVO almVO, DBCollection table, String keyValue) throws Exception;
	
}
