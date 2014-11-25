
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class FirstCassandra {

	private Cluster cluster;
    private Session session;
    private ResultSet results;
	public void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		Metadata metadata = cluster.getMetadata();
		System.out.printf("Connected to cluster: %s\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session=cluster.connect("mydb");
		results = session.execute("select * from Dogs where block_id=1 and breed='xyz'");
		for(Row result:results){
			System.out.println("breed:" + result.getString("color"));
		}
		cluster.close();
		
	}

	public static void main(String[] args) {
		FirstCassandra client = new FirstCassandra();
		client.connect("localhost");
	}
}

