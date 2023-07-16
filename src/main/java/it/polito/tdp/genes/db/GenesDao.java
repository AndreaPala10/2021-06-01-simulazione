package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public void getAllGenes(Map<String,Genes> idMap){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome "
				+ "FROM Genes";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				
				idMap.put(genes.getGeneId(), genes);
			}
			res.close();
			st.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Genes> getVertici(Map<String,Genes> idMap){
		String sql = "SELECT DISTINCT GeneID as id "
				+ "FROM genes "
				+ "WHERE Essential = 'Essential'";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.containsKey(res.getString("id"))) {	
				result.add(idMap.get(res.getString("id")));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenza> getArchi(Map<String,Genes> idMap){
		String sql = "SELECT i.GeneID1 as id1, i.GeneID2 AS id2, ABS(i.Expression_Corr) AS peso "
				+ "FROM interactions i, genes g1, genes g2 "
				+ "WHERE i.GeneID1=g1.GeneID "
				+ "AND i.GeneID2=g2.GeneID "
				+ "AND g1.Essential='Essential' "
				+ "AND g2.Essential='Essential' "
				+ "AND i.GeneID1<>i.GeneID2 "
				+ "GROUP BY id1,id2";
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.containsKey(res.getString("id1")) && idMap.containsKey(res.getString("id2"))){	
				result.add(new Adiacenza(idMap.get(res.getString("id1")),idMap.get(res.getString("id2")), res.getDouble("peso")));
				}
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
