package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import pojos.Candidate;

import static utils.DBUtils.*;

public class CandidateDaoImpl implements CandidateDao{
	
	Connection cn ;
	PreparedStatement pst1,pst2,pst3,pst4;
	
	public CandidateDaoImpl() throws SQLException {
		 cn = openConnection();
		
		 pst1 = cn.prepareStatement("select * from candidates");
		 pst2 = cn.prepareStatement("update candidates set votes = votes + 1 where id=?");
		 pst3 = cn.prepareStatement("Select * from candidates order by votes desc limit 2");
		 pst4 = cn.prepareStatement("select party,sum(votes) from candidates group by party");
		System.out.println("Candidate dao creared");
	}

	@Override
	public List<Candidate> getAllCandidates() throws SQLException {
		
		try(ResultSet rst = pst1.executeQuery()){
		
		List<Candidate> candidateList = new ArrayList<>();
		while(rst.next())
				candidateList.add(new Candidate(rst.getInt(1), rst.getString(2),rst.getString(3), rst.getInt(4)));
		
		return candidateList;
		}
	}
	@Override
	public String incCandidateVotes(int cid) throws SQLException {
		
		pst2.setInt(1, cid);
		
		int rowCnt = pst2.executeUpdate();
		if(rowCnt == 1) {
			return"Vote Confirmed";
		}else {
			return "Voting failed";
		}
		
	}
	
	@Override
	public List<Candidate> getTopTwo() throws SQLException{
		
		try(ResultSet rst = pst3.executeQuery()){
			
			List<Candidate> topTwo = new ArrayList<>();
			while(rst.next()) {
				topTwo.add(new Candidate(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getInt(4)));
			}
		
		
		return topTwo;
		}
		
	}

	@Override
	public Map<String, Integer> partyWiseVotes() throws SQLException {
		try(ResultSet rst = pst4.executeQuery()){
			Map<String,Integer> pMap = new LinkedHashMap<>();
			while(rst.next()) {
			pMap.put(rst.getString(1), rst.getInt(2));
			}
		
		return pMap;
		}
	}
	public void cleanup() throws SQLException {
		pst1.close();
		pst2.close();
		pst3.close();
		pst4.close();
		closeConnection();
	}



	
	
}
