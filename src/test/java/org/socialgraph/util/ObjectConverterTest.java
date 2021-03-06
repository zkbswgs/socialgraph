package org.socialgraph.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.socialgraph.model.Person;
import org.socialgraph.neo4j.DatabaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring-context/socialgraph-context.xml"})
public class ObjectConverterTest {

	@Autowired
	private DatabaseManager databaseMgr;


	@Test
	public void testObjectConversion() throws Exception {
		GraphDatabaseService gds = databaseMgr.getDatabaseService();
		Transaction tx = databaseMgr.startTransaction();
		Node node = gds.createNode();
		node.setProperty("displayName", "test name2");
		
		Person obj = new Person();
		ObjectConverter.populate(obj, node);
		
		databaseMgr.endTransaction(tx);
		Assert.assertNotNull(obj.getDisplayName());
		Assert.assertTrue("test name2".equals(obj.getDisplayName()));
	}

	@Test
	public void testNodeConversion() throws Exception {
		GraphDatabaseService gds = databaseMgr.getDatabaseService();
		Person obj = new Person();
		obj.setDisplayName("bob ");
		obj.setAboutMe("who");
		obj.setDisplayName("Bob Who");
		Transaction tx = databaseMgr.startTransaction();
		Node node = gds.createNode();
		ObjectConverter.convertToNode(obj, node);
		Assert.assertNotNull(obj.getDisplayName().equals(node.getProperty("displayName")));
		databaseMgr.endTransaction(tx);
	}

}
