package pagegen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import super_simple_web_server.Status;
import super_simple_web_server.SuperSimpleWebServer.Request;

public class Favicon {
	private static final Logger s_logger = Logger.getLogger(Favicon.class.getCanonicalName());
	
	public static final void getFavicon(final Request request) throws IOException {
		final String untrust_uri = request.getUri();
		if (untrust_uri.equals("/favicon.ico")) {
			final Path iconPath = Path.of("src/favicon.ico");
			final byte[] iconBytes = Files.readAllBytes(iconPath);
			s_logger.log(Level.INFO, "number of bytes: " + iconBytes.length);
			request.getBinaryWriter("image/x-icon").write(iconBytes, iconBytes.length);
		}
	}

}
