package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import pojos.Candidate;

public interface CandidateDao {
	
	List<Candidate> getAllCandidates() throws SQLException; 
	
	String incCandidateVotes(int cid) throws SQLException;
	
	List<Candidate> getTopTwo() throws SQLException;

	Map<String,Integer> partyWiseVotes() throws SQLException;

}
