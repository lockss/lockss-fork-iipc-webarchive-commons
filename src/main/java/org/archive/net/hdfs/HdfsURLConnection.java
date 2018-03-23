package org.archive.net.hdfs;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HdfsURLConnection extends URLConnection {

	protected static FileSystem hdfs = null;
	protected static FSDataInputStream is = null;
	
	protected HdfsURLConnection(URL url) {
		super(url);
	}

	@Override
	public void connect() throws IOException {
		Configuration config = new Configuration();

		try {
			URI baseURI = new URI("hdfs://" + url.getHost() + ":" + url.getPort() + "/");
			hdfs = FileSystem.get(baseURI, config);
		} catch (URISyntaxException e) {
		    // This should never happen
			e.printStackTrace();
		}
	}

	public InputStream getInputStream() throws IOException {
		this.connect();
		is = hdfs.open(new Path(url.getPath()));
		return new BufferedInputStream(is);
	}
}
